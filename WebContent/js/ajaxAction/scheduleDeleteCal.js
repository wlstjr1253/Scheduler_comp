 /**
  * 일정 삭제
  */
$('.del-sc').on('show.bs.modal', function(e){
	var sc_idx = $(e.relatedTarget).data('scidx');
	var targetTitle = $(e.relatedTarget).parents('.list-control').prev('a').find('.title').text();
	
	var $titleArea = $('.modal-body p');
	var $delBtn = $('.del-schedule');

	$titleArea.text(targetTitle+" 일정을 삭제 합니다.");
	$delBtn.attr('data-scidx', sc_idx);
});

 $(document).on('click', '.del-schedule',function(e){
 	e.preventDefault();
 	var data = {
		sc_idx : $(this).data('scidx')
	}
 	execDelAjax(data);
 });

 function execDelAjax(data) {
 	$.ajax({
 		url: '../sc/deleteScheduleAjax.do',
 		data: data,
 		type: 'post',
 		dataType: 'json',
 		cache: false,
 		timeout: 30000,
 		success: function(data) {
 			alert('삭제 되었습니다.');
 			location.replace('../main/main.do');
 		}, 
 		error: function(){
 			console.log('일정 삭제 중 오류 발생');
 		}
 	});
 }