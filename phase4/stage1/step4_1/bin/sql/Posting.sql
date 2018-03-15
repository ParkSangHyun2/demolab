CREATE TABLE `Posting` (
  `usid` varchar(40) NOT NULL DEFAULT '',
  `title` varchar(20) DEFAULT NULL,
  `writerEmail` varchar(20) NOT NULL DEFAULT '',
  `contents` varchar(80) DEFAULT NULL,
  `writtenDate` varchar(11) DEFAULT NULL,
  `readCount` int(11) DEFAULT NULL,
  `boardId` varchar(40) NOT NULL DEFAULT '',
  PRIMARY KEY (`usid`),
  KEY `boardId` (`boardId`),
  CONSTRAINT `posting_ibfk_1` FOREIGN KEY (`boardId`) REFERENCES `SocialBoard` (`clubId`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;