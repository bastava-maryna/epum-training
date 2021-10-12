<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
  
 <section class="main-form">
  	
  	<div class="left-part">
  		<%@ include file="/WEB-INF/jspf/managermenu.jspf"%>
  	</div>

  	<div class="top-part">
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

  	<div class="down-part">
		
		<table class="table-list margintop20">
			
			<caption>
				<fmt:message key="user.list.table.title" />
			</caption>
			
			<tr>
				<th><fmt:message key="user.list.table.idUser" /></th>
				<th><fmt:message key="user.list.table.lastName" /></th>
				<th><fmt:message key="user.list.table.firstName" /></th>
				<th><fmt:message key="user.list.table.passport" /></th>
				<th><fmt:message key="user.list.table.role" /></th>
				<th><fmt:message key="user.list.table.login" /></th>
				<th><fmt:message key="user.list.table.password" /></th>
				<th><fmt:message key="user.list.table.email" /></th>
				<td>&nbsp;</td>
			</tr>
			
			<c:forEach var="user" items="${users}">
				<tr>
					<td class="content">${user.id}</td>
					<td class="content">${user.lastName}</td>
					<td class="content">${user.firstName}</td>
					<td class="content">${user.passport}</td>
					<td class="content">${user.role}</td>
					<td class="content">${user.login}</td>
					<td class="content">${user.password}</td>
					<td class="content">${user.email}</td>
					<td class="empty">
						
						<c:url var="urlUserEdit" value="/jsp/user/edit">
	 			 			<c:param name="userId" value="${user.id}" />
							<c:param name="lastName" value="${user.lastName}" />
							<c:param name="firstName" value="${user.firstName}" />
							<c:param name="passport" value="${user.passport}" />
							<c:param name="role" value="${user.role}" />
							<c:param name="login" value="${user.login}" />
							<c:param name="password" value="${user.password}" />
							<c:param name="email" value="${user.email}" />
						</c:url> 
					
						<a href="${urlUserEdit}" class="edit"></a>
					</td>
				</tr>
			</c:forEach>
		</table>
		
		<c:url var="urlUserEdit" value="/jsp/user/edit"/>   
		<c:url var="urlUserAdd" value="/jsp/sign"/>	
  	
  	</div>

  	<div class="right-part">
  		<form>
     		<button class="button " formaction="${urlUserEdit}"  type="submit">
     			<fmt:message key="button.add"/>
     		</button>
     	</form>
  	
  	</div>

</section>
