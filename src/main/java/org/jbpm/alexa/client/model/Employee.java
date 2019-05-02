package org.jbpm.alexa.client.model;

import java.util.Date;

/**
 * Employee
 */
public class Employee {

    private String firstName;

    private String lastName;

    private String personalId;

    private Date birthDate;

    private Address address;

    /**
     * @return the firstName
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * @param firstName the firstName to set
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * @return the lastName
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * @param lastName the lastName to set
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * @return the personalId
     */
    public String getPersonalId() {
        return personalId;
    }

    /**
     * @param personalId the personalId to set
     */
    public void setPersonalId(String personalId) {
        this.personalId = personalId;
    }

    /**
     * @return the birthDate
     */
    public Date getBirthDate() {
        return birthDate;
    }

    /**
     * @param birthDate the birthDate to set
     */
    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    /**
     * @return the address
     */
    public Address getAddress() {
        return address;
    }

    /**
     * @param address the address to set
     */
    public void setAddress(Address address) {
        this.address = address;
    }
   
}