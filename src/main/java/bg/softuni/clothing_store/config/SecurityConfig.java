package bg.softuni.clothing_store.config;

import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http.
                authorizeHttpRequests(
                        authorizeRequest -> {
                            authorizeRequest
                                    .requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
                                    .requestMatchers("/", "/users/login", "/users/register", "/about", "/users/login-error", "/error", "users/profile").permitAll()
                                    .anyRequest().authenticated();
                        })
                .formLogin(formLogin -> {
                    formLogin.loginPage("/users/login");
                    formLogin.usernameParameter("username");
                    formLogin.passwordParameter("password");
                    formLogin.defaultSuccessUrl("/", true);
                    formLogin.failureUrl("/users/login-error");
                })
                .logout(
                        logout -> {
                            logout.logoutUrl("/users/logout");
                            logout.logoutSuccessUrl("/");
                            logout.invalidateHttpSession(true);
                        })
                .build();
    }

}
