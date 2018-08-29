-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

-- -----------------------------------------------------
-- Schema periodicals_website
-- -----------------------------------------------------
-- Сайт с периодическими изданиями
-- 
-- Сайт предоставляет доступ к периодическим изданиям, поддерживает регистрацию новых пользователей и хранит информацию о них. Есть две роли: читатель и администратор. У читателей есть баланс (изначально равен 0), который можно пополнять. Читатель может выбрать издание из списка, сформированного на основании типа и темы издания, оставить отзыв на него, оформить подписку, стоимость которой высчитвыается из его баланса. При прерывании подписки часть денежных средств возвращается на баланс.
-- Также система хранит данные об операциях с балансом, чтобы читатель мог просмотреть историю.
-- У изданий есть рейтинг, который высчитывается из оценок отзывов.
-- 
DROP SCHEMA IF EXISTS `periodicals_website` ;

-- -----------------------------------------------------
-- Schema periodicals_website
--
-- Сайт с периодическими изданиями
-- 
-- Сайт предоставляет доступ к периодическим изданиям, поддерживает регистрацию новых пользователей и хранит информацию о них. Есть две роли: читатель и администратор. У читателей есть баланс (изначально равен 0), который можно пополнять. Читатель может выбрать издание из списка, сформированного на основании типа и темы издания, оставить отзыв на него, оформить подписку, стоимость которой высчитвыается из его баланса. При прерывании подписки часть денежных средств возвращается на баланс.
-- Также система хранит данные об операциях с балансом, чтобы читатель мог просмотреть историю.
-- У изданий есть рейтинг, который высчитывается из оценок отзывов.
-- 
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `periodicals_website` DEFAULT CHARACTER SET utf8 ;
USE `periodicals_website` ;

