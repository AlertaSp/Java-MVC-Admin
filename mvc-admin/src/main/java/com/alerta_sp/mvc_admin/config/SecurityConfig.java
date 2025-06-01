package com.alerta_sp.mvc_admin.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    /**
     * Definição de quais URLs estão liberadas e qual é a página de login.
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // Configura quem pode acessar o quê:
                .authorizeHttpRequests(authz -> authz
                        // Recursos estáticos (css, js, imagens, etc.) não precisam de autenticação:
                        .requestMatchers("/css/**", "/js/**", "/images/**").permitAll()
                        // A própria página de login ("/home") deve estar liberada:
                        .requestMatchers("/", "/home", "/login**", "/oauth2/**").permitAll()
                        // Qualquer outra URL precisa de autenticação:
                        .anyRequest().authenticated()
                )
                // Configura o form‐login para usar a sua página “home”:
                .formLogin(form -> form
                        // Defina “/home” como endpoint de login:
                        .loginPage("/home")
                        // Processa o POST do próprio formulário com action="/login"
                        .loginProcessingUrl("/login")
                        // Se falhar, envia de volta para “/home?error” para exibir mensagem:
                        .failureUrl("/home?error")
                        // Após login bem‐sucedido, pode redirecionar para dashboard:
                        .defaultSuccessUrl("/admin/dashboard", true)
                        .permitAll()
                )
                // Configura o logout
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/home?logout")
                        .permitAll()
                )
                // Caso você utilize OAuth2 (ex.: “Entrar com GitHub”), inclui essa parte:
                .oauth2Login(oauth2 -> oauth2
                        // A página de login ainda é “/home”
                        .loginPage("/home")
                )
        ;

        return http.build();
    }

    /**
     * BCryptPasswordEncoder para codificar as senhas.
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Define um usuário “admin” em memória, com senha já criptografada.
     */
    @Bean
    public UserDetailsService userDetailsService(PasswordEncoder encoder) {
        var admin = User.builder()
                .username("admin")
                .password("$2a$12$jf7otcC9kvkOrrCGmUFkvebdyjmgvcNPVElwJshtIsdWZkC6X2ZVa")
                .roles("ADMIN")
                .build();
        return new InMemoryUserDetailsManager(admin);
    }
}
