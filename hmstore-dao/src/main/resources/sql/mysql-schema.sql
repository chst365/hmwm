-- 商品
CREATE TABLE t_article (
  uuid varchar(32) NOT NULL,
  code varchar(20) NOT NULL,
  name varchar(80) NOT NULL,
  categoryCode varchar(13) DEFAULT NULL,
  categoryName varchar(80) DEFAULT NULL,
  PRIMARY KEY (uuid)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
-- 门店
CREATE TABLE t_store (
  uuid varchar(32) NOT NULL,
  code varchar(30) NOT NULL,
  name varchar(30) NOT NULL,
  address varchar(255) DEFAULT NULL,
  remark varchar(255) DEFAULT NULL,
  PRIMARY KEY (uuid)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
-- 门店主机
CREATE TABLE t_storehost (
  uuid varchar(32) NOT NULL,
  name varchar(30) NOT NULL,
  ip varchar(15) NOT NULL,
  macAddress varchar(17) NOT NULL,
  allowAccess varchar(5) NOT NULL,
  storeUuid varchar(32) NOT NULL,
  PRIMARY KEY (uuid)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
-- 设施模板
CREATE TABLE t_facilitytemplate (
  uuid varchar(32) NOT NULL,
  code varchar(30) NOT NULL,
  name varchar(30) NOT NULL,
  PRIMARY KEY (uuid)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
-- 网关
CREATE TABLE t_gateway (
  uuid varchar(32) NOT NULL,
  code varchar(10) NOT NULL,
  ip varchar(15) NOT NULL,
  port varchar(5) NOT NULL,
  templateUuid varchar(32) NULL, --总部功能字段
  orgUuid varchar(32) NOT NULL,
  PRIMARY KEY (uuid)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
-- 电子标签
CREATE TABLE t_electronictag (
  uuid varchar(32) NOT NULL,
  nodeCode varchar(10) NOT NULL,
  nodeAddress varchar(10) NOT NULL,
  nodeType varchar(20) NOT NULL,
  nodeUsage varchar(20) NOT NULL,
  nodeState varchar(20) NOT NULL,
  gatewayUuid varchar(32) NOT NULL,
  checktime datetime,
  remark varchar(255),
  PRIMARY KEY (uuid)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
-- 作业点
CREATE TABLE t_jobpoint (
  uuid varchar(32) NOT NULL,
  code varchar(10) NOT NULL,
  name varchar(20) NOT NULL,
  templateUuid varchar(32) NULL, --总部功能字段
  orgUuid varchar(32) NOT NULL,
  PRIMARY KEY (uuid)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
-- 作业点网关
CREATE TABLE t_jobpointgateway (
  uuid varchar(32) NOT NULL,
  jobpointUuid varchar(32) NOT NULL,
  gatewayUuid varchar(32) NOT NULL,
  PRIMARY KEY (uuid)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
-- 拣货分区
CREATE TABLE t_pickarea (
  uuid varchar(32) NOT NULL,
  code varchar(10) NOT NULL,
  name varchar(20) NOT NULL,
  jobPointUuid varchar(32) NOT NULL,
  PRIMARY KEY (uuid)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
-- 区段
CREATE TABLE t_section (
  uuid varchar(32) NOT NULL,
  code varchar(10) NOT NULL,
  name varchar(20) NOT NULL,
  pickAreaUuid varchar(32) NOT NULL,
  PRIMARY KEY (uuid)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
-- 区段货位
CREATE TABLE t_bineletag (
  uuid varchar(32) NOT NULL,
  binCode varchar(10) NOT NULL,
  nodeCode varchar(10) NOT NULL,
  nodeAddress varchar(10) NOT NULL,
  nodeType varchar(20) NOT NULL,
  nodeUsage varchar(20) NOT NULL,
  sectionUuid varchar(32) NOT NULL,
  PRIMARY KEY (uuid)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
-- 区段节点
CREATE TABLE t_sectioneletag (
  uuid varchar(32) NOT NULL,
  nodeCode varchar(10) NOT NULL,
  nodeAddress varchar(10) NOT NULL,
  nodeType varchar(20) NOT NULL,
  nodeUsage varchar(20) NOT NULL,
  sectionUuid varchar(32) NOT NULL,
  PRIMARY KEY (uuid)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
-- 补货节点
CREATE TABLE t_rpleletag (
  uuid varchar(32) NOT NULL,
  requestNodeCode varchar(10) NOT NULL,
  requestNodeAddress varchar(10) NOT NULL,
  requestNodeType varchar(20) NOT NULL,
  requestNodeUsage varchar(20) NOT NULL,
  responseNodeCode varchar(10) NOT NULL,
  responseNodeAddress varchar(10) NOT NULL,
  responseNodeType varchar(20) NOT NULL,
  responseNodeUsage varchar(20) NOT NULL,
  sectionUuid varchar(32) NOT NULL,
  PRIMARY KEY (uuid)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
-- 拣货方案/拣货方案模板
CREATE TABLE t_pickscheme (
  uuid varchar(32) NOT NULL,
  code varchar(30) NOT NULL,
  name varchar(30) NOT NULL,
  state varchar(15) NOT NULL,
  version int NOT NULL,
  versionTime datetime NOT NULL,
  orgUuid varchar(32) NOT NULL,
  facilityTemplateUuid varchar(32), --总部功能字段
  facilityTemplateCode varchar(30), --总部功能字段
  facilityTemplateName varchar(30), --总部功能字段
  jobPointUuid varchar(32), --门店功能字段
  jobPointCode varchar(10), --门店功能字段
  jobPointName varchar(20), --门店功能字段
  PRIMARY KEY (uuid)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
-- 拣货方案模板分发门店
CREATE TABLE t_pickschemetemplatestore (
  uuid varchar(32) NOT NULL,
  storeUuid varchar(32) NOT NULL,
  storeCode varchar(30) NOT NULL,
  storeName varchar(30) NOT NULL,
  pickSchemeUuid varchar(32) NOT NULL,
  PRIMARY KEY (uuid)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
-- 拣货方案货位商品
CREATE TABLE t_pickschemebinarticle (
  uuid varchar(32) NOT NULL,
  binCode varchar(10) NOT NULL,
  articleUuid varchar(32) NOT NULL,
  articleCode varchar(20) NOT NULL,
  articleName varchar(80) NOT NULL,
  pickSchemeUuid varchar(32) NOT NULL,
  PRIMARY KEY (uuid)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
