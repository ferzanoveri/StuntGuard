/*
  Warnings:

  - You are about to drop the column `ingredients` on the `FoodDetails` table. All the data in the column will be lost.
  - You are about to drop the column `measure` on the `FoodDetails` table. All the data in the column will be lost.
  - You are about to drop the column `nutritions` on the `FoodDetails` table. All the data in the column will be lost.
  - You are about to drop the column `updated_at` on the `FoodDetails` table. All the data in the column will be lost.
  - Added the required column `calories` to the `FoodDetails` table without a default value. This is not possible if the table is not empty.
  - Added the required column `carbohydrates` to the `FoodDetails` table without a default value. This is not possible if the table is not empty.
  - Added the required column `category` to the `FoodDetails` table without a default value. This is not possible if the table is not empty.
  - Added the required column `cholesterol` to the `FoodDetails` table without a default value. This is not possible if the table is not empty.
  - Added the required column `fat` to the `FoodDetails` table without a default value. This is not possible if the table is not empty.
  - Added the required column `fiber` to the `FoodDetails` table without a default value. This is not possible if the table is not empty.
  - Added the required column `food_name` to the `FoodDetails` table without a default value. This is not possible if the table is not empty.
  - Added the required column `monounsaturated_fat` to the `FoodDetails` table without a default value. This is not possible if the table is not empty.
  - Added the required column `polyunsaturated_fat` to the `FoodDetails` table without a default value. This is not possible if the table is not empty.
  - Added the required column `portion` to the `FoodDetails` table without a default value. This is not possible if the table is not empty.
  - Added the required column `potassium` to the `FoodDetails` table without a default value. This is not possible if the table is not empty.
  - Added the required column `protein` to the `FoodDetails` table without a default value. This is not possible if the table is not empty.
  - Added the required column `saturated_fat` to the `FoodDetails` table without a default value. This is not possible if the table is not empty.
  - Added the required column `sodium` to the `FoodDetails` table without a default value. This is not possible if the table is not empty.
  - Added the required column `sugar` to the `FoodDetails` table without a default value. This is not possible if the table is not empty.
  - Added the required column `unit` to the `FoodDetails` table without a default value. This is not possible if the table is not empty.
  - Added the required column `predict_id` to the `Recommendation` table without a default value. This is not possible if the table is not empty.
  - Added the required column `energy` to the `StuntPredict` table without a default value. This is not possible if the table is not empty.
  - Added the required column `protein` to the `StuntPredict` table without a default value. This is not possible if the table is not empty.

*/
-- AlterTable
ALTER TABLE `Child` ALTER COLUMN `updated_at` DROP DEFAULT;

-- AlterTable
ALTER TABLE `FoodDetails` DROP COLUMN `ingredients`,
    DROP COLUMN `measure`,
    DROP COLUMN `nutritions`,
    DROP COLUMN `updated_at`,
    ADD COLUMN `calories` DOUBLE NOT NULL,
    ADD COLUMN `carbohydrates` DOUBLE NOT NULL,
    ADD COLUMN `category` VARCHAR(255) NOT NULL,
    ADD COLUMN `cholesterol` DOUBLE NOT NULL,
    ADD COLUMN `fat` DOUBLE NOT NULL,
    ADD COLUMN `fiber` DOUBLE NOT NULL,
    ADD COLUMN `food_name` VARCHAR(255) NOT NULL,
    ADD COLUMN `monounsaturated_fat` DOUBLE NOT NULL,
    ADD COLUMN `polyunsaturated_fat` DOUBLE NOT NULL,
    ADD COLUMN `portion` INTEGER NOT NULL,
    ADD COLUMN `potassium` DOUBLE NOT NULL,
    ADD COLUMN `protein` DOUBLE NOT NULL,
    ADD COLUMN `saturated_fat` DOUBLE NOT NULL,
    ADD COLUMN `sodium` DOUBLE NOT NULL,
    ADD COLUMN `sugar` DOUBLE NOT NULL,
    ADD COLUMN `unit` VARCHAR(255) NOT NULL;

-- AlterTable
ALTER TABLE `Parent` ALTER COLUMN `updated_at` DROP DEFAULT;

-- AlterTable
ALTER TABLE `Recommendation` ADD COLUMN `predict_id` VARCHAR(8) NOT NULL,
    ALTER COLUMN `updated_at` DROP DEFAULT;

-- AlterTable
ALTER TABLE `StuntPredict` ADD COLUMN `energy` DOUBLE NOT NULL,
    ADD COLUMN `protein` DOUBLE NOT NULL,
    ALTER COLUMN `updated_at` DROP DEFAULT;

-- AddForeignKey
ALTER TABLE `Recommendation` ADD CONSTRAINT `Recommendation_predict_id_fkey` FOREIGN KEY (`predict_id`) REFERENCES `StuntPredict`(`predict_id`) ON DELETE RESTRICT ON UPDATE RESTRICT;
