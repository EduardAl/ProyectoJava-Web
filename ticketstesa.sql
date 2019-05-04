-- phpMyAdmin SQL Dump
-- version 4.8.5
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 04-05-2019 a las 20:17:55
-- Versión del servidor: 10.1.38-MariaDB
-- Versión de PHP: 5.6.40

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `ticketstesa`
--

DELIMITER $$
--
-- Procedimientos
--
CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_delete_department` (IN `idd` VARCHAR(3))  BEGIN
delete from departments where id = idd;
END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_delete_employee` (IN `employee_id` INT)  BEGIN
delete from employees where id = employee_id;
END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_delete_request` (IN `request_id` INT)  BEGIN
if (select count(cases.request) from cases where cases.request = request_id) = 0
then
delete from requests where requests.id = request_id;
END IF;
END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_deny_request` (IN `request_id` INT, IN `commentary` TEXT)  BEGIN
update requests set 
requests.commentary = commentary,
requests.updated_at = now(),
requests.request_status = (select request_status.id from request_status where request_status.rs_name = 'Solicitud rechazada') 
where requests.id = request_id;
END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_insert_binnacle_cases` (IN `idCaso` VARCHAR(8), IN `porcentaje` DECIMAL(5,2), IN `comentario` TEXT)  BEGIN
insert into binnacle values(null, idCaso, comentario, now());
if(porcentaje = 100) then
update cases set percent = porcentaje, case_status = 2, updated_at = now() where id = idCaso;
else
update cases set percent = porcentaje, updated_at = now() where id = idCaso;
end if;

END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_insert_boss_employees` (IN `rol` INT, IN `fname` VARCHAR(250), IN `lname` VARCHAR(250), IN `email` VARCHAR(250), IN `pass` VARCHAR(64), IN `chief` INT, IN `department` VARCHAR(3))  BEGIN
insert into employees values(null, rol, fname, lname, email, sha2(pass, 256), chief, department, now(), null);
END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_insert_department` (IN `id` VARCHAR(3), IN `dname` VARCHAR(250))  BEGIN
	insert into departments values (id,dname);
END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_insert_new_case` (IN `in_department` VARCHAR(250), IN `case_id` VARCHAR(3), IN `request_id` INT, IN `id_assigned_to` INT, IN `in_deadline` TIMESTAMP, IN `in_descript` TEXT)  BEGIN
insert into cases(id,request,assigned_to,case_status,deadline,descrip) 
values (concat((select departments.id from departments where departments.dname = in_department),DATE_FORMAT(now(),'%y'), case_id) ,
		request_id,id_assigned_to,
		(select case_status.id from case_status where case_status.cs_name = 'En desarrollo'),
        in_deadline,in_descript);
update requests set
requests.request_status = (select request_status.id from request_status where request_status.rs_name = 'En desarrollo')
where requests.id = request_id;
END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_insert_new_employee` (IN `rol_in` INT, IN `fname_in` VARCHAR(250), IN `lname_in` VARCHAR(250), IN `email_in` VARCHAR(250), IN `in_chief` INT, IN `department_in` VARCHAR(3))  BEGIN
insert into employees(rol,fname,lname,email,passwd,chief,department,created_at)
values (rol_in,fname_in,lname_in,email_in, sha2('123456',256),in_chief,department_in,now());
END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_insert_request` (IN `request_type` INT, IN `department_in` VARCHAR(255), IN `title` VARCHAR(255), IN `descript` TEXT, IN `created_by` INT, IN `commentary` TEXT)  BEGIN
insert into requests(request_type, department, title, descrip, created_by, request_status, commentary) 
	values (request_type, (select departments.id from departments where departments.dname = department_in)
			,title, descript, created_by
			,(select request_status.id from request_status where request_status.rs_name like 'En espera de respuesta')
			,commentary);
                    
