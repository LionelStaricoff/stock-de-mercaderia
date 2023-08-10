CREATE DATABASE  IF NOT EXISTS `control_de_stock`
-- Table structure for table `categoria`



DROP TABLE IF EXISTS categoria;

CREATE TABLE categoria (
  id int NOT NULL AUTO_INCREMENT,
  nombre varchar(50) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB;


-- Table structure for table `producto`
--

DROP TABLE IF EXISTS producto;

CREATE TABLE producto (
  id int NOT NULL AUTO_INCREMENT,
  nombre varchar(50) NOT NULL,
  descripcion varchar(255) DEFAULT NULL,
  cantidad int NOT NULL DEFAULT '0',
  categoria_id int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `categoria_id` (`categoria_id`),
  CONSTRAINT `producto_ibfk_1` FOREIGN KEY (`categoria_id`) REFERENCES `categoria` (`id`)
) ENGINE=InnoDB;

