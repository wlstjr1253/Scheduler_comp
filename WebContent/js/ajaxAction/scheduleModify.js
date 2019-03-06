/**
 * 일정 수정
 */
$('.modify-schedule .submit-input').on('click', function(e){
	e.preventDefault();
	var data = $(this).parents('.modify-schedule').serialize();
	executeModify(data);
});

function executeModify(data) {
	$.ajax({
		url: 'modifyScheduleAjax.do',
		data: data,
		type: 'post',
		dataType: 'json',
		cache: false,
		timeout: 30000,
		success: function(data) {
			if(data) {
				showCompleteModal(data);
			}
		},
		error: function() {
			console.log('일정 수정 중 네트워크 오류 발생');
		}
	})
}

function showCompleteModal(data) {
	$('.complete-modal').modal({
		backdrop : false,
		keyboard : false
	});
	$('.complete-modal').on('shown.bs.modal', function(){
		$('.complete-modal .modal-body .message').append(data.message);
	})
}