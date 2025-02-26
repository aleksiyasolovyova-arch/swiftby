INSERT INTO super_admin (email, first_name, last_name, password, phone_number) VALUES
                                                                                   ('daniil.mumladze@student.kdg.be', 'Daniil', 'Mumladze', 'password', '0495123456'),
                                                                                   ('mohammed-jan.jalloh@student.kdg.be', 'MJ', 'Jalloh', 'iLoveCookies123', '0492123456');

INSERT INTO axial_sensor_data (horizontal_inclination, vertical_inclination) VALUES
                                                                                 (15.5, 30.2),
                                                                                 (10.3, 25.7),
                                                                                 (12.8, 28.4);

INSERT INTO battery_data (capacity, charge_status, current, temperature, voltage) VALUES
                                                                                      (5000, TRUE, 12.5, 25.4, 48.2),
                                                                                      (6000, FALSE, 10.8, 30.0, 50.1),
                                                                                      (7000, TRUE, 11.2, 28.5, 49.5);

INSERT INTO bike_owner (email, first_name, last_name, password, phone_number) VALUES
                                                                                  ('alice@example.com', 'Alice', 'Doe', 'password123', '555-1234'),
                                                                                  ('bob@example.com', 'Bob', 'Smith', 'securepass', '555-5678');

INSERT INTO facility (address_extra, city, country, email, name, street, street_number, zip_code) VALUES
    ('Unit 5', 'Springfield', 'USA', 'facility@example.com', 'Test Facility', 'Main St', '123', '98765'),
    ('Enter through garage', 'Stroempeltown', 'Belgica', 'storm.vanloon@student.kdg.be', 'StroempelRepairs', 'Stroempelstreet', '86', '2005');

INSERT INTO technician (facility_id, email, first_name, last_name, password, phone_number) VALUES
                                                                                   (1, 'alina.dimova@student.kdg.be', 'Alina', 'Dimova', 'Belgium<3', '01492123456'),
                                                                                   (2, 'skaara.poncin@student.kdg.be', 'Skaara', 'Poncin', 'sk44ra', '+320497334455'),
                                                                                   (2, 'yoran.delcroix@student.kdg.be', 'Yoran', 'Delcroix', 'Breadbreaker', '+320498000000');


INSERT INTO motor (max_power, nominal_power, torque, engine_type, gear_type) VALUES
                                                                                 (500, 250, 100, 'Brushless', 'Automatic'),
                                                                                 (600, 300, 120, 'Brushed', 'Manual');

INSERT INTO bike (battery_capacity, bike_size, max_support, powertrain, motor_id, brand, chassis_number, type) VALUES
                                                                                                                   (5000, 2, 250, 1, 1, 'Trek', 'CHSN12345', 'Mountain'),
                                                                                                                   (6000, 3, 300, 0, 2, 'Giant', 'CHSN67890', 'Road');

INSERT INTO motor_data (engine, engine_power) VALUES
                                                  (1, 250.5),
                                                  (2, 300.0);

INSERT INTO pedal_data (cadence, torque_crank) VALUES
                                                   (90, 35.5),
                                                   (85, 30.0);

INSERT INTO test_bench (is_active, facility_id) VALUES
                                                    (TRUE, 1),
                                                    (FALSE, 1),
                                                    (TRUE, 2),
                                                    (FALSE, 2),
                                                    (FALSE, 2)
;
INSERT INTO test_bench_data (load_cell, load_power, rol, roller_torque, status_plug, test_bench_id) VALUES
                                                                                                        (10.5, 150, 0.3, 20.4, TRUE, 1),
                                                                                                        (12.0, 200, 0.4, 25.7, FALSE, 2);

INSERT INTO wheel_data (power, speed) VALUES
                                          (200.5, 30.2),
                                          (180.0, 28.7);

INSERT INTO bike_report (assistance_level, mileage, report_time, axial_sensor_data_id, battery_data_id, bike_id, motor_data_id, pedal_data_id, test_bench_data, wheel_data_id, technician_comment) VALUES
                                                                                                                                                                                                       (3, 1200, '2025-02-01', 1, 1, 1, 1, 1, 1, 1, 'Battery performing well'),
                                                                                                                                                                                                       (2, 800, '2025-02-15', 2, 2, 2, 2, 2, 2, 2, 'Motor efficiency could be improved');
