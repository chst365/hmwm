/**
 * Created by luyongjie on 2016/7/5.
 */
(function () {
    angular.module('app')
        .factory('hemaAPI', function ($resource,$stateParams) {
        // var protocol = ('https:' == document.location.protocol ? 'https://' : 'http://');
        // var url = protocol +
        // url = protocol + "localhost:8080/api";
            var order = $resource(baseUrl, {}, {
                getGatewayEletag: { //查询所有电子标签列表
                    method: 'get',
                    url: baseUrl + "gateway/eletag/lists"

                },
                getGatewayEletagByOrgUuid: { //查询所有电子标签列表
                    method: 'get',
                    url: baseUrl + "gateway/eletag/lists?orgUuid=:orgUuid"

                },
                getGatewayLists: { //根据标签查询所有网关列表
                    method: 'get',
                    url: baseUrl + "gateway/list?orgUuid=:orgUuid"

                },
                getGatewayListsByUuid: { //根据uuid查询具体一个网关信息
                    method: 'get',
                    url: baseUrl + "gateway/Uuid"
                },
                getFacilityTemplates: { //查询设施模板列表
                    method: 'get',
                    url: baseUrl + "facilitytemplate/list",

                },
                getFacilityTemplateByUuid: { //根据uuid查询查询一个具体的设施模板信息
                    method: 'get',
                    url: baseUrl + "facilitytemplate/:uuid"
                },
                getFacilityTemplates: { //查询设施模板列表
                    method: 'get',
                    url: baseUrl + "facilitytemplate/list"
                },
                getJobPoints: { //查询作业点列表，查询结果集包括分区与区段
                    method: 'get',
                    url: baseUrl + "jobpoint/list?orgUuid=" + localStorage.orgUuid
                },
                getJobPointDetailByUuid: { //根据UUID获取作业点详'细内容
                    method: 'get',
                    url: baseUrl + "jobpoint/:uuid"
                },
                getJobPointDetailByUuid: { //根据UUID获取作业点详细内容
                    method: 'get',
                    url: baseUrl + "jobpoint/:uuid"
                },

                //修改网关 删除网关 新增网关

                //delete
                deleteFacilityTemplateByUuid: { //根据选中设施模板的uuid删除该设施模板及设施模板所包含的所有数据
                    method: 'DELETE',
                    url: baseUrl + "facilitytemplate/:uuid",

                },
                deleteGatewayByUuid: { //根据选中网管的uuid删除该网管及网关所包含的所有数据
                    method: 'DELETE',
                    url: baseUrl + "gateway/:uuid",

                },
                deleteJobPointByUuid: { //根据选中作业点的uuid删除该作业点及作业点所包含的所有数据
                    method: 'DELETE',
                    url: baseUrl + "gateway/:uuid",

                },


                //post

                addGateway: { //根据选中作业点的uuid删除该作业点及作业点所包含的所有数据
                    method: 'post',
                    url: baseUrl + "gateway",
                    parmas: {
                        "uuid": "",
                        "code": "code",
                        "ip": "ip",
                        "port": "port",
                        "templateUuid": "",
                        "orgUuid": "",
                        "tags": [
                            {
                                "uuid": "",
                                "nodeCode": "nodeCode",
                                "nodeAddress": "nodeAddress",
                                "nodeType": "PickTag",
                                "nodeUsage": "PickDisplayQty",
                                "nodeState": "Normal",
                                "checkTime": "checkTime",
                                "remark": "remark",
                                "gatewayUuid": ""
                            }
                        ]
                    }

                },

                addNewfcp: {
                    method: 'post',
                    url: baseUrl + "facilitytemplate",
                    data: {
                        "uuid": "",
                        "code": "nodeCode",
                        "name": "nodeName"
                    }
                }




            });

       return {
           getGatewayEletag: function () {
               var model = order.getGatewayEletag({
               }).$promise;
               return model;
           },
           getGatewayLists: function () {
               var model = order.getGatewayLists({
               }).$promise;
               return model;
           },
           getGatewayListsByUuid: function (Uuid) {
               var model = order.getGatewayListsByUuid({
                   Uuid: Uuid.uuid
               }).$promise;
               return model;
           },
           getFacilityTemplates: function () {
               var modal = order.getFacilityTemplates({
               }).$promise;

               return modal;
           },
           getFacilityTemplateByUuid: function (uuid) {
               //console.log('hahahahaha'+(uuid.uuid));
               var model = order.getFacilityTemplateByUuid({
                   uuid: uuid.uuid
               }).$promise;
               return model;
           },
           getJobPoints: function () {
               var model = order.getJobPoints({
                  // orgUuid: orgUuid
               }).$promise;
               return model;
           },
           getJobPointDetailByUuid: function (Uuid) {
               var model = order.getJobPointDetailByUuid({
                   Uuid: Uuid.uuid
               }).$promise;
               return model;
           },


           //删除方法 $resource delete

           deleteFacilityTemplateByUuid: function (uuid) {
                var model = order.deleteFacilityTemplateByUuid({
                    uuid: uuid
                }).$promise;
                return model;
            },
           deleteGatewayByUuid: function (Uuid) {
               var model = order.deleteGatewayByUuid({
                   Uuid: Uuid.uuid
               }).$promise;
               return model;
           },
           deleteJobPointByUuid: function (Uuid) {
               var model = order.deleteJobPointByUuid({
                   Uuid: Uuid.uuid
               }).$promise;
               return model;
           },


           //增加方法 $resource post
           addGateway: function (parmas) {
               var model = order.addGateway({
                   parmas: {
                       "uuid": "",
                       "code": "code",
                       "ip": "ip",
                       "port": "port",
                       "templateUuid": "",
                       "orgUuid": "",
                       "tags": [
                           {
                               "uuid": "",
                               "nodeCode": "nodeCode",
                               "nodeAddress": "nodeAddress",
                               "nodeType": "PickTag",
                               "nodeUsage": "PickDisplayQty",
                               "nodeState": "Normal",
                               "checkTime": "checkTime",
                               "remark": "remark",
                               "gatewayUuid": ""
                           }
                       ]
                   }
               }).$promise;
               return model;
           },

           addNewfcp: function (data) {
               var model = order.addNewfcp({
                   data: {
                   "uuid": "",
                   "code": "nodeCode",
                   "name": "nodeName"
                   }
               }).$promise;
               return model;
           },

       };
    })
})();
