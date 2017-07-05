app.controller('SidebarCtrl', function($scope, $state, $rootScope,GG, $timeout, $localStorage) {
  $scope.ace = $scope.$parent.ace;
  $scope.ace.sidebar = $scope.ace.sidebar || {};
  
  var userType = $scope.app.admin.userType;
  if(userType == 1){
	  $scope.sidebar = GG.role_fun.concat(GG.admin_role_fun);
  }else{
	  $scope.sidebar = GG.role_fun; 
  }

  $rootScope.subMenuOpen = {};
  $rootScope.isSubOpen = function(name) {
	if( !(name in $rootScope.subMenuOpen) ) $rootScope.subMenuOpen[name] = false;
	return $rootScope.subMenuOpen[name];
  }
  $rootScope.isActiveItem = function(name) {
	return $rootScope.activeItems ? $rootScope.activeItems[name] : false;
  }

});


