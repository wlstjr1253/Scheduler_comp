;(function($){
	var $prevMonthBtn = $('.prev-month.btn');
	var $nextMonthBtn = $('.next-month.btn');
	var $todayBtn = $('.today.btn');
	var $month = $('#month');
	
	var $loadingImg = $('.ajax-loading');
	
	$prevMonthBtn.click(function(){
		changeMonth('prev');
	});
	
	$nextMonthBtn.click(function(){
		changeMonth('next');
	});
	
	$todayBtn.click(function(){
		changeMonth('today');
	});
	$month.change(function(){
		changeMonth('change');
	});
	
	function changeMonth(v){
		  var year = $('#year option:selected').text().trim();
		  var month = $('#month option:selected').text().trim();
		  
		  var ch = 0;
		  if (v == 'prev') {
			  ch = 1;
		  }
		  if (v == 'next') {
			  ch = 2;
		  }
		  if (v == 'today') {
			  ch = 3;
		  }
		  if (v == 'change') {
			  ch = 4;
		  }
		  var data = {
			year: year,
			month: month,
			ch : ch
		  }
		  	  
		  $loadingImg.show();
		  $.ajax({
			  url: 'calendarAjax.do', 
			  type: 'post',
			  data: data,
			  dataType: 'json',
			  cache: false,
			  timeout: 30000,
			  success: function(data){
				  if (data) {
					  $loadingImg.hide();
					  showCalendar(data);
					  var template = $('#calendarBody').html();
					  var html = Handlebars.compile(template);
					  var contextTemp = html(data); 
					  $('.calendar-body').html('');
					  $('.calendar-body').html(contextTemp);
					  loadSchedule();
				  }
			  },
			  errorr: function(){
				  $loadingImg.hide();
				  console.log('이전 달 네트워크 오류 발생');
			  }
		  })
	}

	function showCalendar(data) {
		var $yearOption = $('#year option');
		var $monthOption = $('#month option');
		
		var yearMatch = 0;
		$yearOption.each(function(index, item) {
			$(item).prop('selected', false);
			if ($(item).val() == data.year) {
				$(item).prop('selected', true);
				yearMatch = 1;
			}
		});
		
		// 검색된 년도가 없다면
		if (yearMatch == 0) {
			if (data.year > $('#year option:selected').val()) {
				// 검색된 년도가 현재 년도 보다 클 경우
				$('#year option').first().detach().remove();
				$('#year').append('<option selected value='+data.year+'>'+data.year+'</option>');
			}
			if (data.year < $('#year option:selected').val()) {
				// 검색된 년도가 현재 년도 보다 작을 경우
				$('#year option').last().detach().remove();
				$('#year').prepend('<option selected value='+data.year+'>'+data.year+'</option>');
			}
		}
		
		$monthOption.each(function(index, item){
			$(item).prop('selected', false);
			if ($(this).val() == data.month) {
				$(this).prop('selected', true);
			}
	  	});	
	}
})(jQuery);