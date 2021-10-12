<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
 

 		<c:url var="urlCarriageChoose" value="/jsp/carriage/choose"/>
  		<c:url var="urlBook" value="/jsp/book"/>
  		<c:url var="urlBookList" value="/jsp/book/list"/>
  		<c:url var="urlBookSave" value="/jsp/book/save"/>
 
 <section class="main-form">
  	<div class="left-part">
  	</div>

  	<form class="top-part" method="post" action="${urlBook}" >	  
	
    	<div>
    		<input name="departureStationId" value="${sessionScope.requestedTrain.route.departure.id}"  hidden>
    		<input name="departureStation" value="${sessionScope.requestedTrain.route.departure.stationName}" readonly>	
    	</div>

    	<div>
    		<input name="destinationStationId" value="${sessionScope.requestedTrain.route.destination.id}" hidden>
    		<input name="destinationStation" value="${sessionScope.requestedTrain.route.destination.stationName}" readonly>
  	  	</div>

    	<div>
      		<input type="date" name="departureDate"   value="${sessionScope.requestedTrain.departureDate}" readonly>
    	</div>

    	<div>		
      		<button class="button"><fmt:message key="button.changeSearch" /></button>
    	</div>
  	</form>
  		
  
  	<div class="down-part ">
		<table class="book-table-list price-selector">
			
			<caption>
				<fmt:message key="carriage.choose.table.title" />
			</caption>
			
			<tr>		
				<th><fmt:message key="carriage.choose.table.carriageNumber" /></th>
				<th><fmt:message key="carriage.choose.table.carriageType" /></th>
				<th><fmt:message key="carriage.choose.table.price" /></th>
				<th><fmt:message key="carriage.choose.table.placeNumber" /></th>
			
				<td>&nbsp;</td>
			</tr>
			
			<c:if test="${empty availables}">
				<p3 class="ok"><fmt:message key="carriage.choose.table.noPlace" /></p3>
			</c:if>
			
			<c:forEach var="available" items="${availables}">
	  			<form class="tr" action="${urlBookSave}" method="post" id="editForm">
					<tr >
						<input name="carriageId" value="${available.key.id}" hidden>
			
						<td class="content"><input name="carriageNumber" value="${available.key.carriageNumber}" readonly></td>
						<td class="content"><input name="carriageType" value="${available.key.carriageType}" readonly></td>
						<td class="content"><input name="cost" value="${prices[available.key.carriageType]}" readonly></td>
					
						<td class="content">
							<select id="place" name="place" required>
								<c:forEach var="place" items="${available.value}">
                                        <option value="${place}">${place}</option>
								</c:forEach>
							</select>
						</td>
					
						<td class="empty">
							<c:url var="urlBookSave" value="/jsp/book/save"/>
							<button class="button margintop20" type="submit"><fmt:message key="button.choose"/></button>
						</td>
					</tr>
				</form>
			</c:forEach>			
		</table>	
  	</div>

  	<div class="right-part">
  	<!-- 	<div>
     		<button class="button margintop20" formaction="${urlBookList}" ><fmt:message key="button.back"/></button>
     	</div> -->
  	
  	</div>

</section>
