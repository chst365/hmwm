app.controller('FaciTemplateListController', ['$scope', '$state', '$stateParams', '$resource', '$modal','hemaAPI','modalProvider', function($scope, $state, $stateParams, $resource, $modal, hemaAPI,modalProvider) {
	//查询
    $scope.query = function(){
        hemaAPI.getFacilityTemplates().then(function(resp){
            $scope.FacTmpLists = resp.obj;
            angular.forEach($scope.FacTmpLists,function(data){
                $scope.FacTmpListsInfo = data;
            })
        })
    };
    //根据url参数（分页、搜索关键字）查询数据
    $scope.query($stateParams.page,$stateParams.search);
    $scope.import = function (size) {
        $modal.open({
            templateUrl: 'tpl/app/modal/import/importModalContent.html',
            controller: 'ImportModalController',
            size: size,
            resolve: {title: function() {return '导入设施模板信息';}}});
    };
    //删除设施模板
    $scope.deletefcp = function (uuid) {
        $scope.obj = [];
        angular.forEach($scope.FacTmpLists,function(dfcp){
            if(dfcp.uuid != uuid){
                $scope.obj.push(dfcp);
            }
        });
        modalProvider.modalInstance('确定要删除该设施模板吗？',1).result.then(function () {
            hemaAPI.deleteFacilityTemplateByUuid(uuid).then(function () {
                hemaAPI.getFacilityTemplates().then(function (resp) {
                    $scope.FacTmpLists = resp.obj;
                    angular.forEach($scope.FacTmpLists, function (data) {
                        $scope.FacTmpListsInfo = data;
                    })
                })
            })
        });
    }
}]);
app.controller('FaciTemplateEditController', ['$scope', '$rootScope' ,'$state', '$stateParams', '$resource', '$modal','hemaAPI','$http','_','$location','modalProvider', function($scope,$rootScope, $state, $stateParams, $resource, $modal,hemaAPI,$http,_,$location,modalProvider) {
    $scope.isCollapsed = false;
    $scope.tree_data = [];
    $scope.jobpointTree = tree = {};
    $scope.edit_mode = !!$stateParams.uuid;
    //从新增网关返回到编辑页面
    $scope.returnToSelectedGt = function () {
        window.history.back(-1);
    };
    //从新增作业点返回到编辑页面
    $scope.returnToSelectedJp = function () {
        window.history.back(-1);
    };
    //转换tab关闭弹出模块
    $scope.changeTab = function () {
        $scope.isNewBinFlag = false;
        $scope.isNewSectionTagFlag = false;
        $scope.isNewRplTagFlag = false;
    }
    //保存设施模板
    $scope.saveFacitemplate = function () {
        modalProvider.modalInstance('确定要保存该设施模板吗？',1).result.then(function () {
            $http({
                method: 'PUT',
                url: baseUrl+'facilitytemplate',
                data:{
                    "uuid": $scope.data.uuid,
                    "code": $scope.data.code,
                    "name": $scope.data.name
                }
            }).success(function () {
                if(resp.success == true){
                    $state.go('app.enterprise.facitemplate.list',null,{reload:true});
                }else if (resp.success == false){
                    modalProvider.modalInstance(resp.msg,2).result.then(function(){
                    });
                }
            });
        });
    };
    //判断树的数据是否为空
    if ($scope.edit_mode) {
        hemaAPI.getFacilityTemplateByUuid({uuid: $stateParams.uuid}).then(function(resp){
            $scope.data = resp.obj;
            genTreeData($scope.data.jobPoints || []);
        })
    } else {
        $scope.data = {};
    }
    //选择一个网管的信息
    $scope.onGatewaySelect = function(gateway) {
        $scope.currentGateway = angular.copy(gateway);
        $state.go('app.enterprise.facitemplate.edit.gatewaycontent', {uuid: $stateParams.uuid});
    };
    //选择一个网管
    function jobpoint_selected(branch) {
        $scope.currentJobpoint = angular.copy(branch.data);
        $scope.data.showJobPointGateway = $scope.currentJobpoint.sGateway.code + "<" + $scope.currentJobpoint.sGateway.ip + ":" +$scope.currentJobpoint.sGateway.port + ">";
        $state.go("app.enterprise.facitemplate.edit.jobpointcontent", {uuid: $scope.data.uuid, code: $scope.currentJobpoint.code});
    }
    //获取对应的父节点数据
    function getGatewayByJobpoint(jobpoint) {
        angular.forEach($scope.data.gateways, function(gateway){
          if (gateway.uuid == jobpoint.sGateway.uuid) {
            $scope.currentGateway = gateway;
          }
        });
      }

    function getParentByPickarea(pickarea) {
        angular.forEach($scope.data.jobPoints, function(jobpoint) {
          if (jobpoint.uuid == pickarea.jobPointUuid)
            $scope.currentJobpoint = jobpoint;
        });
      }

    function getParentBySection(section) {
        angular.forEach($scope.data.jobPoints, function(jobpoint) {
          angular.forEach(jobpoint.pickAreas, function(pickarea) {
            if (pickarea.uuid == section.pickAreaUuid) {
              $scope.currentPickarea = pickarea;
            }
          });
        });
      }

    function getTagSelects() {
	    $scope.binTagSelects = [];
	    $scope.sectionTagSelectsAndrplTagSelects = [];
	    angular.forEach($scope.currentGateway.tags, function(tag) {
	      if (tag.nodeType == "PickTag")
	        $scope.binTagSelects.push(tag);
	      if (tag.nodeType == "DisplayTag") {
	        $scope.sectionTagSelectsAndrplTagSelects.push(tag);
	      }
	    });
	  }

    //选择一个分区
    function pickarea_selected(branch) {
        $scope.currentPickarea = angular.copy(branch.data);
        $state.go("app.enterprise.facitemplate.edit.pickareacontent", {uuid: $scope.data.uuid, code: $scope.currentPickarea.code});
    }
    //选择一个区段
    function section_selected(branch) {
        $scope.currentSection = angular.copy(branch.data);
        $scope.allDisplayTagEleTags = $scope.currentSection.sectionEleTags.concat($scope.currentSection.rplEleTags);
        getParentBySection($scope.currentSection);
        getParentByPickarea($scope.currentPickarea);
        getGatewayByJobpoint($scope.currentJobpoint);
        getTagSelects();
        $state.go("app.enterprise.facitemplate.edit.sectioncontent", {uuid: $scope.data.uuid, code: $scope.currentSection.code});
    }
    //给树加入数据
    function genTreeData(jobpoints) {
        angular.forEach(jobpoints, function(jobpoint) {
            var item1 = {
                label: jobpoint.name,
                data: jobpoint,
                onSelect: jobpoint_selected,
                children: []
            };
            angular.forEach(jobpoint.pickAreas || [], function(pickarea) {
                var item2 = {
                    label: pickarea.name,
                    data: pickarea,
                    onSelect: pickarea_selected,
                    children: []
                };
                angular.forEach(pickarea.sections || [], function(section) {
                    var item3 = {
                        label: section.name,
                        onSelect: section_selected,
                        data: section
                    };
                    item2.children.push(item3);
                });
                item1.children.push(item2);
            });
            $scope.tree_data.push(item1);
        });
        if(!($location.path() == '/app/ent/facitemplate/newgateway/'+$stateParams.uuid) && !($location.path() == '/app/ent/facitemplate/newjobpoint/'+$stateParams.uuid)){
            tree.expand_all();
        }
    }
    //新增一个设施模板
    $scope.addNewfcp = function () {
        $http({
            method: 'POST',
            url: baseUrl+'facilitytemplate',
            data: {
                "uuid": "",
                "code": $scope.nodeCode,
                "name": $scope.nodeName,
            }
        }).success(function (resp) {
            if(resp.success == true){
                $state.go('app.enterprise.facitemplate.list',null,{reload:true});
            }else if (resp.success == false){
                modalProvider.modalInstance(resp.msg,2).result.then(function(){
                });
            }
        });
    };
    //选择一个电子标签并修改
    $scope.currentIndex = undefined;
    $scope.selectToEditGatewayTag = function (index) {
    	angular.forEach($scope.currentGateway.tags, function(tag) {
    		tag.collapse = false;
    	});
    	if ($scope.currentIndex != index) {
    		$scope.currentGateway.tags[index].collapse = true;
    		$scope.currentIndex = index;
    	} else {
    		$scope.currentIndex = undefined;
    	}
        $scope.currentGateway.tags[index].nodeAddress = $scope.currentGateway.tags[index].nodeCode;
    };
    //选择一个货位并修改
    $scope.currentBinIndex = undefined;
    $scope.selectTableToEditGoodsPosition = function (index) {
    	angular.forEach($scope.currentSection.binEleTags, function(tag) {
    		tag.collapse = false;
    	});
    	if ($scope.currentBinIndex != index) {
    		$scope.currentSection.binEleTags[index].collapse = true;
    		$scope.currentBinIndex = index;
    	} else {
    		$scope.currentBinIndex = undefined;
    		return;
    	}
        //判断结点编号是否有值可供选择
        if($scope.binTagSelects.length !== 0){
            if($scope.currentSection.binEleTags.length !== 0){
                $scope.sectionbinEleTagsArr= [];
                $scope.allbinEleTags= [];
                for(var i = 0 ; i < $scope.currentSection.binEleTags.length; i++ ){
                    $scope.sectionbinEleTagsArr.push($scope.currentSection.binEleTags[i].nodeCode);
                }
                for(var j = 0 ; j < $scope.binTagSelects.length; j++ ){
                    $scope.allbinEleTags.push($scope.binTagSelects[j].nodeCode);
                }
                $scope.availableChangebinEleTags = _.difference($scope.allbinEleTags, $scope.sectionbinEleTagsArr);
                $scope.availableChangebinEleTags.unshift($scope.currentSection.binEleTags[index].nodeCode);
            }else {
                $scope.availableChangebinEleTags=[];
                for(var k = 0 ; k < $scope.binTagSelects.length; k++ ){
                    $scope.availableChangebinEleTags.unshift($scope.binTagSelects[k].nodeCode);
                }
            }
        }
    };
    //选择一个区段结点并修改
    $scope.currentSectionIndex = undefined;
    $scope.selectTableToEditPickArea = function (index) {
        angular.forEach($scope.currentSection.sectionEleTags, function(tag) {
            tag.collapse = false;
        });
        if ($scope.currentSectionIndex != index) {
            $scope.currentSection.sectionEleTags[index].collapse = true;
            $scope.currentSectionIndex = index;
        } else {
            $scope.currentSectionIndex = undefined;
            return;
        }
        //选择结点编号和提交按钮初始时可点击
        $scope.allDisplayTagEleTags = $scope.currentSection.sectionEleTags.concat($scope.currentSection.rplEleTags);
        //判断结点编号是否有值可供选择
        if($scope.sectionTagSelectsAndrplTagSelects.length !== 0){
            if($scope.allDisplayTagEleTags.length !== 0){
                $scope.sectionSectionAndRplEleTagsArr= [];
                $scope.allSectionAndRplEleTags= [];
                for(var i = 0 ; i < $scope.allDisplayTagEleTags.length; i++ ){
                    $scope.sectionSectionAndRplEleTagsArr.push($scope.allDisplayTagEleTags[i].nodeCode);
                    $scope.sectionSectionAndRplEleTagsArr.push($scope.allDisplayTagEleTags[i].requestNodeCode);
                    $scope.sectionSectionAndRplEleTagsArr.push($scope.allDisplayTagEleTags[i].responseNodeCode);
                }
                for(var j = 0 ; j < $scope.sectionTagSelectsAndrplTagSelects.length; j++ ){
                    $scope.allSectionAndRplEleTags.push($scope.sectionTagSelectsAndrplTagSelects[j].nodeCode);
                    $scope.allSectionAndRplEleTags.push($scope.sectionTagSelectsAndrplTagSelects[j].requestNodeCode);
                    $scope.allSectionAndRplEleTags.push($scope.sectionTagSelectsAndrplTagSelects[j].responseNodeCode);
                }
                $scope.availableSectionAndRplEleTags = _.difference($scope.allSectionAndRplEleTags, $scope.sectionSectionAndRplEleTagsArr);
                $scope.availableSectionAndRplEleTags.unshift($scope.currentSection.sectionEleTags[index].nodeCode);
            }else {
                $scope.availableSectionAndRplEleTags=[];
                for(var k = 0 ; k < $scope.sectionTagSelectsAndrplTagSelects.length; k++ ){
                    $scope.availableSectionAndRplEleTags.unshift($scope.sectionTagSelectsAndrplTagSelects[k].nodeCode);
                }
            }
        }
    };
    //选择一个补货结点并修改
    $scope.currentRplIndex = undefined;
    $scope.selectTableToEditAddGoods = function (index) {
        angular.forEach($scope.currentSection.rplEleTags, function(tag) {
            tag.collapse = false;
        });
        if ($scope.currentRplIndex != index) {
            $scope.currentSection.rplEleTags[index].collapse = true;
            $scope.currentRplIndex = index;
        } else {
            $scope.currentRplIndex = undefined;
            return;
        }
        //选择结点编号和提交按钮初始时可点击
        $scope.disabledClickNewRpl = false;
        $scope.disabledClickSelectRpl = false;
        //判断结点编号是否有值可供选择
        if($scope.sectionTagSelectsAndrplTagSelects.length !== 0){
            if($scope.allDisplayTagEleTags.length !== 0){
                $scope.sectionSectionAndRplEleTagsArr= [];
                $scope.allSectionAndRplEleTags= [];
                for(var i = 0 ; i < $scope.allDisplayTagEleTags.length; i++ ){
                    $scope.sectionSectionAndRplEleTagsArr.push($scope.allDisplayTagEleTags[i].nodeCode);
                    $scope.sectionSectionAndRplEleTagsArr.push($scope.allDisplayTagEleTags[i].requestNodeCode);
                    $scope.sectionSectionAndRplEleTagsArr.push($scope.allDisplayTagEleTags[i].responseNodeCode);
                }
                for(var j = 0 ; j < $scope.sectionTagSelectsAndrplTagSelects.length; j++ ){
                    $scope.allSectionAndRplEleTags.push($scope.sectionTagSelectsAndrplTagSelects[j].nodeCode);
                    $scope.allSectionAndRplEleTags.push($scope.sectionTagSelectsAndrplTagSelects[j].requestNodeCode);
                    $scope.allSectionAndRplEleTags.push($scope.sectionTagSelectsAndrplTagSelects[j].responseNodeCode);
                }
                $scope.availableSectionAndRplEleTagsRq = _.difference($scope.allSectionAndRplEleTags, $scope.sectionSectionAndRplEleTagsArr);
                $scope.availableSectionAndRplEleTagsRq.unshift($scope.currentSection.rplEleTags[index].requestNodeCode);
                $scope.availableSectionAndRplEleTagsRp = _.difference($scope.allSectionAndRplEleTags, $scope.sectionSectionAndRplEleTagsArr);
                $scope.availableSectionAndRplEleTagsRp.unshift($scope.currentSection.rplEleTags[index].responseNodeCode);
            }else {
                $scope.availableSectionAndRplEleTagsAG=[];
                for(var k = 0 ; k < $scope.sectionTagSelectsAndrplTagSelects.length; k++ ){
                    $scope.availableSectionAndRplEleTagsRq.unshift($scope.sectionTagSelectsAndrplTagSelects[k].nodeCode);
                    $scope.availableSectionAndRplEleTagsRp.unshift($scope.sectionTagSelectsAndrplTagSelects[k].nodeCode);
                }
            }
        }
    };

    //保存电子标签
    $scope.editGatewayEleTagsFc = {
        nodeCode:'',
        nodeType:''
    };
    $scope.saveCurrentGatewayTags = function () {
        modalProvider.modalInstance('确定要修改该电子标签的信息吗？',1).result.then(function () {
            $http({
                method: 'PUT',
                url: baseUrl+'gateway',
                data:{
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
                    $state.go('app.enterprise.facitemplate.edit',null,{reload:true});
                }else if (resp.success == false){
                    modalProvider.modalInstance(resp.msg,2).result.then(function(){
                        //maybe @todo 页面跳转到没有错误值的页面
                        //$state.go('app.enterprise.facitemplate.edit.sectionContent',{uuid:$stateParams.uuid ,code:$stateParams.code},{reload:true});
                    });
                }
            });
        });
    };
    //添加新的网关到设施模板
    $scope.addNewgatewayToFc = function () {
        modalProvider.modalInstance("确定要新建该网关到此设施模板吗？",1).result.then(function () {
            $http({
                method: 'POST',
                url: baseUrl+'gateway',
                data: {
                    "uuid": "",
                    "code": $scope.newFcGatewayCode,
                    "ip": $scope.newFcGatewayIP,
                    "port": $scope.newFcGatewayPort,
                    "templateUuid": $scope.data.uuid,
                    "orgUuid": localStorage.orgUuid
                }
            }).success(function (resp) {
                if(resp.success == true){
                    window.history.back(-1);
                }else if (resp.success == false){
                    modalProvider.modalInstance(resp.msg,2).result.then(function(){
                    });
                }
            });
        });
    };
    //删除当前选中网关
    $scope.deleteCurrentGatewayFc = function () {
        modalProvider.modalInstance("确定要删除设施模板中该网关吗？",0).result.then(function () {
            $http({
                method: 'DELETE',
                url: baseUrl+'gateway/' +$scope.currentGateway.uuid
            }).success(function (resp) {
                if(resp.success == true){
                    $state.go('app.enterprise.facitemplate.edit',null,{reload:true});
                }else if (resp.success == false){
                    modalProvider.modalInstance(resp.msg,2).result.then(function(){
                    });
                }
            });
        });
    };
    //修改当前网关的信息
    $scope.saveEditCurrentGatewayFc = function () {
        modalProvider.modalInstance("确定要修改设施模板中该网关的信息吗？",1).result.then(function () {
            $http({
                method: 'PUT',
                url: baseUrl + "gateway",
                data:{
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
                    $state.go('app.enterprise.facitemplate.edit',null,{reload:true});
                }else if (resp.success == false){
                    modalProvider.modalInstance(resp.msg,2).result.then(function(){
                    });
                }
            });
        });
    };
    $scope.isCollapsedNewEleTags = false;
    //新增电子标签
    $scope.createGatewayTagEle = function() {
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
        $scope.currentGateway.tags.splice(index,1);
    };
    //添加新的作业点到设施模板
    $scope.addNewjobpointToFc = function () {
        modalProvider.modalInstance('确定要新建该作业点到此设施模板吗？',1).result.then(function(){
            $http({
                method: 'POST',
                url: baseUrl+'jobpoint',
                data: {
                    "uuid": "",
                    "code": $scope.newJobpointNodeCodeFc,
                    "name": $scope.newJobpointNodeNameFc,
                    "templateUuid": $scope.data.uuid,
                    "orgUuid": localStorage.orgUuid,
                    "sGateway": {
                        "uuid": $scope.currentJobpoint.sGateway.uuid,
                        "code": $scope.currentJobpoint.sGateway.code,
                        "ip": $scope.currentJobpoint.sGateway.ip,
                        "port": $scope.currentJobpoint.sGateway.port
                    }
                }
            }).success(function (resp) {
                if(resp.success == true){
                    window.history.back(-1);
                }else if (resp.success == false){
                    modalProvider.modalInstance(resp.msg,2).result.then(function(){
                    });
                }
            });
        });
    };
    //新增新的门店作业点
    $scope.newJobpointFc = {
        nodeCode:'',
        nodeName:''
    };
    $scope.addNewjobpointFc = function () {
        modalProvider.modalInstance('确定要新增该作业点吗？',1).result.then(function(){
            $http({
                method: 'POST',
                url: baseUrl+'jobpoint',
                data: {
                    "uuid": "",
                    "code": $scope.newJobpointFc.nodeCode,
                    "name": $scope.newJobpointFc.nodeName,
                    "templateUuid": $scope.currentJobpoint.uuid,
                    "orgUuid": localStorage.orgUuid
                }
            }).success(function (resp) {
                if(resp.success == true){
                    $state.go('app.bin.jobpoint',null,{reload:true});
                }else if (resp.success == false){
                    modalProvider.modalInstance(resp.msg,2).result.then(function(){
                    });
                }
            });
        });
    };
    //新增分区
    $scope.newPickAreaFc = {
        nodeCode:'',
        nodeName:''
    };
    $scope.addNewCurrentPickareaFc = function(){
        modalProvider.modalInstance('确定要新增该分区吗？',1).result.then(function(){
            $http({
                method: 'POST',
                url: baseUrl+'jobpoint/pickarea',
                data: {
                    "uuid": "",
                    "code": $scope.newPickAreaFc.nodeCode,
                    "name": $scope.newPickAreaFc.nodeName,
                    "jobPointUuid": $scope.currentJobpoint.uuid
                }
            }).success(function (resp) {
                if(resp.success == true){
                    $state.go('app.enterprise.facitemplate.edit',null,{reload:true});
                }else if (resp.success == false){
                    modalProvider.modalInstance(resp.msg,2).result.then(function(){
                    });
                }
            });
        });
    };
    //新增区段
    $scope.newSectionFc = {
        nodeCode:'',
        nodeName:''
    };
    $scope.addNewCurrentSectionFc = function(){
        modalProvider.modalInstance('确定要新增该区段吗？',1).result.then(function(){
            $http({
                method: 'POST',
                url: baseUrl+ 'jobpoint/section',
                data: {
                    "uuid": "",
                    "code": $scope.newSectionFc.nodeCode,
                    "name": $scope.newSectionFc.nodeName,
                    "pickAreaUuid": $scope.currentPickarea.uuid
                }
            }).success(function (resp) {
                if(resp.success == true){
                    $state.go('app.enterprise.facitemplate.edit',null,{reload:true});
                }else if (resp.success == false){
                    modalProvider.modalInstance(resp.msg,2).result.then(function(){
                    });
                }
            });
        });
    };
    //删除作业点
    $scope.deleteCurrentJobjointFc = function () {
        modalProvider.modalInstance('确定要删除该作业点吗？',0).result.then(function(){
            $http({
                method: 'DELETE',
                url: baseUrl+'jobpoint/' + $scope.currentJobpoint.uuid
            }).success(function (resp) {
                if(resp.success == true){
                    $state.go('app.enterprise.facitemplate.edit',null,{reload:true});
                }else if (resp.success == false){
                    modalProvider.modalInstance(resp.msg,2).result.then(function(){
                    });
                }
            });
        });
    };
    //删除分区
    $scope.deleteCurrentPickareaFc = function () {
        modalProvider.modalInstance('确定要删除该分区吗？',0).result.then(function(){
            $http({
                method: 'DELETE',
                url: baseUrl+'jobpoint/pickarea/' + $scope.currentPickarea.uuid
            }).success(function (resp) {
                if(resp.success == true){
                    $state.go('app.enterprise.facitemplate.edit',null,{reload:true});
                }else if (resp.success == false){
                    modalProvider.modalInstance(resp.msg,2).result.then(function(){
                    });
                }
            });
        });
    };
    //删除区段
    $scope.deleteCurrentSectionFc = function () {
        modalProvider.modalInstance('确定要删除该区段吗？',0).result.then(function(){
            $http({
                method: 'DELETE',
                url: baseUrl+'jobpoint/section/' + $scope.currentSection.uuid
            }).success(function (resp) {
                if(resp.success == true){
                    $state.go('app.enterprise.facitemplate.edit',null,{reload:true});
                }else if (resp.success == false){
                    modalProvider.modalInstance(resp.msg,2).result.then(function(){
                    });
                }
            });
        });
    };
    $scope.saveJobpointFc = function () {
        $scope.spiltData = {
            code:$scope.data.showJobPointGateway.split("<")[0],
            ip:($scope.data.showJobPointGateway.split("<")[1]).split(":")[0],
            port:(($scope.data.showJobPointGateway.split("<")[1]).split(":")[1]).split(">")[0]
        };
        $scope.changedData = $.grep($scope.data.gateways,function(resp){
            return (resp.code == $scope.spiltData.code && resp.ip == $scope.spiltData.ip && resp.port == $scope.spiltData.port)
        });
        modalProvider.modalInstance('确定要保存该作业点吗？',1).result.then(function(){
            $http({
                method: 'PUT',
                url: baseUrl+ 'jobpoint',
                data: {
                    "uuid": $scope.currentJobpoint.uuid,
                    "code": $scope.currentJobpoint.code,
                    "name": $scope.currentJobpoint.name,
                    "templateUuid": $scope.currentJobpoint.templateUuid,
                    "orgUuid": $scope.currentJobpoint.orgUuid,
                    "sGateway": {
                        "uuid": $scope.changedData[0].uuid,
                        "code": $scope.changedData[0].code,
                        "ip": $scope.changedData[0].ip,
                        "port": $scope.changedData[0].port
                    }
                }
            }).success(function (resp) {
                if(resp.success == true){
                    $state.go('app.enterprise.facitemplate.edit',null,{reload:true});
                }else if (resp.success == false){
                    modalProvider.modalInstance(resp.msg,2).result.then(function(){
                    });
                }
            });
        });
    };
    //保存修改过的分区数据
    $scope.savePickareaFc = function () {
        modalProvider.modalInstance('确定要保存该分区吗？',1).result.then(function(){
            $http({
                method: 'PUT',
                url: baseUrl+ 'jobpoint/pickarea',
                data: $scope.currentPickarea
            }).success(function (resp) {
                if(resp.success == true){
                    $state.go('app.enterprise.facitemplate.edit',null,{reload:true});
                }else if (resp.success == false){
                    modalProvider.modalInstance(resp.msg,2).result.then(function(){
                    });
                }
            });
        });
    };

    //删除货位
    $scope.deleteBin = function(index) {
    	$scope.currentSection.binEleTags.splice(index, 1);
    };
    //删除区段结点
    $scope.deleteSection = function (index) {
        $scope.currentSection.sectionEleTags.splice(index, 1);
    };
    //删除补货结点
    $scope.deleteRpl = function (index) {
        $scope.currentSection.rplEleTags.splice(index, 1);
    };
    // 新增货位及标签
    $scope.createBin = function() {
        $scope.isNewBinFlag = !$scope.isNewBinFlag;
        $scope.addCurrentGoodspositionObjFc = {
            "uuid": "",
            "binCode":"",
            "nodeCode": "",
            "nodeAddress": "",
            "nodeType": "PickTag",
            "nodeUsage": "PickDisplayQty",
            "sectionUuid": $scope.currentSection.uuid
        };
        //判断结点编号是否有值可供选择
        if($scope.binTagSelects.length !== 0){
            $scope.sectionbinEleTagsArr= [];
            $scope.allbinEleTags= [];
            for(var i = 0 ; i < $scope.currentSection.binEleTags.length; i++ ){
                $scope.sectionbinEleTagsArr.push($scope.currentSection.binEleTags[i].nodeCode);
            }
            for(var j = 0 ; j < $scope.binTagSelects.length; j++ ){
                $scope.allbinEleTags.push($scope.binTagSelects[j].nodeCode);
            }
            $scope.availablebinEleTags = _.difference($scope.allbinEleTags, $scope.sectionbinEleTagsArr);
        }
    };
    //新增区段结点
    $scope.createSectionTag = function() {
    	$scope.isNewSectionTagFlag = !$scope.isNewSectionTagFlag;
    	$scope.addCurrentPickAreaObjFc = {
			"uuid": "",
		    "nodeCode": "",
		    "nodeAddress": "",
		    "nodeType": "DisplayTag",
		    "nodeUsage": "Section",
		    "sectionUuid": $scope.currentSection.uuid
	    };
        $scope.allDisplayTagEleTags = $scope.currentSection.sectionEleTags.concat($scope.currentSection.rplEleTags);
        //判断结点编号是否有值可供选择
            $scope.sectionSectionAndRplEleTagsArr= [];
            $scope.allSectionAndRplEleTags= [];
            if($scope.allDisplayTagEleTags.length !== 0) {
                for (var i = 0; i < $scope.allDisplayTagEleTags.length; i++) {
                    $scope.sectionSectionAndRplEleTagsArr.push($scope.allDisplayTagEleTags[i].nodeCode);
                    $scope.sectionSectionAndRplEleTagsArr.push($scope.allDisplayTagEleTags[i].requestNodeCode);
                    $scope.sectionSectionAndRplEleTagsArr.push($scope.allDisplayTagEleTags[i].responseNodeCode);
                }
                for (var j = 0; j < $scope.sectionTagSelectsAndrplTagSelects.length; j++) {
                    $scope.allSectionAndRplEleTags.push($scope.sectionTagSelectsAndrplTagSelects[j].nodeCode);
                    $scope.allSectionAndRplEleTags.push($scope.sectionTagSelectsAndrplTagSelects[j].requestNodeCode);
                    $scope.allSectionAndRplEleTags.push($scope.sectionTagSelectsAndrplTagSelects[j].responseNodeCode);
                }
                $scope.newAvailableSectionAndRplEleTagsPA = _.difference($scope.allSectionAndRplEleTags, $scope.sectionSectionAndRplEleTagsArr);
            }else{
                $scope.newAvailableSectionAndRplEleTagsPA = [];
                for(var k = 0 ; k < $scope.sectionTagSelectsAndrplTagSelects.length; k++ ){
                    $scope.newAvailableSectionAndRplEleTagsPA.push($scope.sectionTagSelectsAndrplTagSelects[k].nodeCode);
                }
            }
        };
    //删新增补货结点
    $scope.createRplTag = function() {
    	$scope.isNewRplTagFlag = !$scope.isNewRplTagFlag;
    	$scope.addCurrentAddGoodsPointObjFc = {
			"uuid": "",
	        "requestNodeCode": "",
	        "requestNodeAddress": "",
	        "requestNodeType": "DisplayTag",
	        "requestNodeUsage": "RplRequest",
	        "responseNodeCode": "",
	        "responseNodeAddress": "",
	        "responseNodeType": "DisplayTag",
	        "responseNodeUsage": "RplResponse",
	        "sectionUuid": $scope.currentSection.uuid
	    };
        //选择结点编号和提交按钮初始时可点击
        $scope.disabledClickNewRpl = false;
        $scope.disabledClickSelectRpl = false;
        $scope.allDisplayTagEleTags = $scope.currentSection.sectionEleTags.concat($scope.currentSection.rplEleTags);
        //判断结点编号是否有值可供选择
            if($scope.allDisplayTagEleTags.length !== 0){
                $scope.sectionSectionAndRplEleTagsArr= [];
                $scope.allSectionAndRplEleTags= [];
                for(var i = 0 ; i < $scope.allDisplayTagEleTags.length; i++ ){
                    $scope.sectionSectionAndRplEleTagsArr.push($scope.allDisplayTagEleTags[i].nodeCode);
                    $scope.sectionSectionAndRplEleTagsArr.push($scope.allDisplayTagEleTags[i].requestNodeCode);
                    $scope.sectionSectionAndRplEleTagsArr.push($scope.allDisplayTagEleTags[i].responseNodeCode);
                }
                for(var j = 0 ; j < $scope.sectionTagSelectsAndrplTagSelects.length; j++ ){
                    $scope.allSectionAndRplEleTags.push($scope.sectionTagSelectsAndrplTagSelects[j].nodeCode);
                    $scope.allSectionAndRplEleTags.push($scope.sectionTagSelectsAndrplTagSelects[j].requestNodeCode);
                    $scope.allSectionAndRplEleTags.push($scope.sectionTagSelectsAndrplTagSelects[j].responseNodeCode);
                }
                $scope.newAvailableSectionAndRplEleTagsAG = _.difference($scope.allSectionAndRplEleTags, $scope.sectionSectionAndRplEleTagsArr);
            }else {
                $scope.newAvailableSectionAndRplEleTagsAG = [];
                for(var k = 0 ; k < $scope.sectionTagSelectsAndrplTagSelects.length; k++ ){
                    $scope.newAvailableSectionAndRplEleTagsAG.push($scope.sectionTagSelectsAndrplTagSelects[k].nodeCode);
                }
            }
    };
    //保存修改过的区段数据
    $scope.saveSectionFc = function () {
        modalProvider.modalInstance('确定要保存该区段吗',1).result.then(function(){
            $http({
                method: 'PUT',
                url: baseUrl+ 'jobpoint/section',
                data: $scope.currentSection
            }).success(function (resp) {
                if(resp.success == true){
                    $state.go('app.enterprise.facitemplate.edit',null,{reload:true});
                }else if (resp.success == false){
                   modalProvider.modalInstance(resp.msg,2).result.then(function(){
                    //maybe @todo 页面跳转到没有错误值的页面
                       //$state.go('app.enterprise.facitemplate.edit.sectionContent',{uuid:$stateParams.uuid ,code:$stateParams.code},{reload:true});
                   });
                }
            });
        });
    };
    //新增区段货位
    $scope.addCurrentGoodspositionFc = function () {
       $scope.addCurrentGoodspositionObjFc.nodeAddress = $scope.addCurrentGoodspositionObjFc.nodeCode;
       $scope.currentSection.binEleTags.push($scope.addCurrentGoodspositionObjFc);
       $scope.isNewBinFlag = false;
    };
    //新增区段中区段结点
    $scope.addCurrentAreapoint  = function () {
        $scope.addCurrentPickAreaObjFc.nodeAddress = $scope.addCurrentPickAreaObjFc.nodeCode;
        $scope.currentSection.sectionEleTags.push($scope.addCurrentPickAreaObjFc);
        $scope.isNewSectionTagFlag = false;
    };
    //新增区段中补货结点
    $scope.addGoodsPoint  = function () {
        $scope.addCurrentAddGoodsPointObjFc.requestNodeAddress = $scope.addCurrentAddGoodsPointObjFc.requestNodeCode;
        $scope.addCurrentAddGoodsPointObjFc.responseNodeAddress = $scope.addCurrentAddGoodsPointObjFc.responseNodeCode;
        $scope.currentSection.rplEleTags.push($scope.addCurrentAddGoodsPointObjFc);
        $scope.isNewRplTagFlag = false;
    }
}]);

