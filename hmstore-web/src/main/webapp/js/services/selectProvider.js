/**
 * Created by Administrator on 2016/8/5 0005.
 */

(function(){
  'use strict';
  angular.module('app')
    .provider('selectProvider',function(){
      this.$get=function($modal){
        var service = {
          /**
           *
           * @param elm
           * @param item
           * @param value
           * @param allow
             * @param mult
             * @returns {*}
             */
          select3 : function(elm,item,value,allow,mult){
            return elm.select3({
              allowClear: allow,
              items: item,
              multiple: mult,
              placeholder: '请选择',
              value: value
            });
          }
        };
        return service;
      }
    })
})();
