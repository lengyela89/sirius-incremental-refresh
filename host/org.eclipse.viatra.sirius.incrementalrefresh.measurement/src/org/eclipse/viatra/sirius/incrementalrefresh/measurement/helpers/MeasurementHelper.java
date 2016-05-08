package org.eclipse.viatra.sirius.incrementalrefresh.measurement.helpers;

import java.util.Collection;
import java.util.Map;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.transaction.RecordingCommand;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.sirius.business.api.componentization.ViewpointRegistry;
import org.eclipse.sirius.business.api.dialect.DialectManager;
import org.eclipse.sirius.business.api.session.SavingPolicy;
import org.eclipse.sirius.business.api.session.Session;
import org.eclipse.sirius.business.api.session.SessionManager;
import org.eclipse.sirius.viewpoint.DRepresentation;
import org.eclipse.sirius.viewpoint.description.RepresentationDescription;
import org.eclipse.sirius.viewpoint.description.Viewpoint;
import org.eclipse.viatra.sirius.incrementalrefresh.measurement.util.MeasurementUtil;

import com.google.common.collect.Lists;

import hu.bme.mit.inf.concerto.telecare.example.tdk.model.telecare.EventCompletedTrigger;
import hu.bme.mit.inf.concerto.telecare.example.tdk.model.telecare.FixedIntervalTrigger;
import hu.bme.mit.inf.concerto.telecare.example.tdk.model.telecare.Gateway;
import hu.bme.mit.inf.concerto.telecare.example.tdk.model.telecare.Host;
import hu.bme.mit.inf.concerto.telecare.example.tdk.model.telecare.Measurement;
import hu.bme.mit.inf.concerto.telecare.example.tdk.model.telecare.MeasurementType;
import hu.bme.mit.inf.concerto.telecare.example.tdk.model.telecare.ReportingEvent;
import hu.bme.mit.inf.concerto.telecare.example.tdk.model.telecare.Sensor;
import hu.bme.mit.inf.concerto.telecare.example.tdk.model.telecare.TelecarePackage;
import hu.bme.mit.inf.concerto.telecare.example.tdk.model.telecare.TelecareSystem;
import hu.bme.mit.inf.concerto.telecare.example.tdk.model.telecare.Trigger;

public class MeasurementHelper {
	private static final int MEASUREMENT_REPEAT = 5;

	public void measure(String sessionResource, String viewpointName, String representationDescriptionName, String model) {
		System.out.println("Opening session...");
		final Session session = getSession(sessionResource);
		session.open(new NullProgressMonitor());
		System.out.println("Session opened.");
		
		final Resource semanticResource = getSemanticResource(session, model);
		
		final EObject semanticRoot = semanticResource.getContents().get(0);
		
		final Viewpoint viewpoint = getViewpoint(viewpointName);
		
		final RepresentationDescription representationDescription = getRepresentationDescription(representationDescriptionName, viewpoint);
		
		final TransactionalEditingDomain ted = session.getTransactionalEditingDomain();

		// Sets session saving policy in order to not save the semanticResource...
		session.setSavingPolicy(new SavingPolicy() {
			
			@Override
			public Collection<Resource> save(Iterable<Resource> resourcesToSave, Map<?, ?> options, IProgressMonitor monitor) {
				return Lists.newArrayList(semanticResource);
			}
		});
		
		/* Measurement */
		System.out.println("Measure: " + model);
		MeasurementUtil.getInstance().clear();
		measureDiagramOpening(model, ted, semanticRoot, representationDescription, session);
		measureModifications(model, ted, semanticRoot, representationDescription, session);
		MeasurementUtil.getInstance().printMeasurementResults();
		MeasurementUtil.getInstance().clear();
		/* *********** */

		System.out.println("Closing session...");
		session.close(new NullProgressMonitor());
		System.out.println("Session closed.");
		System.out.println();
	}
	
	private Session getSession(String resourceURI) {
		return SessionManager.INSTANCE.getSession(URI.createPlatformResourceURI(resourceURI, true), new NullProgressMonitor());		
	}
	
	private Resource getSemanticResource(Session session, String model) {
		for (Resource resource : session.getSemanticResources()) {
			if (resource.getURI().toString().endsWith(model)) {
				return resource;
			}
		}
		
		return null;
	}
	
	private Viewpoint getViewpoint(String viewpointID) {
		for (Viewpoint viewpoint : ViewpointRegistry.getInstance().getViewpoints()) {
			if (viewpointID.equals(viewpoint.getName())) {
				return viewpoint;
			}
		}
		
		return null;
	}
	
	private RepresentationDescription getRepresentationDescription(String representationID, Viewpoint viewpoint) {
		for (RepresentationDescription representationDescription : viewpoint.getOwnedRepresentations()) {
			if (representationID.equals(representationDescription.getName())) {
				return representationDescription;
			}
		}
		
		return null;
	}
	
