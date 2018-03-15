CREATE TABLE `ClubMembership` (
  `clubId` varchar(40) NOT NULL DEFAULT '',
  `memberEmail` varchar(20) NOT NULL DEFAULT '',
  `memberName` varchar(20) DEFAULT NULL,
  `role` varchar(11) DEFAULT NULL,
  `joinDate` varchar(15) DEFAULT NULL,
  UNIQUE KEY `memberEmail` (`memberEmail`,`clubId`),
  KEY `clubId` (`clubId`),
  CONSTRAINT `clubmembership_ibfk_1` FOREIGN KEY (`clubId`) REFERENCES `TravelClub` (`usid`) ON DELETE CASCADE,
  CONSTRAINT `clubmembership_ibfk_2` FOREIGN KEY (`memberEmail`) REFERENCES `CommunityMember` (`email`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;