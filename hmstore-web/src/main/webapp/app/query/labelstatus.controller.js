 app.controller('LabelStatusController', ['$scope', '$http', '$filter','hemaAPI','$stateParams','$location', function($scope, $http, $filter,hemaAPI,$stateParams,$location) {

   if($location.path() == '/app/sto/query/labelstatus'){//门店标签状态
       $scope.storesCorrectStatusArray = [];//门店正常状态数组
       $scope.storesWarningStatusArray = [];//门店警告状态数组
       $scope.allStoresStatusInfoArray ='';
       $scope.items =[];//用于存放界面绑定的数组对象
       $scope.backgroundColorFlag = 0;
       getLabelStatusDataByOrgUuid();
   }else{//总部标签状态
       $scope.storeListsInfo = '';//存放门店的数组
       $scope.envAllStoresCorrectStatusArray = [];//总部所有正常状态数组
       $scope.envAllStoresWarningStatusArray = [];//总部所有警告状态数组
       $scope.entCorrectAndWarningConcat = [];//总部所有状态数组
       ////////////////////////////////////////////////////////
       $scope.storeObj={};
       $scope.storeObjArray = [];
       $scope.envItem = [];//用于界面数组统一绑定
       $scope.filterStore = 'allStatus';//全部门店
       $scope.backgroundColorFlag = 3;//全部状态
       ///////////////////////////////////////////////////////
       $scope.isClickStore = false;
       $scope.envItemBadge = 0;//badge
       $scope.queryStore = '';//搜索

       function envItemSort(arr){
           arr.sort(function (a,b) {
               return b.checkTime- a.checkTime;
           });
       }
       getLabelStatusData();
   }
    //总部
    function getLabelStatusData(){
        hemaAPI.getGatewayEletag().then(function(resp){
            $scope.storeListsInfo = resp.obj;
            //初始化数组对象
            for(var g=0;g<$scope.storeListsInfo.length;g++){
                $scope.storeObjArray[g]='';
            }
            for(var i=0;i<$scope.storeListsInfo.length;i++){
                $scope.storeObj={//初始化
                    storeName:'',
                    correctStores:[],
                    warningStores:[]
                };
                $scope.storeObj.storeName = $scope.storeListsInfo[i].storeName;
                for(var j=0;j<$scope.storeListsInfo[i].infos.length;j++){
                    if($scope.storeListsInfo[i].infos[j].nodeState == "Normal"){
                        $scope.storeListsInfo[i].infos[j].nodeState =  "正常";
                        $scope.storeListsInfo[i].infos[j].name = $scope.storeListsInfo[i].storeName;//增加门店名称
                        $scope.envAllStoresCorrectStatusArray.push($scope.storeListsInfo[i].infos[j]);
                        $scope.storeObj.correctStores.push($scope.storeListsInfo[i].infos[j]);
                    }
                    if($scope.storeListsInfo[i].infos[j].nodeState == "Warning"){
                        $scope.storeListsInfo[i].infos[j].nodeState = "警告";
                        $scope.storeListsInfo[i].infos[j].name = $scope.storeListsInfo[i].storeName;//增加门店名称
                        $scope.envAllStoresWarningStatusArray.push($scope.storeListsInfo[i].infos[j]);
                        $scope.storeObj.warningStores.push($scope.storeListsInfo[i].infos[j]);
                    }
                }
                $scope.storeObjArray[i] = $scope.storeObj;
            }
            if($scope.envAllStoresWarningStatusArray.length > 0){
                $scope.envItemBadge = $scope.envAllStoresWarningStatusArray.length;
            }
            $scope.entCorrectAndWarningConcat = $scope.envAllStoresCorrectStatusArray.concat($scope.envAllStoresWarningStatusArray);
            $scope.envItem = [];
            $scope.envItem = $scope.entCorrectAndWarningConcat;
            envItemSort($scope.envItem);
        });
    }
    //门店
    function getLabelStatusDataByOrgUuid(){

        $http({
            method: 'GET',
            url: baseUrl+'gateway/eletag/lists?orgUuid='+localStorage.orgUuid
        }).success(function(resp){
            $scope.data = resp.obj;
            angular.forEach(resp.obj,function(lists){
                angular.forEach(lists.infos,function(obj){
                    if(obj.nodeState === "Normal"){
                        obj.nodeState = '正常';
                        $scope.storesCorrectStatusArray.push(obj);
                    }
                    if(obj.nodeState === "Warning"){
                        obj.nodeState = '警告';
                        $scope.storesWarningStatusArray.push(obj);
                    }
                });
            });
            $scope.allStoresStatusInfoArray = $scope.storesCorrectStatusArray.concat($scope.storesWarningStatusArray);
            $scope.items = [];
            $scope.items = $scope.allStoresStatusInfoArray;
            $scope.backgroundColorFlag = 3;
        });
    }
//选择门店处理
    $scope.selectStore = function(storeName) {
        $scope.backgroundColorFlag = 3;
        $scope.filterStore = storeName;
        $scope.envItem= [];
        $scope.isClickStore = true;
        if(storeName == 'allStatus'){
            $scope.envItem = $scope.entCorrectAndWarningConcat;
            $scope.envItemBadge = $scope.envAllStoresWarningStatusArray.length;
        }else{
            angular.forEach($scope.storeObjArray,function(p){
                if(storeName == p.storeName){
                    $scope.envItemBadge = p.warningStores.length;
                    $scope.envItem = p.correctStores.concat(p.warningStores);
                    envItemSort($scope.envItem);
                }
            });
        }
    };
//各个门店下的状态处理
     $scope.envSelectItem = function (status) {
         $scope.envItem = [];
            if($scope.isClickStore){//点击选择过过门店
                if($scope.filterStore == 'allStatus'){
                    checkStatus();
                }else{
                    angular.forEach($scope.storeObjArray,function(p){
                        if($scope.filterStore == p.storeName){
                            $scope.obj = p;
                        }
                    });
                    if(status == 'allStatus'){//所有状态
                        $scope.backgroundColorFlag = 3;
                        $scope.envItem = $scope.obj.correctStores.concat($scope.obj.warningStores);
                        envItemSort($scope.envItem);
                    }else if(status == 'correct'){//正常状态
                        $scope.backgroundColorFlag = 1;
                        $scope.envItem = $scope.obj.correctStores;
                        envItemSort($scope.envItem);
                    }else{//警告状态
                        $scope.backgroundColorFlag = 2;
                        $scope.envItem = $scope.obj.warningStores;
                        envItemSort($scope.envItem);
                    }
                }

            }else{//没有点击选择过门店默认为所有门店
                checkStatus();
            }
         function checkStatus(){
             if(status == 'allStatus'){//所有状态
                 modifyStatus(3,$scope.entCorrectAndWarningConcat);
             }else if(status == 'correct'){//正常状态
                 modifyStatus(1,$scope.envAllStoresCorrectStatusArray);
             }else{//警告状态
                 modifyStatus(2,$scope.envAllStoresWarningStatusArray);
             }
         }

     };
    $scope.selectItem = function(status) { //| date:'yyyy-MM-dd HH:mm:ss'
       if(status == 'correct'){
           modifySelectItem($scope.storesCorrectStatusArray,1);
       }else if(status == 'allStatus'){
           modifySelectItem($scope.allStoresStatusInfoArray,3);
       } else{
           modifySelectItem($scope.storesWarningStatusArray,2);
       }
    };
     function modifySelectItem(correctArray,flag){
         $scope.items = [];
         $scope.items = correctArray;
         $scope.backgroundColorFlag = flag;
     }
     function modifyStatus(flag,data){
         $scope.backgroundColorFlag = flag;
         $scope.envItem = data;
     }
}]);