CREATE TABLE IF NOT EXISTS `SQLBans_bans` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `ban_code` varchar(20) NOT NULL,
  `username` varchar(16) NOT NULL,
  `reason` tinytext NOT NULL,
  `admin` varchar(16) NOT NULL,
  `timestamp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `banned` tinyint(1) NOT NULL DEFAULT '1',
  PRIMARY KEY (`id`),
  UNIQUE KEY `UNIQUE` (`ban_code`),
  KEY `username` (`username`)
) ENGINE=MyISAM  DEFAULT CHARSET=utf8 AUTO_INCREMENT=1 ;
