/**
 * 회원 검색
 */
$('.add-member-modal .search-box input').on('keyup', function(e){
	e.preventDefault();
	var data = {
		user: $(this).val()
	};
	if (data.user.length < 1) {
		$('.add-member-modal .search-result').html('');
		return;
	}
	searchMemberAjax(data);
});

function searchMemberAjax(data){
	var loadingImg = $('.add-member-modal .result-box .ajax-loading');
	loadingImg.show();
	$.ajax({
		url: 'searchUserAjax.do',
		data: data,
		type: 'post',
		dataType: 'json',
		cache: false,
		timeout: 30000,
		success: function(data){
			loadingImg.hide();
			if (data) {
				var template = $('#userList').html();
				var html = Handlebars.compile(template);
				var contextTemp = html(data); 
				$('.add-member-modal .search-result').html('');
				$('.add-member-modal .search-result').html(contextTemp);
			}
		},
		error: function(){
			loadingImg.hide();
		}
	})
}