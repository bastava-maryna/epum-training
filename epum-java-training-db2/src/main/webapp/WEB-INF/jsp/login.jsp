<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
 
 
 <section class="main-form">
  	
  	<div class="left-part">
  	</div>

	<!--<c:url var="loginUrl" value="${pageContext.request.contextPath}/jsp/login"/>-->
	<c:url var="loginUrl" value="/jsp/login"/>
	<c:url var="joinUrl" value="/jsp/join"/>
	
	<form class="top-part" method="POST" action="${loginUrl}">  
    	<div>
    		<label for="login" class="form-input_label"><fmt:message key="join.login" /></label>
      		<input type="text" id="login" name="login" size="20" required>
      		<input type="hidden" id="redirectId" name="redirectId" value="${param.redirectId }">
    	</div>

	  	<div>
    		<label for="password" class="form-input_label"><fmt:message key="join.password" /></label>
      		<input type="password" id="password" name="password" size="10" required>
    	</div>

    	<div>
      		<p class="fogotten"><fmt:message key="login.forgot" />
				<a href="#"><fmt:message key="login.login" /></a> 
				<fmt:message key="login.or" /> 
				<a href="#"><fmt:message key="login.password" /></a>
				
			</p>
			<p class="fogotten"><fmt:message key="login.haveNo" />
				<a href="${joinUrl}"><fmt:message key="login.account" /></a> 
				
			
			</p>
    	</div>

    	<div>
      		<!--  <input class="button" type="submit" value="Login" name="submit"/>-->
      		<button class="button" type="submit"  ><fmt:message key="join.login" /></button>
    	</div>
    	
  	</form>

  	<div class="down-part ">
  		<div class="margintop100">
  			<c:if test="${not empty errors}">
  			<p class="error"><fmt:message key="error"/></p>
  			<c:forEach var="error" items="${errors}">
  				<p class="error"><fmt:message key="${error.key}" /></p>
  			</c:forEach>			
  		</c:if>	
  		<c:if test="${not empty param.message}">
  			<p class="ok"><fmt:message key="${param.message}"/></p>	
  		</c:if>
  		</div>
  	</div>
  		
	<div class="right-part">	
	 <%@ include file="/WEB-INF/jspf/advantages.jspf"%>
	</div>


</section>
