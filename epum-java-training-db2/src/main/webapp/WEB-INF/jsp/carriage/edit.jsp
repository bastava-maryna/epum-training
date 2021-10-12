<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!--   
 <c:if test="${empty carriage}"><jsp:useBean id="carriage" class="by.epum.training.db.entity.Carriage"/></c:if>
   <c:if test="${empty trains}"><jsp:useBean id="train" class="by.epum.training.db.entity.Train"/></c:if>
 --> 
 
    <c:url var="urlCarriageList" value="/jsp/carriage/list"/>
    <c:url var="urlCarriageSave" value="/jsp/carriage/save"/>
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
					<c:when test="${not empty currentCarriage}">
					<fmt:message key="carriage.list.table.title" />
							:
							<fmt:message key="edit.title.edit" />
					</c:when>
					<c:otherwise>
						<c:if test="${not empty availableCarriageNumbers}">
							<fmt:message key="carriage.list.table.title" />
							:
							<fmt:message key="edit.title.create" />
						</c:if>
						<c:if test="${empty availableCarriageNumbers}">
							<fmt:message key="carriage.list.table.title" />
							:
							<fmt:message key="edit.title.delete" />
						</c:if>
					</c:otherwise>
				</c:choose>
			</div>
			
			<div class="tr">
				<span class="th"> <fmt:message key="carriage.list.table.id_carriage" /></span> 
				<span class="th"> <fmt:message key="carriage.list.table.trainId" /></span>
				<span class="th"> <fmt:message key="carriage.list.table.carriageNumber" /></span>
				<c:if test="${not empty availableCarriageNumbers}">
					<span class="th"> <fmt:message key="carriage.list.table.carriageType" /></span>
				</c:if>
			</div>
			
			<form class="tr" action="${urlCarriageSave}" method="post" id="editForm">
				<span class="td">
					 <c:if test="${not empty currentCarriage or not empty delete}">
						<input name="carriageId" value="${currentCarriage.id}" readonly>
					</c:if>
				</span> 
				
				<span class="td"> 
					<input name="trainId" value="${trainId}" readonly>                       
				</span> 
				
				<c:if test="${not empty availableCarriageNumbers}">
					<span class="td">
						<select id="cariageNumber" name="carriageNumber" required>
							<c:forEach var="carriageNumber" items="${availableCarriageNumbers}">
									<c:if test="${carriageNumber != currentCarriageNumber}">
                                        <option value="${carriageNumber}">${carriageNumber}</option>
                                    </c:if>
                                    <c:if test="${carriageNumber == currentCarriageNumber}">
                                        <option value="${carriageNumber}" selected>${carriageNumber}</option>
                                    </c:if>
						</c:forEach>
						</select>
					</span>
						
					<span class="td"> 
						<select id="carriageType" name="carriageType" required>
							<c:forEach var="carriageType" items="${carriageTypes}">
								<c:if test="${carriage.id != currentCarriage.id}">
                                      <option value="${carriageType}">${carriageType}</option>
                                </c:if>
                                
                                <c:if test="${carriage.id == currentCarriage.id}">
                                     <option value="${carriageType}" selected>${carriageType}</option>
                                </c:if>
							
							</c:forEach>
						</select>
					</span>	
				</c:if>
						
				<c:if test="${not empty emptyCarriagesInTrain}">
					<span class="td">
						<select id="cariageId" name="carriageId" required>
							<c:forEach var="carriage" items="${emptyCarriagesInTrain}">
									<c:if test="${carriageNumber != currentCarriageNumber}">
                                        <option value="${carriage.id}">${carriage.carriageNumber}</option>
                                    </c:if>
                                    
                                    <c:if test="${carriageNumber == currentCarriageNumber}">
                                        <option value="${carriage.id}" selected>${carriage.carriageNumber} ${carriage.carriageType}</option>
                                    </c:if>
							</c:forEach>
						</select>				
                     </span>	
				</c:if>
			</form>
		</div>
	</div>

	<div class="right-part">
		<div >
			 <c:if test="${not empty currentCarriage}">
				<button class="button margintop20" form="editForm" >
					<fmt:message key="button.save" />
				</button>
					
				<c:if test="${not carriageCanBeDeleted}">
					<c:set var="disabled" value="disabled" />
				</c:if>
				
				<button class="button margintop20" formaction="${urlCarriageDelete}" form="editForm"
					formmethod="post" ${disabled}>
					<fmt:message key="button.delete" />
				</button>
			</c:if>
			
			<c:if test="${not empty availableCarriageNumbers}">
				<button class="button margintop20" form="editForm" name="add" value="add" formaction="${urlCarriageSave}">
					<fmt:message key="button.add" />
				</button>
			</c:if>
			
			<c:if test="${empty availableCarriageNumbers}">
				<button class="button margintop20" form="editForm" name="delete"  value="delete" formaction="${urlCarriageDelete}">
					<fmt:message key="button.delete" />
				</button>
			</c:if>
			
			<button class="button margintop20" type="reset" form="editForm">
				<fmt:message key="button.reset" />
			</button>
			
			<button class="button margintop20" formaction="${urlCarriageList}" form="editForm" formnovalidate>
				<fmt:message key="button.cancel" />
			</button>
		</div>
	</div>
</section>