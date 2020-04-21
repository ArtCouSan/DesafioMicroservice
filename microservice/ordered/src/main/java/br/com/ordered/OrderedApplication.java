package br.com.ordered;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EntityScan({"br.com.core.model"})
@EnableJpaRepositories({"br.com.core.repository"})
@ComponentScan(basePackages = {"br.com.ordered",
        "br.com.token",
        "br.com.config",
        "br.com.core",
        "br.com.filter",
        "br.com.util",
        "br.com.users.endpoint.service",
        "br.com.product.endpoint.service"})
public class OrderedApplication {

    public static void main(String[] args) {
        SpringApplication.run(OrderedApplication.class, args);
    }

}
