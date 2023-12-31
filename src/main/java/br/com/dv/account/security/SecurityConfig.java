package br.com.dv.account.security;

import br.com.dv.account.enums.RoleType;
import br.com.dv.account.service.securityevent.logger.SecurityEventLogger;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.servlet.util.matcher.MvcRequestMatcher;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {

    private static final int BCRYPT_STRENGTH = 13;

    private final SecurityEventLogger securityEventLogger;

    public SecurityConfig(SecurityEventLogger securityEventLogger) {
        this.securityEventLogger = securityEventLogger;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, MvcRequestMatcher.Builder mvc) throws Exception {
        return http
                .httpBasic(Customizer.withDefaults())
                .exceptionHandling(ex -> ex.authenticationEntryPoint(new RestAuthenticationEntryPoint())
                        .accessDeniedHandler(new ApiAccessDeniedHandler(securityEventLogger)))
                .authorizeHttpRequests(authz -> authz
                        .requestMatchers(PathRequest.toH2Console()).permitAll()
                        .requestMatchers(mvc.pattern(HttpMethod.POST, "/actuator/shutdown")).permitAll()
                        .requestMatchers(mvc.pattern(HttpMethod.POST, "/api/auth/signup")).permitAll()
                        .requestMatchers(mvc.pattern("/api/acct/**")).hasRole(RoleType.ACCOUNTANT.name())
                        .requestMatchers(mvc.pattern("/api/admin/**")).hasRole(RoleType.ADMINISTRATOR.name())
                        .requestMatchers(mvc.pattern("/api/security/**")).hasRole(RoleType.AUDITOR.name())
                        .anyRequest().authenticated()
                )
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .headers(headers -> headers.frameOptions(HeadersConfigurer.FrameOptionsConfig::disable))
                .csrf(CsrfConfigurer::disable)
                .build();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(BCRYPT_STRENGTH);
    }

    @Bean
    MvcRequestMatcher.Builder mvc(HandlerMappingIntrospector introspector) {
        return new MvcRequestMatcher.Builder(introspector);
    }

}
