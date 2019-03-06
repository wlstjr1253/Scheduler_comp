/**
 * 아이디 중복 체크
 */
var idChecked = 0;
$('.id-check').click(function(){
	if ($(this).siblings('input').val() == '') {
		alert('아이디를 입력 하세요!');
		$(this).siblings('input').focus();
		return;
	}
	
	$('#loading').show(); //로딩 이미지 노출
	
	$.ajax({
		url: 'checkId.do',
		type: 'post',
		data: {id: $(this).siblings('input').val()},
		dataType: 'json',
		cache: false,
		timeout: 30000,
		success: function(data) {
			$('#loading').hide();//로딩 이미지 감추기
			
			if (data.result == 'idNotFound') {
				$('#message_id').css('color','#fff').text('등록 가능 ID');
				idChecked = 1;
			} else if (data.result = 'idDuplicated') {
				$('#message_id').css('color','#e9ef00').text('중복된 ID');
				$('.id-line label input').focus();
				idChecked = 0;
				
			} else {
				alert('아이디 중복 체크 오류 발생');
			}
		},
		error: function(){
			$('#loading').hide();//로딩 이미지 감추기
			console.log('아이디 중복체크 네트워크 오류 발생');
		}
	});
});

$('.id-line input').keydown(function(){
	idChecked=0;
	$('#message_id').text('');
})


$('.join form .email-line input').on('keyup', function(){
	var data = {
			email : $(this).val()
	}
	$.ajax({
		url: 'checkEmail.do',
		type: 'post',
		dataType: 'json',
		data: data,
		cache: false,
		timeout: 30000,
		success: function(data){
			if (data.result == 'emailNotFound') {
				$('#message_mail').css('color','#fff').text('등록 가능 email');
				emailChecked = 1;
			} else if (data.result == 'emailDuplicated') {
				$('#message_mail').css('color','#e9ef00').text('중복된 email');
				//$('.email-line label input').focus();
				emailChecked = 0;
				
			} else {
				alert('이메일 중복 체크 오류 발생');
			}
		},
		error: function(){
			console.log('이메일 중복체크 네트워크 오류 발생');
		}
	}); // ajax end
});  // keypress end

$('.join form .email-line input').on('focusout', function(){
	$(this).siblings('.check-text').text('');
})


$('.join form').on('submit', function(){
	if (idChecked == 0) {
		alert('아이디 중복 체크 필수!');
		$('.id-line label input').focus();
		return false;
	}
	
	if (emailChecked == 0) {
		alert('이메일이 중복 됩니다');
		$('.email-line label input').focus();
		return false;
	}
})