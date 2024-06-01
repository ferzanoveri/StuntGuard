-- CreateTable
CREATE TABLE `Child` (
    `child_id` CHAR(16) NOT NULL,
    `parent_id` CHAR(16) NOT NULL,
    `child_name` VARCHAR(255) NOT NULL,
    `child_gender` BOOLEAN NOT NULL,
    `born_date` DATETIME(3) NOT NULL,
    `child_age` INTEGER NOT NULL,
    `born_weight` DOUBLE NOT NULL,
    `born_height` DOUBLE NOT NULL,
    `breastfeeding` BOOLEAN NOT NULL,
    `created_at` TIMESTAMP(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0),
    `updated_at` TIMESTAMP(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0),

    PRIMARY KEY (`child_id`)
) DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

-- CreateTable
CREATE TABLE `Parent` (
    `parent_id` CHAR(16) NOT NULL,
    `parent_name` VARCHAR(255) NOT NULL,
    `parent_gender` BOOLEAN NOT NULL,
    `email` VARCHAR(200) NOT NULL,
    `phone` VARCHAR(13) NOT NULL,
    `password` VARCHAR(255) NOT NULL,
    `created_at` TIMESTAMP(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0),
    `updated_at` TIMESTAMP(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0),

    UNIQUE INDEX `Parent_email_key`(`email`),
    PRIMARY KEY (`parent_id`)
) DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

-- CreateTable
CREATE TABLE `StuntPredict` (
    `predict_id` VARCHAR(8) NOT NULL,
    `child_weight` DOUBLE NOT NULL,
    `child_height` DOUBLE NOT NULL,
    `predict_result` BOOLEAN NOT NULL,
    `child_id` CHAR(16) NOT NULL,
    `created_at` TIMESTAMP(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0),
    `updated_at` TIMESTAMP(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0),

    PRIMARY KEY (`predict_id`)
) DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

-- CreateTable
CREATE TABLE `Recommendation` (
    `recommendation_id` VARCHAR(8) NOT NULL,
    `child_id` CHAR(16) NOT NULL,
    `created_at` TIMESTAMP(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0),
    `updated_at` TIMESTAMP(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0),

    PRIMARY KEY (`recommendation_id`)
) DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

-- CreateTable
CREATE TABLE `FoodDetails` (
    `food_id` CHAR(16) NOT NULL,
    `ingredients` VARCHAR(255) NOT NULL,
    `nutritions` VARCHAR(255) NOT NULL,
    `measure` DOUBLE NOT NULL,
    `recommendation_id` CHAR(16) NOT NULL,
    `created_at` TIMESTAMP(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0),
    `updated_at` TIMESTAMP(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0),

    PRIMARY KEY (`food_id`)
) DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

-- AddForeignKey
ALTER TABLE `Child` ADD CONSTRAINT `Child_parent_id_fkey` FOREIGN KEY (`parent_id`) REFERENCES `Parent`(`parent_id`) ON DELETE RESTRICT ON UPDATE RESTRICT;

-- AddForeignKey
ALTER TABLE `StuntPredict` ADD CONSTRAINT `StuntPredict_child_id_fkey` FOREIGN KEY (`child_id`) REFERENCES `Child`(`child_id`) ON DELETE RESTRICT ON UPDATE RESTRICT;

-- AddForeignKey
ALTER TABLE `Recommendation` ADD CONSTRAINT `Recommendation_child_id_fkey` FOREIGN KEY (`child_id`) REFERENCES `Child`(`child_id`) ON DELETE RESTRICT ON UPDATE RESTRICT;

-- AddForeignKey
ALTER TABLE `FoodDetails` ADD CONSTRAINT `FoodDetails_recommendation_id_fkey` FOREIGN KEY (`recommendation_id`) REFERENCES `Recommendation`(`recommendation_id`) ON DELETE RESTRICT ON UPDATE RESTRICT;