END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_modify_request` (IN `request_id` INT, IN `request_type` INT, IN `title` VARCHAR(255), IN `descript` TEXT, IN `commentary` TEXT)  BEGIN
update requests set 
requests.request_type = request_type,
requests.title = title,
requests.descrip = descript,
requests.commentary = commentary,
requests.updated_at = now(),
requests.request_status = (select request_status.id from request_status where request_status.rs_name = 'En espera de respuesta')
where requests.id = request_id;
END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_re_open_case` (IN `caseId` VARCHAR(8))  BEGIN 
update cases set deadline = ADDDATE(now(), INTERVAL 7 DAY), case_status = 1, updated_at = now() where id = caseId;
END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_select_back_case` (IN `departamento` VARCHAR(250))  BEGIN
select c.id as Id, r.title as Titulo, concat(e.fname, ' ', e.lname) as CreadoPor, concat(e2.fname, ' ', e2.lname) as Asignado, DATE_FORMAT(c.deadline,  '%W - %d - %M - %Y - %h:%i:%s') as Limite, 
c.percent as Avance, DATE_FORMAT(c.updated_at,  '%W - %d - %M - %Y - %h:%i:%s') as UltimoCambio
from cases c 
inner join requests r on r.id = c.request
inner join employees e on r.created_by = e.id
inner join employees e2 on c.assigned_to = e2.id 
where c.case_status = 4 and r.department = (select id from departments where dname = departamento) order by c.created_at desc limit 1; 
END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_select_binnacle_cases` (IN `idCaso` VARCHAR(8))  BEGIN
select b.id, r.title , b.commentary as comentario, DATE_FORMAT(b.created_at,   '%W - %d - %M - %Y - %h:%i:%s') 
from binnacle b 
inner join cases c on b.case_id = c.id
inner join requests r on c.request = r.id
where c.id = idCaso;
END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_select_boss_employees` ()  BEGIN
select employees.id ,concat(employees.fname ,' ' ,employees.lname) as 'Nombre Empleado' from employees
    inner join roles on employees.rol = roles.id
    inner join departments on employees.department = departments.id
    where employees.rol in (select id from roles where rname like 'Jefe%');
END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_select_death_case` (IN `departamento` VARCHAR(250))  BEGIN
select c.id as Id, r.title as Titulo, concat(e.fname, ' ', e.lname) as CreadoPor, concat(e2.fname, ' ', e2.lname) as Asignado, DATE_FORMAT(c.deadline,  '%W - %d - %M - %Y - %h:%i:%s') as Limite, 
c.percent as Avance, DATE_FORMAT(c.updated_at,  '%W - %d - %M - %Y - %h:%i:%s') as UltimoCambio
from cases c 
inner join requests r on r.id = c.request
inner join employees e on r.created_by = e.id
inner join employees e2 on c.assigned_to = e2.id 
where c.case_status = 3 and r.department = (select id from departments where dname = departamento) order by c.created_at desc limit 1; 
END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_select_department` (IN `idd` VARCHAR(3))  BEGIN
SELECT id, dname as departamento from departments where id = idd;
END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_select_departments` ()  BEGIN
SELECT id, dname as departamento from departments;
END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_select_develop_request` (IN `departamento` VARCHAR(250))  BEGIN
SELECT r.id as Id, r.title as Titulo, concat(e.fname, ' ', e.lname) as CreadoPor, DATE_FORMAT(r.updated_at, '%W - &M - %Y - %h:%i:%s') as UltimoCambio
FROM requests r
INNER JOIN employees e on e.id = r.created_by
WHERE r.request_status = 3 and r.department = departamento;
END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_select_employees` ()  BEGIN
select employees.id ,concat(employees.fname ,' ' ,employees.lname) as 'Nombre Empleado', 
	employees.email, roles.rname, departments.dname from employees
    inner join roles on employees.rol = roles.id
    inner join departments on employees.department = departments.id
    where employees.rol <> (select roles.id from roles where roles.rname like ('Administrador'));
