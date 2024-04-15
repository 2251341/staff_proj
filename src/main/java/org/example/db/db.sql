DROP DATABASE IF EXISTS emp_proj;
CREATE DATABASE emp_proj;
USE emp_proj;

CREATE TABLE employees (
	id INT(10) UNSIGNED NOT NULL KEY AUTO_INCREMENT,
	loginId CHAR(100) NOT NULL,
	`password` VARCHAR(100) NOT NULL,
	`name` CHAR(100) NOT NULL,
	department CHAR(10) NOT NULL,
	`position` CHAR(10) NOT NULL
);

INSERT INTO employees
SET loginId = 'user1',
`password` = 'user1',
`name` = '홍길동',
`department` = '개발',
`position` = '주임';

INSERT INTO employees
SET loginId = 'user2',
`password` = 'user2',
`name` = '홍길순',
`department` = '마케팅',
`position` = '대리';

INSERT INTO employees
SET loginId = 'user3',
`password` = 'user3',
`name` = '홍길두',
`department` = '인사',
`position` = '과장';

SELECT * FROM employees;