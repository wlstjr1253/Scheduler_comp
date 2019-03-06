/**
 * 앨범 수정
 */

////////////////////////////////////
////////// 파일선택 버튼 toggle ///////
////////////////////////////////////

$('.file.modify-file #filename').on('change', function(){
	$(this).show();
	$(this).attr('name', 'filename');
	$(this).parent('.file').find('.file-path').hide();
})


$('form#modifyGallery').on('submit', function(e){
	e.preventDefault();
	
	// 전체 데이터를 전부 지정할 때
	var formData = new FormData($(this)[0]);
	
	// 개별 데이터를 선택적으로 셋팅할 때
	// var formData = new FormData();
	// formData.append("name", document.getElementById("name").value);

	
	$.ajax({
		url: 'galleryModifyAjaxAction.do',
		type: 'post',
		data: formData,
		dataType: 'json',
		contentType: false,
		processData: false,
		cache: false,
		timeout: 30000,
		success: function(data){
			if (data) {
				showCompleteModal(data);
			}
		},
		error: function() {
			console.log('갤러리 수정 네트워크 오류 발생');
		}
	})	
});


$('.del-warn.modal .btn-remove').on('click', function(e){
	e.preventDefault();
	var data = {
			g_idx : $('input[name="g_idx"]').val()
	}	
	$.ajax({
		url: 'galleryDeleteAjaxAction.do',
		data: data,
		type: 'post',
		dataType: 'json',
		cache: false,
		timeout: 30000,
		success: function(data){
			if (data) {
				showCompleteModal(data);
			}
		}, 
		error: function(){
			console.log('갤러리 삭제하기 네트워크 통신 오류');
		}
	});
})
		
function showCompleteModal(data) {
	$('.complete-modal').modal({
		backdrop : false,
		keyboard : false
	});
	$('.complete-modal').on('shown.bs.modal', function(){
		$('.complete-modal .modal-body .message').append(data.message);
	})
}