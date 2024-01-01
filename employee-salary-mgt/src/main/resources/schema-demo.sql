delete from public.payment_info where (user_id, system_time) not in (select user_id, MAX(system_time) from public.payment_info group by user_id) returning *;

ALTER TABLE public.payment_info
RENAME COLUMN payment_info_id TO reg_payment_id;

ALTER TABLE public.payment_info
RENAME COLUMN user_id TO patient_id;

ALTER TABLE public.payment_info
RENAME COLUMN system_time TO created_at;

ALTER TABLE public.payment_info
RENAME TO patient_reg_payment;

update patient_appointment set created_at=updated_at;
