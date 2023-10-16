package com.study.springboot.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import jakarta.servlet.DispatcherType;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig  {  

	@Bean
    protected SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		
		http.csrf((csrf) -> csrf.disable())
		.cors((cors) -> cors.disable())
    	.authorizeHttpRequests(request -> request  
    		.dispatcherTypeMatchers(DispatcherType.FORWARD).permitAll()
    		.requestMatchers("/").permitAll()
            .requestMatchers("/css/**", "/js/**", "/img/**").permitAll()
            .requestMatchers("/guest/**").permitAll()  // 모두에게 허용.
            .requestMatchers("/member/**").hasAnyRole("USER", "ADMIN") // 두권한 허용
            .requestMatchers("/admin/**").hasRole("ADMIN") // ADMIN만 허용
            .anyRequest().authenticated()	// 어떠한 요청이라도 인증 필요
        );
    http.formLogin(login -> login
    				.loginPage("/loginForm") 			// default : /login
				    .loginProcessingUrl("/j_spring_security_check")	// 바꾸면 안됨.
				    .failureUrl("/loginError") 			// default : /login?error
				    //.defaultSuccessUrl("/")
				    .usernameParameter("j_username")	// default : j_username
				    .passwordParameter("j_password") 	// default : j_password
    				.permitAll()); // 기본 로그인 페이지	
   
    http.logout(logout -> logout
    			.logoutUrl("/logout") // default
			    .logoutSuccessUrl("/")	// 처음 페이지로
				.permitAll());	// 로그 아웃 기본 설정 (/logout으로 인증 해제)

		return http.build();  
    }
 
	@Bean
    // users() 메서드는 빠른 테스트를 위해 등록이 간단한 inMemory 방식의 인증 사용자를 등록
    protected UserDetailsService users()
    {
        UserDetails user = User.builder()
        		.username("user")
        		.password(passwordEncoder().encode("1234"))
        		.roles("USER")	// ROLE_USER 에서 ROLE_ 자동으로 붙는다.
        		.build();
        UserDetails admin = User.builder()
        		.username("admin")
        		.password(passwordEncoder().encode("1234"))
        		.roles("USER", "ADMIN")	
        		.build();
       
        // 메모리에 사용자 정보를 담는다.
        return new InMemoryUserDetailsManager(user, admin);
    }
   
    // passwordEncoder() 추가   //버전업 되면서 아래와 같이 수정됨.
    public PasswordEncoder passwordEncoder() {
      return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }
}