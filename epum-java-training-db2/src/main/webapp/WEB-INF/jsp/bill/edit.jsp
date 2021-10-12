
    <c:url var="urlBillList" value="/jsp/bill/list"/>
    <c:url var="urlBillSave" value="/jsp/bill/save">
                         <c:param name="billId" value="${param.billId}"/>  
            
               <c:param name="initialUserId" value="${param.initialUserId}"/>  
                <c:param name="initialCarriageId" value="${param.initialCarriageId}"/>
                 <c:param name="initialCarriageNumber" value="${param.initialCarriageNumber}"/>
                            <c:param name="initialPlace" value="${param.initialPlace}"/>
                           <c:param name="initialCost" value="${param.initialCost}"/>
                           <c:param name="initialCreationTime" value="${param.initialCreationTime}"/>
                           <c:param name="initialStatus" value="${param.initialStatus}"/>
                           <c:param name="initialLastName" value="${param.initialLastName}"/>  
                           <c:param name="initialFirstName" value="${param.initialFirstName}"/>   
                           <c:param name="initialPassport" value="${param.initialPassport}"/>   
                    </c:url>
 
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
					<c:when test="${(not empty sessionScope.currentBill) }">
					<fmt:message key="bill.list.table.title" />
							:
							<fmt:message key="edit.title.edit" />
					</c:when>
					<c:otherwise>
							<fmt:message key="bill.list.table.title" />
							:
							<fmt:message key="edit.title.create" />

					</c:otherwise>
				</c:choose>
			</div>
			
			<div class="tr">
				<c:if test="${not empty sessionScope.currentBill}">
					<span class="th"> 
						<fmt:message key="bill.list.table.idBill"/>
					</span> 
				</c:if>
				
				<span class="th"><fmt:message key="bill.list.table.user"/></span>
				<span class="th"><fmt:message key="bill.list.table.train"/></span>
				<span class="th"><fmt:message key="bill.list.table.carriageNumber"/></span>
				<span class="th"><fmt:message key="bill.list.table.place"/></span>
				<span class="th"><fmt:message key="bill.list.table.cost"/></span>
				<span class="th"><fmt:message key="bill.list.table.creationAt"/></span>
				<span class="th"><fmt:message key="bill.list.table.status"/></span>
			</div>
			
			<form class="tr" action="${urlBillSave}" method="post" id="editForm">
				<c:if test="${not empty sessionScope.currentBill}">
					<span class="td">
						<input name="billId" value="${sessionScope.currentBill.id}" readonly>
					</span> 
				</c:if>
				
				<span class="td"> 
					<select id="userId" name="userId" required>
						<c:forEach var="user" items="${users}">
							<c:if test="${user.id != sessionScope.currentBill.user.id}">
                                  <option value="${user.id}">${user.lastName} ${user.firstName} ${user.passport} </option>
                            </c:if>
                            <c:if test="${user.id == sessionScope.currentBill.user.id}">
                                  <option value="${user.id}" selected>${user.lastName} ${user.firstName} ${user.passport} </option>
                            </c:if>
						</c:forEach>
					</select>
				</span> 
				
				<span class="td">
						<input name="trainName" value="${sessionScope.currentBill.carriage.train.trainName}" readonly >
				</span> 
				
				<span class="td">	
						<input name="carriageNumber" value="${sessionScope.currentBill.carriage.carriageNumber}" readonly>	
				</span> 
				
				<span class="td">
						<input name="place" value="${sessionScope.currentBill.place}" readonly>
				</span> 
				
				<span class="td">
						<input name="cost" value="${sessionScope.currentBill.cost}" readonly>
				</span> 
				
				<span class="td">
					<input name="creationTime" value="${sessionScope.currentBill.creationTime}" type="datetime-local" readonly>
				</span> 
				
				<span class="td"> 
					<select id="status" name="status" required>
						<c:forEach var="status" items="${statuses}">
							<c:if test="${status != sessionScope.currentBill.status}">
                                  <option value="${status}">${status}</option>
                            </c:if>
                            <c:if test="${status == sessionScope.currentBill.status}">
                                 <option value="${status}" selected>${status}</option>
                            </c:if>
						</c:forEach>
					</select>
				</span>
				
				<input type="hidden" name="carriageId" value="${sessionScope.currentBill.carriage.id}" >		
			</form>
		</div>
	</div>

	<div class="right-part">
			<div >
				<button class="button margintop20" form="editForm" >
				<fmt:message key="button.save" />
				</button>
			
				<c:if test="${not empty bill.id}">
					<c:if test="${not billCanBeDeleted}">
						<c:set var="disabled" value="disabled" />
					</c:if>
					<button class="button margintop20" formaction="${urlBillDelete}" form="editForm"
						formmethod="post" ${disabled}>
						<fmt:message key="button.delete" />
					</button>
				</c:if>

		  		<button class="button margintop20" type="reset" form="editForm">
					<fmt:message key="button.reset" />
				</button>
				
				<button class="button margintop20" formaction="${urlBillList}" form="editForm" formnovalidate>
					<fmt:message key="button.cancel" />
				</button>
			</div>
	</div>
</section>