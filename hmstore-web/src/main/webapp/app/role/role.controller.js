app.controller('RoleListController', ['$scope', '$state', '$stateParams', '$resource', '$modal','modalProvider', function($scope,$state, $stateParams, $resource, $modal,modalProvider) {
	//查询
    $scope.query = function(page,filter){
        var $com = $resource(baseUrl+'role/list?orgUuid='+localStorage.orgUuid );
        $com.get(function(data){
          $scope.data = data;
          data.page_index = page;
        });
    }
  //搜索跳转
    $scope.search = function(){
        $state.go('app.news.list',{search:$scope.search_context});
    }
  //根据url参数（分页、搜索关键字）查询数据
    $scope.query($stateParams.page,$stateParams.search);
  //移除
    $scope.remove = function(uuid) {
        var modalInstance = modalProvider.modalInstance('点击后删除本行记录',0);
        modalInstance.result.then(function (selectedItem) {
            var $com = $resource(baseUrl+'role/' + uuid);
            $com.delete(function (data) {
                if(data.success==false){
                  var modalInstance = modalProvider.modalInstance(data.msg,1);
                }else{
                  if(data.success==true){
                      $scope.obj = [];
                      angular.forEach($scope.data.obj, function (d) {
                         if (d.uuid != uuid) {
                             $scope.obj.push(d);
                         }
                      });
                  $scope.data.obj = $scope.obj;
                }
              }
            });
      }, function () {
        console.log('Modal dismissed at: ' + new Date());
      });
    }
}]);

