<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

  
 <section class="main-form">
  	<div class="left-part">
  	</div>

  	<div class="top-part " >
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

  	<div id="join" class="down-part margintop20">
  		<c:url var="signUrl" value="/jsp/sign" />
		
		<form class="join-form" method="POST" action="${signUrl}">				
			<div class="detail">
				<span><fmt:message key="join.enterName" /></span>
			</div>
			
			<p class="obligationWarning"><fmt:message key="join.allFields" /></p>

				<div class="column_6">
					<input id="firstName" name="firstName" value="${param.firstName}" maxlength="30" type="text" required> 
					<label for="firstName" ><fmt:message key="join.name" /></label>
					<span class="error">${errors.firstName}</span>
				</div>
				<div class="column_6">
					<input id="lastName" name="lastName" value="${param.lastName}" type="text" maxlength="30" required> 
					<label for="lastName" ><fmt:message key="join.surname" /></label>
					<span class="error">${errors.lastName}</span>
				</div>
				
				<div class="column_6">
					<input id="passport" name="passport" value="${param.passport}" type="text"  maxlength="30" required>
					<label for="passport" ><fmt:message key="join.passport" /></label>
					<span class="error">${errors.passport}</span>
				</div>
			
				<div class="column_6">
					<input id="email" name="email" value="${param.email}" type="text" maxlength="40" >
					<label for="email" ><fmt:message key="join.email" /></label>
					<span class="error">${errors.email}</span>
				</div>
			
				<div class="column_6">
					<input id="login" name="login" value="${param.login}" type="text" maxlength="20" required>
					<label for="login" ><fmt:message key="join.login" /></label>
					<span class="error">${errors.login}</span>
				</div>
			
				<div class="column_6">
					<input id="password" name="password" value="${param.password}" type="password" required>
					<label for="password" ><fmt:message key="join.password" /></label>
					<a class="wrapper_icoVer"> <span class="icoVer"></span></a>
					<span class="error">${errors.password}</span>
				</div>
				<div class="column_6">
					<input id="passwordConfirm" name="passwordConfirm" value="${param.passwordConfirm}"
						type="password" required> 
						<label for="passwordConfirm"><fmt:message key="join.repeatPassword"/></label>
						 <a class="wrapper_icoVer"><span class="icoVer"></span>
						<span class="error">${errors.passwordConfirm}</span>
					</a>
				</div>
			
			<p class="obligationWarning"><fmt:message key="join.obligatory"/></p>

			
			<div class="column_12">
					<input type="checkbox" class="typeCheck" id="legalConditionsCheckbox" required> 
					<label for="legalConditionsCheckbox" ><fmt:message key="join.accept"/>
							<a href="https://www.urbo.com/en/urbo/urbo-terms-and-conditions">
							<fmt:message key="join.terms"/></a><fmt:message key="join.and"/>  
							<a href="https://www.urbo.com/en/customer-services/privacy-policy">
							<fmt:message key="join.privacy"/></a>
					</label>
			</div>
			
			<div>				
                <button class="button" type="submit"  ><fmt:message key="join.signup"/></button>
			</div>
		</form>
	</div>

<div class="right-part">	
	 <%@ include file="/WEB-INF/jspf/advantages.jspf"%>
</div>


</section>
