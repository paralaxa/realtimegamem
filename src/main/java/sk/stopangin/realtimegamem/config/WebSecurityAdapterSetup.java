package sk.stopangin.realtimegamem.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
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
                .withUser("CuchyJeNajlepsi_2").password("MartinCuchtaJeNajlepsi").roles("USER")
                .and()
                .withUser("kikiriki_3").password("heslo").roles("USER")
                .and()
                .withUser("admin_4").password("admin").roles("USER")
                .and()
                .withUser("riso_5").password("ramones").roles("USER")
                .and()
                .withUser("C++_6").password("Lubos").roles("USER")
                .and()
                .withUser("riekt_7").password("riektriekt").roles("USER")
                .and()
                .withUser("Kotkodaky_8").password("kikirik").roles("USER")
                .and()
                .withUser("Zoligor_9").password("event123").roles("USER")
                .and()
                .withUser("Cplusplus_10").password("Lubos").roles("USER")
        ;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers(HttpMethod.OPTIONS,"/**").permitAll()//allow CORS option calls
                .antMatchers("/v2/api-docs", "/configuration/ui", "/swagger-resources", "/configuration/security",
                        "/swagger-ui.html", "/webjars/**").hasRole("ADMIN")
                .anyRequest().authenticated()
                .and()
                .httpBasic()
                .and().csrf().disable();
    }
}