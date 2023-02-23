package com.attornatus.backend.testeIntegracao.controller;
import io.restassured.http.ContentType;
import com.attornatus.backend.utils.IntegrationTestConfig;
import com.attornatus.backend.domain.model.Endereco;
import com.attornatus.backend.domain.model.Pessoa;
import com.attornatus.backend.domain.repository.EnderecoRepository;
import com.attornatus.backend.domain.repository.PessoaRepository;
import com.attornatus.backend.utils.ResourceUtils;
import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import static io.restassured.RestAssured.basePath;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class PessoaTestIT extends IntegrationTestConfig {

    @Autowired
    private PessoaRepository pessoaRepository;

    @Autowired
    private EnderecoRepository enderecoRepository;


    private Pessoa pessoa;
    private Endereco endereco;
    private String pessoaJson;

    @Before
    public void setUp() {
        super.setUp();
        basePath = "/pessoa";
        pessoaJson = ResourceUtils.getContentFromResource("/json/createPessoa.json");
        startPessoa();
    }
    private void startPessoa() {
        endereco = new Endereco(UUID.fromString("7038b81b-f538-43f5-8c5e-537ee2ffdc46"), "Recreio", "8670-065", 10, "Rio de Janeiro/RJ",true,pessoa);
        this.endereco = enderecoRepository.save(endereco);

        pessoa = new Pessoa(UUID.fromString("7038b81b-f538-43f5-8c5e-537ee2ffdc46"), "Vincius", LocalDate.of(1995, 9, 21), List.of(endereco));
        this.pessoa = pessoaRepository.save(pessoa);
    }
    @Test
    public void WhenCreateReturn200ok() {
        String payload = pessoaJson
                .replace("{{nome}}", "Vinicius")
                .replace("{{dataDeNascimento}}", "1995-09-21")
                .replace("{{logradouro}}", "Recreio")
                .replace("{{cep}}", "8670-065")
                .replace("{{numero}}", "10")
                .replace("{{cidade}}", "Rio de Janeiro/RJ");

       Response response = given()
                .body(payload)
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .when()
                .post();
        response.then()
                .statusCode(HttpStatus.CREATED.value());

        String id = getIdHeaderLocation(response);
        given()
                .pathParam("id", id)
                .when()
                .get("/{id}")
                .then()
                .body("size()", is(4))
                .body("$", hasKey("id"))
                .body("$", hasKey("nome"))
                .body("$", hasKey("dataDeNascimento"))
                .body("endereco", hasKey("logradouro"))
                .body("endereco", hasKey("cep"))
                .body("endereco", hasKey("numero"))
                .body("endereco", hasKey("cidade"))
                .body("id", notNullValue())
                .body("nome", is("Vinicius"))
                .body("dataDeNascimento", is("1995-09-21"))
                .body("endereco.logradouro", is("Recreio"))
                .body("endereco.cep", is("8670-065"))
                .body("endereco.numero", is(10))
                .body("endereco.cidade", is("Rio de Janeiro/RJ"))
                .statusCode(HttpStatus.OK.value());
    }

    @Test
    public void WhenFindByIdReturn200ok() {
        given()
                .pathParam("id", pessoa.getId().toString())
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .when()
                .get("/{id}")
                .then()
                .statusCode(HttpStatus.OK.value());
    }
    @Test
    public void WhenUpdateReturn204noContent() {
        String payload = pessoaJson
                .replace("{{nome}}", "Vinicius")
                .replace("{{dataDeNascimento}}", "1995-09-21")
                .replace("{{logradouro}}", "Recreio")
                .replace("{{cep}}", "8670-065")
                .replace("{{numero}}", "10")
                .replace("{{cidade}}", "Rio de Janeiro/RJ");

        given()
                .pathParam("id", pessoa.getId())
                .body(payload)
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .when()
                .put("/{id}")
                .then()
                .statusCode(HttpStatus.NO_CONTENT.value());
    }

}
