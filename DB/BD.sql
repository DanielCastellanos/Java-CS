-- phpMyAdmin SQL Dump
-- version 4.6.5.2
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 22-04-2017 a las 01:19:21
-- Versión del servidor: 10.1.21-MariaDB
-- Versión de PHP: 5.6.30

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `javacs_bd`
--
CREATE DATABASE IF NOT EXISTS `javacs_bd` DEFAULT CHARACTER SET latin1 COLLATE latin1_swedish_ci;
USE `javacs_bd`;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `acceso_pagina`
--

CREATE TABLE `acceso_pagina` (
  `Sesion_idSesion` int(11) NOT NULL,
  `Pagina_idPagina` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `admin`
--

CREATE TABLE `admin` (
  `idAdmin` int(11) NOT NULL,
  `usr_name` varchar(25) NOT NULL,
  `pass` varchar(45) NOT NULL,
  `tipo` tinyint(1) NOT NULL,
  `status` tinyint(1) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `pagina`
--

CREATE TABLE `pagina` (
  `idPagina` int(11) NOT NULL,
  `nombrePagina` varchar(45) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `pc`
--

CREATE TABLE `pc` (
  `idPC` int(11) NOT NULL,
  `modelo` varchar(30) DEFAULT NULL,
  `procesador` varchar(45) DEFAULT NULL,
  `ram` varchar(5) DEFAULT NULL,
  `disco_duro` varchar(7) DEFAULT NULL,
  `no_serie` varchar(30) DEFAULT NULL,
  `os` varchar(30) DEFAULT NULL,
  `mac` varchar(60) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `programa`
--

CREATE TABLE `programa` (
  `idPROGRAMA` int(11) NOT NULL,
  `Nombre` varchar(50) NOT NULL,
  `proceso` varchar(20) NOT NULL,
  `Descripción` varchar(150) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `sesion`
--

CREATE TABLE `sesion` (
  `idSesion` int(11) NOT NULL,
  `entrada` timestamp(6) NULL DEFAULT NULL,
  `salida` timestamp(6) NULL DEFAULT NULL,
  `Usuario_idUsuario` int(11) DEFAULT NULL,
  `PC_idPC` int(11) NOT NULL,
  `Admin_idAdmin` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `uso_pc`
--

CREATE TABLE `uso_pc` (
  `idUSO_PC` int(11) NOT NULL,
  `Encendido` timestamp(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6),
  `apagado` timestamp(6) NULL DEFAULT NULL,
  `PC_idPC` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `uso_programa`
--

CREATE TABLE `uso_programa` (
  `SESION_idSesion` int(11) NOT NULL,
  `PROGRAMA_idPROGRAMA` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `usuario`
--

CREATE TABLE `usuario` (
  `codigo` int(11) NOT NULL,
  `status` tinyint(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `acceso_pagina`
--
ALTER TABLE `acceso_pagina`
  ADD KEY `fk_Acceso_Pagina_Sesion1_idx` (`Sesion_idSesion`),
  ADD KEY `fk_Acceso_Pagina_Pagina1_idx` (`Pagina_idPagina`);

--
-- Indices de la tabla `admin`
--
ALTER TABLE `admin`
  ADD PRIMARY KEY (`idAdmin`);

--
-- Indices de la tabla `pagina`
--
ALTER TABLE `pagina`
  ADD PRIMARY KEY (`idPagina`);

--
-- Indices de la tabla `pc`
--
ALTER TABLE `pc`
  ADD PRIMARY KEY (`idPC`);

--
-- Indices de la tabla `programa`
--
ALTER TABLE `programa`
  ADD PRIMARY KEY (`idPROGRAMA`);

--
-- Indices de la tabla `sesion`
--
ALTER TABLE `sesion`
  ADD PRIMARY KEY (`idSesion`),
  ADD KEY `fk_Sesion_Usuario_idx` (`Usuario_idUsuario`),
  ADD KEY `fk_Sesion_PC1_idx` (`PC_idPC`),
  ADD KEY `fk_Sesion_Admin1_idx` (`Admin_idAdmin`);

--
-- Indices de la tabla `uso_pc`
--
ALTER TABLE `uso_pc`
  ADD PRIMARY KEY (`idUSO_PC`),
  ADD KEY `fk_USO_PC_PC1_idx` (`PC_idPC`);

--
-- Indices de la tabla `uso_programa`
--
ALTER TABLE `uso_programa`
  ADD PRIMARY KEY (`SESION_idSesion`,`PROGRAMA_idPROGRAMA`),
  ADD KEY `fk_SESION_has_PROGRAMA_PROGRAMA1_idx` (`PROGRAMA_idPROGRAMA`),
  ADD KEY `fk_SESION_has_PROGRAMA_SESION1_idx` (`SESION_idSesion`);

--
-- Indices de la tabla `usuario`
--
ALTER TABLE `usuario`
  ADD PRIMARY KEY (`codigo`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `admin`
--
ALTER TABLE `admin`
  MODIFY `idAdmin` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT de la tabla `pagina`
--
ALTER TABLE `pagina`
  MODIFY `idPagina` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT de la tabla `pc`
--
ALTER TABLE `pc`
  MODIFY `idPC` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT de la tabla `programa`
--
ALTER TABLE `programa`
  MODIFY `idPROGRAMA` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT de la tabla `sesion`
--
ALTER TABLE `sesion`
  MODIFY `idSesion` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT de la tabla `uso_pc`
--
ALTER TABLE `uso_pc`
  MODIFY `idUSO_PC` int(11) NOT NULL AUTO_INCREMENT;
--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `acceso_pagina`
--
ALTER TABLE `acceso_pagina`
  ADD CONSTRAINT `fk_Acceso_Pagina_Pagina1` FOREIGN KEY (`Pagina_idPagina`) REFERENCES `pagina` (`idPagina`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `fk_Acceso_Pagina_Sesion1` FOREIGN KEY (`Sesion_idSesion`) REFERENCES `sesion` (`idSesion`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Filtros para la tabla `sesion`
--
ALTER TABLE `sesion`
  ADD CONSTRAINT `fk_Sesion_Admin1` FOREIGN KEY (`Admin_idAdmin`) REFERENCES `admin` (`idAdmin`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `fk_Sesion_PC1` FOREIGN KEY (`PC_idPC`) REFERENCES `pc` (`idPC`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `fk_Sesion_Usuario` FOREIGN KEY (`Usuario_idUsuario`) REFERENCES `usuario` (`codigo`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Filtros para la tabla `uso_pc`
--
ALTER TABLE `uso_pc`
  ADD CONSTRAINT `fk_USO_PC_PC1` FOREIGN KEY (`PC_idPC`) REFERENCES `pc` (`idPC`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Filtros para la tabla `uso_programa`
--
ALTER TABLE `uso_programa`
  ADD CONSTRAINT `fk_SESION_has_PROGRAMA_PROGRAMA1` FOREIGN KEY (`PROGRAMA_idPROGRAMA`) REFERENCES `programa` (`idPROGRAMA`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `fk_SESION_has_PROGRAMA_SESION1` FOREIGN KEY (`SESION_idSesion`) REFERENCES `sesion` (`idSesion`) ON DELETE NO ACTION ON UPDATE NO ACTION;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
