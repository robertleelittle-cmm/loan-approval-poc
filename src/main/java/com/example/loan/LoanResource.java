package com.example.loan;

import io.quarkus.qute.Template;
import io.quarkus.qute.TemplateInstance;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import org.kie.kogito.decisions.DecisionModels;
import org.kie.kogito.incubation.common.DataContext;
import org.kie.kogito.incubation.common.MapDataContext;

@Path("/")
public class LoanResource {

    @Inject Template loanForm;  // If using Qute for web form
    @Inject Template result;

    @Inject DecisionModels decisionModels;  // For DMN evaluation

    @GET
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance getForm() {
        return loanForm.instance();  // Skip if no web form yet
    }

    @POST
    @Path("/approve")
    @Consumes(MediaType.APPLICATION_JSON)  // Or APPLICATION_FORM_URLENCODED for form
    @Produces(MediaType.APPLICATION_JSON)
    public LoanApplication approveLoan(LoanApplication app) {
        // Prepare DMN input context
        MapDataContext ctx = MapDataContext.of();
        ctx.set("income", app.getIncome());
        ctx.set("creditScore", app.getCreditScore());
        ctx.set("loanAmount", app.getLoanAmount());

        // Evaluate DMN (use your DMN's namespace and decision name)
        DataContext resultCtx = decisionModels.getDecisionModel("http://www.example.org/loan-eligibility", "Eligibility Decision").evaluate(ctx);

        // Extract decision and set on app
        MapDataContext mapResult = resultCtx.as(MapDataContext.class);
        String decision = (String) mapResult.get("decision");
        app.setDecision(decision);

        return app;  // Now with DMN-based decision
    }
}