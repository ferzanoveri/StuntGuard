/*
  Warnings:

  - Added the required column `confidenceScore` to the `StuntPredict` table without a default value. This is not possible if the table is not empty.

*/
-- AlterTable
ALTER TABLE `StuntPredict` ADD COLUMN `confidenceScore` DOUBLE NOT NULL;
