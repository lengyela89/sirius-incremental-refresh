package org.eclipse.viatra.sirius.incrementalrefresh.measurement.handlers;

import java.util.List;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.viatra.sirius.incrementalrefresh.measurement.helpers.MeasurementHelper;

import com.google.common.collect.Lists;

public class MeasureTCVStandardRefreshCommandHandler extends AbstractHandler {

	private static final String SESSION_RESOURCE = "/hu.bme.mit.inf.concerto.telecare.example.tdk.sample/representations.aird";
	private static final String VIEWPOINT_NAME = "vp_telecare_tdk_view";
	private static final String REPRESENTATION_DESCRIPTION_NAME = "dd_tdk_view_sr";
	
	private List<String> models = Lists.<String>newArrayList(
			"tc_1.telecare",
			
//			"tc_1.telecare",
//			"tc_10.telecare",
//			"tc_25.telecare",
//			"tc_50.telecare",
//			"tc_75.telecare",
//			"tc_100.telecare",
//			"tc_150.telecare",
//			"tc_200.telecare"
			
//			"tc_300.telecare",
//			"tc_400.telecare",
//			"tc_500.telecare",
//			"tc_750.telecare"
			
			"tc_1000.telecare"
			
//			"tc_1500.telecare",
//			"tc_2000.telecare",
//			"tc_3000.telecare",
//			"tc_4000.telecare",
//			"tc_5000.telecare",
//			"tc_7500.telecare",
//			"tc_10000.telecare"
			);
	
	
	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		new MeasurementHelper().measure(SESSION_RESOURCE, VIEWPOINT_NAME, REPRESENTATION_DESCRIPTION_NAME, "out_" + models.get(0));
		System.gc();
		System.gc();
		
		models.remove(0);
		
		System.out.println("Measurement:");
		for (String model : models) {
			new MeasurementHelper().measure(SESSION_RESOURCE, VIEWPOINT_NAME, REPRESENTATION_DESCRIPTION_NAME, "out_" + model);
			System.gc();
			System.gc();
		}
		
		return null;
	}
	

}
