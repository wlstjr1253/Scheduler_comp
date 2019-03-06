<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ko">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <meta http-equiv="X-UA-Compatible" content="ie=edge">
  <title>달력 만들기</title>
  <!-- fontawesome -->
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/all.css">
  <!-- bootstrap -->
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.css">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap-theme.css">
  <!-- custom css -->
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
  <script src="${pageContext.request.contextPath}/js/jquery-3.3.1.min.js"></script>
  <script src="${pageContext.request.contextPath}/js/bootstrap.js"></script>
</head>
<body>
  <div class="calendar-control text-center">
    <ul class="clearfix">
      <li>
        <a href="#" class="btn btn-primary">이전달</a>
      </li>
      <li class="sel-box">
        <div class="year-list">
          <select name="year" id="year" class="form-control">
            <option value="${year}" selected>${year}</option>
          </select>
          <ul class="sub-year">
		  	<li>
		  		<a href="#">2018</a>
		  	</li>
		  	<li>
		  		<a href="#">2019</a>
		  	</li>
		  	<li>
		  		<a href="#">2020</a>
		  	</li>
		  	<li>
		  		<a href="#">2021</a>
		  	</li>
		  	<li>
		  		<a href="#">2020</a>
		  	</li>
		  </ul>
        </div>
        <div>년</div>
      </li>
      <li class="sel-box">
        <div>
          <select name="month" id="month" class="form-control">
            <c:forEach var="i" begin="1" end="12">
            <option value="${i}" <c:if test="${i == month}">selected</c:if> >${i}</option>
            </c:forEach>
          </select>
        </div>
        <div>월</div>
      </li>
      <li>
        <a href="#" class="btn btn-primary">다음달</a>
      </li>
      <li>
        <a href="#" class="btn btn-danger">오늘</a>
      </li>
    </ul>
  </div>

  <!-- S: 캘린더 테이블 -->
  <div class="container-fluid">
    <div class="row">
    	
		날짜 = ${date} <br>
      	요일 = ${week} <br>
		이달 = ${days} <br>
		이전 달 = ${prevMonth} <br>
		다음 달 = ${nextMonth} <br>
		이전 달 마지막 월요일 = ${prevWeekMonday} <br>
		이전 달 마지막 날 = ${prevLastOfDate} <br>
		<br>
		다음 달 첫번째 요일 = ${nextMonthWeek} <br>
		다음 달 일요일까지 날들 = ${nextMonthDays} <br>
      <table class="table calendar">
        <thead>
          <tr>
            <th>월</th>
            <th>화</th>
            <th>수</th>
            <th>목</th>
            <th>금</th>
            <th>토</th>
            <th>일</th>
          </tr>
        </thead>
        <tbody>
       	<tr>
       		<c:forEach var="i" begin="${prevWeekMonday}" end="${prevLastOfDate}">
     			<td class="prev-month">${i}</td>     		
     		</c:forEach>
         	<c:forEach var="i" items="${days}">
         	<%-- <c:choose>
     			<c:when test="${i.key == 1 && i.value >= 2}">
         			날짜 시작점 찾기 (월~토)
		      		<c:forEach var="j" begin="1" end="${i.value - 2}">
		      		<td></td>
		      		</c:forEach>
		      		<c:set var="day" value="${i.value - 1}"  />
	      		</c:when>
	      	
		      	<c:when test="${i.key == 1 && i.value == 1}">
		      		날짜 시작점 찾기 (일요일)
		      		<c:forEach var="j" begin="1" end="6">
		      		<td></td>
		      		</c:forEach>
		      		<c:set var="day" value="${i.value - 1}"  />
		      	</c:when>
     		</c:choose> --%>
     		
     		
         	<c:choose>
         	<c:when test="${i.value == 1 }">
         		<td
         		<c:if test="${date == i.key}">class="today"</c:if>
         		>${i.key}</td>
         	</tr>
         	<tr>
         	</c:when>
         	
         	<c:when test="${i.value <= 7 }">
         		<td
         		<c:if test="${date == i.key}">class="today"</c:if>
         		>${i.key}</td>
         	</c:when>
         	</c:choose>
         	
         	</c:forEach>
         	
         	<c:forEach var="i" items="${nextMonthDays}">
         		<td>${i.key}</td>
         	</c:forEach>
         	</tr>
        </tbody>
      </table>
    </div>
  </div>
  <!-- E: 캘린더 테이블 -->
</body>
</html>