-- -----------------------------------------------------
-- Table `periodicals_website`.`themes`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `periodicals_website`.`themes` (
  `id` SMALLINT(6) NOT NULL AUTO_INCREMENT COMMENT 'ID темы.',
  `default_name` VARCHAR(150) NOT NULL COMMENT 'Название темы',
  PRIMARY KEY (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COMMENT = 'Темы периодических изданий';


-- -----------------------------------------------------
-- Table `periodicals_website`.`types`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `periodicals_website`.`types` (
  `id` SMALLINT(6) NOT NULL AUTO_INCREMENT COMMENT 'ID типа',
  `default_name` VARCHAR(150) NOT NULL COMMENT 'Название типа (газета, журнал)',
  PRIMARY KEY (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `periodicals_website`.`publications`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `periodicals_website`.`publications` (
  `id` INT(11) NOT NULL AUTO_INCREMENT COMMENT 'ID издания',
  `periodicity` VARCHAR(45) NOT NULL COMMENT 'Периодичность (кол-во экземпляров в полугодие)',
  `id_theme` SMALLINT(6) NULL DEFAULT NULL COMMENT 'Тема',
  `id_type` SMALLINT(6) NULL DEFAULT NULL COMMENT 'Тип',
  `price` DECIMAL(8,2) NOT NULL COMMENT 'Цена на подписки на месяц',
  `rating` DECIMAL(3,2) NOT NULL DEFAULT '0.00' COMMENT 'Рейтиг (max=5), изначально 0, высчитывается на основании оценок из отзывов.',
  `picture_path` VARCHAR(100) NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_publications_themes_idx` (`id_theme` ASC),
  INDEX `fk_publications_types_idx` (`id_type` ASC),
  CONSTRAINT `fk_publications_themes`
    FOREIGN KEY (`id_theme`)
    REFERENCES `periodicals_website`.`themes` (`id`)
    ON DELETE RESTRICT
    ON UPDATE CASCADE,
  CONSTRAINT `fk_publications_types`
    FOREIGN KEY (`id_type`)
    REFERENCES `periodicals_website`.`types` (`id`)
    ON DELETE RESTRICT
    ON UPDATE CASCADE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COMMENT = 'Таблица для хранения данных о периодических изданиях';


-- -----------------------------------------------------
-- Table `periodicals_website`.`publications_local`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `periodicals_website`.`publications_local` (
  `id_publication` INT(11) NOT NULL COMMENT 'ID издания',
  `locale` ENUM('RU_BY', 'EN_US') NOT NULL COMMENT 'Локаль',
  `name` VARCHAR(200) NOT NULL COMMENT 'Название издания',
  `description` VARCHAR(2000) NOT NULL COMMENT 'Описание издания',
  PRIMARY KEY (`id_publication`, `locale`),
  CONSTRAINT `fk_puplications_locale_puplications`
    FOREIGN KEY (`id_publication`)
    REFERENCES `periodicals_website`.`publications` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COMMENT = 'Локализованные параметры периодических изданий';


-- -----------------------------------------------------
-- Table `periodicals_website`.`roles`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `periodicals_website`.`roles` (
  `id` TINYINT(4) NOT NULL AUTO_INCREMENT COMMENT 'ID роли',
  `name` VARCHAR(20) NOT NULL COMMENT 'Имя роли',
  PRIMARY KEY (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COMMENT = 'Таблица для ролей пользователей';


-- -----------------------------------------------------
-- Table `periodicals_website`.`users`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `periodicals_website`.`users` (
  `id` INT(11) NOT NULL AUTO_INCREMENT COMMENT 'ID пользователя',
  `login` VARCHAR(50) NOT NULL COMMENT 'Логин. Уникален, может использовать для входа на сайт',
  `password` VARCHAR(200) NOT NULL COMMENT 'Пароль. Будет в хэшированном виде.',
  `name` VARCHAR(45) NOT NULL COMMENT 'Имя пользователя',
  `surname` VARCHAR(45) NOT NULL COMMENT 'Фамилия пользователя',
  `email` VARCHAR(50) NOT NULL COMMENT 'Email. Уникален, может использовать для входа на сайт',
  `balance` DECIMAL(15,2) UNSIGNED NULL DEFAULT '0.00' COMMENT 'Баланс пользователя. Изначально равен нулю. Не может быть отрицательным.',
  `id_role` TINYINT(4) NULL DEFAULT 2 COMMENT 'ID роли пользователя. 1 - для читателей по умолчанию',
  PRIMARY KEY (`id`),
  UNIQUE INDEX `login_UNIQUE` (`login` ASC),
  UNIQUE INDEX `email_UNIQUE` (`email` ASC),
  INDEX `fk_users_roles_idx` (`id_role` ASC),
  CONSTRAINT `fk_users_roles`
    FOREIGN KEY (`id_role`)
    REFERENCES `periodicals_website`.`roles` (`id`)
    ON UPDATE CASCADE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COMMENT = 'Таблица для хранения информации о пользователях';


-- -----------------------------------------------------
-- Table `periodicals_website`.`reviews`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `periodicals_website`.`reviews` (
  `id` INT(11) NOT NULL AUTO_INCREMENT COMMENT 'ID отзыва',
  `id_user` INT(11) NULL DEFAULT NULL COMMENT 'Пользователь который написал отзыв',
  `id_publication` INT(11) NULL DEFAULT NULL COMMENT 'Издание, на которое написан отзыв',
  `date_of_publication` DATETIME NOT NULL COMMENT 'Дата написания',
  `text` VARCHAR(4000) NOT NULL COMMENT 'Тело отзыва',
  `mark` TINYINT(4) NULL DEFAULT '0' COMMENT 'оценка (от 0 до 5)',
  PRIMARY KEY (`id`),
  INDEX `fk_reviews_users_idx` (`id_user` ASC),
  INDEX `fk_reviews_publications_idx` (`id_publication` ASC),
  CONSTRAINT `fk_reviews_publications`
    FOREIGN KEY (`id_publication`)
    REFERENCES `periodicals_website`.`publications` (`id`)
    ON DELETE RESTRICT
    ON UPDATE CASCADE,
  CONSTRAINT `fk_reviews_users`
    FOREIGN KEY (`id_user`)
    REFERENCES `periodicals_website`.`users` (`id`)
    ON DELETE RESTRICT
    ON UPDATE CASCADE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COMMENT = 'Отзывы на издания';


-- -----------------------------------------------------
-- Table `periodicals_website`.`subscriptions`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `periodicals_website`.`subscriptions` (
  `id` INT(11) NOT NULL AUTO_INCREMENT COMMENT 'ID подписки',
  `id_publication` INT(11) NULL DEFAULT NULL COMMENT 'Издание, на которое оформлена подписка',
  `id_user` INT(11) NULL DEFAULT NULL COMMENT 'пользователь, который подписался',
  `start_date` DATE NOT NULL COMMENT 'Начало подписки',
  `end_date` DATE NOT NULL COMMENT 'Окончание подписки',
  `price` DECIMAL(15,2) NOT NULL COMMENT 'Цена',
  `status` ENUM('ACTIVE', 'EXPIRED', 'TERMINATED') NULL DEFAULT 'ACTIVE' COMMENT 'Статус: активная, истекла, прервана',
  PRIMARY KEY (`id`),
  INDEX `fk_subscriptions_publications_idx` (`id_publication` ASC),
  INDEX `fk_subscriptions_users_idx` (`id_user` ASC),
  CONSTRAINT `fk_subscriptions_publications`
    FOREIGN KEY (`id_publication`)
    REFERENCES `periodicals_website`.`publications` (`id`)
    ON DELETE RESTRICT
    ON UPDATE CASCADE,
  CONSTRAINT `fk_subscriptions_users`
    FOREIGN KEY (`id_user`)
    REFERENCES `periodicals_website`.`users` (`id`)
    ON DELETE RESTRICT
    ON UPDATE CASCADE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COMMENT = 'Таблица для хранения информации о подписках';


-- -----------------------------------------------------
-- Table `periodicals_website`.`themes_local`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `periodicals_website`.`themes_local` (
  `id_theme` SMALLINT(6) NOT NULL COMMENT 'ID темы',
  `locale` ENUM('RU_BY', 'EN_US') NOT NULL COMMENT 'Локаль. Русский, английский',
  `name` VARCHAR(150) NOT NULL COMMENT 'Название темы',
  PRIMARY KEY (`id_theme`, `locale`),
  CONSTRAINT `fk_themes_local_themes`
    FOREIGN KEY (`id_theme`)
    REFERENCES `periodicals_website`.`themes` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COMMENT = 'Локализованные названия тем';


-- -----------------------------------------------------
-- Table `periodicals_website`.`balance_operations`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `periodicals_website`.`balance_operations` (
  `id` INT(11) NOT NULL AUTO_INCREMENT COMMENT 'ID операции',
  `id_user` INT(11) NULL DEFAULT NULL COMMENT 'пользователь, с балансом которого производится операция',
  `date` DATETIME NOT NULL COMMENT 'Дата операции',
  `sum` DECIMAL(10,0) NOT NULL COMMENT 'Сумма списания/начисления средств',
  `type` ENUM('PAYMENT_OF_SUBSCRIPTION', 'REFUND', 'BALANCE_REPLENISHMENT') NOT NULL COMMENT 'Тип оперции с балансом: оплата подписки, возврат средств при прерывнии подписки, пополнение баланса',
  PRIMARY KEY (`id`),
  INDEX `fk_transactions_users_idx` (`id_user` ASC),
  CONSTRAINT `fk_transactions_users`
    FOREIGN KEY (`id_user`)
    REFERENCES `periodicals_website`.`users` (`id`)
    ON DELETE RESTRICT
    ON UPDATE CASCADE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COMMENT = 'Таблица для операций с балансом пользователя';


-- -----------------------------------------------------
-- Table `periodicals_website`.`types_local`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `periodicals_website`.`types_local` (
  `id_type` SMALLINT(6) NOT NULL COMMENT 'ID типа',
  `locale` ENUM('RU_BY', 'EN_US') NOT NULL COMMENT 'Локаль. Русский, английский',
  `name` VARCHAR(150) NOT NULL COMMENT 'Название типа',
  PRIMARY KEY (`id_type`, `locale`),
  CONSTRAINT `fk_types_local_types`
    FOREIGN KEY (`id_type`)
    REFERENCES `periodicals_website`.`types` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COMMENT = 'Локализованные названия типов';


-- -----------------------------------------------------
-- Table `periodicals_website`.`issues`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `periodicals_website`.`issues` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `date_of_publication` DATE NOT NULL,
  `publication_id` INT NOT NULL,
  `file` VARCHAR(100) NOT NULL,
  `description` VARCHAR(200) NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_issues_publications_idx` (`publication_id` ASC),
  CONSTRAINT `fk_issues_publications`
    FOREIGN KEY (`publication_id`)
    REFERENCES `periodicals_website`.`publications` (`id`)
    ON DELETE RESTRICT
    ON UPDATE RESTRICT)
ENGINE = InnoDB;

USE `periodicals_website`;

DELIMITER $$
USE `periodicals_website`$$
CREATE DEFINER = CURRENT_USER TRIGGER `periodicals_website`.`reviews_AFTER_INSERT` AFTER INSERT ON `reviews` FOR EACH ROW
BEGIN
Update publications 
SET publications.rating = (SELECT SUM(mark) / COUNT(id) FROM reviews WHERE id_publication = NEW.id_publication)
WHERE publications.id = NEW.id_publication;
END$$

USE `periodicals_website`$$
CREATE DEFINER = CURRENT_USER TRIGGER `periodicals_website`.`reviews_AFTER_UPDATE` AFTER UPDATE ON `reviews` FOR EACH ROW
BEGIN
Update publications 
SET publications.rating = (SELECT SUM(mark) / COUNT(id) FROM reviews WHERE id_publication = OLD.id_publication)
WHERE publications.id = OLD.id_publication;
END$$

USE `periodicals_website`$$
CREATE DEFINER = CURRENT_USER TRIGGER `periodicals_website`.`reviews_AFTER_DELETE` AFTER DELETE ON `reviews` FOR EACH ROW
BEGIN
Update publications 
SET publications.rating = (SELECT SUM(mark) / COUNT(id) FROM reviews WHERE id_publication = OLD.id_publication)
WHERE publications.id = OLD.id_publication;
END$$


DELIMITER ;

SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;

-- -----------------------------------------------------
-- Data for table `periodicals_website`.`themes`
-- -----------------------------------------------------
START TRANSACTION;
USE `periodicals_website`;
INSERT INTO `periodicals_website`.`themes` (`id`, `default_name`) VALUES (1, 'Cars. Transport');
INSERT INTO `periodicals_website`.`themes` (`id`, `default_name`) VALUES (2, 'Health. Medicine');
INSERT INTO `periodicals_website`.`themes` (`id`, `default_name`) VALUES (3, 'Scientific and popular science publications');

COMMIT;


-- -----------------------------------------------------
-- Data for table `periodicals_website`.`types`
-- -----------------------------------------------------
START TRANSACTION;
USE `periodicals_website`;
INSERT INTO `periodicals_website`.`types` (`id`, `default_name`) VALUES (1, 'Newspaper');
INSERT INTO `periodicals_website`.`types` (`id`, `default_name`) VALUES (2, 'Magazine');

COMMIT;


-- -----------------------------------------------------
-- Data for table `periodicals_website`.`publications`
-- -----------------------------------------------------
START TRANSACTION;
USE `periodicals_website`;
INSERT INTO `periodicals_website`.`publications` (`id`, `periodicity`, `id_theme`, `id_type`, `price`, `rating`, `picture_path`) VALUES (63109, '12', 1, 1, 3.18, 4.2, '1756377938.jpg');
INSERT INTO `periodicals_website`.`publications` (`id`, `periodicity`, `id_theme`, `id_type`, `price`, `rating`, `picture_path`) VALUES (63103, '12', 1, 1, 4.07, 3.1, '1756377938.jpg');
INSERT INTO `periodicals_website`.`publications` (`id`, `periodicity`, `id_theme`, `id_type`, `price`, `rating`, `picture_path`) VALUES (63241, '24', 1, 1, 7.16, 2.14, '1756377938.jpg');
INSERT INTO `periodicals_website`.`publications` (`id`, `periodicity`, `id_theme`, `id_type`, `price`, `rating`, `picture_path`) VALUES (64121, '26', 1, 1, 8.31, 3.78, '1756377938.jpg');
INSERT INTO `periodicals_website`.`publications` (`id`, `periodicity`, `id_theme`, `id_type`, `price`, `rating`, `picture_path`) VALUES (01264, '6', 1, 2, 14.32, 4.25, '1756377938.jpg');
INSERT INTO `periodicals_website`.`publications` (`id`, `periodicity`, `id_theme`, `id_type`, `price`, `rating`, `picture_path`) VALUES (00908, '6', 2, 2, 8.1, 0.4, '1756377938.jpg');
INSERT INTO `periodicals_website`.`publications` (`id`, `periodicity`, `id_theme`, `id_type`, `price`, `rating`, `picture_path`) VALUES (00140, '6', 2, 2, 4.89, 4.8, '1756377938.jpg');
INSERT INTO `periodicals_website`.`publications` (`id`, `periodicity`, `id_theme`, `id_type`, `price`, `rating`, `picture_path`) VALUES (74827, '6', 2, 2, 6.36, 1.89, '1756377938.jpg');
INSERT INTO `periodicals_website`.`publications` (`id`, `periodicity`, `id_theme`, `id_type`, `price`, `rating`, `picture_path`) VALUES (63189, '12', 2, 1, 2.64, 2.35, '1756377938.jpg');
INSERT INTO `periodicals_website`.`publications` (`id`, `periodicity`, `id_theme`, `id_type`, `price`, `rating`, `picture_path`) VALUES (63112, '6', 2, 1, 0.19, 5, '1756377938.jpg');
INSERT INTO `periodicals_website`.`publications` (`id`, `periodicity`, `id_theme`, `id_type`, `price`, `rating`, `picture_path`) VALUES (63185, '6', 2, 1, 1, 0.15, '1756377938.jpg');
INSERT INTO `periodicals_website`.`publications` (`id`, `periodicity`, `id_theme`, `id_type`, `price`, `rating`, `picture_path`) VALUES (750332, '6', 3, 2, 6.83, 3.38, '1756377938.jpg');
INSERT INTO `periodicals_website`.`publications` (`id`, `periodicity`, `id_theme`, `id_type`, `price`, `rating`, `picture_path`) VALUES (748842, '6', 3, 2, 3.15, 4.2, '1756377938.jpg');
INSERT INTO `periodicals_website`.`publications` (`id`, `periodicity`, `id_theme`, `id_type`, `price`, `rating`, `picture_path`) VALUES (009152, '6', 3, 2, 6.12, 4.58, '1756377938.jpg');
INSERT INTO `periodicals_website`.`publications` (`id`, `periodicity`, `id_theme`, `id_type`, `price`, `rating`, `picture_path`) VALUES (631722, '12', 3, 1, 1.66, 3, '1756377938.jpg');
INSERT INTO `periodicals_website`.`publications` (`id`, `periodicity`, `id_theme`, `id_type`, `price`, `rating`, `picture_path`) VALUES (631642, '6', 3, 1, 1, 2.75, '1756377938.jpg');
INSERT INTO `periodicals_website`.`publications` (`id`, `periodicity`, `id_theme`, `id_type`, `price`, `rating`, `picture_path`) VALUES (633152, '24', 3, 1, 4.28, 1.98, '1756377938.jpg');

COMMIT;


-- -----------------------------------------------------
-- Data for table `periodicals_website`.`publications_local`
-- -----------------------------------------------------
START TRANSACTION;
USE `periodicals_website`;
INSERT INTO `periodicals_website`.`publications_local` (`id_publication`, `locale`, `name`, `description`) VALUES (63109, 'EN_US', 'Autodigest', '\"Autodigest\" - autoitalia Belarus');
INSERT INTO `periodicals_website`.`publications_local` (`id_publication`, `locale`, `name`, `description`) VALUES (63109, 'RU_BY', 'Автодайджест', '\"Автодайджест\" - автоиздание Беларуси');
INSERT INTO `periodicals_website`.`publications_local` (`id_publication`, `locale`, `name`, `description`) VALUES (63103, 'EN_US', 'The World of heavy engines', 'The newspaper contains timely and reliable information concerning international road transport: news, events, facts, relevant interviews, problems and their solution, customs rules, licensing system, conditions of travel on the territories of foreign countries, as well as published comments to new legislation, economic information, reviews of domestic and foreign market of road transport, the procedure for the delivery of goods in Europe, find a place in the newspaper sports news.');
INSERT INTO `periodicals_website`.`publications_local` (`id_publication`, `locale`, `name`, `description`) VALUES (63103, 'RU_BY', 'Мир тяжелых моторов', 'В газете размещается оперативная и достоверная информация, касающаяся международных автоперевозок: новости, события, факты, актуальные интервью, проблемы и их решение, таможенные правила, разрешительная система, условия проезда по территориям иностранных государств, а также публикуются комментарии к новым законодательным актам, экономическая информация, обзоры отечественного и зарубежного рынка автоперевозок, порядок осуществления доставки грузов в странах Европы, находят место в газете спортивные новости');
INSERT INTO `periodicals_website`.`publications_local` (`id_publication`, `locale`, `name`, `description`) VALUES (63241, 'EN_US', 'Transport security', 'The newspaper contains timely and reliable information concerning international road transport: news, events, facts, relevant interviews, problems and their solution, customs rules, licensing system, conditions of travel on the territories of foreign countries, as well as published comments to new legislation, economic information, reviews of domestic and foreign market of road transport, the procedure for the delivery of goods in Europe, find a place in the newspaper sports news.');
INSERT INTO `periodicals_website`.`publications_local` (`id_publication`, `locale`, `name`, `description`) VALUES (63241, 'RU_BY', 'Транспортная безопасность', 'В газете размещается оперативная и достоверная информация, касающаяся международных автоперевозок: новости, события, факты, актуальные интервью, проблемы и их решение, таможенные правила, разрешительная система, условия проезда по территориям иностранных государств, а также публикуются комментарии к новым законодательным актам, экономическая информация, обзоры отечественного и зарубежного рынка автоперевозок, порядок осуществления доставки грузов в странах Европы, находят место в газете спортивные новости');
INSERT INTO `periodicals_website`.`publications_local` (`id_publication`, `locale`, `name`, `description`) VALUES (64121, 'EN_US', 'Transport Bulletin', 'The newspaper contains timely and reliable information concerning international road transport: news, events, facts, relevant interviews, problems and their solution, customs rules, licensing system, conditions of travel on the territories of foreign countries, as well as published comments to new legislation, economic information, reviews of domestic and foreign market of road transport, the procedure for the delivery of goods in Europe, find a place in the newspaper sports news.');
INSERT INTO `periodicals_website`.`publications_local` (`id_publication`, `locale`, `name`, `description`) VALUES (64121, 'RU_BY', 'Транспортный вестник', 'В газете размещается оперативная и достоверная информация, касающаяся международных автоперевозок: новости, события, факты, актуальные интервью, проблемы и их решение, таможенные правила, разрешительная система, условия проезда по территориям иностранных государств, а также публикуются комментарии к новым законодательным актам, экономическая информация, обзоры отечественного и зарубежного рынка автоперевозок, порядок осуществления доставки грузов в странах Европы, находят место в газете спортивные новости');
INSERT INTO `periodicals_website`.`publications_local` (`id_publication`, `locale`, `name`, `description`) VALUES (01264, 'EN_US', 'Belgut Herald: science and transport', 'The journal publishes the results of completed and previously unpublished research of priority nature. The journal is designed for researchers, faculty, graduate students, a wide range of specialists.');
INSERT INTO `periodicals_website`.`publications_local` (`id_publication`, `locale`, `name`, `description`) VALUES (01264, 'RU_BY', 'Вестник БелГУТа: наука и транспорт', 'В журнале публикуются результаты завершенных и не опубликованных ранее научных исследований, имеющих приоритетный характер. Журнал рассчитан на научных сотрудников, профессорско-преподавательский состав, аспирантов, широкий круг специалистов.');
INSERT INTO `periodicals_website`.`publications_local` (`id_publication`, `locale`, `name`, `description`) VALUES (00908, 'EN_US', 'Veterinary business', 'The journal continues to consecrate industry news, new guidelines and guidelines for determining the quality of animal products, new methods of diagnosis, treatment and prevention, to provide comments to regulatory and departmental documents, veterinary rules and regulations, information on veterinary and sanitary examination of products and much more.');
INSERT INTO `periodicals_website`.`publications_local` (`id_publication`, `locale`, `name`, `description`) VALUES (00908, 'RU_BY', 'Ветеринарное дело', 'Журнал продолжает освящать новости отрасли, новые указания и методические рекомендации по определению качества продукции животноводства, новые методы диагностики, лечения и профилактики, давать комментарии специалистов к нормативным и ведомственным документам, ветеринарные правила и нормы, информацию о ветеринарно-санитарной экспертизе продукции и многое другое.');
INSERT INTO `periodicals_website`.`publications_local` (`id_publication`, `locale`, `name`, `description`) VALUES (00140, 'EN_US', 'Journal of pharmacy', 'The journal continues to consecrate industry news, new guidelines and guidelines for determining the quality of animal products, new methods of diagnosis, treatment and prevention, to provide comments to regulatory and departmental documents, veterinary rules and regulations, information on veterinary and sanitary examination of products and much more.');
INSERT INTO `periodicals_website`.`publications_local` (`id_publication`, `locale`, `name`, `description`) VALUES (00140, 'RU_BY', 'Вестник фармации', 'Журнал продолжает освящать новости отрасли, новые указания и методические рекомендации по определению качества продукции животноводства, новые методы диагностики, лечения и профилактики, давать комментарии специалистов к нормативным и ведомственным документам, ветеринарные правила и нормы, информацию о ветеринарно-санитарной экспертизе продукции и многое другое.');
INSERT INTO `periodicals_website`.`publications_local` (`id_publication`, `locale`, `name`, `description`) VALUES (74827, 'EN_US', 'Military medicine', 'The oldest domestic periodical devoted to the issues of medicine, it is published since January 1823.');
INSERT INTO `periodicals_website`.`publications_local` (`id_publication`, `locale`, `name`, `description`) VALUES (74827, 'RU_BY', 'Военная медицина', 'Старейшее отечественное периодическое издание, посвященное вопросам медицины, он выходит с января 1823 г.');
INSERT INTO `periodicals_website`.`publications_local` (`id_publication`, `locale`, `name`, `description`) VALUES (63189, 'EN_US', 'Your health', 'The Republican popular science newspaper \"Your health\", founded in 1994, is a modern publication enjoying the authority and trust of the population of Belarus and a number of other CIS countries.');
INSERT INTO `periodicals_website`.`publications_local` (`id_publication`, `locale`, `name`, `description`) VALUES (63189, 'RU_BY', 'Ваше здоровье', ' Республиканская   научно-популярная газета «Ваше здоровье», основанная в 1994 году — современное издание, пользующееся авторитетом и доверием населения Беларуси, ряда других стран СНГ.');
INSERT INTO `periodicals_website`.`publications_local` (`id_publication`, `locale`, `name`, `description`) VALUES (63112, 'EN_US', 'We will live to a hundred', '\"We will live to a hundred\" - a monthly newspaper for those who dream of a long life and good health.');
INSERT INTO `periodicals_website`.`publications_local` (`id_publication`, `locale`, `name`, `description`) VALUES (63112, 'RU_BY', 'Доживём до ста', '«Доживём до ста» – ежемесячная газета для тех, кто мечтает о долгой жизни и крепком здоровье.');
INSERT INTO `periodicals_website`.`publications_local` (`id_publication`, `locale`, `name`, `description`) VALUES (63185, 'EN_US', 'Health plus', 'A monthly newspaper for those who dream of a long life and good health.');
INSERT INTO `periodicals_website`.`publications_local` (`id_publication`, `locale`, `name`, `description`) VALUES (63185, 'RU_BY', 'Здоровье плюс', 'Ежемесячная газета для тех, кто мечтает о долгой жизни и крепком здоровье.');
INSERT INTO `periodicals_website`.`publications_local` (`id_publication`, `locale`, `name`, `description`) VALUES (750332, 'EN_US', 'Agrarian economy', 'The magazine \"agricultural Economics\" publishes articles on the Economics of agricultural production, as well as materials informing about the latest technologies and developments of domestic and foreign scientists in various sectors of agriculture, the state policy of the Republic of Belarus in this area.');
INSERT INTO `periodicals_website`.`publications_local` (`id_publication`, `locale`, `name`, `description`) VALUES (750332, 'RU_BY', 'Аграрная экономика', 'В журнале \"Аграрная экономика\" публикуются статьи по вопросам экономики агропромышленного производства, а также материалы, информирующие о новейших технологиях и разработках отечественных и зарубежных ученых в различных отраслях сельского хозяйства, о государственной политике Республики Беларусь в этой сфере.');
INSERT INTO `periodicals_website`.`publications_local` (`id_publication`, `locale`, `name`, `description`) VALUES (748842, 'EN_US', 'Agropanorama', '\"Agropanorama\" is a scientific and technical journal for employees of agro-industrial complex. By the order of the Chairman of the Higher attestation Commission of the Republic of Belarus of July 4, 2005 № 101 (as amended by the order of the VAK of 2.02.2011 № 26) included in the List of scientific publications of the Republic of Belarus for the publication of the results of dissertations in technical Sciences (agricultural engineering and energy, technical service in agriculture), economic (agribusiness), agricultural (zootechnics).');
INSERT INTO `periodicals_website`.`publications_local` (`id_publication`, `locale`, `name`, `description`) VALUES (748842, 'RU_BY', 'Агропанорама', '«Агропанорама» - научно-технический журнал для работников агропромышленного комплекса. Приказом председателя Высшей аттестационной комиссии Республики Беларусь от 4 июля 2005 г. № 101 (в редакции приказа ВАК от 2.02.2011 г. № 26) включен в Перечень научных изданий Республики Беларусь для опубликования результатов диссертационных исследований по техническим наукам (сельскохозяйственное машиностроение и энергетика, технический сервис в АПК), экономическим (АПК), сельскохозяйственным (зоотехния).');
INSERT INTO `periodicals_website`.`publications_local` (`id_publication`, `locale`, `name`, `description`) VALUES (009152, 'EN_US', 'Roads and bridges', 'The scientific and technical journal \"Highways and bridges\" is intended for information support of professional activities of specialists in the field of roads and should contribute to the solution of the task facing road builders today not only to maintain the existing network of roads with minimal financial and material and energy costs, but also to create conditions for the further dynamic development of the national economy, , implementation of social policy of the state and further increase of business activity of the population.');
INSERT INTO `periodicals_website`.`publications_local` (`id_publication`, `locale`, `name`, `description`) VALUES (009152, 'RU_BY', 'Автомобильные дороги и мосты', 'Научно-технический журнал «Автомобильные дороги и мосты» предназначен для информационной поддержки профессиональной деятельности специалистов в области автомобильных дорог и должен способствовать решению стоящей сегодня перед дорожниками задачи не только сохранить сложившуюся сеть автомобильных дорог с минимальными финансовыми и материально-энергетическими затратами, но и создать условия для дальнейшего динамичного развития национальной экономики, обеспечения безопасности и обороноспособности страны, реализации социальной политики государства и дальнейшего повышения деловой активности населения.');
INSERT INTO `periodicals_website`.`publications_local` (`id_publication`, `locale`, `name`, `description`) VALUES (631722, 'EN_US', 'Analytical newspaper \" Secret research\"', 'Popular science publication about the unknown, the secrets of history and the mysteries of the Universe.');
INSERT INTO `periodicals_website`.`publications_local` (`id_publication`, `locale`, `name`, `description`) VALUES (631722, 'RU_BY', 'Аналитическая газета \"Секретные исследования\"', 'Научно-популярное издание о непознанном, тайнах истории и загадках Мироздания.');
INSERT INTO `periodicals_website`.`publications_local` (`id_publication`, `locale`, `name`, `description`) VALUES (631642, 'EN_US', 'Version', 'Popular science publication about the unknown, the secrets of history and the mysteries of the Universe.');
INSERT INTO `periodicals_website`.`publications_local` (`id_publication`, `locale`, `name`, `description`) VALUES (631642, 'RU_BY', 'Версия', 'Научно-популярное издание о непознанном, тайнах истории и загадках Мироздания.');
INSERT INTO `periodicals_website`.`publications_local` (`id_publication`, `locale`, `name`, `description`) VALUES (633152, 'EN_US', 'Science', 'Today, the focus of the thematic focus of the newspaper \"Navuka\" is focused on the coverage of the state policy in the field of scientific and technological activities in our country, innovative development, international scientific and technical cooperation, research conducted in academic and sectoral scientific organizations, as well as educational institutions in priority areas of science, history of Belarusian science. In addition, emphasis is placed on publications about the personalities of the scientific community (researcher-contemporary, his views, scientific authority, contribution to science and national economy), the role of youth in the development of modern science. It provides operational information about the events and the results of the organizations and structural units of the NAS of Belarus, as well as highlights the issues of scientific and industrial sphere, commercialization of scientific ideas, the effectiveness of the scientific potential of the country.');
INSERT INTO `periodicals_website`.`publications_local` (`id_publication`, `locale`, `name`, `description`) VALUES (633152, 'RU_BY', 'Наука', 'Сегодня основное внимание в тематической направленности газеты «Навука» сконцентрировано на освещении государственной политики в области научной и научно-технической деятельности в нашей стране, инновационного развития, международного научно-технического сотрудничества, исследований, проводимых в академических и отраслевых научных организациях, а также учреждениях образования по приоритетным направлениям развития науки, истории белорусской науки. Кроме того, делаются акценты на публикациях о персоналиях научного сообщества (исследователь-современник, его взгляды, научный авторитет, вклад в науку и народное хозяйство), о роли молодежи в развитии современной науки. Подается оперативная информация о состоявшихся мероприятиях и результатах деятельности организаций и структурных подразделений НАН Беларуси, а также освещаются вопросы научно-промышленной сферы, коммерциализации научных идей, эффективности использования научного потенциала страны.');

COMMIT;


-- -----------------------------------------------------
-- Data for table `periodicals_website`.`roles`
-- -----------------------------------------------------
START TRANSACTION;
USE `periodicals_website`;
INSERT INTO `periodicals_website`.`roles` (`id`, `name`) VALUES (1, 'ADMIN');
INSERT INTO `periodicals_website`.`roles` (`id`, `name`) VALUES (2, 'CUSTOMER');

COMMIT;


-- -----------------------------------------------------
-- Data for table `periodicals_website`.`users`
-- -----------------------------------------------------
START TRANSACTION;
USE `periodicals_website`;
INSERT INTO `periodicals_website`.`users` (`id`, `login`, `password`, `name`, `surname`, `email`, `balance`, `id_role`) VALUES (1, 'admin', 'c7ad44cbad762a5da0a452f9e854fdc1e0e7a52a38015f23f3eab1d80b931dd472634dfac71cd34ebc35d16ab7fb8a90c81f975113d6c7538dc69dd8de9077ec', 'Admin', 'Adminovich', 'admin@admin.com', 0, 1);
INSERT INTO `periodicals_website`.`users` (`id`, `login`, `password`, `name`, `surname`, `email`, `balance`, `id_role`) VALUES (2, 'user1', '3627909a29c31381a071ec27f7c9ca97726182aed29a7ddd2e54353322cfb30abb9e3a6df2ac2c20fe23436311d678564d0c8d305930575f60e2d3d048184d79', 'User1', 'Surname1', 'user1@user.com', 0, 2);
INSERT INTO `periodicals_website`.`users` (`id`, `login`, `password`, `name`, `surname`, `email`, `balance`, `id_role`) VALUES (3, 'user2', '3627909a29c31381a071ec27f7c9ca97726182aed29a7ddd2e54353322cfb30abb9e3a6df2ac2c20fe23436311d678564d0c8d305930575f60e2d3d048184d79', 'User2', 'Surname2', 'user2@user.com', 0, 2);
INSERT INTO `periodicals_website`.`users` (`id`, `login`, `password`, `name`, `surname`, `email`, `balance`, `id_role`) VALUES (4, 'user3', '3627909a29c31381a071ec27f7c9ca97726182aed29a7ddd2e54353322cfb30abb9e3a6df2ac2c20fe23436311d678564d0c8d305930575f60e2d3d048184d79', 'User3', 'Surname3', 'user3@user.com', 0, 2);
INSERT INTO `periodicals_website`.`users` (`id`, `login`, `password`, `name`, `surname`, `email`, `balance`, `id_role`) VALUES (5, 'user4', '3627909a29c31381a071ec27f7c9ca97726182aed29a7ddd2e54353322cfb30abb9e3a6df2ac2c20fe23436311d678564d0c8d305930575f60e2d3d048184d79', 'User4', 'Surname4', 'user4@user.com', 0, 2);
INSERT INTO `periodicals_website`.`users` (`id`, `login`, `password`, `name`, `surname`, `email`, `balance`, `id_role`) VALUES (6, 'VladSV', '3627909a29c31381a071ec27f7c9ca97726182aed29a7ddd2e54353322cfb30abb9e3a6df2ac2c20fe23436311d678564d0c8d305930575f60e2d3d048184d79', 'Vladislav', 'Sevashko', 'vlad@gmail.com', 0, 2);

COMMIT;


-- -----------------------------------------------------
-- Data for table `periodicals_website`.`reviews`
-- -----------------------------------------------------
START TRANSACTION;
USE `periodicals_website`;
INSERT INTO `periodicals_website`.`reviews` (`id`, `id_user`, `id_publication`, `date_of_publication`, `text`, `mark`) VALUES (DEFAULT, 6, 63109, '2018-03-01 14:24:38', 'Прежде всего, граница обучения кадров, а также свежий взгляд на привычные вещи - безусловно открывает новые горизонты для как самодостаточных, так и внешне зависимых концептуальных решений. В рамках спецификации современных стандартов, тщательные исследования конкурентов неоднозначны и будут обнародованы. Господа, экономическая повестка сегодняшнего дня предопределяет высокую востребованность системы обучения кадров, соответствующей насущным потребностям.', 1);
INSERT INTO `periodicals_website`.`reviews` (`id`, `id_user`, `id_publication`, `date_of_publication`, `text`, `mark`) VALUES (DEFAULT, 6, 64121, '2018-03-01 14:24:38', 'Прежде всего, граница обучения кадров, а также свежий взгляд на привычные вещи - безусловно открывает новые горизонты для как самодостаточных, так и внешне зависимых концептуальных решений. В рамках спецификации современных стандартов, тщательные исследования конкурентов неоднозначны и будут обнародованы. Господа, экономическая повестка сегодняшнего дня предопределяет высокую востребованность системы обучения кадров, соответствующей насущным потребностям.', 2);
INSERT INTO `periodicals_website`.`reviews` (`id`, `id_user`, `id_publication`, `date_of_publication`, `text`, `mark`) VALUES (DEFAULT, 6, 63109, '2018-03-01 14:24:38', 'Прежде всего, граница обучения кадров, а также свежий взгляд на привычные вещи - безусловно открывает новые горизонты для как самодостаточных, так и внешне зависимых концептуальных решений. В рамках спецификации современных стандартов, тщательные исследования конкурентов неоднозначны и будут обнародованы. Господа, экономическая повестка сегодняшнего дня предопределяет высокую востребованность системы обучения кадров, соответствующей насущным потребностям.', 3);
INSERT INTO `periodicals_website`.`reviews` (`id`, `id_user`, `id_publication`, `date_of_publication`, `text`, `mark`) VALUES (DEFAULT, 6, 00908, '2018-03-01 14:24:38', 'Прежде всего, граница обучения кадров, а также свежий взгляд на привычные вещи - безусловно открывает новые горизонты для как самодостаточных, так и внешне зависимых концептуальных решений. В рамках спецификации современных стандартов, тщательные исследования конкурентов неоднозначны и будут обнародованы. Господа, экономическая повестка сегодняшнего дня предопределяет высокую востребованность системы обучения кадров, соответствующей насущным потребностям.', 4);
INSERT INTO `periodicals_website`.`reviews` (`id`, `id_user`, `id_publication`, `date_of_publication`, `text`, `mark`) VALUES (DEFAULT, 2, 00908, '2018-03-01 14:24:38', 'Прежде всего, граница обучения кадров, а также свежий взгляд на привычные вещи - безусловно открывает новые горизонты для как самодостаточных, так и внешне зависимых концептуальных решений. В рамках спецификации современных стандартов, тщательные исследования конкурентов неоднозначны и будут обнародованы. Господа, экономическая повестка сегодняшнего дня предопределяет высокую востребованность системы обучения кадров, соответствующей насущным потребностям.', 5);
INSERT INTO `periodicals_website`.`reviews` (`id`, `id_user`, `id_publication`, `date_of_publication`, `text`, `mark`) VALUES (DEFAULT, 6, 63109, '2018-03-01 14:24:38', 'Прежде всего, граница обучения кадров, а также свежий взгляд на привычные вещи - безусловно открывает новые горизонты для как самодостаточных, так и внешне зависимых концептуальных решений. В рамках спецификации современных стандартов, тщательные исследования конкурентов неоднозначны и будут обнародованы. Господа, экономическая повестка сегодняшнего дня предопределяет высокую востребованность системы обучения кадров, соответствующей насущным потребностям.', 1);
INSERT INTO `periodicals_website`.`reviews` (`id`, `id_user`, `id_publication`, `date_of_publication`, `text`, `mark`) VALUES (DEFAULT, 4, 64121, '2018-03-01 14:24:38', 'Прежде всего, граница обучения кадров, а также свежий взгляд на привычные вещи - безусловно открывает новые горизонты для как самодостаточных, так и внешне зависимых концептуальных решений. В рамках спецификации современных стандартов, тщательные исследования конкурентов неоднозначны и будут обнародованы. Господа, экономическая повестка сегодняшнего дня предопределяет высокую востребованность системы обучения кадров, соответствующей насущным потребностям.', 2);
INSERT INTO `periodicals_website`.`reviews` (`id`, `id_user`, `id_publication`, `date_of_publication`, `text`, `mark`) VALUES (DEFAULT, 5, 63109, '2018-03-01 14:24:38', 'Прежде всего, граница обучения кадров, а также свежий взгляд на привычные вещи - безусловно открывает новые горизонты для как самодостаточных, так и внешне зависимых концептуальных решений. В рамках спецификации современных стандартов, тщательные исследования конкурентов неоднозначны и будут обнародованы. Господа, экономическая повестка сегодняшнего дня предопределяет высокую востребованность системы обучения кадров, соответствующей насущным потребностям.', 3);
INSERT INTO `periodicals_website`.`reviews` (`id`, `id_user`, `id_publication`, `date_of_publication`, `text`, `mark`) VALUES (DEFAULT, 2, 631722, '2018-03-01 14:24:38', 'Прежде всего, граница обучения кадров, а также свежий взгляд на привычные вещи - безусловно открывает новые горизонты для как самодостаточных, так и внешне зависимых концептуальных решений. В рамках спецификации современных стандартов, тщательные исследования конкурентов неоднозначны и будут обнародованы. Господа, экономическая повестка сегодняшнего дня предопределяет высокую востребованность системы обучения кадров, соответствующей насущным потребностям.', 4);
INSERT INTO `periodicals_website`.`reviews` (`id`, `id_user`, `id_publication`, `date_of_publication`, `text`, `mark`) VALUES (DEFAULT, 4, 748842, '2018-03-01 14:24:38', 'Прежде всего, граница обучения кадров, а также свежий взгляд на привычные вещи - безусловно открывает новые горизонты для как самодостаточных, так и внешне зависимых концептуальных решений. В рамках спецификации современных стандартов, тщательные исследования конкурентов неоднозначны и будут обнародованы. Господа, экономическая повестка сегодняшнего дня предопределяет высокую востребованность системы обучения кадров, соответствующей насущным потребностям.', 5);
INSERT INTO `periodicals_website`.`reviews` (`id`, `id_user`, `id_publication`, `date_of_publication`, `text`, `mark`) VALUES (DEFAULT, 5, 63185, '2018-03-01 14:24:38', 'Прежде всего, граница обучения кадров, а также свежий взгляд на привычные вещи - безусловно открывает новые горизонты для как самодостаточных, так и внешне зависимых концептуальных решений. В рамках спецификации современных стандартов, тщательные исследования конкурентов неоднозначны и будут обнародованы. Господа, экономическая повестка сегодняшнего дня предопределяет высокую востребованность системы обучения кадров, соответствующей насущным потребностям.', 1);
INSERT INTO `periodicals_website`.`reviews` (`id`, `id_user`, `id_publication`, `date_of_publication`, `text`, `mark`) VALUES (DEFAULT, 3, 63185, '2018-03-01 14:24:38', 'Прежде всего, граница обучения кадров, а также свежий взгляд на привычные вещи - безусловно открывает новые горизонты для как самодостаточных, так и внешне зависимых концептуальных решений. В рамках спецификации современных стандартов, тщательные исследования конкурентов неоднозначны и будут обнародованы. Господа, экономическая повестка сегодняшнего дня предопределяет высокую востребованность системы обучения кадров, соответствующей насущным потребностям.', 2);
INSERT INTO `periodicals_website`.`reviews` (`id`, `id_user`, `id_publication`, `date_of_publication`, `text`, `mark`) VALUES (DEFAULT, 3, 63185, '2018-03-01 14:24:38', 'Прежде всего, граница обучения кадров, а также свежий взгляд на привычные вещи - безусловно открывает новые горизонты для как самодостаточных, так и внешне зависимых концептуальных решений. В рамках спецификации современных стандартов, тщательные исследования конкурентов неоднозначны и будут обнародованы. Господа, экономическая повестка сегодняшнего дня предопределяет высокую востребованность системы обучения кадров, соответствующей насущным потребностям.', 3);
INSERT INTO `periodicals_website`.`reviews` (`id`, `id_user`, `id_publication`, `date_of_publication`, `text`, `mark`) VALUES (DEFAULT, 3, 63112, '2018-03-01 14:24:38', 'Прежде всего, граница обучения кадров, а также свежий взгляд на привычные вещи - безусловно открывает новые горизонты для как самодостаточных, так и внешне зависимых концептуальных решений. В рамках спецификации современных стандартов, тщательные исследования конкурентов неоднозначны и будут обнародованы. Господа, экономическая повестка сегодняшнего дня предопределяет высокую востребованность системы обучения кадров, соответствующей насущным потребностям.', 4);
INSERT INTO `periodicals_website`.`reviews` (`id`, `id_user`, `id_publication`, `date_of_publication`, `text`, `mark`) VALUES (DEFAULT, 5, 64121, '2018-03-01 14:24:38', 'Прежде всего, граница обучения кадров, а также свежий взгляд на привычные вещи - безусловно открывает новые горизонты для как самодостаточных, так и внешне зависимых концептуальных решений. В рамках спецификации современных стандартов, тщательные исследования конкурентов неоднозначны и будут обнародованы. Господа, экономическая повестка сегодняшнего дня предопределяет высокую востребованность системы обучения кадров, соответствующей насущным потребностям.', 5);
INSERT INTO `periodicals_website`.`reviews` (`id`, `id_user`, `id_publication`, `date_of_publication`, `text`, `mark`) VALUES (DEFAULT, 6, 63103, '2018-03-01 14:24:38', 'Прежде всего, граница обучения кадров, а также свежий взгляд на привычные вещи - безусловно открывает новые горизонты для как самодостаточных, так и внешне зависимых концептуальных решений. В рамках спецификации современных стандартов, тщательные исследования конкурентов неоднозначны и будут обнародованы. Господа, экономическая повестка сегодняшнего дня предопределяет высокую востребованность системы обучения кадров, соответствующей насущным потребностям.', 5);

COMMIT;


-- -----------------------------------------------------
-- Data for table `periodicals_website`.`subscriptions`
-- -----------------------------------------------------
START TRANSACTION;
USE `periodicals_website`;
INSERT INTO `periodicals_website`.`subscriptions` (`id`, `id_publication`, `id_user`, `start_date`, `end_date`, `price`, `status`) VALUES (1, 750332, 6, '2018-01-01', '2018-12-31', 48.48, 'ACTIVE');
INSERT INTO `periodicals_website`.`subscriptions` (`id`, `id_publication`, `id_user`, `start_date`, `end_date`, `price`, `status`) VALUES (2, 009152, 6, '2018-01-01', '2018-12-31', 98.24, 'ACTIVE');
INSERT INTO `periodicals_website`.`subscriptions` (`id`, `id_publication`, `id_user`, `start_date`, `end_date`, `price`, `status`) VALUES (3, 63112, 6, '2018-01-01', '2018-05-31', 125.45, 'EXPIRED');
INSERT INTO `periodicals_website`.`subscriptions` (`id`, `id_publication`, `id_user`, `start_date`, `end_date`, `price`, `status`) VALUES (4, 63103, 6, '2018-01-01', '2018-05-31', 78.11, 'EXPIRED');
INSERT INTO `periodicals_website`.`subscriptions` (`id`, `id_publication`, `id_user`, `start_date`, `end_date`, `price`, `status`) VALUES (5, 00908, 6, '2018-01-01', '2018-12-31', 458.63, 'TERMINATED');
INSERT INTO `periodicals_website`.`subscriptions` (`id`, `id_publication`, `id_user`, `start_date`, `end_date`, `price`, `status`) VALUES (6, 631642, 6, '2018-01-01', '2018-12-31', 68.4, 'TERMINATED');
INSERT INTO `periodicals_website`.`subscriptions` (`id`, `id_publication`, `id_user`, `start_date`, `end_date`, `price`, `status`) VALUES (7, 633152, 2, '2018-01-01', '2018-12-31', 58.98, 'ACTIVE');
INSERT INTO `periodicals_website`.`subscriptions` (`id`, `id_publication`, `id_user`, `start_date`, `end_date`, `price`, `status`) VALUES (8, 63103, 3, '2018-01-01', '2018-05-31', 18.1, 'EXPIRED');
INSERT INTO `periodicals_website`.`subscriptions` (`id`, `id_publication`, `id_user`, `start_date`, `end_date`, `price`, `status`) VALUES (9, 63109, 4, '2018-01-01', '2018-12-31', 168.48, 'TERMINATED');
INSERT INTO `periodicals_website`.`subscriptions` (`id`, `id_publication`, `id_user`, `start_date`, `end_date`, `price`, `status`) VALUES (10, 00908, 5, '2018-01-01', '2018-12-31', 465, 'ACTIVE');
INSERT INTO `periodicals_website`.`subscriptions` (`id`, `id_publication`, `id_user`, `start_date`, `end_date`, `price`, `status`) VALUES (11, 63112, 2, '2018-01-01', '2018-05-31', 15.48, 'EXPIRED');
INSERT INTO `periodicals_website`.`subscriptions` (`id`, `id_publication`, `id_user`, `start_date`, `end_date`, `price`, `status`) VALUES (12, 63185, 3, '2018-01-01', '2018-12-31', 544.98, 'TERMINATED');
INSERT INTO `periodicals_website`.`subscriptions` (`id`, `id_publication`, `id_user`, `start_date`, `end_date`, `price`, `status`) VALUES (13, 63241, 4, '2018-01-01', '2018-12-31', 25.25, 'ACTIVE');
INSERT INTO `periodicals_website`.`subscriptions` (`id`, `id_publication`, `id_user`, `start_date`, `end_date`, `price`, `status`) VALUES (14, 74827, 5, '2018-01-01', '2018-05-31', 63.2, 'EXPIRED');
INSERT INTO `periodicals_website`.`subscriptions` (`id`, `id_publication`, `id_user`, `start_date`, `end_date`, `price`, `status`) VALUES (15, 64121, 2, '2018-01-01', '2018-12-31', 638.5, 'TERMINATED');

COMMIT;


-- -----------------------------------------------------
-- Data for table `periodicals_website`.`themes_local`
-- -----------------------------------------------------
START TRANSACTION;
USE `periodicals_website`;
INSERT INTO `periodicals_website`.`themes_local` (`id_theme`, `locale`, `name`) VALUES (1, 'EN_US', 'Cars. Transport');
INSERT INTO `periodicals_website`.`themes_local` (`id_theme`, `locale`, `name`) VALUES (1, 'RU_BY', 'Автомобили. Транспорт');
INSERT INTO `periodicals_website`.`themes_local` (`id_theme`, `locale`, `name`) VALUES (2, 'EN_US', 'Health. Medicine');
INSERT INTO `periodicals_website`.`themes_local` (`id_theme`, `locale`, `name`) VALUES (2, 'RU_BY', 'Здравоохранение. Медицина');
INSERT INTO `periodicals_website`.`themes_local` (`id_theme`, `locale`, `name`) VALUES (3, 'EN_US', 'Scientific and popular science publications');
INSERT INTO `periodicals_website`.`themes_local` (`id_theme`, `locale`, `name`) VALUES (3, 'RU_BY', 'Научные и научно-популярные издания');

COMMIT;


-- -----------------------------------------------------
-- Data for table `periodicals_website`.`balance_operations`
-- -----------------------------------------------------
START TRANSACTION;
USE `periodicals_website`;
INSERT INTO `periodicals_website`.`balance_operations` (`id`, `id_user`, `date`, `sum`, `type`) VALUES (1, 6, '2018-05-01 12:12:12', 45.4, 'PAYMENT_OF_SUBSCRIPTION\', \'REFUND');
INSERT INTO `periodicals_website`.`balance_operations` (`id`, `id_user`, `date`, `sum`, `type`) VALUES (2, 6, '2018-05-01 12:12:12', 465, 'BALANCE_REPLENISHMENT');
INSERT INTO `periodicals_website`.`balance_operations` (`id`, `id_user`, `date`, `sum`, `type`) VALUES (3, 2, '2018-05-01 12:12:12', 13.2, 'PAYMENT_OF_SUBSCRIPTION\', \'REFUND');
INSERT INTO `periodicals_website`.`balance_operations` (`id`, `id_user`, `date`, `sum`, `type`) VALUES (4, 3, '2018-05-01 12:12:12', 454.1, 'REFUND');
INSERT INTO `periodicals_website`.`balance_operations` (`id`, `id_user`, `date`, `sum`, `type`) VALUES (5, 4, '2018-05-01 12:12:12', 12.51, 'BALANCE_REPLENISHMENT');
INSERT INTO `periodicals_website`.`balance_operations` (`id`, `id_user`, `date`, `sum`, `type`) VALUES (6, 5, '2018-05-01 12:12:12', 18.12, 'PAYMENT_OF_SUBSCRIPTION\', \'REFUND');
INSERT INTO `periodicals_website`.`balance_operations` (`id`, `id_user`, `date`, `sum`, `type`) VALUES (7, 5, '2018-05-01 12:12:12', 146, 'BALANCE_REPLENISHMENT');
INSERT INTO `periodicals_website`.`balance_operations` (`id`, `id_user`, `date`, `sum`, `type`) VALUES (8, 2, '2018-05-01 12:12:12', 12.25, 'PAYMENT_OF_SUBSCRIPTION\', \'REFUND');
INSERT INTO `periodicals_website`.`balance_operations` (`id`, `id_user`, `date`, `sum`, `type`) VALUES (9, 1, '2018-05-01 12:12:12', 145, 'REFUND');
INSERT INTO `periodicals_website`.`balance_operations` (`id`, `id_user`, `date`, `sum`, `type`) VALUES (10, 6, '2018-05-01 12:12:12', 12.5, 'PAYMENT_OF_SUBSCRIPTION\', \'REFUND');
INSERT INTO `periodicals_website`.`balance_operations` (`id`, `id_user`, `date`, `sum`, `type`) VALUES (11, 3, '2018-05-01 12:12:12', 98.63, 'REFUND');

COMMIT;


-- -----------------------------------------------------
-- Data for table `periodicals_website`.`types_local`
-- -----------------------------------------------------
START TRANSACTION;
USE `periodicals_website`;
INSERT INTO `periodicals_website`.`types_local` (`id_type`, `locale`, `name`) VALUES (1, 'EN_US', 'Newspaper');
INSERT INTO `periodicals_website`.`types_local` (`id_type`, `locale`, `name`) VALUES (1, 'RU_BY', 'Газета');
INSERT INTO `periodicals_website`.`types_local` (`id_type`, `locale`, `name`) VALUES (2, 'EN_US', 'Magazine');
INSERT INTO `periodicals_website`.`types_local` (`id_type`, `locale`, `name`) VALUES (2, 'RU_BY', 'Журнал');

COMMIT;


-- -----------------------------------------------------
-- Data for table `periodicals_website`.`issues`
-- -----------------------------------------------------
START TRANSACTION;
USE `periodicals_website`;
INSERT INTO `periodicals_website`.`issues` (`id`, `date_of_publication`, `publication_id`, `file`, `description`) VALUES (1, '2018-01-01', 750332, '750332/2018/Хакер 2018 01-02(227).pdf', 'Выпуск №227');
INSERT INTO `periodicals_website`.`issues` (`id`, `date_of_publication`, `publication_id`, `file`, `description`) VALUES (2, '2018-01-02', 750332, '750332/2018/Хакер 2018 03(228).pdf', 'Выпуск №228');
INSERT INTO `periodicals_website`.`issues` (`id`, `date_of_publication`, `publication_id`, `file`, `description`) VALUES (3, '2018-01-03', 750332, '750332/2018/Хакер 2018 04(229).pdf', 'Выпуск №229');
INSERT INTO `periodicals_website`.`issues` (`id`, `date_of_publication`, `publication_id`, `file`, `description`) VALUES (4, '2018-01-04', 750332, '750332/2018/Хакер 2018 01-05(230).pdf', 'Выпуск №230');
INSERT INTO `periodicals_website`.`issues` (`id`, `date_of_publication`, `publication_id`, `file`, `description`) VALUES (5, '2018-01-05', 750332, '750332/2018/Хакер 2018 06(231).pdf', 'Выпуск №231');

COMMIT;

