/**
 * Created by caikaijie on 2016/5/17.
 */
(function(){
  'use strict';
  angular.module('app')
    .provider('hemaProvider',function(){
      this.$get=function($http){
        var service = {
         //门店主机
          getStoreHost : function(){
            return $http.get(baseUrl+'storehost/list?orgUuid='+localStorage.orgUuid );
          },
          removeStoreHost:function(uuid){
            return $http.delete(baseUrl+'storehost/' + uuid );
          },
          editStoreHost:function(uuid){
            return $http.get(baseUrl+'storehost/'+uuid);
          },
          submitStoreHost:function (data) {
            return $http.post(baseUrl+'storehost',data);
          },
          editPutStoreHost:function (data) {
            return $http.put(baseUrl+'storehost',data);
          },
          deletStoreHost:function (uuid) {
            return $http.delete(baseUrl+'storehost/'+ uuid);
          },
         //门店
          getStore : function(page,filter){
            return $http.get(baseUrl+'store/list/'+page+'/5?code='+filter);
          },
          removeStore:function(uuid){
            return $http.delete(baseUrl+'store/' + uuid);
          },
          editStore:function(uuid){
            return $http.get(baseUrl+'store/'+uuid);
          },
          submitStore:function (data) {
            return $http.post(baseUrl+'store',data);
          },
          editPutStore:function (data) {
            return $http.put( baseUrl+'store',data);
          },
          deletStore:function (uuid) {
            return $http.delete(baseUrl+'storehost/'+uuid);
          },
        };
        return service;
      }
    })
})();
