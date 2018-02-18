app.controller('GenFromTemplateController', ['$scope', '$state', '$stateParams', '$resource', '$modal','$http','modalProvider', function($scope, $state, $stateParams, $resource, $modal,$http,modalProvider) {
    $scope.select="";
    $http.get(baseUrl+'facilitytemplate/list').then(function (data) {
        $scope.templates = data.data.obj;
    });
    $scope.$on('facilityTemp',function(event,data){
       angular.forEach($scope.templates, function (g) {
          if(data == g.name){
              $scope.select = g.uuid;
          }
       });
    });
    $scope.insertToFacility = function(){
        $http({
            method: 'POST',
            url: baseUrl+'facilitytemplate/generator',
            params: {
                "templateUuid": $scope.select,
                "orgUuid": localStorage.orgUuid
            }
        }).then(function(d){
            console.log(angular.toJson(d));
            if(d.data.msg == '操作成功'){
                modalProvider.modalInstance(d.data.msg,1);
            }else{
                modalProvider.modalInstance(d.data.msg,1);
            }

        });
    };

}]);