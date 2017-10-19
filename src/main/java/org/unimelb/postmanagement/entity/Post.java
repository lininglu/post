package org.unimelb.postmanagement.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.persistence.Version;

@Entity
@Table(name = "Posts", //
		uniqueConstraints = { @UniqueConstraint(columnNames = "Post_Num") })
public class Post implements Serializable {

	private static final long serialVersionUID = -2576670215015463100L;

	private String id;
	private Date postDate;
	private int postNum;
	private Account account;
	private String customerName;
	private String customerAddress;
	private String customerEmail;
	private String customerPhone;
	private String comment;

	private Integer version;

	@Version
    @Column(name="OPTLOCK")
	public Integer getVersion() {
		return version;
	}
	

	public void setVersion(Integer version) {
		this.version = version;
	}

	@Id
	@Column(name = "ID", length = 50)
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Column(name = "Post_Date", nullable = false)
	public Date getPostDate() {
		return postDate;
	}

	public void setPostDate(Date postDate) {
		this.postDate = postDate;
	}

	@Column(name = "Post_Num", nullable = false)
	public int getPostNum() {
		return postNum;
	}

	public void setPostNum(int postNum) {
		this.postNum = postNum;
	}

	@ManyToOne
	@JoinColumn(name = "User_Name", nullable = false, foreignKey = @ForeignKey(name = "POST_ACC_FK"))
	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

	@Column(name = "Customer_Name", length = 255, nullable = false)
	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	@Column(name = "Customer_Address", length = 255, nullable = false)
	public String getCustomerAddress() {
		return customerAddress;
	}

	public void setCustomerAddress(String customerAddress) {
		this.customerAddress = customerAddress;
	}

	@Column(name = "Customer_Email", length = 128, nullable = false)
	public String getCustomerEmail() {
		return customerEmail;
	}

	public void setCustomerEmail(String customerEmail) {
		this.customerEmail = customerEmail;
	}

	@Column(name = "Customer_Phone", length = 128, nullable = false)
	public String getCustomerPhone() {
		return customerPhone;
	}

	public void setCustomerPhone(String customerPhone) {
		this.customerPhone = customerPhone;
	}

	@Column(name = "Comment", length = 255)
	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

}
