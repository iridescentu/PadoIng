CREATE TABLE `AdHistory`(
    `id` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `vide_ad_id` BIGINT NOT NULL,
    `created_at` DATETIME NOT NULL
);
CREATE TABLE `VideoStmt`(
    `id` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `video_id` BIGINT NOT NULL,
    `date` DATE NOT NULL,
    `video_stmt` INT NOT NULL
);
CREATE TABLE `user_roles`(
    `user_id` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `role` ENUM('uploader', 'user') NOT NULL
);
CREATE TABLE `AdStats`(
    `id` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `video_ad_id` BIGINT NOT NULL,
    `date` DATE NOT NULL,
    `ad_view` INT NOT NULL
);
CREATE TABLE `WatchHistory`(
    `id` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `user_id` BIGINT NOT NULL,
    `video_id` BIGINT NOT NULL,
    `watch_duration` BIGINT NOT NULL,
    `last_watched_position` BIGINT NOT NULL,
    `created_at` DATETIME NOT NULL COMMENT 'WatchHistory  (  \\\\\\\\uc2dc\\\\\\\\uccad \\\\\\\\uae30\\\\\\\\ub85d  )  \\\\\\\\uac00 \\\\\\\\uc0dd\\\\\\\\uc131\\\\\\\\ub41c \\\\\\\\uc2dc\\\\\\\\uc810'
);
CREATE TABLE `Ads`(
    `id` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `title` VARCHAR(255) NOT NULL,
    `description` TEXT NOT NULL,
    `duration` INT NOT NULL,
    `created_at` DATETIME NOT NULL
);
CREATE TABLE `Video`(
    `id` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `user_id` BIGINT NOT NULL,
    `title` VARCHAR(255) NOT NULL COMMENT '\\\\\\\\ub3d9\\\\\\\\uc601\\\\\\\\uc0c1 \\\\\\\\uc81c\\\\\\\\ubaa9',
    `description` TEXT NULL COMMENT '\\\\\\\\ub3d9\\\\\\\\uc601\\\\\\\\uc0c1 \\\\\\\\uc124\\\\\\\\uba85',
    `upload_date` DATETIME NOT NULL,
    `views` INT NOT NULL,
    `duration` INT NOT NULL COMMENT '\\\\\\\\ucd08 \\\\\\\\ub2e8\\\\\\\\uc704\\\\\\\\ub85c \\\\\\\\uc800\\\\\\\\uc7a5',
    `is_active` BOOLEAN NOT NULL,
    `is_deleted` BOOLEAN NOT NULL
);
CREATE TABLE `User`(
    `id` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `user_name` VARCHAR(255) NOT NULL,
    `email` VARCHAR(255) NOT NULL,
    `created_at` DATETIME NOT NULL,
    `grade` VARCHAR(255) NOT NULL,
    `name` VARCHAR(255) NOT NULL
);
ALTER TABLE
    `User` ADD UNIQUE `user_email_unique`(`email`);
CREATE TABLE `AdStmt`(
    `id` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `video_ad_id` BIGINT NOT NULL,
    `date` DATE NOT NULL,
    `ad_stmt` INT NOT NULL
);
CREATE TABLE `VideoStats`(
    `id` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `video_id` BIGINT NOT NULL,
    `date` DATE NOT NULL,
    `video_view` INT NOT NULL,
    `play_time` INT NOT NULL
);
CREATE TABLE `VideoAd`(
    `id` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `video_id` BIGINT NOT NULL,
    `ad_id` BIGINT NOT NULL,
    `views` INT NOT NULL
);
ALTER TABLE
    `WatchHistory` ADD CONSTRAINT `watchhistory_video_id_foreign` FOREIGN KEY(`video_id`) REFERENCES `Video`(`id`);
ALTER TABLE
    `AdStats` ADD CONSTRAINT `adstats_video_ad_id_foreign` FOREIGN KEY(`video_ad_id`) REFERENCES `VideoAd`(`id`);
ALTER TABLE
    `WatchHistory` ADD CONSTRAINT `watchhistory_user_id_foreign` FOREIGN KEY(`user_id`) REFERENCES `User`(`id`);
ALTER TABLE
    `VideoAd` ADD CONSTRAINT `videoad_video_id_foreign` FOREIGN KEY(`video_id`) REFERENCES `Video`(`id`);
ALTER TABLE
    `AdStmt` ADD CONSTRAINT `adstmt_video_ad_id_foreign` FOREIGN KEY(`video_ad_id`) REFERENCES `VideoAd`(`id`);
ALTER TABLE
    `user_roles` ADD CONSTRAINT `user_roles_user_id_foreign` FOREIGN KEY(`user_id`) REFERENCES `User`(`id`);
ALTER TABLE
    `VideoAd` ADD CONSTRAINT `videoad_ad_id_foreign` FOREIGN KEY(`ad_id`) REFERENCES `Ads`(`id`);
ALTER TABLE
    `VideoStats` ADD CONSTRAINT `videostats_video_id_foreign` FOREIGN KEY(`video_id`) REFERENCES `Video`(`id`);
ALTER TABLE
    `VideoStmt` ADD CONSTRAINT `videostmt_video_id_foreign` FOREIGN KEY(`video_id`) REFERENCES `Video`(`id`);
ALTER TABLE
    `Video` ADD CONSTRAINT `video_user_id_foreign` FOREIGN KEY(`user_id`) REFERENCES `User`(`id`);
ALTER TABLE
    `AdHistory` ADD CONSTRAINT `adhistory_vide_ad_id_foreign` FOREIGN KEY(`vide_ad_id`) REFERENCES `VideoAd`(`id`);