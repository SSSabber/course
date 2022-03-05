-- --------------------------------------------------------
-- 主机:                           127.0.0.1
-- 服务器版本:                        5.7.21-log - MySQL Community Server (GPL)
-- 服务器操作系统:                      Win32
-- HeidiSQL 版本:                  9.5.0.5196
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;


-- 导出 learn 的数据库结构
CREATE DATABASE IF NOT EXISTS `learn` /*!40100 DEFAULT CHARACTER SET utf8 COLLATE utf8_unicode_ci */;
USE `learn`;

-- 导出  表 learn.admin 结构
CREATE TABLE IF NOT EXISTS `admin` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) COLLATE utf8_unicode_ci NOT NULL DEFAULT '0',
  `account` varchar(50) COLLATE utf8_unicode_ci NOT NULL DEFAULT '0',
  `password` varchar(50) COLLATE utf8_unicode_ci NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- 正在导出表  learn.admin 的数据：~0 rows (大约)
DELETE FROM `admin`;
/*!40000 ALTER TABLE `admin` DISABLE KEYS */;
INSERT INTO `admin` (`id`, `name`, `account`, `password`) VALUES
	(1, '管理员', 'admin', 'admin');
/*!40000 ALTER TABLE `admin` ENABLE KEYS */;

-- 导出  表 learn.college 结构
CREATE TABLE IF NOT EXISTS `college` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `intro` text COLLATE utf8_unicode_ci,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- 正在导出表  learn.college 的数据：~3 rows (大约)
DELETE FROM `college`;
/*!40000 ALTER TABLE `college` DISABLE KEYS */;
INSERT INTO `college` (`id`, `name`, `intro`) VALUES
	(2, '计算机科学与技术', '计算机科学与技术'),
	(3, '信息管理学院', '信息管理学院，信息管理与信息系统'),
	(8, '外国语言学院', '集成英语、俄语、法语、日语、韩语、西班牙语、德语等主流外语语种');
/*!40000 ALTER TABLE `college` ENABLE KEYS */;

-- 导出  表 learn.course 结构
CREATE TABLE IF NOT EXISTS `course` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) COLLATE utf8_unicode_ci NOT NULL DEFAULT '0',
  `college_id` int(11) NOT NULL DEFAULT '0',
  `num` varchar(50) COLLATE utf8_unicode_ci NOT NULL DEFAULT '0',
  `intro` text COLLATE utf8_unicode_ci NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- 正在导出表  learn.course 的数据：~3 rows (大约)
DELETE FROM `course`;
/*!40000 ALTER TABLE `course` DISABLE KEYS */;
INSERT INTO `course` (`id`, `name`, `college_id`, `num`, `intro`) VALUES
	(1, 'Java Web 程序设计', 2, '80015001', '介绍MVC设计思想，包括JSP+Servet+Java Bean，以及Spring MVC+MyBatis的框架整合开发。'),
	(3, 'SQLServer 2016技术', 2, '80015002', '掌握微软SQLServer 2016数据库的使用以及触发器等开发'),
	(5, '英语高级写作技巧', 8, '70010002', '基于英语写作基础，进阶教授英语写作高级技巧，锻炼学生写作能力。');
/*!40000 ALTER TABLE `course` ENABLE KEYS */;

-- 导出  表 learn.course_file 结构
CREATE TABLE IF NOT EXISTS `course_file` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `path` varchar(50) COLLATE utf8_unicode_ci DEFAULT '0',
  `course_id` int(11) DEFAULT '0',
  `record_time` datetime DEFAULT NULL,
  `teacher_id` int(11) DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- 正在导出表  learn.course_file 的数据：~1 rows (大约)
DELETE FROM `course_file`;
/*!40000 ALTER TABLE `course_file` DISABLE KEYS */;
INSERT INTO `course_file` (`id`, `path`, `course_id`, `record_time`, `teacher_id`) VALUES
	(6, 'Spring MVC教程.pptx', 5, '2018-03-31 22:50:24', 1);
