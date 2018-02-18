app.controller('ComputerListController', ['$scope', '$state', '$stateParams', '$resource', '$modal','hemaProvider','modalProvider', function($scope, $state, $stateParams, $resource, $modal,hemaProvider,modalProvider) {
	//查询
    $scope.query = function(page,filter){
      hemaProvider.getStoreHost().success(function(data){
          $scope.data = data;
      })
    }
    //根据url参数（分页、搜索关键字）查询数据
    $scope.query($stateParams.page,$stateParams.search);
   //移除
    $scope.remove = function(uuid) {
          var modalInstance =  modalProvider.modalInstance('点击后删除本行记录',0);
          modalInstance.result.then(function (selectedItem) {
                $scope.obj = [];
                angular.forEach($scope.data.obj, function (d) {
                     if (d.uuid != uuid) {
                       $scope.obj.push(d);
                     }
                });
                $scope.data.obj = $scope.obj;
                hemaProvider.removeStoreHost(uuid).success(function(data){
                });
          }, function () {
               console.log('Modal dismissed at: ' + new Date());
         });
    }
}]);
app.controller('ComputerEditController', ['$scope', '$state', '$stateParams','$resource', '$http', '$modal','hemaProvider','modalProvider', function($scope, $state, $stateParams,$resource,$http,$modal,hemaProvider,modalProvider) {
    $scope.edit_mode = !!$stateParams.uuid;
    if ($scope.edit_mode) {
        hemaProvider.editStoreHost($stateParams.uuid).success(function (data) {
            $scope.hostName = data.obj.name;
            $scope.hostIp = data.obj.ip;
            $scope.hostMac = data.obj.macAddress;
            $scope.hostallowAccess=data.obj.allowAccess;
            $scope.data1=data;
         })
    } else {
         $scope.data = {};
    }
    $scope.submit = function() {
        var modalInstance =  modalProvider.modalInstance('是否保存主机？',1);
        modalInstance.result.then(function (selectedItem) {
                if(!$scope.edit_mode) {
                   $scope.data={
                      "uuid": null,
                      "name": $scope.hostName,
                      "ip": $scope.hostIp,
                      "macAddress": $scope.hostMac,
                      "allowAccess": $scope.hostallowAccess,
                      "storeUuid": localStorage.orgUuid
                  }
                  hemaProvider.submitStoreHost($scope.data).success(function (data) {
                      if(data.success == true){
                             $state.go('app.store.computer.list');
                      }else{
                             var modalInstance =  modalProvider.modalInstance(data.msg,1);
                      }
                 })
               }else{
                    $scope.data={
                        "uuid": $scope.data1.obj.uuid,
                        "name": $scope.hostName,
                        "ip": $scope.hostIp,
                        "macAddress": $scope.hostMac,
                        "allowAccess": $scope.hostallowAccess,
                        "storeUuid": localStorage.orgUuid
                    }
                 hemaProvider.editPutStoreHost($scope.data).success(function (data) {
                 if(data.success == true){
                        $state.go('app.store.computer.list');
                 }else{
                        var modalInstance = modalProvider.modalInstance(data.msg,1);
                 }
               })
            }
      }, function () {
        console.log('Modal dismissed at: ' + new Date());
      });
    };
    $scope.delete = function(uuid) {
       var modalInstance = modalProvider.modalInstance('这里将删除该主机信息',0);
       modalInstance.result.then(function (selectedItem) {
            hemaProvider.deletStoreHost(uuid).success(function (data) {
                if(data.success == true){
                      $state.go('app.store.computer.list')
                 }else{
                      var modalInstance = modalProvider.modalInstance(data.msg,1);
                 }
                })
      }, function () {
        console.log('Modal dismissed at: ' + new Date());
      });
    };
}]);

