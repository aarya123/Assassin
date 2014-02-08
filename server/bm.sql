-- phpMyAdmin SQL Dump
-- version 4.0.9
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1
-- Generation Time: Feb 08, 2014 at 07:31 PM
-- Server version: 5.6.14
-- PHP Version: 5.5.6

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Database: `bm`
--

-- --------------------------------------------------------

--
-- Table structure for table `locations`
--

CREATE TABLE IF NOT EXISTS `locations` (
  `user_id` int(11) NOT NULL,
  `location` text NOT NULL,
  KEY `fk` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `locations`
--

INSERT INTO `locations` (`user_id`, `location`) VALUES
(22, 'a'),
(22, 'b'),
(22, 'c'),
(23, 'd'),
(23, 'e'),
(23, 'f'),
(24, 'a'),
(24, 'b'),
(24, 'c');

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

CREATE TABLE IF NOT EXISTS `users` (
  `password_hash` text NOT NULL,
  `user_id` int(11) NOT NULL AUTO_INCREMENT,
  `email_address` varchar(100) NOT NULL,
  `sex` text NOT NULL,
  `age` int(11) NOT NULL,
  `race` text NOT NULL,
  `height` int(11) NOT NULL,
  `current_latitude` decimal(9,6) DEFAULT NULL,
  `current_longitude` decimal(9,6) DEFAULT NULL,
  `registration_id` text,
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=25 ;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`password_hash`, `user_id`, `email_address`, `sex`, `age`, `race`, `height`, `current_latitude`, `current_longitude`, `registration_id`) VALUES
('900150983cd24fb0d6963f7d28e17f72', 22, 'a@abcdeffff.com', 'm', 19, 'white', 7, '50.000000', '90.000000', 'test9'),
('098f6bcd4621d373cade4e832627b4f6', 23, 'b@b.com', 'f', 22, 'black', 8, '20.000000', '30.000000', 'test'),
('900150983cd24fb0d6963f7d28e17f72', 24, 'a@a.com', 'm', 2, 'white', 7, NULL, NULL, NULL);

--
-- Constraints for dumped tables
--

--
-- Constraints for table `locations`
--
ALTER TABLE `locations`
  ADD CONSTRAINT `fk` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
