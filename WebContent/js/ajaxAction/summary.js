/**
 * 일정이 곧 다가와요
 */
;(function($){
	function ScSummary() {
		this._$snb = null;
		this._$commingList = null;

		this._init();
		this._evt();
	}

	ScSummary.prototype._init = function() {
		this._$snb = $('.snb');
		this._$commingList = $('.comming-list', this._$snb);
	}

	ScSummary.prototype._evt = function() {
		var that = this;
		$.ajax({
			url: '/Scheduler/sc/commingScheduleSummary.do',
			type: 'post',
			dataType: 'json',
			cache: false,
			timeout: 30000,
			success: function(data){
				if (data.commingList.length > 0) {
					that._$commingList.html('');
					
					for (var i=0; i<data.commingList.length; i++) {
						var comeYear = parseInt(data.commingList[i].sc_startDate.substr(0,4));
						var comeMonth = parseInt(data.commingList[i].sc_startDate.substr(5,2));
						var comeDay = parseInt(data.commingList[i].sc_startDate.substr(8,2));

						var comeDate = new Date(comeYear, comeMonth-1, comeDay);
						
						var now = new Date();
						var dDay = (now.getDate() - comeDate.getDate());
						
						if (dDay > 0) {
							dDay = "+"+dDay;
						}
						if (dDay == 0) {
							dDay = "-day";
						}
																	
						var output = "";
						output += "<li>";
						output += "	<a href='/Scheduler/sc/detailSchedule.do?";
						output += "sc_idx="+data.commingList[i].sc_idx+"'>";
						output += "		"+(i+1)+". "+data.commingList[i].sc_title.substr(0,10);
						
						output += "		&nbsp;D"+dDay;
						output += "	</a>";
						output += "</li>";
						that._$commingList.append(output);
						
					} // for end
				} // if end
			},
			error: function(){
				console.log('일정이 곧 다가와요. 조회 중 네트워크 에러 발생');
			}
		})
	}

	$.fn.scSummary = function(){
		this.each(function(){
			var scSummary = new ScSummary();
		});
		return this;
	}

	$('.snb').scSummary();
	
})(jQuery);