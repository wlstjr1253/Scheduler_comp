/**
 * 일기 수정
 */
$('.modify-diary input[type="submit"]').on('click', function(e){
	e.preventDefault();
	var data = {
		d_idx : $('.modify-diary input[name="d_idx"]').val(),
		d_title: $('.modify-diary input[name="d_title"]').val(),
		d_content: $('.modify-diary .diary-content .note-editable').html()
	}
	
	$.ajax({
		url: 'modifyDiary.do',
		data: data,
		type: 'post',
		dataType: 'json',
		cache: false,
		timeout: 30000,
		success: function(data){
			if (data.update == 'success') {
				$('.complete-modal').find('.modal-body .message').text('수정 되었습니다.');
				$('.complete-modal').modal();
				
			}
		},
		error: function() {
			console.log('일기 수정 중 네트워크 오류 발생');
		}
	})
})