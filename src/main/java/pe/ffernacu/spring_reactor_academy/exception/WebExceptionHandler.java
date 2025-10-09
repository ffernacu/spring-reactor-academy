package pe.ffernacu.spring_reactor_academy.exception;

import org.springframework.boot.autoconfigure.web.WebProperties;
import org.springframework.boot.autoconfigure.web.reactive.error.AbstractErrorWebExceptionHandler;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.reactive.error.ErrorAttributes;
import org.springframework.context.ApplicationContext;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.MediaType;
import org.springframework.http.codec.ServerCodecConfigurer;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.*;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.Map;

/**
 * AbstractErrorWebExceptionHandler: para implementar enfoque funcional usando metodos reactivos para evitar romper el patron de webflux
 * nos ofrece metodos para reutilizar como: getError, getErrorAttributes, setMessageWriters
 * Ordered.HIGHEST_PRECEDENCE: prioriza la ejecucion ante otra ejecucion de error por defecto de spring boot
 */
@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class WebExceptionHandler extends AbstractErrorWebExceptionHandler {

    public WebExceptionHandler(ErrorAttributes errorAttributes, WebProperties.Resources resources, ApplicationContext applicationContext, ServerCodecConfigurer configurer) {
        super(errorAttributes, resources, applicationContext);
        setMessageWriters(configurer.getWriters());
    }

    @Override
    protected RouterFunction<ServerResponse> getRoutingFunction(ErrorAttributes errorAttributes) {
        return RouterFunctions.route(RequestPredicates.all(), this::renderErrorResponse); //req -> this.renderErrorResponse(req)
    }

    private Mono<ServerResponse> renderErrorResponse(ServerRequest req) {
        Map<String, Object> defaultError = getErrorAttributes(req, ErrorAttributeOptions.defaults());

        Throwable ex = getError(req);
        int statusCode = Integer.parseInt(String.valueOf(defaultError.get("status")));

        CustomErrorResponse errorResponse;
        switch (statusCode) {
            case 400, 422 -> {
                errorResponse = new CustomErrorResponse(LocalDateTime.now(), ex.getMessage());
            }
            case 404 -> errorResponse = new CustomErrorResponse(LocalDateTime.now(), "Not Found");
            case 401, 403 -> errorResponse = new CustomErrorResponse(LocalDateTime.now(), "Not Authorized");
            case 500 -> errorResponse = new CustomErrorResponse(LocalDateTime.now(), "Internal Server Error");
            default -> errorResponse = new CustomErrorResponse(LocalDateTime.now(), ex.getMessage());
        }

        return ServerResponse.status(statusCode)
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(errorResponse));
    }
}
