-- MySQL Script generated by MySQL Workbench
-- Mon Feb 20 09:33:01 2023
-- Model: New Model    Version: 1.0
-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `mydb` DEFAULT CHARACTER SET utf8 ;
USE `mydb` ;

-- -----------------------------------------------------
-- Table `mydb`.`tb_user`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`tb_user` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(255) NOT NULL,
  `email` VARCHAR(255) NOT NULL,
  `senha` VARCHAR(255) NOT NULL,
  `role` VARCHAR(255) NOT NULL,
  `cpf` VARCHAR(255) NULL,
  `address` VARCHAR(255) NULL,
  `cnpj` VARCHAR(255) NULL,
  `brand` VARCHAR(255) NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`tb_tag`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`tb_tag` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(255) NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`tb_product`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`tb_product` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(255) NOT NULL,
  `price` DECIMAL(8,2) NOT NULL,
  `available_quantity` INT NULL,
  `tb_tag_id` BIGINT NOT NULL,
  `tb_user_id` BIGINT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_tb_product_tb_tag_idx` (`tb_tag_id` ASC) VISIBLE,
  INDEX `fk_tb_product_tb_user1_idx` (`tb_user_id` ASC) VISIBLE,
  CONSTRAINT `fk_tb_product_tb_tag`
    FOREIGN KEY (`tb_tag_id`)
    REFERENCES `mydb`.`tb_tag` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_tb_product_tb_user1`
    FOREIGN KEY (`tb_user_id`)
    REFERENCES `mydb`.`tb_user` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`tb_purschase`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`tb_purschase` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `quantity_purchased` VARCHAR(255) NOT NULL,
  `payment_method` VARCHAR(255) NOT NULL,
  `tb_user_id` BIGINT NOT NULL,
  `tb_product_id` BIGINT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_tb_purschase_tb_user1_idx` (`tb_user_id` ASC) VISIBLE,
  INDEX `fk_tb_purschase_tb_product1_idx` (`tb_product_id` ASC) VISIBLE,
  CONSTRAINT `fk_tb_purschase_tb_user1`
    FOREIGN KEY (`tb_user_id`)
    REFERENCES `mydb`.`tb_user` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_tb_purschase_tb_product1`
    FOREIGN KEY (`tb_product_id`)
    REFERENCES `mydb`.`tb_product` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;