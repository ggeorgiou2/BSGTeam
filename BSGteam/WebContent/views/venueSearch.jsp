<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="java.util.*"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>



<div class="col-lg-7 col-md-10 col-md-push-3">
	
	<c:if test="${not empty venueList}">
		<script>
			setTimeout(function() {
				$('#mytab a[href=#Venues]').tab('show');
				window.location.href = '#venueResults';
			});
		</script>
		<div class="row">
			<div class="well bs-component">
				<h1>List of Users</h1>
				<div class="row">
					<div class="col-md-10">
						<table
							class="table table-hover table-responsive table-bordered table-condensed">
							<thead>
								<tr>
									<th><b>Users</b>
									<th><b>Venue</b>
								</tr>
								<%
									int count = 0;
										String color = "#F9EBB3";
										if (request.getAttribute("venueList") != null) {
											ArrayList al = (ArrayList) request.getAttribute("venueList");
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