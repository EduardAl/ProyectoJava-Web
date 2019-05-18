CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_select_events_dev_boss`()
BEGIN
SELECT ca.created_at, ca.percent , ca.descrip, st.cs_name AS STATUS FROM cases ca
INNER JOIN case_status st ON st.id = ca.case_status 
INNER JOIN requests re ON re.id = ca.request
ORDER BY ca.created_at desc 
LIMIT 10;
END

CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_select_events_functional_boss` (IN `idUsuario` INT)
BEGIN
SELECT ca.created_at, ca.percent , ca.descrip, st.cs_name AS STATUS FROM cases ca
INNER JOIN case_status st ON st.id = ca.case_status 
INNER JOIN requests re ON re.id = ca.request
WHERE re.created_by = idUsuario
ORDER BY ca.created_at desc 
LIMIT 10;
END