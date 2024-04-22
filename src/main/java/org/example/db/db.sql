DROP DATABASE IF EXISTS emp_proj;
CREATE DATABASE emp_proj;
USE emp_proj;

CREATE TABLE employee (
	id INT UNSIGNED NOT NULL AUTO_INCREMENT,
	regDate DATETIME NOT NULL,
	dateTimeKey DATETIME NOT NULL,
	employeeId CHAR(100) NOT NULL,
	`password` VARCHAR(100) NOT NULL,
	`name` CHAR(100) NOT NULL,
	department CHAR(10) NOT NULL,
	`position` CHAR(10) NOT NULL,
	PRIMARY KEY (id),
	UNIQUE KEY (employeeId)
);

INSERT INTO employee
SET regDate = NOW(),
dateTimeKey = NOW(),
employeeId = 'user1',
`password` = 'user1',
`name` = '홍길동',
`department` = '개발',
`position` = '주임';

INSERT INTO employee
SET regDate = NOW(),
dateTimeKey = NOW(),
employeeId = 'user2',
`password` = 'user2',
`name` = '홍길순',
`department` = '마케팅',
`position` = '대리';

INSERT INTO employee
SET regDate = NOW(),
dateTimeKey = NOW(),
employeeId = 'user3',
`password` = 'user3',
`name` = '홍길두',
`department` = '인사',
`position` = '과장';

SELECT employeeId AS 'ID',
`name` AS '이름',
`department` AS '부서',
`position` AS '직급'
FROM employee;

CREATE TABLE attendance (
    id INT AUTO_INCREMENT PRIMARY KEY,
    employeeId CHAR(100) NOT NULL,
    checkIn DATETIME,
    checkOut DATETIME,
    FOREIGN KEY (employeeId) REFERENCES employee(employeeId)
);

SELECT * FROM employee;
SELECT * FROM attendance;

#일일 출퇴근 기록 조회
SELECT e.name AS '이름', e.department AS '부서', a.checkIn AS '출근시간', a.checkOut AS '퇴근시간'
FROM employee e
JOIN attendance a ON e.employeeId = a.employeeId
WHERE DATE(a.checkIn) = CURDATE();

#특정 직원의 한 달 간 출퇴근 기록 조회
SELECT e.name AS '이름', a.checkIn AS '출근시간', a.checkOut AS '퇴근시간'
FROM employee e
JOIN attendance a ON e.employeeId = a.employeeId
WHERE e.employeeId = 'user1' AND MONTH(a.checkIn) = MONTH(CURDATE()) AND YEAR(a.checkIn) = YEAR(CURDATE());

#출근하지 않은 직원 목록 조회
SELECT e.name AS '이름', e.department AS '부서'
FROM employee e
WHERE e.employeeId NOT IN (
    SELECT a.employeeId
    FROM attendance a
    WHERE DATE(a.checkIn) = CURDATE()
);

#직원의 최근 7일간 출퇴근 기록 조회
SELECT e.name AS '이름', a.checkIn AS '출근시간', a.checkOut AS '퇴근시간'
FROM employee e
JOIN attendance a ON e.employeeId = a.employeeId
WHERE a.checkIn >= DATE_SUB(CURDATE(), INTERVAL 7 DAY)
ORDER BY a.checkIn DESC;

#출퇴근 기록이 없는 날짜 조회
SELECT DATE(a.checkIn) AS '날짜', COUNT(*) AS '출근한 직원 수'
FROM attendance a
GROUP BY DATE(a.checkIn)
HAVING COUNT(*) < (SELECT COUNT(*) FROM employee);