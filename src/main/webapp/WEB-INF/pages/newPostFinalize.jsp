<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
 
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
 
<title>All Done</title>
 
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/styles.css">
 
</head>
<body>
   <jsp:include page="_header.jsp" />
 
   <jsp:include page="_menu.jsp" />
 
   <div class="page-title">All Done</div>
  
   <div class="container">
       <h3>Thank you!</h3>
       Your post number is: ${lastOrderedPost.postNum}
   </div>
 
   <jsp:include page="_footer.jsp" />
 
</body>
</html>