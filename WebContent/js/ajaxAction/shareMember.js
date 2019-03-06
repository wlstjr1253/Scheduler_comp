/**
 * 검색된 회원 추가
 */
$(document).on('click', '.add-member-modal .invite-btn', function(e){
	e.preventDefault();
	var data = {
		memberId: $(this).data('id'),
		scIdx: $('.modify-schedule input[name="sc_idx"]').val()
	}
	var invitedMember = {
		memberName : $(this).parents('li').find('.member-name').text(),
		memberMail : $(this).parents('li').find('.member-mail').text(),
		memberId : $(this).data('id')
	}
	
	var invitedMemberList = [];
	$('.member section ul .member-list').each(function(){
		invitedMemberList.push($(this).find('.member-mail').text().trim());
	});
	for (var i=0; i<invitedMemberList.length; i++) {
		if (invitedMember.memberMail == invitedMemberList[i]) {
			alert('이미 추가된 회원 입니다.!');
			return;
		}
	} 
	
	if (data.scIdx) {
		// 일정 수정시 처리 (DB에 추가)
		execInviteMember(data, invitedMember);
	} 
	if (data.scIdx == undefined) {
		// 일정 등록시 처리 (화면만 제어)
		addSharedMemberList(invitedMember);
	}
});

function execInviteMember(data, invitedMember){
	// 공유 회원 추가
	$.ajax({
		url: 'inviteMemberAjax.do',
		data: data,
		type: 'post',
		dataType: 'json',
		cache: false,
		timeout: 30000,
		success: function(data) {
			if (data.si_idx) {
				var addedSi_idx = data.si_idx;
				addSharedMemberList(invitedMember, addedSi_idx);
			}
		},
		error: function(e) {
			console.log('회원 추가 중 네트워크 오류 발생');
		}
	});
		
}

function addSharedMemberList(member, si_idx) {
	var $sharedList = $('li.member section ul');
	$sharedList.find('.no-result').hide();
	if ($('.modify-schedule input[name="sc_idx"]').length > 0) { 
		// 수정시 
		if ($sharedList.find('li').eq(0).hasClass('writer')) {
			// 등록된게 하나도 없을 경우 (작성자 표시로 인한 구분)
			var length = $sharedList.find('li').length + 1;
		} else {
			var length = $sharedList.find('li').length;
		}
		
		if ($sharedList.find('li').length < 2 && $sharedList.parents('.modify-schedule').length > 0) {
			var length = $sharedList.find('li').length + 1;
		}
	}
	if ($('.modify-schedule input[name="sc_idx"]').length < 1) {
		// 등록시
		var length = $sharedList.find('li').length;
	}
	console.log(length);
	var domAdded = '<li class="member-list"><span class="list-idx">'+length+'. '+'</span><span class="member-name">'+member.memberName+'</span>';
		domAdded += '	<input type="hidden" name="sharedId" value="'+member.memberId+'">';
		domAdded += '	<span class="member-mail">'+member.memberMail+'</span>';
		domAdded += '	<a href="#" class="remove-member" data-sidx="'+si_idx+'" data-toggle="modal" data-target=".warn-modal.del-member">';
		domAdded += '		<i class="fas fa-times"></i>';	
		domAdded += '	</a>';
		domAdded += '</li>';
		
	if ($sharedList.find('.member-list').length > 0) {
		$sharedList.find('.member-list').last().after(domAdded);
	}
	
	if ($sharedList.find('.member-list').length < 1) {
		if ($('.modify-schedule').length > 0) {
			// 수정시
			var userName = $('aside.left .user-info .user-name').text();
			var userEmail = $('aside.left .user-info .user-email').text();
			var writerAdded = '<li class="member-list writer"><span class="text-success">1. [작성자] </span><span class="member-name">'+userName+'</span>';
				writerAdded += '	<span class="member-mail">'+userEmail+'</span>';
				writerAdded += '</li>';
			$sharedList.append(writerAdded);
		}
		$sharedList.append(domAdded);
	}
	
	$('.add-member-modal').modal('hide');
}


/**
 * 공유 멤버 삭제 처리 모달
 */

function removeMember(){
	var data = {};
	var scIdx = $('.modify-schedule input[name="sc_idx"]').val();
	var $target = null;
	
	$('.warn-modal.del-member').on('show.bs.modal', function(e){
		$target = $(e.relatedTarget);
		var memberMail = $target.siblings('.member-mail').text().trim(); 
		var memberName = $target.siblings('.member-name').text().trim();
		
		$(this).find('.modal-body .member-name').text(memberName);
		$(this).find('.modal-body .member-mail').text(memberMail);
		data = {
			si_idx: $(e.relatedTarget).data('sidx')
		}

	});
	
	$('.del-member').find('.modal-footer .confirm-remove').on('click', function(e){
		if (scIdx == undefined) {
			// 일정 등록시 (화면만 제어)
			leaveTargetMemberList($target);
		} else {
			// 일정 수정시 (DB에서 제거)
			leaveMemberAjax(data, $target);
		}
	});
	
	function leaveTargetMemberList() {
		$target.parents('.member-list').fadeOut();
		$('.del-member').modal('hide');
		$target.parents('.member-list').promise().done(function(){
			$target.parents('.member-list').detach().remove();
			if ($('.member .member-list').length == 0) {
				$('.member section ul .no-result').show();
			}
			$('.member section ul .member-list').each(function(i){
				$(this).find('.list-idx').text((i+1)+". ");
			});
		});
	}
	
	/**
	 * 공유 회원 삭제
	 */
	function leaveMemberAjax(data) {
		// 공유 회원 삭제
		$.ajax({
			url: 'leaveMemberAjax.do',
			data: data,
			type: 'post',
			dataType: 'text',
			cache: false,
			timeout: 30000,
			success: function(data){
				if (data) {
					leaveTargetMemberList();
				}
			},
			error: function(){
				console.log('회원 삭제 중 네트워크 오류 발생');
			}
		})
	}
}

removeMember();