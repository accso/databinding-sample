package de.accso.rcp.sample.data;

import javax.persistence.Column;

/**
 * 
 * @author Jan Mischlich, Accso GmbH
 * 
 */
public class Person extends ModelObj {

	@Column(length=30)
	private String firstName;
	@Column(length=30)
	private String surName;
	private Integer age;
	private Address address;

	public Person() {
	}

	public Person(String firstName, String surName, Integer age) {
		super();
		this.firstName = firstName;
		this.surName = surName;
		this.age = age;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstname) {
		 firePropertyChange("firstName", this.firstName, this.firstName = firstname);
	}

	public String getSurName() {
		return surName;
	}


	public Integer getAge() {
		return age;
	}

	public void setSurName(String surName) {
		firePropertyChange("surName", this.surName, this.surName = surName);
	}

	public void setAge(Integer age) {
		firePropertyChange("age", this.age, this.age = age);
	}

	public void setAddress(Address address) {
		firePropertyChange("address", this.address, this.address = address);
	}

	public Address getAddress() {
		return address;
	}


	@Override
	public String toString() {
		return "Person [firstname=" + firstName + ", surname=" + surName
				+ ", age=" + age + "]";
	}

}
