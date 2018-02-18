app.controller('StoreListController', ['$scope', '$state', '$stateParams', '$resource', '$modal','modalProvider', function($scope, $state, $stateParams, $resource, $modal,modalProvider) {
	//查询
    $scope.query = function(page,filter){
         page = !page ? 1 : parseInt(page);
         filter = !filter ? "" : filter;
         var $com = $resource(baseUrl+'store/list/:page/5?code=' + filter, {page: '@page', filter: '@filter'
         });
         if (!page) {
           page = 1;
         } else {
           page = parseInt(page);
         }
        $com.get({page: page, filter: filter}, function (data) {
          //扩展分页数据，显示页签，最终效果为  < 1 2 3 4 5 >
          data.page_index = page;
          if ((data.page_index <= data.obj.pageCount)||(data.page_index>0&&data.obj.pageCount==0)) {
            data.pages = [];    //页签表
            var N = 5;          //每次显示5个页签
            var s = Math.floor(page / N) * N;
            if (s == page)s -= N;
            s += 1;
            var e = Math.min(data.obj.pageCount, s + N - 1)
            for (var i = s; i <= e; i++)
              data.pages.push(i)//1 2 3 4
            data.previous = (page != 1);
            data.next = (page != data.pages.length);
            $scope.data = data;
            console.log("data=" + angular.toJson(data))
          }
        });
      };
  //搜索跳转
    $scope.search = function(a){
        $state.go('app.enterprise.store.list',{filter:$scope.search_context});
    }
    //自定义操作处理，其中1为删除所选记录
    $scope.exec = function(){
        if($scope.operate=="1"){
            var ids = [];
            angular.forEach($scope.data.results,function(item){
                if(item.selected){
                    ids.push(item.id);
                }
            });
            if(ids.length>0){
                //弹出删除确认
                var modalInstance = $modal.open({
                    templateUrl: 'admin/confirm.html',
                    controller: 'ConfirmController',
                    size:'sm',
                });
                modalInstance.result.then(function () {
                      var $com = $resource($scope.app.host + "/news/deletes/?");
                      $com.delete({'ids':ids.join(',')},function(){
                          $state.go('app.news.list');
                      });
                });
            }
        }
    }
    //根据url参数（分页、搜索关键字）查询数据
    $scope.query($stateParams.page,$stateParams.search);
    $scope.import = function (size) {
        var modalInstance = $modal.open({
            templateUrl: 'tpl/app/modal/import/importModalContent.html',
            controller: 'ImportModalController',
            size: size,
            resolve: {
                title: function() {
                    return '导入门店信息';
                }
            }
        });
      modalInstance.result.then(function (selectedItem) {
            console.log('ok');
        }, function () {
            console.log('Modal dismissed at: ' + new Date());
        });
    }
    $scope.remove = function(uuid) {
         var modalInstance =  modalProvider.modalInstance('点击后删除本行记录',0);
         modalInstance.result.then(function (selectedItem) {
              var $com = $resource(baseUrl+'store/' + uuid);
              $com.delete(function (data) {
                    if(data.success==false){
                          var modalInstance = modalProvider.modalInstance(data.msg,1);
                    }else{
                          if(data.success==true) {
                                $scope.obj = [];
                                angular.forEach($scope.data.obj.records, function (d) {
                                  if (d.uuid != uuid) {
                                       $scope.obj.push(d);
                                  }
                                });
                          $scope.data.obj.records = $scope.obj;
                          $state.go('app.enterprise.store.list',null,{
                            reload:true
                          })
                          }
                      }
               });
        }, function () {
        console.log('Modal dismissed at: ' + new Date());
      });
    }
}]);

app.controller('StoreEditController', ['$scope', '$state', '$stateParams','$resource','$http', '$modal','modalProvider', function($scope, $state, $stateParams, $resource,$http, $modal,modalProvider) {
    $scope.edit_mode = !!$stateParams.uuid;
    if ($scope.edit_mode) {
           var $com = $resource(baseUrl+'store/'+$stateParams.uuid);
           $com.get({uuid: $stateParams.uuid}, function(data) {
                 $scope.data = data;
           });
    }else {
           $scope.data = {};
    }
     $scope.submit = function() {
         var modalInstance = modalProvider.modalInstance('是否保存门店?',1);
          modalInstance.result.then(function (selectedItem) {
                if($scope.data == null){
                }else {
                     if (!$scope.edit_mode)//新增
                         {
                           $http({
                             method: 'POST',
                             url: baseUrl+'store',
                             data: {
                               "uuid": null,
                               "code": $scope.data.obj.code,
                               "name": $scope.data.obj.name,
                               "remark": $scope.data.obj.remark,
                               "address": $scope.data.obj.address
                             }
                           }).then(function (data) {
                             console.log("data2=" + angular.toJson(data));
                             if(data.data.success == true){
                                 $state.go('app.enterprise.store.list');
                             }else{
                                 var modalInstance =  modalProvider.modalInstance(data.data.msg,1);
                             }
                           })
                   }else {
                     $http({
                       method: 'PUT',
                       url: baseUrl+'store',
                       data: {
                         "uuid": $scope.data.obj.uuid,
                         "code": $scope.data.obj.code,
                         "name": $scope.data.obj.name,
                         "remark": $scope.data.obj.remark,
                         "address": $scope.data.obj.address
                       }
                     }).then(function (data) {
                       if(data.data.success == true){
                           $state.go('app.enterprise.store.list');
                       }else{
                           var modalInstance =  modalProvider.modalInstance(data.data.msg,1);
                       }
                     })
                   }
                 }
               }, function () {
                      console.log('Modal dismissed at: ' + new Date());
               });
       };
    $scope.delete = function(uuid) {
      var modalInstance = modalProvider.modalInstance('这里将删除该主机信息',0);
      modalInstance.result.then(function (selectedItem) {
            var $com = $resource(baseUrl+'store/'+uuid);
            $com.delete(function(data){
              if(data.success == true){
                $state.go('app.enterprise.store.list');
              }else{
                var modalInstance =modalProvider.modalInstance(data.msg,1);
              }
            });
      }, function () {
        console.log('Modal dismissed at: ' + new Date());
      });
    };
}]);

