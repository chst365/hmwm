app.controller('BinController', ['$scope', '$state', '$stateParams', '$resource', '$modal', 'hemaAPI', '$http','modalProvider','$location', function ($scope, $state, $stateParams, $resource, $modal, hemaAPI, $http, modalProvider,$location) {
        $scope.isCollapsed = false;
        $scope.tree_data = [];
        //初始化树的数据
        $scope.jobpointTree = tree = {};
        $scope.edit_mode = !!$stateParams.uuid;
        $scope.returnToSelectedJp = function () {
            window.history.back(-1);
        };
        //切换区段tab页面让打开的页面关闭
        $scope.changeTab = function () {
            $scope.isNewBinFlag = false;
            $scope.isNewSectionTagFlag = false;
            $scope.isNewRplTagFlag = false;
        }
        //新增作业点时时取到可选网关
        $http({
            method: 'GET',
            url: baseUrl+'gateway/list?orgUuid=' + localStorage.orgUuid
        }).success(function (resp) {
            $scope.availableGateways = resp.obj;
        });
        //获取作业点页面所有数据
        if (!$scope.edit_mode) {
            $http({
                method: 'GET',
                url: baseUrl + 'jobpoint/list?orgUuid=' + localStorage.orgUuid
            }).success(function (resp) {
                $scope.data = resp.obj;
                genTreeData($scope.data || []);
            });
        }else{
            $scope.data = {};
        }
        //选择一个作业点查看具体作业点信息
        function jobpoint_selected(branch) {
            $scope.currentJobpoint = angular.copy(branch);
            $scope.data.stoshowJobPointGateway = $scope.currentJobpoint.data.sGateway.code + "<" + $scope.currentJobpoint.data.sGateway.ip + ":" +$scope.currentJobpoint.data.sGateway.port + ">";
            $state.go("app.store.bin.jobpointcontent", {uuid: $scope.data.uuid, code: $scope.currentJobpoint.code});
        }
        //
        // function getGatewayByJobpoint(jobpoint) {
        // }
        //选择一个分区的父级信息
        function getParentByPickarea(pickarea) {
            angular.forEach($scope.data, function(jobpoint) {
                if (jobpoint.uuid == pickarea.jobPointUuid)
                    $scope.currentJobpoint = jobpoint;
            });
        }
        //选择一个区段的父级信息
        function getParentBySection(section) {
            angular.forEach($scope.data, function(jobpoint) {
                angular.forEach(jobpoint.pickAreas, function(pickarea) {
                    if (pickarea.uuid == section.pickAreaUuid) {
                        $scope.currentPickarea = pickarea;
                    }
                });
            });
        }
       //选择一个区段后判断区段所有的结点编号
        function getTagSelects() {
            $scope.binTagSelects = [];
            $scope.sectionTagSelectsAndrplTagSelects = [];
            $scope.changedData = $.grep($scope.availableGateways,function(resp){
                return (resp.code == $scope.currentJobpoint.sGateway.code && resp.ip == $scope.currentJobpoint.sGateway.ip && resp.port == $scope.currentJobpoint.sGateway.port)
            });
            angular.forEach($scope.changedData[0].tags, function(tagData) {
                if (tagData.nodeType == "PickTag")
                    $scope.binTagSelects.push(tagData.nodeCode);
                if (tagData.nodeType == "DisplayTag") {
                    $scope.sectionTagSelectsAndrplTagSelects.push(tagData.nodeCode);
                }
            });
        }
        //选择一个分区查看分区的信息
        function pickarea_selected(branch) {
            $scope.currentPickarea = angular.copy(branch.data);
            $state.go("app.store.bin.pickareacontent", {uuid: $scope.data.uuid, code: $scope.currentPickarea.code});
        }
        //选择一个区段查看区段的信息
        function section_selected(branch) {
            $scope.currentSection = angular.copy(branch.data);
            $scope.allDisplayTagEleTags = $scope.currentSection.sectionEleTags.concat($scope.currentSection.rplEleTags);
            getParentBySection($scope.currentSection);
            getParentByPickarea($scope.currentPickarea);
            getGatewayByJobpoint($scope.currentJobpoint);
            $state.go("app.store.bin.sectioncontent");
            getTagSelects();
        }
        //给树分别加入3个层级的具体数据
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
            if(!($location.path() == '/app/sto/newstojobpoint')){
                tree.expand_all();
            }
        }
        //选择区段中货位然后修改
        $scope.currentIndex = undefined;
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
                        $scope.allbinEleTags.push($scope.binTagSelects[j]);
                    }
                    $scope.availableChangebinEleTags = _.difference($scope.allbinEleTags, $scope.sectionbinEleTagsArr);
                    $scope.availableChangebinEleTags.unshift($scope.currentSection.binEleTags[index].nodeCode);
                }else {
                    $scope.availableChangebinEleTags=[];
                    for(var k = 0 ; k < $scope.binTagSelects.length; k++ ){
                        $scope.availableChangebinEleTags.unshift($scope.binTagSelects[k]);
                    }
                }
            }
        };
        //选择区段中区段结点并修改
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
                        $scope.allSectionAndRplEleTags.push($scope.sectionTagSelectsAndrplTagSelects[j]);
                    }
                    $scope.availableSectionAndRplEleTags = _.difference($scope.allSectionAndRplEleTags, $scope.sectionSectionAndRplEleTagsArr);
                    $scope.availableSectionAndRplEleTags.unshift($scope.currentSection.sectionEleTags[index].nodeCode);
                }else {
                    $scope.availableSectionAndRplEleTags=[];
                    for(var k = 0 ; k < $scope.sectionTagSelectsAndrplTagSelects.length; k++ ){
                        $scope.availableSectionAndRplEleTags.unshift($scope.sectionTagSelectsAndrplTagSelects[k]);
                    }
                }
            }
        };
        //选择区段中补货结点并修改
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
                        $scope.allSectionAndRplEleTags.push($scope.sectionTagSelectsAndrplTagSelects[j]);
                    }
                    $scope.availableSectionAndRplEleTagsRq = _.difference($scope.allSectionAndRplEleTags, $scope.sectionSectionAndRplEleTagsArr);
                    $scope.availableSectionAndRplEleTagsRq.unshift($scope.currentSection.rplEleTags[index].requestNodeCode);
                    $scope.availableSectionAndRplEleTagsRp = _.difference($scope.allSectionAndRplEleTags, $scope.sectionSectionAndRplEleTagsArr);
                    $scope.availableSectionAndRplEleTagsRp.unshift($scope.currentSection.rplEleTags[index].responseNodeCode);
                }else {
                    $scope.availableSectionAndRplEleTagsAG=[];
                    for(var k = 0 ; k < $scope.sectionTagSelectsAndrplTagSelects.length; k++ ){
                        $scope.availableSectionAndRplEleTagsRq.unshift($scope.sectionTagSelectsAndrplTagSelects[k]);
                        $scope.availableSectionAndRplEleTagsRp.unshift($scope.sectionTagSelectsAndrplTagSelects[k]);
                    }
                }
            }
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
                        $state.go('app.store.bin',null,{reload:true});
                    }else if (resp.success == false){
                        modalProvider.modalInstance(resp.msg,2).result.then(function(){
                        });
                    }
                });
            });
        };
        //新增新的门店作业点
        $scope.newJobpoint = {
            nodeCode:'',
            nodeName:''
        };
        $scope.addNewjobpoint = function () {
            modalProvider.modalInstance('确定要新增该作业点吗？',1).result.then(function(){
                $http({
                    method: 'POST',
                    url: baseUrl+'jobpoint',
                    data: {
                        "uuid": "",
                        "code": $scope.newJobpoint.nodeCode,
                        "name": $scope.newJobpoint.nodeName,
                        "templateUuid": "",
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
                        $state.go('app.store.bin',null,{reload:true});
                    }else if (resp.success == false){
                        modalProvider.modalInstance(resp.msg,2).result.then(function(){
                            //maybe @todo 页面跳转到没有错误值的页面
                            //$state.go('app.enterprise.facitemplate.edit.sectionContent',{uuid:$stateParams.uuid ,code:$stateParams.code},{reload:true});
                        });
                    }
                });
            });
        };
        //新增分区
        $scope.newPickArea = {
            nodeCode:'',
            nodeName:''
        };
        $scope.addNewCurrentPickarea = function(){
            modalProvider.modalInstance('确定要新增该分区吗？',1).result.then(function(){
                $http({
                    method: 'POST',
                    url: baseUrl+'jobpoint/pickarea',
                    data: {
                        "uuid": "",
                        "code": $scope.newPickArea.nodeCode,
                        "name": $scope.newPickArea.nodeName,
                        "jobPointUuid": $scope.currentJobpoint.data.uuid
                    }
                }).success(function (resp) {
                    if(resp.success == true){
                        $state.go('app.store.bin', null, {reload: true});
                    }else if (resp.success == false){
                        modalProvider.modalInstance(resp.msg,2).result.then(function(){
                        });
                    }
                });
            });
        };
        //新增区段
        $scope.newSection = {
            nodeCode:'',
            nodeName:''
        };
        $scope.addNewCurrentSection = function(){
            modalProvider.modalInstance('确定要新增该区段吗？',1).result.then(function(){
                $http({
                    method: 'POST',
                    url: baseUrl+ 'jobpoint/section',
                    data: {
                        "uuid": "",
                        "code": $scope.newSection.nodeCode,
                        "name": $scope.newSection.nodeName,
                        "pickAreaUuid": $scope.currentPickarea.uuid
                    }
                }).success(function (resp) {
                    if(resp.success == true){
                        $state.go('app.store.bin', null, {reload: true});
                    }else if (resp.success == false){
                        modalProvider.modalInstance(resp.msg,2).result.then(function(){
                        });
                    }
                });
            });
        };
        //删除作业点
        $scope.deleteCurrentJobjoint = function () {
            modalProvider.modalInstance('确定要删除该作业点吗？',0).result.then(function(){
                $http({
                    method: 'DELETE',
                    url: baseUrl+'jobpoint/' + $scope.currentJobpoint.data.uuid
                }).success(function (resp) {
                    if(resp.success == true){
                        $state.go('app.store.bin', null, {reload: true});
                    }else if (resp.success == false){
                        modalProvider.modalInstance(resp.msg,2).result.then(function(){
                        });
                    }
                });
            });
        };
        //删除分区
        $scope.deleteCurrentPickarea = function () {
            modalProvider.modalInstance('确定要删除该分区吗？',0).result.then(function(){
                $http({
                    method: 'DELETE',
                    url: baseUrl+'jobpoint/pickarea/' + $scope.currentPickarea.uuid
                }).success(function (resp) {
                    if(resp.success == true){
                        $state.go('app.store.bin', null, {reload: true});
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
                        $state.go('app.store.bin', null, {reload: true});
                    }else if (resp.success == false){
                        modalProvider.modalInstance(resp.msg,2).result.then(function(){
                        });
                    }
                });
            });
        };

        //保存作业点
        $scope.saveJobpoint = function () {
            $scope.spiltData = {
                code:$scope.data.stoshowJobPointGateway.split("<")[0],
                ip:($scope.data.stoshowJobPointGateway.split("<")[1]).split(":")[0],
                port:(($scope.data.stoshowJobPointGateway.split("<")[1]).split(":")[1]).split(">")[0]
            };
            $scope.changedData = $.grep($scope.availableGateways,function(resp){
                return (resp.code == $scope.spiltData.code && resp.ip == $scope.spiltData.ip && resp.port == $scope.spiltData.port)
            });
            modalProvider.modalInstance('确定要保存该作业点吗？',1).result.then(function(){
                $http({
                    method: 'PUT',
                    url: baseUrl+ 'jobpoint',
                    data: {
                        "uuid": $scope.currentJobpoint.data.uuid,
                        "code": $scope.currentJobpoint.data.code,
                        "name": $scope.currentJobpoint.data.name,
                        "templateUuid": $scope.currentJobpoint.data.templateUuid,
                        "orgUuid": localStorage.orgUuid,
                        "sGateway": {
                            "uuid": $scope.changedData[0].uuid,
                            "code": $scope.changedData[0].code,
                            "ip": $scope.changedData[0].ip,
                            "port": $scope.changedData[0].port
                        }
                    }
                }).success(function (resp) {
                    if(resp.success == true){
                        $state.go('app.store.bin', null, {reload: true});
                    }else if (resp.success == false){
                        modalProvider.modalInstance(resp.msg,2).result.then(function(){
                        });
                    }
                });
            });
        };

        //保存修改过的分区数据
        $scope.savePickarea = function () {
            modalProvider.modalInstance('确定要保存该分区吗？',1).result.then(function(){
                $http({
                    method: 'PUT',
                    url: baseUrl+ 'jobpoint/pickarea',
                    data: $scope.currentPickarea
                }).success(function (resp) {
                    if(resp.success == true){
                        $state.go('app.store.bin', null, {reload: true});
                    }else if (resp.success == false){
                        modalProvider.modalInstance(resp.msg,2).result.then(function(){
                        });
                    }
                });
            });
        };
        //删除选中的一个货位
        $scope.deleteBin = function(index) {
            $scope.currentSection.binEleTags.splice(index, 1);
        };
        //删除选中的一个区段结点
        $scope.deleteSection = function (index) {
            $scope.currentSection.sectionEleTags.splice(index, 1);
        };
        //删除选中的一个补货结点
        $scope.deleteRpl = function (index) {
            $scope.currentSection.rplEleTags.splice(index, 1);
        };

        // 新增区段中货位
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
                if($scope.currentSection.binEleTags.length !== 0){
                    $scope.sectionbinEleTagsArr= [];
                    $scope.allbinEleTags= [];
                    for(var i = 0 ; i < $scope.currentSection.binEleTags.length; i++ ){
                        $scope.sectionbinEleTagsArr.push($scope.currentSection.binEleTags[i].nodeCode);
                    }
                    for(var j = 0 ; j < $scope.binTagSelects.length; j++ ){
                        $scope.allbinEleTags.push($scope.binTagSelects[j]);
                    }
                    $scope.availablebinEleTags = _.difference($scope.allbinEleTags, $scope.sectionbinEleTagsArr);
                }else{
                    $scope.availablebinEleTags = [];
                    for(var k = 0 ; k < $scope.binTagSelects.length; k++ ){
                        $scope.availablebinEleTags.push($scope.binTagSelects[k]);
                    }
                }
            }
        };
        // 新增区段中区段结点
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
                    $scope.allSectionAndRplEleTags.push($scope.sectionTagSelectsAndrplTagSelects[j]);
                }
                $scope.newAvailableSectionAndRplEleTagsPA = _.difference($scope.allSectionAndRplEleTags, $scope.sectionSectionAndRplEleTagsArr);
            }else{
                $scope.newAvailableSectionAndRplEleTagsPA = [];
                for(var k = 0 ; k < $scope.sectionTagSelectsAndrplTagSelects.length; k++ ){
                    $scope.newAvailableSectionAndRplEleTagsPA.push($scope.sectionTagSelectsAndrplTagSelects[k]);
                }
            }
        };
         // 新增区段中补货结点
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
                    $scope.allSectionAndRplEleTags.push($scope.sectionTagSelectsAndrplTagSelects[j]);
                }
                $scope.newAvailableSectionAndRplEleTagsAG = _.difference($scope.allSectionAndRplEleTags, $scope.sectionSectionAndRplEleTagsArr);
            }else {
                $scope.newAvailableSectionAndRplEleTagsAG = [];
                for(var k = 0 ; k < $scope.sectionTagSelectsAndrplTagSelects.length; k++ ){
                    $scope.newAvailableSectionAndRplEleTagsAG.push($scope.sectionTagSelectsAndrplTagSelects[k]);
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
                        $state.go('app.store.bin', null, {reload: true});
                    }else if (resp.success == false){
                        modalProvider.modalInstance(resp.msg,2).result.then(function(){
                        });
                    }
                });
            });
        };
        //新增区段货位 (push操作)
        $scope.addCurrentGoodspositionFc = function () {
            $scope.addCurrentGoodspositionObjFc.nodeAddress = $scope.addCurrentGoodspositionObjFc.nodeCode;
            $scope.currentSection.binEleTags.push($scope.addCurrentGoodspositionObjFc);
            $scope.isNewBinFlag = false;
        };
        //新增区段中区段结点 (push操作)
        $scope.addCurrentAreapoint  = function () {
            $scope.addCurrentPickAreaObjFc.nodeAddress = $scope.addCurrentPickAreaObjFc.nodeCode;
            $scope.currentSection.sectionEleTags.push($scope.addCurrentPickAreaObjFc);
            $scope.isNewSectionTagFlag = false;
        };
        //新增区段中补货结点 (push操作)
        $scope.addGoodsPoint  = function () {
            $scope.addCurrentAddGoodsPointObjFc.requestNodeAddress = $scope.addCurrentAddGoodsPointObjFc.requestNodeCode;
            $scope.addCurrentAddGoodsPointObjFc.responseNodeAddress = $scope.addCurrentAddGoodsPointObjFc.responseNodeCode;
            $scope.currentSection.rplEleTags.push($scope.addCurrentAddGoodsPointObjFc);
            $scope.isNewRplTagFlag = false;
        }
}]);