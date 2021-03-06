CREATE TABLE `ClubMembership` (
  `clubid` int(11) NOT NULL,
  `MemberEmail` varchar(20) DEFAULT '',
  `MemberName` varchar(15) DEFAULT NULL,
  `Role` enum('President','Member') DEFAULT NULL,
  `JoinDate` varchar(15) DEFAULT NULL,
  UNIQUE KEY `clubid` (`clubid`,`MemberEmail`),
  KEY `foreignKey of CommunityMember` (`MemberEmail`),
  CONSTRAINT `foreignKey of CommunityMember` FOREIGN KEY (`MemberEmail`) REFERENCES `CommunityMember` (`Email`) ON DELETE CASCADE,
  CONSTRAINT `foreignKey of TravelClub` FOREIGN KEY (`clubid`) REFERENCES `TravelClub` (`Usid`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;