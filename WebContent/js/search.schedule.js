/**
 * 다가올 일정 검색 (모레 부터 검색됨) 
 */
$('#searchSch').on('submit', function(e){
	var keyword = $('#searchSch input[name="keyword"]').val();
	e.preventDefault();
	srchSchedule('comming', keyword);
});

/**
 * 지난 일정 검색
 */
$('#passedSearchSch').on('submit', function(e) {
	var keyword = $('#passedSearchSch input[name="keyword"]').val();
	e.preventDefault();
	srchSchedule('passed', keyword);
});

function srchSchedule(day, keyword){
	var loadingImg = $('.schedule-list .comming-list .ajax-loading');
	loadingImg.show();
	var keyword = $('input[name="keyword"]').val();
	var pageNum = "1";
	if (keyword == "") {
		pageNum = "1";
	} else {
		var pageNum = ($('.pagination .page-item a').text() == "")? 1 : $('.pagination .page-item a').text();
	}
	var data = {
		keyword : keyword,
		pageNum : pageNum
	}

	var url = "";

	if (day == 'comming') {
		url = "./commingScheduleAjax.do";
	} else {
		url = './passedScheduleAjax.do';
	}

	$.ajax({
		url: url,
		data: data,
		type: 'post',
		dataType : 'json',
		cache : false,
		timeout : 30000,
		success : function(data){
			loadingImg.hide();
			console.log(data)
			if (data.count > 0) {
				var template = $('#come-list').html();
				var html = Handlebars.compile(template);
				var contextTemp = html(data); 
				$('.comming-list ul').html('');
				$('.comming-list ul').html(contextTemp);
				
			} else {
				$('.comming-list ul').html('');
				$('.comming-list ul').append('<li class="no-result">'+'검색된 결과가 없습니다.'+'</li>');
			}
			$('.total-guide').text('총 ' + data.count  +' 개의 일정이 등록 되었습니다.');
			$('.pagination').html('');
			$('.pagination').append(data.pagingHtml);		
		},
		error : function(e) {
			loadingImg.hide();
			console.log('스케쥴 검색 네트워크 에러 발생');
		}
	});
}



/**
 * 페이징 클릭시 처리
 */
$(document).on('click', '.pagination.comming a', function(e){
	e.preventDefault(); 
	loadSchedule('comming', $(this));
});

$(document).on('click', '.pagination.passed a', function(e){
	e.preventDefault(); 
	loadSchedule('passed', $(this));
});


function loadSchedule(day, $this) {
	var loadingImg = $('.schedule-list .comming-list .ajax-loading');
	var pageNum = $this.data('link');
	var keyword = $('.search-box form input[name="keyword"]').val();
	var data = {
		pageNum: pageNum,
		keyword: keyword
	}

	
	var url = "";
	if (day == 'comming') {
		url = './commingScheduleAjax.do';
	} else {
		url = './passedScheduleAjax.do';
	}
	
	$.ajax({
		url: url,
		data: data,
		type: 'post',
		dataType : 'json',
		cache : false,
		timeout : 30000,
		success: function(data){
			loadingImg.hide();
			if (data.count > 0) {
				var template = $('#come-list').html();
				var html = Handlebars.compile(template);
				var contextTemp = html(data); 
				$('.comming-list ul').html('');
				$('.comming-list ul').html(contextTemp);
				$('.total-guide').text('총 ' + data.count +' 개의 일정이 등록 되었습니다.');
			} else {
				$('.comming-list ul').html('');
				$('.comming-list ul').append('<li class="no-result">'+'검색된 결과가 없습니다.'+'</li>');
			}
			$('#searchSch input[name="keyword"]').val('');
			$('.pagination').html('');
			$('.pagination').append(data.pagingHtml);
		},
		error : function(){
			loadingImg.hide();
			console.log('다가올 스케줄 페이징 네트워크 에러 발생');
		}
	})
}