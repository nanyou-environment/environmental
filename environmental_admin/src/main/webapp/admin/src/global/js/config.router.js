'use strict';

/**
 * 配置路由
 */
angular.module('app').config(['$stateProvider', '$urlRouterProvider', 'APP_MODULES',
        function ($stateProvider, $urlRouterProvider, APP_MODULES) {
            function concat(allLoads) {
                var loads = allLoads || {};
                var ocLazyLoad = ( loads.ocLazyLoad || (loads.ocLazyLoad = [])),
                    lazyLoad = (loads.lazyLoad || (loads.lazyLoad = []));
                var deffers = ['$q.when(1)'];
                var ocIdx = 0;

                function concatAll() {
                    deffers.push('.then(function(){  ');
                    deffers.push(' return $ocLazyLoad.load(ocLazyLoad[' + ocIdx + '],{serie: true})');
                    ocIdx++;
                    deffers.push('})');
                    if (ocIdx < ocLazyLoad.length) {
                        concatAll();
                    } else {
                        concatUI();
                    }
                }

                var uiIdx = 0;

                function concatUI() {
                    deffers.push('.then(function(){  ');
                    deffers.push(' return uiLoad.load(APP_MODULES[lazyLoad[' + uiIdx + ']])');
                    uiIdx++;
                    deffers.push('})');
                    if (uiIdx < lazyLoad.length) {
                        concatUI();
                    }
                }

                concatAll();
                var expr = deffers.join('');
                return {
                    deps: ['$ocLazyLoad', 'uiLoad', '$q',
                        function ($ocLazyLoad, uiLoad, $q) {
                            var q = eval(expr);
                            return q;
                        }
                    ]
                }
            }
            
            // 加载第三方模块使用
            function ocLazyLoad(moduleNames) {
                if (!angular.isArray(moduleNames)) {
                    moduleNames = [moduleNames];
                } else if (moduleNames.length == 0) {
                    return {};
                }

                var deffers = ['$q.when(1)'];
                var ocIdx = 0;

                function concatAll() {
                    deffers.push('.then(function(){  ');
                    deffers.push(' return $ocLazyLoad.load(moduleNames[' + ocIdx + '])');
                    ocIdx++;
                    deffers.push('})');
                    if (ocIdx < moduleNames.length) {
                        concatAll();
                    }
                }

                concatAll();
                var expr = deffers.join('');
                return {
                    deps: ['$ocLazyLoad', '$q',
                        function ($ocLazyLoad, $q) {
                            var q = eval(expr);
                            return q;
                        }
                    ]
                }
            }
            // 加载 app模块使用
            function lazyLoad(moduleNames) {
                if (!angular.isArray(moduleNames)) {
                    moduleNames = [moduleNames];
                } else if (moduleNames.length == 0) {
                    return {};
                }
                var deffers = ['$q.when(1)'];
                var uiIdx = 0;

                function concatAll() {
                    deffers.push('.then(function(){  ');
                    deffers.push(' return uiLoad.load(APP_MODULES[moduleNames[' + uiIdx + ']])');
                    uiIdx++;
                    deffers.push('})');
                    if (uiIdx < moduleNames.length) {
                        concatAll();
                    }
                }

                concatAll();
                var expr = deffers.join('');
                
                return {
                    deps: ['uiLoad', '$q',
                        function (uiLoad, $q) {
                            var q = eval(expr);
                            return q;
                        }
                    ]
                }
            }
           
            $urlRouterProvider
                .otherwise('user/list');

            $stateProvider
	            .state('user', {
	                url: '/user',
	                abstract: true,
	                title: '人员管理',
	                template:'<div ui-view></div>'
	            })
	            .state('user.list', {
	                url: '/list',
	                title: '人员列表',
	                templateUrl: 'user/list/user-list.html',
	                resolve:lazyLoad(['userList'])
	            })
	            .state('rubbish', {
	                url: '/rubbish',
	                abstract: true,
	                title: '垃圾管理',
	                template:'<div ui-view></div>'
	            })
	            .state('rubbish.catagory', {
	                url: '/catagory',
	                title: '垃圾分类',
	                templateUrl: 'rubbish/catagory/rubbish-catagory.html',
	                resolve:lazyLoad(['rubbishCatagory'])
	            })
	            .state('rubbish.list', {
	                url: '/list',
	                title: '垃圾列表',
	                templateUrl: 'rubbish/list/rubbish-list.html',
	                resolve:lazyLoad(['rubbishList'])
	            })
	            
	            
	            
        }
    ]
)
;
