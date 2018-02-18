-- 企业
INSERT INTO `t_sys_enterprise` VALUES ('hemaUuid', 'HM', '盒马', '盒马', NULL, NULL, 0, NULL, '2016-7-11 10:52:48', 'admin', '2016-7-11 10:52:56', 'admin');

-- 菜单
INSERT INTO `t_sys_function` VALUES ('F100e', '总部功能', 1, 0, '', '', 'F0', 1, 1);
INSERT INTO `t_sys_function` VALUES ('F100s', '门店功能', 1, 0, '', '', 'F0', 1, 2);
INSERT INTO `t_sys_function` VALUES ('Fe201', '系统', 2, 0, '', 'glyphicon glyphicon-stats icon text-primary-dker', 'F100e', 1, 1);
INSERT INTO `t_sys_function` VALUES ('Fe202', '资料', 2, 0, '', 'glyphicon glyphicon-edit icon text-info-dker', 'F100e', 1, 1);
INSERT INTO `t_sys_function` VALUES ('Fe203', '查询', 2, 0, '', 'glyphicon glyphicon-th-large icon text-success', 'F100e', 1, 1);
INSERT INTO `t_sys_function` VALUES ('Fea301', '员工', 3, 0, 'app.enterprise.employee.list', '', 'Fe201', 1, 1);
INSERT INTO `t_sys_function` VALUES ('Fea302', '角色', 3, 0, 'app.enterprise.role.list', '', 'Fe201', 1, 1);
INSERT INTO `t_sys_function` VALUES ('Feb301', '门店', 3, 0, 'app.enterprise.store.list', '', 'Fe202', 1, 1);
INSERT INTO `t_sys_function` VALUES ('Feb302', '商品', 3, 0, 'app.store.goods.list', '', 'Fe202', 1, 1);
INSERT INTO `t_sys_function` VALUES ('Feb303', '设施模板', 3, 0, 'app.enterprise.facitemplate.list', '', 'Fe202', 1, 1);
INSERT INTO `t_sys_function` VALUES ('Feb304', '分拣方案模板', 3, 0, 'app.enterprise.pickschemetemplate.list', '', 'Fe202', 1, 1);
INSERT INTO `t_sys_function` VALUES ('Fec301', '门店标签状态', 3, 0, 'app.enterprise.query.labelstatus', '', 'Fe203', 1, 1);
INSERT INTO `t_sys_function` VALUES ('Fec302', '门店拣货情况查询', 3, 0, 'app.enterprise.query.orderquery', '', 'Fe203', 1, 1);
INSERT INTO `t_sys_function` VALUES ('Fs201', '资料', 2, 0, '', 'glyphicon glyphicon-edit icon text-info-dker', 'F100s', 1, 2);
INSERT INTO `t_sys_function` VALUES ('Fs202', '设施', 2, 0, '', 'glyphicon glyphicon-stats icon text-primary-dker', 'F100s', 1, 2);
INSERT INTO `t_sys_function` VALUES ('Fs203', '查询', 2, 0, '', 'glyphicon glyphicon-th-large icon text-success', 'F100s', 1, 2);
INSERT INTO `t_sys_function` VALUES ('Fsa301', '商品', 3, 0, 'app.store.goods.list', '', 'Fs201', 1, 2);
INSERT INTO `t_sys_function` VALUES ('Fsa302', '拣货员', 3, 0, 'app.store.picker.list', '', 'Fs201', 1, 2);
INSERT INTO `t_sys_function` VALUES ('Fsa303', '门店主机', 3, 0, 'app.store.computer.list', '', 'Fs201', 1, 2);
INSERT INTO `t_sys_function` VALUES ('Fsb301', '从模板生成设施', 3, 0, 'app.store.genfromtemplate', '', 'Fs202', 1, 2);
INSERT INTO `t_sys_function` VALUES ('Fsb302', '货位', 3, 0, 'app.store.bin', '', 'Fs202', 1, 2);
INSERT INTO `t_sys_function` VALUES ('Fsb303', '电子标签', 3, 0, 'app.store.label', '', 'Fs202', 1, 2);
INSERT INTO `t_sys_function` VALUES ('Fsb304', '分拣方案', 3, 0, 'app.store.pickscheme.list', '', 'Fs202', 1, 2);
INSERT INTO `t_sys_function` VALUES ('Fsc301', '门店标签状态', 3, 0, 'app.store.query.labelstatus', '', 'Fs203', 1, 2);
INSERT INTO `t_sys_function` VALUES ('Fsc302', '门店拣货情况查询', 3, 0, 'app.store.query.orderquery', '', 'Fs203', 1, 2);

