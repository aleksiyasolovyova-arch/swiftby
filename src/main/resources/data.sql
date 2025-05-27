-- Cleaned and ready for Spring auto schema generation
-- Ensure Hibernate generates tables before this is executed

-- Super Admins (hashed passwords)
INSERT INTO super_admin (email, first_name, last_name, password, phone_number) VALUES
                                                                                   ('daniil.mumladze@student.kdg.be', 'Daniil', 'Mumladze', '$2a$12$B2Z5NXujfYW1BbkvuEsAieagqjRfgl7ALG6YP5kklNpdsjrdytn6G', '0495123456'),
                                                                                   ('mohammed-jan.jalloh@student.kdg.be', 'MJ', 'Jalloh', '$2a$12$C0Op6Raw5BmKNOuaFTnO9uPZbH854VCWgY1AhZgPgQACqAdBX92Pm', '0492123456'),
                                                                                   ('bob@helper.com', 'Bob', 'Helper', '$2a$12$0AQwvBMDXS.V72Em2a9ftOLoMeJijLPJ3ynyJVWKAhfIasHPK6Eee', '0476010203'),
                                                                                   ('super@admin.com', 'super', 'admin', '$2a$12$z08GOjaScv45/PJ3AaCWVOJIrB9kyCRvVAWdFJ0U2eMIVistjPOy.', '0889861088');

-- Facilities

INSERT INTO axial_sensor_data (horizontal_inclination, vertical_inclination) VALUES
                                                                                 (15.5, 30.2),
                                                                                 (10.3, 25.7),
                                                                                 (10.3, 25.7),
                                                                                  (12.8, 28.4);

INSERT INTO battery_data (capacity, charge_status, battery_current, temperature, voltage)
VALUES
    (5000, true, 12.5, 25.4, 48.2),
    (6000, false, 10.8, 30.0, 50.1),
    (7000, true, 11.2, 28.5, 49.5);

INSERT INTO facility (address_extra, city, country, email, name, street, street_number, zip_code) VALUES
                                                                                                      ('Unit 5', 'Springfield', 'USA', 'facility@example.com', 'Test Facility', 'Main St', '123', '98765'),
                                                                                                      ('Enter through garage', 'Stroempeltown', 'Belgica', 'storm.vanloon@student.kdg.be', 'StroempelRepairs', 'Stroempelstreet', '86', '2005');
INSERT INTO test_bench (facility_id) values (1);
-- Bike Owners
INSERT INTO bike_owner (email, first_name, last_name, password, phone_number, facility_id) VALUES
                                                                                               ('alice@example.com', 'Alice', 'Doe', '$2a$12$GK/Xh4.LP7OSc/P.85CahOmR8XlzbCGghhNvFXhajvbV8dgcdGpHW', '555-1234', 1),
                                                                                               ('bob@example.com', 'Bob', 'Smith', '$2a$12$NrfMkUFyBjFwTMZaKEvfEucjUPd1rRJRYkL/Sg61oY8dvjdFb.IrO', '555-5678', 1),
                                                                                               ('bikeModel@owner.com', 'Bike', 'Owner', '$2a$12$E2ZsrwqOpaRwgrWTeI4bPOPxzGROAnUjd4/QqzSbjNuu8Je0vS2hK', '0889861088', 1);

-- Technicians
INSERT INTO technician (facility_id, email, first_name, last_name, password, phone_number,is_approved) VALUES
                                                                                               (1, 'farah.bekkal@student.kdg.be', 'Farah', 'Bekkal', '$2a$12$hFfPdsIFV.ixaBmnwmcKr..D54dqnroPodYfnmjVUnFTLHG39IZWi', '01492123456',true),
                                                                                               (2, 'skaara.poncin@student.kdg.be', 'Skaara', 'Poncin', '$2a$12$AKSegUlAcs9t4tgwF4l4CuQ5vfhPmKnozycvaeYRcSLrA5aCOsb9i', '+320497334455',true),
                                                                                               (2, 'yoran.delcroix@student.kdg.be', 'Yoran', 'Delcroix', '$2a$12$GALjP67ZIgeerpOSPhRlyeWYMetlAYKM9ehL3Mxd1gUQFkqePx/Fe', '+320498000000',true),
                                                                                               (2, 'aleksiya.solovyova@student.kdg.be', 'Aleksiya', 'Solovyova', '$2a$12$Fvk0.w09r1tB7C/yX7RyQedlKWbuKAhY8oVXO0EbX8BvxUmFBIFvO', '+320498000000',true),
                                                                                               (2, 'technician@tech.com', 'Tech', 'no', '$2a$12$IQ/UCr3A/9ysim9K9GEohew1ybx7vIAxBgKbTJdVO5ObVD5OlWKyy', '0889861088',true);


