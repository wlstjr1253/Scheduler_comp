/**
 * 일별 스케쥴 조회
 */
$('.schedule-detail').on('show.bs.modal', function(e) {
	 var $scs = $(e.relatedTarget).parents('td').find('.sc');
	 var scsData = {
		year: $('#year option:selected').val(),
		month: $('#month option:selected').val(),
		day: $(e.relatedTarget).parents('td').text().substring(0,2).trim()
	 }
	 $('.modal-title .day').text(scsData.day);
	scheduleDetailShow(scsData); 
 })
	 
 function scheduleDetailShow(data){
	 if (data.length < 1) return;
	 $.ajax({
		 url: 'getScheduleByDay.do',
		 data: data,
		 type: 'post',
		 dataType: 'json',
		 cache: false,
		 timeout: 30000,
		 success: function(data){
			 
			 if (data) {
				 // showModalInData(data);
				 var template = $('#schedulesDayModal').html();
				 var html = Handlebars.compile(template);
				 var contextTemp = html(data);
				 $('.schedule-detail .modal-body .result-box ul').html('');
				 $('.schedule-detail .modal-body .result-box ul').html(contextTemp);
				 $('.schedule-detail .modal-body .result-box').mCustomScrollbar();
			 }
			 
		 },
		 error: function(){
			 console.log('일별 스케쥴 조회중 네트워크 오류 발생');
		 }
	 })
 }
	 
 function showModalInData(data) {
	 for (var i = 0; i < data.list.length; i++) {
		 var listOutput = "";
	 listOutput += "<li class='clearfix'>";
	 listOutput += "	<a href='#'>";
	 listOutput += "		<div class='list-content'>";
	 listOutput += "			<h4>"+data.list[i].sc_title+"</h4>";
	 listOutput += "			<p> 날짜 : "+data.list[i].sc_startDate+" 부터 " + data.list[i].sc_endDate+"</p>";
	 listOutput += "			<p> 시간 : "+data.list[i].sc_startTime+" ~ "+data.list[i].sc_endTime+"</p>";
	 listOutput += "			<p> 장 소 :"+data.list[i].sc_place+"</p>";
	 listOutput += "			<p> 메모 <br>"+data.list[i].sc_content+"</p>";
	 listOutput += "		</div>";
	 listOutput += "	</a>";
	 listOutput += "	<div class='list-control'>";
	 listOutput += "		<a href='#' class='add-favorite'>";
	 listOutput += "			<span class='icon'>";
	 listOutput += "				<i class='fas fa-star'></i>";
	 listOutput += "			</span>";
	 listOutput += "		</a>";
	 listOutput += "		<a href='#' class='remove-list'>";
	 listOutput += "			<span class='icon'>";
	 listOutput += "				<i class='fas fa-trash-alt'></i>";
	 listOutput += "			</span>";
	 listOutput += "		</a>";
	 listOutput += "	</div>";
	 listOutput += "</li>";
	 var $resBox = $('.comming-list .result-box ul');
		 $resBox.append(listOutput);
	 } 
 }