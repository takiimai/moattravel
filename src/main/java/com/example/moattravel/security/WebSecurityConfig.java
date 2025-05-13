package com.example.moattravel.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class WebSecurityConfig {

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http
				.authorizeHttpRequests(requests -> requests
						.requestMatchers("/css/**", "/images/**", "/js/**", "/storage/**", "/", "/signup/**", "/houses",
								"/login", "/houses/{id}", "/", "/reservations")
						.permitAll()// すべてのユーザーにアクセスを許可するURL
						.requestMatchers("/admin/**").hasRole("ADMIN") // 管理者にのみアクセスを許可するURL
						.anyRequest().authenticated())// 上記以外のURLはログインが必要（会員または管理者のどちらでもOK）
				.formLogin(form -> form
						.loginPage("/login") // ログインページのURL (GET)
						.loginProcessingUrl("/login") // 認証処理を実行するURL (POST)
						.defaultSuccessUrl("/", true) // 成功後にリダイレクトするURL
						.failureUrl("/login?error") // 失敗時にリダイレクトするURL
						.permitAll())
				.logout(logout -> logout
						.logoutUrl("/logout")
						.logoutSuccessUrl("/")
						.permitAll());

		return http.build();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}