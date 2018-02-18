/**
 * Created by luyongjie on 2016/7/19.
 */
'use strict';

/* Controllers */

angular.module('app')
    .controller('cstcontroller', ['$scope','$resource','$state', function($scope,$resource,$state) {
          // $scope.a = "hahahahah!";
          var $com = $resource(baseUrl+'menu/'+localStorage.userName);
          $com.get(function(data){
            $scope.data=data
            //console.log("一系列的信息="+angular.toJson($scope.data))
          })

        }]);
