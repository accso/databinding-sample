package de.accso.rcp.sample.data;

import javax.persistence.Column;

/**
 * 
 * @author Jan Mischlich, Accso GmbH
 * 
 */
public class Address extends ModelObj {

	
	@Column(length=50)
	private String street;
	@Column(length=5)
	private String postcode;
	@Column(length=50)
	private String location;
	
	public String getPostcode() {
		return postcode;
	}

	public void setPostcode(String postcode) {
		firePropertyChange("postcode", this.postcode, this.postcode = postcode);
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		firePropertyChange("street", this.street, this.street = street);
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		firePropertyChange("location", this.location, this.location = location);
	}

	@Override
	public String toString() {
		return "Address [postcode=" + postcode + ", street=" + street
				+ ", location=" + location + "]";
	}
}
