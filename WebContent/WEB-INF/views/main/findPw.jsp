<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!-- main -->
<div class="main find-form">
   <!-- S: bg -->
   <div class="bg">
       <!-- S: content -->
       <div class="content">
           
	      <div>
	          <p class="legend">비밀번호 찾기 <span class="icon"><i class="fas fa-fingerprint"></i></span></p>
	          <ul>
	            <li class="form-group">
	                <label>
	                    <span class="title">아이디</span>
	                    <input type="text" class="required form-control" required aria-required="true" name="userName" maxlength="10">
	                </label>
	            </li>
	            <li class="form-group">
	               <label>
	                   <span class="title">가입시 입력한 이메일</span>
	                   <input type="email" name="email" class="required form-control auth-mail" required aria-required="true">
	                   <a href="#" class="ok-btn btn-success btn-srch">검색</a>
	               </label>
	           </li>
	          </ul>
	      </div>
	      
	       <!-- S: result -->
           <div class="result">
               
           </div>
           <!-- E: result -->
	
	       <!-- S: btn-footer -->
	       <div class="btn-footer">
	           <!-- S: btn-list -->
	           <ul class="btn-list">
	               <li><a href="#" class="back-btn">뒤로가기</a></li>
	               <li><a href="./login.html?id=summerjava" class="ok-btn">로그인</a></li>  
	           </ul>
	           <!-- E: btn-list -->
	       </div>
	       <!-- E: btn-footer -->
 
       </div>
       <!-- E: content -->
   </div>
   <!-- E: bg -->
</div>

<script id="findRes" type="text/x-handlebars-template">
{{#list}}
<p>당신의 비밀번호는 <a href="${pageContext.request.contextPath}/main/login.do" class="strong">{{result}}</a>입니다.</p>
{{/list}}
</script>

<script src="${pageContext.request.contextPath}/js/ajaxAction/findPw.js"></script>