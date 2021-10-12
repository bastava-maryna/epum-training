<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
 
 
 <c:url var="userUpdate" value="/jsp/user/update" />
 <c:url var="urlBookings" value="/jsp/user/bookings" />
 
 <section class="main-form">
  	<div class="left-part">
  	
  	</div>

  	<div class="top-part" >
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

  	<div id="person" class="down-part margintop20">
  		<div><h3 class="start"> <fmt:message key="person.personalData"/></h3></div>
  		
		<form class="join-form margintop20" method="POST" action="${userUpdate}" form="editForm">
			<c:if test="${not empty sessionScope.loginedUser}">
				<input type="hidden" name="currentUserId" value="${sessionScope.loginedUser.id}">
				<input type="hidden" name="role" value="${sessionScope.loginedUser.role}">
				<input type="hidden" name="userId" value="${sessionScope.loginedUser.id}">
			</c:if>	
  	
  				<div class="column_6">
					<input id="firstName" name="firstName" value="${sessionScope.loginedUser.firstName}" maxlength="30"type="text"> 
					<label for="firstName" ><fmt:message key="join.name"/></label>
				</div>
				<div class="column_6">
					<input id="lastName" name="lastName" value="${sessionScope.loginedUser.lastName}" maxlength="30" type="text"> 
					<label for="lastName"><fmt:message key="join.surname"/></label>
				</div>
				
				<div class="column_6">
					<input id="passport" name="passport" value="${sessionScope.loginedUser.passport}" type="text" maxlength="30">
					<label for="passport" ><fmt:message key="join.passport"/></label>
				</div>
			
				<div class="column_6">
					<input id="email" name="email" value="${sessionScope.loginedUser.email}" type="text" maxlength="40">
					<label for="email" ><fmt:message key="join.email"/></label>
				</div>
			
				<div class="column_6">
					<input id="login" name="login" value="${sessionScope.loginedUser.login}" type="text" maxlength="20">
					<label for="login" ><fmt:message key="join.login"/></label>
				</div>
			
				<div class="column_6">
					<input id="password" name="password" value="${sessionScope.loginedUser.password}" type="password">
					<label for="password" ><fmt:message key="join.password"/></label>
					<a id="passwordShowButton" class="wrapper_icoVer"> <span
						class="icoSpriteB icoVer"></span></a>
				</div>
				<div>
			
                <button class="button margintop20" type="submit" ><fmt:message key="button.saveChanges"/></button>
			</div>
		</form>	
	</div>

	<div class="right-part">	
	 <%@ include file="/WEB-INF/jspf/advantages.jspf"%>
	</div>

</section>
				