<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="java.util.*"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<div class="col-lg-6 col-md-10 col-md-push-3">
	<!-- 	<div class="row"> -->
	<c:if test="${empty twList}">
		<script>
			setTimeout(function() {
				$('#mytab a[href=#twitter]').tab('show');
				window.location.href = '#venueResults'
			});
		</script>
	</c:if>
	<!-- 	</div> -->
	<c:if test="${not empty twList}">
		<div class="row">
			<div class="well bs-component">
				<h1>List of Tweets</h1>
				<div class="row">
					<div class="col-md-10">
						<table
							class="table table-hover table-responsive table-bordered table-condensed">
							<thead>
								<tr>
									<th><b>Picture</b>
									<th><b>Twitter Username</b>
									<th><b>Location</b>
									<th><b>Description</b>
									<th><b>Tweet</b>
									<th><b>ReTweet</b>
								</tr>
								<%
									int count = 0;
										String color = "#F9EBB3";
										if (request.getAttribute("twList") != null) {
											ArrayList tl = (ArrayList) request.getAttribute("twList");
											System.out.println(tl);
											Iterator itr = tl.iterator();
											while (itr.hasNext()) {

												if ((count % 2) == 0) {
													color = "#eeffee";
												}
												count++;
												ArrayList tList = (ArrayList) itr.next();
								%>
							</thead>
							<tbody class="table-hover">
								<tr>
									<td><img src="<%=tList.get(1)%>" height="100" width="100"></td>
									<td><%=tList.get(2)%></td>
									<td><%=tList.get(3)%></td>
									<td><%=tList.get(4)%></td>
									<td><%=tList.get(5)%></td>
									<td><%=tList.get(6)%></td>
								</tr>
								<%
									}
										}
										if (count == 0) {
								%>
								<tr>
									<td colspan=7 align="center" style="background-color: #eeffee"><b>No
											Record Found..</b></td>
								</tr>
								<%
									}
								%>
							
						</table>
					</div>
				</div>
			</div>
	</c:if>
</div>