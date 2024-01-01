-- INSERT INTO public.role (role_id, role_name)
-- VALUES
-- (1, 'employee'),
-- (2,'sysadmin');

INSERT INTO public.basic_salary(
	basic_salary_id, balance)
	VALUES ('basic-salary-info-0001', 10000);


INSERT INTO public.company_bank_ac(
	com_bank_id, ac_name, ac_number, ac_type, balance, bank, branch)
	VALUES ('com-salary-ac-0001', 'Salary Account', '03637527276', 'current', 100000, 'Agrani Bank', 'Mohammadpur');