 select max(salaries.salary), employees.gender from employees join salaries on (employees.emp_no = salaries.emp_no) group by employees.gender;
