<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:url var="urlFindTrain" value="/jsp/book/list"/>
	
  
  	<h3 class="book-question"><fmt:message key="book.whereDoYou"/></h3>
  	
 <section class="main-form">
  	<div class="left-part">
  	</div>
  	

  	<form class="top-part" method="post" action="${urlFindTrain}" >
	
    	<div>
    			<select id="departureStationId" name="departureStationId" required>
						<c:forEach var="station" items="${stations}">
										<option value="" disabled selected hidden><fmt:message key="book.from" /></option>
                                        <option value="${station.id}">${station.stationName}</option>
                                    
						</c:forEach>
					</select> 		
    	</div>

    	<div>
    	<select id="destinationStationId" name="destinationStationId" required>
						<c:forEach var="station" items="${stations}">
				<option value="" disabled selected hidden><fmt:message key="book.to" /></option>
                                        <option value="${station.id}">${station.stationName}</option>
                                
						</c:forEach>
				</select> 		
    	</div>

    	<div>
      		<input name="departureDate" type="date" min="${today}" required>
    	</div>

    	<div>
    	
      		<button class="button"><fmt:message key="button.find" /></button>
    	</div>
  	</form>

  	<div class="down-part">
  	
  	<c:if test="${not empty param.payment }"> 
  			<p3>${param.payment }</p3>
  	</c:if>
  	
  	</div>

  	<div class="right-part">
	
	 <%@ include file="/WEB-INF/jspf/advantages.jspf"%>
	</div>
  	

</section>
