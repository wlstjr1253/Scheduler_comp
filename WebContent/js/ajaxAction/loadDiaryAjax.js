/**
 * 다이어리 읽어오기
 */
function loadDiary(){
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
		url: 'getDiaryByMonth.do',
		data: data,
		type: 'post',
		dataType: 'json',
		cache: false,
		timeout: 30000,
		success: function(data){
			$loadingImg.hide();

			if (data) {
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
				
				for (var i=0; i<data.dList.length; i++) {
					/* 등록일 */
					var dDate = data.dList[i].d_reg_date;
					var dYear = parseInt(dDate.substr(0,4));
					var dMonth = parseInt(dDate.substr(5,2));					
					var dDay = parseInt(dDate.substr(8,2));
															
					$calTd.each(function(){
						var thisDay = parseInt($(this).text());
						
						// td의 날짜
						if (thisYear == dYear && 
							thisMonth == dMonth &&
							thisDay == dDay) {
							printTd(data.dList[i], $(this));
						}
						
						if ($(this).hasClass('prev-month')) {
							$(this).find('.sc').detach().remove();
						}
						if ($(this).hasClass('next-month')) {
							$(this).find('.sc').detach().remove();
						}
					})
				}
			}
		},
		error: function(){
			$loadingImg.hide();
			console.log('월별 다이어리 가져오는 중 네트워크 오류 발생');
		}
	})
	
}

function printTd(data, $this) {
	var output = "";
		output += "<div class='sc diary'>";
		output += "		<a href='readDiary.do?d_idx="+data.d_idx+"'>";
		output += "			읽기"
		output += "		</a>";
		output += "</div>";
		$this.append(output);	
}

loadDiary();