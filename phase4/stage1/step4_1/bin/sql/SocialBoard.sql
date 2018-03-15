CREATE TABLE `SocialBoard` (
  `clubId` varchar(40) NOT NULL DEFAULT '',
  `name` varchar(20) NOT NULL DEFAULT '',
  `adminEmail` varchar(20) NOT NULL DEFAULT '',
  `createDate` varchar(15) NOT NULL DEFAULT '',
  `sequence` int(11) DEFAULT NULL,
  PRIMARY KEY (`clubId`),
  KEY `sequence` (`sequence`),
  CONSTRAINT `TravelClub종속` FOREIGN KEY (`clubId`) REFERENCES `TravelClub` (`usid`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;