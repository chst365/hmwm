app.controller('ImportModalController', ['$scope', '$modalInstance', 'FileUploader', 'title','$location','$timeout', function($scope, $modalInstance, FileUploader, title,$location,$timeout){
   //$scope.$on('sendEXL',function(event,data){
   //    console.log('sendEXL');
   //});
    $scope.title = title || '文件导入';

    $scope.ok = function () {
      $modalInstance.close(null);
    };

    $scope.cancel = function () {
      $modalInstance.dismiss('cancel');
    };
    //alert($location.path());
    var urlOption;
    if($location.path() == '/app/ent/pickschemetemplate/list'){
        urlOption = baseUrl+'pickschemetemplate/upload';
    }else if($location.path() == '/app/sto/pickscheme/list'){
        urlOption = baseUrl+'pickscheme/upload';
    }else if($location.path() =='/app/ent/store/list'){
       urlOption =baseUrl+'store/upload';
       urlOption =baseUrl + 'store/upload';
    }else if($location.path() =='/app/ent/facitemplate/list'){
        urlOption =baseUrl + 'facitemplate/upload';
    }else if($location.path() =='/app/sto/goods/list'){
       urlOption =baseUrl+'article/upload';
    }else if($location.path() =='/app/ent/employee/list'){
       urlOption =baseUrl+'user/upload';
    }
    var uploader = $scope.uploader = new FileUploader({
        url: urlOption
        //method:'POST',
        //headers :{'Content-Type':'multipart/form-data'}
    });

    // FILTERS

    uploader.filters.push({
        name: 'customFilter',
        fn: function(item /*{File|FileLikeObject}*/, options) {
            return this.queue.length < 10;
        }
    });

    // CALLBACKS

    uploader.onWhenAddingFileFailed = function(item /*{File|FileLikeObject}*/, filter, options) {
        console.info('onWhenAddingFileFailed', item, filter, options);
    };
    uploader.onAfterAddingFile = function(fileItem) {
        console.info('onAfterAddingFile', fileItem);
    };
    uploader.onAfterAddingAll = function(addedFileItems) {
        console.info('onAfterAddingAll', addedFileItems);
    };
    uploader.onBeforeUploadItem = function(item) {
        console.info('onBeforeUploadItem', item);
    };
    uploader.onProgressItem = function(fileItem, progress) {
        console.info('onProgressItem', fileItem, progress);
    };
    uploader.onProgressAll = function(progress) {
        console.info('onProgressAll', progress);
        $timeout(function () {
            $modalInstance.close(null);

          //$scope.$emit("loadPickSchemeTemData",'yes');
            //alert('okdd');
        },2000);
    };
    uploader.onSuccessItem = function(fileItem, response, status, headers) {
        console.info('onSuccessItem', fileItem, response, status, headers);
    };
    uploader.onErrorItem = function(fileItem, response, status, headers) {
        console.info('onErrorItem', fileItem, response, status, headers);
    };
    uploader.onCancelItem = function(fileItem, response, status, headers) {
        console.info('onCancelItem', fileItem, response, status, headers);
    };
    uploader.onCompleteItem = function(fileItem, response, status, headers) {
        console.info('onCompleteItem', fileItem, response, status, headers);
    };
    uploader.onCompleteAll = function() {
        console.info('onCompleteAll');
    };
}]);
