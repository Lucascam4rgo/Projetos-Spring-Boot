package dio.spring_security.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig {
    @Autowired
    private SecurityDatabaseService securityService;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public void globalUserDetails(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(securityService).passwordEncoder(passwordEncoder);
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests((requests) -> requests
                        .requestMatchers(HttpMethod.POST, "/login").permitAll()
                        .requestMatchers("/").permitAll()
                        .requestMatchers("/managers").hasRole("MANAGERS")
                        .requestMatchers("/users").hasAnyRole("USERS", "MANAGERS")
                        .anyRequest().authenticated())
                .httpBasic(Customizer.withDefaults());


        return http.build();
    }

    //@Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        http.authorizeHttpRequests((requests) -> requests
//                        .anyRequest().authenticated())  // Qualquer requisição deve ser autenticada
//                .formLogin(Customizer.withDefaults())
//                .httpBasic(Customizer.withDefaults());  // Habilita login básico para testes
//
//        return http.build();
//    }


    /*@Bean public UserDetailsService userDetailsService() {
    UserDetails user = User.builder()
    .username("user")
    .password(passwordEncoder().encode("password"))
    .roles("USER")
    .build();
    UserDetails admin = User.builder()
    .username("admin")
    .password(passwordEncoder().encode("password"))
    .roles("MANAGER")
    .build();

    return new InMemoryUserDetailsManager(user, admin);
    }

     @Bean public PasswordEncoder passwordEncoder() {
     return new BCryptPasswordEncoder();
     }*/
}
