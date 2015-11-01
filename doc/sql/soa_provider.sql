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

 Date: 11/01/2015 15:39:39 PM
*/

SET NAMES utf8;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
--  Table structure for `soa_provider`
-- ----------------------------
DROP TABLE IF EXISTS `soa_provider`;
CREATE TABLE `soa_provider` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `sp_name` varchar(100) DEFAULT NULL,
  `start_port` int(11) DEFAULT '8000',
  `stop_port` int(11) DEFAULT '9000',
  `service_port` int(11) DEFAULT '20880',
  `sp_description` varchar(500) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `index2` (`sp_name`)
) ENGINE=MyISAM AUTO_INCREMENT=28 DEFAULT CHARSET=utf8;

SET FOREIGN_KEY_CHECKS = 1;
