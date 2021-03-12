-- phpMyAdmin SQL Dump
-- version 4.9.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Mar 11, 2021 at 11:20 AM
-- Server version: 10.4.8-MariaDB
-- PHP Version: 7.3.11

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `exam_management`
--

-- --------------------------------------------------------

--
-- Table structure for table `exams`
--

CREATE TABLE `exams` (
  `examId` varchar(6) CHARACTER SET utf8mb4 NOT NULL,
  `examName` varchar(50) CHARACTER SET utf8mb4 NOT NULL,
  `questions` text CHARACTER SET utf8mb4 NOT NULL,
  `facultyUsername` varchar(50) CHARACTER SET utf8mb4 NOT NULL,
  `password` varchar(20) CHARACTER SET utf8mb4 NOT NULL,
  `duration` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_general_ci;

--
-- Dumping data for table `exams`
--

INSERT INTO `exams` (`examId`, `examName`, `questions`, `facultyUsername`, `password`, `duration`) VALUES
('5KWZJ9', 'CC', 'Q1, Q2\nQ3, Q4', 'hardikjoshi', 'qwerty', 10),
('bOYyya', 'OOAD', 'No questions', 'maitrijhaveri', 'password', 10),
('HQUxLt', 'FON', '1. Q1\n2. Q2', 'JayPatel', 'password', 3600),
('qekvD7', 'DAD', 'Q1, Q2', 'bhumikashah', 'password', 3600);

-- --------------------------------------------------------

--
-- Table structure for table `students`
--

CREATE TABLE `students` (
  `rollno` int(11) NOT NULL,
  `studentName` varchar(50) NOT NULL,
  `examId` varchar(6) NOT NULL,
  `startTime` datetime NOT NULL DEFAULT current_timestamp(),
  `endTime` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `students`
--

INSERT INTO `students` (`rollno`, `studentName`, `examId`, `startTime`, `endTime`) VALUES
(10, 'Pradip', '5KWZJ9', '2021-03-11 15:12:50', '2021-03-11 15:13:04'),
(19, 'mn', '5KWZJ9', '2021-03-11 15:30:24', NULL),
(19, 'mn', '5KWZJ9', '2021-03-11 15:31:41', NULL),
(19, 'mn', '5KWZJ9', '2021-03-11 15:32:58', NULL),
(19, 'mn', '5KWZJ9', '2021-03-11 15:34:31', NULL),
(19, 'mn', '5KWZJ9', '2021-03-11 15:36:25', NULL),
(19, 'Milind', 'bOYyya', '2021-03-11 15:47:39', NULL);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `exams`
--
ALTER TABLE `exams`
  ADD PRIMARY KEY (`examId`);

--
-- Indexes for table `students`
--
ALTER TABLE `students`
  ADD KEY `examId` (`examId`);

--
-- Constraints for dumped tables
--

--
-- Constraints for table `students`
--
ALTER TABLE `students`
  ADD CONSTRAINT `students_ibfk_1` FOREIGN KEY (`examId`) REFERENCES `exams` (`examId`) ON DELETE NO ACTION ON UPDATE NO ACTION;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
