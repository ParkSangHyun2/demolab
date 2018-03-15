CREATE TABLE `TravelClub` (
  `usid` varchar(40) NOT NULL DEFAULT '',
  `name` varchar(20) DEFAULT NULL,
  `intro` varchar(20) DEFAULT NULL,
  `foundationDay` varchar(10) DEFAULT NULL,
  `boardId` varchar(11) DEFAULT NULL,
  PRIMARY KEY (`usid`),
  KEY `INDEX` (`usid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;