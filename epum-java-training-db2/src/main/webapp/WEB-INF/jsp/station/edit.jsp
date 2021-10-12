<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
 <!--  
 <c:if test="${empty station}"><jsp:useBean id="station" class="by.epum.training.db.entity.Station"/></c:if>
--> 
  	<c:url var="urlStationList" value="/jsp/station/list"/>
    <c:url var="urlStationSave" value="/jsp/station/save"/>
    <c:url var="urlStationDelete" value="/jsp/station/delete"/>
 
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
		
		<div class="station-list-edit-table">
			<div class="caption">
				<c:choose>
					<c:when test="${not empty station.id}">
						<fmt:message key="station.list.table.title" />
							:
						<fmt:message key="edit.title.edit" />
					</c:when>
					
					<c:otherwise>
						<fmt:message key="station.list.table.title" />
							:
						<fmt:message key="edit.title.create" />
					</c:otherwise>
				</c:choose>

			</div>
			
			<div class="tr">
				<span class="th"> <fmt:message
						key="station.list.table.id_station" />
				</span> <span class="th"> <fmt:message
						key="station.list.table.station_name" />
						
				</span>
			</div>
			
			<form class="tr" action="${urlStationSave}" method="post" id="editForm">
				<span class="td">
					<c:if test="${not empty station.id}">
						<input name="id" value="${station.id}" readonly>
					</c:if> 
				</span> 
				
				<span class="td"><input id="stationName"
					name="stationName" value="${station.stationName}"> </span>

			</form>			
	
		</div>
	</div>

	<div class="right-part">
		<div >
				<button class="button margintop20" form="editForm">
					<fmt:message key="button.save" />
				</button>
				
				<c:if test="${not empty station.id}">
					<c:if test="${not stationCanBeDeleted}">
						<c:set var="disabled" value="disabled" />
					</c:if>
					<button class="button margintop20" formaction="${urlStationDelete}" form="editForm"
						formmethod="post" ${disabled}>
						<fmt:message key="button.delete" />
					</button>
				</c:if>

				<button class="button margintop20" type="reset" form="editForm">
					<fmt:message key="button.reset" />
				</button>
			
				<button class="button margintop20" formaction="${urlStationList} "
					formmethod="post" form="editForm" formnovalidate><fmt:message key="button.cancel" />
				</button>
		</div>
	</div>
	
</section>