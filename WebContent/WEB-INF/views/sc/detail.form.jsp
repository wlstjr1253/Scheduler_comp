<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> 
<form class="modify-schedule">
	<fieldset>
		<legend>
			등록된 일정 <span class="icon"><i class="far fa-edit"></i></span>
		</legend>
		<input type="hidden" name="sc_idx" value="${schedule.sc_idx}">
		<ul>
			<li class="form-group"><label> <span class="title">제목</span>
					<input type="text" class="required form-control" required
					aria-required="true" name="sc_title" maxlength="50"
					autocomplete="off" value="${schedule.sc_title}"> <i class="form-group__bar"></i>
			</label></li>
			<li class="form-group"><label> <span class="title">장소</span>
					<input type="text" class="form-control" name="sc_place"
					maxlength="20" autocomplete="off" value="${schedule.sc_place}"> <i
					class="form-group__bar"></i>
			</label></li>
			<li class="all-day no-full"><label> <span class="title">하루
						종일</span> <!-- switch bbtn -->
					<div class="toggle-switch toggle-switch--blue">
					<c:if test="${schedule.sc_all_day == 'N'}">
					<input type="hidden" value="N" name="sc_all_day"> 
					<input type="checkbox" class="toggle-switch__checkbox">
					</c:if>
					<c:if test="${schedule.sc_all_day == 'Y'}">
					<input type="hidden" value="Y" name="sc_all_day">
					<input type="checkbox" class="toggle-switch__checkbox" checked>
					</c:if>
						 <i class="toggle-switch__helper"></i>
					</div>
			</label></li>
			<li class="form-group no-full start-time"><label> <span
					class="title">시작일</span> <input type="text" name="startDate"
					class="datepicker-here print-today" data-language="ko"
					autocomplete="off" value="${schedule.sc_startDate}"> <input type="text" name="startTime"
					class="timepicker start-time" autocomplete="off" value="${schedule.sc_startTime}">
					<input type="hidden" name="startSecond">
			</label></li>
			<li class="form-group no-full end-time"><label> <span
					class="title">종료일</span> <input type="text" name="endDate"
					class="datepicker-here print-today" data-language="ko"
					autocomplete="off" value="${schedule.sc_endDate}"> <input type="text" name="endTime"
					class="timepicker end-time" autocomplete="off" value="${schedule.sc_endTime}">
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
			<li class="no-full alarm">
				<label> 
					<span class="title strong">알람 시간</span>
					<select name="alarmTime" class="sel-box">
						<c:forEach var="i" begin="1" end="24">
						<option value="${i}"
							<c:if test="${schedule.al_timer == i}">
							selected
							</c:if>
						>${i}시간 전
						</option>
						</c:forEach> 
				</select>
			</label></li>
			<li class="memo"><label> <span class="title">메모</span> <textarea
						name="sc_content" id="memo" cols="30" rows="10"
						placeholder="메모를 입력하세요.">${schedule.sc_content}</textarea>
			</label></li>
			<jsp:include page="shareMember.jsp" />
		</ul>
		<div class="btn-box">
			<ul class="btn-list">
				<li><a href="#" class="btn btn-basic" data-toggle="modal" data-target="#back_warn_modal">뒤로</a></li>
				<c:if test="${loginUser == writer.id}">
				<li><a href="#" class="btn btn-danger" data-toggle="modal" data-target=".del-sc">삭제</a></li>
				<li><input type="submit" value="수정" class="submit-input btn btn-primary"></li>
				</c:if>
			</ul>
		</div>
	</fieldset>
</form>

<!-- S: complete-modal -->
<div class="modal fade complete-modal" role="dialog">
	<div class="modal-dialog modal-dialog-centered">
		<div class="modal-content">

			<!-- S: modal-body -->
			<div class="modal-body">
				<p class="message"></p>
			</div>
			<!-- E: modal-body -->

			<!-- S: modal-footer -->
			<div class="modal-footer align-center">
				<button type="button" class="btn btn-primary"
					onclick="location.replace('${pageContext.request.contextPath}/main/main.do');">확인</button>
			</div>
			<!-- E: modal-footer -->
		</div>
	</div>
</div>
<!-- E: complete-modal -->


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
	function printTime(){
		var startTime = $('.timepicker.start-time').val();
		$('.timepicker.start-time').wickedpicker({
			twentyFour : true,
			title: '일정 시작 시간',
			now: startTime
		});
		
		var endTime = $('.timepicker.end-time').val();
		$('.timepicker.end-time').wickedpicker({
			twentyFour : true,
			title: '일정 종료 시간',
			now: endTime
		});
	}
	printTime();
	
	if ($('.all-day input[type="checkbox"]').is(':checked')) {
		$('.start-time input[name="startTime"]').hide();
		$('.end-time input[name="endTime"]').hide();
	}
</script>

<script src="${pageContext.request.contextPath}/js/ajaxAction/scheduleModify.js"></script>
<script src="${pageContext.request.contextPath}/js/ajaxAction/scheduleDelete.js"></script>