CREATE TABLE `Posting` (
  `Usid` int(11) NOT NULL AUTO_INCREMENT,
  `Title` varchar(15) DEFAULT NULL,
  `WriterEmail` varchar(15) DEFAULT NULL,
  `Contents` varchar(50) DEFAULT NULL,
  `WrittenDate` varchar(15) DEFAULT NULL,
  `ReadCount` int(11) DEFAULT NULL,
  `BoardId` int(15) DEFAULT NULL,
  PRIMARY KEY (`Usid`),
  KEY `foreignKey of  SocialBoard(boardId)` (`BoardId`),
  CONSTRAINT `foreignKey of  SocialBoard(boardId)` FOREIGN KEY (`BoardId`) REFERENCES `SocialBoard` (`ClubId`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;