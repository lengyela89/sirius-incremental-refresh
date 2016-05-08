package org.eclipse.viatra.sirius.incrementalrefresh.measurement.util;

import java.util.List;
import java.util.Map;

import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EObject;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

public class MeasurementUtil {
	
	public static final int MEASUREMENT_TITLE = 0;
	
	public static final int MEASUREMENT_SOURCE_MODEL = 1;
	
	public static final int MEASUREMENT_SOURCE_MODEL_SIZE = 2;
	
	public static final int MEASUREMENT_SIRIUS_NOTATION_MODEL_SIZE = 3;
	
	public static final int MEASUREMENT_GMF_NOTATION_MODEL_SIZE = 4;
	
	public static final int MEASUREMENT_TRACEABILITY_MODEL_SIZE = 5;
	
	public static final int MEASUREMENT_INCREMENTAL_REFRESH_INIT = 12;
	
	public static final int MEASUREMENT_REFRESH_OPERATION = 6;
	
	public static final int MEASUREMENT_VMM_INIT = 7;
	
	public static final int MEASUREMENT_VMM_FIRST_EXEC = 8;
	
	public static final int MEASUREMENT_CANONICAL_SYNC = 9;

	private static MeasurementUtil instance = null;

	private int measurementID = 0;
	
	private Map<Integer, Long> measurementIDToStartTS;
	
	private MeasurementData currentMeasurement;
	
	private List<MeasurementData> measurements;
	
	
	public static MeasurementUtil getInstance() {
		if (instance == null) {
			instance = new MeasurementUtil();
		}
		
		return instance;
	}
	
	private MeasurementUtil() {
		measurements = Lists.newArrayList();
		measurementIDToStartTS = Maps.newHashMap();
	}
	
	public void startMeasurement(String title, String model, String comment) {
		currentMeasurement = new MeasurementData(title, model, comment);
	}
	
	public void endMeasurement() {
		measurements.add(currentMeasurement);
		
		currentMeasurement = null;
	}
	
	public int measureExecutionTime_Start() {
		measurementID++;
		
		if (currentMeasurement == null) {
			currentMeasurement = new MeasurementData("-", "-", "-");
		}
		
		measurementIDToStartTS.put(measurementID, System.currentTimeMillis());
		return measurementID;
	}
	
	public void measureExecutionTime_Stop(int id, int key) {
		long execTime = (System.currentTimeMillis() - measurementIDToStartTS.get(id));
		
		switch (key) {
		case MEASUREMENT_REFRESH_OPERATION:
			currentMeasurement.setRefreshOperation(execTime);
			break;
			
		case MEASUREMENT_INCREMENTAL_REFRESH_INIT:
			currentMeasurement.setIncrementalRefreshInit(execTime);
			break;
		
		case MEASUREMENT_VMM_INIT:
			currentMeasurement.setVmmInit(execTime);
			break;
			
		case MEASUREMENT_VMM_FIRST_EXEC:
			currentMeasurement.setVmmFirstExec(execTime);
			break;
			
		case MEASUREMENT_CANONICAL_SYNC:
			currentMeasurement.setCanonicalSync(execTime);
			break;
		}
	}
	
	public void measureModelSize(int key, EObject root) {
		long modelSize = 0L;
		TreeIterator<EObject> iterator = root.eAllContents();
		while (iterator.hasNext()) {
			modelSize++;
			iterator.next();
		}

		switch (key) {
		case MEASUREMENT_SOURCE_MODEL_SIZE:
			currentMeasurement.setSourceModelSize(modelSize);
			break;
			
		case MEASUREMENT_SIRIUS_NOTATION_MODEL_SIZE:
			currentMeasurement.setSiriusNotationModelSize(modelSize);
			break;
			
		case MEASUREMENT_GMF_NOTATION_MODEL_SIZE:
			currentMeasurement.setGmfNotationModelSize(modelSize);
			break;
			
		case MEASUREMENT_TRACEABILITY_MODEL_SIZE:
			currentMeasurement.setTraceabilityModelSize(modelSize);
			break;
		}
	}
	
	public void printMeasurementResults() {
		for (MeasurementData md : measurements) {
			System.out.println(md);
		}
	}
	
	public void clear() {
		measurementID = 0;
		currentMeasurement = null;
		measurements = Lists.newArrayList();
		measurementIDToStartTS = Maps.newHashMap();
	}
}
