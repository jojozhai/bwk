'use strict';

/**
 * @ngdoc overview
 * @name testApp
 * @description # testApp
 * 
 * Main module of the application.
 */
// 应用主模块
angular.module('adminApp', [ 'admin', 'userAdminModule', 'posterAdminModule',
		'weixinReplyAdminModule', "messageSendModule", "swiperAdminModule",
		"tagAdminModule", "teacherAdminModule", "lessonAdminModule", "productAdminModule", "orderAdminModule", "withdrawalsAdminModule" ]);