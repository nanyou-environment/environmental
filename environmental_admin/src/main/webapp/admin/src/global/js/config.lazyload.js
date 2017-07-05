//app所需模块
angular.module('app').constant('APP_MODULES', {
	
	
});
//第三方模块
angular.module('app').config(['$ocLazyLoadProvider',
function ($ocLazyLoadProvider) {
    var modules = {
    		
    		'ngCkeditor':['../bower_components/ngCkeditor/ng-ckeditor.css','../bower_components/ngCkeditor/ckeditor/ckeditor.js','../bower_components/ngCkeditor/ng-ckeditor.js'],
        	'treeControl':['../bower_components/ace_admin/components/angular-tree-control/angular-tree-control.js']
    };

        function toMudules(json) {
            var arr = [];
            for (var key in json) {
                arr.push({
                    name: key,
                    files: json[key]
                });
            }
            return arr;
        }

        $ocLazyLoadProvider.config({
            debug: false,
            modules: toMudules(modules)
        });
    }
]).config(['$controllerProvider', '$compileProvider', '$filterProvider', '$provide',
           function ($controllerProvider, $compileProvider, $filterProvider, $provide) {

    // 懒加载 controller, directive ,service
    app.controller = $controllerProvider.register;
    app.directive = $compileProvider.directive;
    app.filter = $filterProvider.register;
    app.factory = $provide.factory;
    app.service = $provide.service;
    app.constant = $provide.constant;
    app.value = $provide.value;
}
]);