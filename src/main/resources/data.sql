

insert into facility(id, name, email, country, city, zip_code, street, street_number, address_extra)
values
(1, 'StroempelRepairs', 'storm.vanloon@student.kdg.be', 'Belgica', 'Merksplas', '2330', 'Stroempelbaan', '32', 'go through garage'),
(2, 'DaniilFix', 'daniil.mumladze@student.kdg.be', 'Ukraine', 'Idk', 'Idk2', 'Daniilstreet', '4', null);

insert into test_bench (id, is_active, facility_id)
values
    (1, true, 1),
    (2, false, 1),
    (3, true, 2);