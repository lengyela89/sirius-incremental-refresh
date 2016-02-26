/**
 */
package org.eclipse.incquery.examples.cps.cyberPhysicalSystem;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Resource Requirement</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.eclipse.incquery.examples.cps.cyberPhysicalSystem.ResourceRequirement#getRequiredCpu <em>Required Cpu</em>}</li>
 *   <li>{@link org.eclipse.incquery.examples.cps.cyberPhysicalSystem.ResourceRequirement#getRequiredRam <em>Required Ram</em>}</li>
 *   <li>{@link org.eclipse.incquery.examples.cps.cyberPhysicalSystem.ResourceRequirement#getRequiredHdd <em>Required Hdd</em>}</li>
 *   <li>{@link org.eclipse.incquery.examples.cps.cyberPhysicalSystem.ResourceRequirement#getType <em>Type</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.incquery.examples.cps.cyberPhysicalSystem.CyberPhysicalSystemPackage#getResourceRequirement()
 * @model
 * @generated
 */
public interface ResourceRequirement extends EObject {
	/**
	 * Returns the value of the '<em><b>Required Cpu</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Required Cpu</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Required Cpu</em>' attribute.
	 * @see #setRequiredCpu(int)
	 * @see org.eclipse.incquery.examples.cps.cyberPhysicalSystem.CyberPhysicalSystemPackage#getResourceRequirement_RequiredCpu()
	 * @model
	 * @generated
	 */
	int getRequiredCpu();

	/**
	 * Sets the value of the '{@link org.eclipse.incquery.examples.cps.cyberPhysicalSystem.ResourceRequirement#getRequiredCpu <em>Required Cpu</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Required Cpu</em>' attribute.
	 * @see #getRequiredCpu()
	 * @generated
	 */
	void setRequiredCpu(int value);

	/**
	 * Returns the value of the '<em><b>Required Ram</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Required Ram</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Required Ram</em>' attribute.
	 * @see #setRequiredRam(int)
	 * @see org.eclipse.incquery.examples.cps.cyberPhysicalSystem.CyberPhysicalSystemPackage#getResourceRequirement_RequiredRam()
	 * @model
	 * @generated
	 */
	int getRequiredRam();

	/**
	 * Sets the value of the '{@link org.eclipse.incquery.examples.cps.cyberPhysicalSystem.ResourceRequirement#getRequiredRam <em>Required Ram</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Required Ram</em>' attribute.
	 * @see #getRequiredRam()
	 * @generated
	 */
	void setRequiredRam(int value);

	/**
	 * Returns the value of the '<em><b>Required Hdd</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Required Hdd</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Required Hdd</em>' attribute.
	 * @see #setRequiredHdd(int)
	 * @see org.eclipse.incquery.examples.cps.cyberPhysicalSystem.CyberPhysicalSystemPackage#getResourceRequirement_RequiredHdd()
	 * @model
	 * @generated
	 */
	int getRequiredHdd();

	/**
	 * Sets the value of the '{@link org.eclipse.incquery.examples.cps.cyberPhysicalSystem.ResourceRequirement#getRequiredHdd <em>Required Hdd</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Required Hdd</em>' attribute.
	 * @see #getRequiredHdd()
	 * @generated
	 */
	void setRequiredHdd(int value);

	/**
	 * Returns the value of the '<em><b>Type</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Type</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Type</em>' reference.
	 * @see #setType(HostType)
	 * @see org.eclipse.incquery.examples.cps.cyberPhysicalSystem.CyberPhysicalSystemPackage#getResourceRequirement_Type()
	 * @model required="true"
	 * @generated
	 */
	HostType getType();

	/**
	 * Sets the value of the '{@link org.eclipse.incquery.examples.cps.cyberPhysicalSystem.ResourceRequirement#getType <em>Type</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Type</em>' reference.
	 * @see #getType()
	 * @generated
	 */
	void setType(HostType value);

} // ResourceRequirement
