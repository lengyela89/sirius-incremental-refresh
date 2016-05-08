package org.eclipse.viatra.sirius.incrementalrefresh.measurement.helpers;

import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.transaction.RecordingCommand;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.sirius.business.api.dialect.DialectManager;
import org.eclipse.sirius.business.api.session.Session;
import org.eclipse.sirius.viewpoint.DRepresentation;
import org.eclipse.sirius.viewpoint.description.RepresentationDescription;

public class CreateRepresentationCommand extends RecordingCommand {

	private String representationName;
	private EObject semanticRoot;
	private RepresentationDescription descrition;
	private Session session;
	private DRepresentation representation;

	public CreateRepresentationCommand(TransactionalEditingDomain domain, String representationName, EObject semanticRoot,
			RepresentationDescription description, Session session) {
		super(domain);
		
		this.representationName = representationName;
		this.semanticRoot = semanticRoot;
		this.descrition = description;
		this.session = session;
	}

	@Override
	protected void doExecute() {
		representation = DialectManager.INSTANCE.createRepresentation(representationName, semanticRoot, descrition, session, new NullProgressMonitor());
	}

	public DRepresentation getRepresentation() {
		return representation;
	}
}
