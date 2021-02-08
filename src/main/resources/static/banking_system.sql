DROP SCHEMA banking_system;
CREATE SCHEMA banking_system;
USE banking_system;

CREATE TABLE `account` (
  `account_id` int NOT NULL AUTO_INCREMENT,
  `balance` decimal(19,2) DEFAULT NULL,
  `penalty_fee` decimal(19,2) DEFAULT NULL,
  `primary_owner_id` int DEFAULT NULL,
  `secondary_owner_id` int DEFAULT NULL,
  PRIMARY KEY (`account_id`),
  KEY `FKeb4hr0wmc9ab52ulodr1dcoq2` (`primary_owner_id`),
  KEY `FKa4ukcqpfubnnuxjp9sqwfvj1c` (`secondary_owner_id`),
  CONSTRAINT `FKa4ukcqpfubnnuxjp9sqwfvj1c` FOREIGN KEY (`secondary_owner_id`) REFERENCES `account_holder` (`user_id`),
  CONSTRAINT `FKeb4hr0wmc9ab52ulodr1dcoq2` FOREIGN KEY (`primary_owner_id`) REFERENCES `account_holder` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `checking` (
  `creation_date` datetime(6) DEFAULT NULL,
  `minimum_balance` decimal(19,2) DEFAULT NULL,
  `monthly_maintenance_fee` decimal(19,2) DEFAULT NULL,
  `secret_key` varchar(255) DEFAULT NULL,
  `status` int DEFAULT NULL,
  `account_id` int NOT NULL,
  PRIMARY KEY (`account_id`),
  CONSTRAINT `FKcisv3y9jpxim874s3b7pko2bc` FOREIGN KEY (`account_id`) REFERENCES `account` (`account_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;




CREATE TABLE `user` (
  `user_id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `account_holder` (
  `birth_date` date DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `user_id` int NOT NULL,
  PRIMARY KEY (`user_id`),
  CONSTRAINT `FK209spbfupdxbgcihhfpvguxsx` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

