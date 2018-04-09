CREATE TABLE `CommunityMember` (
  `Email` varchar(20) NOT NULL,
  `Name` varchar(15) DEFAULT NULL,
  `NickName` varchar(20) DEFAULT NULL,
  `PhoneNumber` varchar(14) DEFAULT NULL,
  `BirthDay` varchar(15) DEFAULT NULL,
  PRIMARY KEY (`Email`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;