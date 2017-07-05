angular.module('app').factory('ConfirmService', [ '$q','$uibModal',
    function ($q,$uibModal) {
	
		var show = function(content){
			var deferred = $q.defer();
			var modalInstance = $uibModal.open({
			  animation: true,
			  templateUrl: 'global/confirmModal.html',
			  controller: function ($scope, $uibModalInstance,items) {
				  $scope.content = items;
				  $scope.ok = function () {
					$uibModalInstance.close(true);
				  };
				  $scope.cancel = function () {
					$uibModalInstance.dismiss('cancel');
				  };
			  },
			  resolve: {
	                items: function () {
	                    return content;
	                }
	            }
			});
			modalInstance.result.then(function (result) {
				deferred.resolve(true);
           }, function (reason) {  
               
           });
			 return deferred.promise;
		}
	
        var service = {
        		show:show
        };
        return service;
    }
]);