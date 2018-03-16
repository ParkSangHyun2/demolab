CREATE TABLE `SocialBoard` (
  `ClubId` int(15) NOT NULL,
  `Name` varchar(15) DEFAULT NULL,
  `AdminEmail` varchar(15) DEFAULT NULL,
  `CreateDate` varchar(15) DEFAULT NULL,
  `Sequence` int(11) DEFAULT NULL,
  PRIMARY KEY (`ClubId`),
  CONSTRAINT `foreignKey of TravelClub(clubId)` FOREIGN KEY (`ClubId`) REFERENCES `TravelClub` (`Usid`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;