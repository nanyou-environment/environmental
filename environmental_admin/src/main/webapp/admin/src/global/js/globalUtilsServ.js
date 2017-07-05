angular.module('app')
.provider('$gritter', function() {
  this.$get = function () {
	return {
		add: jQuery.gritter.add,
		remove: jQuery.gritter.remove,
		removeAll: jQuery.gritter.removeAll
	}
  }
})

angular.module('app').factory('GlobalUtilsService', [ 'GG','$gritter',
    function ( GG,$gritter) {
	
		var DEFAULT_DATE_FORMAT = "yyyy-MM-dd";
		var DEFAULT_TIME_FORMAT = "HH:mm:ss";
		var DEFAULT_DATETIME_FORMAT = "yyyy-MM-dd hh:mm:ss";
		var SHORT_DATETIME_FORMAT = "yyyy-MM-dd hh:mm";
		
		Date.prototype.formatDateTime = function(format) {
		       var date = {
		              "M+": this.getMonth() + 1,
		              "d+": this.getDate(),
		              "h+": this.getHours(),
		              "m+": this.getMinutes(),
		              "s+": this.getSeconds(),
		              "q+": Math.floor((this.getMonth() + 3) / 3),
		              "S+": this.getMilliseconds()
		       };
		       if (/(y+)/i.test(format)) {
		              format = format.replace(RegExp.$1, (this.getFullYear() + '').substr(4 - RegExp.$1.length));
		       }
		       for (var k in date) {
		              if (new RegExp("(" + k + ")").test(format)) {
		                     format = format.replace(RegExp.$1, RegExp.$1.length == 1
		                            ? date[k] : ("00" + date[k]).substr(("" + date[k]).length));
		              }
		       }
		       return format;
		}
		
		function formatTime(date,format){
			return date.formatDateTime(format);
		}
		
		var gritter = {
			'count': 0,
			'light': false,
			'show': function(id) {
				var options = angular.copy(gritter[id]);
			
				if( !('before_open' in options) ) options.before_open = function() { gritter.count = gritter.count + 1 }
				if( !('after_close' in options) ) options.after_close = function() { gritter.count = gritter.count - 1 }
				
				options['class_name'] = (options['class_name'] || '') + (gritter.light ? ' gritter-light' : '');
				
				$gritter.add(options);
			},
			'clear': function() {
				$gritter.removeAll();
				gritter.count = 0;
			},
			'info': {
				title: '提示信息',
				class_name: 'gritter-info'
			},
			'warning': {
				title: '提示信息',
				class_name: 'gritter-warning'
			},
			'success': {
				title: '提示信息',
				class_name: 'gritter-success'
			},
			'error': {
				title: '提示信息',
				class_name: 'gritter-error'
			}
		};
		
		function gritterAlert(type,text){
			gritter[type].text = text;
			gritter.show(type);
		}
	
        var service = {
    		DEFAULT_DATE_FORMAT:DEFAULT_DATE_FORMAT,
			DEFAULT_TIME_FORMAT:DEFAULT_TIME_FORMAT,
			DEFAULT_DATETIME_FORMAT:DEFAULT_DATETIME_FORMAT,
			SHORT_DATETIME_FORMAT:SHORT_DATETIME_FORMAT,
    		formatTime : formatTime,
    		gritterAlert:gritterAlert
        };
        return service;
    }
]);