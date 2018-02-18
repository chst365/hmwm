app.controller('ConfirmModalController', ['$scope', '$modalInstance', 'FileUploader', 'title', 'content','$http','$location',function($scope, $modalInstance, FileUploader, title, content,$http,$location){
    $scope.title = title || '确认对话框';
    $scope.content = content;
    $scope.rememberMe = false;

    $scope.ok = function (name,pwd) {
        if($location.path() == '/app/dashboard'){
            if(name != null &&　pwd != null){

                postAccountInfo(name,pwd);
            }else{
                //alert("用户名或者密码为空");
                $modalInstance.close(null);
            }
        }else{
            $modalInstance.close(null);
        }
    };
        $scope.cancel = function () {
        $modalInstance.dismiss('cancel');
    };

    function postAccountInfo (name,pwd) {
        //console.log("用户名="+name+"密码="+pwd);
        $http({
            method: 'POST',
            url: baseUrl+'auth/login',
            params :{
                userCode: name,
                password: pwd
            }
        }).then(function(d){
            //d.getResponseHeader();

            //console.log("生效返回数据="+angular.toJson(d));
            //console.log("获取session=");
            if(d.data.msg == '操作成功'){
                //console.log('d.data.obj.orgUuid='+d.data.obj.orgUuid);
                localStorage.orgUuid = d.data.obj.orgUuid;//组织uuid
                localStorage.orgCode = d.data.obj.orgCode;
                localStorage.aleadyLogin = 'yes';
                if($scope.rememberMe){
                    localStorage.userName = name;
                    localStorage.password = pwd;
                }
                //console.log('localStorage.orgUuid='+localStorage.orgUuid);
                //console.log('localStorage.orgCode='+localStorage.orgCode);
                $modalInstance.close(null);
            }else{
                alert('用户名或者密码错误');
            }
        });

    }
}]);
