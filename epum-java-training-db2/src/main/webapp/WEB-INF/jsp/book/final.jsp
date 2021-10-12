<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
 <%@ taglib prefix="func" uri="/WEB-INF/func.tld" %>
 
 	<c:url var="urlCarriageChoose" value="/jsp/carriage/choose"/>
  	<c:url var="urlBook" value="/jsp/book"/>
  	<c:url var="urlPay" value="/jsp/pay"/>
  		
 
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
      		<input type="date" name="departureDate" type="date"  value="${sessionScope.requestedTrain.departureDate}" readonly>
    	</div>

    	<div>
    	  		
      		<button class="button"><fmt:message key="button.changeSearch" /></button>
    	</div>
  	</form>
  		
  
  	<div class="down-part ">
		<form class="tr" action="${urlPay}" method="post" id="editForm">
			<table class="book-table-list">
		
				<caption>
					<fmt:message key="book.final.title" />
				</caption>
			
				<tr>
					<td class="info">${sessionScope.bill.user.lastName}</td>
					<td class="info"></td>
					<td class="info">${sessionScope.bill.user.firstName}</td>
				</tr>
			
				<tr>
					<td class="info"><fmt:message key="book.final.passport" /></td>
					<td class="info"></td>
					<td class="info">${sessionScope.bill.user.passport}</td>
				</tr>
						
				<tr>
					<td class="info"></td>
					<td class="info">${sessionScope.train.departureDate}</td>
					<td class="info"></td>
				</tr>
				
				<tr >	
					<td class="info">${sessionScope.train.route.departure.stationName}</td>
					<td class="info"></td>
					<td class="info">${sessionScope.train.route.destination.stationName}</td>
				</tr>
				
				<tr> 
					<td class="info">${sessionScope.train.departureTime}	</td>
					<td class="info"></td>
					<td class="info">${sessionScope.train.destinationTime}</td>
				</tr>
					
				<tr>
					<td class="info"><fmt:message key="book.final.train" /></td>
					<td class="info"></td>
					<td class="info">${sessionScope.train.trainName}</td>
				</tr>
				
				<tr>
					<td class="info"><fmt:message key="book.final.carriage" /></td>
					<td class="info">${sessionScope.bill.carriage.carriageType}</td>
					<td class="info">${sessionScope.bill.carriage.carriageNumber}</td>
				</tr>
				
				<tr>
					<td class="info"><fmt:message key="book.final.place" /></td>
					<td class="info"></td>
					<td class="info">${sessionScope.bill.place}</td>
				</tr>
				
				<tr>
					<td class="info"><fmt:message key="book.final.cost" /></td>
					<td class="info"></td>
					<td class="info">${sessionScope.bill.cost} BYN</td>
				</tr>
										
				<tr>
						
				<c:if test="${not empty pay}">
					<c:set var="disabled" value="disabled" />
				</c:if>
				
					<td>
						<button class="button" type="submit" name="pay" value="now" ${disabled}><fmt:message key="button.payNow"/></button>
					</td>
				
					<td class="empty">
						<c:url var="urlBookSave" value="/jsp/book/save"/>
					</td>
					<td>
						<button class="button"  type="submit" name="pay" value="later" ${disabled}><fmt:message key="button.payLater"/></button>
					</td>
				</tr>
			</table>	
		</form>
		
		<div>
			<c:if test="${pay eq 'now'}">
				<div class="start" ><fmt:message key="book.final.success" /></div>
			</c:if>
		
			<c:if test="${pay eq 'later'}">
				<div class="start" ><fmt:message key="book.final.later" /></div>
			</c:if>
		</div>		
  	</div>
  	

  	<div class="right-part">
 		  <%@ include file="/WEB-INF/jspf/advantages.jspf"%>
  	</div>

</section>
