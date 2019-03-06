<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<script src="${pageContext.request.contextPath}/js/jquery.mask.js"></script>
<script src="${pageContext.request.contextPath}/js/jquery.validate.js"></script>
<script src="${pageContext.request.contextPath}/js/additional-methods.js"></script>
<script src="${pageContext.request.contextPath}/js/localization/messages_ko.js"></script>
<!-- main -->
<div class="main join no-header">
    <!-- S: bg -->
    <div class="bg">
        <!-- S: content -->
        <div class="content">
        	<img src="${pageContext.request.contextPath}/imgs/curved-bar-spinner.svg"
				id="loading" style="display: none">
            <form action="joinData.do" method="post">
                <fieldset>
                    <legend>Sign Up <span class="icon"><i class="fas fa-edit"></i></span></legend>
                    <ul>
                        <li class="form-group">
                            <label>
                                <span class="title">이름</span>
                                <input type="text" class="required form-control" required aria-required="true" name="name" maxlength="20">
                                <i class="form-group__bar"></i>
                            </label>
                        </li>
                        <li class="form-group id-line">
                            <label>
                                <span class="title">아이디</span>
                                <span id="message_id" class="check-text"></span>
                                <input type="text" class="required form-control" required aria-required="true" name="id" maxlength="20">
                                <i class="form-group__bar"></i>
                                <a href="#" class="btn btn-default id-check">아이디 중복 조회</a>
                            </label>
                        </li>
                        <li class="form-group">
                            <label>
                                <span class="title">비밀번호</span>
                                <input type="password" class="required form-control" required aria-required="true" name="pwd" maxlength="20">
                                <i class="form-group__bar"></i>
                            </label>
                        </li>
                        <li class="form-group">
                            <label>
                                <span class="title">비밀번호 확인</span>
                                <input type="password" class="required form-control" required aria-required="true" name="pwd2" maxlength="20">
                                <i class="form-group__bar"></i>
                            </label>
                        </li>
                        <li class="form-group">
                            <label>
                                <span class="title">핸드폰 번호</span>
                                <input type="tel" name="phone" class="form-control phone" maxlength="15">
                                <i class="form-group__bar"></i>
                            </label>
                        </li>
                        <li class="form-group email-line">
                            <label>
                                <span class="title">이메일</span>
                                <span id="message_mail" class="check-text"></span>
                                <input type="email" name="email" class="required form-control" required aria-required="true">
                                <i class="form-group__bar"></i>
                            </label>
                        </li>
                    </ul>
                    <ul class="btn-list align-center">
                        <li><a href="#" data-toggle="modal" data-target="#back_warn_modal">뒤로가기</a></li>
                        <li><input type="submit" value="회원가입" class="submit-input"></li>
                    </ul>
                </fieldset>
            </form>
        </div>
        <!-- E: content -->
    </div>
    <!-- E: bg -->
</div>

<!-- S: modal -->
<div class="modal fade" id="back_warn_modal" role="dialog">
    <div class="modal-dialog">
        <div class="modal-content">
            <!-- modal-header -->
            <div class="modal-header">
                <h5 class="modal-title">뒤로 가시겠습니까?</h5>
            </div>
            
            <!-- modal-body -->
            <div class="modal-body">
                <p>뒤로 돌아가시면 작성한 내용이 모두 삭제 됩니다</p>
            </div>

            <!-- modal-footer -->
            <div class="modal-footer align-center">
                <button type="button" class="btn btn-secondary" data-dismiss="modal">취소</button>
                <button type="button" class="btn btn-primary" onclick="location.href='${pageContext.request.contextPath}/main/login.do'">뒤로가기</button>
            </div>
        </div>
    </div>
</div>
<!-- E: modal -->

<script>
$('.phone').mask('000-0000-0000');
$('form').validate();
</script>
<script src="${pageContext.request.contextPath}/js/join.js"></script>