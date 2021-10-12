<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
 
    <c:url var="urlRouteList" value="/jsp/route/list"/>
    <c:url var="urlRouteSave" value="/jsp/route/save"/>
    <c:url var="urlRouteDelete" value="/jsp/route/delete"/>
 
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
					<c:when test="${not empty currentRoute }">
					<fmt:message key="route.list.table.title" />
							:
							<fmt:message key="edit.title.edit" />
					</c:when>
					<c:otherwise>
							<fmt:message key="route.list.table.title" />
							:
							<fmt:message key="edit.title.create" />

					</c:otherwise>
				</c:choose>
			</div>
			
			<div class="tr">
				<span class="th"> <fmt:message key="route.list.table.id_route" /></span> 
				<span class="th"> <fmt:message key="route.list.table.departure" /></span>
				<span class="th"> <fmt:message key="route.list.table.destination" /></span>
			</div>
			
			<form class="tr" action="${urlRouteSave}" method="post" id="editForm">
				<span class="td">
					 <c:if test="${not empty currentRoute}">
						<input name="routeId" value="${currentRoute.id}" readonly>
					</c:if>
				</span> 
				
				<span class="td"> 
					<select id="departureStationId" name="departureStationId" required>
						<c:forEach var="station" items="${stations}">
							<c:if test="${station.id != currentRoute.departure.id}">
                                  <option value="${station.id}">${station.stationName}</option>
                            </c:if>
                            
                            <c:if test="${station.id == currentRoute.departure.id}">
                                   <option value="${station.id}" selected>${station.stationName}</option>
                            </c:if>
						</c:forEach>
					</select>
				</span> 
				
				<span class="td"> 
					<select id="destinationStationId" name="destinationStationId" required>
						<c:forEach var="station" items="${stations}">
							<c:if test="${station.id != currentRoute.destination.id}">
                                  <option value="${station.id}">${station.stationName}</option>
                            </c:if>
                            
                            <c:if test="${station.id == currentRoute.destination.id}">
                                  <option value="${station.id}" selected>${station.stationName}</option>
                            </c:if>
							
						</c:forEach>
					</select>
				</span>	
			</form>
		</div>
	</div>

	<div class="right-part">
		<div >
				
			<button class="button margintop20" form="editForm" >
				<fmt:message key="button.save" />
			</button>
			
			<c:if test="${not empty route.id}">
				<c:if test="${not routeCanBeDeleted}">
					<c:set var="disabled" value="disabled" />
				</c:if>
				<button class="button margintop20" formaction="${urlRouteDelete}" form="editForm"
					formmethod="post" ${disabled}>
					<fmt:message key="button.delete" />
				</button>
			</c:if>

		 	<button class="button margintop20" type="reset" form="editForm">
				<fmt:message key="button.reset" />
			</button>
			
			<button class="button margintop20" formaction="${urlRouteList} " form="editForm" formnovalidate>
				<fmt:message key="button.cancel" />
			</button>
		</div>
	
	</div>
</section>