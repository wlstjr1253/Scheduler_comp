<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!-- S: main -->
 <div class="main login">
     <!-- S: wrapper -->
     <div class="wrapper">
         <!-- S: login-form -->
         <div class="login-form">
             <form action="loginData.do" method="post">
                 <h2>Welcome</h2>
                 <ul>
                     <li>
                         <input type="text" placeholder="UserID" name="id" autocomplete="off">
                     </li>
                     <li>
                         <input type="password" placeholder="password" name="pwd" autocomplete="off">
                     </li>
                     <li>
                         <input type="submit" value="Login">
                     </li>
                 </ul>
             </form>
             <dl>
                 <dt><a href="${pageContext.request.contextPath}/main/join.do">회원가입</a></dt>
                 <dd><a href="${pageContext.request.contextPath}/main/findId.do">아이디 찾기</a></dd>
                 <dd><a href="${pageContext.request.contextPath}/main/findPass.do">비밀번호 찾기</a></dd>
             </dl>
         </div>
         <!-- E: login-form -->
     </div>
     <!-- E: wrapper -->

     <!-- S: bg-bubbles -->
     <ul class="bg-bubbles">
         <li></li>
         <li></li>
         <li></li>
         <li></li>
         <li></li>
         <li></li>
         <li></li>
         <li></li>
         <li></li>
         <li></li>
     </ul>
     <!-- E: bg-bubbles -->
 </div>
 <!-- E: main -->