-- 超级权限
INSERT INTO `t_sys_role` VALUES ('aefc4610bd4b4af78ef07da1665d4efa', 'admin', '超级管理员', 1, 'hemaUuid', '2016-8-4 10:58:26', 'HM', '2016-8-4 11:09:50', 'HM');
INSERT INTO `t_sys_rolefunction` VALUES ('01a805691d0046a890144142a6e32d5b', 'aefc4610bd4b4af78ef07da1665d4efa', 'Fea301');
INSERT INTO `t_sys_rolefunction` VALUES ('02d4a6888b99463e96409268dabbbf07', 'aefc4610bd4b4af78ef07da1665d4efa', 'Feb301');
INSERT INTO `t_sys_rolefunction` VALUES ('1af9ec29505d4b859d8f6cf01b75713e', 'aefc4610bd4b4af78ef07da1665d4efa', 'Fsb304');
INSERT INTO `t_sys_rolefunction` VALUES ('2cba645da5b74d71becb026738a43c15', 'aefc4610bd4b4af78ef07da1665d4efa', 'Fs201');
INSERT INTO `t_sys_rolefunction` VALUES ('2f1cebb1fa624088a3d998f9953ac5d9', 'aefc4610bd4b4af78ef07da1665d4efa', 'F100s');
INSERT INTO `t_sys_rolefunction` VALUES ('3dab9088e32943159370a809635057d8', 'aefc4610bd4b4af78ef07da1665d4efa', 'Fsa302');
INSERT INTO `t_sys_rolefunction` VALUES ('47d8ff57ea314af59a86510b27c0d9e1', 'aefc4610bd4b4af78ef07da1665d4efa', 'Feb303');
INSERT INTO `t_sys_rolefunction` VALUES ('4b494f5b18ce4f968ab54b890b107eb4', 'aefc4610bd4b4af78ef07da1665d4efa', 'Fsa301');
INSERT INTO `t_sys_rolefunction` VALUES ('628ac2dd7b4b4f38854147d383f8245b', 'aefc4610bd4b4af78ef07da1665d4efa', 'Fsc301');
INSERT INTO `t_sys_rolefunction` VALUES ('88f0350e82ba47f286a1fc39866fc904', 'aefc4610bd4b4af78ef07da1665d4efa', 'Fe203');
INSERT INTO `t_sys_rolefunction` VALUES ('898a96ede1194bec87474a32fdfe63cd', 'aefc4610bd4b4af78ef07da1665d4efa', 'Fe202');
INSERT INTO `t_sys_rolefunction` VALUES ('a044819c8d3d4ec3afcb27c780b22214', 'aefc4610bd4b4af78ef07da1665d4efa', 'Fe201');
INSERT INTO `t_sys_rolefunction` VALUES ('a52a4f853965427d9cc0e28a5850962a', 'aefc4610bd4b4af78ef07da1665d4efa', 'Fea302');
INSERT INTO `t_sys_rolefunction` VALUES ('a81c8e1df95f454581508981d154bc60', 'aefc4610bd4b4af78ef07da1665d4efa', 'Fsa303');
INSERT INTO `t_sys_rolefunction` VALUES ('a994c2b87f8c4cb884a2e8ee12dbad32', 'aefc4610bd4b4af78ef07da1665d4efa', 'Fs203');
INSERT INTO `t_sys_rolefunction` VALUES ('aa89ce67844741f9946972521e91ae09', 'aefc4610bd4b4af78ef07da1665d4efa', 'Feb304');
INSERT INTO `t_sys_rolefunction` VALUES ('ad949da97b1e48b0998088160fa7a20f', 'aefc4610bd4b4af78ef07da1665d4efa', 'Fsc302');
INSERT INTO `t_sys_rolefunction` VALUES ('c3f9a64d859f4b94ab248ac0fa9aa26b', 'aefc4610bd4b4af78ef07da1665d4efa', 'Fec302');
INSERT INTO `t_sys_rolefunction` VALUES ('cadcb2c489dc4a7da0beffe98a58674c', 'aefc4610bd4b4af78ef07da1665d4efa', 'Fec301');
INSERT INTO `t_sys_rolefunction` VALUES ('cb84f02544ef44ac856ccbf2bb216a50', 'aefc4610bd4b4af78ef07da1665d4efa', 'Feb302');
INSERT INTO `t_sys_rolefunction` VALUES ('d431bdf4d2fb4c939da438168e8a1040', 'aefc4610bd4b4af78ef07da1665d4efa', 'F100e');
INSERT INTO `t_sys_rolefunction` VALUES ('f1976cf622504b169e23676c8dac5cad', 'aefc4610bd4b4af78ef07da1665d4efa', 'Fsb303');
INSERT INTO `t_sys_rolefunction` VALUES ('f1f7155a441640a8b76a9958b4bbe4a3', 'aefc4610bd4b4af78ef07da1665d4efa', 'Fsb302');
INSERT INTO `t_sys_rolefunction` VALUES ('f31db153d3ac4feab66e981ed1782360', 'aefc4610bd4b4af78ef07da1665d4efa', 'Fs202');
INSERT INTO `t_sys_rolefunction` VALUES ('fd2deff1f3d14c61b1650775fcea76ea', 'aefc4610bd4b4af78ef07da1665d4efa', 'Fsb301');

-- 超级管理员
INSERT INTO `t_sys_user` VALUES ('9b5a2eb09da34b4fadcdae0d32df2b1b', 'admin', '超级管理员', 'admin', 1, '15858588888', NULL, NULL, NULL, '2016-8-4 10:57:33', 'HM', '2016-8-4 11:10:01', 'HM', 'hemaUuid');
INSERT INTO `t_sys_userrole` VALUES ('09fd10e4802e4c65911c06462e5c820f', '9b5a2eb09da34b4fadcdae0d32df2b1b', 'aefc4610bd4b4af78ef07da1665d4efa');
