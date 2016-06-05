package org.eclipse.viatra.examples.cps.viewpoint.services;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.sirius.diagram.AbstractDNode;
import org.eclipse.sirius.diagram.DEdge;
import org.eclipse.viatra.examples.cps.cyberPhysicalSystem.ApplicationInstance;
import org.eclipse.viatra.examples.cps.cyberPhysicalSystem.ApplicationType;
import org.eclipse.viatra.examples.cps.cyberPhysicalSystem.CyberPhysicalSystem;
import org.eclipse.viatra.examples.cps.cyberPhysicalSystem.CyberPhysicalSystemFactory;
import org.eclipse.viatra.examples.cps.cyberPhysicalSystem.HostInstance;
import org.eclipse.viatra.examples.cps.cyberPhysicalSystem.HostType;
import org.eclipse.viatra.examples.cps.cyberPhysicalSystem.State;
import org.eclipse.viatra.examples.cps.cyberPhysicalSystem.StateMachine;
import org.eclipse.viatra.examples.cps.cyberPhysicalSystem.Transition;

public class CPSDemoToolsService {

	public EObject createApplicationType(EObject container) {
		((CyberPhysicalSystem) container).getAppTypes().add(createApplicationType());
		
		return null;
	}
	
	public EObject createHostType(EObject container) {
		((CyberPhysicalSystem) container).getHostTypes().add(createHostType());
		
		return null;
	}
	
	public EObject createHasAllocationOnEdge(EObject source, EObject target) {
		ApplicationInstance ai = createApplicationInstance();
		HostInstance hi = createHostInstance();
		
		((ApplicationType) source).getInstances().add(ai);
		((HostType) target).getInstances().add(hi);
		ai.setAllocatedTo(hi);
		
		return null;
	}
	
	public EObject createState(EObject container) {
		((StateMachine) container).getStates().add(createState());
		
		return null;
	}
	
	public EObject createTransition(EObject container, EObject target) {
		Transition t = createTransition();
		t.setTargetState((State) target);
		
		((State) container).getOutgoingTransitions().add(t);
		
		return null;
	}
	
	public EObject removeApplicationType(EObject at) {
		EcoreUtil.delete(at);
		
		return null;
	}
	
	public EObject removeHostType(EObject ht) {
		EcoreUtil.delete(ht);
		
		return null;
	}
	
	public EObject removeHasAllocationOnEdge(EObject view) {
		DEdge edge = (DEdge) view;
		ApplicationType at = (ApplicationType) ((AbstractDNode) edge.getSourceNode()).getTarget();
		HostType ht = (HostType) ((AbstractDNode) edge.getTargetNode()).getTarget();
		
		for (ApplicationInstance ai : at.getInstances()) {
			if (ht.getInstances().contains(ai.getAllocatedTo())) {
				ai.setAllocatedTo(null);
			}
		}
		
		return null;
	}
	
	public EObject removeState(EObject s) {
		EcoreUtil.delete(s);
		
		return null;
	}
	
	public EObject removeTransition(EObject t) {
		EcoreUtil.delete(t);
		
		return null;
	}
	
	public ApplicationType createApplicationType() {
		ApplicationType at = CyberPhysicalSystemFactory.eINSTANCE.createApplicationType();
		StateMachine sm = createStateMachine();
		at.setBehavior(sm);
		at.setIdentifier("newApplicationType");
		
		return at;
	}
	
	public ApplicationInstance createApplicationInstance() {
		ApplicationInstance ai = CyberPhysicalSystemFactory.eINSTANCE.createApplicationInstance();
		ai.setIdentifier("newApplicationInstance");
		
		return ai;
	}
	
	public HostType createHostType() {
		HostType ht = CyberPhysicalSystemFactory.eINSTANCE.createHostType();
		ht.setIdentifier("newHostType");
		
		return ht;
	}
	
	public HostInstance createHostInstance() {
		HostInstance hi = CyberPhysicalSystemFactory.eINSTANCE.createHostInstance();
		hi.setIdentifier("newHostInstance");
		
		return hi;
	}
	
	public StateMachine createStateMachine() {
		StateMachine sm = CyberPhysicalSystemFactory.eINSTANCE.createStateMachine();
		sm.setIdentifier("newStateMachine");
		
		return sm;
	}
	
	public State createState() {
		State state = CyberPhysicalSystemFactory.eINSTANCE.createState();
		state.setIdentifier("newState");
		
		return state;
	}
	
	public Transition createTransition() {
		Transition t = CyberPhysicalSystemFactory.eINSTANCE.createTransition();
		t.setIdentifier("newTransition");
		t.setAction("newAction");
		
		return t;
	}
}
