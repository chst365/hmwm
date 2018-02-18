'use strict';

/* Controllers */

angular.module('app')
  .controller('AppCtrl', ['$scope', '$translate', '$localStorage', '$window', '$resource','$state','$location','$http','$rootScope','$modal','modalProvider',
    function($scope, $translate, $localStorage, $window,$resource,$state,$location,$http,$rootScope,$modal,modalProvider) {
      // add 'ie' classes to html
      var isIE = !!navigator.userAgent.match(/MSIE/i);
      isIE && angular.element($window.document.body).addClass('ie');
      isSmartDevice( $window ) && angular.element($window.document.body).addClass('smart');

      $resource(baseUrl+'menu/'+localStorage.userName).get(function(data){$scope.headerData=data});
      // config
      $scope.app = {
        name: 'WmsCloud',
        companyName: '上海海鼎信息工程股份有限公司',
        version: '1.0.0',
        // for chart colors
        color: {
          primary: '#7266ba',
          info:    '#23b7e5',
          success: '#27c24c',
          warning: '#fad733',
          danger:  '#f05050',
          light:   '#e8eff0',
          dark:    '#3a3f51',
          black:   '#1c2b36'
        },
        settings: {
          themeID: 1,
          navbarHeaderColor: 'bg-black',
          navbarCollapseColor: 'bg-white-only',
          asideColor: 'bg-black',
          headerFixed: true,
          asideFixed: false,
          asideFolded: false,
          asideDock: false,
          container: false
        }
      };

      // save settings to local storage
      if ( angular.isDefined($localStorage.settings) ) {
        $scope.app.settings = $localStorage.settings;
      } else {
        $localStorage.settings = $scope.app.settings;
      }
      $scope.$watch('app.settings', function(){
        if( $scope.app.settings.asideDock  &&  $scope.app.settings.asideFixed ){
          // aside dock and fixed must set the header fixed.
          $scope.app.settings.headerFixed = true;
        }
        // save to local storage
        $localStorage.settings = $scope.app.settings;
      }, true);

      // angular translate
      $scope.lang = { isopen: false };
      $scope.langs = {en:'English', zh_cn:'中文(简体)'};
      $scope.selectLang = $scope.langs[$translate.proposedLanguage()] || "中文(简体)";
      $scope.setLang = function(langKey, $event) {
        // set the current lang
        $scope.selectLang = $scope.langs[langKey];
        // You can change the language during runtime
        $translate.use(langKey);
        $scope.lang.isopen = !$scope.lang.isopen;
      };
      function isSmartDevice( $window )
      {
          // Adapted from http://www.detectmobilebrowsers.com
          var ua = $window['navigator']['userAgent'] || $window['navigator']['vendor'] || $window['opera'];
          // Checks for iOs, Android, Blackberry, Opera Mini, and Windows mobile devices
          return (/iPhone|iPod|iPad|Silk|Android|BlackBerry|Opera Mini|IEMobile/).test(ua);
      }
      if(localStorage.name){
        $scope.userName = localStorage.name;
      }
      window.onfocus = function(){
        if(localStorage.userName && localStorage.password){
          //var windowChangeUri = $location.path().substring(1,$location.path().length).replace(/\//g, ".");
          $http({method: 'POST', url: baseUrl+'auth/login', params :{userCode: localStorage.userName, password: localStorage.password}}).then(function(d){
            if(d.data.msg == '操作成功' && d.data.obj != null){
              $scope.loginPageShow = false;
              loginStorage(d.data.obj.orgUuid,d.data.obj.orgCode,'yes',d.data.obj.code,localStorage.password,d.data.obj.name);
              $scope.userName = d.data.obj.name;
              $resource(baseUrl+'menu/'+localStorage.userName).get(function(data){$scope.headerData=data});
            }
          });

        }
      };
      //登出
      $scope.logout = function () {
        var $com = $resource(baseUrl+'auth/logout');
        $com.get(function(data){
          if(data.msg == '操作成功'){
            $scope.loginPageShow = true;
            loginStorage(null,null,null,null,null,null);
            $state.go('app.dashboard',null,{reload:true});
          }
        });
      };
      $scope.toGenerate = function(){$state.go('app.store.genfromtemplate',null,{reload:true});
      };
      $scope.loginPageShow = true;//显示登录界面
      //登录
      $scope.login = function (name,pwd) {
        if($location.path() == '/app/dashboard'){
          if(name != null &&　pwd != null){
            postAccountInfo(name,pwd);
          }else{
            modalProvider.modalInstance("用户名或者密码为空",1);
          }
        }else{
        }
      };
      function postAccountInfo (name,pwd) {
        $http({method: 'POST', url: baseUrl+'auth/login', params :{userCode: name, password: pwd}}).then(function(d){
          if(d.data.msg == '操作成功'){
            $scope.loginPageShow = false;
            $state.go('app.dashboard',null,{reload:true});
            loginStorage(d.data.obj.orgUuid,d.data.obj.orgCode,'yes',name,pwd, d.data.obj.name);
            $scope.userName = d.data.obj.name;
            $resource(baseUrl+'menu/'+localStorage.userName).get(function(data){$scope.headerData=data});
          }else{
            modalProvider.modalInstance(d.data.msg,1);
          }
        });
      }
      function loginStorage(orgUuid,orgCode,aleadyLogin,userName,password,name){
        localStorage.orgUuid = orgUuid;//组织uuid
        localStorage.orgCode = orgCode;
        localStorage.aleadyLogin = aleadyLogin;
        localStorage.userName = userName;
        localStorage.password = password;
        localStorage.name = name;
      }
      if((localStorage.userName != 'null' && localStorage.password != 'null' && localStorage.userName != undefined)
          ||  localStorage.aleadyLogin != 'null' && localStorage.aleadyLogin != undefined){
        $scope.loginPageShow = false;
      }
    }]);
