package com.hm.packer.application.security;

import com.hm.packer.application.security.encrypt.PackerRSA;
import com.hm.packer.application.security.encrypt.PackerSafeAES;
import com.hm.packer.application.security.provider.LicenseKeyAuthenticationProvider;
import com.hm.packer.application.security.provider.LoginAuthenticationProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.client.RestTemplate;

import javax.crypto.NoSuchPaddingException;
import java.security.*;
import java.util.Arrays;


@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private LoginAuthenticationProvider loginAuthenticationProvider;

    @Autowired
    private LicenseKeyAuthenticationProvider licenseKeyAuthenticationProvider;

    @Value("${packer-property.engineer-auth-serverIP}")
    private String engineerAuthServerIP;

    @Bean
    public PackerSafeAES safeRSA(RestTemplate restTemplate){
        PackerSafeAES safeAES = null;
        try {
            safeAES = new PackerSafeAES(new PackerRSA(engineerAuthServerIP));
            safeAES.connect(restTemplate);
            if (safeAES.isExchange())
                System.out.println("Key Exchange Success");
        }catch (Exception e){
            e.printStackTrace();
        }
        return safeAES;
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Override
    public void configure(WebSecurity web){
        web.ignoring().antMatchers("/css/**", "/script/**", "image/**", "/fonts/**", "lib/**");
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .authenticationProvider(loginAuthenticationProvider)
                .authenticationProvider(licenseKeyAuthenticationProvider);
    }

    protected PackerAuthenticationFilter authenticationFilter() throws Exception{
        FilterProcessingMatcher matcher = new FilterProcessingMatcher(Arrays.asList("/engineer/login/auth", "/licenseKey/auth"));
        PackerAuthenticationFilter filter = new PackerAuthenticationFilter(matcher);
        filter.setAuthenticationManager(super.authenticationManagerBean());
        return filter;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests()
                    .antMatchers("/").permitAll()
                    .anyRequest().authenticated()
                    .and()
                .formLogin()
                    .loginPage("/licenseKey")
                    .loginPage("/engineer/login")
                    .permitAll()
                    .and()
                .addFilterBefore(authenticationFilter(), UsernamePasswordAuthenticationFilter.class);
    }

}
