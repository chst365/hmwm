/**
 * Created by Administrator on 2016/8/4 0004.
 */

(function(){
  'use strict';
  angular.module('app')
    .provider('modalProvider',function(){
      this.$get=function($modal){
        var service = {
          //门店主机
          /**
           *
           * @param msg 代表提示信息
           * @param template 选择模板，1是选择提交模板，0为删除模板, 2为警告
             */
          modalInstance : function(msg,template){
            return $modal.open({
              templateUrl: template == 1 ? 'tpl/app/modal/confirmModalContent.html' : template == 0 ? 'tpl/app/modal/deleteModalContent.html' : 'tpl/app/modal/warningModalContent.html',
              controller: 'ConfirmModalController',
              size: 'sm',
              resolve: {
                title: function(){
                  return '提示';
                },
                content: function(){
                  return msg;
                }
              }
            });
          },
        };
        return service;
      }
    })
})();
