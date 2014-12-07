package com.foo.bar.si.file.demo.context;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;


@Configuration
@ComponentScan(basePackages={"com.foo.bar.si.file.demo.filter",
							"com.foo.bar.si.file.demo.service.activators",
							"com.foo.bar.si.file.demo.transformer"})


@ImportResource("classpath:META-INF/spring/integration/spring-integration-context.xml")
public class SpringIntegrationFileDemoContext {

}
