app.controller('MainController', function (GG,$window,$scope, $rootScope, $http, $timeout, $location/**, cfpLoadingBar*/,authServ,$uibModal,$interval) {

	$scope.ace = $scope.ace || {};
	$scope.app = {};
	
	if (angular.isDefined($window.sessionStorage.admin)) {
        $scope.app.admin = JSON.parse($window.sessionStorage.admin);
    }
	
	$scope.ace.settings = {
			'is_open': false,
			'open': function() {
				$scope.ace.settings.is_open = !$scope.ace.settings.is_open;
			},
			
			'navbar': false,
			'sidebar': false,
			'breadcrumbs': false,
			'hover': false,
			'compact': false,
			'highlight': false,
			
			'skinColor': '#438EB9',
			'skinIndex': 0
		};

	$scope.ace.site = {
		avatars: GG.BASE + '/admin/src/global/image/user.jpg',
		logo: GG.BASE + '/admin/src/global/image/image-1.jpg',
		brand_text : '垃圾回收',
		brand_icon : 'fa fa-leaf',
		version : '1.4'
	};

	//sidebar variables
	$scope.ace.sidebar = {
		'minimized': false,//used to collapse/expand
		'toggle': false,//used to toggle in/out mobile menu
		'reset': false//used to reset sidebar (for sidebar scrollbars, etc)
	};

	
	$rootScope.viewContentLoading = false;
	$rootScope.$on('$stateChangeStart', function(event) {
		$rootScope.viewContentLoading = true;
		$scope.ace.sidebar.toggle = false;
		if (!authServ.isLoggedIn()) {
			event.preventDefault();
			location.href="login.html";
		}
	});
	$rootScope.$on('$stateChangeSuccess', function(event){ 
		$rootScope.viewContentLoading = false;
	});
	$rootScope.$on('$stateChangeError', function(event, p1, p2, p3){ 
		$rootScope.viewContentLoading = false;
		location.href="login.html";
	});
	
	
	$scope.logout = function(){
		$window.sessionStorage.clear();
		location.href="login.html";
	}
	

});