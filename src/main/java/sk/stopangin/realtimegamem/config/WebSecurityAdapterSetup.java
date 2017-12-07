package sk.stopangin.realtimegamem.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled=true)
public class WebSecurityAdapterSetup extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
                .withUser("user1").password("ADMIN123").roles("ADMIN")
                .and()
                .withUser("user2").password("secret2").roles("USER")
                .and()
                .withUser("user3").password("secret3").roles("USER")
                .and()
                .withUser("user4").password("secret4").roles("USER")
                .and()
                .withUser("user5").password("secret5").roles("USER")
                .and()
                .withUser("user6").password("secret6").roles("USER")
                .and()
                .withUser("user7").password("secret7").roles("USER");
    }
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests().anyRequest().authenticated()
                .and()
                .httpBasic()
                .and().csrf().disable();
    }
}