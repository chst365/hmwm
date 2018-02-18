app.controller('PickSchemeListController', ['$scope', '$state', '$stateParams', '$resource', '$modal','$http','modalProvider', function($scope, $state, $stateParams, $resource, $modal,$http,modalProvider) {
    getPickSchemeList();
    $scope.import = function (size) {
        var modalInstance = $modal.open({
            templateUrl: 'tpl/app/modal/import/importModalContent.html',
            controller: 'ImportModalController',
            size: size,
            resolve: {
                title: function() {
                    return '导入拣货方案信息';
                }
            }
        });
        modalInstance.result.then(function (selectedItem) {
            getPickSchemeList();
        });
    };
    //删除
    $scope.remove = function(uuid) {
        modalProvider.modalInstance('确定要删除本条数据?',0).result.then(function(){
            $http({
                method: 'DELETE',
                url: baseUrl+'pickscheme/'+uuid
            }).then(function(d){
                if(d.data.msg == '操作成功'){
                    getPickSchemeList();
                }else{
                    modalProvider.modalInstance(d.data.msg,1);
                }

            });
        });
    };
    //生效
    $scope.startEffective = function(uuid){
        modalProvider.modalInstance('确定要生效本条数据?',1).result.then(function () {
            $http({
                method: 'PUT',
                url: baseUrl+'pickscheme/'+uuid
            }).then(function(d){
                getPickSchemeList();
            });
        });
    };
    function getPickSchemeList(){
        $http.get(baseUrl+'pickscheme/list?orgUuid='+localStorage.orgUuid).then(function (d) {
            for(var k=0;k<d.data.obj.length;k++){
                if(d.data.obj[k].state == 'Using'){
                    d.data.obj[k].state = '0';
                }else if(d.data.obj[k].state == 'UnEffective'){
                    d.data.obj[k].state = '1';
                }
            }
            $scope.datalength = d.data.obj.length;
            $scope.data = d.data;
        });
    }
    //编辑
    $scope.toEdit = function (uuid,code,name) {
        $state.go('app.store.pickscheme.edit',{uuid:uuid,code:code,name:name},{
            reload:true
        });
    };
}]);


