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

INSERT INTO "PUBLIC"."cliente" VALUES
(1, '05496280966', 'me@diego.dev.br', '', '', '', '', 'Joao Silva', '', 'FISICA', NULL),
(2, '05720076980', 'diegosous@gmail.com', '83.407-730', 'Apto 21', 'Rua Ailton Luiz Nodari', '157', STRINGDECODE('\u00c9der Diego de Sousa'), '(41) 99820-3155', 'FISICA', 17),
(3, '05881460000190', 'agne@agnemoveis.com.br', '83.404-000', 'Fundos', STRINGDECODE('Avenida S\u00e3o Gabriel'), '272', 'Agne Moveis', '(41) 3621-9846', 'JURIDICA', 9),
(4, '81259250822', '', '', '', '', '', 'Malu Gabrielly Maria Viana', '', 'FISICA', NULL),
(5, '80164296379', '', '', '', '', '', 'Geraldo Bento Drumond', '', 'FISICA', NULL),
(6, '19624430519', '', '', '', '', '', 'Helena Daniela Carla Sales', '', 'FISICA', NULL),
(7, '13579942000118', '', '', '', '', '', 'Antonio e Isabella Marketing Ltda', '', 'JURIDICA', NULL),
(8, '45225488000193', '', '', '', '', '', 'Tatiane e Giovanni Telas Ltda', '', 'JURIDICA', NULL),
(9, '77476450000180', '', '', '', '', '', STRINGDECODE('B\u00e1rbara e Milena Constru\u00e7\u00f5es ME'), '', 'JURIDICA', NULL),
(10, '81632896000123', '', '', '', '', '', 'Aline e Luiza Telecom ME', '', 'JURIDICA', NULL),
(11, '99581636000131', '', '', '', '', '', STRINGDECODE('Ian e M\u00e1rcio Cont\u00e1bil ME'), '', 'JURIDICA', NULL),
(12, '18622608425', '', '', '', '', '', 'Larissa Rebeca da Paz', '(95) 98589-7931', 'FISICA', NULL),
(13, '53150182611', '', '', '', '', '', 'Andreia Silvana Milena Campos', '(21) 3850-9410', 'FISICA', NULL),
(14, '19116205000165', '', '', '', '', '', STRINGDECODE('M\u00e1rcia e Antonio Entulhos Ltda'), '(19) 2549-7346', 'JURIDICA', NULL);

INSERT INTO "PUBLIC"."grupo" VALUES
(1, 'Administrador'),
(2, 'Vendedor');

INSERT INTO "PUBLIC"."usuario" VALUES
(1, TRUE, DATE '1986-12-11', 'diego@teste', 'Diego', '$2a$10$7uL2bSbv/IK4jlDiWv3xq.aKy/tlX.D4gQ00hi8RJLVW.rHEUd4fy'),
(2, TRUE, NULL, 'joao@teste', 'Cerveja Escura', '$2a$10$MXf6TOPZ2I9rWHROtUoLVOy8Ifu.yJMZpyI/FQF5onVoohxvRnzn6'),
(3, TRUE, DATE '2021-07-20', 'carlos@teste', 'Carlos Alberto', '$2a$10$ToEmiNw5fow9mM4daksoe.zt1SMZbv0UMwYRVUOnb9VPiz8KV3LpG'),
(4, FALSE, DATE '2021-07-12', 'ruty@teste', 'Ruty', '$2a$10$9UEXnV7kqetN5bRYcN0m/.p15SlEFlPYfa37OBAnHLutPYBAr2EIO');

INSERT INTO "PUBLIC"."permissao" VALUES
(1, 'ROLE_CADASTRAR_CIDADE'),
(2, 'ROLE_CADASTRAR_USUARIO'),
(3, 'ROLE_CANCELAR_VENDA');          ;

INSERT INTO "PUBLIC"."grupo_permissao" VALUES
(1, 1),
(1, 2),
(1, 3);

INSERT INTO "PUBLIC"."usuario_grupo" VALUES
(1, 1),
(2, 2),
(3, 1),
(3, 2),
(4, 1);

INSERT INTO "PUBLIC"."venda" VALUES
(1, DATE '2021-06-14', TIMESTAMP '2021-07-14 22:34:00', 'Entregar na Portaria 2', 'EMITIDA', 2.00, 10.00, 27.11, 1, 1),
(2, DATE '2021-06-14', NULL, '', 'ORCAMENTO', NULL, NULL, 800.00, 5, 1),
(3, DATE '2021-07-14', NULL, '', 'EMITIDA', NULL, NULL, 24.10, 6, 1),
(4, DATE '2021-07-17', NULL, '', 'ORCAMENTO', NULL, NULL, 8.00, 13, 1),
(5, DATE '2021-07-20', NULL, '', 'ORCAMENTO', NULL, NULL, 4.99, 8, 1);

INSERT INTO "PUBLIC"."item_venda" VALUES
(1, 1, 11.11, 1, 1),
(2, 1, 8.00, 2, 1),
(3, 100, 8.00, 2, 2),
(4, 1, 8.00, 2, 3),
(5, 1, 11.11, 1, 3),
(6, 1, 4.99, 3, 3),
(7, 1, 8.00, 2, 4),
(8, 1, 4.99, 3, 5);