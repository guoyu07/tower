/*
 Navicat Premium Data Transfer

 Source Server         : tsl-dev
 Source Server Type    : MySQL
 Source Server Version : 50622
 Source Host           : 192.168.1.110
 Source Database       : soafw_db

 Target Server Type    : MySQL
 Target Server Version : 50622
 File Encoding         : utf-8

 Date: 11/01/2015 15:40:12 PM
*/

SET NAMES utf8;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
--  Table structure for `soa_cache_version`
-- ----------------------------
DROP TABLE IF EXISTS `soa_cache_version`;
CREATE TABLE `soa_cache_version` (
  `obj_name` varchar(32) DEFAULT NULL,
  `rec_version` bigint(20) NOT NULL,
  `tab_version` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

SET FOREIGN_KEY_CHECKS = 1;