/*!40000 ALTER TABLE `course_file` ENABLE KEYS */;

-- 导出  表 learn.course_video 结构
CREATE TABLE IF NOT EXISTS `course_video` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `path` varchar(50) COLLATE utf8_unicode_ci NOT NULL DEFAULT '0',
  `course_id` int(11) NOT NULL DEFAULT '0',
  `record_time` datetime NOT NULL,
  `teacher_id` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- 正在导出表  learn.course_video 的数据：~1 rows (大约)
DELETE FROM `course_video`;
/*!40000 ALTER TABLE `course_video` DISABLE KEYS */;
INSERT INTO `course_video` (`id`, `path`, `course_id`, `record_time`, `teacher_id`) VALUES
	(9, 'Web开发技术 00_00_00-00_02_00.mp4', 5, '2018-03-31 22:49:57', 1);
/*!40000 ALTER TABLE `course_video` ENABLE KEYS */;

-- 导出  表 learn.discuss 结构
CREATE TABLE IF NOT EXISTS `discuss` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(50) COLLATE utf8_unicode_ci NOT NULL DEFAULT '0',
  `content` text COLLATE utf8_unicode_ci NOT NULL,
  `teacher_id` int(11) DEFAULT NULL,
  `record_time` datetime DEFAULT NULL,
  `course_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- 正在导出表  learn.discuss 的数据：~1 rows (大约)
DELETE FROM `discuss`;
/*!40000 ALTER TABLE `discuss` DISABLE KEYS */;
INSERT INTO `discuss` (`id`, `title`, `content`, `teacher_id`, `record_time`, `course_id`) VALUES
	(4, '在大学英语六级考试中，看图写作文的语言组织思路是什么？', '大学六级考试作文部分越来越重视看图写作文技巧的考察，从读懂图中所说含义到架构构思，再到具体撰写，应该遵循怎样的思路？', 1, '2018-03-31 22:52:37', 5);
/*!40000 ALTER TABLE `discuss` ENABLE KEYS */;

-- 导出  表 learn.discuss_post 结构
CREATE TABLE IF NOT EXISTS `discuss_post` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `content` text COLLATE utf8_unicode_ci NOT NULL,
  `discuss_id` int(11) NOT NULL DEFAULT '0',
  `student_id` int(11) NOT NULL DEFAULT '0',
  `record_time` datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- 正在导出表  learn.discuss_post 的数据：~5 rows (大约)
DELETE FROM `discuss_post`;
/*!40000 ALTER TABLE `discuss_post` DISABLE KEYS */;
INSERT INTO `discuss_post` (`id`, `content`, `discuss_id`, `student_id`, `record_time`) VALUES
	(1, 'dsdsadsadsadsadsasa肥肥的是非得失', 1, 1, '2018-03-30 22:21:08'),
	(2, 'dsdsadsadsadsadsasa肥肥的是非得失', 1, 1, '2018-03-30 22:22:30'),
	(3, 'dsdsadsadsadsadsasa肥肥的是非得失', 1, 1, '2018-03-30 22:23:34'),
	(4, '地方范德萨发范德萨发斯蒂芬大萨达的范德萨', 1, 1, '2018-03-31 22:31:41'),
	(6, '看图写作文应当从图中人物或事物关系开始，理解图中所描述的事是关于生活、工作、旅行、哲学等等，搞清楚这些才是理解图文的重要步骤！！！', 4, 1, '2018-03-31 23:00:15');
/*!40000 ALTER TABLE `discuss_post` ENABLE KEYS */;

-- 导出  表 learn.notice 结构
CREATE TABLE IF NOT EXISTS `notice` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(50) COLLATE utf8_unicode_ci NOT NULL DEFAULT '0',
  `content` text COLLATE utf8_unicode_ci NOT NULL,
  `record_time` datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- 正在导出表  learn.notice 的数据：~2 rows (大约)
DELETE FROM `notice`;
/*!40000 ALTER TABLE `notice` DISABLE KEYS */;
INSERT INTO `notice` (`id`, `title`, `content`, `record_time`) VALUES
	(1, '关于《大学生学习平台》的启用通知', '请各学院老师、学生注意，由我校开发的《大学生学习平台》已经上线，今后将实现在平台上进行资料上传', '2018-03-29 15:29:04'),
	(4, '关于学院期中考试安排的通知', '学院期中考试将于2018年5月15日至2018年5月22日进行，请各专业、各班级组织和实施好各自的考试进度安排以及阅卷工作！  学院管理处，2018年3月31日', '2018-03-31 22:49:21');
/*!40000 ALTER TABLE `notice` ENABLE KEYS */;

-- 导出  表 learn.student 结构
CREATE TABLE IF NOT EXISTS `student` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) COLLATE utf8_unicode_ci NOT NULL DEFAULT '0',
  `num` varchar(50) COLLATE utf8_unicode_ci NOT NULL DEFAULT '0',
  `college_id` int(11) NOT NULL DEFAULT '0',
  `telphone` varchar(50) COLLATE utf8_unicode_ci NOT NULL DEFAULT '0',
  `id_card_no` varchar(50) COLLATE utf8_unicode_ci NOT NULL DEFAULT '0',
  `gender` int(11) NOT NULL DEFAULT '0',
  `account` varchar(50) COLLATE utf8_unicode_ci NOT NULL DEFAULT '0',
  `password` varchar(50) COLLATE utf8_unicode_ci NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- 正在导出表  learn.student 的数据：~2 rows (大约)
DELETE FROM `student`;
/*!40000 ALTER TABLE `student` DISABLE KEYS */;
INSERT INTO `student` (`id`, `name`, `num`, `college_id`, `telphone`, `id_card_no`, `gender`, `account`, `password`) VALUES
	(2, '李贤', '80010001', 2, '17709319089', '62209119980712031X', 0, 'lixian', '123456');
/*!40000 ALTER TABLE `student` ENABLE KEYS */;

-- 导出  表 learn.student_task 结构
CREATE TABLE IF NOT EXISTS `student_task` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `student_id` int(11) NOT NULL DEFAULT '0',
  `task_id` int(11) NOT NULL DEFAULT '0',
  `score` int(11) NOT NULL DEFAULT '0',
  `record_time` datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- 正在导出表  learn.student_task 的数据：~3 rows (大约)
DELETE FROM `student_task`;
/*!40000 ALTER TABLE `student_task` DISABLE KEYS */;
INSERT INTO `student_task` (`id`, `student_id`, `task_id`, `score`, `record_time`) VALUES
	(1, 1, 2, 20, '2018-03-31 20:28:53'),
	(2, 1, 2, 60, '2018-03-31 22:40:56'),
	(3, 1, 4, 60, '2018-03-31 23:00:57');
/*!40000 ALTER TABLE `student_task` ENABLE KEYS */;

-- 导出  表 learn.task 结构
CREATE TABLE IF NOT EXISTS `task` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `teacher_id` int(11) NOT NULL DEFAULT '0',
  `course_id` int(11) NOT NULL DEFAULT '0',
  `record_time` datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- 正在导出表  learn.task 的数据：~3 rows (大约)
DELETE FROM `task`;
/*!40000 ALTER TABLE `task` DISABLE KEYS */;
INSERT INTO `task` (`id`, `teacher_id`, `course_id`, `record_time`) VALUES
	(2, 1, 1, '2018-03-29 22:23:24'),
	(3, 1, 3, '2018-03-31 22:28:09'),
	(4, 1, 5, '2018-03-31 22:55:35');
/*!40000 ALTER TABLE `task` ENABLE KEYS */;

-- 导出  表 learn.task_question 结构
CREATE TABLE IF NOT EXISTS `task_question` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(50) COLLATE utf8_unicode_ci NOT NULL DEFAULT '0',
  `item_a` varchar(50) COLLATE utf8_unicode_ci NOT NULL DEFAULT '0',
  `item_b` varchar(50) COLLATE utf8_unicode_ci NOT NULL DEFAULT '0',
  `item_c` varchar(50) COLLATE utf8_unicode_ci NOT NULL DEFAULT '0',
  `item_d` varchar(50) COLLATE utf8_unicode_ci NOT NULL DEFAULT '0',
  `answer` varchar(50) COLLATE utf8_unicode_ci NOT NULL DEFAULT '0',
  `task_id` int(11) NOT NULL DEFAULT '0',
  `score` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- 正在导出表  learn.task_question 的数据：~7 rows (大约)
DELETE FROM `task_question`;
/*!40000 ALTER TABLE `task_question` DISABLE KEYS */;
INSERT INTO `task_question` (`id`, `title`, `item_a`, `item_b`, `item_c`, `item_d`, `answer`, `task_id`, `score`) VALUES
	(3, 'MVC三层模式中，C指的是哪一层？', '控制层', '模型层', '视图层', '以上都不是', 'A', 2, 10),
	(4, '以下关于重定向forward说法正确的是？', '重定向时URL不变而且不可传递参数', '重定向时URL会变而且不可传递参数', '重定向时URL不变而且可传递参数', '重定向时URL会变而且可传递参数', 'C', 2, 10),
	(5, '以下关于Http Session会话的说法正确的是？', 'Session对象保存于磁盘中', 'Session存在于服务器内存中', 'Session存在于服务器数据库中', 'session存在于客户端浏览器中', 'B', 2, 10),
	(6, '在MySQL脚本中，查询第一条数据的写法正确的是？', 'LIMIT 1', 'LIMIT 0', 'TOP 1', 'FIRST', 'A', 2, 10),
	(8, '而我却二无群二', '而为全额', '而为全额', '而我却二无群', '企鹅窝群二', 'A', 3, 10),
	(9, 'What’s the mean on the earth road ?', 'YES OR NO', 'NO', 'YES', 'BOTH YES', 'B', 4, 20),
	(10, 'How much dose the sheep on the moution ?', 'Three sheeps in the house.', 'No sheep in the house.', 'Much sheep in the door.', 'Both Wrong', 'C', 4, 10);
/*!40000 ALTER TABLE `task_question` ENABLE KEYS */;

-- 导出  表 learn.teacher 结构
CREATE TABLE IF NOT EXISTS `teacher` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) COLLATE utf8_unicode_ci NOT NULL DEFAULT '0',
  `gender` int(11) NOT NULL DEFAULT '0',
  `college_id` int(11) NOT NULL DEFAULT '0',
  `telphone` varchar(50) COLLATE utf8_unicode_ci NOT NULL DEFAULT '0',
  `id_card_no` varchar(50) COLLATE utf8_unicode_ci NOT NULL DEFAULT '0',
  `account` varchar(50) COLLATE utf8_unicode_ci NOT NULL DEFAULT '0',
  `password` varchar(50) COLLATE utf8_unicode_ci NOT NULL DEFAULT '0',
  `num` varchar(50) COLLATE utf8_unicode_ci NOT NULL DEFAULT '0',
  `course_id` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- 正在导出表  learn.teacher 的数据：~4 rows (大约)
DELETE FROM `teacher`;
/*!40000 ALTER TABLE `teacher` DISABLE KEYS */;
INSERT INTO `teacher` (`id`, `name`, `gender`, `college_id`, `telphone`, `id_card_no`, `account`, `password`, `num`, `course_id`) VALUES
	(1, '李正涛', 0, 8, '13990390912', '62292319601209012X', 'lizhengtao', '111111', '80012001', 5),
	(3, '32', 0, 2, '323', '232', '323', '3232', '232', 1),
	(4, 'wewq', 0, 2, 'eqwewqe', 'wqewqeq', 'qweqwe', 'ewqe', 'ewqewq', 1),
	(5, '21321', 1, 2, '321312', '321312', '321312', '312321', '32131', 3);
/*!40000 ALTER TABLE `teacher` ENABLE KEYS */;

/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
