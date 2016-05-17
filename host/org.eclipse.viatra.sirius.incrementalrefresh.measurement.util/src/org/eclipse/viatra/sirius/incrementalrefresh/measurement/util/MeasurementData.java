package org.eclipse.viatra.sirius.incrementalrefresh.measurement.util;

public class MeasurementData {

	private String title;
	private String model;
	private String comment;
	private Long sourceModelSize;
	private Long siriusNotationModelSize;
	private Long gmfNotationModelSize;
	private Long traceabilityModelSize;
	private Long refreshOperation;
	private Long incrementalRefreshInit;
	private Long vmmInit;
	private Long vmmFirstExec;
	private Long canonicalSync;
	private Long transformationViewModelSize;
	private Long transformationOpening;
	private Long transformationModification;
	
	public MeasurementData(String title, String model, String comment) {
		this.title = title;
		this.model = model;
		this.comment = comment;
	}

	public Integer getGroupId() {
		return (model + "" + comment).hashCode();
	}

	public String getTitle() {
		return title;
	}


	public void setTitle(String title) {
		this.title = title;
	}


	public String getModel() {
		return model;
	}


	public void setModel(String model) {
		this.model = model;
	}


	public Long getSourceModelSize() {
		return sourceModelSize;
	}


	public void setSourceModelSize(Long sourceModelSize) {
		if (this.sourceModelSize == null) {
			this.sourceModelSize = sourceModelSize;
		}
	}


	public Long getSiriusNotationModelSize() {
		return siriusNotationModelSize;
	}


	public void setSiriusNotationModelSize(Long siriusNotationModelSize) {
		if (this.siriusNotationModelSize == null) {
			this.siriusNotationModelSize = siriusNotationModelSize;
		}
	}


	public Long getGmfNotationModelSize() {
		return gmfNotationModelSize;
	}


	public void setGmfNotationModelSize(Long gmfNotationModelSize) {
		if (this.gmfNotationModelSize == null) {
			this.gmfNotationModelSize = gmfNotationModelSize;
		}
	}


	public Long getTraceabilityModelSize() {
		return traceabilityModelSize;
	}


	public void setTraceabilityModelSize(Long traceabilityModelSize) {
		if (this.traceabilityModelSize == null) {
			this.traceabilityModelSize = traceabilityModelSize;
		}
	}


	public Long getRefreshOperation() {
		return refreshOperation;
	}


	public void setRefreshOperation(Long refreshOperation) {
		if (this.refreshOperation == null) {
			this.refreshOperation = refreshOperation;
		}
	}


	public Long getVmmInit() {
		return vmmInit;
	}


	public void setVmmInit(Long vmmInit) {
		if (this.vmmInit == null) {
			this.vmmInit = vmmInit;
		}
	}


	public Long getVmmFirstExec() {
		return vmmFirstExec;
	}


	public void setVmmFirstExec(Long vmmFirstExec) {
		if (this.vmmFirstExec == null) {
			this.vmmFirstExec = vmmFirstExec;
		}
	}


	public Long getCanonicalSync() {
		return canonicalSync;
	}


	public void setCanonicalSync(Long canonicalSync) {
		if (this.canonicalSync == null) {
			this.canonicalSync = canonicalSync;
		}
	}

	public String getComment() {
		return comment;
	}


	public void setComment(String comment) {
		this.comment = comment;
	}

	
	public Long getIncrementalRefreshInit() {
		return incrementalRefreshInit;
	}


	public void setIncrementalRefreshInit(Long incrementalRefreshInit) {
		this.incrementalRefreshInit = incrementalRefreshInit;
	}

	

	public Long getTransformationViewModelSize() {
		return transformationViewModelSize;
	}


	public void setTransformationViewModelSize(Long transformationViewModelSize) {
		this.transformationViewModelSize = transformationViewModelSize;
	}


	public Long getTransformationOpening() {
		return transformationOpening;
	}


	public void setTransformationOpening(Long transformationOpening) {
		this.transformationOpening = transformationOpening;
	}


	public Long getTransformationModification() {
		return transformationModification;
	}


	public void setTransformationModification(Long transformationModification) {
		this.transformationModification = transformationModification;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(model).append(";")
		.append(sourceModelSize).append(";")
		.append(siriusNotationModelSize).append(";")
		.append(transformationViewModelSize).append(";")
//		.append(gmfNotationModelSize).append(";")
//		.append(traceabilityModelSize).append(";")
		.append(transformationOpening).append(";")
		.append(transformationModification).append(";")
		.append(refreshOperation).append(";")
		.append(incrementalRefreshInit).append(";")
		.append(vmmInit).append(";")
		.append(vmmFirstExec).append(";")
		.append(canonicalSync).append(";")
		.append(comment);
		
		return sb.toString();
	}
}
