/**
 * Created by luyongjie on 2016/7/29.
 */
angular.module('app')
.directive('ngUpdateHidden',function() {
    return function(scope, el, attr) {
        var model = attr['ngModel'];
        scope.$watch(model, function(nv) {
            el.val(nv);
        });

    };
})
