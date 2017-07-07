/**
 * 常量的配置
 */
angular.module('app').constant('GG', {
	BASE: '../..',
    role_fun: [
			{
			    "abstract": true,
			    "title": "用户管理",
			    "icon": "glyphicon glyphicon-user",
			    "name": "user",
			    "url": "user",
			    "level-1": true,
			    "submenu": [
			        {
			            "url": "user.list",
			            "title": "用户列表",
			            "name": "user.list",
			            "level-2": true
			        }
		        ]
			},{
			    "abstract": true,
			    "title": "垃圾管理",
			    "icon": "glyphicon  glyphicon-tag",
			    "name": "rubbish",
			    "url": "rubbish",
			    "level-1": true,
			    "submenu": [
			        {
			            "url": "rubbish.catagory",
			            "title": "垃圾分类",
			            "name": "rubbish.catagory",
			            "level-2": true
			        },{
			            "url": "rubbish.list",
			            "title": "垃圾列表",
			            "name": "rubbish.list",
			            "level-2": true
			        }
		        ]
			}
   		]
});
