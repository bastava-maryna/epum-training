<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
  
 <jsp:useBean id="now" class="java.util.Date"/>
 
 <fmt:formatDate var="nowDate" value="${now}" pattern="yyyy-MM-dd"/> 
 <fmt:formatDate var="nowTime" value="${now}" pattern="HH:mm"/> 
 
  
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
				<fmt:message key="train.list.table.title" />
			</caption>
			
			<tr>
				<th><fmt:message key="train.list.table.idTrain" /></th>
				<th><fmt:message key="train.list.table.trainName" /></th>
				<th><fmt:message key="train.list.table.route" /></th>
				<th class="empty-bottom">&nbsp;</th>
				<th><fmt:message key="train.list.table.departureTime" /></th>
				<th><fmt:message key="train.list.table.destinationTime" /></th>
				<th><fmt:message key="train.list.table.basePrice" /></th>
				<th><fmt:message key="train.list.table.scheduleMode" /></th>
				<th><fmt:message key="train.list.table.departureDate" /></th>
				<td>&nbsp;</td>
			</tr>
			
			<c:forEach var="train" items="${trains}">
				<tr>
					<td class="content">${train.id}</td>
					<td class="content">${train.trainName}</td>
					<td class="content">${train.route.id} :</td>
					<td class="content">${train.route.departure.stationName} -> ${train.route.destination.stationName}</td>
					<td class="content">${train.departureTime}</td>
					<td class="content">${train.destinationTime}</td>
					<td class="content">${train.price}</td>
					<td class="content">${train.scheduleMode.description}</td>
					<td class="content">${train.departureDate}</td>
					<td class="empty">
						
						<c:url var="urlTrainEdit" value="/jsp/train/edit">
	 			 			<c:param name="trainId" value="${train.id}" />
							<c:param name="trainName" value="${train.trainName}" />
							<c:param name="routeId" value="${train.route.id}" />
							<c:param name="destinationStationId" value="${train.route.destination.id}" />
							<c:param name="departureStationId" value="${train.route.departure.id}" />
							<c:param name="destinationStationName" value="${train.route.destination.stationName}" />
							<c:param name="departureStationName" value="${train.route.departure.stationName}" />
							<c:param name="departureTime" value="${train.departureTime}" />
							<c:param name="destinationTime" value="${train.destinationTime}" />
							<c:param name="price" value="${train.price}" />
							<c:param name="scheduleModeId" value="${train.scheduleMode.id}" />
							<c:param name="departureDate" value="${train.departureDate}" />	
						</c:url> 
			<c:if test="${ train.departureDate gt nowDate or (train.departureDate eq nowDate and train.departureTime gt nowTime)}">
				
						<a href="${urlTrainEdit}" class="edit" disabled="disabled"></a>
						</c:if>
					</td>
				</tr>
			</c:forEach>
		</table>
		
		<c:url var="urlTrainEdit" value="/jsp/train/edit"/>   	
  	
  	</div>

  	<div class="right-part">
  		<form>
     		<button class="button " formaction="${urlTrainEdit}"  type="submit"><fmt:message key="button.add"/></button>
     	</form>
  	
  	</div>

</section>
