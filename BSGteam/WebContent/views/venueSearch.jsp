<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="java.util.*"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>



<div class="col-lg-6 col-md-10 col-md-push-3">
	<c:if test="${empty piList}">
		
	</c:if>
	<c:if test="${not empty piList}">
		<script>
			setTimeout(function() {
				$('#mytab a[href=#Venues]').tab('show');
				window.location.href = '#venueResults';
			});
		</script>
		<div class="row">
			<div class="well bs-component">
				<h1>List of Venues</h1>
				<div class="row">
					<div class="col-md-10">
						<table
							class="table table-hover table-responsive table-bordered table-condensed">
							<thead>
								<tr>
									<th><b>VenueName</b>
									<th><b>Address</b>
									<th><b>URL</b>
									<th><b>Description</b>
								</tr>
								<%
									int count = 0;
										String color = "#F9EBB3";
										if (request.getAttribute("piList") != null) {
											ArrayList al = (ArrayList) request.getAttribute("piList");
											System.out.println(al);
											Iterator itr = al.iterator();
											while (itr.hasNext()) {

												if ((count % 2) == 0) {
													color = "#eeffee";
												}
												count++;
												ArrayList pList = (ArrayList) itr.next();
								%>
							
							<tbody class="table-hover">
								<tr>
									<td><%=pList.get(1)%></td>
									<td><%=pList.get(2)%></td>
									<td><%=pList.get(3)%></td>
									<td><%=pList.get(4)%></td>
								</tr>
								<%
									}
										}
										if (count == 0) {
								%>
								<tr>
									<td colspan=4 align="center" style="background-color: #eeffee"><b>No
											Record Found..</b></td>
								</tr>
								<%
									}
								%>
							
						</table>
					</div>
				</div>
			</div>
		</div>
	</c:if>
</div>