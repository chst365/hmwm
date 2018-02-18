app.controller('PickSchemeTemplateListController', ['$scope', '$state', '$stateParams', '$resource', '$modal','$http','$location','modalProvider', function($scope, $state, $stateParams, $resource, $modal,$http,$location,modalProvider) {
  //列表查询
    $scope.query = function(page,filter){
        var $com = $resource(baseUrl+"pickschemetemplate/list?orgUuid="+localStorage.orgUuid,{page:'@page',filter:'@filter'});
        $com.get({page:page,filter:filter},function(data){
            $scope.data = data;
            $scope.listSize = $scope.data.obj.length;
        });
    };
    //编辑
    $scope.editTem = function (uuid) {
        $state.go('app.enterprise.pickschemetemplate.edit',{uuid:uuid},{
            reload:true
        });
    };
    //新增模板
    $scope.addNewTem = function(){
        $state.go('app.enterprise.pickschemetemplate.edit',null,{
            reload:true
        });
    };
  //分发模板的
    $scope.distributeTem = function(uuid){
        modalProvider.modalInstance('确定要分发吗?',1).result.then(function () {
            $http.put(baseUrl+'pickschemetemplate/'+uuid).then(function (resp) {
                if(resp.data.msg != '操作成功'){
                    modalProvider.modalInstance(resp.data.msg,1);
                }
            });
        });
    };
    //根据url参数（分页、搜索关键字）查询数据
    $scope.query($stateParams.page,$stateParams.search);
    //上传文件
    $scope.import = function (size) {
        var modalInstance = $modal.open({
            templateUrl: 'tpl/app/modal/import/importModalContent.html',
            controller: 'ImportModalController',
            size: size,
            resolve: {
                title: function() {
                    return '导入拣货方案模板信息';
                }
            }
        });
        modalInstance.result.then(function (selectedItem) {
        });
    };
    //删除模板
    $scope.remove = function(uuid) {
        modalProvider.modalInstance('确定删除该条数据吗？',0).result.then(function () {
            var $com = $resource(baseUrl+'pickschemetemplate/'+uuid);
            $com.delete(function(data){
                if(data.msg == '操作成功'){
                    $com = $resource(baseUrl+"pickschemetemplate/list?orgUuid="+localStorage.orgUuid);
                    $com.get(function(data){
                        $scope.data.obj = data.obj;
                    });
                }else{
                    modalProvider.modalInstance(data.msg,1);
                }
            });
        });
    }
}]);
//编辑界面的控制器
app.controller('PickSchemeTemplateEditController', ['$scope', '$state', '$stateParams', '$http','$resource','$modal','modalProvider','_', function($scope, $state, $stateParams, $http,$resource,$modal,modalProvider,_) {
    $scope.edit_mode = !!$stateParams.uuid;//编辑/新增
    $scope.stores = [];//门店数组
    $scope.pickSchemeStoreObjArray=[];//分发门店数组
    $scope.binArticalAray=[];//获取货位和货位对应的商品（如果有的话）
    $scope.createObjArray = [];//获取商品select时数组
    $scope.storeUuid = [];//获取分发门店广播的数组
    $scope.goodsList = [];//商品列表
    $scope.storeGoodsUuid = [];
    $scope.selectStore = false;//区分是默认分发商店还是修改分发商店
    //总部selct编辑广播
    $scope.$on('pickSchTemplateId', function (event,data) {
        $scope.createObj = {
            uuid:'',
            index:''
        };
        for(var hh = 0 ; hh < $scope.goodsList.length ; hh++){
            if(data.name == $scope.goodsList[hh].name){
                $scope.createObj.uuid = $scope.goodsList[hh].uuid;
                $scope.createObj.index = data.index;
                $scope.createObjArray[data.index]= $scope.createObj;
            }
        }
        $scope.storeGoodsUuid.length = 0;
        for(var w = 0;w < $scope.binArticalAray.length;w++) {
            $scope.createObj = {
                uuid: '',
                index: ''
            };
            $scope.createObj.uuid = $scope.binArticalAray[w].article.uuid;
            $scope.createObj.index = w;
            $scope.storeGoodsUuid.push($scope.createObj);
        }
        for(var t =0 ; t < $scope.createObjArray.length ; t++){
            if($scope.createObjArray[t] && $scope.createObjArray[t].uuid){
                $scope.storeGoodsUuid[$scope.createObjArray[t].index].uuid = $scope.createObjArray[t].uuid;
            }
        }
    });
    //设施模板删除
    $scope.delete = function(uuid){
        modalProvider.modalInstance('确定要删除吗？',0).result.then(function () {
            var $com = $resource(baseUrl+'pickschemetemplate/'+uuid);
            $com.delete(function(data){
                $state.go('app.enterprise.pickschemetemplate.list',null,{
                    reload:true
                });
            });
        });
    };
    //接受点击后发出的设施模板的广播
    $scope.$on('facilityTemplateId',function(event,data){
        for(var hh=0;hh<$scope.facitemplatesList.length;hh++){
            if(data == $scope.facitemplatesList[hh].name){
                $scope.facilityTemplateUuid = $scope.facitemplatesList[hh].uuid;
            }
        }
        //商品列表
        $http.get(baseUrl+'facilitytemplate/'+$scope.facilityTemplateUuid).then(function (resp) {
            $scope.binArticalAray=[];
            getEditRelativeInfo(resp);
            angular.forEach($scope.binEleTags, function (h) {
                var binArticalObj =  {
                    "uuid": h.uuid,
                    "binCode": h.binCode,
                    "article": {
                        "uuid": "",
                        "code": "",
                        "name": ""
                    },
                    "pickSchemeUuid": ""
                };
                $scope.binArticalAray.push(binArticalObj);
            });
            //$scope.binArticalAray [{},{},{}]去重
            var binArticle = [];
            binArticle.push($scope.binArticalAray[0]);
            for(var t=1; t<$scope.binArticalAray.length; t++){
                var str = $scope.binArticalAray[t];
                for(var s=0;s<binArticle.length;s++){
                    if(binArticle[s].binCode.indexOf(str.binCode) == -1){
                        binArticle.push(str);
                    }
                    break;
                }
            }
            $scope.binArticalAray = binArticle;
        });
    });
    //分发门店的广播
    $scope.$on('sharedStoreId',function(event,data){
        $scope.selectStore = true;
        $scope.storeUuid.length = 0;
        if(data.length == 0){
        }else{
            for(var gg=0;gg<data.length;gg++){
                for(var hh=0;hh<$scope.stores.length;hh++){
                    if(data[gg] == $scope.stores[hh].name){
                        $scope.storeUuid.push($scope.stores[hh].uuid);
                    }
                }
            }
        }
    });
    //编辑界面分发
    $scope.editSharedTo = function(uuid){
        modalProvider.modalInstance('确定要分发吗？',1).result.then(function () {
            $http.put(baseUrl+'pickschemetemplate/'+uuid).then(function (resp) {
                if(resp.data.msg == '操作成功'){
                }else{
                    modalProvider.modalInstance(resp.data.msg,1);
                }
            });
        });

    };
    //编辑界面提交（包括新增和编辑）
    $scope.addDataSubmit = function(){
        var binArticleArray =[];//货位商品数组
        var binArticleObj;  //货位商品构造对象
        //如果没有修改商品就把原来的商品返回上去
        if($scope.storeGoodsUuid.length <= 0){
            angular.forEach($scope.goodsUuidFromTemp,function(f){
                $scope.createObj = {uuid:''};
                $scope.createObj.uuid = f;
                $scope.storeGoodsUuid.push($scope.createObj);
            });
        }
        for(var n=0;n<$scope.storeGoodsUuid.length;n++){
            if($scope.storeGoodsUuid[n]){
                for(var k=0;k<$scope.goodsList.length;k++){
                    if($scope.storeGoodsUuid[n].uuid == $scope.goodsList[k].uuid){
                        binArticleObj={  //货位商品构造对象
                            "uuid":"",
                            "binCode":$scope.binArticalAray[n].binCode,
                            "article":{
                                "uuid":$scope.goodsList[k].uuid,
                                "code":$scope.goodsList[k].code,
                                "name":$scope.goodsList[k].name
                            },
                            "pickSchemeUuid":""
                        };
                        binArticleArray.push(binArticleObj); //binEleTags
                    }
                }
            }else{
                binArticleObj={  //货位商品构造对象
                    "uuid":"",
                    "binCode":$scope.binArticalAray[n].binCode,
                    "article":{
                        "uuid":$scope.binArticalAray[n].article.uuid,
                        "code":$scope.binArticalAray[n].article.code,
                        "name":$scope.binArticalAray[n].article.name
                    },
                    "pickSchemeUuid":""
                };
                binArticleArray.push(binArticleObj); //binEleTags
            }
        }
        /////////////////////////////////////////////////////////////////
        $scope.facilityTemplateArray = [];
        angular.forEach($scope.facitemplatesList,function(t){
            if($scope.facilityTemplateUuid !=null){//广播发来的uuid
                if(t.uuid == $scope.facilityTemplateUuid ){
                    $scope.facilityTemplateArray.push(t);
                }
            }else{//默认携带的uuid
                if(t.uuid == $scope.defaultFacilityTemUuid){
                    $scope.facilityTemplateArray.push(t);
                }
            }

        });
        ///////////////////////////////////////////////////////////////////
        var storeArray = [];
        //分发门店默认
        if($scope.storeUuid){
            if($scope.storeUuid.length <= 0 && $scope.selectStore){//人为清空
                if($scope.storeUuid.length == 0){
                    modalProvider.modalInstance('分发门店不能为空',1);
                    return;
                }
            }
            if($scope.storeUuid.length <= 0 && !$scope.selectStore){//没有修改
                if($scope.edit_mode){//编辑模式
                    $scope.storeUuid =  $scope.uuidArray ;
                }else{//新增模式
                    modalProvider.modalInstance('分发门店不能为空',1);
                    return;
                }
            }
            angular.forEach($scope.storeUuid, function (k) {
                angular.forEach($scope.stores,function(h){
                    if(k == h.uuid){
                        storeArray.push(h);
                    }
                });
            });
            ////////////////////////////////////////////////////////////////////
            angular.forEach(storeArray,function(p){
                var pickSchemeStoreObj={
                    "uuid": "",
                    "store": {
                        "uuid":p.uuid,
                        "code":p.name,
                        "name":p.code
                    },
                    "pickSchemeTemplateUuid": ""
                };
                $scope.pickSchemeStoreObjArray.push(pickSchemeStoreObj);
            });
        }else{
            return;
        }
        if(!$scope.edit_mode){//新增
            if($scope.facilityTemplateArray.length == 0){
                modalProvider.modalInstance('请选择设施模板',1);
            }else{
                addNewTemOrEditTemplate(binArticleArray,$stateParams.uuid,$scope.data.obj.version,$scope.data.obj.versionTime,'POST','UnEffective');
            }
        }else{//编辑
            addNewTemOrEditTemplate(binArticleArray,$stateParams.uuid,$scope.data.obj.version,$scope.data.obj.versionTime,'PUT',$scope.data.obj.state);
        }
    };
    function addNewTemOrEditTemplate(binArticleArray,uuid,version,versionTime,method,state){
        modalProvider.modalInstance('确定要提交吗？',1).result.then(function () {
            $http({
                method: method,
                url: baseUrl+'pickschemetemplate',
                data: {
                    "uuid": uuid,
                    "version": version,
                    "versionTime": versionTime,
                    "code": $scope.data.obj.code,
                    "name": $scope.data.obj.name,
                    "state": state,
                    "orgUuid": localStorage.orgUuid,
                    "binArticle": binArticleArray,   //货位商品
                    "facilityTemplate": {  //设施模板
                        "uuid": $scope.facilityTemplateArray[0].uuid,
                        "code": $scope.facilityTemplateArray[0].code,
                        "name": $scope.facilityTemplateArray[0].name
                    },
                    "pickSchemeStores": $scope.pickSchemeStoreObjArray   //分发门店
                }
            }).then(function(data){
                if(data.data.msg == '操作成功'){
                    $state.go('app.enterprise.pickschemetemplate.list',null,{reload:true});
                }else{
                    modalProvider.modalInstance(data.data.msg,1);
                }
            });
        });
    }
    function getEditRelativeInfo(resp){
        var jobPoints = resp.data.obj.jobPoints;
        $scope.binEleTags=[];
        $scope.sections=[];
        angular.forEach(jobPoints,function(data){
            if(data.pickAreas != null){
                angular.forEach(data.pickAreas,function(pickAreasData){
                    if(pickAreasData.sections != null){
                        angular.forEach(pickAreasData.sections,function(sectionsData){
                            $scope.sections.push(sectionsData);
                            if(sectionsData.binEleTags != null){
                                angular.forEach(sectionsData.binEleTags,function(binEleTagsData){
                                    $scope.binEleTags.push(binEleTagsData);
                                })
                            }
                        })
                    }else{//pickAreasData.sections = null
                    }
                });
            }else{//data.pickAreas = null
            }
        });
    }
    //分发门店列表
    $http.get(baseUrl+'store/list/1/-1').then(function (resp) {
        $scope.stores = resp.data.obj.records;
    });
    //获取商品
    $http.get(baseUrl+'article/list/1/-1').then(function (d) {
        $scope.goodsList = d.data.obj.records;
    });
    //设施模板列表
    $http.get(baseUrl+'facilitytemplate/list').then(function (resp) {
        $scope.facitemplatesList = resp.data.obj;
    });
    if ($scope.edit_mode) {//编辑模式
        //默认加载列表
        $http.get(baseUrl+'pickschemetemplate/'+$stateParams.uuid).then(function (resp) {
            $scope.data = resp.data;
            $scope.binArticalAray.length = 0;
            $scope.binArticalAray = $scope.data.obj.binArticle;
            $scope.goodsUuidFromTemp=[];
            angular.forEach($scope.binArticalAray, function (g) {
                $scope.goodsUuidFromTemp.push(g.article.uuid);
            });
            $scope.uuidArray = [];
            $scope.uuidNameArray = [];
            angular.forEach($scope.data.obj.pickSchemeStores,function(q){
                $scope.uuidArray.push(q.store.uuid);
                $scope.uuidNameArray.push(q.store.code);
            });
            $scope.$broadcast('uuidNameArray',$scope.uuidNameArray);
            $scope.defaultGoodsValueObj=resp.data.obj.binArticle;
            ////////////////////////////////////////////////////////////////////////////////
            if(resp.data.obj.facilityTemplate){
                $scope.defaultFacilityTem = resp.data.obj.facilityTemplate.name;
                $scope.defaultFacilityTemUuid = resp.data.obj.facilityTemplate.uuid;
                $scope.defaultFacilityTemName = resp.data.obj.facilityTemplate.name;
                $http.get(baseUrl+'facilitytemplate/'+$scope.defaultFacilityTemUuid).then(function (resp) {
                    getEditRelativeInfo(resp);
                });
            }
        });
    } else {
        $scope.data = {
            "obj":{
                "code":"",
                "name":""
            }
        };
    }
}]);

