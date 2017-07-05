/**
 * 认证相关
 */
angular
	.module('app')
	.factory(
		'authInterceptor', [
			'$rootScope',
			'$q',
			'$window',
			'$injector',
			'GG',
			'GlobalUtilsService',
			function($rootScope, $q, $window, $injector, GG,
					GlobalUtilsService) {
				return {
					request: function(config) {
						config.headers = config.headers || {};
						if ($window.sessionStorage.token) {
							config.headers['access-token'] = $window.sessionStorage.token;
						}
						return config;
					},
					response: function(response) {
						return response || $q.when(response);
					},
					responseError: function(rejection) {
						if (rejection && rejection.status === 403) {
							location.href="login.html";
						}else{
							GlobalUtilsService.gritterAlert('error','服务器异常');
						}
						return rejection || $q.when(rejection);
					},
					requestError: function(rejection) {
						GlobalUtilsService.gritterAlert('error','服务器异常');
						return rejection || $q.when(rejection);
					}

				};
			}
		]);


angular
.module('app')
.config(
	[
		'$httpProvider',
		function($httpProvider) {
			$httpProvider.interceptors.push('authInterceptor');
		}
	]);

angular.module('app').service('authServ', function($window) {
	this.isLoggedIn = function() {
		if ($window.sessionStorage.token) {
			return true;
		} else {
			return false;
		}
	}
});
