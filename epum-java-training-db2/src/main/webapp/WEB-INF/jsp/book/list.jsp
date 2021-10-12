<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
 
 <c:url var="urlCarriageChoose" value="/jsp/carriage/choose"/>
  		<c:url var="urlBook" value="/jsp/book"/>
 
 <section class="main-form">
  	<div class="left-part">
  	
  	</div>

  	<form class="top-part" method="post" action="${urlBook}" >	  
	
    	<div>
    			<input name="departureStationId" value="${requestedTrain.route.departure.id}"  hidden>
    			<input name="departureStation" value="${requestedTrain.route.departure.stationName}" readonly>
    			
    	</div>

    	<div>
    			<input name="destinationStationId" value="${requestedTrain.route.destination.id}" hidden>
    			<input name="destinationStation" value="${requestedTrain.route.destination.stationName}" readonly>
      		
    	</div>

    	<div>
      		<input type="date" name="departureDate"   value="${requestedTrain.departureDate}" readonly>
    	</div>

    	<div>
    	
    		
      		<button class="button"><fmt:message key="button.changeSearch" /></button>
    	</div>
  	</form>
  		
  

  	<div class="down-part ">
		<div class="book-list-edit-table">
			<c:if test="${not empty errors}">
  			<p class="error"><fmt:message key="error"/></p>
  			<c:forEach var="error" items="${errors}">
  				<p class="error"><fmt:message key="${error.key}" /></p>
  			</c:forEach>			
  		</c:if>	
  		<c:if test="${not empty param.message}">
  			<p class="ok"><fmt:message key="${param.message}"/></p>	
  		</c:if>
			
			<c:if test="${empty param.message}">	
				<c:if test="${empty trains}">
					<div class="ok" margintop20><fmt:message key="book.list.table.noResult"/></div>	
			     </c:if>
			<c:if test="${not empty trains}">
			</c:if>
		<div class="caption">
				<fmt:message key="book.list.table.title" />
			</div>
			
			<div class="tr">
			
				<span class="th"><fmt:message key="train.list.table.trainName" /></span>
				<span class="th"><fmt:message key="train.list.table.departureTime" /></span>
				<span class="th"><fmt:message key="train.list.table.destinationTime" /></span>
				<span class="th"><fmt:message key="train.list.table.scheduleMode" /></span>
				<span class="th"><fmt:message key="book.list.table.priceFrom"  /> BYN</span>
		
				<span class="th">&nbsp;</span>
			</div>
			
			<c:forEach var="train" items="${trains}">
	  			<form class="tr" action="${urlCarriageChoose}" method="post" id="editForm">
			
					<input name="trainId" value="${train.id}" hidden>
					<input name="price" value="${train.price}" hidden>
			
					<span class="td">
						<input name="trainName" value=${train.trainName} readonly>
					</span> 
					
					<span class="td">
						<input name="departureTime" value=${train.departureTime} readonly>
					</span> 
					
					<span class="td">
						<input name="destinationTime" value=${train.destinationTime}  readonly>
					</span> 
					
					<span class="td">
						<input  name="scheduleMode" value=${train.scheduleMode.description} readonly>
					</span> 
					
					<span class="td">
						<input name="cost" value=${prices[train.id][2]} readonly>
					</span> 
						
					
					
					<td class="empty">
						<button class="button"><fmt:message key="button.choose" /></button>
					</td>
			</form>
		</c:forEach>			
		</c:if>
		</div> 	
  	</div>

  	<div class="right-part">
  
  	</div>

</section>
