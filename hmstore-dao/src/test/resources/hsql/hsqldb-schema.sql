CREATE TABLE t_article (
  uuid varchar(32) NOT NULL,
  code varchar(20) NOT NULL,
  name varchar(80) NOT NULL,
  categoryCode varchar(13) DEFAULT NULL,
  categoryName varchar(80) DEFAULT NULL,
  CONSTRAINT PK_F_OWNER PRIMARY KEY (uuid)
);

CREATE TABLE t_store (
  uuid varchar(32) NOT NULL,
  code varchar(30) NOT NULL,
  name varchar(30) NOT NULL,
  address varchar(255) DEFAULT NULL,
  remark varchar(255) DEFAULT NULL,
  PRIMARY KEY (uuid)
);

CREATE TABLE t_storehost (
  uuid varchar(32) NOT NULL,
  name varchar(30) NOT NULL,
  ip varchar(15) NOT NULL,
  macAddress varchar(17) NOT NULL,
  allowAccess varchar(5) NOT NULL,
  storeUuid varchar(32) NOT NULL,
  PRIMARY KEY (uuid)
);

CREATE TABLE t_electronictag (
  uuid varchar(32) NOT NULL,
  nodeCode varchar(10) NOT NULL,
  nodeAddress varchar(10) NOT NULL,
  nodeType varchar(20) NOT NULL,
  nodeUsage varchar(20) NOT NULL,
  nodeState varchar(20) NOT NULL,
  gatewayUuid varchar(32) NOT NULL,
  checktime datetime DEFAULT NULL,
  remark varchar(255) DEFAULT NULL,
  PRIMARY KEY (uuid)
);

CREATE TABLE t_gateway (
  uuid varchar(32) NOT NULL,
  code varchar(10) NOT NULL,
  ip varchar(15) NOT NULL,
  port varchar(5) NOT NULL,
  templateUuid varchar(32) DEFAULT NULL,
  orgUuid varchar(32) NOT NULL,
  PRIMARY KEY (uuid)
);

CREATE TABLE t_bineletag (
  uuid varchar(32) NOT NULL,
  binCode varchar(10) NOT NULL,
  nodeCode varchar(10) NOT NULL,
  nodeAddress varchar(10) NOT NULL,
  nodeType varchar(20) NOT NULL,
  nodeUsage varchar(20) NOT NULL,
  sectionUuid varchar(32) NOT NULL,
  PRIMARY KEY (uuid)
);

CREATE TABLE t_jobpoint (
  uuid varchar(32) NOT NULL,
  code varchar(10) NOT NULL,
  name varchar(20) NOT NULL,
  templateUuid varchar(32) DEFAULT NULL,
  orgUuid varchar(32) NOT NULL,
  PRIMARY KEY (uuid)
);

CREATE TABLE t_jobpointgateway (
  uuid varchar(32) NOT NULL,
  jobpointUuid varchar(32) NOT NULL,
  gatewayUuid varchar(32) NOT NULL,
  PRIMARY KEY (uuid)
);

CREATE TABLE t_pickarea (
  uuid varchar(32) NOT NULL,
  code varchar(10) NOT NULL,
  name varchar(20) NOT NULL,
  jobPointUuid varchar(32) NOT NULL,
  PRIMARY KEY (uuid)
);

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
);

CREATE TABLE t_section (
  uuid varchar(32) NOT NULL,
  code varchar(10) NOT NULL,
  name varchar(20) NOT NULL,
  pickAreaUuid varchar(32) NOT NULL,
  PRIMARY KEY (uuid)
);

CREATE TABLE t_sectioneletag (
  uuid varchar(32) NOT NULL,
  nodeCode varchar(10) NOT NULL,
  nodeAddress varchar(10) NOT NULL,
  nodeType varchar(20) NOT NULL,
  nodeUsage varchar(20) NOT NULL,
  sectionUuid varchar(32) NOT NULL,
  PRIMARY KEY (uuid)
);

CREATE TABLE t_pickschemebinarticle (
  uuid varchar(32) NOT NULL,
  binCode varchar(10) NOT NULL,
  articleUuid varchar(32) NOT NULL,
  articleCode varchar(20) NOT NULL,
  articleName varchar(80) NOT NULL,
  pickSchemeUuid varchar(32) NOT NULL,
  PRIMARY KEY (uuid)
);

CREATE TABLE t_pickscheme (
  uuid varchar(32) NOT NULL,
  code varchar(30) NOT NULL,
  name varchar(30) NOT NULL,
  state varchar(15) NOT NULL,
  version int NOT NULL,
  versionTime datetime NOT NULL,
  orgUuid varchar(32) NOT NULL,
  facilityTemplateUuid varchar(32) DEFAULT NULL,
  facilityTemplateCode varchar(30) DEFAULT NULL,
  facilityTemplateName varchar(30) DEFAULT NULL,
  jobPointUuid varchar(32) DEFAULT NULL,
  jobPointCode varchar(10) DEFAULT NULL,
  jobPointName varchar(20) DEFAULT NULL,
  PRIMARY KEY (uuid)
);

CREATE TABLE t_pickschemetemplatestore (
  uuid varchar(32) NOT NULL,
  storeUuid varchar(32) NOT NULL,
  storeCode varchar(30) NOT NULL,
  storeName varchar(30) NOT NULL,
  pickSchemeUuid varchar(32) NOT NULL,
  PRIMARY KEY (uuid)
);

CREATE TABLE t_facilitytemplate (
  uuid varchar(32) NOT NULL,
  code varchar(30) NOT NULL,
  name varchar(30) NOT NULL,
  PRIMARY KEY (uuid)
);