package org.unimelb.postmanagement.util;

import javax.servlet.http.HttpServletRequest;

import org.unimelb.postmanagement.model.NewPost;

public class Utils {

	// New post in Cart, stored in Session.
	public static NewPost getNewPostInSession(HttpServletRequest request) {

		// Get new post from Session.
		NewPost newPost = (NewPost) request.getSession().getAttribute("newPost");

		// If null, create it.
		if (newPost == null) {
			newPost = new NewPost();

			// And store to Session.
			request.getSession().setAttribute("newPost", newPost);
		}

		return newPost;
	}

	public static void removeNewPostInSession(HttpServletRequest request) {
		request.getSession().removeAttribute("newPost");
	}

	public static void storeLastOrderedPostInSession(HttpServletRequest request, NewPost newPost) {
		request.getSession().setAttribute("lastOrderedPost", newPost);
	}

	public static NewPost getLastOrderedPostInSession(HttpServletRequest request) {
		return (NewPost) request.getSession().getAttribute("lastOrderedPost");
	}

}