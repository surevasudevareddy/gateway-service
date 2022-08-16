package com.fse.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;


@SpringBootApplication
public class GatewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(GatewayApplication.class, args);
	}
	@Bean
	public RouteLocator customRouteLocator(RouteLocatorBuilder builder){
		return builder.routes()
				.route("userAndSkillsByUserId", r ->	r.path("/skill-tracker/api/v1/engineer/userAndSkillsByUserId/**")
						.filters(f -> f
								.rewritePath("/skill-tracker/api/v1/engineer/userAndSkillsByUserId/(?<segment>/?.*)","/userSkillCombiner/userAndSkillsByUserId/${segment}")
								.addRequestHeader("skillTracker","Skill Tracker request")
								.circuitBreaker(c -> c
										.setName("skillTrackerCB")
										.setFallbackUri("forward:/fallback")))
						.uri("http://localhost:8083/"))
				.route("userAndSkillsByUserName", r ->	r.path("/skill-tracker/api/v1/engineer/userAndSkillsByUserName/**")
						.filters(f -> f
								.rewritePath("/skill-tracker/api/v1/engineer/userAndSkillsByUserName/(?<segment>/?.*)","/userSkillCombiner/userAndSkillsByUserName/${segment}")
								.circuitBreaker(c -> c
										.setName("skillTrackerCB")
										.setFallbackUri("forward:/fallback")))
						.uri("http://localhost:8083/"))
				.route("saveUserAndSkills", r ->	r.path("/skill-tracker/api/v1/engineer/add-profile")
						.filters(f -> f
								.rewritePath("/skill-tracker/api/v1/engineer/add-profile","/userSkillCombiner/saveUserAndSkills")
								.addRequestHeader("skillTracker","Save Skill Tracker request")
								.circuitBreaker(c -> c
										.setName("skillTrackerCB")
										.setFallbackUri("forward:/fallback")))
						.uri("http://localhost:8083/"))
				.route("updateUserAndSkills", r ->	r.path("/skill-tracker/api/v1/engineer/update-profile")
						.filters(f -> f
								.rewritePath("/skill-tracker/api/v1/engineer/update-profile","/userSkillCombiner/updateUserAndSkills")
								.circuitBreaker(c -> c
										.setName("skillTrackerCB")
										.setFallbackUri("forward:/fallback")))
						.uri("http://localhost:8083/"))
				.route("searchUserAndSkills", r ->	r.path("/skill-tracker/api/v1/admin/criteria")
						.filters(f -> f
								.rewritePath("/skill-tracker/api/v1/admin/criteria","/userprofile/criteria")
								.circuitBreaker(c -> c
										.setName("skillTrackerCB")
										.setFallbackUri("forward:/fallback")))
						.uri("http://localhost:8086/"))
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
