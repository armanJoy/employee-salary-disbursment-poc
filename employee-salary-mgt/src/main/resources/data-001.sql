INSERT INTO public.role (role_id, role_name)
VALUES
(1, 'employee'),
(2,'sysadmin');

INSERT INTO public.basic_salary(
	basic_salary_id, balance)
	VALUES ('basic-salary-info-0001', 10000);


INSERT INTO public.company_bank_ac(
	com_bank_id, ac_name, ac_number, ac_type, balance, bank, branch)
	VALUES ('com-salary-ac-0001', 'Salary Account', '03637527276', 'current', 100000, 'Agrani Bank', 'Mohammadpur');

alter table if exists public.user_role 
       add constraint user_role_fk_user_id 
       foreign key (user_id) 
       references public.user_info;

alter table if exists public.user_role 
       add constraint user_role_fk_role_id 
       foreign key (role_id) 
       references public.role;

alter table if exists public.emp_bank 
       add constraint emp_bank_fk_user_id 
       foreign key (user_id) 
       references public.user_info;

alter table if exists public.emp_salary 
       add constraint emp_salary_fk_user_id 
       foreign key (user_id) 
       references public.user_info;