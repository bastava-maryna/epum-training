<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
 <section class="main-form">
  	
 <div class="left-part">
 
</div>
  	<div class="top-part" >
		<div>
			<c:if test="${not empty errors}">
				<p class="error">
					<fmt:message key="error" />
				</p>
				<c:forEach var="error" items="${errors}">
					<p class="error">
						<fmt:message key="${error.key}" />
					</p>
				</c:forEach>
			</c:if>
			<c:if test="${not empty param.message}">
				<p class="ok">
					<fmt:message key="${param.message}" />
				</p>
			</c:if>
		</div>
	</div>

  	<div class="down-part">
  		<div class="main-img">
  		<c:url var="mainImg" value="/images/main1.png"/>
  		<img class="main-img" src="${mainImg}" alt="#">
  		</div>
  		<div><h1 class="start"><fmt:message key="home.start"/></h1></div>
  	</div>
  	

  	<div class="right-part">
  	</div>

</section>