app.controller('PickSchemeEditController', ['$scope', '$state', '$stateParams', '$http','$modal','$location','modalProvider', function($scope, $state, $stateParams, $http,$modal,$location,modalProvider) {
    $scope.storeGoodsUuid = [];//获取select商品uuid的数组
    var storeGoodsObj;
    //定义上传商品对象的数组
    $scope.storeGoodsArray=[];
    $scope.createObj={};
    $scope.createObjArray = [];
    $scope.noSelectIndex=[];//用来存放修改部分selct商品数组的下标
    $scope.noSelectIndexReArray=[];
    $scope.shapes = []; //商品下拉框数组
    $scope.isModifyPoint = false;//是否修改作业点
    $scope.selectPoint = {
        "uuid": "",
        "code": "",
        "name": ""
    };
    $scope.eachStoreGoodsArray = [];
    //监听商品select选择发来的广播
    $scope.$on('pickSchmeId', function (event,data) {
        $scope.createObj = {
            uuid:'',
            index:''
        };
        for(var hh = 0 ; hh < $scope.goodslist.length ; hh++){
            if(data.name == $scope.goodslist[hh].name){
                $scope.createObj.uuid = $scope.goodslist[hh].uuid;
                $scope.createObj.index = data.index;
                $scope.createObjArray[data.index]= $scope.createObj;
            }
        }
        $scope.storeGoodsUuid.length = 0;
        if($scope.isModifyPoint){//没有修改作业点
            angular.forEach($scope.shapes,function(d){
                if(d.name == data.name){
                    storeGoodsObj = {
                        "uuid": $scope.defaultGoodsValueObj[data.index].uuid,
                        "binCode": $scope.defaultGoodsValueObj[data.index].binCode,
                        "article": {
                            "uuid": d.uuid,
                            "code": d.code,
                            "name": d.name
                        },
                        "pickSchemeUuid": ""
                    };
                    $scope.eachStoreGoodsArray.push(storeGoodsObj);
                }
            });
            $scope.storeGoodsArray = $scope.eachStoreGoodsArray;
        }else{//修改作业点
            for(var w = 0;w < $scope.defaultGoodsValueObj.length;w++) {
                $scope.createObj = {
                    uuid: $scope.defaultGoodsValueObj[w].article.uuid,
                    index: w
                };
                $scope.storeGoodsUuid.push($scope.createObj);
            }
            for(var t =0 ; t < $scope.createObjArray.length ; t++){
                if($scope.createObjArray[t] && $scope.createObjArray[t].uuid){
                    $scope.storeGoodsUuid[$scope.createObjArray[t].index].uuid = $scope.createObjArray[t].uuid;
                }

            }
        }
    });
    //监听作业点发来的广播
    $scope.$on('jobPointId', function (event,data) {
        $scope.isModifyPoint = true;
        $http.get(baseUrl+'jobpoint/list?orgUuid='+ localStorage.orgUuid).then(function (resp) {
            $scope.jobPoint = resp.data.obj;
            angular.forEach($scope.jobPoint, function (c) {
                if(c.name == data){
                    $scope.selectPoint.uuid = c.uuid;
                    $scope.selectPoint.code = c.code;
                    $scope.selectPoint.name = c.name;
                    if(c.pickAreas.length>0){
                        angular.forEach(c.pickAreas,function(q){
                            if(q.sections.length > 0){
                                angular.forEach(q.sections, function (a) {
                                    if(a.binEleTags.length > 0){
                                        $scope.defaultGoodsValueObj = [];
                                        $scope.defaultGoodsValueObj = a.binEleTags;
                                    }
                                });
                            }
                        });
                    }
                }
            });
        })
    });
    $http.get(baseUrl+'jobpoint/list?orgUuid='+localStorage.orgUuid).then(function (d) {
        angular.forEach(d.data.obj,function(data){
            if(data.pickAreas != null){
                angular.forEach(data.pickAreas,function(pickAreasData){
                    if(pickAreasData.sections != null){
                        angular.forEach(pickAreasData.sections,function(sectionsData){
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
    });
    //获取商品
    $scope.binEleTags=[];
    $scope.sections=[];
    $http.get(baseUrl+'article/list/1/-1').then(function (d) {
        $scope.goodslist = d.data.obj.records;
        $scope.shapes = $scope.goodslist;
    });
    //编辑/新增状态下的提交
    $scope.addSubmit = function(){ //新增拣货方案$scope.edit_mode
        if($scope.isModifyPoint){
        }else{
            if($scope.storeGoodsUuid.length <= 0){//如果没有选择，默认
                angular.forEach($scope.goodsUuidFromTemp,function(f){
                    $scope.createObj = {uuid:''};
                    $scope.createObj.uuid = f;
                    $scope.createObjArray.push($scope.createObj);
                });

                $scope.storeGoodsUuid = $scope.createObjArray;
            }
            for(var h=0;h<$scope.storeGoodsUuid.length;h++){
                if($scope.storeGoodsUuid[h]){
                    angular.forEach($scope.goodslist, function (f) {
                        if($scope.storeGoodsUuid[h].uuid == f.uuid){
                            storeGoodsObj = {
                                "uuid": "",
                                "binCode": $scope.defaultGoodsValueObj[h].binCode,
                                "article": {
                                    "uuid": f.uuid,
                                    "code": f.code,
                                    "name": f.name
                                },
                                "pickSchemeUuid": ""
                            };
                            $scope.storeGoodsArray.push(storeGoodsObj);
                        }
                    });
                }else{
                    storeGoodsObj = {
                        "uuid": "",
                        "binCode": $scope.defaultGoodsValueObj[h].binCode,
                        "article": {
                            "uuid": $scope.defaultGoodsValueObj[h].article.uuid,
                            "code": $scope.defaultGoodsValueObj[h].article.code,
                            "name": $scope.defaultGoodsValueObj[h].article.name
                        },
                        "pickSchemeUuid": ""
                    };
                    $scope.storeGoodsArray.push(storeGoodsObj);
                }
            }
        }
        if($scope.selectPoint.uuid == ''){
            //判断是编辑模式还是新增模式
            if(!$scope.edit_mode){
                modalProvider.modalInstance('作业点不能为空',1);
                return;
            }else{
                $scope.selectPoint = {
                    "uuid": $scope.data.jobPoint.uuid,
                    "code": $scope.data.jobPoint.code,
                    "name": $scope.data.jobPoint.name
                };
            }
        }
        if(!$scope.edit_mode){//新增
            submitAddTemOrEditTem('POST','',0,'',"UnEffective");
        }else{//编辑
            submitAddTemOrEditTem('PUT',$scope.data.uuid,$scope.data.version,$scope.data.versionTime,$scope.data.state);
        }
    };

    function submitAddTemOrEditTem(method,uuid,version,versionTime,state){
        modalProvider.modalInstance('确定提交吗',1).result.then(function () {
            $http({
                method: method,
                url: baseUrl+'pickscheme',
                data: {
                    "uuid": uuid,
                    "version": version,
                    "versionTime": versionTime,
                    "code": $scope.data.code,
                    "name": $scope.data.name,
                    "state": state,
                    "orgUuid": localStorage.orgUuid,
                    "jobPoint": $scope.selectPoint,
                    "binArticle": $scope.storeGoodsArray
                }
            }).then(function(d){
                if(d.data.msg == '操作成功'){
                    $state.go('app.store.pickscheme.list',null,{reload:true});
                }else{
                    modalProvider.modalInstance(d.data.msg,1);
                }
            });
        });
    }
    //开始生效
    $scope.startEffective = function () {  //http://172.17.4.90:8080/hmstore-web/pickscheme/ss
        modalProvider.modalInstance('确定生效?',1).result.then(function () {
            $http({
                method: 'PUT',
                url: baseUrl+'pickscheme/'+$stateParams.uuid
            }).then(function(d){
                if(d.data.msg == '操作成功'){
                    $state.go('app.store.pickscheme.list',null,{reload:true});
                }else{
                    modalProvider.modalInstance(d.data.msg,1);
                }
            });
        });

    };
    //编辑界面上删除
    $scope.delete = function () {
        modalProvider.modalInstance('确认删除本条数据?',0).result.then(function(){
            $http({
                method: 'DELETE',
                url: baseUrl+'pickscheme/'+$stateParams.uuid
            }).then(function(d){
                if(d.data.msg == '操作成功'){
                    $state.go('app.store.pickscheme.list',null,{reload:true});
                }
            });
        });
    };
    $scope.edit_mode = !!$stateParams.uuid;
    //$scope.goodslist = [];
    //$http.get('app/goods/list.json').then(function (resp) {
    //    $scope.goodslist = resp.data.results;
    //});
    if ($scope.edit_mode) {
        $http.get(baseUrl+'pickscheme/'+$stateParams.uuid).then(function (resp) {
            $scope.data = resp.data.obj;
            $scope.defaultGoodsValueObj=resp.data.obj.binArticle;
            $scope.goodsUuidFromTemp=[];
            angular.forEach($scope.defaultGoodsValueObj, function (g) {
                $scope.goodsUuidFromTemp.push(g.article.uuid);
            });
        });
    } else {
        $scope.data = {
            "code":"",
            "name":"",
            "version":'0'
        };
    }
}]);

