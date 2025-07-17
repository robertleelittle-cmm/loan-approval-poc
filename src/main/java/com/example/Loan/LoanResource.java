package com.example.loan;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import io.quarkus.qute.Template;
import jakarta.ws.rs.core.MediaType;
import org.kie.api.runtime.KieSession;

@Path("/")
public class LoanResource {

    @Inject 
    KieSession kieSession;

    @Inject 
    Template loanForm;
    
    @Inject 
    Template results;

    @GET
    @Produces(MediaType.TEXT_HTML)
    public String getForm() {
        return loanForm.instance().render();
    }

    @POST
    @Path("/approve")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.TEXT_HTML)
    public String processForm(@FormParam("income") double income,
                             @FormParam("creditScore") int creditScore,
                             @FormParam("loanAmount") double loanAmount) {
        LoanApplication app = new LoanApplication(income, creditScore, loanAmount);
        
        // Insert the loan application into the session
        kieSession.insert(app);

        // Fire the rules
        kieSession.fireAllRules();

        return results.data("income", income)
                     .data("creditScore", creditScore)
                     .data("loanAmount", loanAmount)
                     .data("decision", app.getDecision())
                     .render();
    }

    @POST
    @Path("/approve")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public LoanApplication processJson(LoanApplication application) {
        // Insert the loan application into the session
        kieSession.insert(application);

        // Fire the rules
        kieSession.fireAllRules();

        return application;
    }

    @POST
    @Path("/evaluate")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public LoanApplication evaluateLoan(LoanApplication application) {
        // Insert the loan application into the session
        kieSession.insert(application);
        
        // Fire the rules
        kieSession.fireAllRules();
        
        return application;
    }
}
