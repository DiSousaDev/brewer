INSERT INTO estilo (nome) VALUES
('Amber Lager'),
('Dark Lager'),
('Pale Lager'),
('Pilsner'),
('American Lager'),
('Pale Ale'),
('IPA'),
('Stout'),
('Vienna Lager'),
('Weiss'),
('Porter'),
('Witbier'),
('Tripel');

INSERT INTO cerveja VALUES
(1, 11.11, 'image/png', 'cffqwe', '4a0d26c2-234e-42a7-9436-eb90f8eedcec_negra-modelo-long-neck-355ml.png', 'Cerveja Escura', 'NACIONAL', 111, 'ADOCICADA', 'AA1233', 10.00, 11.11, 1),
(2, 0.11, 'image/png', 'dsfsdfgs', '2a118603-c894-4353-8e27-1cd7f27c8e33_beck-long-neck-275ml.png', 'Becks', 'INTERNACIONAL', 111, 'ADOCICADA', 'AA1234', 1.11, 8.00, 2),
(3, 5.00, '', 'Cerveja Teste', '', 'Cerveja Bohemia 14 Weiss 300ml', 'NACIONAL', 100, 'SUAVE', 'AA1131', 5.00, 4.99, 4),
(4, 5.00, '', 'Cerveja Stella', '', 'Stella Artois', 'NACIONAL', 150, 'FORTE', 'AA2222', 6.00, 2.90, 1),
(5, 10.00, 'image/png', 'Cerveja teste corona', 'cc4358a5-3e7c-442f-b01b-ac9cbd43a7ec_coronita-extra-210ml.png', 'Corona Extra', 'INTERNACIONAL', 150, 'ADOCICADA', 'AA2223', 8.00, 5.80, 3);


INSERT INTO estado (codigo, nome, sigla) VALUES (1,'Acre', 'AC');
INSERT INTO estado (codigo, nome, sigla) VALUES (2,'Bahia', 'BA');
INSERT INTO estado (codigo, nome, sigla) VALUES (3,'Goiás', 'GO');
INSERT INTO estado (codigo, nome, sigla) VALUES (4,'Minas Gerais', 'MG');
INSERT INTO estado (codigo, nome, sigla) VALUES (5,'Santa Catarina', 'SC');
INSERT INTO estado (codigo, nome, sigla) VALUES (6,'São Paulo', 'SP');


INSERT INTO cidade (nome, codigo_estado) VALUES ('Rio Branco', 1);
INSERT INTO cidade (nome, codigo_estado) VALUES ('Cruzeiro do Sul', 1);
INSERT INTO cidade (nome, codigo_estado) VALUES ('Salvador', 2);
INSERT INTO cidade (nome, codigo_estado) VALUES ('Porto Seguro', 2);
INSERT INTO cidade (nome, codigo_estado) VALUES ('Santana', 2);
INSERT INTO cidade (nome, codigo_estado) VALUES ('Goiânia', 3);
INSERT INTO cidade (nome, codigo_estado) VALUES ('Itumbiara', 3);
INSERT INTO cidade (nome, codigo_estado) VALUES ('Novo Brasil', 3);
INSERT INTO cidade (nome, codigo_estado) VALUES ('Belo Horizonte', 4);
INSERT INTO cidade (nome, codigo_estado) VALUES ('Uberlândia', 4);
INSERT INTO cidade (nome, codigo_estado) VALUES ('Montes Claros', 4);
INSERT INTO cidade (nome, codigo_estado) VALUES ('Florianópolis', 5);
INSERT INTO cidade (nome, codigo_estado) VALUES ('Criciúma', 5);
INSERT INTO cidade (nome, codigo_estado) VALUES ('Camboriú', 5);
INSERT INTO cidade (nome, codigo_estado) VALUES ('Lages', 5);
INSERT INTO cidade (nome, codigo_estado) VALUES ('São Paulo', 6);
INSERT INTO cidade (nome, codigo_estado) VALUES ('Ribeirão Preto', 6);
INSERT INTO cidade (nome, codigo_estado) VALUES ('Campinas', 6);
INSERT INTO cidade (nome, codigo_estado) VALUES ('Santos', 6);