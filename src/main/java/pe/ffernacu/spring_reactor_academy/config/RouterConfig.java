package pe.ffernacu.spring_reactor_academy.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;
import pe.ffernacu.spring_reactor_academy.handler.MatriculaHandler;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class RouterConfig {

    @Bean
    public RouterFunction<ServerResponse> routesMatricula(MatriculaHandler matriculaHandler){
        return route(POST("/v1/registrations"), matriculaHandler::save)
                .andRoute(GET("/v1/registrations"), matriculaHandler::report);
    }
}
