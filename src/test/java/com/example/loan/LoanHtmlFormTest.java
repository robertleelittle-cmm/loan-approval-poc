package com.example.loan;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.containsString;

@QuarkusTest
public class LoanHtmlFormTest {

    @Test
    public void testFormPageLoad() {
        given()
                .when()
                .get("/")
                .then()
                .statusCode(200)
                .contentType(ContentType.HTML)
                .body(containsString("Submit Loan Application"))
                .body(containsString("Income:"))
                .body(containsString("Credit Score:"))
                .body(containsString("Loan Amount:"));
    }

    @Test
    public void testFormSubmission() {
        given()
                .contentType(ContentType.URLENC)
                .formParam("income", "80000")
                .formParam("creditScore", "700")
                .formParam("loanAmount", "300000")
                .when()
                .post("/approve")
                .then()
                .statusCode(200)
                .contentType(ContentType.HTML)
                .body(containsString("Decision: Review"))
                .body(containsString("Income 80000"))
                .body(containsString("Credit 700"))
                .body(containsString("Amount 300000"));
    }
}