END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_select_employees_chief` (IN `id_boss` INT)  BEGIN
select employees.id, concat(employees.fname, ' ', employees.lname) as 'Nombre Empleado' 
from employees where employees.chief = id_boss;
END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_select_finalized_case` (IN `departamento` VARCHAR(250))  BEGIN
select c.id as Id, r.title as Titulo, concat(e.fname, ' ', e.lname) as CreadoPor, concat(e2.fname, ' ', e2.lname) as Asignado, DATE_FORMAT(c.deadline,  '%W - %d - %M - %Y - %h:%i:%s') as Limite, 
c.percent as Avance, DATE_FORMAT(c.updated_at,  '%W - %d - %M - %Y - %h:%i:%s') as UltimoCambio
from cases c 
inner join requests r on r.id = c.request
inner join employees e on r.created_by = e.id
inner join employees e2 on c.assigned_to = e2.id 
where c.case_status = 5 and r.department = (select id from departments where dname = departamento) order by c.created_at desc limit 1; 
END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_select_finalized_request` (IN `departamento` VARCHAR(250))  BEGIN
SELECT r.id as Id, r.title as Titulo, concat(e.fname, ' ', e.lname) as CreadoPor, DATE_FORMAT(r.updated_at, '%W - &M - %Y - %h:%i:%s') as UltimoCambio
FROM requests r
INNER JOIN employees e on e.id = r.created_by
WHERE r.request_status = 4 and r.department = departamento;
END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_select_individual_request` (IN `id` INT, IN `created_by` INT)  BEGIN
select requests.id, request_types.rt_name, departments.dname, requests.title, requests.descrip, 
concat(employees.fname, ' ' , employees.lname) as 'Creado por',
request_status.rs_name, requests.commentary, requests.created_at, requests.updated_at 
from requests inner join request_types on requests.request_type = request_types.id inner join
request_status on requests.request_status = request_status.id inner join
employees on requests.created_by = employees.id inner join 
departments on requests.department = departments.id
where requests.id = id and requests.created_by = created_by;
END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_select_individual_user` (`ID` INT)  BEGIN
SELECT e.id, r.rname as 'Rol', e.fname, e.lname, e.email as 'Correo', concat(ec.fname, ' ', ec.lname) as 'Superior', d.dname as 
'Departamento' from roles r inner join employees e on r.id = e.rol inner join departments d on d.id = e.department inner join employees ec on ec.id = e.chief where 
e.id = 1;
END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_select_latest_cases` (IN `departamento` VARCHAR(250))  BEGIN
select c.id as Id, r.title as Titulo, concat(e.fname, ' ', e.lname) as CreadoPor, concat(e2.fname, ' ', e2.lname) as Asignado,  DATE_FORMAT(c.deadline,  '%W - %d - %M - %Y - %h:%i:%s') as Limite, 
c.percent as Avance, DATE_FORMAT(c.updated_at,'%W - %d - %M - %Y - %h:%i:%s') as UltimoCambio
from cases c 
inner join requests r on r.id = c.request
inner join employees e on r.created_by = e.id
inner join employees e2 on c.assigned_to = e2.id 
where c.case_status = 1 and r.department = (select id from departments where dname = departamento) order by c.created_at limit 4; 
END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_select_refuse_request` (IN `departamento` VARCHAR(250))  BEGIN
SELECT r.id as Id, r.title as Titulo, concat(e.fname, ' ', e.lname) as CreadoPor, DATE_FORMAT(r.updated_at, '%W - &M - %Y - %h:%i:%s') as UltimoCambio
FROM requests r
INNER JOIN employees e on e.id = r.created_by
WHERE r.request_status = 2 and r.department = departamento;
END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_select_request` (IN `created_by_id` INT, IN `department_in` VARCHAR(250))  BEGIN
select requests.id, requests.title, requests.descrip, departments.dname, request_types.rt_name, request_status.rs_name 
from requests inner join departments on requests.department = departments.id 
inner join request_types on requests.request_type = request_types.id
inner join request_status on requests.request_status = request_status.id
where requests.created_by = created_by_id or requests.department = (select departments.id from departments where departments.dname = department_in);
END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_select_roles` ()  BEGIN
SELECT id, rname as rol from roles;
END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_select_single_case` (IN `caseId` VARCHAR(8))  BEGIN
SELECT c.id AS IdCaso, r.title AS Caso, c.descrip AS Descripcion, c.percent AS porcentaje,
concat(t.fname, ' ', t.lname) AS Tester, concat(a.fname, ' ', a.lname) AS Asignado, cs.cs_name AS Estado,
DATE_FORMAT(c.created_at,  '%W - %d - %M - %Y - %h:%i:%s') AS Creado, DATE_FORMAT(c.updated_at,  '%W - %d - %M - %Y - %h:%i:%s')  
AS Modificacion, concat(cp.fname, ' ', cp.lname) as CreadoPor,
DATE_FORMAT(c.deadline,  '%W - %d - %M - %Y - %h:%i:%s') as FechaLimite,
DATE_FORMAT(c.to_production,  '%W - %d - %M - %Y - %h:%i:%s') as Aproduccion from cases c
INNER JOIN requests r ON c.request = r.id 
INNER JOIN employees t ON c.tester = t.id  
INNER JOIN employees a ON c.assigned_to = a.id 
INNER JOIN case_status cs ON c.case_status = cs.id  
INNER JOIN employees cp ON r.created_by = cp.id 
WHERE c.id = caseId
UNION ALL 
SELECT c.id AS IdCaso, r.title AS Caso, c.descrip AS Descripcion, c.percent AS porcentaje, 
null as Tester, concat(a.fname, ' ', a.lname) AS Asignado, cs.cs_name AS Estado,   +
DATE_FORMAT(c.created_at,  '%W - %d - %M - %Y - %h:%i:%s') AS Creado, DATE_FORMAT(c.updated_at,  '%W - %d - %M - %Y - %h:%i:%s')  
AS Modificacion, concat(cp.fname, ' ', cp.lname) as CreadoPor,    +
DATE_FORMAT(c.deadline,  '%W - %d - %M - %Y - %h:%i:%s') as FechaLimite,    +
DATE_FORMAT(c.to_production,  '%W - %d - %M - %Y - %h:%i:%s') as Aproduccion from cases c                   
INNER JOIN requests r ON c.request = r.id  
INNER JOIN employees a ON c.assigned_to = a.id  
INNER JOIN case_status cs ON c.case_status = cs.id  
INNER JOIN employees cp ON r.created_by = cp.id 
WHERE c.id = caseId and c.tester is null ;
END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_select_to_accept_case` (IN `departamento` VARCHAR(250))  BEGIN
select c.id as Id, r.title as Titulo, concat(e.fname, ' ', e.lname) as CreadoPor, concat(e2.fname, ' ', e2.lname) as Asignado, DATE_FORMAT(c.deadline,  '%W - %d - %M - %Y - %h:%i:%s') as Limite, 
c.percent as Avance, DATE_FORMAT(c.updated_at,  '%W - %d - %M - %Y - %h:%i:%s') as UltimoCambio
from cases c 
inner join requests r on r.id = c.request
inner join employees e on r.created_by = e.id
inner join employees e2 on c.assigned_to = e2.id 
where c.case_status = 2 and r.department = (select id from departments where dname = departamento) order by c.created_at desc limit 1; 
END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_select_to_accept_request` (IN `departamento` VARCHAR(250))  BEGIN
SELECT r.id as Id, r.title as Titulo, concat(e.fname, ' ', e.lname) as CreadoPor, DATE_FORMAT(r.updated_at, '%W - &M - %Y - %h:%i:%s') as UltimoCambio
FROM requests r
INNER JOIN employees e on e.id = r.created_by
WHERE r.request_status = 1 and r.department = departamento;
END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_select_user` (IN `email` VARCHAR(250), IN `passwd` VARCHAR(64))  BEGIN
IF ((Select count(*) from employees where employees.email = email and employees.passwd = sha2(passwd, 256)) = 1) THEN
SELECT e.id, r.rname as 'Rol', concat(e.fname, ' ', e.lname) as 'Nombre', e.email as 'Correo', e.chief as 'Superior', d.dname as 
'Departamento', NULL as 'Error' from roles r inner join employees e on r.id = e.rol inner join departments d on d.id = e.department where 
e.email = email and e.passwd = SHA2(passwd, 256); 
ELSEIF ((Select count(*) from employees where employees.email = email) = 0) THEN
SELECT NULL as 'id', NULL as 'Rol', NULL as 'Nombre', NULL as 'Correo', NULL as 'Superior', NULL as 'Departamento', 'No se encontró ningún usuario' as 'Error';
ELSE 
SELECT NULL as 'id', NULL as 'Rol', NULL as 'Nombre', NULL as 'Correo', NULL as 'Superior', NULL as 'Departamento', 'Contraseña incorrecta' as 'Error';
END IF;
END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_select_users` ()  BEGIN
SELECT e.id as 'ID', r.rname as 'Rol', e.id, e.fname as 'Nombres', e.lname as 'Apellidos', e.email as 'Correo', e.chief as 'Superior', 
d.dname as 'Departamento', e.created_at as 'Fecha de creacion', NULL as 'Error' from roles r inner join employees e on r.id = e.rol inner join departments d on d.id = e.department order by e.id;
END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_update_boss_employees` (IN `id` INT, IN `rol` INT, IN `fname` VARCHAR(250), IN `lname` VARCHAR(250), IN `email` VARCHAR(250), IN `pass` VARCHAR(64), IN `department` VARCHAR(3), IN `mod_pass` BOOLEAN, IN `jefe` INT)  BEGIN
IF mod_pass = TRUE
then
update employees set
	employees.rol = rol,
    employees.fname = fname,
    employees.lname = lname,
    employees.email = email,
    employees.passwd = pass,
    employees.department = department,
    employees.passwd = sha2(pass,256),
    employees.updated_at = now(),
    employees.chief = jefe
    where employees.id = id;
else
update employees set
	employees.rol = rol,
    employees.fname = fname,
    employees.lname = lname,
    employees.email = email,
    employees.department = department,
    employees.updated_at = now(),
    employees.chief = jefe
    where employees.id = id;
END IF;
END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_update_cases` (IN `asignado` INT, IN `descrip` TEXT, IN `id` VARCHAR(8))  update cases set cases.assigned_to = asignado, cases.descrip = descrip where cases.id = id$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_update_cases_JAF` (IN `tester` INT, IN `id` VARCHAR(8))  BEGIN
update cases set cases.tester = tester where cases.id = id;
END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_update_department` (IN `id` VARCHAR(3), IN `dname` VARCHAR(250))  BEGIN
 UPDATE departments SET  departments.dname = dname
 where departments.id = id;
