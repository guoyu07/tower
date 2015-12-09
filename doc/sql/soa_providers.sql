/*
 Navicat Premium Data Transfer

 Source Server         : 10.10.6.63
 Source Server Type    : MySQL
 Source Server Version : 50617
 Source Host           : 10.10.6.63
 Source Database       : soafw_db

 Target Server Type    : MySQL
 Target Server Version : 50617
 File Encoding         : utf-8

 Date: 12/09/2015 10:17:33 AM
*/

SET NAMES utf8;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
--  Table structure for `soa_sp`
-- ----------------------------
DROP TABLE IF EXISTS `soa_sp`;
CREATE TABLE `soa_sp` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `sp_name` varchar(100) DEFAULT NULL,
  `sp_description` varchar(500) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `index2` (`sp_name`)
) ENGINE=MyISAM AUTO_INCREMENT=28 DEFAULT CHARSET=utf8;

SET FOREIGN_KEY_CHECKS = 1;
