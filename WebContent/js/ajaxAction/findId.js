/**
 * 아이디 찾기
 */
(function($){
	$('.btn-srch').on('click', function(e){
		e.preventDefault();
		if ($('input[name="userName"]').val().length < 1) {
			alert('이름을 입력해 주세요.');
			$('input[name="userName"]').focus();
			return;
		}
		if ($('input[name="email"]').val().length < 1) {
			alert('이메일을 입력해 주세요.');
			$('input[name="email"]').focus();
			return;
		}
		
		var data = {
			userName : $('input[name="userName"]').val(),
			email : $('input[name="email"]').val()
		}
		
		srchId(data);
	});
	
	function srchId(data) {
		$.ajax({
			url: '/Scheduler/main/searchIdAjax.do',
			data: data,
			type: 'post',
			dataType: 'json',
			cache: false,
			timeout: 30000,
			success: function(data){
				if (data.result != 'fail') {
					// 찾은 경우
					var template = $('#findRes').html();
					var html = Handlebars.compile(template);
					var dataRes = {
						list: [data]
					}
					var contextTemp = html(dataRes);
					$('.result').html('');
					$('.result').html(contextTemp);
				}
				
				if (data.result == 'fail') {
					$('.result').html('');
					$('.result').html('<p>검색된 결과가 없습니다.</p>');
				}
			},
			error: function(){
				console.log('아이디 검색 중 네트워크 오류 발생');
			}
		})
	}
})(jQuery);