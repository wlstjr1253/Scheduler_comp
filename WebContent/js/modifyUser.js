/**
 * 회원 정보 수정
 */
$('#modify_form .submit-input').on('click', function(e){
	console.log(123)
    e.preventDefault();
    var $iptPwd = $('input[name="pwd"]');
    var $iptPwd2 = $('input[name="pwd2"]');
    
    if ($iptPwd.val() == '') {
    	alert('비밀번호를 입력하세요!');
    	return;
    }
    
    if ($iptPwd2.val() == '') {
    	alert('비밀번호를 확인 하세요!');
    	return;
    }
    
    $('#loading').show();  // 로딩 이미지 노출
    
    var data = {
    		id: $('input[name="userId"]').val(),
    		name: $('input[name="userName"]').val(),
    		pwd : $('input[name="pwd"]').val(),
    		phone : $('input[name="phone"]').val(),
    		email : $('input[name="email"]').val()
    }
    
    $.ajax({
    	url: 'modifyAjaxUser.do',
    	type: 'post',
    	data: data,
    	dataType: 'json',
    	cache: false,
    	timeout: 30000,
    	success: function(data) {
    		$('#loading').hide();   // 로딩 이미지 감추기
    		if (data.result == "modifySuccess") {
    			$('.confirm-modify').modal();
    		}
    		if (data.result == "modifyFail") {
    			alert("수정 실패");
    		}
    	},
    	error: function(){
    		$('#loading').hide();	// 로딩 이미지 감추기
    		console.log('회원 정보 수정하기 네트워크 오류 발생')
    	}
    }); // ajax end
});  // 수정하기 end

$('.bye-modal .btn-danger').on('click', function(e){
	e.preventDefault();
	
	var data = {
			id : $('input[name="userId"]').val(),
			pwd : $('.bye-modal .modal-body input[name="passwd"]').val()
	}
	
    // S: 회원 탈퇴
    $.ajax({
    	url: 'byeAjaxUser.do',
    	type: 'post',
    	data: data,
    	dataType: 'json',
    	cache: false,
    	timeout: 30000,
    	success: function(data){
    		if (data.result == 'bye') {
    			alert('이용해 주셔서 감사합니다.');
    			location.replace('./logout.do');
    		}
    		if (data.result == 'stay') {
    			alert('비밀번호가 일치하지 않습니다.');
    		}
    	},
    	error: function(){
    		console.log('회원 탈퇴 네트워크 오류 발생');
    	}
    })
    // E: 회원 탈퇴
})