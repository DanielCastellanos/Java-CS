-- phpMyAdmin SQL Dump
-- version 4.5.1
-- http://www.phpmyadmin.net
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 09-12-2016 a las 17:00:15
-- Versión del servidor: 10.1.13-MariaDB
-- Versión de PHP: 7.0.8

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `serviciosocial`
--
CREATE DATABASE IF NOT EXISTS `serviciosocial` DEFAULT CHARACTER SET latin1 COLLATE latin1_swedish_ci;
USE `serviciosocial`;

DELIMITER $$
--
-- Procedimientos
--
CREATE DEFINER=`root`@`localhost` PROCEDURE `RegEntrada` (IN `cod` INT)  NO SQL
    DETERMINISTIC
BEGIN
DECLARE salida TIME;
SELECT registro.Hr_Salida into salida 
FROM registro 
WHERE registro.Usuario_Pk_Codigo=cod ORDER BY registro.idRegistro DESC LIMIT 1;
if salida='00:00:00' THEN
 UPDATE registro SET registro.Hr_Salida=CURTIME() WHERE registro.Usuario_Pk_Codigo=cod and registro.Hr_Salida='00:00:00' ;
ELSE
 INSERT INTO registro 
 (registro.Hr_Entrada,registro.Fecha,registro.Usuario_Pk_Codigo) VALUES
 (CURTIME(),CURDATE(),cod);
 END IF;
END$$

DELIMITER ;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `admin`
--

CREATE TABLE `admin` (
  `idAdmin` int(11) NOT NULL,
  `Nombre` varchar(45) NOT NULL,
  `Pass` varchar(80) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `admin`
--

INSERT INTO `admin` (`idAdmin`, `Nombre`, `Pass`) VALUES
(123456789, 'José Guadalupe Ortega Delgado', '202cb962ac59075b964b07152d234b70');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `plaza`
--

CREATE TABLE `plaza` (
  `idPlaza` int(11) NOT NULL,
  `Lugar` varchar(45) NOT NULL,
  `Detalle` varchar(100) NOT NULL,
  `Admin_idAdmin` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `plaza`
--

INSERT INTO `plaza` (`idPlaza`, `Lugar`, `Detalle`, `Admin_idAdmin`) VALUES
(1, 'Laboratorio de Redes', 'Administración y mantenimiento de equipo electrónico', 123456789),
(2, 'CTA', 'asd', 123456789);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `registro`
--

CREATE TABLE `registro` (
  `idRegistro` int(11) NOT NULL,
  `Hr_Entrada` time NOT NULL,
  `Hr_Salida` time NOT NULL,
  `Fecha` date NOT NULL,
  `Usuario_Pk_Codigo` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `registro`
--

INSERT INTO `registro` (`idRegistro`, `Hr_Entrada`, `Hr_Salida`, `Fecha`, `Usuario_Pk_Codigo`) VALUES
(1, '04:00:00', '13:00:00', '2016-12-08', 210436966),
(2, '02:00:00', '07:00:00', '2016-12-15', 210436966),
(3, '04:00:00', '18:16:05', '2016-12-14', 210436966),
(4, '18:21:33', '18:22:00', '2016-12-07', 210436966),
(5, '18:22:17', '18:23:22', '2016-12-07', 210436966),
(6, '21:54:14', '21:54:40', '2016-12-08', 210436885),
(7, '21:56:23', '00:00:00', '2016-12-08', 210436885),
(8, '01:59:23', '00:00:00', '2016-12-09', 210436966);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `usuario`
--

CREATE TABLE `usuario` (
  `Pk_Codigo` int(11) NOT NULL,
  `Nombre` varchar(45) NOT NULL,
  `Pass` varchar(32) NOT NULL,
  `Sexo` char(1) DEFAULT NULL,
  `Fec_Nac` date DEFAULT NULL,
  `Correo` varchar(45) DEFAULT NULL,
  `Semestre` int(2) DEFAULT NULL,
  `Prom_Prev` smallint(1) DEFAULT NULL,
  `Prom_Act` smallint(1) DEFAULT NULL,
  `Plaza_idPlaza` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `usuario`
--

INSERT INTO `usuario` (`Pk_Codigo`, `Nombre`, `Pass`, `Sexo`, `Fec_Nac`, `Correo`, `Semestre`, `Prom_Prev`, `Prom_Act`, `Plaza_idPlaza`) VALUES
(1, 'asd', '7815696ecbf1c96e6894b779456d330e', NULL, '2016-12-06', 'asd@hotmail.com', NULL, NULL, NULL, 2),
(210436857, 'panfilo', '7815696ecbf1c96e6894b779456d330e', NULL, '1995-07-12', 'asd@hotmail.com', NULL, NULL, NULL, 2),
(210436859, 'Panfilo', '7815696ecbf1c96e6894b779456d330e', NULL, '1992-06-23', 'panfilo@hotmail.com', NULL, NULL, NULL, 2),
(210436885, 'Ricardo Avalos Navarro', '81dc9bdb52d04dc20036dbd8313ed055', 'M', '1995-03-08', 'ricardo.avalos@gmail.com', 7, NULL, NULL, 1),
(210436966, 'Alejandro Zuno Flores', '9c4b082ebf2703ed38f3cc9ee15e6462', 'M', '1995-09-25', 'alejandrozuno@gmail.com', 7, 280, 160, 1);

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `admin`
--
ALTER TABLE `admin`
  ADD PRIMARY KEY (`idAdmin`);

--
-- Indices de la tabla `plaza`
--
ALTER TABLE `plaza`
  ADD PRIMARY KEY (`idPlaza`),
  ADD KEY `fk_Plaza_Admin1_idx` (`Admin_idAdmin`);

--
-- Indices de la tabla `registro`
--
ALTER TABLE `registro`
  ADD PRIMARY KEY (`idRegistro`),
  ADD KEY `fk_Registro_Usuario_idx` (`Usuario_Pk_Codigo`);

--
-- Indices de la tabla `usuario`
--
ALTER TABLE `usuario`
  ADD PRIMARY KEY (`Pk_Codigo`),
  ADD UNIQUE KEY `Pk_Codigo_UNIQUE` (`Pk_Codigo`),
  ADD KEY `fk_Usuario_Plaza1_idx` (`Plaza_idPlaza`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `registro`
--
ALTER TABLE `registro`
  MODIFY `idRegistro` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=9;
--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `plaza`
--
ALTER TABLE `plaza`
  ADD CONSTRAINT `fk_Plaza_Admin1` FOREIGN KEY (`Admin_idAdmin`) REFERENCES `admin` (`idAdmin`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Filtros para la tabla `registro`
--
ALTER TABLE `registro`
  ADD CONSTRAINT `fk_Registro_Usuario` FOREIGN KEY (`Usuario_Pk_Codigo`) REFERENCES `usuario` (`Pk_Codigo`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Filtros para la tabla `usuario`
--
ALTER TABLE `usuario`
  ADD CONSTRAINT `fk_Usuario_Plaza1` FOREIGN KEY (`Plaza_idPlaza`) REFERENCES `plaza` (`idPlaza`) ON DELETE NO ACTION ON UPDATE NO ACTION;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
