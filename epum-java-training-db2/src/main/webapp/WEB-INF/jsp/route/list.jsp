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
  		
  		<caption><fmt:message key="route.list.table.title"/></caption>
        
        <tr>
            <th><fmt:message key="route.list.table.id_route"/></th>
            <th><fmt:message key="route.list.table.departure"/></th>
            <th><fmt:message key="route.list.table.destination"/></th>
            <td>&nbsp;</td>
        </tr>
        
        	<c:forEach var="route" items="${routes}">
            	<tr>
                	<td class="content">${route.id}</td>
                	<td class="content">${route.departure.stationName}</td>
                	<td class="content">${route.destination.stationName}</td>
                	<td class="empty">
                    	<c:url var="urlRouteEdit" value="/jsp/route/edit">
                           <c:param name="routeId" value="${route.id}"/>
                           <c:param name="departureStationId" value="${route.departure.id}"/>
                           <c:param name="destinationStationId" value="${route.destination.id}"/>
                    	</c:url>
                    
                    	<a href="${urlRouteEdit}" class="edit"></a>
                	</td>
            	</tr>
        	</c:forEach>
    	</table>
     
     <c:url var="urlRouteEdit" value="/jsp/route/edit"/>
     	
  	</div>

  	<div class="right-part">
  		<form>
     		<button class="button" formaction="${urlRouteEdit}"  type="submit"><fmt:message key="button.add"/></button>
     	</form>
  	</div>

</section>
