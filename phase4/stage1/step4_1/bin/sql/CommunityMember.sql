CREATE TABLE `CommunityMember` (
  `email` varchar(20) NOT NULL DEFAULT '',
  `name` varchar(15) NOT NULL DEFAULT '',
  `nickName` varchar(15) DEFAULT NULL,
  `phoneNumber` varchar(15) DEFAULT NULL,
  `birthday` varchar(10) DEFAULT NULL,
  PRIMARY KEY (`email`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;