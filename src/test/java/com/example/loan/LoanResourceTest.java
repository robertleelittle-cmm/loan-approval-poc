package com.example.loan;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;

import com.example.loan.LoanApplication;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
public class LoanResourceTest {

    @Test
    public void testHighIncomeApproval() {
        LoanApplication app = new LoanApplication();
        app.setIncome(150000);
        app.setCreditScore(750);
        app.setLoanAmount(400000);

        given()
                .contentType(ContentType.JSON)
                .body(app)
                .when()
                .post("/approve")
                .then()
                .statusCode(200)
                .body("decision", is("Approve"));
    }

    @Test
    public void testLowIncomeRejection() {
        LoanApplication app = new LoanApplication();
        app.setIncome(40000);
        app.setCreditScore(700);
        app.setLoanAmount(200000);

        given()
                .contentType(ContentType.JSON)
                .body(app)
                .when()
                .post("/approve")
                .then()
                .statusCode(200)
                .body("decision", is("Reject"));
    }

    @Test
    public void testReviewDecision() {
        LoanApplication app = new LoanApplication();
        app.setIncome(80000);
        app.setCreditScore(650);
        app.setLoanAmount(300000);

        given()
                .contentType(ContentType.JSON)
                .body(app)
                .when()
                .post("/approve")
                .then()
                .statusCode(200)
                .body("decision", is("Review"));
    }
}
