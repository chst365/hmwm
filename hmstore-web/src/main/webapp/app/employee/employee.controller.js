app.controller('EmployeeListController', ['$scope', '$state', '$stateParams', '$resource', '$modal', function($scope, $state, $stateParams, $resource, $modal) {
	// 查询
    $scope.query = function(page,filter){
        page = !page ? 1 : parseInt(page);
        filter = !filter ? "" : filter;
        var $com = $resource(baseUrl + "user/list/"+ page + '/' + 5 + '?code=' + filter);
        $com.get({},function(data){
            // 扩展分页数据，显示页签，最终效果为 < 1 2 3 4 5 >
            data.page_index = page;
            if (data.obj.pageCount <= 0 || page <= data.obj.pageCount) {
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
        $state.go('app.enterprise.employee.list',{search:$scope.search_context});
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
                    return '导入员工信息';
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
                    return '确定要删除员工' + code + "吗？";
                }
            }
        });
        modalInstance.result.then(function () {
            var $com = $resource(baseUrl + 'user/'+uuid);
            $com.delete({},function(data) {
                if(data.success == true){
                    $state.go('app.enterprise.employee.list',null,{reload:true});
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

app.controller('EmployeeEditController', ['$scope', '$state', '$stateParams', '$resource', '$modal', '$http', function($scope, $state, $stateParams, $resource, $modal, $http) {
	$scope.orgs = [];
	var $stores = $resource(baseUrl + "store/list/1/-1");
    $stores.get({},function(data){
    	var orgs = [];
    	angular.forEach(data.obj.records, function(item){
    		orgs.push(new ucn(item.uuid, item.code, item.name));
    	});
    	$scope.orgs.push.apply($scope.orgs, orgs);
    });
    var $enterprises = $resource(baseUrl + "enterprise/" + localStorage.orgUuid);
    $enterprises.get({},function(data){
    	if (data.obj != null) {
    		$scope.orgs.push(new ucn(data.obj.uuid, data.obj.code, data.obj.name));
    	}
    });
    var $roles = $resource(baseUrl + "role/list?orgUuid=" + localStorage.orgUuid);
    $roles.get({},function(data){
        var roles = [];
        angular.forEach(data.obj,function(item){
        	roles.push(new ucn(item.uuid, item.code, item.name));
        });
        $scope.roles = roles;
    });

	function ucn(uuid, code, name) {
		this.uuid = uuid;
		this.code = code;
		this.name = name;

		this.toString = toString;
		function toString() {
			return "[" + code + "]" + name;
		}
	}
	function findUcnByUuid(uuid, ucns) {
		for (index in ucns) {
			if (uuid == ucns[index].uuid) {
				return ucns[index];
			}
		}
	}
	function findUcnsByUuids(uuids, ucns) {
		var result = [];
		for (index_ucns in ucns) {
			for (index_uuids in uuids) {
				if (uuids[index_uuids] == ucns[index_ucns].uuid) {
					result.push(ucns[index_ucns]);
				}
			}
		}
		return result;
	}

	$scope.edit_mode = !!$stateParams.uuid;
    if ($scope.edit_mode) {
        var $com = $resource(baseUrl + 'user/' + $stateParams.uuid);
        $com.get({}, function(data) {
            $scope.data = data.obj;

            var roleUuids = [];
            angular.forEach(data.obj.roles,function(item){
            	roleUuids.push(item.uuid);
            });
            $scope.data.roleUuids = roleUuids;
        },function(data) {
            //alert(data.msg);
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
                    return '确定要提交员工' + $scope.data.code + "吗？";
                }
            }
        });
        modalInstance.result.then(function () {
//        	console.log("提交时的orgUuid = " + angular.toJson($scope.data.org.uuid));
//        	console.log("提交时的org = " + angular.toJson(findUcnByUuid($scope.data.org.uuid, $scope.orgs)));
        	console.log("提交时的org = " + angular.toJson(findUcnsByUuids($scope.data.roleUuids, $scope.roles)));
        	if ($scope.edit_mode) {
        		$http({
                    method: 'PUT',
                    url: baseUrl + 'user',
                    data: {
                    	uuid:$scope.data.uuid,
                        code:$scope.data.code,
                        name:$scope.data.name,
                        password:$scope.data.password,
                        mobile:$scope.data.mobile,
                        operator:localStorage.orgCode,
                        org:findUcnByUuid($scope.data.org.uuid, $scope.orgs),
                        roles:findUcnsByUuids($scope.data.roleUuids, $scope.roles)
                    }
                  }).then(function(response) {
                	  if(response.data.success == true){
                          $state.go('app.enterprise.employee.list');
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
                  })
        	} else {
        		var $com = $resource(baseUrl + 'user');
                $com.save({},{
                	code:$scope.data.code,
                    name:$scope.data.name,
                    password:$scope.data.password,
                    mobile:$scope.data.mobile,
                    operator:localStorage.orgCode,
                    org:findUcnByUuid($scope.data.org.uuid, $scope.orgs),
                    roles:findUcnsByUuids($scope.data.roleUuids, $scope.roles)
                }, function(data){
                	if(data.success == true){
                        $state.go('app.enterprise.employee.list',null,{reload:true});
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
                    return '确定要删除员工' + $scope.data.code + "吗？";
                }
            }
        });
        modalInstance.result.then(function () {
            var $com = $resource(baseUrl + 'user/'+$scope.data.uuid);
            $com.delete({},function(data) {
                if(data.success == true){
                    $state.go('app.enterprise.employee.list',null,{reload:true});
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
    };
}]);

