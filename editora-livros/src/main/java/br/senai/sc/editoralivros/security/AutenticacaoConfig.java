package br.senai.sc.editoralivros.security;

import br.senai.sc.editoralivros.security.service.GoogleService;
import br.senai.sc.editoralivros.security.service.JpaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@EnableWebSecurity
public class AutenticacaoConfig {

    @Autowired
    private JpaService jpaService;

    @Autowired
    private GoogleService googleService;

    @Autowired
    public void configure(AuthenticationManagerBuilder amb) throws Exception {
        amb.userDetailsService(jpaService).passwordEncoder(NoOpPasswordEncoder.getInstance());
    }

    private CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration corsConfiguration =
                new CorsConfiguration   ();
        corsConfiguration.setAllowedOrigins(List.of(
                "https://localhost:3000"
        ));
        corsConfiguration.setAllowedMethods(List.of(
                "POST", "DELETE", "PUT", "GET"
        ));
        corsConfiguration.setAllowCredentials(true);
        corsConfiguration.setAllowedHeaders(List.of("*"));
        UrlBasedCorsConfigurationSource source =
                new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfiguration);
        return source;
    }

    // Configura as autorizações de acesso
    @Bean
    protected SecurityFilterChain configure(HttpSecurity httpSecurity) throws Exception {

        httpSecurity.authorizeRequests()
                // Permite acesso sem autenticação para \login
                .antMatchers("/login", "/login/auth", "/logout").permitAll()
                // Define que o Autor pode acessar o post do livro
                .antMatchers(HttpMethod.POST,
                        "/editoralivros/livro")
                    .hasAuthority("Autor")
                // Determina que todas as outras requisições precisam de autenticação
                .anyRequest().authenticated();
        httpSecurity.csrf().disable();
        httpSecurity.cors().configurationSource(corsConfigurationSource());
        httpSecurity.logout().permitAll();
        httpSecurity.sessionManagement().sessionCreationPolicy(
                SessionCreationPolicy.STATELESS);
        httpSecurity.addFilterBefore(new AutenticacaoFiltro(new TokenUtils(), jpaService),
                UsernamePasswordAuthenticationFilter.class);
        return httpSecurity.build();
    }


    //Essa função injeta o AuthenticationManager no AuthenticationController
    @Bean
    public AuthenticationManager authenticationManagerBean(AuthenticationConfiguration ac) throws Exception {
        return ac.getAuthenticationManager();
    }

}
