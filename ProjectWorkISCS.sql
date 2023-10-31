CREATE TABLE clienti (
    id_cliente INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(50) NOT NULL,
	 cognome VARCHAR(50) NOT NULL   
);

CREATE TABLE prodotto (
    id_prodotto INT AUTO_INCREMENT PRIMARY KEY,
    nome_prodotto VARCHAR(50) NOT NULL,
	 descrizione_prodotto VARCHAR(50) NOT NULL ,
	 prezzo_prodotto DOUBLE NOT NULL,
	 quantita_prodotto INT NOT NULL
);

CREATE TABLE ordine (
    id_ordine INT AUTO_INCREMENT PRIMARY KEY,
    id_cliente INT NOT NULL,
	 tipo_pagamento VARCHAR(50) NOT NULL
);

CREATE TABLE articoli_ordinati (
    id_articoli_ordinati INT AUTO_INCREMENT PRIMARY KEY,
    id_ordine INT NOT NULL,
	 id_prodotto INT NOT NULL,
	 quantita_ordinata INT NOT NULL
);


INSERT INTO clienti(nome,cognome)
VALUES ('Iary','Corsiero'),
		 ('Andrea','Dutto'),
		 ('Eduard','Farcasanu'),
		 ('Fabio','Cerundolo');
		 
INSERT INTO prodotto(nome_prodotto,descrizione_prodotto,prezzo_prodotto,quantita_prodotto)
VALUES ('Telefono','Xiaomi', 120, 82),
		 ('Telefono','Huawei', 199, 97),
		 ('Cover','Per_Xiaomi', 9, 50),
		('Cover','Per_Huawei', 8, 44);		 