app.controller('RoleEditController', ['$scope', '$state', '$stateParams', '$resource', '$modal','$http','modalProvider',function($scope, $state, $stateParams, $resource, $modal,$http,modalProvider) {
    $scope.edit_mode = !!$stateParams.uuid;
    if ($scope.edit_mode) {
        var $com = $resource(baseUrl+'role/'+$stateParams.uuid);
        $com.get({uuid: $stateParams.uuid}, function(data) {
            $scope.data = data;
 //ivhTreeview:定义了传总部还是门店的勾选状态,判断第一级和第二级是否为半勾选状态
            function ivhTreeviewIndeterminated(ivhTreeview){
                    var ivhTreeviewIndeterminate={
                            "ivhTreeviewIndeterminatefirst":[],
                            "ivhTreeviewIndeterminatesecond":[],
                    };
                    var determinate=[];
                    for(var i=0;i<$scope.data.obj.functions[ivhTreeview].child.length;i++) {
                        if($scope.data.obj.functions[ivhTreeview].child[i].check==true){
                              determinate.push($scope.data.obj.functions[ivhTreeview].child[i])
                        }
                    }
                    if(determinate.length<$scope.data.obj.functions[ivhTreeview].child.length && determinate.length>0){
                        ivhTreeviewIndeterminate.ivhTreeviewIndeterminatefirst.push(true);
                    }
                    if(determinate.length==0){
                        ivhTreeviewIndeterminate.ivhTreeviewIndeterminatefirst.push(false);
                    }
                    for(var i=0;i<determinate.length;i++) {
                        for(var j=0;j<determinate[i].child.length;j++){
                            if(determinate[i].child[j].check==false) {
                                  ivhTreeviewIndeterminate.ivhTreeviewIndeterminatesecond[i]=true;
                                  ivhTreeviewIndeterminate.ivhTreeviewIndeterminatefirst[0]=true;
                                  break;
                            }
                      }
                    }
                    return ivhTreeviewIndeterminate;
                  }
//动态生成树形结构，此处为编辑的树
          $scope.firstTree=[];
          for(var i=0;i<$scope.data.obj.functions.length;i++){
              $scope.secondTree=[]
              for(var j=0;j<$scope.data.obj.functions[i].child.length;j++){
                  $scope.thirdTree=[]
                  for(var k=0;k<$scope.data.obj.functions[i].child[j].child.length;k++){
                        $scope.threeChild={}
                        $scope.threeChild.label=$scope.data.obj.functions[i].child[j].child[k].name
                        $scope.threeChild.Uuid=$scope.data.obj.functions[i].child[j].child[k].uuid
                        $scope.threeChild.selected=$scope.data.obj.functions[i].child[j].child[k].check
                        $scope.thirdTree.push($scope.threeChild)
                 }
                  $scope.twoChild={}
                  $scope.twoChild.children=$scope.thirdTree
                  if(i==0){
                        $scope.twoChild.__ivhTreeviewIndeterminate=ivhTreeviewIndeterminated(0).ivhTreeviewIndeterminatesecond[j]
                  }else{
                        $scope.twoChild.__ivhTreeviewIndeterminate=ivhTreeviewIndeterminated(1).ivhTreeviewIndeterminatesecond[j]
                  }
                  $scope.twoChild.label=$scope.data.obj.functions[i].child[j].name
                  $scope.twoChild.Uuid=$scope.data.obj.functions[i].child[j].uuid
                  $scope.twoChild.selected=$scope.data.obj.functions[i].child[j].check
                  $scope.secondTree.push($scope.twoChild)
              }
              $scope.oneChild={}
              $scope.oneChild.children=$scope.secondTree
              $scope.oneChild.__ivhTreeviewIndeterminate=ivhTreeviewIndeterminated(i).ivhTreeviewIndeterminatefirst[0]
              $scope.oneChild.label=$scope.data.obj.functions[i].name
              $scope.oneChild.Uuid=$scope.data.obj.functions[i].uuid
              $scope.oneChild.selected=$scope.data.obj.functions[i].check
              $scope.firstTree.push($scope.oneChild)
          }
          $scope.permissionTree=$scope.firstTree
        });
    } else {
      var $com = $resource(baseUrl+'role/func');
      $com.get(function(data) {
        $scope.data = data;
 //动态生成树形结构，此处为新增的树
        $scope.firstTree=[];
        for(var i=0;i<$scope.data.obj.length;i++){
            $scope.secondTree=[]
            for(var j=0;j<$scope.data.obj[i].child.length;j++){
                $scope.thirdTree=[]
                for(var k=0;k<$scope.data.obj[i].child[j].child.length;k++){
                    $scope.threeChild={}
                    $scope.threeChild.label=$scope.data.obj[i].child[j].child[k].name
                    $scope.threeChild.Uuid=$scope.data.obj[i].child[j].child[k].uuid
                    $scope.threeChild.selected=$scope.data.obj[i].child[j].child[k].check
                    $scope.thirdTree.push($scope.threeChild)
                }
                $scope.twoChild={}
                $scope.twoChild.children=$scope.thirdTree
                $scope.twoChild.label=$scope.data.obj[i].child[j].name
                $scope.twoChild.Uuid=$scope.data.obj[i].child[j].uuid
                $scope.twoChild.selected=$scope.data.obj[i].child[j].check
                $scope.secondTree.push($scope.twoChild)
          }
            $scope.oneChild={}
            $scope.oneChild.children=$scope.secondTree
            $scope.oneChild.label=$scope.data.obj[i].name
            $scope.oneChild.Uuid=$scope.data.obj[i].uuid
            $scope.oneChild.selected=$scope.data.obj[i].check
            $scope.firstTree.push($scope.oneChild)
        }
        $scope.permissionTree=$scope.firstTree
      })
    }
  //提交保存角色
  $scope.submit = function() {
        var modalInstance =  modalProvider.modalInstance('是否保存角色?',1);
        modalInstance.result.then(function (selectedItem) {
            if($scope.data == null){
            }else {
              var checkedobj=[]
              angular.forEach($scope.permissionTree, function (i) {
                angular.forEach(i.children, function (j) {
                  angular.forEach(j.children, function (k) {
                          if(k.selected){
                              checkedobj.push(k.Uuid)
                              checkedobj.push(j.Uuid)
                              checkedobj.push(i.Uuid)
                          }
                  })
                      if(j.selected){
                      checkedobj.push(j.Uuid)
                  }
                });
                 if(i.selected){
                  checkedobj.push(i.Uuid)
                }
              });
  //处理传输数组的格式
              var checkedObj=[];
              angular.forEach(checkedobj,function(obj) {
                var truefomat={
                      "uuid": ''
                  };
                truefomat.uuid = obj;
                checkedObj.push(truefomat)
              })
  //新增的数据提交
              if (!$scope.edit_mode)//新增
              {
                $http({
                  method: 'POST',
                  url: baseUrl+'role',
                  data: {
                    "uuid": null,
                    "code": $scope.data.obj.code,
                    "name": $scope.data.obj.name,
                    "operator": localStorage.orgCode,
                    "orgUuid": localStorage.orgUuid,
                    "functions":checkedObj,
                    "modifier": null
                  }
                }).then(function (data) {
                  if(data.data.success == true){
                    $state.go('app.enterprise.role.list');
                  }else{
                    var modalInstance =  modalProvider.modalInstance(data.data.msg,1);
                  }
                })
              } else {
  //编辑的数据提交
                $http({
                  method: 'PUT',
                  url: baseUrl+'role',
                  data: {
                    "uuid":$scope.data.obj.uuid ,
                    "code": $scope.data.obj.code,
                    "name": $scope.data.obj.name,
                    "operator": localStorage.orgCode,
                    "orgUuid": localStorage.orgUuid,
                    "functions":checkedObj,
                    "modifier": null
                  }
                }).then(function (data) {
                  if(data.data.success == true){
                    $state.go('app.enterprise.role.list');
                  }else{
                    var modalInstance =modalProvider.modalInstance(data.data.msg,1);
                  }
                })
              }
            }
      }, function () {
        console.log('Modal dismissed at: ' + new Date());
      });
    };
   //编辑界面删除
    $scope.delete = function(uuid) {
        var modalInstance = modalProvider.modalInstance('这里将删除该角色信息',0);
        modalInstance.result.then(function (selectedItem) {
              var $com = $resource(baseUrl+'role/'+uuid);
               $com.delete(function(data){
                    if(data.success == true){
                        $state.go('app.enterprise.role.list');
                    }else{
                        var modalInstance =modalProvider.modalInstance(data.msg,1);
                    }
              });
       }, function () {
        console.log('Modal dismissed at: ' + new Date());
      });
    };
}]);

