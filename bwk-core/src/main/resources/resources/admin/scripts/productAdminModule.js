'use strict';
//平台管理模块的配置
angular.module('productAdminModule',[]).config(function($stateProvider) {
	//路由配置
	$stateProvider.state('index.productManage', {
		url: "/productManage",
		controller: "productManageCtrl",
		templateUrl: "admin/views/productManage.html"
	});
//服务配置
}).service("productRestService", function($resource, commonService){
	var config = commonService.getDefaultRestSetting();
	config.findAll = {url:"product/all", method:"GET", isArray:true};
	return $resource("product/:id", {id:"@id"}, config);
//控制器
}).controller('productManageCtrl', function($scope, $uibModal, productRestService, commonService) {

	$scope.pageInfo = commonService.getDefaultPageSetting();
	
	$scope.query = function() {
		var condition = commonService.buildPageCondition($scope.condition, $scope.pageInfo);
		productRestService.query(condition).$promise.then(function(data){
			$scope.pageInfo.totalElements = data.totalElements;
			$scope.products = data.content;
		});
	}
	
	$scope.create = function() {
		$scope.save({
			price: 0,
			saleCount: 0,
			saleCountPlus: 0,
			enable: true
		});
	}
	
	$scope.update = function(product) {
		$scope.save(product);
	}
	
	$scope.save = function(product){
		$uibModal.open({
			size: "lg",
			templateUrl : 'admin/views/productForm.html',
			controller: 'productFormCtrl',
			resolve: {
		        product : function() {return product;},
			}
		}).result.then(function(form){
			if(form.id){
				new productRestService(form).$save().then(function(){
					commonService.showMessage("修改商品信息成功");
				},function(response){
					for (var i = 0; i < $scope.products.length; i++) {
						if(form.id == $scope.products[i].id) {
							$scope.products[i] = productRestService.get({id:form.id});
							break;
						}
					}
				});
			}else{
				new productRestService(form).$create().then(function(product){
					$scope.products.unshift(product);
					commonService.showMessage("新建商品成功");
				});
			}
		});
	}
	
	$scope.remove = function(product) {
		commonService.showConfirm("您确认要删除此商品?").result.then(function() {
			productRestService.remove({id:product.id});
		}).then(function(){
			commonService.showMessage("删除商品成功");
			$scope.products.splice($scope.products.indexOf(product), 1);
			if($scope.products.length == 0){
				$scope.pageInfo.page = $scope.pageInfo.page - 1;
				$scope.query();
			}
		});
	} 
	
	$scope.cleanCondition = function() {
		$scope.condition = {};
		$scope.query();
	}
	
	$scope.query();
	
}).controller('productFormCtrl',function ($scope, $uibModalInstance, product, commonService, lessonRestService, productRestService) {
	
	if(product.id){
		$scope.product = productRestService.get({id: product.id});
	}else{
		$scope.product = product;
	}
	
	$scope.tinymceOptions = commonService.getDefaultTinymceOptions();
	
	$scope.lessons = lessonRestService.findAll();
	
	$scope.save = function(product) {
		$uibModalInstance.close(product);
	};
	
	$scope.doUpload = function(files){
		commonService.uploadImage(files, $scope, function(imageUrl){
			$scope.product.image = imageUrl;
		})		
	}
	
});