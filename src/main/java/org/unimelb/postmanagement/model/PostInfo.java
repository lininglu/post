package org.unimelb.postmanagement.model;

import java.util.Date;

import org.unimelb.postmanagement.entity.Account;

public class PostInfo {

	private String id;
	private Date postDate;
	private int postNum;
	private Account account;

	private String customerName;
	private String customerAddress;
	private String customerEmail;
	private String customerPhone;
	private String comment;

	public PostInfo() {

	}

	// Using for Hibernate Query.
	public PostInfo(String id, Date postDate, int postNum, Account account, String customerName, String customerAddress,
			String customerEmail, String customerPhone, String comment) {
		this.id = id;
		this.postDate = postDate;
		this.postNum = postNum;
		this.account = account;

		this.customerName = customerName;
		this.customerAddress = customerAddress;
		this.customerEmail = customerEmail;
		this.customerPhone = customerPhone;
		this.comment = comment;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Date getPostDate() {
		return postDate;
	}

	public void setPostDate(Date postDate) {
		this.postDate = postDate;
	}

	public int getPostNum() {
		return postNum;
	}

	public void setPostNum(int postNum) {
		this.postNum = postNum;
	}

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getCustomerAddress() {
		return customerAddress;
	}

	public void setCustomerAddress(String customerAddress) {
		this.customerAddress = customerAddress;
	}

	public String getCustomerEmail() {
		return customerEmail;
	}

	public void setCustomerEmail(String customerEmail) {
		this.customerEmail = customerEmail;
	}

	public String getCustomerPhone() {
		return customerPhone;
	}

	public void setCustomerPhone(String customerPhone) {
		this.customerPhone = customerPhone;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}
}
