/**
 * 즐겨찾기
 */

// 일정 추가
$(document).on('click', '.add-favorite', function(e){
	e.preventDefault();
	var $this = $(this);
	var sc_idx = $this.data('scidx');
	addFavSchedule(sc_idx, $this);
});

$('.fav-warn').on('show.bs.modal', function(e){
	var $this = $(e.relatedTarget);
	var sc_idx = $this.data('scidx');
	
	// 일정 추가
	$(document).on('click', '.add-favo', function(e){
		e.preventDefault();
		addFavSchedule(sc_idx, $this);
		console.log($(this))
	});
})

function addFavSchedule(idx, $this) {
	var data = {
		sc_idx: idx
	}
	console.log(data.sc_idx);
	$.ajax({
		url: '/Scheduler/sc/addFavScheduleAjax.do',
		data: data,
		type: 'post',
		dataType: 'json',
		cache: false,
		timeout: 30000,
		success: function(data){
			if (data.result == 'success') {
				alert('즐겨찾기에 추가 되었습니다.');
				$('.fav-warn').modal('hide');
				$this.removeClass('add-favorite').addClass('added');
				$this.attr('href', '/Scheduler/record/favorite.do');
			} else {
				console.log('즐겨찾기 등록 중 오류 발생!');
			}
		},
		error: function(){
			
		}
	}); // ajax end
}

// 일기 추가
$(document).on('click', '.add-fav-diary', function(e){
	e.preventDefault();
	var d_idx = $('.modify-diary input[name="d_idx"]').val();
	addFavDiary(d_idx);
});
function addFavDiary(idx) {
	var data ={
		d_idx: idx
	}
	
	$.ajax({
		url: '/Scheduler/sc/addFavDiaryAjax.do',
		data: data,
		type: 'post',
		dataType: 'json',
		cache: false,
		timeout: 30000,
		success: function(data){
			if (data.result == 'success') {
				alert('즐겨찾기에 추가 되었습니다.');
				location.replace('./diaryCal.do');
			}
			if (data.result == 'fail') {
				alert('이미 즐겨찾기에 추가되어 있습니다.');
			}
		},
		error: function(){
			console.log('일기 즐겨찾기 추가 중 네트워크 오류 발생')
		}
	})
}

// 앨범 즐겨찾기 추가
$(document).on('click', '.add-fav-gallery', function(e){
	e.preventDefault();
	var g_idx = $('input[name="g_idx"]').val();
	addFavGallery(g_idx);
});
function addFavGallery(idx) {
	var data ={
		g_idx: idx
	}
	
	$.ajax({
		url: '/Scheduler/sc/addFavGalleryAjax.do',
		data: data,
		type: 'post',
		dataType: 'json',
		cache: false,
		timeout: 30000,
		success: function(data){
			if (data.result == 'success') {
				alert('즐겨찾기에 추가 되었습니다.');
				location.replace('./gallery.do');
			}
			if (data.result == 'fail') {
				alert('이미 즐겨찾기에 추가되어 있습니다.');
			}
		},
		error: function(){
			console.log('일기 즐겨찾기 추가 중 네트워크 오류 발생')
		}
	})
}


// 일정 검색
$('.sch-list form').on('submit', function(e){
	e.preventDefault();
	var keyword = $('.sch-list form input[name="scKeyword"]').val();
	srchSc(keyword);
})
// 일정 페이징 클릭시 처리
$(document).on('click', '.sc-paging a', function(e){
	e.preventDefault();
	var keyword = $('.sch-list form input[name="scKeyword"]').val();
	var pageNum = $(this).data('link');
	srchSc(keyword, pageNum);
})
function srchSc(keyword, pageNum) {
	var loadingImg = $('.sch-list .ajax-loading');
	loadingImg.show();
	var data = {
		keyword: keyword,
		pageNum :pageNum
	}
	
	$.ajax({
		url: 'srchFavScheduleAjax.do',
		data: data,
		type: 'post',
		dataType: 'json',
		cache: false,
		timeout: 30000,
		success: function(data){
			loadingImg.hide();
			if (data) {
				var template = $('#schList').html();
				var html = Handlebars.compile(template);
				var contextTemp = html(data);
				$('.sch-list ul').html('');
				$('.sch-list ul').html(contextTemp);
				$('.sch-list .total-guide').text('총 '+ data.scCount + '개의 일정 모음이 있습니다.');
				
				$('input[name="scKeyword"]').val('');
				
				$('.sc-paging').html('');
				$('.sc-paging').append(data.sc_pagingHtml.pagingHtml);
			}
		},
		error: function() {
			loadingImg.hide();
			console.log('일정 즐겨찾기 검색 중 네트워크 오류 발생');
		}
	})
	
}


