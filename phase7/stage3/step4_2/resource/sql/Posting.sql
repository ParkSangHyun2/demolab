CREATE TABLE `Posting` (
  `Title` varchar(20) DEFAULT NULL,
  `WriterEmail` varchar(20) DEFAULT NULL,
  `Contents` varchar(100) DEFAULT NULL,
  `WrittenDate` varchar(15) DEFAULT NULL,
  `ReadCount` int(11) DEFAULT NULL,
  `boardId` int(15) DEFAULT NULL,
  `Usid` int(11) unsigned NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`Usid`),
  KEY `rereignKey of SocialBoard` (`boardId`),
  CONSTRAINT `rereignKey of SocialBoard` FOREIGN KEY (`boardId`) REFERENCES `SocialBoard` (`clubId`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8;