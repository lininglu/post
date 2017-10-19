package org.unimelb.postmanagement.model;
 
public class NewPost {
 
    private int postNum;
 
    private CustomerInfo customerInfo;
 
    public NewPost() {
 
    }
 
    public int getPostNum() {
        return postNum;
    }
 
    public void setPostNum(int postNum) {
        this.postNum = postNum;
    }
 
    public CustomerInfo getCustomerInfo() {
        return customerInfo;
    }
 
    public void setCustomerInfo(CustomerInfo customerInfo) {
        this.customerInfo = customerInfo;
    }
    
    public boolean isValidCustomer() {
        return this.customerInfo != null && this.customerInfo.isValid();
    }
}
