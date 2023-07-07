package com.mindhub.homebanking.configurations;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.logout.HttpStatusReturningLogoutSuccessHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@EnableWebSecurity
@Configuration
public class WebAuthorization{

    @Bean
    protected SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http.authorizeRequests()
                .antMatchers(HttpMethod.POST,"/api/clients").permitAll()
                .antMatchers(HttpMethod.POST, "/api/loans","/api/clients/current/accounts",
                        "/api/clients/current/cards", "/api/transactions", "/api/pdf/generate").hasAuthority("CLIENT")
                .antMatchers("/rest/**", "/api/**", "/h2-console/**", "/manager.html", "/manager.js" ).hasAuthority("ADMIN")
                .antMatchers("/web/accounts.html", "/web/accounts.js","/web/account.html",
                             "/web/account.js", "/web/cards.html","/web/cards.js", "/web/transfers.html", "/web/transfers.js",
                             "/web/loan-application.html","/web/loan-application.js" , "/web/products.html", "/web/products.js").hasAuthority("CLIENT")
                .antMatchers(  "/web/login.html", "/web/login.js", "/web/signin.html", "/web/signin.js","/web/index.html" ,
                        "/web/index.js" , "/web/css/style.css","/web/css/style.css","/web/img/**","/web/img/**","/api/**", "/web/maintenance.html","/api/clients").permitAll()
                .anyRequest().denyAll();

        http.formLogin()
                .usernameParameter("email")
                .passwordParameter("password")
                .loginPage("/api/login");

        http.logout().logoutUrl("/api/logout")
                .deleteCookies("JSESSIONID")
                .invalidateHttpSession(true)
                .clearAuthentication(true);


        // turn off checking for CSRF tokens
        //desactivar la comprobación de tokens CSRF
                http.csrf().disable();

        //disabling frameOptions so h2-console can be accessed
        //deshabilitar frameOptions para que se pueda acceder a h2-console
                http.headers().frameOptions().disable();

        // if user is not authenticated, just send an authentication failure response
        //si el usuario no está autenticado, simplemente envíe una respuesta de falla de autenticación
                http.exceptionHandling().authenticationEntryPoint((req, res, exc) -> res.sendError(HttpServletResponse.SC_UNAUTHORIZED));

        // if login is successful, just clear the flags asking for authentication
        //si el inicio de sesión es exitoso, simplemente borre las banderas que solicitan autenticación
                http.formLogin().successHandler((req, res, auth) -> clearAuthenticationAttributes(req));

        // if login fails, just send an authentication failure response
        //si el inicio de sesión falla, simplemente envíe una respuesta de falla de autenticación
                http.formLogin().failureHandler((req, res, exc) -> res.sendError( HttpServletResponse.SC_UNAUTHORIZED));

        // if logout is successful, just send a success response
        //si el cierre de sesión es exitoso, simplemente envíe una respuesta exitosa
                http.logout().logoutSuccessHandler(new HttpStatusReturningLogoutSuccessHandler());

                return http.build();
    }

    private void clearAuthenticationAttributes(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
        }
    }
}
