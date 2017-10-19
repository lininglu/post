package org.unimelb.postmanagement.dao;

import org.unimelb.postmanagement.entity.Account;

public interface AccountDAO {

	public Account findAccount(String userName);

}
