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
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.transaction.RecordingCommand;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.incquery.viewmodel.configuration.Configuration;
import org.eclipse.incquery.viewmodel.configuration.Scheduler;
import org.eclipse.incquery.viewmodel.core.TransformationInitializer;
import org.eclipse.incquery.viewmodel.core.ViewModelManager;
import org.eclipse.incquery.viewmodel.sirius.inmemoryresource.InMemoryResourceImpl;
import org.eclipse.sirius.business.api.componentization.ViewpointRegistry;
import org.eclipse.sirius.business.api.dialect.DialectManager;
import org.eclipse.sirius.business.api.session.SavingPolicy;
import org.eclipse.sirius.business.api.session.Session;
import org.eclipse.sirius.business.api.session.SessionManager;
import org.eclipse.sirius.tools.api.command.semantic.AddSemanticResourceCommand;
import org.eclipse.sirius.tools.api.command.semantic.RemoveSemanticResourceCommand;
import org.eclipse.sirius.viewpoint.DRepresentation;
import org.eclipse.sirius.viewpoint.description.RepresentationDescription;
import org.eclipse.sirius.viewpoint.description.Viewpoint;
import org.eclipse.viatra.query.runtime.exception.ViatraQueryException;
import org.eclipse.viatra.sirius.incrementalrefresh.measurement.util.MeasurementUtil;
import org.eclipse.viatra.transformation.runtime.emf.transformation.eventdriven.InconsistentEventSemanticsException;

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
import hu.bme.mit.inf.concerto.telecare.example.tdk.view.model.tdktelecareview.Server;
import hu.bme.mit.inf.concerto.telecare.example.tdk.view.model.tdktelecareview.TdktelecareviewFactory;
import hu.bme.mit.inf.concerto.telecare.example.tdk.view.model.tdktelecareview.ViewModel;

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
		//measureDiagramOpening(model, ted, semanticRoot, representationDescription, session);
		//measureModifications(model, ted, semanticRoot, representationDescription, session);
		//measureTrafoIncrementalRefreshOpening(model, ted, semanticRoot, representationDescription, session);
		//measureTrafoIncrementalRefreshModification(model, ted, semanticRoot, representationDescription, session);
		//easureTCVStandardRefreshOpening(model, ted, semanticRoot, representationDescription, session);
		measureTCVStandardRefreshModifications(model, ted, semanticRoot, representationDescription, session);
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
	
	private void measureTCVStandardRefreshOpening(String model, TransactionalEditingDomain ted, EObject semanticRoot, RepresentationDescription representationDescription, Session session) {
		System.out.println("Measure TelecareView Standard Refresh -> Opening");
		
		measureDiagramOpening(model, ted, semanticRoot, representationDescription, session);
	}
	
	private void measureTCVStandardRefreshModifications(String model, TransactionalEditingDomain ted, EObject semanticRoot, RepresentationDescription representationDescription, Session session) {
		System.out.println("Measure TelecareView Standard Refresh -> Modifications");
		
		CreateRepresentationCommand crc = new CreateRepresentationCommand(ted, "measurement_insert", semanticRoot, representationDescription, session);
		MeasurementUtil.getInstance().startMeasurement("Measure Diagram Opening", model, "Opening");
		ted.getCommandStack().execute(crc);
		MeasurementUtil.getInstance().endMeasurement();
		
		/* Measure modifications */
		measure_TCV_Server_Creation(model, ted, semanticRoot, crc.getRepresentation());
		/* ********************* */
		
		ted.getCommandStack().execute(new DeleteRepresentationCommand(ted, crc.getRepresentation(), session));
	}
	
	private void measureTrafoIncrementalRefreshOpening(String model, TransactionalEditingDomain ted, EObject semanticRoot, RepresentationDescription representationDescription, Session session) {
		System.out.println("Measure Transformation + Incremental Refresh -> Opening");

		/* Loading Configuration model */
		ResourceSet rset = new ResourceSetImpl();
		Resource configResource = rset.getResource(URI.createPlatformResourceURI("/hu.bme.mit.concerto.telecare.example.tdk.view.design/model/telecareview_transformation.configuration", true), true);
		final Configuration configurationModel = (Configuration) configResource.getContents().get(0);
		configurationModel.setScheduler(Scheduler.MANUAL);
		/* *************************** */
		
		for (int i = 0; i < MEASUREMENT_REPEAT; i++) {
			URI targetResourceURI = URI.createURI(InMemoryResourceImpl.URI_SCHEME + "_out_" + model);
			removeInMemoryResourceFromSession(session, targetResourceURI);
			
			ResourceSet modelsRSet = new ResourceSetImpl();
			configurationModel.setSourceModel(modelsRSet.getResource(URI.createPlatformResourceURI("/hu.bme.mit.inf.concerto.telecare.example.tdk.sample/model/" + model, true), true));
			configurationModel.setTargetModel(createInMemoryResource(session, targetResourceURI));

			try {
				MeasurementUtil.getInstance().startMeasurement("Measure Trafo + IR -> Opening_" + i, model, "Measure Trafo + IR -> Opening");
				final ViewModelManager vmm = new ViewModelManager(configurationModel, new TransformationInitializer() {
					
					@Override
					public void beforeInitialize(ViewModelManager viewModelManager) {
						// Nothing to do...
					}
					
					@Override
					public void afterInitialize(ViewModelManager viewModelManager) {
						viewModelManager.getExecutionSchema().startUnscheduledExecution();
					}
				});
				
				/* Initialize transformation */
				ted.getCommandStack().execute(new RecordingCommand(ted) {
					
					@Override
					protected void doExecute() {
						int id = MeasurementUtil.getInstance().measureExecutionTime_Start();
						try {
							vmm.initialize();
						} catch (InconsistentEventSemanticsException | ViatraQueryException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						MeasurementUtil.getInstance().measureExecutionTime_Stop(id, MeasurementUtil.MEASUREMENT_TRANSFORMATION_OPENING);
						MeasurementUtil.getInstance().measureModelSize(MeasurementUtil.MEASUREMENT_TRANSFORMATION_VIEW_MODEL_SIZE, configurationModel.getTargetModel().getContents().get(0));
					}
				});
				/* ************************* */

				
				/* Adding Semantic Resource to the session */
				addInMemoryResourceToSession(session, targetResourceURI);
				/* *************************************** */

				
				semanticRoot = configurationModel.getTargetModel().getContents().get(0);
				final CreateRepresentationCommand crc = new CreateRepresentationCommand(ted, "measure_" + i, semanticRoot, representationDescription, session);
				
				ted.getCommandStack().execute(crc);
				MeasurementUtil.getInstance().endMeasurement();
				
				//TODO
				ted.getCommandStack().execute(new RecordingCommand(ted) {
					
					@Override
					protected void doExecute() {
						MeasurementUtil.getInstance().startMeasurement("?", "?", "?");
						DialectManager.INSTANCE.refresh(crc.getRepresentation(), true, new NullProgressMonitor());
						MeasurementUtil.getInstance().endMeasurement();
					}
				});
				
				vmm.dispose();
				// TODO
				//DSemanticDiagramIncrementalRefreshFactory.disposeIncrementalRefresh(crc.getRepresentation());
				
				ted.getCommandStack().execute(new DeleteRepresentationCommand(ted, crc.getRepresentation(), session));
				
				
				/* Deleting Semantic Resource from Session */
				removeInMemoryResourceFromSession(session, targetResourceURI);
				/* *************************************** */
			} catch (ViatraQueryException e) {
				e.printStackTrace();
			}
			
			System.gc(); try { Thread.sleep(1000); } catch (InterruptedException e) {}
			System.gc(); try { Thread.sleep(1000); } catch (InterruptedException e) {}
			System.gc();
		}		
	}

	private void measureTrafoIncrementalRefreshModification(String model, TransactionalEditingDomain ted, EObject semanticRoot, RepresentationDescription representationDescription, Session session) {
		System.out.println("Measure Transformation + Incremental Refresh -> Modification");
		
		/* Loading Configuration model */
		ResourceSet rset = new ResourceSetImpl();
		Resource configResource = rset.getResource(URI.createPlatformResourceURI("/hu.bme.mit.concerto.telecare.example.tdk.view.design/model/telecareview_transformation.configuration", true), true);
		Configuration configurationModel = (Configuration) configResource.getContents().get(0);
		configurationModel.setScheduler(Scheduler.MANUAL);
		/* *************************** */
		
		URI targetResourceURI = URI.createURI(InMemoryResourceImpl.URI_SCHEME + "_out_" + model);
		removeInMemoryResourceFromSession(session, targetResourceURI);
		
		ResourceSet modelsRSet = new ResourceSetImpl();
		configurationModel.setSourceModel(modelsRSet.getResource(URI.createPlatformResourceURI("/hu.bme.mit.inf.concerto.telecare.example.tdk.sample/model/" + model, true), true));
		configurationModel.setTargetModel(createInMemoryResource(session, targetResourceURI));

		try {
			final ViewModelManager vmm = new ViewModelManager(configurationModel, new TransformationInitializer() {
				
				@Override
				public void beforeInitialize(ViewModelManager viewModelManager) {
					// Nothing to do...
				}
				
				@Override
				public void afterInitialize(ViewModelManager viewModelManager) {
					viewModelManager.getExecutionSchema().startUnscheduledExecution();
				}
			});
			
			/* Initialize transformation */
			ted.getCommandStack().execute(new RecordingCommand(ted) {
				
				@Override
				protected void doExecute() {
					try {
						vmm.initialize();
					} catch (InconsistentEventSemanticsException | ViatraQueryException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			});
			/* ************************* */

			
			/* Adding Semantic Resource to the session */
			addInMemoryResourceToSession(session, targetResourceURI);
			/* *************************************** */

			
			semanticRoot = configurationModel.getTargetModel().getContents().get(0);
			CreateRepresentationCommand crc = new CreateRepresentationCommand(ted, "measure_modification", semanticRoot, representationDescription, session);
			ted.getCommandStack().execute(crc);

			
			measure_TC_TCV_Server_Creation(model, ted, configurationModel.getSourceModel().getContents().get(0), vmm, crc.getRepresentation());
			
			
			vmm.dispose();
			// TODO
			//DSemanticDiagramIncrementalRefreshFactory.disposeIncrementalRefresh(crc.getRepresentation());
			
			ted.getCommandStack().execute(new DeleteRepresentationCommand(ted, crc.getRepresentation(), session));
			
			
			/* Deleting Semantic Resource from Session */
			removeInMemoryResourceFromSession(session, targetResourceURI);
			/* *************************************** */
		} catch (ViatraQueryException e) {
			e.printStackTrace();
		}
		
		System.gc(); try { Thread.sleep(1000); } catch (InterruptedException e) {}
		System.gc(); try { Thread.sleep(1000); } catch (InterruptedException e) {}
		System.gc();
	}	
	
	
	private void addInMemoryResourceToSession(Session session, URI targetResourceURI) {
		AddSemanticResourceCommand addCommandToSession = new AddSemanticResourceCommand(
				session, targetResourceURI, new NullProgressMonitor());
		session.getTransactionalEditingDomain().getCommandStack()
				.execute(addCommandToSession);
	}
	
	private Resource createInMemoryResource(Session session, URI targetResourceURI) {
		ResourceSet rset = session.getTransactionalEditingDomain().getResourceSet();
		if (rset != null) {
			return rset.createResource(targetResourceURI);
		}
		
		return null;
	}
	
	private void removeInMemoryResourceFromSession(Session session, URI targetResourceURI) {
		Resource resourceToRemove = null;
		for (Resource resource : session.getTransactionalEditingDomain().getResourceSet().getResources()) {
			if (resource.getURI().equals(targetResourceURI)) {
				resourceToRemove = resource;
				
				break;
			}
		}
		
		if (resourceToRemove != null) {
			RemoveSemanticResourceCommand removeSemanticResourceCommand = new RemoveSemanticResourceCommand(
					session, resourceToRemove, new NullProgressMonitor(), true);
			session.getTransactionalEditingDomain().getCommandStack()
					.execute(removeSemanticResourceCommand);
		}
	}
	
	private void measure_TC_TCV_Server_Creation(String model, TransactionalEditingDomain ted, final EObject semanticRoot, final ViewModelManager vmm, final DRepresentation representation) {
		System.out.println("Measure TC -> TCV Server Creation");
		
		for (int i = 0; i < MEASUREMENT_REPEAT; i++) {
			MeasurementUtil.getInstance().startMeasurement("Creating TC -> TCV Server _ " + i, model, "Creating TC -> TCV Server");

			ted.getCommandStack().execute(new RecordingCommand(ted) {
				
				@Override
				protected void doExecute() {
					int id = MeasurementUtil.getInstance().measureExecutionTime_Start();
					create_tc_elements_for_tcv_server((TelecareSystem) semanticRoot);
					
					vmm.getExecutionSchema().startUnscheduledExecution();
					MeasurementUtil.getInstance().measureExecutionTime_Stop(id, MeasurementUtil.MEASUREMENT_TRANSFORMATION_MODIFICATION);
					MeasurementUtil.getInstance().measureModelSize(MeasurementUtil.MEASUREMENT_TRANSFORMATION_VIEW_MODEL_SIZE, vmm.getConfigurationModel().getTargetModel().getContents().get(0));
					
					DialectManager.INSTANCE.refresh(representation, true, new NullProgressMonitor());
				}
				
			});

			MeasurementUtil.getInstance().endMeasurement();
		}
		
	}
	
	private void measure_TCV_Server_Creation(String model, TransactionalEditingDomain ted, final EObject semanticRoot, final DRepresentation representation) {
		System.out.println("Measure TCV Server Creation");
		
		for (int i = 0; i < MEASUREMENT_REPEAT; i++) {
			MeasurementUtil.getInstance().startMeasurement("Creating TCV Server _ " + i, model, "Creating TCV Server");

			ted.getCommandStack().execute(new RecordingCommand(ted) {
				
				@Override
				protected void doExecute() {
					int id = MeasurementUtil.getInstance().measureExecutionTime_Start();
					createTCVServer((ViewModel) semanticRoot);
					
					DialectManager.INSTANCE.refresh(representation, true, new NullProgressMonitor());
				}
				
			});

			MeasurementUtil.getInstance().endMeasurement();
		}
	}
	
	private Server createTCVServer(ViewModel viewModel) {
		Server server = TdktelecareviewFactory.eINSTANCE.createServer();
		viewModel.getServers().add(server);
		server.setName("newServer!");
		
		return server;
	}
	
	private Host create_tc_elements_for_tcv_server(TelecareSystem telecareSystem) {
		Host host = createHost(telecareSystem);
		Gateway gateway = createGateway(telecareSystem);
		EventCompletedTrigger ect = createEventCompletedTrigger(gateway);
		ReportingEvent re = createReportingEvent(ect);
		
		set(re, TelecarePackage.eINSTANCE.getReportingEvent_Address(), host);
		
		return host;
	}
	
	private Sensor create_tc_elements_for_tcv_sensor(TelecareSystem telecareSystem) {
		Sensor sensor = createSensor(telecareSystem);
		Gateway gateway = createGateway(telecareSystem);
		FixedIntervalTrigger fit = createFixedIntervalTrigger(gateway);
		EventCompletedTrigger ect = createEventCompletedTrigger(gateway);
		
		ReportingEvent re = createReportingEvent(ect);
		Host host = createHost(telecareSystem);
		set(re, TelecarePackage.eINSTANCE.getReportingEvent_Address(), host);
		
		Measurement measurement = createMeasurement(fit);
		MeasurementType measurementType = createMeasurementType(sensor);
		set(ect, TelecarePackage.eINSTANCE.getEventCompletedTrigger_TriggeredBy(), measurement);
		set(measurement, TelecarePackage.eINSTANCE.getMeasurement_MeasurementType(), measurementType);
		set(measurementType, TelecarePackage.eINSTANCE.getMeasurementType_Measurement(), measurement);
		
		return sensor;
	}
	
	private void create_tc_elements_for_tcv_ref_sensor_connectedTo(TelecareSystem telecareSystem) {
		create_tc_elements_for_tcv_sensor(telecareSystem);
	}
	
	private void measureDiagramOpening(String model, TransactionalEditingDomain ted, EObject semanticRoot, RepresentationDescription representationDescription, Session session) {
		System.out.println("Measure Diagram Opening (" + MEASUREMENT_REPEAT + ")");
		
		for (int i = 0; i < MEASUREMENT_REPEAT; i++) {
			System.out.println("Opening (" + i + ")");
			CreateRepresentationCommand crc = new CreateRepresentationCommand(ted, "measure_" + i, semanticRoot, representationDescription, session);
			
			MeasurementUtil.getInstance().startMeasurement("Opening_" + i, model, "Opening");
			ted.getCommandStack().execute(crc);
			MeasurementUtil.getInstance().endMeasurement();
			
			// TODO
			//DSemanticDiagramIncrementalRefreshFactory.disposeIncrementalRefresh(crc.getRepresentation());
			
			ted.getCommandStack().execute(new DeleteRepresentationCommand(ted, crc.getRepresentation(), session));
			
			System.gc(); try { Thread.sleep(1000); } catch (InterruptedException e) {}
			System.gc(); try { Thread.sleep(1000); } catch (InterruptedException e) {}
			System.gc();
		}
	}
	
	private void measureModifications(String model, TransactionalEditingDomain ted, EObject semanticRoot, RepresentationDescription representationDescription, Session session) {
		CreateRepresentationCommand crc = new CreateRepresentationCommand(ted, "measurement_insert", semanticRoot, representationDescription, session);
		MeasurementUtil.getInstance().startMeasurement("Measure Diagram Opening", model, "Opening");
		ted.getCommandStack().execute(crc);
		MeasurementUtil.getInstance().endMeasurement();
		
		/* Measure modifications */
		measureNodeCreation(model, ted, semanticRoot, crc.getRepresentation());
		measureRBECreation_Sensor_ConnectedTo(model, ted, semanticRoot, crc.getRepresentation());
		measureEBECreation_Measurement(model, ted, semanticRoot, crc.getRepresentation());
		measureEBECreation_ReportingEvent(model, ted, semanticRoot, crc.getRepresentation());
		/* ********************* */
		
		ted.getCommandStack().execute(new DeleteRepresentationCommand(ted, crc.getRepresentation(), session));
	}
	
	private void measureNodeCreation(String model, TransactionalEditingDomain ted, final EObject semanticRoot, final DRepresentation representation) {
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
	
	private void measureRBECreation_Sensor_ConnectedTo(String model, TransactionalEditingDomain ted, final EObject semanticRoot, final DRepresentation representation) {
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
	
	private void measureEBECreation_Measurement(String model, TransactionalEditingDomain ted, final EObject semanticRoot, final DRepresentation representation) {
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
	
	private void measureEBECreation_ReportingEvent(String model, TransactionalEditingDomain ted, final EObject semanticRoot, final DRepresentation representation) {
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
