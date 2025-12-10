CREATE TABLE `users`
(
    `id`         BINARY(16)   NOT NULL,
    `name`       VARCHAR(255) NOT NULL,
    `phone`      VARCHAR(255) NOT NULL,
    `password`   VARCHAR(255) NOT NULL,
    `email`      VARCHAR(255) NULL,
    `role`       ENUM('CUSTOMER','MANAGER','OWNER') NOT NULL,
    `banned`     TINYINT(1) NOT NULL,
    `created_at` DATETIME     NOT NULL,
    `updated_at` DATETIME NULL,
    `version`    SMALLINT NULL,
    CONSTRAINT `pk_users` PRIMARY KEY (`id`)
);

CREATE TABLE `verification_sessions`
(
    `id`                BINARY(16)   NOT NULL,
    `phone`             VARCHAR(255) NOT NULL,
    `code`              VARCHAR(255) NOT NULL,
    `request_client_id` VARCHAR(255) NOT NULL,
    `purpose`           ENUM('REGISTRATION','PASSWORD_RESET','PHONE_NUMBER_CHANGE') NOT NULL,
    `verified`          TINYINT(1)       NOT NULL,
    `created_at`        DATETIME     NOT NULL,
    `version`           SMALLINT NULL,
    CONSTRAINT `pk_verification_sessions` PRIMARY KEY (`id`)
);
