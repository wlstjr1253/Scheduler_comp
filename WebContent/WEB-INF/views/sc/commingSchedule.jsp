<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/material-design-iconic-font.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/jquery.mCustomScrollbar.css">
<script src="${pageContext.request.contextPath}/js/jquery.mCustomScrollbar.js"></script>
<script src="${pageContext.request.contextPath}/js/jquery.mCustomScrollbar.concat.min.js"></script>
<!-- main -->
<div class="main schedule-list">
	<!-- S: bg -->
	<div class="bg">
		<!-- S: content -->
		<div class="content">
			<!-- S: recent-list -->
			<section class="recent-list">
				<h2>눈 앞의 일</h2>
				<!-- S: today -->
				<article class="today">
					<h3>오늘 일정</h3>
					<ul>
						<c:if test="${empty list2 && empty sharedList2}">
						<li class="no-result">오늘 일정이 없습니다. <br> <i
							class="fas fa-calendar-times"></i></li>
						</c:if>
						
						<c:if test="${!empty list2}">
						<c:forEach var="sch" items="${list2}">
						<c:if test="${user == sch.id}">
						<li class="clearfix">
						</c:if>
						<c:if test="${user != sch.id}">
						<li class="clearfix shared">
						</c:if>
						<a href="detailSchedule.do?sc_idx=${sch.sc_idx}"> <!-- S: list-content -->
								<div class="list-content">
									<h4 class="title">${sch.sc_title}</h4>
									<p>날짜 : ${sch.sc_startDate} <c:if test="${sch.sc_startDate != sch.sc_endDate}">~  ${sch.sc_endDate}</c:if></p>
									<c:choose>
									<c:when test="${sch.sc_all_day != 'Y'}">
									<p>시간 : ${sch.sc_startTime} <c:if test="${sch.sc_startTime != sch.sc_endTime}">부터 ${sch.sc_endTime}까지</c:if></p>										
									</c:when>
									<c:when test="${sch.sc_all_day == 'Y'}">
									<p>시간 : 종일</p>
									</c:when>
									</c:choose>
									<p>장소 : ${sch.sc_place}</p>
									<c:if test="${!empty sch.sc_content}">
									<p>메모 : <br>${sch.sc_content}</p>
									</c:if>
								</div> <!-- E: list-content -->
						</a> 
							<c:if test="${user == sch.id}">
							<!-- S: list-control -->
							<div class="list-control">
								<c:choose>
								<c:when test="${sch.sc_favorite == 'Y'}">
								<a href="/Scheduler/record/favorite.do"
								class="added">
								</c:when>
								<c:otherwise>
								<a href="#" data-scidx="${sch.sc_idx}" data-toggle="modal" data-target=".fav-warn">
								</c:otherwise>
								</c:choose>
								 <span class="icon">
										<i class="fas fa-star"></i>
								</span>
								</a> 
								<a href="#" class="remove-list" data-toggle="modal" data-target=".del-sc" data-scidx="${sch.sc_idx}"> <span class="icon">
										<i class="fas fa-trash-alt"></i>
								</span>
								</a>
							</div> <!-- E: list-control -->
							</c:if>
							</li>
						</c:forEach>						
						</c:if>
					</ul>
				</article>
				<!-- E: today -->

				<!-- S: tomorrow -->
				<article class="tomorrow">
					<h3>내일 일정</h3>
					<ul>
						<c:if test="${empty list3 && empty sharedList3}">
						<li class="no-result">내일 일정이 없습니다.<br> <i
							class="fas fa-calendar-times"></i></li>
						</c:if>
						
						
						<c:if test="${!empty list3}">
						<c:forEach var="sch" items="${list3}">
						<c:if test="${user == sch.id }">
						<li class="clearfix">
						</c:if>
						<c:if test="${user != sch.id }">
						<li class="clearfix shared">
						</c:if>
						<a href="detailSchedule.do?sc_idx=${sch.sc_idx}"> <!-- S: list-content -->
								<div class="list-content">
									<h4 class="title">${sch.sc_title}</h4>
									<p>날짜 : ${sch.sc_startDate} <c:if test="${sch.sc_startDate != sch.sc_endDate}">~  ${sch.sc_endDate}</c:if></p>
									<c:choose>
									<c:when test="${sch.sc_all_day != 'Y'}">
									<p>시간 : ${sch.sc_startTime} <c:if test="${sch.sc_startTime != sch.sc_endTime}">부터 ${sch.sc_endTime}까지</c:if></p>										
									</c:when>
									<c:when test="${sch.sc_all_day == 'Y'}">
									<p>시간 : 종일</p>
									</c:when>
									</c:choose>
									<p>장소 : ${sch.sc_place}</p>
									<c:if test="${!empty sch.sc_content}">
									<p>메모 : <br>${sch.sc_content}</p>
									</c:if>
								</div> <!-- E: list-content -->
						</a> 
							<c:if test="${user == sch.id }">
							<!-- S: list-control -->
							<div class="list-control">
								<c:choose>
								<c:when test="${sch.sc_favorite == 'Y'}">
								<a href="/Scheduler/record/favorite.do"
								class="added">							</c:when>
								<c:otherwise>
								<a href="#" data-scidx="${sch.sc_idx}" data-toggle="modal" data-target=".fav-warn">
								</c:otherwise>
								</c:choose>
								<span class="icon">
										<i class="fas fa-star"></i>
								</span>
								</a> 
								<a href="#" class="remove-list" data-toggle="modal" data-target=".del-sc" data-scidx="${sch.sc_idx}"> 
								<span class="icon">
										<i class="fas fa-trash-alt"></i>
								</span>
								</a>
							</div> <!-- E: list-control -->
							</c:if>
							</li>
						</c:forEach>						
						</c:if>
						
						<c:if test="${!empty sharedList3}">
						<c:forEach var="sharedList3" items="${sharedList3}">
						<li class="clearfix shared"><a href="detailSchedule.do?sc_idx=${sharedList3.sc_idx}"> <!-- S: list-content -->
								<div class="list-content">
									<h4 class="title">${sharedList3.sc_title}</h4>
									<p>날짜 : ${sharedList3.sc_startDate} <c:if test="${sharedList3.sc_startDate != sharedList3.sc_endDate}">~  ${sharedList3.sc_endDate}</c:if></p>
									<c:choose>
									<c:when test="${sharedList3.sc_all_day != 'Y'}">
									<p>시간 : ${sharedList3.sc_startTime} <c:if test="${sharedList3.sc_startTime != sharedList3.sc_endTime}">부터 ${sharedList3.sc_endTime}까지</c:if></p>										
									</c:when>
									<c:when test="${sharedList3.sc_all_day == 'Y'}">
									<p>시간 : 종일</p>
									</c:when>
									</c:choose>
									<p>장소 : ${sharedList3.sc_place}</p>
									<c:if test="${!empty sharedList3.sc_content}">
									<p>메모 : <br>${sharedList3.sc_content}</p>
									</c:if>
								</div> <!-- E: list-content -->
							</a>
						 </li>
						</c:forEach>						
						</c:if>
					</ul>
				</article>
				<!-- E: tomorrow -->
			</section>
			<!-- E: recent-list -->

			<!-- S: comming-list -->
			<section class="comming-list">
				<h2>다가오고 있습니다.</h2>
				<div class="search-box">
					<p class="total-guide">총 ${count + sharedCount} 개의 일정이 등록 되었습니다.</p>
					<form id="searchSch">
						<input type="text" name="keyword"> <input type="submit"
							value="검색">
					</form>
				</div>
				<div class="ajax-loading">
					<img alt="로딩중" src="${pageContext.request.contextPath}/imgs/curved-bar-spinner.svg">
				</div>
				<ul>
					<c:if test="${empty list && empty sharedList}">
					<li class="no-result">등록된 일정이 없습니다.<br> <i
						class="fas fa-calendar-times"></i></li>
					</c:if>
					
					
					<c:if test="${!empty list}">
						<c:forEach var="sch" items="${list}">
						<c:if test="${user == sch.id}">
						<li class="clearfix">
						</c:if>
						<c:if test="${user != sch.id}">
						<li class="clearfix shared">
						</c:if>
							<a href="detailSchedule.do?sc_idx=${sch.sc_idx}"> <!-- S: list-content -->
								<div class="list-content">
									<h4 class="title">${sch.sc_title}</h4>
									<p>날짜 : ${sch.sc_startDate} <c:if test="${sch.sc_startDate != sch.sc_endDate}">~  ${sch.sc_endDate}</c:if></p>
									<c:choose>
									<c:when test="${sch.sc_all_day != 'Y'}">
									<p>시간 : ${sch.sc_startTime} <c:if test="${sch.sc_startTime != sch.sc_endTime}">부터 ${sch.sc_endTime}까지</c:if></p>
									</c:when>
									<c:when test="${sch.sc_all_day == 'Y'}">
									<p>시간 : 종일</p>
									</c:when>
									</c:choose>
									<p>장소 : ${sch.sc_place}</p>
									<c:if test="${!empty sch.sc_content}">
									<p>메모 : <br>${sch.sc_content}</p>
									</c:if>
								</div> <!-- E: list-content -->
							</a> 
							<c:if test="${user == sch.id }">
							<!-- S: list-control -->
							<div class="list-control">
								<c:choose>
								<c:when test="${sch.sc_favorite == 'Y'}">
								<a href="/Scheduler/record/favorite.do"
								class="added">
								</c:when>
								<c:otherwise>
								<a href="#" data-scidx="${sch.sc_idx}" data-toggle="modal" data-target=".fav-warn">
								</c:otherwise>
								</c:choose>
									<span class="icon">
										<i class="fas fa-star"></i>
									</span>
								</a>
								<a href="#" class="remove-list" data-toggle="modal" data-target=".del-sc" data-scidx="${sch.sc_idx}">
									<span class="icon">
										<i class="fas fa-trash-alt"></i>
									</span>
								</a>
							</div>
							<!-- E: list-control -->
							</c:if>
						</li>
						</c:forEach>
					</c:if>
					
					<c:forEach var="shared" items="${sharedList}">
					<li class="clearfix shared">
						<a href="detailSchedule.do?sc_idx=${shared.sc_idx}"> 
						<!-- S: list-content -->
							<div class="list-content">
								<h4 class="title">[공유됨] ${shared.sc_title}</h4>
								<p>날짜 : ${shared.sc_startDate} <c:if test="${shared.sc_startDate != shared.sc_endDate}">~  ${shared.sc_endDate}</c:if></p>
								<c:choose>
								<c:when test="${sch.sc_all_day != 'Y'}">
								<p>시간 : ${shared.sc_startTime} <c:if test="${shared.sc_startTime != shared.sc_endTime}">부터 ${shared.sc_endTime}까지</c:if></p>
								</c:when>
								<c:when test="${shared.sc_all_day == 'Y'}">
								<p>시간 : 종일</p>
								</c:when>
								</c:choose>
								<p>장소 : ${shared.sc_place}</p>
								<c:if test="${!empty shared.sc_content}">
								<p>메모 : <br>${shared.sc_content}</p>
								</c:if>
							</div> <!-- E: list-content -->
						</a>
					</li>
					</c:forEach>	
				</ul>
			</section>
			
			<script>
				Handlebars.registerHelper('isSame', function(startDate, endDate, options) {
					var startDate = startDate;
					var endDate = endDate;
					if (startDate == endDate) {
						return options.fn(this);
					} else {
						return options.inverse(this);
					}
				})
				
				Handlebars.registerHelper('getContext', function(sc_content, options){
					if (sc_content) {
						return options.fn(this);
					} else {
						return options.inverse(this);
					}
				})
				
				Handlebars.registerHelper('isAllDay', function(sc_all_day, options) {
					if (sc_all_day == 'Y') {
						return options.fn(this);
					} else {
						return options.inverse(this);
					}
				})
				
				Handlebars.registerHelper('isFavorite', function(sc_favorite, options) {
					if (sc_favorite == 'Y') {
						return options.fn(this);
					} else {
						return options.inverse(this);
					}
				})
				
				Handlebars.registerHelper('isMine', function(id, user, options) {
		 			if (id == user) { // 글 작성자와 사용자 아이디가 같을 경우
			 			return options.fn(this);
		 			} 
		 			if (id != user){
			 			return options.inverse(this);
		 			}
	 			})
			</script>
				
			<script id="come-list" type="text/x-handlebars-template">
				{{#list}}
				<li
					{{#isMine id ../user}}
					class="clearfix"
					{{else}}
					class="clearfix shared"
					{{/isMine}}
				>
					<a href="detailSchedule.do?sc_idx={{sc_idx}}"> <!-- S: list-content -->
						<div class="list-content">
							<h4 class="title">{{sc_title}}</h4>
							{{#isSame sc_startDate sc_endDate}}
							<p>날짜 : {{sc_startDate}}</p>
							{{else}} 
							<p>날짜 : {{sc_startDate}} ~ {{sc_endDate}}</p>
							{{/isSame}}

							{{#isAllDay sc_all_day}}
							<p>시간 : 종일 </p>
							{{else}}
							{{#isSame sc_startTime sc_endTime}}
							<p>시간 : {{sc_startTime}}</p>
							{{else}}
							<p>시간 : {{sc_startTime}} 부터 {{sc_endTime}} 까지</p>
							{{/isSame}}
							{{/isAllDay}}

							<p>장소 : {{sc_place}}</p>
							{{#getContext sc_content}}
							<p>메모 : <br>{{sc_content}}</p>
							{{/getContext}}
						</div> <!-- E: list-content -->
					</a> 
					{{#isMine id ../user}}
					<!-- S: list-control -->
					<div class="list-control">
						{{#isFavorite sc_favorite}}
						<a href="/Scheduler/record/favorite.do" class="added">
						{{else}}
						<a href="#" data-scidx="{{sc_idx}}" data-toggle="modal" data-target=".fav-warn">
						{{/isFavorite}}
							<span class="icon">
								<i class="fas fa-star"></i>
							</span>
						</a>
						<a href="#" class="remove-list" data-toggle="modal" data-target=".del-sc" data-scidx="{{sc_idx}}">
							<span class="icon">
								<i class="fas fa-trash-alt"></i>
							</span>
						</a>
					</div>
					<!-- E: list-control -->
					{{else}}
					{{/isMine}}
				</li>
				{{/list}}
			</script>
			<!-- E: comming-list -->
				
			<!-- S: sharedComeList -->
			<script id="sharedComeList" type="text/x-handlebars-template">
			{{#sharedList}}
				<li class="clearfix shared">
					<a href="detailSchedule.do?sc_idx={{sc_idx}}"> <!-- S: list-content -->
						<div class="list-content">
							<h4 class="title">[공유됨] {{sc_title}}</h4>
							{{#isSame sc_startDate sc_endDate}}
							<p>날짜 : {{sc_startDate}}</p>
							{{else}} 
							<p>날짜 : {{sc_startDate}} ~ {{sc_endDate}}</p>
							{{/isSame}}

							{{#isAllDay sc_all_day}}
							<p>시간 : 종일 </p>
							{{else}}
							{{#isSame sc_startTime sc_endTime}}
							<p>시간 : {{sc_startTime}}</p>
							{{else}}
							<p>시간 : {{sc_startTime}} 부터 {{sc_endTime}} 까지</p>
							{{/isSame}}
							{{/isAllDay}}

							<p>장소 : {{sc_place}}</p>
							{{#getContext sc_content}}
							<p>메모 : <br>{{sc_content}}</p>
							{{/getContext}}
						</div> <!-- E: list-content -->
					</a>
				</li>
				{{/sharedList}}
			</script>
			<!-- E: sharedComeList -->

			<c:if test="${!empty list || !empty sharedList}">
			<!-- S: pagination -->
			<ul class="pagination justify-content-center comming">
				${pagingHtml}
			</ul>
			<!-- E: pagination -->
			</c:if>
		</div>
		<!-- E: content -->
	</div>
	<!-- E: bg -->
</div>


<!-- S: fav-warn -->
<div class="modal fade warn-modal fav-warn" role="dialog">
	<div class="modal-dialog modal-dialog-centered">
		<div class="modal-content">
			<!-- S: modal-header -->
			<div class="modal-header">
				<h5 class="modal-title">즐겨찾기에 추가 하시겠습니까?</h5>
			</div>
			<!-- E: modal-header -->

			<!-- S: modal-body -->
			<div class="modal-body">
				<p>즐겨찾기에 등록 하시겠습니까?</p>
			</div>
			<!-- E: modal-body -->

			<!-- S: modal-footer -->
			<div class="modal-footer align-center">
				<button type="button" class="btn btn-secondary" data-dismiss="modal">취소</button>
				<button type="button" class="btn btn-primary add-favo">추가</button>
			</div>
			<!-- E: modal-footer -->
		</div>
	</div>
</div>
<!-- E: fav-warn -->

<!-- S: del-sc -->
<div class="modal fade warn-modal del-sc" role="dialog">
	<div class="modal-dialog modal-dialog-centered">
		<div class="modal-content">
			<!-- S: modal-header -->
			<div class="modal-header">
				<h5 class="modal-title">삭제 하시겠습니까?</h5>
			</div>
			<!-- E: modal-header -->

			<!-- S: modal-body -->
			<div class="modal-body">
				<p></p>
			</div>
			<!-- E: modal-body -->

			<!-- S: modal-footer -->
			<div class="modal-footer align-center">
				<button type="button" class="btn btn-secondary" data-dismiss="modal">취소</button>
				<button type="button" class="btn btn-danger del-schedule">삭제</button>
			</div>
			<!-- E: modal-footer -->
		</div>
	</div>
</div>
<!-- E: del-sc -->

<script>
   $('.recent-list article ul').mCustomScrollbar();
</script>

<script src="${pageContext.request.contextPath}/js/search.schedule.js"></script>
<script src="${pageContext.request.contextPath}/js/ajaxAction/scheduleDeleteCal.js"></script>