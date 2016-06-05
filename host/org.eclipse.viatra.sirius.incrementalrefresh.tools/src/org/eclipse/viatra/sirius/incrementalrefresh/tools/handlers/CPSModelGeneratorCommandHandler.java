package org.eclipse.viatra.sirius.incrementalrefresh.tools.handlers;

import java.io.IOException;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.viatra.examples.cps.generator.dtos.CPSFragment;
import org.eclipse.viatra.examples.cps.generator.tests.constraints.DemoCPSConstraints;
import org.eclipse.viatra.examples.cps.generator.tests.constraints.LargeCPSConstraints;
import org.eclipse.viatra.examples.cps.generator.utils.CPSGeneratorBuilder;

public class CPSModelGeneratorCommandHandler extends AbstractHandler {

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		ResourceSet rset = new ResourceSetImpl();
		Resource res = null;
		
		CPSFragment cpsFragment = CPSGeneratorBuilder.buildAndGenerateModel(111111L, new DemoCPSConstraints());
		res = rset.createResource(URI.createPlatformResourceURI("/org.eclipse.viatra.examples.cps.sample/sample_small.cyberphysicalsystem", true));
		res.getContents().add(cpsFragment.modelRoot);
		try {
			res.save(null);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		cpsFragment = CPSGeneratorBuilder.buildAndGenerateModel(111111L, new LargeCPSConstraints());
		res = rset.createResource(URI.createPlatformResourceURI("/org.eclipse.viatra.examples.cps.sample/sample_large.cyberphysicalsystem", true));
		res.getContents().add(cpsFragment.modelRoot);
		try {
			res.save(null);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
		return null;
	}

}
