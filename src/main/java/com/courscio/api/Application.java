package com.courscio.api;

import com.courscio.api.user.User;
import com.courscio.api.user.UserRepository;
import com.courscio.api.user.authentication.AuthenticationController;
import com.courscio.api.user.authentication.JwtAuthenticationFilter;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.zaxxer.hikari.HikariDataSource;
import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.boot.web.servlet.server.ConfigurableServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.transaction.annotation.Transactional;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import javax.servlet.ServletContext;
import javax.sql.DataSource;
import java.util.Collections;

/**
 * @author Jiupeng Zhang
 * @since 03/07/2019
 */
@SpringBootApplication
@EnableSwagger2
public class Application {
    public static final String VERSION_MAJOR = "1";
    public static final String VERSION_MINOR = "0";
    public static final String REVISION = "3";
    public static final String CONTEXT_PATH = "/api/v" + VERSION_MAJOR;
    public static final String G_CLIENT_ID = "1008678353700-5sm5h523ni8qugq62odmk5pn4j2s4brk.apps.googleusercontent.com";

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean("dataSource")
    @ConfigurationProperties(prefix = "app.datasource")
    public DataSource dataSource() {
        final HikariDataSource ds = new HikariDataSource();
        ds.setMaximumPoolSize(100);
        ds.setConnectionTimeout(30000);
        ds.setIdleTimeout(600000);
        ds.setMaxLifetime(1800000);
        ds.addDataSourceProperty("poolName", "Hikari Connection Pool");
        ds.setTransactionIsolation("TRANSACTION_REPEATABLE_READ");
        ds.setDriverClassName(com.mysql.cj.jdbc.Driver.class.getName());
        ds.setJdbcUrl("jdbc:mysql://localhost:3306/courscio?characterEncoding=UTF8&serverTimezone=UTC&useSSL=false");
        ds.addDataSourceProperty("user", "root");
        ds.addDataSourceProperty("password", "root");
        ds.addDataSourceProperty("cachePrepStmts", true);
        ds.addDataSourceProperty("prepStmtCacheSize", 250);
        ds.addDataSourceProperty("prepStmtCacheSqlLimit", 2048);
        ds.addDataSourceProperty("useServerPrepStmts", true);
        return ds;
    }

    @Bean("jackson2ObjectMapperBuilder")
    public Jackson2ObjectMapperBuilder jackson2ObjectMapperBuilder() {
        return new Jackson2ObjectMapperBuilder()
                .serializationInclusion(JsonInclude.Include.ALWAYS)
                .indentOutput(true);
    }

    @Bean("webServerFactoryCustomizer")
    public WebServerFactoryCustomizer<ConfigurableServletWebServerFactory> webServerFactoryCustomizer() {
        return factory -> factory.setContextPath(CONTEXT_PATH);
    }

    @Bean("apiDocumentation")
    public Docket apiDocumentation(ServletContext servletContext) {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.courscio.api"))
                .build()
                .apiInfo(new ApiInfoBuilder()
                        .title("Courscio API Center")
                        .version(VERSION_MAJOR + "." + VERSION_MINOR + "." + REVISION)
                        .description("\n" +
                                "This project subjects the MIT license:\n\n" +
                                "Copyright (c) 2019 Jiupeng Zhang (jiupeng.zhang@rochester.edu)\n\n" +
                                "Permission is hereby granted, free of charge, to any person obtaining a copy " +
                                "of this software and associated documentation files (the \"Software\"), to deal " +
                                "in the Software without restriction, including without limitation the rights " +
                                "to use, copy, modify, merge, publish, distribute, sublicense, and/or sell " +
                                "copies of the Software, and to permit persons to whom the Software is " +
                                "furnished to do so, subject to the following conditions:\n\n" +
                                "The above copyright notice and this permission notice shall be included in all " +
                                "copies or substantial portions of the Software.\n\n" +
                                "THE SOFTWARE IS PROVIDED \"AS IS\", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR " +
                                "IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, " +
                                "FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE " +
                                "AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER " +
                                "LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, " +
                                "OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE " +
                                "SOFTWARE.\n")
                        .build());
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public GoogleIdTokenVerifier googleIdTokenVerifier() {
        return new GoogleIdTokenVerifier.Builder(new NetHttpTransport(), new JacksonFactory())
                .setAudience(Collections.singletonList(G_CLIENT_ID))
                .build();
    }
}

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(
        prePostEnabled = true,
        securedEnabled = true,
        jsr250Enabled = true
)
class SecurityConfig extends WebSecurityConfigurerAdapter {

    private UserDetailsService userDetailsService;

    private BCryptPasswordEncoder encoder;

    public SecurityConfig(UserDetailsService userDetailsService, BCryptPasswordEncoder encoder) {
        this.userDetailsService = new UserDetailsService() {
            @Autowired
            private UserRepository userRepository;

            @Override
            @Transactional
            public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
                User user = userRepository.findByEmail(email);
                if (user == null) throw new UsernameNotFoundException(email);
                return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), Collections.emptyList());
            }
        };
        this.encoder = encoder;
    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(encoder);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable()
                .authorizeRequests()
                .antMatchers(HttpMethod.POST, AuthenticationController.PATH).permitAll()
                .mvcMatchers(Application.CONTEXT_PATH).permitAll()
//                .mvcMatchers("/**/teaching/**").permitAll()
//                .mvcMatchers("/**/professor/**").permitAll()
//                .mvcMatchers("/**/course/**").permitAll()
//                .mvcMatchers("/**/user/**").authenticated()
//                .mvcMatchers("/**/cart/**").authenticated()
//                .mvcMatchers("/**/schedule/**").authenticated()
                .and()
                .exceptionHandling().authenticationEntryPoint(authenticationEntryPoint()).and()
                .formLogin().successHandler(successHandler()).failureHandler(failureHandler()).and()
                .addFilterBefore(new JwtAuthenticationFilter(authenticationManager()), UsernamePasswordAuthenticationFilter.class)
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }

    private AuthenticationEntryPoint authenticationEntryPoint() {
        return (request, response, e) -> response.sendError(Response.SC_UNAUTHORIZED, "Unauthorized");
    }

    private AuthenticationSuccessHandler successHandler() {
        return (request, response, authentication) -> {
            System.out.println(authentication);
            response.sendError(Response.SC_OK, "OK");
        };
    }

    private AuthenticationFailureHandler failureHandler() {
        return (request, response, e) -> response.sendError(Response.SC_FORBIDDEN, "Forbidden");
    }
}