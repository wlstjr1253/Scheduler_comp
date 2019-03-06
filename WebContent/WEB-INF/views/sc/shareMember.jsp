<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<li class="member">
	<c:if test="${loginUser == writer.id}">
	<a href="#" data-toggle="modal" data-target="#add-member-modal" 
				class="add-btn">멤버추가
		<span class="icon">
			<i class="fas fa-user-plus"></i>
		</span>
	</a>
	</c:if>
	<section>
		<h3>공유된 회원</h3>
		<div class="shared-list">
			<ul>
				<c:if test="${empty invitedUserList}">
				<li class="no-result">
					공유된 회원이 없습니다. 
					<span class="icon">
						<i class="fas fa-user-times"></i>
					</span>
				</li>
				</c:if>
				
				<c:if test="${!empty invitedUserList}">
				<li class="member-list writer">
					<span class="text-success">1. [작성자]</span> 
					<span class="member-name">${writer.name}</span>
					<span class="member-mail">${writer.email}</span>
				</li>
				<c:forEach var="list" items="${invitedUserList}" varStatus="status">
				<li class="member-list">
					<span class="list-idx">${(status.index)+2}. </span>
					<span class="member-name">${list.memberName}</span>
					<span class="member-mail">${list.memberMail}</span>
					<c:if test="${loginUser == writer.id}">
					<a href="#" class="remove-member" data-toggle="modal"
								data-target=".warn-modal.del-member"
								data-sidx="${list.si_idx}">
						<i class="fas fa-times"></i>
					</a>
					</c:if>
				</li>
				</c:forEach>
				</c:if>
				<%-- <li class="no-result">
					공유된 회원이 없습니다. 
					<span class="icon">
						<i class="fas fa-user-times"></i>
					</span>
				</li>
				<li>1. 캡틴 아메리카 
					<span class="member-mail">captain@avengers.com</span>
					<a href="#" class="remove-member" data-toggle="modal"
								data-target=".warn-modal.del-member">
						<i class="fas fa-times"></i>
					</a>
				</li>
				<li>2. 헐크 
					<span class="member-mail">hulk@avengers.com</span>
					<a href="#" class="remove-member" data-toggle="modal"
								data-target=".warn-modal.del-member">
						<i class="fas fa-times"></i>
					</a>
				</li>
				<li>3. 블랙 팬서 
					<span class="member-mail">black_pencer@avengers.com</span>
					<a href="#" class="remove-member" data-toggle="modal"
								data-target=".warn-modal.del-member">
						<i class="fas fa-times"></i>
					</a>
				</li> --%>
			</ul>
		</div>
	</section>
</li>