// 다이어리 검색
$('.diary-list form').on('submit', function(e){
	e.preventDefault();
	var keyword = $('.diary-list form input[name="diKeyword"]').val();
	srchDiary(keyword);
})
// 다이어리 페이징 클릭시 처리
$(document).on('click', '.di-paging a', function(e){
	e.preventDefault();
	var keyword = $('.diary-list form input[name="diKeyword"]').val();
	var pageNum = $(this).data('link');
	srchDiary(keyword, pageNum);
})

function srchDiary(keyword, pageNum) {
	var loadingImg = $('.diary-list .ajax-loading');
	loadingImg.show();
	var data = {
		keyword: keyword,
		pageNum :pageNum
	}
	
	$.ajax({
		url: 'srchFavDiaryAjax.do',
		data: data,
		type: 'post',
		dataType: 'json',
		cache: false,
		timeout: 30000,
		success: function(data){
			loadingImg.hide();
			if (data) {
				var template = $('#diaryList').html();
				var html = Handlebars.compile(template);
				var contextTemp = html(data);
				$('.diary-list ul').html('');
				$('.diary-list ul').html(contextTemp);
				$('.diary-list .total-guide').text('총 '+ data.dCount + '개의 다이어리 모음이 있습니다.');
				
				$('input[name="diKeyword"]').val('');
				
				$('.di-paging').html('');
				$('.di-paging').append(data.d_pagingHtml.pagingHtml);
			}
		},
		error: function() {
			loadingImg.hide();
			console.log('다이어리 즐겨찾기 검색 중 네트워크 오류 발생');
		}
	})
	
}

// 갤러리 검색
$('.gallery-list form').on('submit', function(e){
	e.preventDefault();
	var keyword = $('.gallery-list form input[name="galKeyword"]').val();
	srchGallery(keyword);
})
// 갤러리 페이징 클릭시 처리
$(document).on('click', '.g_paging a', function(e){
	e.preventDefault();
	var keyword = $('.gallery-list form input[name="galKeyword"]').val();
	var pageNum = $(this).data('link');
	srchGallery(keyword, pageNum);
})

function srchGallery(keyword, pageNum) {
	var loadingImg = $('.gallery-list .ajax-loading');
	loadingImg.show();
	var pageNum;
	if (keyword == "") {
		pageNum = pageNum;
	} else {
		if ($('.g_paging .page-item a').data('link') == "") {
			pageNum = 1;
		} else {
			pageNum = pageNum;
		}
	}
	
	var data = {
		keyword : keyword,
		pageNum : pageNum
	}
		
	$.ajax({
		url: 'srchFavGalleryAjax.do',
		data: data,
		type: 'post',
		dataType: 'json',
		cache: false,
		timeout: 30000,
		success: function(data){
			loadingImg.hide();
			if (data) {
				var template = $('#galleryList').html();
				var html = Handlebars.compile(template);
				var contextTemp = html(data);
				$('.gallery-list ul').html('');
				$('.gallery-list ul').html(contextTemp);
				$('.gallery-list .total-guide').text('총 '+ data.galleryCount + '개의 갤러리 모음이 있습니다.');
				
				$('input[name="galKeyword"]').val('');
				
				$('.g_paging').html('');
				$('.g_paging').append(data.g_pagingHtml.pagingPrevNextHtml);
			}
		},
		error: function(){
			loadingImg.hide();
			console.log('갤러리 모음 조회 중 네트워크 오류 발생!');
		}
	})
}


// 갤러리 삭제
$('.del-fav').on('show.bs.modal', function(e){
	var targetTitle = $(e.relatedTarget).parents('.list-control').prev('a').find('.title').text();
	if ($(e.relatedTarget).parents('.list-control').length < 1) {
		targetTitle = $(e.relatedTarget).prev('a').find('figcaption.title').text();
	}
	$(this).find('.modal-body p').text(targetTitle);

	var idx = $(e.relatedTarget).data('idx');
	var part = $(e.relatedTarget).data('part');

	var data = {
		idx : idx,
		part: part
	}
	var $target = $(e.relatedTarget).parents('li')
	
	$('.del-favorite').on('click', function(){
		execRemoveFav($target, targetTitle, data);
	});
});


function execRemoveFav($target, title, data) {

	$.ajax({
		url: 'removeFavoriteAjax.do',
		type: 'post',
		data: data,
		dataType: 'json',
		success: function(data){
			if (data.result == 'success') {
				$('.del-fav').modal('hide');
				alert(title + ' 즐겨찾기 삭제 완료!');
//				$target.fadeOut(400, function(){
//					$(this).detach().remove();
//				})
				location.replace('favorite.do');
			}
		},
		error: function(){
			console.log('즐겨찾기 제거 중 네트워크 오류 발생');
		}
	});
}
