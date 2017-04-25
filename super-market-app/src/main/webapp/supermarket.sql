/*
Navicat MySQL Data Transfer

Source Server         : test
Source Server Version : 50716
Source Host           : localhost:3306
Source Database       : supermarket

Target Server Type    : MYSQL
Target Server Version : 50716
File Encoding         : 65001

Date: 2017-04-25 21:31:48
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `st_employee`
-- ----------------------------
DROP TABLE IF EXISTS `st_employee`;
CREATE TABLE `st_employee` (
  `employee_id` varchar(10) NOT NULL,
  `employee_name` varchar(100) NOT NULL,
  `employee_password` varchar(100) NOT NULL,
  `employee_type` varchar(50) NOT NULL,
  PRIMARY KEY (`employee_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of st_employee
-- ----------------------------

-- ----------------------------
-- Table structure for `st_goods`
-- ----------------------------
DROP TABLE IF EXISTS `st_goods`;
CREATE TABLE `st_goods` (
  `goods_id` varchar(10) NOT NULL,
  `goods_name` varchar(100) NOT NULL,
  `goods_company` varchar(100) NOT NULL,
  `goods_type` varchar(20) NOT NULL,
  `goods_cost` decimal(10,2) NOT NULL,
  `goods_price` decimal(10,2) NOT NULL,
  PRIMARY KEY (`goods_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of st_goods
-- ----------------------------

-- ----------------------------
-- Table structure for `st_goods_type`
-- ----------------------------
DROP TABLE IF EXISTS `st_goods_type`;
CREATE TABLE `st_goods_type` (
  `type_id` int(11) NOT NULL,
  `type_name` varchar(100) NOT NULL,
  `type_explain` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`type_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of st_goods_type
-- ----------------------------

-- ----------------------------
-- Table structure for `st_log`
-- ----------------------------
DROP TABLE IF EXISTS `st_log`;
CREATE TABLE `st_log` (
  `log_id` int(11) NOT NULL,
  `employee_id` varchar(10) NOT NULL,
  `login_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `logout_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `login_ip` varchar(100) NOT NULL,
  PRIMARY KEY (`log_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of st_log
-- ----------------------------

-- ----------------------------
-- Table structure for `st_market`
-- ----------------------------
DROP TABLE IF EXISTS `st_market`;
CREATE TABLE `st_market` (
  `bill_id` varchar(10) NOT NULL,
  `goods_id` varchar(10) NOT NULL,
  `sales_quantity` varchar(100) NOT NULL,
  `amount_receivable` decimal(10,2) NOT NULL,
  `discount_rate` varchar(100) NOT NULL,
  `amount_paidvar` decimal(10,2) NOT NULL,
  `sale_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `operator_id` int(11) NOT NULL,
  PRIMARY KEY (`bill_id`),
  KEY `goods_id` (`goods_id`),
  CONSTRAINT `st_market_ibfk_1` FOREIGN KEY (`goods_id`) REFERENCES `st_goods` (`goods_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of st_market
-- ----------------------------

-- ----------------------------
-- Table structure for `st_purchase`
-- ----------------------------
DROP TABLE IF EXISTS `st_purchase`;
CREATE TABLE `st_purchase` (
  `purchase_id` varchar(10) NOT NULL,
  `goods_id` varchar(10) NOT NULL,
  `supplier_id` varchar(10) NOT NULL,
  `purchase_quantity` varchar(100) NOT NULL,
  `payment_amount` decimal(10,2) NOT NULL,
  `purchase_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `operator_id` varchar(10) NOT NULL,
  PRIMARY KEY (`purchase_id`),
  KEY `goods_id` (`goods_id`),
  KEY `supplier_id` (`supplier_id`),
  CONSTRAINT `st_purchase_ibfk_1` FOREIGN KEY (`goods_id`) REFERENCES `st_goods` (`goods_id`),
  CONSTRAINT `st_purchase_ibfk_2` FOREIGN KEY (`supplier_id`) REFERENCES `st_supplier` (`supplier_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of st_purchase
-- ----------------------------

-- ----------------------------
-- Table structure for `st_stock`
-- ----------------------------
DROP TABLE IF EXISTS `st_stock`;
CREATE TABLE `st_stock` (
  `stock_id` varchar(10) NOT NULL,
  `goods_id` varchar(10) NOT NULL,
  `inventory_quantity` int(11) DEFAULT NULL,
  PRIMARY KEY (`stock_id`),
  KEY `goods_id` (`goods_id`),
  CONSTRAINT `st_stock_ibfk_1` FOREIGN KEY (`goods_id`) REFERENCES `st_goods` (`goods_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of st_stock
-- ----------------------------

-- ----------------------------
-- Table structure for `st_supplier`
-- ----------------------------
DROP TABLE IF EXISTS `st_supplier`;
CREATE TABLE `st_supplier` (
  `supplier_id` varchar(10) NOT NULL,
  `supplier_name` varchar(100) NOT NULL,
  `contacts` varchar(100) NOT NULL,
  `telephone` varchar(100) NOT NULL,
  `address` varchar(200) NOT NULL,
  PRIMARY KEY (`supplier_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of st_supplier
-- ----------------------------
