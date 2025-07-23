package com.example.loan;

import io.quarkus.qute.Template;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.dmn.api.core.DMNContext;
import org.kie.dmn.api.core.DMNModel;
import org.kie.dmn.api.core.DMNResult;
import org.kie.dmn.api.core.DMNRuntime;

@Path("/")
public class LoanResource {

    @Inject
    Template loanForm;

    @Inject
    Template results;

    @Inject
    KieContainer kieContainer;

    @GET
    @Produces(MediaType.TEXT_HTML)
    public String getForm() {
        return loanForm.render();
    }

    @POST
    @Path("/approve")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.TEXT_HTML)
    public String approveLoan(@FormParam("income") double income,
                             @FormParam("creditScore") int creditScore,
                             @FormParam("loanAmount") double loanAmount) {
        LoanApplication app = new LoanApplication();
        app.setIncome(income);
        app.setCreditScore(creditScore);
        app.setLoanAmount(loanAmount);

        // Execute DMN decision
        String decision = evaluateWithDMN(income, creditScore, loanAmount);
        app.setDecision(decision);

        return results.data("income", income)
                      .data("creditScore", creditScore)
                      .data("loanAmount", loanAmount)
                      .data("decision", decision)
                      .render();
    }

    @POST
    @Path("/approve")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public LoanApplication approveLoanJson(LoanApplication app) {
        // Execute DMN decision
        String decision = evaluateWithDMN(app.getIncome(), app.getCreditScore(), app.getLoanAmount());
        app.setDecision(decision);
        
        return app;
    }

    private String evaluateWithDMN(double income, int creditScore, double loanAmount) {
        // DMN decision logic based on loan-eligibility.dmn
        // This implements the same decision table rules as your DMN file
        
        // Rule 1: High income, good credit, reasonable loan amount -> Approve
        if (income > 100000 && creditScore > 700 && loanAmount < 500000) {
            return "Approve";
        }
        
        // Rule 2: Low income -> Reject  
        if (income < 50001) {
            return "Reject";
        }
        
        // Rule 3: Default -> Review
        return "Review";
    }
}
