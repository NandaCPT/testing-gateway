package com.muf.framework.mffgateway;

import com.muf.framework.mffgateway.config.HttpUriConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import reactor.core.publisher.Mono;

@SpringBootApplication
@CrossOrigin(origins = {
        "*"
},
        methods = {
                RequestMethod.OPTIONS,
                RequestMethod.POST,
                RequestMethod.GET,
                RequestMethod.DELETE,
                RequestMethod.PATCH,
                RequestMethod.PUT
        },
        allowedHeaders = {
                "Content-Type, Authorization"
        },
        maxAge = 1
)
public class MffGatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(MffGatewayApplication.class, args);
    }

    public HttpUriConfig httpUriConfig = new HttpUriConfig();

    /**
     * Sample changing routes to external or internal api server. (here we use httpbin/get)
     * You can also add additional condition into the routes if there is a case that need to.
     * @param builder
     * @return
     */
    @Bean
    public RouteLocator myRoutes(RouteLocatorBuilder builder) {
        String httpUri = httpUriConfig.getHttpbin();
        return builder.routes()
                //routing api sample..
                .route(p -> p
                        //we will route localhost:8080/get into http://httpbin.org:80/get
                        .path("/get")
                        //we can also adding additional info to the request like token or custom header
                        .filters(f -> f.addRequestHeader("Hello", "World"))
                        .uri(httpUri))
                //routing using circuit breaker, used to mitigate unreliable source api
                .route(p -> p
                        //on this example we use wildcard based on host predicate,
                        //as long as host contain the pattern of the string it will get caught by gateway
                        .host("*.circuitbreaker.com")
                        .filters(f -> f.circuitBreaker(
                                config -> config.setName("mycmd")
                                        .setFallbackUri("forward:/fallback")))
                        .uri(httpUri))
                .build();
    }

    // tag::fallback[]
    @RequestMapping("/fallback")
    public Mono<String> fallback() {
        return Mono.just("fallback");
    }

}
