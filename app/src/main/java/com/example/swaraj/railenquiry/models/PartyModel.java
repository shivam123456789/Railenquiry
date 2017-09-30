package com.example.swaraj.railenquiry.models;

/**
 * Created by swaraj on 10/8/17.
 */

public class PartyModel {
	String mobile_no;
	String state;
	String city;
	String court;
	String format;
	String party_name;
	String petitioner_names;
	String respondent;
	String order_date;
	String passed_by;

	public String getMobile_no() {
		return mobile_no;
	}

	public void setMobile_no(String mobile_no) {
		this.mobile_no = mobile_no;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCourt() {
		return court;
	}

	public void setCourt(String court) {
		this.court = court;
	}

	public String getFormat() {
		return format;
	}

	public void setFormat(String format) {
		this.format = format;
	}

	public String getParty_name() {
		return party_name;
	}

	public void setParty_name(String party_name) {
		this.party_name = party_name;
	}

	public String getPetitioner_names() {
		return petitioner_names;
	}

	public void setPetitioner_names(String petitioner_names) {
		this.petitioner_names = petitioner_names;
	}

	public String getRespondent() {
		return respondent;
	}

	public void setRespondent(String respondent) {
		this.respondent = respondent;
	}

	public String getOrder_date() {
		return order_date;
	}

	public void setOrder_date(String order_date) {
		this.order_date = order_date;
	}

	public String getPassed_by() {
		return passed_by;
	}

	public void setPassed_by(String passed_by) {
		this.passed_by = passed_by;
	}

	public String getPassed_in() {
		return passed_in;
	}

	public void setPassed_in(String passed_in) {
		this.passed_in = passed_in;
	}

	String passed_in;

}
