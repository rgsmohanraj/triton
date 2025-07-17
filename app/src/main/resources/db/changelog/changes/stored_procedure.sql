--liquibase formatted sql
-- liquibase update
--changeset Ajith Rameshkumar:stored_procedure

DELIMITER //
CREATE PROCEDURE view_other_deferral_document_report() BEGIN SELECT d.* FROM (SELECT *, CASE
								WHEN initial_time < curdate() AND revised_time IS NULL THEN '0'
								WHEN initial_time = curdate() AND revised_time IS NULL THEN '1'
								WHEN initial_time = curdate() + INTERVAL 2 DAY AND revised_time IS NULL THEN '2'
								WHEN initial_time = curdate() + INTERVAL 5 DAY AND revised_time IS NULL THEN '5'
								WHEN revised_time = curdate() THEN '0'
								WHEN revised_time = curdate() + INTERVAL 2 DAY THEN '2'
								WHEN revised_time = curdate() + INTERVAL 5 DAY THEN '5'
								WHEN revised_time < curdate() THEN '1'
							END AS data_view  FROM dms_other_document_master WHERE status IN (0,1)  AND deferral_type=1 ) AS d WHERE d.data_view IS NOT NULL; END//
DELIMITER ;


DELIMITER //
CREATE PROCEDURE view_deferral_document_report() BEGIN SELECT d.* FROM (SELECT *, CASE
								WHEN initial_time < curdate() AND revised_time IS NULL THEN '0'
								WHEN initial_time = curdate() AND revised_time IS NULL THEN '1'
								WHEN initial_time = curdate() + INTERVAL 2 DAY AND revised_time IS NULL THEN '2'
								WHEN initial_time = curdate() + INTERVAL 5 DAY AND revised_time IS NULL THEN '5'
								WHEN revised_time = curdate() THEN '0'
								WHEN revised_time = curdate() + INTERVAL 2 DAY THEN '2'
								WHEN revised_time = curdate() + INTERVAL 5 DAY THEN '5'
								WHEN revised_time < curdate() THEN '1'
							END AS data_view  FROM dms_deferral_document_reports WHERE status IN (0,1)) AS d WHERE d.data_view IS NOT NULL; END//
DELIMITER ;