END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_view_request_no_description` (IN `department` VARCHAR(3))  BEGIN
select requests.id, requests.title, departments.dname, request_types.rt_name, request_status.rs_name from
requests inner join departments on requests.department = departments.id
inner join request_status on requests.request_status = request_status.id
inner join request_types on requests.request_type = request_types.id
where requests.department = department;
END$$

DELIMITER ;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `binnacle`
--

CREATE TABLE `binnacle` (
  `id` int(11) NOT NULL,
  `case_id` varchar(8) NOT NULL,
  `commentary` text NOT NULL,
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Volcado de datos para la tabla `binnacle`
--

INSERT INTO `binnacle` (`id`, `case_id`, `commentary`, `created_at`) VALUES
(1, 'DVT19945', 'Bitacora', '2019-04-06 20:52:50'),
(2, 'DVT19945', 'klshdvyurhteaid', '2019-04-06 20:53:40');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `cases`
--

CREATE TABLE `cases` (
  `id` varchar(8) NOT NULL,
  `request` int(11) NOT NULL,
  `assigned_to` int(11) NOT NULL,
  `case_status` int(11) NOT NULL,
  `deadline` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `descrip` text NOT NULL,
  `percent` decimal(5,2) NOT NULL DEFAULT '0.00',
  `tester` int(11) DEFAULT NULL,
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NULL DEFAULT NULL,
  `to_production` timestamp NULL DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Volcado de datos para la tabla `cases`
--

INSERT INTO `cases` (`id`, `request`, `assigned_to`, `case_status`, `deadline`, `descrip`, `percent`, `tester`, `created_at`, `updated_at`, `to_production`) VALUES
('DST19238', 1, 7, 3, '2019-04-14 08:19:42', 'Descripción', '0.00', NULL, '2019-04-06 20:19:50', NULL, NULL),
('DVT19945', 2, 13, 2, '2019-04-27 08:51:36', 'Caso aceptado', '100.00', NULL, '2019-04-06 20:51:48', '2019-04-06 20:53:40', NULL);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `case_status`
--

CREATE TABLE `case_status` (
  `id` int(11) NOT NULL,
  `cs_name` varchar(100) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Volcado de datos para la tabla `case_status`
--

INSERT INTO `case_status` (`id`, `cs_name`) VALUES
(1, 'En desarrollo'),
(2, 'Esperando aprobación del área solicitante'),
(3, 'Vencido'),
(4, 'Devuelto con observaciones'),
(5, 'Finalizado');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `departments`
--

CREATE TABLE `departments` (
  `id` varchar(3) NOT NULL,
  `dname` varchar(250) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Volcado de datos para la tabla `departments`
--

INSERT INTO `departments` (`id`, `dname`) VALUES
('DFF', 'Departamento de Facturación Fija'),
('DFM', 'Departamento de Facturación Móvil'),
('DFN', 'Departamento de Finanzas'),
('DST', 'Departamento de Sistemas'),
('DVT', 'Departamento de Ventas');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `employees`
--

CREATE TABLE `employees` (
  `id` int(11) NOT NULL,
  `rol` int(11) NOT NULL,
  `fname` varchar(250) NOT NULL,
  `lname` varchar(250) NOT NULL,
  `email` varchar(250) NOT NULL,
  `passwd` varchar(64) NOT NULL,
  `chief` int(11) DEFAULT NULL,
  `department` varchar(3) NOT NULL,
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Volcado de datos para la tabla `employees`
--

INSERT INTO `employees` (`id`, `rol`, `fname`, `lname`, `email`, `passwd`, `chief`, `department`, `created_at`, `updated_at`) VALUES
(1, 1, 'Eduardo', 'Henríquez', 'eduard_alfons@hotmail.com', '5e884898da28047151d0e56f8dc6292773603d0d6aabbdd62a11ef721d1542d8', 12, 'DFF', '2019-04-06 19:34:58', '2019-05-04 15:47:28'),
(2, 1, 'Eduardo', 'Arevalo', 'jefe', '8d969eef6ecad3c29a3a629280e686cf0c3f5d5a86aff3ca12020c923adc6c92', NULL, 'DST', '2019-04-06 19:34:58', NULL),
(4, 2, 'José', 'Arévalo', 'Jefe1', '8d969eef6ecad3c29a3a629280e686cf0c3f5d5a86aff3ca12020c923adc6c92', NULL, 'DST', '2019-04-06 19:34:58', NULL),
(5, 3, 'José', 'Arévalo', 'EmpleadoFuncional', '0dfa400dc39e0723ba4c5b6336f8144fd7fd60373e0263bd563e4b699300aa85', NULL, 'DST', '2019-04-06 19:34:58', NULL),
(6, 4, 'José', 'Arévalo', 'JefeDesarrollo', '0dfa400dc39e0723ba4c5b6336f8144fd7fd60373e0263bd563e4b699300aa85', NULL, 'DST', '2019-04-06 19:34:58', NULL),
(7, 5, 'José', 'Arévalo 2', 'EmpleadoDesarrollo', '0dfa400dc39e0723ba4c5b6336f8144fd7fd60373e0263bd563e4b699300aa85', 3, 'DST', '2019-04-06 19:34:58', '2019-04-13 00:00:27'),
(8, 2, 'Rodrigo Alejandro', 'Cerón Moreno', 'ceron@proyecto.com', '8d969eef6ecad3c29a3a629280e686cf0c3f5d5a86aff3ca12020c923adc6c92', 6, 'DST', '2019-04-06 20:33:08', '2019-04-06 20:33:08'),
(9, 5, 'edu', 'arevalo', 'arevalo@proyecto.com', '8d969eef6ecad3c29a3a629280e686cf0c3f5d5a86aff3ca12020c923adc6c92', 8, 'DST', '2019-04-06 20:37:01', '2019-04-06 20:37:01'),
(10, 3, 'Juan', 'Pérez', 'perez@proyecto.com', '8d969eef6ecad3c29a3a629280e686cf0c3f5d5a86aff3ca12020c923adc6c92', 8, 'DST', '2019-04-06 20:42:49', '2019-04-06 20:42:49'),
(11, 2, 'maza', 'riego', 'mazariego@proyecto.com', '8d969eef6ecad3c29a3a629280e686cf0c3f5d5a86aff3ca12020c923adc6c92', 6, 'DVT', '2019-04-06 20:46:14', '2019-04-06 20:46:14'),
(12, 4, 'Marvin', 'Martinez', 'vaselinux@proyecto.com', '8d969eef6ecad3c29a3a629280e686cf0c3f5d5a86aff3ca12020c923adc6c92', 6, 'DVT', '2019-04-06 20:49:21', '2019-04-06 20:49:21'),
(13, 5, 'Eduardo', 'Henríquez', 'eduard@proyecto.com', '8d969eef6ecad3c29a3a629280e686cf0c3f5d5a86aff3ca12020c923adc6c92', 12, 'DVT', '2019-04-06 20:51:16', '2019-04-06 20:51:16'),
(14, 1, 'Eduardo', 'Rivera', 'ejemplo@email.com', '8d969eef6ecad3c29a3a629280e686cf0c3f5d5a86aff3ca12020c923adc6c92', 3, 'DFF', '2019-04-12 20:43:11', '2019-04-12 20:43:11'),
(15, 1, 'Eduardo', 'Rivera', 'ejemplo@email.com', '8d969eef6ecad3c29a3a629280e686cf0c3f5d5a86aff3ca12020c923adc6c92', 3, 'DFF', '2019-04-12 20:44:01', '2019-04-12 20:44:01'),
(16, 1, 'Eduardo', 'Rivera', 'ejemplo@email.com', '8d969eef6ecad3c29a3a629280e686cf0c3f5d5a86aff3ca12020c923adc6c92', 3, 'DFF', '2019-04-12 20:44:36', '2019-04-12 20:44:36'),
(17, 1, 'Eduardo', 'Rivera', 'ejemplo@email.com', '8d969eef6ecad3c29a3a629280e686cf0c3f5d5a86aff3ca12020c923adc6c92', 3, 'DFF', '2019-04-12 20:44:39', '2019-04-12 20:44:39'),
(18, 1, 'Eduardo', 'Rivera', 'ejemplo@email.com', '8d969eef6ecad3c29a3a629280e686cf0c3f5d5a86aff3ca12020c923adc6c92', 3, 'DFF', '2019-04-12 20:49:03', '2019-04-12 20:49:03');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `requests`
--

CREATE TABLE `requests` (
  `id` int(11) NOT NULL,
  `request_type` int(11) NOT NULL,
  `department` varchar(3) NOT NULL,
  `title` varchar(255) NOT NULL,
  `descrip` text NOT NULL,
  `created_by` int(11) NOT NULL,
  `request_status` int(11) NOT NULL,
  `commentary` text,
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NULL DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Volcado de datos para la tabla `requests`
--

INSERT INTO `requests` (`id`, `request_type`, `department`, `title`, `descrip`, `created_by`, `request_status`, `commentary`, `created_at`, `updated_at`) VALUES
(1, 1, 'DST', 'Prueba	', 'Descripción', 1, 3, NULL, '2019-04-06 19:35:46', NULL),
(2, 1, 'DVT', 'Prueba demostración	', 'Comentario demostración', 11, 3, NULL, '2019-04-06 20:46:54', NULL);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `request_status`
--

CREATE TABLE `request_status` (
  `id` int(11) NOT NULL,
  `rs_name` varchar(100) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Volcado de datos para la tabla `request_status`
--

INSERT INTO `request_status` (`id`, `rs_name`) VALUES
(1, 'En espera de respuesta'),
(2, 'Solicitud rechazada'),
(3, 'En desarrollo'),
(4, 'Cerrado');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `request_types`
--

CREATE TABLE `request_types` (
  `id` int(11) NOT NULL,
  `rt_name` varchar(100) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Volcado de datos para la tabla `request_types`
--

INSERT INTO `request_types` (`id`, `rt_name`) VALUES
(1, 'Nuevo sistema'),
(2, 'Nueva funcionalidad'),
(3, 'Corrección de sistema');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `roles`
--

CREATE TABLE `roles` (
  `id` int(11) NOT NULL,
  `rname` varchar(40) DEFAULT NULL,
  `descrip` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Volcado de datos para la tabla `roles`
--

INSERT INTO `roles` (`id`, `rname`, `descrip`) VALUES
(1, 'Administrador', 'Con la capacidad de registrar y gestionar áreas funcionales de la empresa (departamentos), jefes de áreas funcionales y jefes de desarrollo.'),
(2, 'Jefe de área funcional', 'Con la capacidad de solicitar la apertura de casos y monitorear el porcentaje de progreso y bitácora de los casos aperturados.'),
(3, 'Empleado de área funcional', 'Pueden ser asignados como probadores de un caso, en tal situación deben monitorear el porcentaje de progreso y bitácoras de trabajo de dicho caso y aprobarlo o rechazarlo una vez que este haya sido entregado por el programador asignado.'),
(4, 'Jefe de desarrollo', 'Tiene la capacidad de aceptar o rechazar las solicitudes de casos realizadas por los jefes de las áreas funcionales que tiene a su cargo. Además, debe tener la capacidad de monitorear el trabajo de los programadores que tiene a su cargo.'),
(5, 'Programador', 'Debe actualizar los porcentajes de progreso y bitácoras de trabajo de los casos a los que ha sido asignado.');

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `binnacle`
--
ALTER TABLE `binnacle`
  ADD PRIMARY KEY (`id`),
  ADD KEY `case_id` (`case_id`);

--
-- Indices de la tabla `cases`
--
ALTER TABLE `cases`
  ADD PRIMARY KEY (`id`),
  ADD KEY `request` (`request`),
  ADD KEY `assigned_to` (`assigned_to`),
  ADD KEY `case_status` (`case_status`),
  ADD KEY `tester` (`tester`);

--
-- Indices de la tabla `case_status`
--
ALTER TABLE `case_status`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `departments`
--
ALTER TABLE `departments`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `employees`
--
ALTER TABLE `employees`
  ADD PRIMARY KEY (`id`),
  ADD KEY `rol` (`rol`),
  ADD KEY `department` (`department`);

--
-- Indices de la tabla `requests`
--
ALTER TABLE `requests`
  ADD PRIMARY KEY (`id`),
  ADD KEY `department` (`department`),
  ADD KEY `request_type` (`request_type`),
  ADD KEY `created_by` (`created_by`),
  ADD KEY `request_status` (`request_status`);

--
-- Indices de la tabla `request_status`
--
ALTER TABLE `request_status`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `request_types`
--
ALTER TABLE `request_types`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `roles`
--
ALTER TABLE `roles`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `binnacle`
--
ALTER TABLE `binnacle`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT de la tabla `case_status`
--
ALTER TABLE `case_status`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT de la tabla `employees`
--
ALTER TABLE `employees`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=21;

--
-- AUTO_INCREMENT de la tabla `requests`
--
ALTER TABLE `requests`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT de la tabla `request_status`
--
ALTER TABLE `request_status`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT de la tabla `request_types`
--
ALTER TABLE `request_types`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT de la tabla `roles`
--
ALTER TABLE `roles`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `binnacle`
--
ALTER TABLE `binnacle`
  ADD CONSTRAINT `binnacle_ibfk_1` FOREIGN KEY (`case_id`) REFERENCES `cases` (`id`) ON DELETE CASCADE;

--
-- Filtros para la tabla `cases`
--
ALTER TABLE `cases`
  ADD CONSTRAINT `cases_ibfk_1` FOREIGN KEY (`request`) REFERENCES `requests` (`id`),
  ADD CONSTRAINT `cases_ibfk_2` FOREIGN KEY (`assigned_to`) REFERENCES `employees` (`id`),
  ADD CONSTRAINT `cases_ibfk_3` FOREIGN KEY (`case_status`) REFERENCES `case_status` (`id`),
  ADD CONSTRAINT `cases_ibfk_4` FOREIGN KEY (`tester`) REFERENCES `employees` (`id`);

--
-- Filtros para la tabla `employees`
--
ALTER TABLE `employees`
  ADD CONSTRAINT `employees_ibfk_1` FOREIGN KEY (`rol`) REFERENCES `roles` (`id`),
  ADD CONSTRAINT `employees_ibfk_2` FOREIGN KEY (`department`) REFERENCES `departments` (`id`);

--
-- Filtros para la tabla `requests`
--
ALTER TABLE `requests`
  ADD CONSTRAINT `requests_ibfk_1` FOREIGN KEY (`department`) REFERENCES `departments` (`id`),
  ADD CONSTRAINT `requests_ibfk_2` FOREIGN KEY (`request_type`) REFERENCES `request_types` (`id`),
  ADD CONSTRAINT `requests_ibfk_3` FOREIGN KEY (`created_by`) REFERENCES `employees` (`id`),
  ADD CONSTRAINT `requests_ibfk_4` FOREIGN KEY (`request_status`) REFERENCES `request_status` (`id`);

DELIMITER $$
--
-- Eventos
--
CREATE DEFINER=`root`@`localhost` EVENT `e_Casos_Vencidos` ON SCHEDULE EVERY 1 MINUTE STARTS '2019-04-06 13:34:59' ON COMPLETION NOT PRESERVE ENABLE DO update cases set case_status = 3 where deadline <= now() and case_status = 1$$

DELIMITER ;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
