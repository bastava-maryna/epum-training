<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
 
 <section class="main-form">
  	<div class="left-part">
  	
  		<%@ include file="/WEB-INF/jspf/managermenu.jspf"%>
  	
  	</div>


  	<div class="top-part">	
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
  	<caption><fmt:message key="station.list.table.title"/></caption>
        <tr>
            <th><fmt:message key="station.list.table.id_station"/></th>
            <th><fmt:message key="station.list.table.station_name"/></th>
            <td>&nbsp;</td>
        </tr>
        <c:forEach var="station" items="${stations}">
            <tr>
                <td class="content">${station.id}</td>
                <td class="content">${station.stationName}</td>
                <td class="empty">
                    <c:url var="urlStationEdit" value="/jsp/station/edit">
                        <c:param name="id" value="${station.id}"/>
                    </c:url>
                    
                    <a href="${urlStationEdit}" class="edit"></a>
                </td>
            </tr>
        </c:forEach>
    </table>
    
    <c:url var="urlStationEdit" value="/jsp/station/edit"/>
    
  	
  	
  	</div>

  	<div class="right-part">
  	 <form>
     	<button class="button" formaction="${urlStationEdit}"  type="submit"><fmt:message key="button.add"/></button>
     </form>
  	</div>

</section>
