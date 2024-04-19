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

