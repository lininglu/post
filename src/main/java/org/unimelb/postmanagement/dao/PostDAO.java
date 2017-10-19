package org.unimelb.postmanagement.dao;

import org.unimelb.postmanagement.model.PostInfo;
import org.unimelb.postmanagement.model.NewPost;
import org.unimelb.postmanagement.model.PaginationResult; 
public interface PostDAO {
 
    public void savePost(NewPost newPost, String userName);
 
    public PaginationResult<PostInfo> listPostInfo(int page,
            int maxResult, int maxNavigationPage, String userName);
  
    public PostInfo getPostInfo(String postId, String userName);

	public void update(PostInfo postInfo);
	
	public void delete(String postId);

	public PaginationResult<PostInfo> listAllPosts(int page, int maxResult, int maxNavigationPage);
}
