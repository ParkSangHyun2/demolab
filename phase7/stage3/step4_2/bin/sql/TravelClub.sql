CREATE TABLE `TravelClub` (
  `Name` varchar(15) DEFAULT NULL,
  `Intro` varchar(40) DEFAULT NULL,
  `FoundationDay` varchar(11) DEFAULT NULL,
  `BoardId` int(15) DEFAULT NULL,
  `Usid` int(11) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`Usid`)
) ENGINE=InnoDB AUTO_INCREMENT=42 DEFAULT CHARSET=utf8;