package com.example.loan;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Produces;
import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;

@ApplicationScoped
public class DMNConfiguration {
    
    @Produces
    @ApplicationScoped
    public KieContainer kieContainer() {
        KieServices kieServices = KieServices.Factory.get();
        return kieServices.getKieClasspathContainer();
    }
}
