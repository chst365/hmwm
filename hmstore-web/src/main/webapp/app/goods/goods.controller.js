app.controller('GoodsListController', ['$scope', '$state', '$stateParams', '$resource', '$modal', function($scope, $state, $stateParams, $resource, $modal) {
	$scope.isHeadquarters = false;
	var $enterprises = $resource(baseUrl + "enterprise/list");
	$enterprises.get({},function(data){
		for (index in data.obj) {
			if (localStorage.orgUuid == data.obj[index].uuid) {
				$scope.isHeadquarters = true;
				return;
			}
		}
	});
	// 查询
    $scope.query = function(page,filter){
        page = !page ? 1 : parseInt(page);
        filter = !filter ? "" : filter;
        var $com = $resource(baseUrl + "article/list/"+ page + '/' + 5 + '?code=' + filter);
        $com.get({},function(data){
        	if (data.obj.pageCount <= 0 || page <= data.obj.pageCount) {
                // 扩展分页数据，显示页签，最终效果为 < 1 2 3 4 5 >
                data.page_index = page;
                data.pages = [];    // 页签数
                var N = 5;          // 每次显示5个页签
                var s = Math.floor(page/N)*N;
                if(s==page)s-=N;
                s += 1;
                var e = Math.min(data.obj.pageCount,s+N-1) // s+N-1最小值5
                for(var i=s;i<=e;i++)
                    data.pages.push(i)
                data.previous = (page !=1);
                data.next = (page != data.pages.length);
                $scope.data = data;
                $scope.search_context = filter;
        	}
        });
    }
    // 搜索跳转
    $scope.search = function(){
        $state.go('app.store.goods.list',{filter:$scope.search_context});
    }
    // 根据url参数（分页、搜索关键字）查询数据
    $scope.query($stateParams.page,$stateParams.search);

    $scope.import = function (size) {
        var modalInstance = $modal.open({
            templateUrl: 'tpl/app/modal/import/importModalContent.html',
            controller: 'ImportModalController',
            size: size,
            resolve: {
                title: function() {
                    return '导入商品信息';
                }
            }
        });

        modalInstance.result.then(function (selectedItem) {
            console.log('ok');
        }, function () {
            console.log('Modal dismissed at: ' + new Date());
        });
    }

    $scope.remove = function(uuid, code) {
        var modalInstance = $modal.open({
            templateUrl: 'tpl/app/modal/confirmModalContent.html',
            controller: 'ConfirmModalController',
            size: 'sm',
            resolve: {
                title: function() {
                    return '提示';
                },
                content: function() {
                    return '确定要删除商品' + code + "吗？";
                }
            }
        });
        modalInstance.result.then(function () {
            var $com = $resource(baseUrl + 'article/'+uuid);
            $com.delete({},function(data) {
            	if(data.success == true) {
            		$state.transitionTo('app.store.goods.list', null, {reload:true});
                }else{
                    var modalInstance = $modal.open({
                      templateUrl: 'tpl/app/modal/confirmModalContent.html',
                      controller: 'ConfirmModalController',
                      size: 'sm',
                      resolve: {
                        title: function() {
                          return '提示';
                        },
                        content: function() {
                          return data.msg ;
                        }
                      }
                    });
              }
            });
        });
    }
}]);


app.controller('GoodsEditController', ['$scope', '$state', '$stateParams', '$resource', '$modal', '$http', function($scope, $state, $stateParams, $resource, $modal, $http) {
    $scope.edit_mode = !!$stateParams.uuid;
    if ($scope.edit_mode) {
        var $com = $resource(baseUrl + 'article/' + $stateParams.uuid);
        $com.get({}, function(data) {
            $scope.data = data.obj;
        },function(data) {
            alert(data.msg);
        });
    } else {
        $scope.data = {};
    }

    $scope.submit = function() {
        var modalInstance = $modal.open({
            templateUrl: 'tpl/app/modal/confirmModalContent.html',
            controller: 'ConfirmModalController',
            size: 'sm',
            resolve: {
                title: function() {
                    return '提示';
                },
                content: function() {
                    return '确定要提交商品' + $scope.data.code + "吗？";
                }
            }
        });
        modalInstance.result.then(function () {
        	if ($scope.edit_mode) {
        		$http({
                    method: 'PUT',
                    url: baseUrl + 'article',
                    data: {
                    	uuid:$scope.data.uuid,
                    	code:$scope.data.code,
                        name:$scope.data.name,
                        categoryCode:$scope.data.categoryCode,
                        categoryName:$scope.data.categoryName,
                    }
                  }).then(function (response) {
                	  if(response.data.success == true){
                          $state.go('app.store.goods.list');
                      } else {
                          var modalInstance = $modal.open({
                            templateUrl: 'tpl/app/modal/confirmModalContent.html',
                            controller: 'ConfirmModalController',
                            size: 'sm',
                            resolve: {
                              title: function() {
                                return '提示';
                              },
                              content: function() {
                                return data.msg ;
                              }
                            }
                          });
                      }
                  })
        	} else {
        		var $com = $resource(baseUrl + 'article');
                $com.save({},{
                    code:$scope.data.code,
                    name:$scope.data.name,
                    categoryCode:$scope.data.categoryCode,
                    categoryName:$scope.data.categoryName,
                }, function(data){
                	if(data.success == true){
                        $state.go('app.store.goods.list');
                    } else {
                        var modalInstance = $modal.open({
                          templateUrl: 'tpl/app/modal/confirmModalContent.html',
                          controller: 'ConfirmModalController',
                          size: 'sm',
                          resolve: {
                            title: function() {
                              return '提示';
                            },
                            content: function() {
                              return data.msg ;
                            }
                          }
                        });
                    }
                });
        	}
        });
    };

    $scope.delete = function() {
        var modalInstance = $modal.open({
            templateUrl: 'tpl/app/modal/confirmModalContent.html',
            controller: 'ConfirmModalController',
            size: 'sm',
            resolve: {
                title: function() {
                    return '提示';
                },
                content: function() {
                    return '确定要删除商品' + $scope.data.code + "吗？";
                }
            }
        });
        modalInstance.result.then(function () {
            var $com = $resource(baseUrl + 'article/'+$scope.data.uuid);
            $com.delete({},function(data){
            	if(data.success == true){
                    $state.go('app.store.goods.list');
                } else {
                    var modalInstance = $modal.open({
                      templateUrl: 'tpl/app/modal/confirmModalContent.html',
                      controller: 'ConfirmModalController',
                      size: 'sm',
                      resolve: {
                        title: function() {
                          return '提示';
                        },
                        content: function() {
                          return data.msg ;
                        }
                      }
                    });
                }
            });
        });
    };
}]);

