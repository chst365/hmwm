(function () {
    angular.module('app')
        .controller('LabelController',LabelController);
    LabelController.$inject = ['$scope', '$state', '$stateParams','hemaAPI','$http','modalProvider'];
    function LabelController($scope, $state, $stateParams,hemaAPI,$http,modalProvider) {
        //进入页面获取所有网关列表
        $http({
            method: "GET",
            url: baseUrl + "gateway/list?orgUuid=" + localStorage.orgUuid
        }).then(function (resp) {
            $scope.gatewayInfoLists = resp.data.obj;
        });
        $scope.isCollapsed = false;
        $scope.tree_data = [];
        //初始化树的数据
        $scope.jobpointTree = tree = {};
        $scope.edit_mode = !!$stateParams.uuid;
        //返回edit页面
        $scope.returnToSelectedGt = function () {
            window.history.back(-1);
        };
        //判断一开始树的数据是否为空
        if ($scope.edit_mode) {
            hemaAPI.getFacilityTemplateByUuid({uuid: $stateParams.uuid}).then(function (resp) {
                $scope.data = resp.obj;
            })
        } else {
            $scope.data = {};
        }
        //选择一个具体的网管来获取网关信息和电子标签信息
        $scope.onGatewaySelect = function (gateway) {
            $scope.currentGateway = angular.copy(gateway);
            $state.go('app.store.label.gatewaycontent', {uuid: $stateParams.uuid});
        };


        $scope.currentIndex = undefined;
        //在表格中选择要修改的网关
        $scope.selectToEditGatewayTag = function (index) {
            angular.forEach($scope.currentGateway.tags, function (tag) {
                tag.collapse = false;
            });
            if ($scope.currentIndex != index) {
                $scope.currentGateway.tags[index].collapse = true;
                $scope.currentIndex = index;
            } else {
                $scope.currentIndex = undefined;
                //return;
            }
            $scope.currentGateway.tags[index].nodeAddress = $scope.currentGateway.tags[index].nodeCode;
        };
        $scope.editGatewayEleTagsFc = {
            nodeCode: '',
            nodeType: ''
        };
        //保存网关及电子标签信息
        $scope.saveCurrentGatewayTags = function () {
            modalProvider.modalInstance('确定要修改该电子标签的信息吗？', 1).result.then(function () {
                $http({
                    method: 'PUT',
                    url: baseUrl + 'gateway',
                    data: {
                        "uuid": $scope.currentGateway.uuid,
                        "code": $scope.currentGateway.code,
                        "ip": $scope.currentGateway.ip,
                        "port": $scope.currentGateway.port,
                        "templateUuid": $scope.currentGateway.templateUuid,
                        "orgUuid": localStorage.orgUuid,
                        "tags": $scope.currentGateway.tags
                    }
                }).success(function (resp) {
                    if(resp.success == true){
                        $state.go('app.store.label', null, {reload: true});
                    }else if (resp.success == false){
                        modalProvider.modalInstance(resp.msg,2).result.then(function(){
                        });
                    }
                });
            });
        };
        //添加新的网关
        $scope.addNewGatewayData ={
            code:'',
            ip:'',
            port:''
        };
       $scope.addNewgateway = function () {
            modalProvider.modalInstance("确定要新建该网关吗？", 1).result.then(function () {
                $http({
                    method: 'POST',
                    url: baseUrl + 'gateway',
                    data: {
                        "uuid": "",
                        "code": $scope.addNewGatewayData.code,
                        "ip": $scope.addNewGatewayData.ip,
                        "port": $scope.addNewGatewayData.port,
                        "templateUuid": $scope.data.uuid,
                        "orgUuid": localStorage.orgUuid
                    }
                }).success(function (resp) {
                    if(resp.success == true){
                        $state.go('app.store.label', null, {reload: true});
                    }else if (resp.success == false){
                        modalProvider.modalInstance(resp.msg,2).result.then(function(){
                        });
                    }
                });
            });
        };
        //删除当前选中网关
        $scope.deleteCurrentGatewayFc = function () {
            modalProvider.modalInstance("确定要该网关吗？", 0).result.then(function () {
                $http({
                    method: 'DELETE',
                    url: baseUrl + 'gateway/' + $scope.currentGateway.uuid
                }).success(function (resp) {
                    if(resp.success == true){
                        $state.go('app.store.label', null, {reload: true});
                    }else if (resp.success == false){
                        modalProvider.modalInstance(resp.msg,2).result.then(function(){
                        });
                    }
                });
            });
        };
        //修改当前网关的信息
      $scope.saveEditCurrentGatewayFc = function () {
             modalProvider.modalInstance("确定要修改该网关的信息吗？", 1).result.then(function () {
               $http({
                 method: 'PUT',
                 url: baseUrl + "gateway",
                 data: {
                   "uuid": $scope.currentGateway.uuid,
                   "code": $scope.currentGateway.code,
                   "ip": $scope.currentGateway.ip,
                   "port": $scope.currentGateway.port,
                   "templateUuid": $scope.currentGateway.templateUuid,
                   "orgUuid": localStorage.orgUuid,
                   "tags": $scope.currentGateway.tags
                 }
               }).success(function (resp) {
                 if (resp.success == true) {
                   $state.go('app.store.label', null, {reload: true});
                 } else if (resp.success == false) {
                   modalProvider.modalInstance(resp.msg, 2).result.then(function () {
                   });
                 }
               });
             });

           };

        $scope.isCollapsedNewEleTags = false;
        //新建电子标签
        $scope.createGatewayTagEle = function () {
            $scope.isCollapsedNewEleTags = !$scope.isCollapsedNewEleTags;
            $scope.addNewTagEleFc = {
                "uuid": "",
                "nodeCode": "",
                "nodeAddress": "",
                "nodeType": "PickTag",
                "nodeUsage": "PickDisplayQty",
                "nodeState": "Normal",
                "checkTime": "2016-07-25T06:57:09.574Z",
                "remark": "",
                "gatewayUuid": $scope.currentGateway.uuid
            };
        };
        $scope.addNewCurrentEleTagFc = function () {
            $scope.addNewTagEleFc.nodeAddress = $scope.addNewTagEleFc.nodeCode;
            $scope.currentGateway.tags.push($scope.addNewTagEleFc);
            $scope.isCollapsedNewEleTags = false;
        };
        //删除当前网关下的电子标签
        $scope.deleteGatewayTag = function (index) {
            $scope.currentGateway.tags.splice(index, 1);
        };
    }
})();
