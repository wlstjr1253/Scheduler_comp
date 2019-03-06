/**
 * 일기 삭제
 */
$('.del-warn .modal-footer .btn-danger').on('click', function(){
	var data = {
		d_idx : $('.modify-diary input[name="d_idx"]').val()
	}
	
	$.ajax({
		url: 'deleteDiary.do',
		data: data,
		type: 'post',
		dataType: 'json',
		cache: false,
		timeout: 30000,
		success: function(data){
			if (data.result == 'success') {
				alert('삭제 되었습니다.');
				location.replace('diaryCal.do');
			}
			if (data.result == 'fail') {
				alert('삭제 중 문제가 발생했습니다 잠시 후 다시 시도해 주세요.');
			}
		},
		error: function(){
			console.log('일기 삭제 중 네트워크 오류 발생');
		}
	})
})