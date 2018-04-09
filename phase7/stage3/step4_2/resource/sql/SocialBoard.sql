CREATE TABLE `SocialBoard` (
  `Name` varchar(15) DEFAULT NULL,
  `AdminEmail` varchar(20) DEFAULT NULL,
  `CreateDate` varchar(15) DEFAULT NULL,
  `Sequence` int(11) DEFAULT NULL,
  `clubId` int(11) DEFAULT NULL,
  KEY `foreignKey of TraveClub` (`clubId`),
  CONSTRAINT `foreignKey of TraveClub` FOREIGN KEY (`clubId`) REFERENCES `TravelClub` (`Usid`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;