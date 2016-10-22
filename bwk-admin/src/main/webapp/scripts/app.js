'use strict';

/**
 * @ngdoc overview
 * @name testApp
 * @description # testApp
 * 
 * Main module of the application.
 */
// 应用主模块
angular.module('adminApp', [ 'admin', 'userAdminModule', 'posterAdminModule', 'buttonAdminModule', 'paramAdminModule', 
		'weixinReplyAdminModule', "messageSendModule", "swiperAdminModule", "commentAdminModule", 'clearingTreeAdminModule',
		"tagAdminModule", "teacherAdminModule", "lessonAdminModule", 
		"productAdminModule", "orderAdminModule", "withdrawalsAdminModule", 
		"heraldAdminModule", "rebateConfigAdminModule"]);
