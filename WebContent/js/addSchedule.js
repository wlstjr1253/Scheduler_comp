/**
 * 일정 추가
 */
function SetAllDay() {
	this._$allDayIpt = null;
	this._$startTimeIpt = null;
	this._$endTimeIpt = null;
	this._$startSecondIpt = null;
	this._$endSecondIpt = null; 
	
	this._init();
	this._evt();
}

SetAllDay.prototype._init = function(){
	this._$allDayIpt = $('.all-day label input[type="checkbox"]');
	this._$startTimeIpt = $('.start-time label input[name="startTime"]');
	this._$endTimeIpt = $('.end-time label input[name="endTime"]');
	this._$startSecondIpt = $('.start-time label input[name="startSecond"]');
	this._$endSecondIpt = $('.end-time label input[name="endSecond"]');
}

SetAllDay.prototype._evt = function() {
	var that = this;
	var startTimeVal = "";
	var endTimeVal = "";
	var startSecondVal = "";
	var endSecondVal = "";
	this._$allDayIpt.on('click', function(){
		if ($(this).is(':checked')) {
			startTimeVal = that._$startTimeIpt.val();
			endTimeVal = that._$endTimeIpt.val();
			startSecondVal = that._$startSecondIpt.val();
			endSecondVal = that._$endSecondIpt.val();

			$(this).siblings('input[type="hidden"]').val('Y');
			that._$startTimeIpt.fadeOut(function(){
				that._$startTimeIpt.val('00:00');
				$(this).siblings('[name="startSecond"]').val('00');
			});
			that._$endTimeIpt.fadeOut(function(){
				that._$endTimeIpt.val('23:59')
				$(this).siblings('[name="endSecond"]').val('59');
			});
			
		} else {
			$(this).siblings('input[type="hidden"]').val('N');
			that._$startTimeIpt.val(startTimeVal);
			that._$endTimeIpt.val(endTimeVal);
			
			that._$startSecondIpt.val(startSecondVal);
			that._$endSecondIpt.val(endSecondVal);
			
			that._$startTimeIpt.fadeIn();
			that._$endTimeIpt.fadeIn();
		}
	})
}


$.fn.setAllDay = function(){
	this.each(function(index){
		var setAllDay = new SetAllDay();
	});
	return this;
}

function SetAlarm(){
	this._$alarmSet = null;
	this._$
	
	this._init();
	this._evt();
}

SetAlarm.prototype._init = function(){
	this._$alarmSet = $('.alarm-set label input[type="checkbox"]');
	this._$alarm = $('.content li.alarm');
}

SetAlarm.prototype._evt = function() {
	var that = this;
	this._$alarmSet.on('click', function(){
		if ($(this).is(':checked')) {
			that._$alarm.fadeIn();
		} else {
			that._$alarm.fadeOut();
		}
	})
}

$.fn.setAlarm = function(){
	this.each(function(index) {
		var setAlarm = new SetAlarm();
	});
	return this;
}

function CheckStartEndDate() {
	this._startDate = null;
	this._endDate = null;
	this._$startDateIpt = null;
	this._$endDateIpt = null;
	this._$startTimeIpt = null;
	this._$endTimeIpt = null;
	
	this._init();
	this._evt();
	this._printTime();
}

CheckStartEndDate.prototype._init = function() {
	this._startDate = $('input[name="startDate"]').val();
	this._endDate = $('input[name="endDate"]').val();

	this._$startDateIpt = $('input[name="startDate"]');
	this._$endDateIpt = $('input[name="endDate"]');
	this._$startTimeIpt = $('input[name="startTime"]');
	this._$endTimeIpt = $('input[name="endTime"]');
}

CheckStartEndDate.prototype._evt = function() {
	var that = this;
	this._$startDateIpt.on('click', function(){
		that._splitDate();
	});
	this._$startTimeIpt.on('click', function() {
		that._splitDate();
	});
	this._$endDateIpt.on('click', function() {
		that._splitDate();
	});
	this._$endTimeIpt.on('click', function() {
		that._splitDate();
	});
	
	this._$startTimeIpt.on('change', function(){
		that._printTime($(this));
	});
	this._$endTimeIpt.on('change', function(){
		that._printTime($(this));
	})
}

CheckStartEndDate.prototype._splitDate = function() {
	var startArray = this._startDate.split('-');
	var endArray = this._endDate.split('-');
	this._createDate(startArray, endArray);
}

CheckStartEndDate.prototype._createDate = function(start, end) {
	var start_date = new Date(start[0], start[1], start[2]);
	var end_date = new Date(end[0], end[1], end[2]);

	if (start_date.getTime() > end_date.getTime()) {
		alert('종료날짜보다 시작날짜가 작아야 합니다.');
	}
}

CheckStartEndDate.prototype._printTime = function($this) {
	var date = new Date();
	var nowTime = date.getSeconds();

	if ($this == null) {
		this._$startTimeIpt.siblings('[name="startSecond"]').val(nowTime);
		this._$endTimeIpt.siblings('[name="endSecond"]').val(nowTime);
		return;
	}
	
	if ($this.parents('li').hasClass('start-time')) {
		$this.siblings('[name="startSecond"]').val(nowTime);
	}
	
	if ($this.parents('li').hasClass('end-time')) {
		$this.siblings('[name="endSecond"]').val(nowTime);
	}
}

$.fn.checkStartEndDate = function() {
	this.each(function(index){
		var checkStartEndDate = new CheckStartEndDate();
	});
	return this;
}

$('.add-schedule').setAllDay();
$('.add-schedule').setAlarm();
$('.add-schedule').checkStartEndDate();