	private void measureDiagramOpening(String model, TransactionalEditingDomain ted, EObject semanticRoot, RepresentationDescription representationDescription, Session session) {
		System.out.println("Measure Diagram Opening (" + MEASUREMENT_REPEAT + ")");
		
		for (int i = 0; i < MEASUREMENT_REPEAT; i++) {
			CreateRepresentationCommand crc = new CreateRepresentationCommand(ted, "measure_" + i, semanticRoot, representationDescription, session);
			
			MeasurementUtil.getInstance().startMeasurement("Opening_" + i, model, "Opening");
			ted.getCommandStack().execute(crc);
			MeasurementUtil.getInstance().endMeasurement();
			
			ted.getCommandStack().execute(new DeleteRepresentationCommand(ted, crc.getRepresentation(), session));
		}
	}
	
	private void measureModifications(String model, TransactionalEditingDomain ted, EObject semanticRoot, RepresentationDescription representationDescription, Session session) {
		CreateRepresentationCommand crc = new CreateRepresentationCommand(ted, "measurement_insert", semanticRoot, representationDescription, session);
		ted.getCommandStack().execute(crc);
		
		/* Measure modifications */
		measureNodeCreation(model, ted, semanticRoot, crc.getRepresentation());
		measureRBECreation_Sensor_ConnectedTo(model, ted, semanticRoot, crc.getRepresentation());
		measureEBECreation_Measurement(model, ted, semanticRoot, crc.getRepresentation());
		measureEBECreation_ReportingEvent(model, ted, semanticRoot, crc.getRepresentation());
		/* ********************* */
		
		ted.getCommandStack().execute(new DeleteRepresentationCommand(ted, crc.getRepresentation(), session));
	}
	
	private void measureNodeCreation(String model, TransactionalEditingDomain ted, EObject semanticRoot, DRepresentation representation) {
		System.out.println("Measure Node Creation (" + MEASUREMENT_REPEAT + ")");
		
		for (int i = 0; i < MEASUREMENT_REPEAT; i++) {
			MeasurementUtil.getInstance().startMeasurement("Creating Host _ " + i, model, "Create Host node");

			ted.getCommandStack().execute(new RecordingCommand(ted) {
				
				@Override
				protected void doExecute() {
					createHost((TelecareSystem) semanticRoot);
					
					DialectManager.INSTANCE.refresh(representation, true, new NullProgressMonitor());
				}
				
			});

			MeasurementUtil.getInstance().endMeasurement();
		}
	}
	
	private void measureRBECreation_Sensor_ConnectedTo(String model, TransactionalEditingDomain ted, EObject semanticRoot, DRepresentation representation) {
		System.out.println("Measure RelationBasedEdge Creation - Sensor_ConnectedTo (" + MEASUREMENT_REPEAT + ")");
		
		for (int i = 0; i < MEASUREMENT_REPEAT; i++) {
			MeasurementUtil.getInstance().startMeasurement("Creating RelationBasedEdge - Sensor_connectedTo _ " + i, model, "Create RelationBasedEdge - Sensor_connectedTo");

			ted.getCommandStack().execute(new RecordingCommand(ted) {
				
				@Override
				protected void doExecute() {
					createRBE_sensor_connectedTo((TelecareSystem) semanticRoot);
					
					DialectManager.INSTANCE.refresh(representation, true, new NullProgressMonitor());
				}
				
			});

			MeasurementUtil.getInstance().endMeasurement();
		}
	}
	
	private void measureEBECreation_Measurement(String model, TransactionalEditingDomain ted, EObject semanticRoot, DRepresentation representation) {
		System.out.println("Measure ElementBasedEdge Creation - Measurement (" + MEASUREMENT_REPEAT + ")");
		
		for (int i = 0; i < MEASUREMENT_REPEAT; i++) {
			MeasurementUtil.getInstance().startMeasurement("Creating ElementBasedEdge - Measurement _ " + i, model, "Create ElementBasedEdge - Measurement");

			ted.getCommandStack().execute(new RecordingCommand(ted) {
				
				@Override
				protected void doExecute() {
					createEBE_measurement((TelecareSystem) semanticRoot);
					
					DialectManager.INSTANCE.refresh(representation, true, new NullProgressMonitor());
				}
				
			});

			MeasurementUtil.getInstance().endMeasurement();
		}
	}
	
	private void measureEBECreation_ReportingEvent(String model, TransactionalEditingDomain ted, EObject semanticRoot, DRepresentation representation) {
		System.out.println("Measure ElementBasedEdge Creation - ReportingEvent (" + MEASUREMENT_REPEAT + ")");
		
		for (int i = 0; i < MEASUREMENT_REPEAT; i++) {
			MeasurementUtil.getInstance().startMeasurement("Creating ElementBasedEdge - ReportingEvent _ " + i, model, "Create ElementBasedEdge - ReportingEvent");

			ted.getCommandStack().execute(new RecordingCommand(ted) {
				
				@Override
				protected void doExecute() {
					createEBE_reportingEvent((TelecareSystem) semanticRoot);
					
					DialectManager.INSTANCE.refresh(representation, true, new NullProgressMonitor());
				}
				
			});

			MeasurementUtil.getInstance().endMeasurement();
		}
	}
	
