<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
 
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
 
<title>New Post Confirmation</title>
 
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/styles.css">
 
</head>
<body>
  <jsp:include page="_header.jsp" />
 
  <jsp:include page="_menu.jsp" />
 
  <fmt:setLocale value="en_US" scope="session"/>
 
  <div class="page-title">New Post Confirmation</div>
 
 
 
  <div class="customer-info-container">
      <h3>Customer Information:</h3>
      <ul>
          <li>Name: ${newPost.customerInfo.name}</li>
          <li>Email: ${newPost.customerInfo.email}</li>
          <li>Phone: ${newPost.customerInfo.phone}</li>
          <li>Address: ${newPost.customerInfo.address}</li>
      </ul>
  </div>
 
  <form method="POST"
      action="${pageContext.request.contextPath}/newPostConfirmation">
 
      <!-- Edit Customer Info -->
      <a class="navi-item"
          href="${pageContext.request.contextPath}/newPost">Edit
          Customer Info</a>
 
      <!-- Send/Save -->
      <input type="submit" value="Send" class="button-send-sc" />
  </form>
 
  <jsp:include page="_footer.jsp" />
 
</body>
</html>