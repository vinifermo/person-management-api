package com.attornatus.backend.testeIntegracao.controller;
import com.attornatus.backend.domain.model.Pessoa;
import io.restassured.http.ContentType;
import com.attornatus.backend.domain.model.Endereco;
import com.attornatus.backend.domain.repository.EnderecoRepository;
import com.attornatus.backend.utils.IntegrationTestConfig;
import com.attornatus.backend.utils.ResourceUtils;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import io.restassured.response.Response;
import org.springframework.http.HttpStatus;

import java.util.UUID;

import static io.restassured.RestAssured.basePath;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class EnderecoTestIT extends IntegrationTestConfig {

    @Autowired
    private EnderecoRepository enderecoRepository;
    private String enderecoJson;
    private Endereco endereco;
    private Pessoa pessoa;

    @Before
    public void setUp() {
        super.setUp();
        basePath = "/endereco";
        enderecoJson = ResourceUtils.getContentFromResource("/json/createEndereco.json");
        startEndereco();
    }

    private void startEndereco() {
        endereco = new Endereco(UUID.fromString("7038b81b-f538-43f5-8c5e-537ee2ffdc46"), "Recreio", "8670-065", 10, "Rio de Janeiro/RJ",true,pessoa);
        this.endereco = enderecoRepository.save(endereco);
    }

    @Test
    public void WhenCreateReturn200ok() {
        String payload = enderecoJson
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
                .body("size()", is(5))
                .body("$", hasKey("id"))
                .body("$", hasKey("cep"))
                .body("$", hasKey("logradouro"))
                .body("$", hasKey("numero"))
                .body("$", hasKey("cidade"))
                .body("id", notNullValue())
                .body("logradouro", is("Recreio"))
                .body("cep", is("8670-065"))
                .body("numero", is(10))
                .body("cidade", is("Rio de Janeiro/RJ"))
                .statusCode(HttpStatus.OK.value());
    }

    @Test
    public void WhenUpdateReturn204noContent() {
        String payload = enderecoJson
                .replace("{{logradouro}}", "Recreio")
                .replace("{{cep}}", "8670-065")
                .replace("{{numero}}", "10")
                .replace("{{cidade}}", "Rio de Janeiro/RJ");
        given()
                .pathParam("id", endereco.getId())
                .body(payload)
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .when()
                .put("/{id}")
                .then()
                .statusCode(HttpStatus.NO_CONTENT.value());
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

}
