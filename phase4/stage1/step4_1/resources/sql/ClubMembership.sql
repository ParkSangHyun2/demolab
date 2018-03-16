CREATE TABLE `ClubMembership` (
  `clubid` int(11) NOT NULL,
  `MemberEmail` varchar(20) DEFAULT '',
  `MemberName` varchar(15) DEFAULT NULL,
  `Role` enum('PRESIDENT','MEMBER') DEFAULT NULL,
  `JoinDate` varchar(15) DEFAULT NULL,
  UNIQUE KEY `clubid` (`clubid`,`MemberEmail`),
  KEY `foreignKey of CommunityMember(email)` (`MemberEmail`),
  CONSTRAINT `foreignKey of CommunityMember(email)` FOREIGN KEY (`MemberEmail`) REFERENCES `CommunityMember` (`Email`) ON DELETE CASCADE,
  CONSTRAINT `foreignKey of TravelClub(usid)` FOREIGN KEY (`clubid`) REFERENCES `TravelClub` (`Usid`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;