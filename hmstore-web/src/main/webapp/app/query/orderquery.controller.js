app.controller('OrderQueryController', ['$scope', '$http', '$filter','$location', function($scope, $http, $filter,$location) {

    $scope.pickerPercent = 0;//当日分拣百分比
    $scope.pickerAleadyCompleted = 0;//当日分拣已经完成
    $scope.format = 'yyyy-MM-dd';
    $scope.dt =$filter('date')(new Date(),'yyyy-MM-dd');
    $scope.storesCheckData=[];//折线图数据
    for(var j=0;j<24;j++){
        $scope.storesCheckData.push(0);
    }
    //初始化折线图
    getAxisChartOption();
    $scope.axisChart = echarts.init(document.getElementById('entLineEchart'));
    $scope.axisChart.setOption($scope.axisOption);

    //初始化饼图
    getPieChartOption();
    $scope.chart = echarts.init(document.getElementById('entCircleEchart'));
    $scope.chart.setOption($scope.pieChartOtion);


    $(window).resize(function () {
        $scope.axisChart.resize();
        $scope.chart.resize();
    });
    //搜索
    $scope.$watch('search_context',function(newValue,oldValue, scope){
        if($location.path() == '/app/sto/query/orderquery'){//门店
            searchFunc(localStorage.orgCode,newValue,oldValue);
        }else{//总部搜索
            searchFunc($scope.selectStores,newValue,oldValue);
        }
    });
    /**
     *
     */
    function searchFunc(uuid,newValue,oldValue){
        $http.get(baseUrl+'storepickstatus/listdate?storeCodes='+uuid+'&pickDate='+$filter('date')($scope.dt,'yyyy-MM-dd')).then(function (resp) {
            console.log('选择日期后返回='+angular.toJson(resp));
            var searchObj = [];
            angular.forEach(resp.data.obj.status, function(d) {
                if(newValue!=null){
                    if(d.batchNumber.toLowerCase().indexOf(newValue.toLowerCase())>=0){
                        if(d.pickState == '已完成'){
                            d.pickState = '1';
                            $scope.pickerAleadyCompleted++;
                        }else{
                            d.pickState = '0';
                        }
                        searchObj.push(d);
                    }
                }
            });
            if((newValue==undefined && oldValue==undefined)||(newValue==null)){
            }else{
                if(newValue!=undefined){
                    $scope.dataObj = searchObj;
                    $scope.listLength = $scope.dataObj.length;
                }
            }
        });
    }
    //门店拣货情况查询
    if($location.path() == '/app/sto/query/orderquery'){
        //监听门店拣货查询界面时间变化
        $scope.$watch('dt',function(newValue,oldValue,scope){
           if(newValue != oldValue){
               $http.get(baseUrl+'storepickstatus/listdate?storeCodes='+localStorage.orgCode+'&pickDate='+$filter('date')($scope.dt,'yyyy-MM-dd')).then(function (resp) {
                   if(resp.data.obj.status.length == 0){
                       $scope.pickerPercent = 0;
                       $scope.storesCheckData = [];
                       for(var j=0;j<24;j++){
                           $scope.storesCheckData.push(0);
                       }
                   }
                   setDataToModel(resp.data.obj);
               });
           }
        });

    }else{//总部拣货情况查询
        //获取门店列表
        $http.get(baseUrl+'store/list/1/-1').then(function (resp) {
            $scope.stores = resp.data.obj.records;
        });
        $scope.$on('belongStore', function (event,data) {
            angular.forEach($scope.stores, function (d) {
              if(d.name==data.name){
                  //console.log("d.uuid="+d.code)
                $scope.selectStores = d.code;
              }
            });
            $scope.pickerAleadyCompleted = 0;
            $http.get(baseUrl+'storepickstatus/listdate?storeCodes='+$scope.selectStores+'&pickDate='+$filter('date')($scope.dt,'yyyy-MM-dd')).then(function (resp) {
                if(resp.data.obj.charts.length == 0){
                    $scope.storesCheckData=[];//折线图数据
                    for(var j=0;j<24;j++){
                        $scope.storesCheckData.push(0);
                    }
                }
                setDataToModel(resp.data.obj);
            });


        });

        $scope.$watch('dt',function(newValue,oldValue,scope){
            $scope.pickerAleadyCompleted = 0;
            $http.get(baseUrl+'storepickstatus/listdate?storeCodes='+$scope.selectStores+'&pickDate='+$filter('date')($scope.dt,'yyyy-MM-dd')).then(function (resp) {
                if(resp.data.obj.status.length == 0){
                    $scope.pickerPercent = 0;
                    $scope.storesCheckData = [];
                    for(var j=0;j<24;j++){
                        $scope.storesCheckData.push(0);
                    }
                }
                setDataToModel(resp.data.obj);
            });
        });

    }
    //////////////////////////////////////////////////////////////////////////////////
    function setDataToModel(d){
        $scope.data = d;
        $scope.listLength = $scope.data.length;
        $scope.pickerAleadyCompleted = 0;
        angular.forEach($scope.data.status,function(d){
            if(d.pickState == '已完成'){
                d.pickState = '1';
                $scope.pickerAleadyCompleted++;
            }else{
                d.pickState = '0';
            }
        });
        $scope.dataObj = $scope.data.status;
        for(var l=0;l<24;l++){
            for(var i=0;i<$scope.data.charts.length;i++){
                if($scope.data.charts[i].createHours == l){
                    $scope.storesCheckData[l]= $scope.data.charts[i].orderCount;
                }
            }
        }
        $scope.pickerPercent = ($scope.pickerAleadyCompleted / $scope.data.status.length)*100;
        //console.log("百分比="+angular.toJson($scope.pickerPercent));
        $scope.listLength = $scope.data.status.length;
        if($scope.pickerAleadyCompleted && $scope.data.status.length){
            $scope.pickerPercent = parseInt(($scope.pickerAleadyCompleted / $scope.data.status.length)*100);
        }else{
            $scope.pickerPercent=0;
        }
        getAxisChartOption();
        $scope.axisChart.setOption($scope.axisOption);
        getPieChartOption();
        $scope.chart.setOption($scope.pieChartOtion);
    }
    ////////////////////////////////////////////////////////////////////////////////////
    /*
    *配置左边的折线图
    */
    function  getAxisChartOption(){
        $scope.axisOption = {
            color:['#4CDA2A'],
            tooltip : {
                trigger: 'axis'
            },
            toolbox: {
                show : true
            },
            calculable : true,
            xAxis : [
                {
                    name:'小时(h)',
                    type : 'category',
                    boundaryGap : false,
                    data : ['0h','1h','2h','3h','4h','5h','6h','7h','8h','9h','10h','11h','12h','13h','14h','15h','16h','17h','18h','19h','20h','21h','22h','23h']
                }
            ],
            yAxis : [
                {
                    type : 'value',
                    name : '订单数(份)'
                }
            ],
            series : [
                {
                    name:'订单数',
                    type:'line',
                    stack: '总量',
                    data:$scope.storesCheckData
                }
            ]
        };

    }
    ////////////////////////////////////////////////////////////////////////////////////
    /*
    * 配置右边的饼图
    */
    function getPieChartOption(){
        //$scope.percentValue=90;
        $scope.labelTop = {
            normal : {
                label : {
                    show : true,
                    position : 'center',
                    formatter : '{b}',
                    textStyle: {
                        baseline : 'bottom'
                    }
                },
                labelLine : {
                    show : false
                }
            }
        };
        $scope.labelFromatter = {
            normal : {
                label : {
                    formatter : function (params){
                        return 100 - params.value + '%'
                    },
                    textStyle: {
                        baseline : 'top',
                        fontSize:22,
                        color:'#4CDA2A'
                    }
                }
            }
        };
        $scope.labelBottom = {
            normal : {
                color: '#ccc',
                label : {
                    show : true,
                    position : 'center'
                },
                labelLine : {
                    show : false
                }
            },
            emphasis: {
                color: 'rgba(0,0,0,0)'
            }
        };
        $scope.radius = [90, 95];
        $scope.pieChartOtion ={
            color:['#4CDA2A'],
            legend: {
                textStyle:{
                    fontSize: 30,
                    fontweight: 'bold'
                }
            },
            toolbox: {
                show : true,
                feature : {
                    //dataView : {show: true, readOnly: false},
                    magicType : {
                        show: true,
                        type: ['pie', 'funnel'],
                        option: {
                            funnel: {
                                width: '20%',
                                height: '30%',
                                itemStyle : {
                                    normal : {
                                        label : {
                                            formatter : function (params){
                                                return 'other\n' + params.value + '%\n'
                                            },
                                            textStyle: {
                                                baseline : 'middle'
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            },
            series : [
                {
                    type : 'pie',
                    center : ['50%', '50%'],
                    radius :  $scope.radius,
                    x: '0%', // for funnel
                    itemStyle :  $scope.labelFromatter,
                    data : [
                        {name:'未完成', value:100-$scope.pickerPercent, itemStyle :  $scope.labelBottom,
                            icon:'rect'
                        },
                        {name:'已完成', value:$scope.pickerPercent,itemStyle :  $scope.labelTop,
                            textStyle:{
                                fontSize:30,
                                color:'#333'
                            }
                        }
                    ],
                    textStyle: {
                        fontWeight: '20px'
                    }
                }
            ]};
    }
    $scope.open = function($event) {
      $event.preventDefault();
      $event.stopPropagation();

      $scope.opened = true;
    };
    //搜索查询
    $scope.searchCode = function(key){
        var searchArray = [];
        if(key == null){
            alert('请输入要搜索的内容');
        }else{
            angular.forEach($scope.data, function (d) {
                if(d.batchNumber.indexOf(key) >= 0){
                    searchArray.push(d);
                }
            });
            $scope.data =searchArray;
        }
    }

}]);
