<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
 
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
  			<caption><fmt:message key="carriage.list.table.title"/></caption>
        		<tr>
            		<th><fmt:message key="carriage.list.table.id_carriage"/></th>
            		<th><fmt:message key="carriage.list.table.trainId"/></th>
            		<th><fmt:message key="carriage.list.table.carriageNumber"/></th>
            		<th><fmt:message key="carriage.list.table.carriageType"/></th>
            	<!--<td>&nbsp;</td>  -->
        		</tr>
        		<c:forEach var="carriage" items="${carriages}">
            		<tr>
                		<td class="content">${carriage.id}</td>
                		<td class="content">${carriage.train.id}</td>
                		<td class="content">${carriage.carriageNumber}</td>
                		<td class="content">${carriage.carriageType}</td>
            		</tr>
        	</c:forEach>
    	</table>
    
    	<c:url var="urlCarriageEdit" value="/jsp/carriage/edit"/>  	
    	<c:url var="urlTrainList" value="/jsp/train/list"/>
  	</div>

  	<div class="right-part">
  		<form>
     		<button class="button" formaction="${urlTrainList}"  type="submit"><fmt:message key="button.add"/></button>
     	</form>
     	
     	<form>
     		<button class="button" formaction="${urlTrainList}"  type="submit"><fmt:message key="button.delete"/></button>
     	</form>
  	</div>

</section>
