package sk.stopangin.realtimegamem.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import sk.stopangin.realtimegamem.service.GameService;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true)
public class WebSecurityAdapterSetup extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
                .withUser("admin_1").password("ADMIN123").roles("ADMIN")
                .and()
                //initial user - me - BIG BAD WOLF
                .withUser(GameService.USER_1).password("ivanjesaman").roles("ADMIN")
                .and()
                //real users from here
                .withUser("user_2").password("secret2").roles("USER")
                .and()
                .withUser("user_3").password("secret3").roles("USER")
                .and()
                .withUser("user_4").password("secret4").roles("USER")
                .and()
                .withUser("user_5").password("secret5").roles("USER")
                .and()
                .withUser("user_6").password("secret6").roles("USER")
                .and()
                .withUser("user_7").password("secret7").roles("USER");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/v2/api-docs", "/configuration/ui", "/swagger-resources", "/configuration/security",
                        "/swagger-ui.html", "/webjars/**").hasRole("ADMIN")
                .anyRequest().authenticated()
                .and()
                .httpBasic()
                .and().csrf().disable();
    }
}