'use strict';
;(function($){
    /**
     * Scheduler Utilities
     */
      $('.history-back-btn').on('click', function(e){
         history.back();
         e.preventDefault();
      });

      function CheckPwd() {
         this.$iptPwd=null;
         this.$iptPwd2=null;

         this.init();
         this.initEvent();
      }
      CheckPwd.prototype.init = function(){
         this.$iptPwd = $('input[name="pwd"]');
         this.$iptPwd2 = $('input[name="pwd2"]');
      }

      CheckPwd.prototype.initEvent = function(){
         var that = this;
         this.$iptPwd2.on('focusout', function(){            
            if (that.$iptPwd.val().length > 0) {
               that.confirmPw();
            }
         })
      }

      CheckPwd.prototype.confirmPw = function(){
         if (this.$iptPwd.val() !== this.$iptPwd2.val()) {
            this.showError();
         }
      }

      CheckPwd.prototype.showError = function() {
         alert('비밀번호가 일치하지 않습니다.')
         this.$iptPwd2.val('');
         this.$iptPwd.focus();
      }

      $.fn.checkPwd = function(){
         this.each(function(index){
            var checkPwd = new CheckPwd();
         });
         return this;
      }
      
      $('.join form').checkPwd(); // 비밀번호 확인
      
      
      /**
       * SNB 메뉴
       */
      function SnbMenu(){
    	  this._$left = null;
    	  this._$leftBtn = null;
    	  
    	  this.init();
    	  this.evt();
    	  this.initMenu();
      }
      
      SnbMenu.prototype.init = function(){
    	  this._$left = $('#main > .left');
    	  this._$leftBtn = $('.left .snb-btn');
      }
      
      SnbMenu.prototype.evt = function() {
    	  var that = this;
    	  this._$leftBtn.on('click', function(e){
    		  e.preventDefault();
    		  that.toggleSnb();
    		  that.toggleSession();
    	  });
      }
      
      SnbMenu.prototype.toggleSnb = function(){
    	  if (this._$left.hasClass('hide')) {
    		  this._$left.css('left', 0).removeClass('hide');
    		  return;
    	  }
    	  this._$left.css('left', '-250px').addClass('hide');
      }
      
      SnbMenu.prototype.toggleSession = function() {
    	  if (sessionStorage.getItem("snb") == "on") {
    		  sessionStorage.setItem("snb", "");
    		  return;
    	  }
    	  sessionStorage.setItem("snb", "on");
      }
      
      SnbMenu.prototype.initMenu = function() {
    	  if (sessionStorage.getItem("snb") == "") {
    		  this._$left.css('left', '-250px').addClass('hide');
    	  }
      }
      
      $.fn.snbMenu = function(){
    	  this.each(function(index){
    		  var snbMenu = new SnbMenu();
    	  });
    	  return this;
      }
      
      $('.snb').snbMenu();
      
      
      function AlarmWin() {
    	  this._$alarmWindow = null;
    	  this._$closeBtn = null;
    	  
    	  this._init();
    	  this._evt();
      }
      
      AlarmWin.prototype._init = function(){
    	  this._$alarmWindow = $('.alarm-window');
    	  this._$closeBtn = $('.btn-close');
      }
      
      AlarmWin.prototype._evt = function(){
    	  var that = this;
    	  this._$closeBtn.on('click', function(){
    		 that._closeWindow($(this)); 
    	  });
      }
      
      AlarmWin.prototype._closeWindow = function($this){
    	  $this.parents('.alarm-window').stop().animate({
    		  right: '-100%',
    		  'background-color': 'rgba(0,0,0,0)'
    	  })
      }
      
      $.fn.alarmWin = function(){
    	  this.each(function(index) {
    		  var alarmWin = new AlarmWin();
    	  });
    	  return this;
      }
      
      $('.alarm-window').alarmWin();
      
})(jQuery);