-- Administrators
INSERT INTO administrator (is_approved,facility_id, email, first_name, last_name, password, phone_number) VALUES
                                                                                                  (true,1, 'alina.dimova@student.kdg.be', 'Alina', 'Dimova', '$2a$12$2.47mwMkxWpJVa1s/9TnKeMdjsQc5.L9zCO5npqrnzWwwSrdV1jeG', '784-556-778'),
                                                                                                  (true,1, 'aleksiya.solovyova@student.kdg.be', 'Aleksiya', 'Solovyova', '$2a$12$in.yf3xeexORUO4uDeAPZeG9J7lKYBzAWDSY1T7.XlrfJD5VAYk4m', '+359 246 44 88'),
                                                                                                  (true,2, 'storm.vanloon@student.kdg.be', 'Storm', 'van Loon', '$2a$12$fEjdz49TVnoTiiXgSoG/PurTH7rUxLZ8vMofz9hkPD7CAM03.MXme', '0492454545'),
                                                                                                  (true,2, 'bob.thehelper@stroempeltown.be', 'Bob', 'The Helper', '$2a$12$b1kRFuVAHPSEEKUI9UpmwO0tIgYXhlGn9W54e3.WmaHYviJo91Iv.', '0476010203'),
                                                                                                  (true, 1, 'admin@admin.com', 'Admin', 'Jr', '$2a$12$8DZYsusMRABF6Mi07CIx6u.4eTiDfqt8KhYLajndmd6OeGtZlWxKO', '0889861088');

-- BIKE MODELS
INSERT INTO motor (engine_type, gear_type, max_power, nominal_power, torque) VALUES
                                                                                 ('Mid-drive', 'Derailleur', 750, 500, 80),
                                                                                 ('Hub-drive', 'Internal Hub', 500, 250, 50),
                                                                                 ('Mid-drive', 'Belt Drive', 1000, 700, 100);

INSERT INTO bike_model (brand, type, battery_capacity, bike_size, max_support, powertrain, motor_id) VALUES
                                                                                                         ('Gazelle', 'Ultimate C8+', 500, 2, 400, 0, 1),
                                                                                                         ('VanMoof', 'S5', 600, 3, 500, 1, 2),
                                                                                                         ('Specialized', 'Turbo Vado', 700, 2, 600, 0, 3);

-- BIKE INSTANCES
INSERT INTO bike_instance (model_id, chassis_number) VALUES
                                                             ( 1, 'CHSN-GAZ-001'),
                                                             ( 2, 'CHSN-VAN-002'),
                                                             ( 3, 'CHSN-SPE-003'),
                                                             ( 1, 'CHSN-GAZ-004'),
                                                             ( 2, 'CHSN-VAN-005');



INSERT INTO bike_ownerships (bike_id, owner_id) VALUES (1, 1),
                                                            (2, 2),
                                                            (3, 1);

INSERT INTO motor_data (engine, engine_power) VALUES
                                                  (1, 250.5),
                                                  (2, 300.0);

INSERT INTO pedal_data (cadence, torque_crank) VALUES
                                                   (90, 35.5),
                                                   (85, 30.0);

INSERT INTO test_bench (is_active, facility_id) VALUES
                                                    (true, 1),
                                                    (false, 1),
                                                    (true, 2),
                                                    (false, 2),
                                                    (false, 2)
;
INSERT INTO test_bench_data (load_cell, load_power, rol, roller_torque, status_plug, test_bench_id) VALUES
                                                                                                        (10.5, 150, 0.3, 20.4, true, 1),
                                                                                                        (12.0, 200, 0.4, 25.7, false, 2);

INSERT INTO wheel_data (power, speed) VALUES
                                          (200.5, 30.2),
                                          (180.0, 28.7);

INSERT INTO bike_report (assistance_level, mileage, report_time, axial_sensor_data_id, battery_data_id, bike_id, motor_data_id, pedal_data_id, test_bench_data, wheel_data_id, technician_comment) VALUES
                                                                                                                                                                                                       (3, 1200, '2025-02-01', 1, 1, 1, 1, 1, 1, 1, 'Battery performing well'),
                                                                                                                                                                                                       (2, 800, '2025-02-15', 2, 2, 2, 2, 2, 2, 2, 'Motor efficiency could be improved');

INSERT INTO bike_report_summary (
    report_time,
    avg_mileage,
    avg_assistance_level,
    horizontal_inclination,
    vertical_inclination,
    charge_status,
    battery_current,
    voltage,
    capacity,
    temperature,
    engine_type,
    gear_type,
    max_power,
    nominal_power,
    torque,
    torque_crank,
    cadence,
    roller_torque,
    load_cell,
    rol,
    load_power,
    status_plug,
    speed,
    power,
    technician_comment
) VALUES
      ('2025-02-01', 1100, 2.5, 14.0, 29.0, true, 12.3, 48.5, 5200, 26.5, 'Brushless', 'Automatic', 500, 250, 90, 33.2, 88, 18.5, 9.8, 0.32, 155, true, 29.5, 198.0, 'Battery efficiency is good'),
      ('2025-02-15', 900, 2.8, 10.5, 24.0, false, 11.0, 50.2, 6000, 28.0, 'Brushed', 'Manual', 600, 300, 120, 31.5, 82, 22.1, 11.2, 0.28, 165, false, 27.8, 180.5, 'Motor is overheating, check cooling'),
      ( '2025-02-10', 950, 3.0, 12.0, 26.5, true, 12.0, 49.0, 5500, 27.0, 'Brushless', 'Automatic', 500, 250, 100, 34.0, 90, 20.5, 10.5, 0.30, 160, true, 30.2, 200.5, 'Overall performance is stable'),
      ( '2025-02-18', 1050, 3.2, 13.5, 27.5, true, 13.0, 48.8, 5300, 26.8, 'Brushed', 'Manual', 600, 320, 110, 35.0, 85, 19.7, 9.5, 0.35, 170, false, 28.5, 195.3, 'Power output fluctuates under load');
