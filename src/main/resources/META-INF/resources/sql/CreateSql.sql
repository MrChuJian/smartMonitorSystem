SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for deploy
-- ----------------------------
DROP TABLE IF EXISTS `file`;
CREATE TABLE `file` (
  `id` int(255) NOT NULL AUTO_INCREMENT,
  `fileName` varchar(255) DEFAULT NULL,
  `uuidName` varchar(255) DEFAULT NULL,
  `mimeType` varchar(255) DEFAULT NULL,
  `createTime` timestamp,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;

