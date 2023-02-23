CREATE TABLE tb_endereco
(
    id         UUID PRIMARY KEY,
    logradouro VARCHAR(255),
    cep        VARCHAR(255),
    numero     INT,
    cidade     VARCHAR(255),
    endereco_Principal  BOOLEAN,
    pessoa_id  UUID,
    FOREIGN KEY (pessoa_id) REFERENCES tb_pessoa(id)
);