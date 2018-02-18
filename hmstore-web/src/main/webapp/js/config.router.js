'use strict';

/**
 * Config for the router
 */
angular.module('app')
  .run(
    [          '$rootScope', '$state', '$stateParams',
      function ($rootScope,   $state,   $stateParams) {
          $rootScope.$state = $state;
          $rootScope.$stateParams = $stateParams;
      }
    ]
  )
  .config(
    ['$stateProvider', '$urlRouterProvider',
      function ($stateProvider, $urlRouterProvider) {
          $urlRouterProvider
              .otherwise('/app/dashboard');
          $stateProvider
              .state('app', {
                  abstract: true,
                  url: '/app',
                  templateUrl: 'tpl/app.html',
                  resolve: {
                      deps: ['$ocLazyLoad',
                        function( $ocLazyLoad){
                            return $ocLazyLoad.load([
                                      'toaster',
                                      'angularFileUpload',
                                      'ui.select',
                                      'angularBootstrapNavTree',
                                      'app/modal/import/importmodal.controller.js',
                                      'app/modal/confirmmodal.controller.js'
                            ]);
                      }]
                  }
              })

              .state('app.dashboard', {
                  url: '/dashboard',
                  templateUrl: 'tpl/app/dashboard.html',
                  resolve: {
                      deps: ['$ocLazyLoad',
                        function( $ocLazyLoad ){
                          return $ocLazyLoad.load(
                              {
                                  name: 'angular-skycons',
                                  files: ['js/app/weather/skycons.js',
                                          'vendor/libs/moment.min.js',
                                          'js/app/weather/angular-skycons.js',
                                          'js/app/weather/ctrl.js' ,
                                      'app/dashbroad/dashbroad.controller.js'
                                  ]
                              }
                          );
                      }]
                  }
              })

              .state('app.enterprise', {
                  abstract: true,
                  url: '/ent',
                  template: '<div ui-view class="fade-in-up"></div><toaster-container toaster-options="{\'position-class\': \'toast-bottom-right\', \'close-button\':true}"></toaster-container>'
              })
              .state('app.store', {
                  abstract: true,
                  url: '/sto',
                  template: '<div ui-view class="fade-in-up"></div>'
              })


              .state('app.enterprise.store', {
                  abstract: true,
                  url: '/store',
                  template: '<div ui-view class="fade-in-up"></div>',
                  resolve: {
                      deps: ['$ocLazyLoad',
                          function($ocLazyLoad) {
                            return $ocLazyLoad.load(['app/store/store.controller.js']);
                          }
                      ]
                  }
              })
              .state('app.enterprise.store.list', {
                url: '/list',
                templateUrl: 'tpl/app/ent/store/list.html'
              })
              .state('app.enterprise.store.edit', {
                url: '/edit/{uuid}',
                templateUrl: 'tpl/app/ent/store/edit.html'
              })

              .state('app.enterprise.employee', {
                  abstract: true,
                  url: '/employee',
                  template: '<div ui-view class="fade-in-up"></div>',
                  resolve: {
                      deps: ['$ocLazyLoad',
                          function($ocLazyLoad) {
                            return $ocLazyLoad.load(['app/employee/employee.controller.js']);
                          }
                      ]
                  }
              })
              .state('app.enterprise.employee.list', {
                url: '/list',
                templateUrl: 'tpl/app/ent/employee/list.html'
              })
              .state('app.enterprise.employee.edit', {
                url: '/edit/{uuid}',
                templateUrl: 'tpl/app/ent/employee/edit.html'
              })

              .state('app.enterprise.role', {
                  abstract: true,
                  url: '/role',
                  template: '<div ui-view class="fade-in-up"></div>',
                  resolve: {
                      deps: ['$ocLazyLoad',
                          function($ocLazyLoad) {
                            return $ocLazyLoad.load(['app/role/role.controller.js']);
                          }
                      ]
                  }
              })
              .state('app.enterprise.role.list', {
                url: '/list',
                templateUrl: 'tpl/app/ent/role/list.html'
              })
              .state('app.enterprise.role.edit', {
                url: '/edit/{uuid}',
                templateUrl: 'tpl/app/ent/role/edit.html'
              })

              .state('app.enterprise.facitemplate', {
                  abstract: true,
                  url: '/facitemplate',
                  template: '<div ui-view class="fade-in-up"></div>',
                  resolve: {
                      deps: ['$ocLazyLoad',
                          function($ocLazyLoad) {
                            return $ocLazyLoad.load(['app/facitemplate/facitemplate.controller.js']);
                          }
                      ]
                  }
              })
              .state('app.enterprise.facitemplate.list', {
                url: '/list',
                templateUrl: 'tpl/app/ent/facitemplate/list.html'
              })
              .state('app.enterprise.facitemplate.edit', {
                url: '/edit/{uuid}',
                templateUrl: 'tpl/app/ent/facitemplate/edit.html'
              })
              .state('app.enterprise.facitemplate.edit.gatewaycontent', {
                url: '/gateway/{code}',
                views:{
                  "gatewayView": { templateUrl: 'tpl/app/ent/facitemplate/gatewayContent.html' }
                },
                //controller: "FaciTemplateGatewayController",
                // resolve: {
                //     getFcGatewayData :function(hemaAPI , $stateParams){
                //         console.log($stateParams);
                //         return hemaAPI.getFacilityTemplateByUuid({uuid:$stateParams.uuid});
                //
                //     }
                // },
                // params:{
                //
                // }
              })
              .state('app.enterprise.facitemplate.newgateway', {
                  url: '/newgateway/{uuid}',
                  templateUrl: 'tpl/app/ent/facitemplate/newGateway.html',
              })
              .state('app.enterprise.facitemplate.newFac', {
                  url: '/newFacitemplate',
                  templateUrl: 'tpl/app/ent/facitemplate/newFacitemplate.html',
              })
              .state('app.enterprise.facitemplate.newjobpoint', {
                  url: '/newjobpoint/{uuid}',
                  templateUrl: 'tpl/app/ent/facitemplate/newjobPoint.html',
              })
              .state('app.enterprise.facitemplate.edit.jobpointcontent', {
                url: '/jobpoint/{code}',

                views:{
                  "jobpointView": { templateUrl: 'tpl/app/ent/facitemplate/jobpointContent.html' }
                }
                  /*controller:"FaciTemplateJobPointController",
                  // resolve: {
                  //     getFcJobPointData :function(hemaAPI){
                  //         return hemaAPI.getFacilityTemplateByUuid({uuid:$stateParams.templateUuid});
                  //
                  //     }
                  //
                  // },
                  parmas:{

                  }*/
              })
              .state('app.enterprise.facitemplate.edit.pickareacontent', {
                url: '/pickarea/{code}/{optionDataNodeType}/{optionDataUuid}',
                views:{
                  "jobpointView": { templateUrl: 'tpl/app/ent/facitemplate/pickareaContent.html' }
                }
                 /* controller: "FaciTemplatePickAreaController",
                  resolve: {
                      getFcPickAreaData :function(hemaAPI){
                          return hemaAPI.getFacilityTemplateByUuid({uuid:$stateParams.jobPointUuid});

                      }

                  }*/
              })
              .state('app.enterprise.facitemplate.edit.sectioncontent', {
                url: '/section/{code}',
                views:{
                  "jobpointView": { templateUrl: 'tpl/app/ent/facitemplate/sectionContent.html' }
                }
                 /* controller: "FaciTemplateSectionController",
                  resolve: {
                      getFcSectionData :function(hemaAPI){
                          return hemaAPI.getFacilityTemplateByUuid({uuid:$stateParams.pickAreaUuid});

                      }

                  }*/
              })

              .state('app.enterprise.pickschemetemplate', {
                abstract: true,
                url: '/pickschemetemplate',
                template: '<div ui-view class="fade-in-up"></div>',
                resolve: {
                  deps: ['$ocLazyLoad',
                    function($ocLazyLoad) {
                      return $ocLazyLoad.load(['app/pickschemetemplate/pickschemetemplate.controller.js']);
                    }
                  ]
                }
              })
              .state('app.enterprise.pickschemetemplate.list', {
                url: '/list',
                templateUrl: 'tpl/app/ent/pickschemetemplate/list.html',

              })
              .state('app.enterprise.pickschemetemplate.edit', {
                url: '/edit/{uuid}',
                templateUrl: 'tpl/app/ent/pickschemetemplate/edit.html',
                  //controller:'PickSchemeTemplateEditController'

              })

              .state('app.enterprise.query', {
                  abstract: true,
                  url: '/query',
                  template: '<div ui-view class="fade-in-up"></div>'
              })
              .state('app.enterprise.query.labelstatus', {
                  url: '/labelstatus',
                  templateUrl: 'tpl/app/ent/query/labelstatus.html',
                  resolve: {
                      deps: ['$ocLazyLoad',
                          function($ocLazyLoad) {
                            return $ocLazyLoad.load(['app/query/labelstatus.controller.js']);
                          }
                      ]
                  }
              })
              .state('app.enterprise.query.orderquery', {
                  url: '/orderquery',
                  templateUrl: 'tpl/app/ent/query/orderquery.html',
                  resolve: {
                      deps: ['$ocLazyLoad',
                          function($ocLazyLoad) {
                            return $ocLazyLoad.load(['app/query/orderquery.controller.js']);
                          }
                      ]
                  }
              })



              .state('app.store.goods', {
                  abstract: true,
                  url: '/goods',
                  template: '<div ui-view class="fade-in-up"></div>',
                  resolve: {
                      deps: ['$ocLazyLoad',
                          function($ocLazyLoad) {
                            return $ocLazyLoad.load(['app/goods/goods.controller.js']);
                          }
                      ]
                  }
              })
              .state('app.store.goods.list', {
                url: '/list',
                templateUrl: 'tpl/app/sto/goods/list.html'
              })
              .state('app.store.goods.edit', {
                url: '/edit/{uuid}',
                templateUrl: 'tpl/app/sto/goods/edit.html'
              })


              .state('app.store.picker', {
                  abstract: true,
                  url: '/picker',
                  template: '<div ui-view class="fade-in-up"></div>'
              })
              .state('app.store.picker.list', {
                url: '/list',
                templateUrl: 'tpl/app/sto/picker/list.html'
              })

              .state('app.store.computer', {
                  abstract: true,
                  url: '/computer',
                  template: '<div ui-view class="fade-in-up"></div>',
                  resolve: {
                      deps: ['$ocLazyLoad',
                          function($ocLazyLoad) {
                            return $ocLazyLoad.load(['app/computer/computer.controller.js']);
                          }
                      ]
                  }
              })
              .state('app.store.computer.list', {
                  url: '/list',
                  templateUrl: 'tpl/app/sto/computer/list.html'
              })
              .state('app.store.computer.edit', {
                  url: '/edit/{uuid}',
                  templateUrl: 'tpl/app/sto/computer/edit.html'
              })

              .state('app.store.genfromtemplate', {
                  url: '/gft',
                  templateUrl: 'tpl/app/sto/genfromtemplate/list.html',
                  //resolve: {
                  //    deps: ['$ocLazyLoad',
                  //        function($ocLazyLoad) {
                  //          return $ocLazyLoad.load(['app/genfromtemplate/genfromtemplate.controller.js']);
                  //        }
                  //    ]
                  //}
              })

              .state('app.store.label', {
                  url: '/label',
                  templateUrl: 'tpl/app/sto/label/list.html',
                  resolve: {
                      deps: ['$ocLazyLoad',
                          function($ocLazyLoad) {
                            return $ocLazyLoad.load(['app/label/label.controller.js']);
                          }
                      ]
                  }
              })
              .state('app.store.newgateway', {
                  url: '/newgateway',
                  templateUrl: 'tpl/app/sto/label/newStoGatewayContent.html',
                  controller: "LabelController",
                  //hmstore-web/src/main/webapp/tpl/app/sto/label/newstogatewaycontent.html

              })
              .state('app.store.label.gatewaycontent', {
                  url: '/gatewaycontent',
                  views:{
                      "gatewayView": { templateUrl: 'tpl/app/sto/label/stoGatewayContent.html' }
                  }
              })


              .state('app.store.bin', {
                  url: '/bin',
                  templateUrl: 'tpl/app/sto/bin/list.html',
                  resolve: {
                      deps: ['$ocLazyLoad',
                          function($ocLazyLoad) {
                            return $ocLazyLoad.load(['app/bin/bin.controller.js']);
                          }
                      ]
                  }
              })
              .state('app.store.newstojobpoint', {
                  url: '/newstojobpoint',
                  templateUrl: 'tpl/app/sto/bin/newStoJobPointContent.html'
              })
              .state('app.store.bin.jobpointcontent', {
                url: '/jobpoint',
                views:{
                  "jobpointView": { templateUrl: 'tpl/app/sto/bin/stoJobPointContent.html' }
                }
              })
              .state('app.store.bin.pickareacontent', {
                url: '/pickarea',
                views:{
                  "jobpointView": { templateUrl: 'tpl/app/sto/bin/stoPickAreaContent.html' }
                }
              })
              .state('app.store.bin.sectioncontent', {
                url: '/section',
                views:{
                  "jobpointView": { templateUrl: 'tpl/app/sto/bin/stoSectionContent.html' }
                }
              })

              .state('app.store.pickscheme', {
                abstract: true,
                url: '/pickscheme',
                template: '<div ui-view class="fade-in-up"></div>',
                resolve: {
                  deps: ['$ocLazyLoad',
                    function($ocLazyLoad) {
                      return $ocLazyLoad.load(['app/pickscheme/pickscheme.controller.js']);
                    }
                  ]
                }
              })
              .state('app.store.pickscheme.list', {
                url: '/list',
                templateUrl: 'tpl/app/sto/pickscheme/list.html'
              })
              .state('app.store.pickscheme.edit', {
                url: '/edit/{uuid}/{code}/{name}',
                templateUrl: 'tpl/app/sto/pickscheme/edit.html'
              })

              .state('app.store.query', {
                  abstract: true,
                  url: '/query',
                  template: '<div ui-view class="fade-in-up"></div>'
              })
              .state('app.store.query.labelstatus', {
                  url: '/labelstatus',
                  templateUrl: 'tpl/app/sto/query/labelstatus.html',
                  resolve: {
                      deps: ['$ocLazyLoad',
                          function($ocLazyLoad) {
                            return $ocLazyLoad.load(['app/query/labelstatus.controller.js']);
                          }
                      ]
                  }
              })
              .state('app.store.query.orderquery', {
                  url: '/orderquery',
                  templateUrl: 'tpl/app/sto/query/orderquery.html',
                  resolve: {
                      deps: ['$ocLazyLoad',
                          function($ocLazyLoad) {
                            return $ocLazyLoad.load(['app/query/orderquery.controller.js']);
                          }
                      ]
                  }
              })





              .state('app.dashboard-v1', {
                  url: '/dashboard-v1',
                  templateUrl: 'tpl/app_dashboard_v1.html',
                  resolve: {
                    deps: ['$ocLazyLoad',
                      function( $ocLazyLoad ){
                        return $ocLazyLoad.load(['js/controllers/chart.js']);
                    }]
                  }
              })
              .state('app.dashboard-v2', {
                  url: '/dashboard-v2',
                  templateUrl: 'tpl/app_dashboard_v2.html',
                  resolve: {
                    deps: ['$ocLazyLoad',
                      function( $ocLazyLoad ){
                        return $ocLazyLoad.load(['js/controllers/chart.js']);
                    }]
                  }
              })
              .state('app.ui', {
                  url: '/ui',
                  template: '<div ui-view class="fade-in-up"></div>'
              })
              .state('app.ui.buttons', {
                  url: '/buttons',
                  templateUrl: 'tpl/ui_buttons.html'
              })
              .state('app.ui.icons', {
                  url: '/icons',
                  templateUrl: 'tpl/ui_icons.html'
              })
              .state('app.ui.grid', {
                  url: '/grid',
                  templateUrl: 'tpl/ui_grid.html'
              })
              .state('app.ui.widgets', {
                  url: '/widgets',
                  templateUrl: 'tpl/ui_widgets.html'
              })
              .state('app.ui.bootstrap', {
                  url: '/bootstrap',
                  templateUrl: 'tpl/ui_bootstrap.html'
              })
              .state('app.ui.sortable', {
                  url: '/sortable',
                  templateUrl: 'tpl/ui_sortable.html'
              })
              .state('app.ui.portlet', {
                  url: '/portlet',
                  templateUrl: 'tpl/ui_portlet.html'
              })
              .state('app.ui.timeline', {
                  url: '/timeline',
                  templateUrl: 'tpl/ui_timeline.html'
              })
              .state('app.ui.tree', {
                  url: '/tree',
                  templateUrl: 'tpl/ui_tree.html',
                  resolve: {
                      deps: ['$ocLazyLoad',
                        function( $ocLazyLoad ){
                          return $ocLazyLoad.load('angularBootstrapNavTree').then(
                              function(){
                                 return $ocLazyLoad.load('js/controllers/tree.js');
                              }
                          );
                        }
                      ]
                  }
              })
              .state('app.ui.toaster', {
                  url: '/toaster',
                  templateUrl: 'tpl/ui_toaster.html',
                  resolve: {
                      deps: ['$ocLazyLoad',
                        function( $ocLazyLoad){
                          return $ocLazyLoad.load('toaster').then(
                              function(){
                                 return $ocLazyLoad.load('js/controllers/toaster.js');
                              }
                          );
                      }]
                  }
              })
              .state('app.ui.jvectormap', {
                  url: '/jvectormap',
                  templateUrl: 'tpl/ui_jvectormap.html',
                  resolve: {
                      deps: ['$ocLazyLoad',
                        function( $ocLazyLoad){
                          return $ocLazyLoad.load('js/controllers/vectormap.js');
                      }]
                  }
              })
              .state('app.ui.googlemap', {
                  url: '/googlemap',
                  templateUrl: 'tpl/ui_googlemap.html',
                  resolve: {
                      deps: ['uiLoad',
                        function( uiLoad ){
                          return uiLoad.load( [
                            'js/app/map/load-google-maps.js',
                            'js/app/map/ui-map.js',
                            'js/app/map/map.js'] ).then(
                              function(){
                                return loadGoogleMaps();
                              }
                            );
                      }]
                  }
              })
              .state('app.chart', {
                  url: '/chart',
                  templateUrl: 'tpl/ui_chart.html',
                  resolve: {
                      deps: ['uiLoad',
                        function( uiLoad){
                          return uiLoad.load('js/controllers/chart.js');
                      }]
                  }
              })
              // table
              .state('app.table', {
                  url: '/table',
                  template: '<div ui-view></div>'
              })
              .state('app.table.static', {
                  url: '/static',
                  templateUrl: 'tpl/table_static.html'
              })
              .state('app.table.datatable', {
                  url: '/datatable',
                  templateUrl: 'tpl/table_datatable.html'
              })
              .state('app.table.footable', {
                  url: '/footable',
                  templateUrl: 'tpl/table_footable.html'
              })
              .state('app.table.grid', {
                  url: '/grid',
                  templateUrl: 'tpl/table_grid.html',
                  resolve: {
                      deps: ['$ocLazyLoad',
                        function( $ocLazyLoad ){
                          return $ocLazyLoad.load('ngGrid').then(
                              function(){
                                  return $ocLazyLoad.load('js/controllers/grid.js');
                              }
                          );
                      }]
                  }
              })
              // form
              .state('app.form', {
                  url: '/form',
                  template: '<div ui-view class="fade-in"></div>',
                  resolve: {
                      deps: ['uiLoad',
                        function( uiLoad){
                          return uiLoad.load('js/controllers/form.js');
                      }]
                  }
              })
              .state('app.form.elements', {
                  url: '/elements',
                  templateUrl: 'tpl/form_elements.html'
              })
              .state('app.form.validation', {
                  url: '/validation',
                  templateUrl: 'tpl/form_validation.html'
              })
              .state('app.form.wizard', {
                  url: '/wizard',
                  templateUrl: 'tpl/form_wizard.html'
              })
              .state('app.form.fileupload', {
                  url: '/fileupload',
                  templateUrl: 'tpl/form_fileupload.html',
                  resolve: {
                      deps: ['$ocLazyLoad',
                        function( $ocLazyLoad){
                          return $ocLazyLoad.load('angularFileUpload').then(
                              function(){
                                 return $ocLazyLoad.load('js/controllers/file-upload.js');
                              }
                          );
                      }]
                  }
              })
              .state('app.form.imagecrop', {
                  url: '/imagecrop',
                  templateUrl: 'tpl/form_imagecrop.html',
                  resolve: {
                      deps: ['$ocLazyLoad',
                        function( $ocLazyLoad){
                          return $ocLazyLoad.load('ngImgCrop').then(
                              function(){
                                 return $ocLazyLoad.load('js/controllers/imgcrop.js');
                              }
                          );
                      }]
                  }
              })
              .state('app.form.select', {
                  url: '/select',
                  templateUrl: 'tpl/form_select.html',
                  controller: 'SelectCtrl',
                  resolve: {
                      deps: ['$ocLazyLoad',
                        function( $ocLazyLoad ){
                          return $ocLazyLoad.load('ui.select').then(
                              function(){
                                  return $ocLazyLoad.load('js/controllers/select.js');
                              }
                          );
                      }]
                  }
              })
              .state('app.form.slider', {
                  url: '/slider',
                  templateUrl: 'tpl/form_slider.html',
                  controller: 'SliderCtrl',
                  resolve: {
                      deps: ['$ocLazyLoad',
                        function( $ocLazyLoad ){
                          return $ocLazyLoad.load('vr.directives.slider').then(
                              function(){
                                  return $ocLazyLoad.load('js/controllers/slider.js');
                              }
                          );
                      }]
                  }
              })
              .state('app.form.editor', {
                  url: '/editor',
                  templateUrl: 'tpl/form_editor.html',
                  controller: 'EditorCtrl',
                  resolve: {
                      deps: ['$ocLazyLoad',
                        function( $ocLazyLoad ){
                          return $ocLazyLoad.load('textAngular').then(
                              function(){
                                  return $ocLazyLoad.load('js/controllers/editor.js');
                              }
                          );
                      }]
                  }
              })
              // pages
              .state('app.page', {
                  url: '/page',
                  template: '<div ui-view class="fade-in-down"></div>'
              })
              .state('app.page.profile', {
                  url: '/profile',
                  templateUrl: 'tpl/page_profile.html'
              })
              .state('app.page.post', {
                  url: '/post',
                  templateUrl: 'tpl/page_post.html'
              })
              .state('app.page.search', {
                  url: '/search',
                  templateUrl: 'tpl/page_search.html'
              })
              .state('app.page.invoice', {
                  url: '/invoice',
                  templateUrl: 'tpl/page_invoice.html'
              })
              .state('app.page.price', {
                  url: '/price',
                  templateUrl: 'tpl/page_price.html'
              })
              .state('app.docs', {
                  url: '/docs',
                  templateUrl: 'tpl/docs.html'
              })
              // others
              .state('lockme', {
                  url: '/lockme',
                  templateUrl: 'tpl/page_lockme.html'
              })
              .state('access', {
                  url: '/access',
                  template: '<div ui-view class="fade-in-right-big smooth"></div>'
              })
              .state('access.signin', {
                  url: '/signin',
                  templateUrl: 'tpl/page_signin.html',
                  resolve: {
                      deps: ['uiLoad',
                        function( uiLoad ){
                          return uiLoad.load( ['js/controllers/signin.js'] );
                      }]
                  }
              })
              .state('access.signup', {
                  url: '/signup',
                  templateUrl: 'tpl/page_signup.html',
                  resolve: {
                      deps: ['uiLoad',
                        function( uiLoad ){
                          return uiLoad.load( ['js/controllers/signup.js'] );
                      }]
                  }
              })
              .state('access.forgotpwd', {
                  url: '/forgotpwd',
                  templateUrl: 'tpl/page_forgotpwd.html'
              })
              .state('access.404', {
                  url: '/404',
                  templateUrl: 'tpl/page_404.html'
              })

              // fullCalendar
              .state('app.calendar', {
                  url: '/calendar',
                  templateUrl: 'tpl/app_calendar.html',
                  // use resolve to load other dependences
                  resolve: {
                      deps: ['$ocLazyLoad', 'uiLoad',
                        function( $ocLazyLoad, uiLoad ){
                          return uiLoad.load(
                            ['vendor/jquery/fullcalendar/fullcalendar.css',
                              'vendor/jquery/fullcalendar/theme.css',
                              'vendor/jquery/jquery-ui-1.10.3.custom.min.js',
                              'vendor/libs/moment.min.js',
                              'vendor/jquery/fullcalendar/fullcalendar.min.js',
                              'js/app/calendar/calendar.js']
                          ).then(
                            function(){
                              return $ocLazyLoad.load('ui.calendar');
                            }
                          )
                      }]
                  }
              })

              // mail
              .state('app.mail', {
                  abstract: true,
                  url: '/mail',
                  templateUrl: 'tpl/mail.html',
                  // use resolve to load other dependences
                  resolve: {
                      deps: ['uiLoad',
                        function( uiLoad ){
                          return uiLoad.load( ['js/app/mail/mail.js',
                                               'js/app/mail/mail-service.js',
                                               'vendor/libs/moment.min.js'] );
                      }]
                  }
              })
              .state('app.mail.list', {
                  url: '/inbox/{fold}',
                  templateUrl: 'tpl/mail.list.html'
              })
              .state('app.mail.detail', {
                  url: '/{mailId:[0-9]{1,4}}',
                  templateUrl: 'tpl/mail.detail.html'
              })
              .state('app.mail.compose', {
                  url: '/compose',
                  templateUrl: 'tpl/mail.new.html'
              })

              .state('layout', {
                  abstract: true,
                  url: '/layout',
                  templateUrl: 'tpl/layout.html'
              })
              .state('layout.fullwidth', {
                  url: '/fullwidth',
                  views: {
                      '': {
                          templateUrl: 'tpl/layout_fullwidth.html'
                      },
                      'footer': {
                          templateUrl: 'tpl/layout_footer_fullwidth.html'
                      }
                  },
                  resolve: {
                      deps: ['uiLoad',
                        function( uiLoad ){
                          return uiLoad.load( ['js/controllers/vectormap.js'] );
                      }]
                  }
              })
              .state('layout.mobile', {
                  url: '/mobile',
                  views: {
                      '': {
                          templateUrl: 'tpl/layout_mobile.html'
                      },
                      'footer': {
                          templateUrl: 'tpl/layout_footer_mobile.html'
                      }
                  }
              })
              .state('layout.app', {
                  url: '/app',
                  views: {
                      '': {
                          templateUrl: 'tpl/layout_app.html'
                      },
                      'footer': {
                          templateUrl: 'tpl/layout_footer_fullwidth.html'
                      }
                  },
                  resolve: {
                      deps: ['uiLoad',
                        function( uiLoad ){
                          return uiLoad.load( ['js/controllers/tab.js'] );
                      }]
                  }
              })
              .state('apps', {
                  abstract: true,
                  url: '/apps',
                  templateUrl: 'tpl/layout.html'
              })
              .state('apps.note', {
                  url: '/note',
                  templateUrl: 'tpl/apps_note.html',
                  resolve: {
                      deps: ['uiLoad',
                        function( uiLoad ){
                          return uiLoad.load( ['js/app/note/note.js',
                                               'vendor/libs/moment.min.js'] );
                      }]
                  }
              })
              .state('apps.contact', {
                  url: '/contact',
                  templateUrl: 'tpl/apps_contact.html',
                  resolve: {
                      deps: ['uiLoad',
                        function( uiLoad ){
                          return uiLoad.load( ['js/app/contact/contact.js'] );
                      }]
                  }
              })
              .state('app.weather', {
                  url: '/weather',
                  templateUrl: 'tpl/apps_weather.html',
                  resolve: {
                      deps: ['$ocLazyLoad',
                        function( $ocLazyLoad ){
                          return $ocLazyLoad.load(
                              {
                                  name: 'angular-skycons',
                                  files: ['js/app/weather/skycons.js',
                                          'vendor/libs/moment.min.js',
                                          'js/app/weather/angular-skycons.js',
                                          'js/app/weather/ctrl.js' ]
                              }
                          );
                      }]
                  }
              })
              .state('music', {
                  url: '/music',
                  templateUrl: 'tpl/music.html',
                  controller: 'MusicCtrl',
                  resolve: {
                      deps: ['$ocLazyLoad',
                        function( $ocLazyLoad ){
                          return $ocLazyLoad.load([
                            'com.2fdevs.videogular',
                            'com.2fdevs.videogular.plugins.controls',
                            'com.2fdevs.videogular.plugins.overlayplay',
                            'com.2fdevs.videogular.plugins.poster',
                            'com.2fdevs.videogular.plugins.buffering',
                            'js/app/music/ctrl.js',
                            'js/app/music/theme.css'
                          ]);
                      }]
                  }
              })
                .state('music.home', {
                    url: '/home',
                    templateUrl: 'tpl/music.home.html'
                })
                .state('music.genres', {
                    url: '/genres',
                    templateUrl: 'tpl/music.genres.html'
                })
                .state('music.detail', {
                    url: '/detail',
                    templateUrl: 'tpl/music.detail.html'
                })
                .state('music.mtv', {
                    url: '/mtv',
                    templateUrl: 'tpl/music.mtv.html'
                })
                .state('music.mtvdetail', {
                    url: '/mtvdetail',
                    templateUrl: 'tpl/music.mtv.detail.html'
                })
                .state('music.playlist', {
                    url: '/playlist/{fold}',
                    templateUrl: 'tpl/music.playlist.html'
                })
      }
    ]
  );
