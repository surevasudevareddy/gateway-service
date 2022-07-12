package com.fse.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class GatewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(GatewayApplication.class, args);
	}
	@Bean
	public RouteLocator customRouteLocator(RouteLocatorBuilder builder){
		return builder.routes()
				.route("userSkillsByUserId", r ->	r.path("/userSkillsByUserId/**")
						.filters(f -> f
								.rewritePath("/userSkillsByUserId/(?<segment>/?.*)","/userSkillCombiner/userSkillsByUserId/${segment}")
								.addRequestHeader("skillTracker","Skill Tracker request")
								.circuitBreaker(c -> c
										.setName("skillTrackerCB")
										.setFallbackUri("forward:/fallback")))
						.uri("http://localhost:8083/"))
				.route("users",r -> r.path("/users")
						.filters(f-> f.rewritePath("/users","/userService/users"))
						.uri("http://localhost:8081/"))
				.route("user",r -> r.path("/user/**")
						.filters(f-> f.rewritePath("/user/(?<segment>/?.*)","/userService/user/${segment}"))
						.uri("http://localhost:8081/"))
				.route("saveUser",r -> r.path("/saveUser")
						.filters(f-> f.rewritePath("/saveUser","/userService/saveUser"))
						.uri("http://localhost:8081/"))
				.route("deleteUser",r -> r.path("/deleteUser/**")
						.filters(f-> f.rewritePath("/deleteUser/(?<segment>/?.*)","/userService/deleteUser/${segment}"))
						.uri("http://localhost:8081/"))
				.route(r->r.path("/skills")
						.filters(f -> f.rewritePath("/skills","/skillService/skills"))
						.uri("http://localhost:8082/"))
				.route(r->r.path("/skill/**")
						.filters(f -> f.rewritePath("/skill/(?<segment>/?.*)","/skillService/skill/${segment}"))
						.uri("http://localhost:8082/"))
				.route(r->r.path("/saveSkill")
						.filters(f -> f.rewritePath("/saveSkill","/skillService/saveSkill"))
						.uri("http://localhost:8082/"))
				.route(r->r.path("/deleteSkill/**")
						.filters(f -> f.rewritePath("/deleteSkill/(?<segment>/?.*)","/skillService/delete/${segment}"))
						.uri("http://localhost:8082/"))
				.build();

	}

}
