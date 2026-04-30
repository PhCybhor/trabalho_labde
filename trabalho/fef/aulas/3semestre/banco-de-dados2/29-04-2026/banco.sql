create table clientes (
cod_cli integer not null,
cpf integer not null,
nome varchar (50), 
endereco varchar (50),
cidade varchar(50),
estado varchar(02),
salario integer ,
idade integer, 
CONSTRAINT PK_CPFcliente PRIMARY KEY (cpf));

create table veiculos (
chassi varchar ( 50) not null,
placa varchar (10) not null, 
cor varchar (20),
modelo varchar(20),
marca varchar(20),
ano_fabricacao integer,
preco_compra integer,
preco_venda integer,
motor integer,
cpf_cli integer not null, 
constraint pk_placa primary key (placa));

Alter table veiculos add constraint fk_cpf_cli foreign key (cpf_cli) references clientes (cpf);

INSERT INTO clientes(cod_cli,cpf,nome,endereco,cidade,estado,salario,idade ) VALUES (01,11,'BEATRIZ','RUA 01','FERNANDOPOLIS', 'SP',1800,32);
INSERT INTO clientes(cod_cli,cpf,nome,endereco,cidade,estado,salario,idade ) VALUES (02,12,'JOANA','RUA 031','SAO JOSE DO RIO PRETO', 'SP',2000,40);
INSERT INTO clientes(cod_cli,cpf,nome,endereco,cidade,estado,salario,idade ) VALUES (03,13,'LUANA','RUA 051','VOTUPORANGA', 'SP',3500,41);
INSERT INTO clientes(cod_cli,cpf,nome,endereco,cidade,estado,salario,idade ) VALUES (04,114,'KATIA','RUA 015','JALES', 'SP',7000,32);
INSERT INTO clientes(cod_cli,cpf,nome,endereco,cidade,estado,salario,idade ) VALUES (05,115,'AMANDA','RUA 061','FERNANDOPOLIS', 'SP',5500,40);
INSERT INTO clientes(cod_cli,cpf,nome,endereco,cidade,estado,salario,idade ) VALUES (06,116,'GIOVANA','RUA 021','SAO JOSE DO RIO PRETO', 'SP',2000,34);
INSERT INTO clientes(cod_cli,cpf,nome,endereco,cidade,estado,salario,idade ) VALUES (07,117,'TAIZ','RUA 011','MACEDONIA', 'SP',1500,32);
INSERT INTO clientes(cod_cli,cpf,nome,endereco,cidade,estado,salario,idade ) VALUES (08,181,'MILENA','RUA 013','SANTA FE DO SUL', 'SP',2300,42);
INSERT INTO clientes(cod_cli,cpf,nome,endereco,cidade,estado,salario,idade ) VALUES (09,911,'ELIZANGELA','RUA 015','CATANDUVA', 'SP',2800,62);
INSERT INTO clientes(cod_cli,cpf,nome,endereco,cidade,estado,salario,idade ) VALUES (010,101,'PATRACIA','RUA 012','ARAGUAINA', 'TO',8800,22);
INSERT INTO clientes(cod_cli,cpf,nome,endereco,cidade,estado,salario,idade ) VALUES (011,131,'MARIA','RUA 014','PALMAS', 'TO',9000,23);
INSERT INTO clientes(cod_cli,cpf,nome,endereco,cidade,estado,salario,idade ) VALUES (012,141,'FELISBINA','RUA 015','DOURADO', 'MS',3100,32);

