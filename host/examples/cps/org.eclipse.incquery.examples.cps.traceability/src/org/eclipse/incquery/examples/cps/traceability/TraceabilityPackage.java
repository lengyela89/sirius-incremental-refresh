/**
 */
package org.eclipse.incquery.examples.cps.traceability;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

/**
 * <!-- begin-user-doc -->
 * The <b>Package</b> for the model.
 * It contains accessors for the meta objects to represent
 * <ul>
 *   <li>each class,</li>
 *   <li>each feature of each class,</li>
 *   <li>each operation of each class,</li>
 *   <li>each enum,</li>
 *   <li>and each data type</li>
 * </ul>
 * <!-- end-user-doc -->
 * @see org.eclipse.incquery.examples.cps.traceability.TraceabilityFactory
 * @model kind="package"
 * @generated
 */
public interface TraceabilityPackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "traceability";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "http://org.eclipse.incquery/model/cps-traceability";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "cpstraceability";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	TraceabilityPackage eINSTANCE = org.eclipse.incquery.examples.cps.traceability.impl.TraceabilityPackageImpl.init();

	/**
	 * The meta object id for the '{@link org.eclipse.incquery.examples.cps.traceability.impl.CPSToDeploymentImpl <em>CPS To Deployment</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.incquery.examples.cps.traceability.impl.CPSToDeploymentImpl
	 * @see org.eclipse.incquery.examples.cps.traceability.impl.TraceabilityPackageImpl#getCPSToDeployment()
	 * @generated
	 */
	int CPS_TO_DEPLOYMENT = 0;

	/**
	 * The feature id for the '<em><b>Cps</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CPS_TO_DEPLOYMENT__CPS = 0;

	/**
	 * The feature id for the '<em><b>Deployment</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CPS_TO_DEPLOYMENT__DEPLOYMENT = 1;

	/**
	 * The feature id for the '<em><b>Traces</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CPS_TO_DEPLOYMENT__TRACES = 2;

	/**
	 * The number of structural features of the '<em>CPS To Deployment</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CPS_TO_DEPLOYMENT_FEATURE_COUNT = 3;

	/**
	 * The number of operations of the '<em>CPS To Deployment</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CPS_TO_DEPLOYMENT_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link org.eclipse.incquery.examples.cps.traceability.impl.CPS2DeplyomentTraceImpl <em>CPS2 Deplyoment Trace</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.incquery.examples.cps.traceability.impl.CPS2DeplyomentTraceImpl
	 * @see org.eclipse.incquery.examples.cps.traceability.impl.TraceabilityPackageImpl#getCPS2DeplyomentTrace()
	 * @generated
	 */
	int CPS2_DEPLYOMENT_TRACE = 1;

	/**
	 * The feature id for the '<em><b>Cps Elements</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CPS2_DEPLYOMENT_TRACE__CPS_ELEMENTS = 0;

	/**
	 * The feature id for the '<em><b>Deployment Elements</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CPS2_DEPLYOMENT_TRACE__DEPLOYMENT_ELEMENTS = 1;

	/**
	 * The number of structural features of the '<em>CPS2 Deplyoment Trace</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CPS2_DEPLYOMENT_TRACE_FEATURE_COUNT = 2;

	/**
	 * The number of operations of the '<em>CPS2 Deplyoment Trace</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CPS2_DEPLYOMENT_TRACE_OPERATION_COUNT = 0;


	/**
	 * Returns the meta object for class '{@link org.eclipse.incquery.examples.cps.traceability.CPSToDeployment <em>CPS To Deployment</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>CPS To Deployment</em>'.
	 * @see org.eclipse.incquery.examples.cps.traceability.CPSToDeployment
	 * @generated
	 */
	EClass getCPSToDeployment();

	/**
	 * Returns the meta object for the reference '{@link org.eclipse.incquery.examples.cps.traceability.CPSToDeployment#getCps <em>Cps</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Cps</em>'.
	 * @see org.eclipse.incquery.examples.cps.traceability.CPSToDeployment#getCps()
	 * @see #getCPSToDeployment()
	 * @generated
	 */
	EReference getCPSToDeployment_Cps();

	/**
	 * Returns the meta object for the reference '{@link org.eclipse.incquery.examples.cps.traceability.CPSToDeployment#getDeployment <em>Deployment</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Deployment</em>'.
	 * @see org.eclipse.incquery.examples.cps.traceability.CPSToDeployment#getDeployment()
	 * @see #getCPSToDeployment()
	 * @generated
	 */
	EReference getCPSToDeployment_Deployment();

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.incquery.examples.cps.traceability.CPSToDeployment#getTraces <em>Traces</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Traces</em>'.
	 * @see org.eclipse.incquery.examples.cps.traceability.CPSToDeployment#getTraces()
	 * @see #getCPSToDeployment()
	 * @generated
	 */
	EReference getCPSToDeployment_Traces();

	/**
	 * Returns the meta object for class '{@link org.eclipse.incquery.examples.cps.traceability.CPS2DeplyomentTrace <em>CPS2 Deplyoment Trace</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>CPS2 Deplyoment Trace</em>'.
	 * @see org.eclipse.incquery.examples.cps.traceability.CPS2DeplyomentTrace
	 * @generated
	 */
	EClass getCPS2DeplyomentTrace();

	/**
	 * Returns the meta object for the reference list '{@link org.eclipse.incquery.examples.cps.traceability.CPS2DeplyomentTrace#getCpsElements <em>Cps Elements</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Cps Elements</em>'.
	 * @see org.eclipse.incquery.examples.cps.traceability.CPS2DeplyomentTrace#getCpsElements()
	 * @see #getCPS2DeplyomentTrace()
	 * @generated
	 */
	EReference getCPS2DeplyomentTrace_CpsElements();

	/**
	 * Returns the meta object for the reference list '{@link org.eclipse.incquery.examples.cps.traceability.CPS2DeplyomentTrace#getDeploymentElements <em>Deployment Elements</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Deployment Elements</em>'.
	 * @see org.eclipse.incquery.examples.cps.traceability.CPS2DeplyomentTrace#getDeploymentElements()
	 * @see #getCPS2DeplyomentTrace()
	 * @generated
	 */
	EReference getCPS2DeplyomentTrace_DeploymentElements();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	TraceabilityFactory getTraceabilityFactory();

	/**
	 * <!-- begin-user-doc -->
	 * Defines literals for the meta objects that represent
	 * <ul>
	 *   <li>each class,</li>
	 *   <li>each feature of each class,</li>
	 *   <li>each operation of each class,</li>
	 *   <li>each enum,</li>
	 *   <li>and each data type</li>
	 * </ul>
	 * <!-- end-user-doc -->
	 * @generated
	 */
	interface Literals {
		/**
		 * The meta object literal for the '{@link org.eclipse.incquery.examples.cps.traceability.impl.CPSToDeploymentImpl <em>CPS To Deployment</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.incquery.examples.cps.traceability.impl.CPSToDeploymentImpl
		 * @see org.eclipse.incquery.examples.cps.traceability.impl.TraceabilityPackageImpl#getCPSToDeployment()
		 * @generated
		 */
		EClass CPS_TO_DEPLOYMENT = eINSTANCE.getCPSToDeployment();

		/**
		 * The meta object literal for the '<em><b>Cps</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference CPS_TO_DEPLOYMENT__CPS = eINSTANCE.getCPSToDeployment_Cps();

		/**
		 * The meta object literal for the '<em><b>Deployment</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference CPS_TO_DEPLOYMENT__DEPLOYMENT = eINSTANCE.getCPSToDeployment_Deployment();

		/**
		 * The meta object literal for the '<em><b>Traces</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference CPS_TO_DEPLOYMENT__TRACES = eINSTANCE.getCPSToDeployment_Traces();

		/**
		 * The meta object literal for the '{@link org.eclipse.incquery.examples.cps.traceability.impl.CPS2DeplyomentTraceImpl <em>CPS2 Deplyoment Trace</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.incquery.examples.cps.traceability.impl.CPS2DeplyomentTraceImpl
		 * @see org.eclipse.incquery.examples.cps.traceability.impl.TraceabilityPackageImpl#getCPS2DeplyomentTrace()
		 * @generated
		 */
		EClass CPS2_DEPLYOMENT_TRACE = eINSTANCE.getCPS2DeplyomentTrace();

		/**
		 * The meta object literal for the '<em><b>Cps Elements</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference CPS2_DEPLYOMENT_TRACE__CPS_ELEMENTS = eINSTANCE.getCPS2DeplyomentTrace_CpsElements();

		/**
		 * The meta object literal for the '<em><b>Deployment Elements</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference CPS2_DEPLYOMENT_TRACE__DEPLOYMENT_ELEMENTS = eINSTANCE.getCPS2DeplyomentTrace_DeploymentElements();

	}

} //TraceabilityPackage
