<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
 
   <c:url var="urlUserList" value="/jsp/user/list"/>
   <c:url var="urlUserDelete" value="/jsp/user/delete"/>
   <c:url var="urlUserEdit" value="/jsp/user/edit"/>    
   <c:url var="urlUserUdate" value="/jsp/user/update"/> 
   <c:url var="urlUserAdd" value="/jsp/sign"/>
   <c:url var="urlUserSave" value="/jsp/user/save"/>


 <section class="main-form">
  	
  	<div class="left-part">
  		<%@ include file="/WEB-INF/jspf/managermenu.jspf"%>
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

	<div class="down-part">		
		<div class="station-list-edit-table">
			
			<div class="caption">
				<c:choose>
					<c:when test="${(not empty sessionScope.currentUser) }">
					<fmt:message key="user.list.table.title" />
							:
							<fmt:message key="edit.title.edit" />
					</c:when>
					<c:otherwise>
							<fmt:message key="user.list.table.title" />
							:
							<fmt:message key="edit.title.create" />

					</c:otherwise>
				</c:choose>
			</div>
			
			<div class="tr">
				<c:if test="${not empty sessionScope.currentUser}">
					
					<span class="th"> 
						<fmt:message key="user.list.table.idUser"/>
					</span> 
				</c:if>
				
				<span class="th"> <fmt:message key="user.list.table.lastName"/></span>
				<span class="th"><fmt:message key="user.list.table.firstName"/></span>
				<span class="th"><fmt:message key="user.list.table.passport"/></span>
				<span class="th"><fmt:message key="user.list.table.login"/></span>
				<span class="th"><fmt:message key="user.list.table.password"/></span>
				<span class="th"><fmt:message key="user.list.table.role"/></span>
				<span class="th"><fmt:message key="user.list.table.email"/></span>
			</div>
			
			<form class="tr" action="${urlUserSave}" method="post" id="editForm">
			 	<c:if test="${not empty sessionScope.currentUser}">	
					<span class="td">
						<input name="userId" value="${sessionScope.currentUser.id}" readonly>
					</span>	 
				</c:if>
				
				<span class="td">	
					<input name="lastName" value="${sessionScope.currentUser.lastName}" type="text" maxlength="30" required >
				</span> 
				
				<span class="td">	
					<input name="firstName" value="${sessionScope.currentUser.firstName}" type="text" maxlength="30" required >
				</span>
				
				<span class="td">	
					<input name="passport" value="${sessionScope.currentUser.passport}"type="text" maxlength="30" required >
				</span>
				
				<c:if test="${not empty sessionScope.currentUser}">	
					<span class="td">	
						<input name="login" value="${sessionScope.currentUser.login}" type="text" maxlength="20" readonly >
					</span>
				</c:if>
				<c:if test="${empty sessionScope.currentUser}">	
					<span class="td">	
						<input name="login" value="${sessionScope.currentUser.login}" type="text" maxlength="20" required >
					</span>
				</c:if>
				
				<span class="td">	
					<input name="password" value="${sessionScope.currentUser.password}" type="text" min="10" max="10" required >
				</span>
				
				<span class="td"> 
					<select id="role" name="role" required>
						<c:forEach var="role" items="${roles}">
							
							<c:if test="${role != sessionScope.currentUser.role}">
                                  <option value="${role}">${role} </option>
                            </c:if>
                                 
                            <c:if test="${role == sessionScope.currentUser.role}">
                                  <option value="${role}" selected>${role}</option>     
                            </c:if>
                            
						</c:forEach>
					</select>		
				</span> 
				
				<span class="td">	
					<input name="email" value="${sessionScope.currentUser.email}" type="text" maxlength="40"  >
				</span>				
			</form>
			
		</div>
	</div>

	<div class="right-part">
		<div >
	<!--		
			<c:if test="${empty sessionScope.currentUser}">
				<button class="button margintop20" form="editForm"  formaction="${urlUserAdd}">
					<fmt:message key="button.add" />
				</button>
			</c:if>
	  -->		

				<button class="button margintop20" form="editForm"  formaction="${urlUserSave}">
					<fmt:message key="button.save" />
				</button>

			
			<c:if test="${not empty user.id}">
				<c:if test="${not userCanBeDeleted}">
					<c:set var="disabled" value="disabled" />
				</c:if>
				
				<button class="button margintop20" formaction="${urlUserDelete}" form="editForm" formmethod="post" ${disabled}>
					<fmt:message key="button.delete" />
				</button>
			</c:if>

			<button class="button margintop20" type="reset" form="editForm">
				<fmt:message key="button.reset" />
			</button>
			
			<button class="button margintop20" formaction="${urlUserList}" form="editForm" formnovalidate>
				<fmt:message key="button.cancel" />
			</button>

		</div>
	</div>
</section>