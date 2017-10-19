package org.unimelb.postmanagement.dao.impl;

import java.util.Date;
import java.util.UUID;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.unimelb.postmanagement.dao.AccountDAO;
import org.unimelb.postmanagement.dao.PostDAO;
import org.unimelb.postmanagement.entity.Account;
import org.unimelb.postmanagement.entity.Post;
import org.unimelb.postmanagement.model.NewPost;
import org.unimelb.postmanagement.model.CustomerInfo;
import org.unimelb.postmanagement.model.PostInfo;
import org.unimelb.postmanagement.model.PaginationResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

@SuppressWarnings("deprecation")
@Transactional
public class PostDAOImpl implements PostDAO {

	@Autowired
	private SessionFactory sessionFactory;
	
	@Autowired
    private AccountDAO accountDAO;


	@SuppressWarnings("rawtypes")
	private int getMaxPostNum() {
		String sql = "Select max(p.postNum) from " + Post.class.getName() + " p ";
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery(sql);
		Integer value = (Integer) query.uniqueResult();
		if (value == null) {
			return 0;
		}
		return value;
	}

	public void savePost(NewPost newPost, String userName) {
		Session session = sessionFactory.getCurrentSession();

		int postNum = this.getMaxPostNum() + 1;
		Post post = new Post();

		post.setId(UUID.randomUUID().toString());
		post.setPostNum(postNum);
		post.setPostDate(new Date());

		CustomerInfo customerInfo = newPost.getCustomerInfo();
		post.setCustomerName(customerInfo.getName());
		post.setCustomerEmail(customerInfo.getEmail());
		post.setCustomerPhone(customerInfo.getPhone());
		post.setCustomerAddress(customerInfo.getAddress());
		Account account = accountDAO.findAccount(userName);
		post.setAccount(account);
		session.persist(post);

		// Set postNum for report.
		newPost.setPostNum(postNum);
	}

	// @page = 1, 2, ...
	@SuppressWarnings("rawtypes")
	public PaginationResult<PostInfo> listPostInfo(int page, int maxResult, int maxNavigationPage, String userName) {
		String sql = "Select new " + PostInfo.class.getName()//
				+ "(pst.id, pst.postDate, pst.postNum, pst.account,"
				+ " pst.customerName, pst.customerAddress, pst.customerEmail, pst.customerPhone, pst.comment) " + " from "
				+ Post.class.getName()
				+ " pst " + " where  pst.account.userName = :userName"
				+  " order by pst.postNum desc";
		Session session = this.sessionFactory.getCurrentSession();

		Query query = session.createQuery(sql);
		query.setParameter("userName", userName);
		return new PaginationResult<PostInfo>(query, page, maxResult, maxNavigationPage);
	}

	public Post findPost(String postId) {
		Session session = sessionFactory.getCurrentSession();
		Criteria crit = session.createCriteria(Post.class);
		crit.add(Restrictions.eq("id", postId));
		return (Post) crit.uniqueResult();
	}

	public PostInfo getPostInfo(String postId, String userName) {
		Post post = this.findPost(postId);
		if (post == null) {
			return null;
		}
		Account account = accountDAO.findAccount(userName);
		return new PostInfo(post.getId(), post.getPostDate(), //
				post.getPostNum(), account, post.getCustomerName(), //
				post.getCustomerAddress(), post.getCustomerEmail(), post.getCustomerPhone(), post.getComment());
	}

	public void update(PostInfo postInfo) {
		String code = postInfo.getId();
		 
        Post post = null;

        if (code != null) {
            post = this.findPost(code);
        }
        post.setCustomerAddress(postInfo.getCustomerAddress());
        post.setCustomerEmail(postInfo.getCustomerEmail());
        post.setCustomerName(postInfo.getCustomerName());
        post.setCustomerPhone(postInfo.getCustomerPhone());
        post.setComment(postInfo.getComment());
        
        this.sessionFactory.getCurrentSession().flush();
	}

	public void delete(String postId) {
		Session session = this.sessionFactory.getCurrentSession();
		Post p = findPost(postId);
		if (null != p) {
			session.delete(p);
		}
	}

	public PaginationResult<PostInfo> listAllPosts(int page, int maxResult, int maxNavigationPage) {
		String sql = "Select new " + PostInfo.class.getName()//
				+ "(pst.id, pst.postDate, pst.postNum, pst.account,"
				+ " pst.customerName, pst.customerAddress, pst.customerEmail, pst.customerPhone, pst.comment) " + " from "
				+ Post.class.getName()
				+ " pst "
				+ " order by pst.postNum desc";
		Session session = this.sessionFactory.getCurrentSession();

		Query query = session.createQuery(sql);
		return new PaginationResult<PostInfo>(query, page, maxResult, maxNavigationPage);
	}
	
}
