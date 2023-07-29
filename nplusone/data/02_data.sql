INSERT INTO address (id, bid, city, street, street_no, created_date, last_modified, version) VALUES (1, 'ADD_0001', 'Nehe', 'Evergreen', 494, '2022-10-16 14:05:54.718206', '2022-10-16 14:05:54.718206', 0);
INSERT INTO address (id, bid, city, street, street_no, created_date, last_modified, version) VALUES (2, 'ADD_0002', 'Shu', 'Shasta', 44726, '2022-10-16 14:05:54.718206', '2022-10-16 14:05:54.718206', 0);
INSERT INTO address (id, bid, city, street, street_no, created_date, last_modified, version) VALUES (3, 'ADD_0003', 'Yanshang', 'Anderson', 31558, '2022-10-16 14:05:54.718206', '2022-10-16 14:05:54.718206', 0);
INSERT INTO address (id, bid, city, street, street_no, created_date, last_modified, version) VALUES (4, 'ADD_0004', 'Zhanghekou', 'Mesta', 195, '2022-10-16 14:05:54.718206', '2022-10-16 14:05:54.718206', 0);
INSERT INTO address (id, bid, city, street, street_no, created_date, last_modified, version) VALUES (5, 'ADD_0005', 'Ryjewo', 'Annamark', 61, '2022-10-16 14:05:54.718206', '2022-10-16 14:05:54.718206', 0);
INSERT INTO address (id, bid, city, street, street_no, created_date, last_modified, version) VALUES (6, 'ADD_0006', 'Bauru', 'Oxford', 978, '2022-10-16 14:05:54.718206', '2022-10-16 14:05:54.718206', 0);

INSERT INTO person (id, bid, name, primary_address_id, created_date, last_modified, version) VALUES (1, 'NA_0001', 'Nevin', 1, '2022-10-16 14:06:03.542698', '2022-10-16 14:06:03.542698', 0);
INSERT INTO person (id, bid, name, primary_address_id, created_date, last_modified, version) VALUES (2, 'NA_0002', 'Vlad', 1, '2022-10-16 14:06:03.542698', '2022-10-16 14:06:03.542698', 0);

INSERT INTO person_addresses (person_id, addresses_id) VALUES (1, 1);
INSERT INTO person_addresses (person_id, addresses_id) VALUES (1, 2);
INSERT INTO person_addresses (person_id, addresses_id) VALUES (1, 3);
INSERT INTO person_addresses (person_id, addresses_id) VALUES (2, 3);
INSERT INTO person_addresses (person_id, addresses_id) VALUES (2, 4);
INSERT INTO person_addresses (person_id, addresses_id) VALUES (2, 5);
INSERT INTO person_addresses (person_id, addresses_id) VALUES (2, 6);
