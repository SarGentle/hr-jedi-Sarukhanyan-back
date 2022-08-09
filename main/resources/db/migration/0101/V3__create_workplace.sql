CREATE TABLE WORKPLACE
(
    id          NUMBER PRIMARY KEY,
    office_id   NUMBER NOT NULL,
    employee_id NUMBER,
    floor       NUMBER NOT NULL,
    room        NUMBER NOT NULL,
    table_num   NUMBER NOT NULL,

    CONSTRAINT fk_workplace_office FOREIGN KEY (office_id) REFERENCES OFFICE (id),
    CONSTRAINT fk_workplace_employee FOREIGN KEY (employee_id) REFERENCES EMPLOYEE (id)
);
CREATE SEQUENCE sq_workplace_id START WITH 1 INCREMENT BY 1;

INSERT INTO WORKPLACE
VALUES (next value for sq_workplace_id, 2, 5, 7, 711, 7),
       (next value for sq_workplace_id, 2, null, 7, 711, 8),
       (next value for sq_workplace_id, 2, null, 7, 711, 9),
       (next value for sq_workplace_id, 2, null, 7, 711, 10),
       (next value for sq_workplace_id, 2, null, 7, 711, 11),
       (next value for sq_workplace_id, 1, 6, 3, 324, 1),
       (next value for sq_workplace_id, 1, 7, 3, 324, 2),
       (next value for sq_workplace_id, 1, null, 3, 324, 3),
       (next value for sq_workplace_id, 1, null, 3, 324, 4),
       (next value for sq_workplace_id, 1, null, 3, 324, 5);
