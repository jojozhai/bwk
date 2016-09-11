'use strict';

/**
 * @ngdoc overview
 * @name testApp
 * @description
 * # testApp
 *
 * Main module of the application.
 */
//应用主模块
angular.module('bwkApp', ['weixin', 'userWeixinModule', 'posterWeixinModule'
//应用配置	
]).config(function($stateProvider, $urlRouterProvider, $httpProvider) {
	
//应用主控制器，所有控制器的父，定义整个应用公用的作用域
}).controller('mainCtrl', function($scope) {
	
	
//默认设置服务
});