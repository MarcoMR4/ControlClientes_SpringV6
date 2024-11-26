package mx.com.gm.web;

/*import jakarta.servlet.Filter;
import jakarta.servlet.http.HttpServletRequest;
import java.util.List; */
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final UserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public SecurityConfig(UserDetailsService userDetailsService, PasswordEncoder passwordEncoder) {
        this.userDetailsService = userDetailsService;
        this.passwordEncoder = passwordEncoder;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(authorizeRequests -> authorizeRequests
                .requestMatchers("/login", "/resources/**", "/webjars/**", "/css/**", "/js/**").permitAll()
                .requestMatchers("/editar/**", "/agregar/**", "/eliminar/**").hasRole("ADMIN")
                .requestMatchers("/").hasAnyRole("USER", "ADMIN")
                .anyRequest().authenticated()
                )
                .formLogin(formLogin -> formLogin
                .loginPage("/login")
                .defaultSuccessUrl("/", true)
                .permitAll()
                )
                .logout(logout -> logout
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login?logout")
                )
                .exceptionHandling(exceptionHandling -> exceptionHandling
                .accessDeniedPage("/errores/403")
                );
        return http.build();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
    }

    /*@Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(authorizeRequests -> authorizeRequests
                // Permitir acceso a login y recursos estáticos 
                .requestMatchers("/login", "/resources/**").permitAll()
                // Restricción de acceso por rol 
                .requestMatchers("/editar/**", "/agregar/**",
                        "/eliminar/**").hasRole("ADMIN")
                // Acceso a usuarios autenticados 
                .requestMatchers("/").hasAnyRole("USER", "ADMIN")
                // Cualquier otra solicitud requiere autenticación 
                .anyRequest().authenticated()
                )
                .formLogin(formLogin -> formLogin
                .loginPage("/login") // Página de login personalizada 
                .defaultSuccessUrl("/", true) // Redirigir después del login exitoso 
                .permitAll() // Permitir acceso al formulario de login 
                )
                .logout(logout -> logout
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login?logout")
                )
                .exceptionHandling(exceptionHandling -> exceptionHandling
                .accessDeniedPage("/errores/403")
                );
        return http.build();
    } */

 /*@Bean 
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception { 
        http 
                .authorizeHttpRequests(authorizeRequests -> 
                        authorizeRequests 
                                .anyRequest().authenticated() 
                ) 
                .formLogin(formLogin -> 
                        formLogin 
                                .permitAll() 
                ); 
        return http.build(); 
    } */
 /*@Bean
    public UserDetailsService userDetailsService() {
        UserDetails user = User.builder()
                .username("user")
                .password(passwordEncoder().encode("123"))
                .roles("USER")
                .build();

        UserDetails admin = User.builder()
                .username("admin")
                .password(passwordEncoder().encode("123"))
                .roles("ADMIN", "USER")
                .build();

        return new InMemoryUserDetailsManager(user, admin);
    }*/

 /*@Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    } */
}
