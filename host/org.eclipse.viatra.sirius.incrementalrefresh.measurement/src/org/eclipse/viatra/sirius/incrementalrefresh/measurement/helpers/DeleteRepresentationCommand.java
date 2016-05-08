package org.eclipse.viatra.sirius.incrementalrefresh.measurement.helpers;

import org.eclipse.emf.transaction.RecordingCommand;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.sirius.business.api.dialect.DialectManager;
import org.eclipse.sirius.business.api.session.Session;
import org.eclipse.sirius.viewpoint.DRepresentation;

public class DeleteRepresentationCommand extends RecordingCommand {

	private DRepresentation representation;
	private Session session;

	public DeleteRepresentationCommand(TransactionalEditingDomain domain, DRepresentation representation, Session session) {
		super(domain);
		
		this.representation = representation;
		this.session = session;
	}

	@Override
	protected void doExecute() {
		DialectManager.INSTANCE.deleteRepresentation(representation, session);
	}

}
