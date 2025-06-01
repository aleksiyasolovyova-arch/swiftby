-- Cleaned and ready for Spring auto schema generation
-- Ensure Hibernate generates tables before this is executed

-- Super Admins (hashed passwords)
INSERT INTO super_admin (email, first_name, last_name, password, phone_number) VALUES
                                                                                   ('daniil.mumladze@student.kdg.be', 'Daniil', 'Mumladze', '$2a$12$B2Z5NXujfYW1BbkvuEsAieagqjRfgl7ALG6YP5kklNpdsjrdytn6G', '0495123456'),
                                                                                   ('mohammed-jan.jalloh@student.kdg.be', 'MJ', 'Jalloh', '$2a$12$C0Op6Raw5BmKNOuaFTnO9uPZbH854VCWgY1AhZgPgQACqAdBX92Pm', '0492123456'),
                                                                                   ('bob@helper.com', 'Bob', 'Helper', '$2a$12$0AQwvBMDXS.V72Em2a9ftOLoMeJijLPJ3ynyJVWKAhfIasHPK6Eee', '0476010203'),
                                                                                   ('super@admin.com', 'super', 'admin', '$2a$12$z08GOjaScv45/PJ3AaCWVOJIrB9kyCRvVAWdFJ0U2eMIVistjPOy.', '0889861088');

-- Facilities
INSERT INTO facility (address_extra, city, country, email, name, street, street_number, zip_code) VALUES
                                                                                                      ('Unit 5', 'Springfield', 'USA', 'facility@example.com', 'Test Facility', 'Main St', '123', '98765'),
                                                                                                      ('Enter through garage', 'Stroempeltown', 'Belgica', 'storm.vanloon@student.kdg.be', 'StroempelRepairs', 'Stroempelstreet', '86', '2005');
INSERT INTO test_bench (is_active, facility_id) values (false,1),
                                                       (false, 2),
                                                       (true, 1),
                                                       (true, 2);
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
                                                                                               (2, 'technician@tech.com', 'Tech', 'no', '$2a$12$IQ/UCr3A/9ysim9K9GEohew1ybx7vIAxBgKbTJdVO5ObVD5OlWKyy', '0889861088',true),
                                                                                               (1, 'technician2@tech.com', 'Tech', 'no', '$2a$12$IQ/UCr3A/9ysim9K9GEohew1ybx7vIAxBgKbTJdVO5ObVD5OlWKyy', '0889861088',true);


-- Administrators
INSERT INTO administrator (is_approved,facility_id, email, first_name, last_name, password, phone_number) VALUES
                                                                                                  (true,1, 'alina.dimova@student.kdg.be', 'Alina', 'Dimova', '$2a$12$2.47mwMkxWpJVa1s/9TnKeMdjsQc5.L9zCO5npqrnzWwwSrdV1jeG', '784-556-778'),
                                                                                                  (true,1, 'aleksiya.solovyova@student.kdg.be', 'Aleksiya', 'Solovyova', '$2a$12$in.yf3xeexORUO4uDeAPZeG9J7lKYBzAWDSY1T7.XlrfJD5VAYk4m', '+359 246 44 88'),
                                                                                                  (true,2, 'storm.vanloon@student.kdg.be', 'Storm', 'van Loon', '$2a$12$fEjdz49TVnoTiiXgSoG/PurTH7rUxLZ8vMofz9hkPD7CAM03.MXme', '0492454545'),
                                                                                                  (true,2, 'bob.thehelper@stroempeltown.be', 'Bob', 'The Helper', '$2a$12$b1kRFuVAHPSEEKUI9UpmwO0tIgYXhlGn9W54e3.WmaHYviJo91Iv.', '0476010203'),
                                                                                                  (true, 1, 'admin@admin.com', 'Admin', 'Jr', '$2a$12$8DZYsusMRABF6Mi07CIx6u.4eTiDfqt8KhYLajndmd6OeGtZlWxKO', '0889861088'),
                                                                                                  (true, 2, 'admin2@admin.com', 'Admin', 'Jr', '$2a$12$8DZYsusMRABF6Mi07CIx6u.4eTiDfqt8KhYLajndmd6OeGtZlWxKO', '0889861088');

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

-- BIKE OWNERSHIPS (connect to seeded owners with IDs 1–3)
INSERT INTO bike_ownerships ( bike_id, owner_id) VALUES
                                                        ( 1, 1),  -- Alice owns Gazelle
                                                        ( 2, 1),  -- Alice also owns VanMoof
                                                        ( 3, 2),  -- Bob owns Specialized
                                                        ( 4, 2),  -- Bob also owns second Gazelle
                                                        ( 5, 3);  -- bikeModel@owner.com owns VanMoof
