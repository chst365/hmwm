/**
 * Created by luyongjie on 2016/7/27.
 */
'use strict';

/* Filters */
// filter nodeType 筛选改变nodetype 数据
angular.module('hemaFilter')
    .filter('typeFilter', function() {
        return function(item){
            if(item === "PickTag"){
                return "拣货标签";
            }else if(item === "DisplayTag"){
                return "显示标签";
            }else if(item === "PickDisplayQty"){
                return "拣货显示数量";
            }else if(item === "Section"){
                return "区段";
            }else if(item === "RplRequest"){
                return "补货请求";
            }else if(item === "RplResponse"){
                return "补货应答";
            }
        };
    });