/**
 * 일기 등록
 */
$('.write-diary .submit-input').on('click', function(e){
	e.preventDefault();
	var data = {
		d_title: $('.diary .content .title input').val(),
		d_content: $('.diary .content .diary-content .note-editable').text()
	}
	callInsertDiaryAjax(data);
});

function callInsertDiaryAjax(data){
	$.ajax({
		url: 'diaryWrite.do',
		type: 'post',
		data: data,
		dataType: 'json',
		cache: false,
		timeout: 30000,
		success: function(data) {
			if (data.message.match('일기가 있습니다.')) {
				alert(data.message);
			} else {
				showCompleteModal(data);
			}
		},
		error: function(){
			console.log('다이어리 등록 네트워크 오류 발생');
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