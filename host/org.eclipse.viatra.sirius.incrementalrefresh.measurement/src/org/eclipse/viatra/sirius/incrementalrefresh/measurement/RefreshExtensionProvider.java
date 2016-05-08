package org.eclipse.viatra.sirius.incrementalrefresh.measurement;

import org.eclipse.sirius.diagram.DDiagram;
import org.eclipse.sirius.diagram.DSemanticDiagram;
import org.eclipse.sirius.diagram.business.api.refresh.IRefreshExtension;
import org.eclipse.sirius.diagram.business.api.refresh.IRefreshExtensionProvider;
import org.eclipse.viatra.sirius.incrementalrefresh.measurement.util.MeasurementUtil;

public class RefreshExtensionProvider implements IRefreshExtensionProvider {
	private static int mId;

	public RefreshExtensionProvider() {}

	@Override
	public boolean provides(DDiagram viewPoint) {
		return true;
	}

	@Override
	public IRefreshExtension getRefreshExtension(DDiagram viewPoint) {
		return new IRefreshExtension() {


			@Override
			public void beforeRefresh(DDiagram dDiagram) {
				mId = MeasurementUtil.getInstance().measureExecutionTime_Start();
			}

			@Override
			public void postRefresh(DDiagram dDiagram) {
				MeasurementUtil.getInstance().measureExecutionTime_Stop(mId, MeasurementUtil.MEASUREMENT_REFRESH_OPERATION);
				
	            MeasurementUtil.getInstance().measureModelSize(MeasurementUtil.MEASUREMENT_SIRIUS_NOTATION_MODEL_SIZE, dDiagram);
	            MeasurementUtil.getInstance().measureModelSize(MeasurementUtil.MEASUREMENT_SOURCE_MODEL_SIZE, ((DSemanticDiagram)dDiagram).getTarget());
			}
			
		};
	}

}
