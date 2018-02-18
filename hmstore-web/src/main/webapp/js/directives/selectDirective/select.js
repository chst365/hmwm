var app = angular.module('app');
app.directive('selectNumber', function($resource,$timeout,$http,$rootScope,selectProvider) {
  return {
    restrict : 'AE',
    replace : true,
    link : function(scope,elm,attrs) {
      if(attrs.id.indexOf('pickSchmeId')>0){//门店编辑select
        getData('article/list/1/-1',false,false,3);
      }
      if(attrs.id.indexOf('orderQuery')>0){//总部门店拣货方案查询
        getData('store/list/1/-1',false,false,3);
      }
      if(attrs.id.indexOf('pickSchTemplateId')>0){//总部编辑商品select
        getData('article/list/1/-1',false,false,3);
      }
      if(attrs.id.indexOf('facilityTemplateId')>0){//设施模板select
        getData('facilitytemplate/list',false,false,2);
      }
      if(attrs.id.indexOf('sharedStoreId')>0){//分发门店
        scope.$on('uuidNameArray',function(event,data){
          scope.sharedStoreData= data;
        });
        $http.get(baseUrl+'store/list/1/-1').then(function (resp) {
          scope.sharedStoresList = resp.data.obj.records;
          var a=[];
          angular.forEach(scope.sharedStoresList, function (c) {
            a.push(c.name)
          });
          selectProvider.select3(elm,a,scope.sharedStoreData,true,true);
        });
      }
      if(attrs.id.indexOf('generatorId')>0) {//生成设施
        getData('facilitytemplate/list',false,false,2);
      }
      if(attrs.id.indexOf('jobPointId')>0) {//作业点选择网关
        getData('jobpoint/list?orgUuid='+ localStorage.orgUuid,false,false,2);
      }
      ///////////////////////////////////////////////
      if(attrs.id.indexOf('pickGateway')>0) {//总部设施模板页面作业点选择网关
        $http.get(baseUrl+'gateway/list?orgUuid=' + localStorage.orgUuid).then(function (resp) {
          scope.selectGatewayinjobpoint = resp.data.obj;
          scope.choosenGatewayInfo = [];
          angular.forEach(scope.selectGatewayinjobpoint, function (c) {
            scope.choosenGatewayInfo.push(c.code + "<" + c.ip + ":" + c.port + ">");
          });
          selectProvider.select3(elm,scope.choosenGatewayInfo,attrs.optiondata,true,false);
        })
      }
      /*******第三层次区段*******/
     if(attrs.id.indexOf('newPickGateway')>0) {//总部设施模板页面作业点 货位选择网关结点标签
        $http.get(baseUrl+'gateway/list?orgUuid=' + localStorage.orgUuid).then(function (resp) {
          scope.selectNewGatewayinjobpoint = resp.data.obj;
          scope.newchoosenJobPointGatewayInfo = [];
          angular.forEach(scope.selectNewGatewayinjobpoint, function (c) {
            scope.newchoosenJobPointGatewayInfo.push(c.code + "<" + c.ip + ":" + c.port + ">");
          });
          selectProvider.select3(elm,scope.newchoosenJobPointGatewayInfo,attrs.optiondata,true,false);
        })
      }
      if(attrs.id.indexOf('fcGoodsPositionGatewayNodeTag')>0) {//总部设施模板页面作业点 分区选择网关结点标签
        $http.get(baseUrl+"jobpoint/unuse_eletags?orgUuid="+ localStorage.orgUuid+ "&type=" + $rootScope.optionDataNodeType + "&gatewayUuid=" + $rootScope.optionDataUuid + "&facilityTemplateUuid=" + $rootScope.fcfacilityTemplateUuid).then(function (resp) {
          scope.selectGatewayinjobpointGP = resp.data.obj;
          var a = [];
          angular.forEach(scope.selectGatewayinjobpointGP, function (c) {
            a.push(c);
          });
          selectProvider.select3(elm,a,attrs.optiondata,true,false);
        })
      }
//////////////////////////////////////////////////////////////////////////////////////////////////////////
//change事件
      elm.bind('change',function(value){
          sendBroadcast('pickSchmeId',true);
        if(attrs.id.indexOf('pickSchTemplateId')>0){//总部编辑select商品广播
          sendBroadcast('pickSchTemplateId',true);
        }
        if(attrs.id.indexOf('belongStore')>0){
          sendBroadcast('belongStore',true);
        }
        if(attrs.id.indexOf('facilityTemplateId')>0){
          scope.$emit('facilityTemplateId',elm.select3('value'));
        }
        if(attrs.id.indexOf('sharedStoreId')>0){
          scope.$emit('sharedStoreId',elm.select3('value'));
        }
        if(attrs.id.indexOf('generatorId')>0){
          scope.$emit('facilityTemp',elm.select3('value'));
        }
        if(attrs.id.indexOf('jobPointId')>0){
          scope.$emit('jobPointId',elm.select3('value'));
        }
        //////////////////////////////设施模板
        if(attrs.id.indexOf('pickGateway')>0){//设施模板作业点选取网关
          sendBroadcast('jobPointContactGateway',false);
        }
        if(attrs.id.indexOf('newPickGateway')>0){
          sendBroadcast('newPickGateway',false);
        }
        if(attrs.id.indexOf('fcGoodsPositionGatewayNodeTag')>0){
          scope.$emit('fcGoodsPositionGatewayNodeTag',elm.select3('value'));
        }
      });
      function getData(url,isClear,isMulti,level){
        $http.get(baseUrl+url).then(function (d) {
          if(level == 3){
            scope.b = d.data.obj.records;
          }else if(level == 2){
            scope.b = d.data.obj;
          }
          var a=[];
          angular.forEach(scope.b, function (c) {
            a.push(c.name)
          });
          selectProvider.select3(elm,a,attrs.optiondata,isClear,isMulti);
        });
      }
      function sendBroadcast(who,needIndex){
        if(needIndex){
          var t = attrs.id.split('_');
          var obj = {
            name:elm.select3('value'),
            index:t[2]
          };
          scope.$emit(who,obj);
        }else{
          var transfer = {
            changeData:elm.select3('value'),
            allData:scope.selectNewGatewayinjobpoint
          };
          scope.$emit(who,transfer);
        }

      }
    }
  }
});
