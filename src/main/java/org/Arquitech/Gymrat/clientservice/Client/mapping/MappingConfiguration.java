package org.Arquitech.Gymrat.clientservice.Client.mapping;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration("clientMappingConfiguration")
public class MappingConfiguration {
    @Bean
    public ClientMapper  clientMapper(){ return new ClientMapper(); }

    @Bean
    public MeasurementMapper measurementMapper(){ return new MeasurementMapper(); }

    @Bean
    public GoalMapper goalMapper(){ return new GoalMapper(); }

}
