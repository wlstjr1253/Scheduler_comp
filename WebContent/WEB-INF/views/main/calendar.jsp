<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/jquery.mCustomScrollbar.css">
<script src="${pageContext.request.contextPath}/js/jquery.mCustomScrollbar.js"></script>
<script src="${pageContext.request.contextPath}/js/jquery.mCustomScrollbar.concat.min.js"></script>
<!-- S: main calendar -->
<div class="main calendar">
	<!-- S: bg -->
	<div class="bg">
		<!-- S: content -->
		<div class="content">
			<!-- S: calendar-control -->
			<div class="calendar-control text-center">
    			<ul class="clearfix">
      				<li>
			        	<a href="#" class="btn btn-primary prev-month">이전달</a>
			      	</li>
			      	
			      	<!-- S: sel-box -->
			      	<li class="sel-box">
			        	<div class="year-list">
				          <select name="year" id="year" class="form-control">
				          	<option value="${year - 1}">${year - 1}</option>
				            <option value="${year}" selected>${year}</option>
				            <option value="${year + 1}">${year + 1}</option>
				            <option value="${year + 2}">${year + 2}</option>
				            <option value="${year + 3}">${year + 3}</option>
				          </select>
        			  </div>
        			  <div class="text">년</div>
      			  </li>
      			  <!-- E: sel-box -->
      			  
      			  <!-- S: sel-box -->
      			  <li class="sel-box">
        		    <div>
          				<select name="month" id="month" class="form-control">
           				<c:forEach var="i" begin="1" end="12">
				            <option value="${i}" <c:if test="${i == month}">selected</c:if> >${i}</option>
            			</c:forEach>
          				</select>
        			</div>
        			<div class="text">월</div>
			      </li>
			      <!-- E: sel-box -->
			      
			      <li>
			        <a href="#" class="btn btn-primary next-month">다음달</a>
			      </li>
			      <li>
			        <a href="#" class="btn btn-danger today">오늘</a>
			      </li>
			    </ul>
			  </div>
			  <!-- E: calendar-control -->
			  

		  <!-- S: 캘린더 테이블 -->
		    <div class="row">
			  <div class="ajax-loading">
			  	<img src="${pageContext.request.contextPath}/imgs/curved-bar-spinner.svg">
			  </div>
			  <!-- S: table -->
		      <table class="table">
		        <thead>
		          <tr>
		            <th>월</th>
		            <th>화</th>
		            <th>수</th>
		            <th>목</th>
		            <th>금</th>
		            <th>토</th>
		            <th>일</th>
		          </tr>
		        </thead>
		        <tbody class="calendar-body">
		       	<tr>
		       		<c:forEach var="i" begin="${prevWeekMonday}" end="${prevLastOfDate}">
		     			<td class="prev-month">${i}</td>     		
		     		</c:forEach>
		         	<c:forEach var="i" items="${days}">
		         	<%-- <c:choose>
		     			<c:when test="${i.key == 1 && i.value >= 2}">
		         			날짜 시작점 찾기 (월~토)
				      		<c:forEach var="j" begin="1" end="${i.value - 2}">
				      		<td></td>
				      		</c:forEach>
				      		<c:set var="day" value="${i.value - 1}"  />
			      		</c:when>
			      	
				      	<c:when test="${i.key == 1 && i.value == 1}">
				      		날짜 시작점 찾기 (일요일)
				      		<c:forEach var="j" begin="1" end="6">
				      		<td></td>
				      		</c:forEach>
				      		<c:set var="day" value="${i.value - 1}"  />
				      	</c:when>
		     		</c:choose> --%>
		     		
		     		
		         	<c:choose>
		         	<c:when test="${i.value == 1 }">
		         		<td
		         		<c:if test="${date == i.key}">class="today"</c:if>
		         		>${i.key}</td>
		         	</tr>
		         	<tr>
		         	</c:when>
		         	
		         	<c:when test="${i.value <= 7 }">
		         		<td
		         		<c:if test="${date == i.key}">class="today"</c:if>
		         		>${i.key}</td>
		         	</c:when>
		         	</c:choose>
		         	
		         	</c:forEach>
		         	
		         	<c:forEach var="i" items="${nextMonthDays}">
		         		<td class="next-month">${i.key}</td>
		         	</c:forEach>
		         	</tr>
		        </tbody>
		      </table>
		      <!-- E: table -->
		  </div>
		  <!-- E: row -->
		</div>
		<!-- E: content -->
	</div>
	<!-- E: bg -->  
