CREATE DATABASE IF NOT EXISTS elective CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci;

USE elective;

CREATE TABLE `users` (
  `user_id` int unsigned NOT NULL AUTO_INCREMENT,
  `login` varchar(45) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `password` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `email` varchar(45) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  PRIMARY KEY (`user_id`) USING BTREE,
  UNIQUE KEY `login` (`login`)
) ENGINE=InnoDB AUTO_INCREMENT=45 DEFAULT CHARSET=utf8mb4;

CREATE TABLE `administrators` (
  `administrator_id` int unsigned NOT NULL AUTO_INCREMENT,
  `user_id` int unsigned NOT NULL,
  `name` varchar(45) NOT NULL,
  `last_name` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`administrator_id`) USING BTREE,
  KEY `fk_administrators_users1_idx` (`user_id`),
  CONSTRAINT `fk_administrators_users1` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4;

CREATE TABLE `courses` (
  `course_id` int unsigned NOT NULL AUTO_INCREMENT,
  `topic` varchar(45) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `title` varchar(45) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `status` varchar(45) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT 'New',
  PRIMARY KEY (`course_id`),
  UNIQUE KEY `title` (`title`)
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8mb4;

CREATE TABLE `students` (
  `student_id` int unsigned NOT NULL AUTO_INCREMENT,
  `user_id` int unsigned DEFAULT NULL,
  `name` varchar(45) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `last_name` varchar(45) DEFAULT NULL,
  `status` varchar(45) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT 'unlocked',
  PRIMARY KEY (`student_id`) USING BTREE,
  KEY `fk_students_users` (`user_id`) USING BTREE,
  CONSTRAINT `fk_students_users` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`) ON DELETE SET NULL ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8mb4;

CREATE TABLE `students_assignments` (
  `courses_course_id` int unsigned NOT NULL,
  `students_student_id` int unsigned NOT NULL,
  `mark` int unsigned DEFAULT NULL,
  PRIMARY KEY (`courses_course_id`,`students_student_id`),
  KEY `fk_students_assigments_courses` (`courses_course_id`) USING BTREE,
  KEY `fk_students_assigments_students` (`students_student_id`) USING BTREE,
  CONSTRAINT `fk_students_assignments_courses` FOREIGN KEY (`courses_course_id`) REFERENCES `courses` (`course_id`),
  CONSTRAINT `fk_students_assignments_students` FOREIGN KEY (`students_student_id`) REFERENCES `students` (`student_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `teachers` (
  `teacher_id` int unsigned NOT NULL AUTO_INCREMENT,
  `user_id` int unsigned DEFAULT NULL,
  `name` varchar(45) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `last_name` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`teacher_id`),
  KEY `fk_teachers_users` (`user_id`) USING BTREE,
  CONSTRAINT `fk_teachers_users` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`) ON DELETE SET NULL
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4;

CREATE TABLE `teachers_assignments` (
  `courses_course_id` int unsigned NOT NULL,
  `teachers_teacher_id` int unsigned NOT NULL,
  PRIMARY KEY (`courses_course_id`,`teachers_teacher_id`) USING BTREE,
  UNIQUE KEY `courses_course_id` (`courses_course_id`),
  KEY `fk_teacher_assigments_teachers` (`teachers_teacher_id`) USING BTREE,
  CONSTRAINT `fk_teacher_assignments_courses` FOREIGN KEY (`courses_course_id`) REFERENCES `courses` (`course_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_teacher_assignments_teachers` FOREIGN KEY (`teachers_teacher_id`) REFERENCES `teachers` (`teacher_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