INSERT INTO VEICULOS(CHASSI,PLACA,COR,MODELO,MARCA,ANO_FABRICACAO,PRECO_COMPRA,PRECO_VENDA,MOTOR, cpf_cli ) VALUES ('GM02','ESC2033','BRANCO','HILUX','TOYOTA',2018,160000,175000,1.8,11);
INSERT INTO VEICULOS(CHASSI,PLACA,COR,MODELO,MARCA,ANO_FABRICACAO,PRECO_COMPRA,PRECO_VENDA,MOTOR, cpf_cli ) VALUES ('FI02','WDR4528','PRETO','COROLLA','TOYOTA',2018,90000,95000,2.0,141);
INSERT INTO VEICULOS(CHASSI,PLACA,COR,MODELO,MARCA,ANO_FABRICACAO,PRECO_COMPRA,PRECO_VENDA,MOTOR , cpf_cli ) VALUES ('RD02','GHY5823','AZUL','HILUX','TOYOTA',2020,170000,175000,2.8,131);
INSERT INTO VEICULOS(CHASSI,PLACA,COR,MODELO,MARCA,ANO_FABRICACAO,PRECO_COMPRA,PRECO_VENDA,MOTOR, cpf_cli ) VALUES ('DF024','TON5689','VERMELHO','COROLLA' ,'TOYOTA',2015,70000,75000,2.0,101);
INSERT INTO VEICULOS(CHASSI,PLACA,COR,MODELO,MARCA,ANO_FABRICACAO,PRECO_COMPRA,PRECO_VENDA,MOTOR, cpf_cli ) VALUES ('FG0452','ERG8965','BRANCO','GOL','VW',2018,40000,45000,1.6,911);
INSERT INTO VEICULOS(CHASSI,PLACA,COR,MODELO,MARCA,ANO_FABRICACAO,PRECO_COMPRA,PRECO_VENDA,MOTOR, cpf_cli ) VALUES ('GT02','EDV5214','BRANCO','SAVEIRO','VW',2014,35000,40000,1.6,181);
INSERT INTO VEICULOS(CHASSI,PLACA,COR,MODELO,MARCA,ANO_FABRICACAO,PRECO_COMPRA,PRECO_VENDA,MOTOR, cpf_cli ) VALUES ('GM0322','TGB2589','PRETO','UNO','FIAT',2010,20000,25000,1.0,181);
INSERT INTO VEICULOS(CHASSI,PLACA,COR,MODELO,MARCA,ANO_FABRICACAO,PRECO_COMPRA,PRECO_VENDA,MOTOR, cpf_cli ) VALUES ('GM0452','FGB8521','PRETO','S10','CHEVROLET',2018,150000,175000,2.8,117);
INSERT INTO VEICULOS(CHASSI,PLACA,COR,MODELO,MARCA,ANO_FABRICACAO,PRECO_COMPRA,PRECO_VENDA,MOTOR, cpf_cli ) VALUES ('GM02245','RFV5235','PRATA','COROLLA','TOYOTA',2021,130000,135000,2.0,116);
INSERT INTO VEICULOS(CHASSI,PLACA,COR,MODELO,MARCA,ANO_FABRICACAO,PRECO_COMPRA,PRECO_VENDA,MOTOR, cpf_cli ) VALUES ('GM07892','PLK5689','PRATA','ONIX','CHEVROLET',2018,145000,155000,2.8,115);
INSERT INTO VEICULOS(CHASSI,PLACA,COR,MODELO,MARCA,ANO_FABRICACAO,PRECO_COMPRA,PRECO_VENDA,MOTOR, cpf_cli ) VALUES ('GM05212','UJM5874','AZUL','KA','FORD',2018,40000,45000,1.0,114);
INSERT INTO VEICULOS(CHASSI,PLACA,COR,MODELO,MARCA,ANO_FABRICACAO,PRECO_COMPRA,PRECO_VENDA,MOTOR, cpf_cli ) VALUES ('GM02452','VCX1458','VERMELHO','TRACKER','CHEVROLET',2017,90000,95000,1.4,13);
INSERT INTO VEICULOS(CHASSI,PLACA,COR,MODELO,MARCA,ANO_FABRICACAO,PRECO_COMPRA,PRECO_VENDA,MOTOR, cpf_cli ) VALUES ('GM02785','EDC8526','BRANCO','ONIX','CHEVROLET',2018,45000,50000,1.4,12);
INSERT INTO VEICULOS(CHASSI,PLACA,COR,MODELO,MARCA,ANO_FABRICACAO,PRECO_COMPRA,PRECO_VENDA,MOTOR, cpf_cli ) VALUES ('GM02752','EDCV4569','PRETO','HILUX','TOYOTA',2018,165000,175000,2.8,11);

SELECT nome_cliente, uf
FROM cliente
WHERE uf IN ('SP', 'MG');

SELECT nome_cliente, uf
FROM cliente
WHERE uf NOT IN ('SP', 'MG');

SELECT nome_vendedor, faixa_comissao
FROM vendedor
WHERE faixa_comissao IN ('A', 'B');

SELECT codigo_produto, unidade, descricao, val_unit
FROM produto
WHERE unidade IN ('M', 'G', 'L')
AND val_unit <= 1.05;

SELECT clientes.nome,clientes.cidade,veiculos.modelo,veiculos.marca
FROM clientes,veiculos
WHERE (clientes.cpf = veiculos.cpf_cli);

SELECT clientes.nome,clientes.cidade,veiculos.modelo,veiculos.marca
FROM clientes,veiculos
WHERE (clientes.cpf = veiculos.cpf_cli)
ORDER BY clientes.cidade;

SELECT clientes.nome,clientes.cidade,veiculos.modelo,veiculos.marca
FROM clientes,veiculos
WHERE (clientes.cpf = veiculos.cpf_cli)
AND (clientes.estado = 'SP');

SELECT clientes.nome,clientes.cidade,veiculos.modelo,veiculos.marca
FROM clientes,veiculos
WHERE (clientes.cpf = veiculos.cpf_cli)
AND (clientes.estado = 'SP')
ORDER BY clientes.cidade;

SELECT clientes.nome, clientes.cpf, veiculos.marca, veiculos.modelo
FROM clientes, veiculos
WHERE clientes.cpf = veiculos.cpf_cli
AND veiculos.marca = 'TOYOTA';

SELECT veiculos.chassi, veiculos.placa, veiculos.modelo, veiculos.marca, clientes.nome, clientes.cpf
FROM veiculos, clientes
WHERE veiculos.cpf_cli = clientes.cpf
AND clientes.estado <> 'SP';

SELECT *
FROM veiculos
WHERE marca IN ('TOYOTA', 'VW');
