CREATE TABLE `ADDRESS` (
  `zipCode` int(11) NOT NULL,
  `zipAddress` varchar(45) DEFAULT NULL,
  `streetAddress` varchar(45) DEFAULT NULL,
  `Country` varchar(45) DEFAULT NULL,
  `AddressType` enum('Office','Home') DEFAULT NULL,
  PRIMARY KEY (`zipCode`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `COMMUNITYMEMBER` (
  `memberEmail` varchar(45) NOT NULL,
  `memberName` varchar(45) DEFAULT '',
  `nickName` varchar(45) DEFAULT NULL,
  `phoneNumber` varchar(45) DEFAULT NULL,
  `birthDay` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`memberEmail`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `TRAVELCLUB` (
  `usid` varchar(45) NOT NULL DEFAULT '',
  `name` varchar(45) DEFAULT NULL,
  `intro` varchar(45) DEFAULT NULL,
  `foundationDay` varchar(45) DEFAULT NULL,
  `boardId` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`usid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `CLUBMEMBERSHIP` (
  `clubId` varchar(45) NOT NULL,
  `memberEmail` varchar(45) NOT NULL,
  `memberName` varchar(45) DEFAULT NULL,
  `role` enum('President','Member') NOT NULL,
  `joinDate` varchar(45) DEFAULT NULL,
  KEY `FK_CLUBMEMBERSHIP_memberEmail_COMMUNITYMEMBER_memberEmail` (`memberEmail`),
  KEY `clubId` (`clubId`),
  CONSTRAINT `FK_CLUBMEMBERSHIP_memberEmail_COMMUNITYMEMBER_memberEmail` FOREIGN KEY (`memberEmail`) REFERENCES `COMMUNITYMEMBER` (`memberEmail`),
  CONSTRAINT `clubmembership_ibfk_1` FOREIGN KEY (`clubId`) REFERENCES `TRAVELCLUB` (`usid`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `SOCIALBOARD` (
  `clubId` varchar(45) NOT NULL,
  `name` varchar(45) DEFAULT NULL,
  `adminEmail` varchar(45) DEFAULT NULL,
  `createDate` varchar(45) DEFAULT NULL,
  `sequence` int(11) DEFAULT NULL,
  PRIMARY KEY (`clubId`),
  CONSTRAINT `socialboard_ibfk_1` FOREIGN KEY (`clubId`) REFERENCES `TRAVELCLUB` (`usid`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `POSTING` (
  `usid` varchar(45) NOT NULL,
  `title` varchar(45) DEFAULT NULL,
  `writerEmail` varchar(45) DEFAULT NULL,
  `contents` varchar(45) DEFAULT NULL,
  `writtenDate` varchar(45) DEFAULT NULL,
  `readCount` int(11) DEFAULT NULL,
  `boardId` varchar(45) NOT NULL DEFAULT '',
  PRIMARY KEY (`usid`),
  KEY `boardId` (`boardId`),
  CONSTRAINT `posting_ibfk_1` FOREIGN KEY (`boardId`) REFERENCES `SOCIALBOARD` (`clubId`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

