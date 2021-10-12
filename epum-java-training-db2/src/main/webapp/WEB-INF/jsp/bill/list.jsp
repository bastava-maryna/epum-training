 <%@ taglib prefix="func" uri="/WEB-INF/func.tld" %>
 
 <jsp:useBean id="now" class="java.util.Date"/>
 
 <fmt:formatDate var="nowDate" value="${now}" pattern="yyyy-MM-dd"/> 
 <fmt:formatDate var="nowTime" value="${now}" pattern="HH:mm"/> 
 
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
  		<table class="table-list">
  		<caption><fmt:message key="bill.list.table.title"/></caption>
        	<tr>
            	<th><fmt:message key="bill.list.table.idBill"/></th>
            	<th><fmt:message key="bill.list.table.user"/></th>
            	<th><fmt:message key="bill.list.table.train"/></th>
             	<th><fmt:message key="bill.list.table.carriageNumber"/></th>
             	<th><fmt:message key="bill.list.table.place"/></th>
             	<th><fmt:message key="bill.list.table.cost"/></th>
              	<th><fmt:message key="bill.list.table.creationAt"/></th>
               	<th><fmt:message key="bill.list.table.status"/></th>
            
            	<td>&nbsp;</td>
        	</tr>
        	
        	<c:forEach var="bill" items="${bills}">
            	<tr>
                	<td class="content">${bill.id}</td>
                	<td class="content">${bill.user.lastName} ${bill.user.firstName} ${bill.user.passport} </td>
                 	<td class="content">${bill.carriage.id} ${bill.carriage.train.trainName}</td>
                 	<td class="content">${bill.carriage.carriageNumber}</td>
                	<td class="content">${bill.place}</td>
                	<td class="content">${bill.cost}</td>
               		<td class="content"> ${ func:formatLocalDateTime(bill.creationTime, "yyyy-MM-dd HH:mm" )}</td>
                	<td class="content">${bill.status}</td>
                	<td class="empty">
                    	<c:url var="urlBillEdit" value="/jsp/bill/edit">
                           <c:param name="billId" value="${bill.id}"/>
                           <c:param name="userId" value="${bill.user.id}"/>
                           <c:param name="carriageId" value="${bill.carriage.id}"/>
                           <c:param name="place" value="${bill.place}"/>
                           <c:param name="cost" value="${bill.cost}"/>
                           <c:param name="status" value="${bill.status}"/>
                           <c:param name="creationTime" value="${bill.creationTime}"/>
                           <c:param name="lastName" value="${bill.user.lastName}"/>
                           <c:param name="firstName" value="${bill.user.firstName}"/>
                           <c:param name="passport" value="${bill.user.passport}"/>
                           <c:param name="carriageNumber" value="${bill.carriage.carriageNumber}"/>
                           <c:param name="trainName" value="${bill.carriage.train.trainName}"/>   
                    	</c:url>
             <c:if test="${ bill.carriage.train.departureDate gt nowDate or (bill.carriage.train.departureDate eq nowDate and bill.carriage.train.departureTime gt nowTime)}">       
                    <a href="${urlBillEdit}" class="edit" disabled="disabled"></a>
             </c:if>       
                	</td>
            	</tr>
        	</c:forEach>
    	</table>
    	
    	<c:url var="urlBillEdit" value="/jsp/bill/edit"/>
  	</div>

  	<div class="right-part">
  	</div>

</section>