	private void createRBE_sensor_connectedTo(TelecareSystem telecareSystem) {
		Sensor sensor = createSensor(telecareSystem);
		Gateway gateway = createGateway(telecareSystem);
		
		set(sensor, TelecarePackage.eINSTANCE.getSensor_ConnectedTo(), gateway);
	}

	private void createEBE_measurement(TelecareSystem telecareSystem) {
		Sensor sensor = createSensor(telecareSystem);
		Gateway gateway = createGateway(telecareSystem);
		MeasurementType measurementType = createMeasurementType(sensor);
		FixedIntervalTrigger fit = createFixedIntervalTrigger(gateway);
		
		Measurement measurement = createMeasurement(fit);
		set(measurement, TelecarePackage.eINSTANCE.getMeasurement_MeasurementType(), measurementType);
		set(measurementType, TelecarePackage.eINSTANCE.getMeasurementType_Measurement(), measurement);
	}
	
	private void createEBE_reportingEvent(TelecareSystem telecareSystem) {
		Host host = createHost(telecareSystem);
		Gateway gateway = createGateway(telecareSystem);
		EventCompletedTrigger ect = createEventCompletedTrigger(gateway);
		ReportingEvent re = createReportingEvent(ect);
		
		set(re, TelecarePackage.eINSTANCE.getReportingEvent_Address(), host);
	}
	
	private EventCompletedTrigger createEventCompletedTrigger(Gateway gateway) {
		EventCompletedTrigger ect = (EventCompletedTrigger) createNode(gateway, TelecarePackage.eINSTANCE.getEventCompletedTrigger(), TelecarePackage.eINSTANCE.getGateway_Triggers());
		ect.setName("new EventCompletedTrigger");
		
		return ect;
	}
	
	private ReportingEvent createReportingEvent(Trigger trigger) {
		ReportingEvent reportingEvent = (ReportingEvent) createNode(trigger, TelecarePackage.eINSTANCE.getReportingEvent(), TelecarePackage.eINSTANCE.getTrigger_TriggeredEvents());
		reportingEvent.setName("new ReportingEvent");
		
		return reportingEvent;
	}

	private Measurement createMeasurement(Trigger trigger) {
		Measurement measurement = (Measurement) createNode(trigger, TelecarePackage.eINSTANCE.getMeasurement(), TelecarePackage.eINSTANCE.getTrigger_TriggeredEvents());
		measurement.setName("new Measurement");
		
		return measurement;
	}
	
	private MeasurementType createMeasurementType(Sensor sensor) {
		MeasurementType measurementType = (MeasurementType) createNode(sensor, TelecarePackage.eINSTANCE.getMeasurementType(), TelecarePackage.eINSTANCE.getSensor_MeasurementTypes());
		measurementType.setName("new MeasurementType");
		
		return measurementType;
	}
	
	private FixedIntervalTrigger createFixedIntervalTrigger(Gateway gateway) {
		FixedIntervalTrigger fit = (FixedIntervalTrigger) createNode(gateway, TelecarePackage.eINSTANCE.getFixedIntervalTrigger(), TelecarePackage.eINSTANCE.getGateway_Triggers());
		fit.setName("new FixedIntervalTrigger");
		
		return fit;
	}
	
	private Host createHost(TelecareSystem telecareSystem) {
		Host host = (Host) createNode(telecareSystem, TelecarePackage.eINSTANCE.getHost(), TelecarePackage.eINSTANCE.getTelecareSystem_Hosts());
		host.setName("new Host");
		
		return host;
	}
	
	private Sensor createSensor(TelecareSystem telecareSystem) {
		Sensor sensor = (Sensor) createNode(telecareSystem, TelecarePackage.eINSTANCE.getSensor(), TelecarePackage.eINSTANCE.getTelecareSystem_Sensors());
		sensor.setName("new Sensor");
		
		return sensor;
	}

	private Gateway createGateway(TelecareSystem telecareSystem) {
		Gateway gateway = (Gateway) createNode(telecareSystem, TelecarePackage.eINSTANCE.getGateway(), TelecarePackage.eINSTANCE.getTelecareSystem_Gateways());
		gateway.setName("new Gateway");
		
		return gateway;
	}
	
	private EObject createNode(EObject owner, EClass targetType, EReference reference) {
		EObject target = EcoreUtil.create(targetType);
		
		set(owner, reference, target);
		
		return target;
	}
	
    @SuppressWarnings("unchecked")
	public void set(EObject source, EReference reference, EObject target) {
    	reference = (EReference) source.eClass().getEStructuralFeature(reference.getName());
    	
		if (reference.isMany()) {
			((EList<EObject>) source.eGet(reference)).add(target);
		} else {
			source.eSet(reference, target);
		}
    }
}
