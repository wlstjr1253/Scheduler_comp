<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<form action="addScheduleData.do" method="post" class="add-schedule">
	<fieldset>
		<legend>
			새로운 일정 등록 <span class="icon"><i class="far fa-edit"></i></span>
		</legend>
		<%-- <input type="hidden" name="sc_idx" value="${scIdx}"> --%>
		<ul>
			<li class="form-group"><label> <span class="title">제목</span>
					<input type="text" class="required form-control" required
					aria-required="true" name="sc_title" maxlength="50"
					autocomplete="off"> <i class="form-group__bar"></i>
			</label></li>
			<li class="form-group"><label> <span class="title">장소</span>
					<input type="text" class="form-control" name="sc_place"
					maxlength="20" autocomplete="off"> <i
					class="form-group__bar"></i>
			</label></li>
			<li class="all-day no-full"><label> <span class="title">하루
						종일</span> <!-- switch bbtn -->
					<div class="toggle-switch toggle-switch--blue">
						<input type="hidden" value="N" name="sc_all_day"> <input
							type="checkbox" class="toggle-switch__checkbox"> <i
							class="toggle-switch__helper"></i>
					</div>
			</label></li>
			<li class="form-group no-full start-time"><label> <span
					class="title">시작일</span> 
					<input type="text" name="startDate"
					class="datepicker-here print-today" data-language="ko"
					autocomplete="off" required> 
					<input type="text" name="startTime"
					class="timepicker" autocomplete="off" required>
					<input type="hidden" name="startSecond">
			</label></li>
			<li class="form-group no-full end-time"><label> <span
					class="title">종료일</span> <input type="text" name="endDate"
					class="datepicker-here print-today" data-language="ko"
					autocomplete="off" required> <input type="text" name="endTime"
					class="timepicker" autocomplete="off" required>
					<input type="hidden" name="endSecond">
			</label></li>
			<li class="form-group no-full alarm-set">
				<!-- <label>
								<span class="title">알람 설정</span>
								<div class="toggle-switch toggle-switch--green">
									<input type="checkbox" class="toggle-switch__checkbox" checked="checked">
									<i class="toggle-switch__helper"></i>
								</div>
							</label> -->
			</li>
			<li class="no-full alarm"><label> <span
					class="title strong">알람 시간</span> <select name="alarmTime" id=""
					class="sel-box">
						<c:forEach var="i" begin="1" end="24">
						<option value="${i}"
							<c:if test="${i == 1}">selected</c:if>
						>${i}시간 전</option>
						</c:forEach>
				</select>
			</label></li>
			<li class="memo"><label> <span class="title">메모</span> <textarea
						name="sc_content" id="memo" cols="30" rows="10"
						placeholder="메모를 입력하세요."></textarea>
			</label></li>
			<jsp:include page="shareMember.jsp" />
		</ul>
		<div class="btn-box">
			<ul class="btn-list">
				<li><a href="#" class="btn btn-basic" data-toggle="modal" data-target="#back_warn_modal">뒤로</a></li>
				<li><input type="submit" value="저장" class="submit-input"></li>
			</ul>
		</div>
	</fieldset>
</form>
<script>

$('.timepicker').wickedpicker({
	twentyFour : true
});

var date = new Date();
var year = date.getFullYear();
var month = date.getMonth()+1;
if (month < 10) {
	month = "0"+month;
}
var today = date.getDate();
if (today < 10) {
	today = "0"+today;
}
$('.print-today').val(year + '-' + month + '-' + today);
</script>