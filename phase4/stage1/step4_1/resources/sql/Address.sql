CREATE TABLE `Address` (
  `ZipCode` varchar(15) DEFAULT NULL,
  `ZipAddress` varchar(15) DEFAULT NULL,
  `StreetAddress` varchar(15) DEFAULT NULL,
  `Country` varchar(15) DEFAULT NULL,
  `AddressType` enum('HOME','OFFICE') DEFAULT 'HOME',
  `id` int(10) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;