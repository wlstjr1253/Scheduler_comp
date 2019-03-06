/**
 * 알람 기능
 */
;(function($){
	function alarm() {
		$.ajax({
			url: '/Scheduler/main/alarm.do',
			type: 'post',
			dataType: 'json',
			success: function(data){
				if (data) {
					var pushAlarm = [];
					var pushTime = [];
					for (var i=0; i<data.list.length; i++) {
						var startYear = data.list[i].sc_startDate.substr(0,4);
						var startMonth = data.list[i].sc_startDate.substr(4,2);
						var startDay = data.list[i].sc_startDate.substr(6,2);
						var startTime = data.list[i].sc_startDate.substr(9,17);
						
						var alarmTime = new Date(startYear+" "+startMonth+" "+startDay+" "+startTime);
						var al_Timer = data.list[i].al_timer;
						
						pushAlarm.push(alarmTime);
						pushTime.push(al_Timer+"000");
					}
					
					var now = new Date();
					
					var alarmTable = {
						list: []
					};
					for (var i=0; i<pushAlarm.length; i++) {
						if (new Date(Date.parse(pushAlarm[i]) - pushTime[i] * 60 * 60) <=
							new Date(Date.parse(now))
							) {
							alarmTable.list.push(data.list[i]);
						} // if end
					}// for end  
					
					for (var i=0,t=0; i<alarmTable.list.length; i++) {
			
						var alWindow = $('#alarmWindow').html();
						var html = Handlebars.compile(alWindow);
						var contextTemp = html(alarmTable);
						
						$('.al-modal').html('');
						$('.al-modal').html(contextTemp);

						$('.alarm-window').eq(0).delay(0*400).animate({
							'right': 15+t,
							'backgroundColor': 'rgba(0,0,0,1)'
						});
						$('.alarm-window').eq(1).delay(1*400).animate({
							'right': 15+t,
							'backgroundColor': 'rgba(0,0,0,1)'
						});
						t =+ 10;
						$('.alarm-window').eq(2).delay(2*400).animate({
							'right': 15+t,
							'backgroundColor': 'rgba(0,0,0,1)'
						});
						t =+ 10;
						$('.alarm-window').eq(3).delay(3*400).animate({
							'right': 15+t,
							'backgroundColor': 'rgba(0,0,0,1)'
						});
						t =+ 10;
						$('.alarm-window').eq(4).delay(4*400).animate({
							'right': 15+t,
							'backgroundColor': 'rgba(0,0,0,1)'
						});
						t =+ 10;
					}
				}
				$('.alarm-window').alarmWin();
			},
			error: function(){
				console.log('알람 조회 중 네트워크 오류 발생');
			}
		})
	}
	
	$(document).on('click', '.alarm-window .btn-close',function(e){
		e.preventDefault();
		if ($(this).siblings('label').find('input').is(':checked')) {
			var idx = $(this).data('alidx');
			callDelAlarm(idx);
		}
	});
	
	function callDelAlarm(idx){
		var data = {
			al_idx: idx
		}
		
		$.ajax({
			url: '/Scheduler/main/alarmTurnOffAjax.do',
			data: data,
			type: 'post',
			dataType: 'json',
			cache: false,
			timeout: 30000,
			success: function(data){
				if (data.result == 'success') {
					alert('알람이 꺼졌습니다.')
				} else {
					alert('알람이 꺼지지 않았습니다!');
				}
			},
			error: function(){
				console.log('알람 끄기 중 네트워크 오류 발생');
			}
				
		})
	}
	

	alarm();
})(jQuery);