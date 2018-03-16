CREATE TABLE `TravelClub` (
  `Usid` int(11) NOT NULL,
  `Name` varchar(15) DEFAULT NULL,
  `Intro` varchar(15) DEFAULT NULL,
  `FoundationDay` varchar(11) DEFAULT NULL,
  `BoardId` varchar(15) DEFAULT NULL,
  PRIMARY KEY (`Usid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;