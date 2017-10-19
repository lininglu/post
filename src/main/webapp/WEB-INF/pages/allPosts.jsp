<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
 
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>All Posts</title>
 
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/styles.css">
 
</head>
<body>
 
   <jsp:include page="_header.jsp" />
   <jsp:include page="_menu.jsp" />
 
   <fmt:setLocale value="en_US" scope="session"/>
  
   <div class="page-title">All Posts</div>
  
   <table border="1" style="width:100%">
       <tr>
           <th>Post Num</th>
           <th>Post Date</th>
           <th>Customer Name</th>
           <th>Customer Address</th>
           <th>Customer Email</th>
           <th>Comment</th>
           <th>Update</th>
           <th>Delete</th>
       </tr>
       <c:forEach items="${paginationResult.list}" var="postInfo">
           <tr>
               <td>${postInfo.postNum}</td>
               <td>
                  <fmt:formatDate value="${postInfo.postDate}" pattern="dd-MM-yyyy HH:mm"/>
               </td>
               <td>${postInfo.customerName}</td>
               <td>${postInfo.customerAddress}</td>
               <td>${postInfo.customerEmail}</td>
               <td>${postInfo.comment}</td>
               <td><a href="${pageContext.request.contextPath}/adminPost?postId=${postInfo.id}">
                  Update</a></td>
               <td><a href="${pageContext.request.contextPath}/adminDelete?postId=${postInfo.id}">
                  Delete</a></td>
           </tr>
       </c:forEach>
   </table>
   <c:if test="${paginationResult.totalPages > 1}">
       <div class="page-navigator">
          <c:forEach items="${paginationResult.navigationPages}" var = "page">
              <c:if test="${page != -1 }">
                <a href="myPosts?page=${page}" class="nav-item">${page}</a>
              </c:if>
              <c:if test="${page == -1 }">
                <span class="nav-item"> ... </span>
              </c:if>
          </c:forEach>
          
       </div>
   </c:if>
 
 
 
 
   <jsp:include page="_footer.jsp" />
 
</body>
</html>