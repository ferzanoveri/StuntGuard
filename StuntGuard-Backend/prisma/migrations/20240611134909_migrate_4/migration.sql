/*
  Warnings:

  - Added the required column `note` to the `StuntPredict` table without a default value. This is not possible if the table is not empty.

*/
-- AlterTable
ALTER TABLE `StuntPredict` ADD COLUMN `note` TEXT NOT NULL;
