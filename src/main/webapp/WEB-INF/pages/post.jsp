<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="security" %>  
 
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
 
<title>Update Post</title>
 
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/styles.css">
 
</head>
<body>
<jsp:include page="_header.jsp" />
<jsp:include page="_menu.jsp" />
 
<div class="page-title">Update Post</div>
 
   <form:form method="POST" modelAttribute="postInfo"
       action="${pageContext.request.contextPath}/post">
 
       <table>
          <tr>
               <td>Id *</td>
               <td style="color:red;">
                  <c:if test="${not empty postInfo.id}">
                       <form:hidden path="id"/>
                       ${postInfo.id}
                  </c:if>
                  <c:if test="${empty postInfo.id}">
                       <form:input path="id" />
                       <form:hidden path="newPost" />
                  </c:if>
               </td>
               <td><form:errors path="id" class="error-message" /></td>
           </tr>
           <tr>
               <td>Name *</td>
               <td><form:input path="customerName" /></td>
               <td><form:errors path="customerName" class="error-message" /></td>
           </tr>
 
           <tr>
               <td>Email *</td>
               <td><form:input path="customerEmail" /></td>
               <td><form:errors path="customerEmail" class="error-message" /></td>
           </tr>
 
           <tr>
               <td>Phone *</td>
               <td><form:input path="customerPhone" /></td>
               <td><form:errors path="customerPhone" class="error-message" /></td>
           </tr>
 
           <tr>
               <td>Address *</td>
               <td><form:input path="customerAddress" /></td>
               <td><form:errors path="customerAddress" class="error-message" /></td>
           </tr>
           <tr>
               <td>&nbsp;</td>
               <td>
               		<input type="submit" value="Submit" /> 
               		<input type="reset" value="Reset" />
               </td>
           </tr>
       </table>
 
   </form:form>
 
 
   <jsp:include page="_footer.jsp" />
 
 
</body>
</html>