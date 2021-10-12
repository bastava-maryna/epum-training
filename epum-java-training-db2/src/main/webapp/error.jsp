
<%@ page language="java" contentType="text/html; charset=UTF-8"
    isErrorPage="true"
    pageEncoding="UTF-8"%>
    
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

 
 <section class="main-form">
  	
  	<div class="left-part">
  	</div>

  	<div class="top-part">	
  		<strong class="error"><fmt:message key="exception" /></strong>
  	</div>
<div class="down-part">
  	<div class="error" >
  		<strong class="error">${requestScope.exception}</strong>
  	</div>
</div>
  	<div class="right-part">
  	</div>

</section>
