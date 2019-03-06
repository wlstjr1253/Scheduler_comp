/**
 * 스케쥴 데이터 읽어오기
 */
function loadSchedule() {
	var $loadingImg = $('.ajax-loading');
	var year = $('#year option:selected').text().trim();
	var month = $('#month option:selected').text().trim();
	var thisToday = parseInt($('.calendar-body td').filter('.today').text().trim());
	
	var data = {
		year: year,
		month: month
	}
	
	var $calTd = $('.calendar-body td');
	$loadingImg.show();

	$.ajax({
		url: 'scheduleByMonth.do',
		data: data,
		type: 'post',
		dataType: 'json',
		cache: false,
		timeout: 30000,
		success: function(data){
			$loadingImg.hide();
			console.log(data);
			if (data.scList) {
				// 내가 작성한 일정
				var thisYear = parseInt(year);
				var thisMonth = parseInt(month);
				
				var prevYear = thisYear - 1;
				var prevMonth = thisMonth - 1;
				if (prevMonth == 0) {
					prevMonth = 12;
				}
				
				var nextYear = thisYear + 1;
				var nextMonth = thisMonth + 1;
				if (nextMonth == 13) {
					nextMonth = 1;
				}
				
				for (var i=0; i<data.scList.length; i++) {
					/* 시작 년, 월, 일 */
					var thisStartYear = parseInt(data.scList[i].sc_startDate.substr(0,4));
					var thisStartMonth = parseInt(data.scList[i].sc_startDate.substr(5,2));
					var thisStartDay = parseInt(data.scList[i].sc_startDate.substr(8,2));
					var sDate = new Date(thisStartYear, thisStartMonth-1, thisStartDay);
					
					/* 종료 년, 월, 일 */
					var thisEndYear = parseInt(data.scList[i].sc_endDate.substr(0,4));
					var thisEndMonth = parseInt(data.scList[i].sc_endDate.substr(5,2));
					var thisEndDay = parseInt(data.scList[i].sc_endDate.substr(8,2));
					var eDate = new Date(thisEndYear, thisEndMonth-1, thisEndDay);
					var chkShare = "";
					
					
					$calTd.each(function(){						
						var thisDay = parseInt($(this).text());
						var nDate = new Date(thisYear, thisMonth - 1, thisDay);
						
						if (sDate <= nDate && eDate >= nDate) {
							if (data.scList[i].id != data.user) {
								chkShare = 'shared'; 
							}
							printTd(data.scList[i], $(this), chkShare);
						}
						
						if ($(this).hasClass('prev-month')) {
							$(this).find('.sc').detach().remove();
						}
						if ($(this).hasClass('next-month')) {
							$(this).find('.sc').detach().remove();
						}
					});	// each end
				} // for data end
			} // if data end
			
			$('.calendar-body td').each(function(index, item){
				if ($(item).find('.sc').length > 3) {
					for (var i=2; i<$(item).find('.sc').length; i++) {
						$(item).find('.sc').eq(i).hide();
					}
					var output = "";
					output += "<div class='sc'>";
					output += "		<a href='#' data-toggle='modal' data-target='.schedule-detail'>";
					output += "			그외 + "+($(item).find('.sc').length - 2);
					output += "		</a>";
					output += "</div>";
					
					$(item).append(output);
				}
			})
		},
		error: function(){
			$loadingImg.hide();
			console.log('일정 가져오는 중 네트워크 오류 발생');
		}
	});
}

function printTd(data, $this, chkShare) {
	var output = "";
	if (chkShare == 'shared') {
		output += "<div class='sc shared'>";
		output += "		<a href='#' data-toggle='modal' data-target='.schedule-detail'>";
		output += "공유 - "+data.sc_title;
	} else {
		output += "<div class='sc'>";
		output += "		<a href='#' data-toggle='modal' data-target='.schedule-detail'>";
		output += data.sc_title;
	}
	output += "		</a>";
	output += "</div>";
	$this.append(output);	
}



loadSchedule();
