/**
 * 즐겨찾기
 */

// 일정 추가
$(document).on('click', '.add-favorite', function(e){
	e.preventDefault();
	var sc_idx = $(this).data('scidx');
	addFavSchedule(sc_idx);
});
function addFavSchedule(idx) {
	var data = {
		sc_idx: idx
	}
	$.ajax({
		url: '/Scheduler/sc/addFavScheduleAjax.do',
		data: data,
		type: 'post',
		dataType: 'json',
		cache: false,
		timeout: 30000,
		success: function(data){
			if (data.result == 'success') {
				
			} else {
				console.log('즐겨찾기 등록 중 오류 발생!');
			}
		},
		error: function(){
			
		}
	}); // ajax end
}

// 일기 추가
$(document).on('click', '.add-fav-diary', function(e){
	e.preventDefault();
	var d_idx = $('.modify-diary input[name="d_idx"]').val();
	addFavDiary(d_idx);
});
function addFavDiary(idx) {
	var data ={
		d_idx: idx
	}
	$.ajax({
		url: '/Scheduler/'
	})
}