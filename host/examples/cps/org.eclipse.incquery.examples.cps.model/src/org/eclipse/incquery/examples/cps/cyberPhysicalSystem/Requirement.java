/**
 */
package org.eclipse.incquery.examples.cps.cyberPhysicalSystem;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Requirement</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.eclipse.incquery.examples.cps.cyberPhysicalSystem.Requirement#getCount <em>Count</em>}</li>
 *   <li>{@link org.eclipse.incquery.examples.cps.cyberPhysicalSystem.Requirement#getRequest <em>Request</em>}</li>
 *   <li>{@link org.eclipse.incquery.examples.cps.cyberPhysicalSystem.Requirement#getType <em>Type</em>}</li>
 *   <li>{@link org.eclipse.incquery.examples.cps.cyberPhysicalSystem.Requirement#getApplications <em>Applications</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.incquery.examples.cps.cyberPhysicalSystem.CyberPhysicalSystemPackage#getRequirement()
 * @model
 * @generated
 */
public interface Requirement extends EObject {
	/**
	 * Returns the value of the '<em><b>Count</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Count</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Count</em>' attribute.
	 * @see #setCount(int)
	 * @see org.eclipse.incquery.examples.cps.cyberPhysicalSystem.CyberPhysicalSystemPackage#getRequirement_Count()
	 * @model
	 * @generated
	 */
	int getCount();

	/**
	 * Sets the value of the '{@link org.eclipse.incquery.examples.cps.cyberPhysicalSystem.Requirement#getCount <em>Count</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Count</em>' attribute.
	 * @see #getCount()
	 * @generated
	 */
	void setCount(int value);

	/**
	 * Returns the value of the '<em><b>Request</b></em>' container reference.
	 * It is bidirectional and its opposite is '{@link org.eclipse.incquery.examples.cps.cyberPhysicalSystem.Request#getRequirements <em>Requirements</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Request</em>' container reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Request</em>' container reference.
	 * @see #setRequest(Request)
	 * @see org.eclipse.incquery.examples.cps.cyberPhysicalSystem.CyberPhysicalSystemPackage#getRequirement_Request()
	 * @see org.eclipse.incquery.examples.cps.cyberPhysicalSystem.Request#getRequirements
	 * @model opposite="requirements" required="true" transient="false"
	 * @generated
	 */
	Request getRequest();

	/**
	 * Sets the value of the '{@link org.eclipse.incquery.examples.cps.cyberPhysicalSystem.Requirement#getRequest <em>Request</em>}' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Request</em>' container reference.
	 * @see #getRequest()
	 * @generated
	 */
	void setRequest(Request value);

	/**
	 * Returns the value of the '<em><b>Type</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Type</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Type</em>' reference.
	 * @see #setType(ApplicationType)
	 * @see org.eclipse.incquery.examples.cps.cyberPhysicalSystem.CyberPhysicalSystemPackage#getRequirement_Type()
	 * @model required="true"
	 * @generated
	 */
	ApplicationType getType();

	/**
	 * Sets the value of the '{@link org.eclipse.incquery.examples.cps.cyberPhysicalSystem.Requirement#getType <em>Type</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Type</em>' reference.
	 * @see #getType()
	 * @generated
	 */
	void setType(ApplicationType value);

	/**
	 * Returns the value of the '<em><b>Applications</b></em>' reference list.
	 * The list contents are of type {@link org.eclipse.incquery.examples.cps.cyberPhysicalSystem.ApplicationInstance}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Applications</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Applications</em>' reference list.
	 * @see org.eclipse.incquery.examples.cps.cyberPhysicalSystem.CyberPhysicalSystemPackage#getRequirement_Applications()
	 * @model
	 * @generated
	 */
	EList<ApplicationInstance> getApplications();

} // Requirement
