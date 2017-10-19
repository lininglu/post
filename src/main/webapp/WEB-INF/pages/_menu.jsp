<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="security" %>   
 
 
<div class="menu-container">
  
   <a href="${pageContext.request.contextPath}/">Home</a>
   |
   <security:authorize  access="hasRole('ROLE_CUSTOMER')">
     <a href="${pageContext.request.contextPath}/myPosts">
         My Posts
     </a>
     |
   </security:authorize>
   
   <security:authorize  access="hasRole('ROLE_CUSTOMER')">
     <a href="${pageContext.request.contextPath}/newPost">
         New Post
     </a>
     |
   </security:authorize>
   
   <security:authorize  access="hasRole('ROLE_ADMIN')">
     <a href="${pageContext.request.contextPath}/allPosts">
         All Posts
     </a>
     |
   </security:authorize>
  
</div>