</div>
<!-- E: main calendar -->
	
	 <!-- S: modal -->
	 <div class="modal fade schedule-detail schedule-list" role="dialog">
	     <div class="modal-dialog modal-dialog-centered">
	         <div class="modal-content">
	             <!-- modal-header -->
	             <div class="modal-header">
	                 <h5 class="modal-title">등록된 <span class="day"></span>일 일정</h5>
	             </div>
	             
	             <!-- modal-body -->
	             <div class="modal-body comming-list">
	                 <!-- S: result-box -->
	                 <div class="result-box">
	                 	<ul>
	                 	
	 					</ul>
	                 </div>
	                 <!-- E: result-box -->
	             </div>
	
	             <!-- modal-footer -->
	             <div class="modal-footer">
	                 <button type="button" class="btn-modal-basic" data-dismiss="modal">닫기</button>
	             </div>
	         </div>
	     </div>
	 </div>
	 <!-- E: modal -->	 	 	 

	 <script>
	 Handlebars.registerHelper('isPrevWeekMon', function(prevMon,lastDay){
		// prevMon: 마지막 월요일, prevLastOfDate: 마지막 날짜
		if (prevMon == 0 ) { return; }
		
		var outputTd = "";
		for (var i=prevMon; i<=lastDay; i++) {
			outputTd += "<td class='prev-month'>"+i+"</td>";
		}
		return outputTd;
	 });
 
	 Handlebars.registerHelper('isSunday', function(value, options){
		 if (value == 1) { // 일요일인 경우
			 return options.fn(this);
		 } else {
			 return options.inverse(this);
		 }		 
	 });
	 
	 Handlebars.registerHelper('isToday', function(k, options) {
		 var outputClass = "";
		 var date = new Date();
		 var year = date.getFullYear();
		 var month = date.getMonth()+1;
		 var today = date.getDate();
		 if ($('#year option:selected').val() == year &&
			 $('#month option:selected').val() == month &&
			 k == today) {
			 outputClass = "today";
		 }
		 return outputClass;
	 })
	 </script>

	 <script id="calendarBody" type="text/x-handlebars-template">
	 <tr>
	 {{date}}
	 {{#isPrevWeekMon prevWeekMonday prevLastOfDate}}
	 {{isPrevWeekMon outputTd}}
     {{/isPrevWeekMon}}
	 {{#isToday date days as |value key|}}
	 {{isToday outputTd}}
	 {{/isToday}}
	 {{#each days as |value key|}}
		{{#isSunday value}}
		<td class="
			{{#isToday key}}
			{{isToday outputClass}}
			{{/isToday}}"
		>{{key}}</td>
		</tr>
		<tr>
		{{else}}
		<td class="
			{{#isToday key}}
			{{isToday outputClass}}
			{{/isToday}}"
		>{{key}}</td>
		{{/isSunday}}
     {{/each}}
	 {{#each nextMonthDays as |value key|}}
		<td class="prev-month">{{key}}</td>
  	 {{/each}}
     </tr>
	 </script>
	 
		 
	 <script>
	 Handlebars.registerHelper('isScAllDay', function(value, options){
		 if (value == 'Y') { // 하루종일인 경우
			 return options.fn(this);
		 } else {
			 return options.inverse(this);
		 }		 
	 });
	 
	 Handlebars.registerHelper('isSame', function(startDate, endDate, options) {
			var startDate = startDate;
			var endDate = endDate;
			if (startDate == endDate) {
				return options.fn(this);
			} else {
				return options.inverse(this);
			}
		})
		
	 Handlebars.registerHelper('isFav', function(scFav, options) {
		 if (scFav == 'Y') { // 즐겨찾기가 추가된 경우
			 return options.fn(this);
		 } 
		 if (scFav == 'N'){
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
	 
	<script id="schedulesDayModal" type="text/x-handlebars-template">
	{{#each schedules}}
		{{#isMine id ../user}}
		<li class="clearfix">
		{{else}}
		<li class="clearfix shared">
		{{/isMine}}
	    	<a href="${pageContext.request.contextPath}/sc/detailSchedule.do?sc_idx={{sc_idx}}">
	        	<!-- S: list-content -->
	        	<div class="list-content">
	            	<h4 class="title">{{sc_title}}</h4>
	                <p>날짜 : {{sc_startDate}} ~ {{sc_endDate}}</p>
	                {{#isScAllDay sc_all_day}}
					<p>시간 : 종일</p>
					{{else}}
					{{#isSame sc_startTime sc_endTime}}
					<p>시간 : {{sc_startTime}}</p>
					{{else}}
					<p>시간 : {{sc_startTime}} 부터 {{sc_endTime}}까지</p>
					{{/isSame}}
					{{/isScAllDay}}
	                <p>장소 : {{sc_place}}</p>
					{{#if sc_content}}
	                <p>메모<br>{{sc_content}}</p>
					{{/if}}
	            </div>
	            <!-- E: list-content -->
	        </a>
	        <!-- S: list-control -->
			{{#isMine id ../user}}
	        <div class="list-control">
				{{#isFav sc_favorite}}
				<a href="/Scheduler/record/favorite.do" class="added" data-scidx="{{sc_idx}}" data-fav="{{sc_favorite}}">
				{{else}}
	        	<a href="#" class="add-favorite" data-scidx="{{sc_idx}}" data-fav="{{sc_favorite}}">
				{{/isFav}}
	            	<span class="icon">
	                	<i class="fas fa-star"></i>
	                </span>
	            </a>
	            <a href="#" class="remove-list del-schedule" data-scidx="{{sc_idx}}">
	            	<span class="icon">
	                	<i class="fas fa-trash-alt"></i>
	                </span>
	            </a>
	         </div>
			 {{else}}
			 {{/isMine}}
	         <!-- E: list-control -->
	     </li>
	{{/each}}
	</script>
	 
  <script src="${pageContext.request.contextPath}/js/calendar.js"></script>
  <script src="${pageContext.request.contextPath}/js/ajaxAction/schedulesByDay.js"></script>
  <script src="${pageContext.request.contextPath}/js/ajaxAction/loadSchedule.js"></script>
  <script src="${pageContext.request.contextPath}/js/ajaxAction/scheduleDeleteCal.js"></script>
</body>
</html>