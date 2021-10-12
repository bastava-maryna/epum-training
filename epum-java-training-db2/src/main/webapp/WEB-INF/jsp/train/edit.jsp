<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
 
   <c:url var="urlTrainList" value="/jsp/train/list"/>
   <c:url var="urlTrainDelete" value="/jsp/train/delete"/>
   <c:url var="urlTrainSave" value="/jsp/train/save"/>
   <c:url var="urlCarriageEdit" value="/jsp/carriage/edit"/>   
   <c:url var="urlCarriageDelete" value="/jsp/carriage/delete"/>   

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
					<c:when test="${(not empty sessionScope.currentTrain) }">
					<fmt:message key="train.list.table.title" />
							:
							<fmt:message key="edit.title.edit" />
					</c:when>
					<c:otherwise>
							<fmt:message key="train.list.table.title" />
							:
							<fmt:message key="edit.title.create" />

					</c:otherwise>
				</c:choose>
			</div>
			
			<div class="tr">
				<c:if test="${not empty sessionScope.currentTrain}">
					
					<span class="th"> 
						<fmt:message key="train.list.table.idTrain"/>
					</span> 
				</c:if>
				
				<span class="th"> <fmt:message key="train.list.table.trainName"/></span>
				<span class="th"><fmt:message key="train.list.table.route"/></span>
				<span class="th"><fmt:message key="train.list.table.departureTime"/></span>
				<span class="th"><fmt:message key="train.list.table.destinationTime"/></span>
				<span class="th"><fmt:message key="train.list.table.basePrice"/></span>
				<span class="th"><fmt:message key="train.list.table.scheduleMode"/></span>
				<span class="th"><fmt:message key="train.list.table.departureDate"/></span>
			</div>
			
			<form class="tr" action="${urlTrainSave}" method="post" id="editForm">
			 	<c:if test="${not empty sessionScope.currentTrain}">	
					<span class="td">
						<input name="trainId" value="${sessionScope.currentTrain.id}" readonly>
					</span>	 
				</c:if>
				
				<span class="td">	
					<input name="trainName" value="${sessionScope.currentTrain.trainName}" maxlength="5" required >
				</span> 
				
				<span class="td"> 
					<select id="routeId" name="routeId" required>
						<c:forEach var="route" items="${routes}">
							
							<c:if test="${route.id != sessionScope.currentTrain.route.id}">
                                  <option value="${route.id}">${route.departure.stationName} - ${route.destination.stationName} </option>
                            </c:if>
                                 
                            <c:if test="${route.id == sessionScope.currentTrain.route.id}">
                                  <option value="${route.id}" selected>${route.departure.stationName} - ${route.destination.stationName} </option>     
                            </c:if>
                            
						</c:forEach>
					</select>
						
				</span> 
				
				<span class="td">
					<input name="departureTime" value="${sessionScope.currentTrain.departureTime}" type="time" required>
				</span>
				 
				<span class="td">	
					<input name="destinationTime" value="${sessionScope.currentTrain.destinationTime}" type="time" required>
				</span> 
				
				<span class="td">
					<input name="price" value="${sessionScope.currentTrain.price}" type="text" step="any" min="1" maxlength="6" pattern="(^[0-9]{0,3}$)|(^[0-9]{0,3}\.[0-9]{0,2}$)" validate="true" required>
				</span> 
				
				<span class="td"> 
					<select id="scheduleModeId" name="scheduleModeId" required>
						<c:forEach var="scheduleMode" items="${scheduleModes}">
					
								<c:if test="${scheduleMode.id != sessionScope.currentTrain.scheduleMode.id}">
                                    <option value="${scheduleMode.id}">${scheduleMode.description}</option>
                                </c:if>
                                    
                                <c:if test="${scheduleMode.id == sessionScope.currentTrain.scheduleMode.id}">
                                    <option value="${scheduleMode.id}" selected>${scheduleMode.description}</option>
                                </c:if>
							
						</c:forEach>
					</select>
				</span>
				
				<span class="td">
					<input name="departureDate" value="${sessionScope.currentTrain.departureDate}" type="date" min="${today}" required>
				</span> 
										
			</form>
			
		</div>
	</div>

	<div class="right-part">
		<div >
			<button class="button margintop20" formaction="${urlTrainSave}"form="editForm" >
				<fmt:message key="button.save" />
			</button>
			
			<c:if test="${not empty train.id}">
				<c:if test="${not trainCanBeDeleted}">
					<c:set var="disabled" value="disabled" />
				</c:if>
				
				<button class="button margintop20" formaction="${urlTrainDelete}" form="editForm" formmethod="post" ${disabled}>
					<fmt:message key="button.delete" />
				</button>
			</c:if>

			<button class="button margintop20" type="reset" form="editForm">
				<fmt:message key="button.reset" />
			</button>
			
			<button class="button margintop20" formaction="${urlTrainList}" form="editForm" formnovalidate>
				<fmt:message key="button.cancel" />
			</button>
			
			<button class="button margintop20" formaction="${urlCarriageEdit}"form="editForm" name="add">
				<fmt:message key="button.addCarriage" />
			</button>
			
			
			<button class="button margintop20" formaction="${urlCarriageEdit}"form="editForm" name="delete">
				<fmt:message key="button.deleteCarriage" />
			</button>
		</div>
	</div>
</section>