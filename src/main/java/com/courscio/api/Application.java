package com.courscio.api;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.boot.web.servlet.server.ConfigurableServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import javax.servlet.ServletContext;
import javax.sql.DataSource;

/**
 * @author Jiupeng Zhang
 * @since 03/07/2019
 */
@SpringBootApplication
@EnableSwagger2
public class Application {
    public static final String VERSION_MAJOR = "1";
    public static final String VERSION_MINOR = "0.1";

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
        return factory -> factory.setContextPath("/api/v" + VERSION_MAJOR);
    }

    @Bean("apiDocumentation")
    public Docket apiDocumentation(ServletContext servletContext) {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.courscio.api"))
                .build()
                .apiInfo(new ApiInfoBuilder()
                        .title("Courscio API Center")
                        .version(VERSION_MAJOR + "." + VERSION_MINOR)
                        .description("\n" +
                                "This project subjects the MIT license:\n" +
                                "\n" +
                                "Copyright (c) 2019 Jiupeng Zhang (jiupeng.zhang@rochester.edu)\n" +
                                "\n" +
                                "Permission is hereby granted, free of charge, to any person obtaining a copy\n" +
                                "of this software and associated documentation files (the \"Software\"), to deal\n" +
                                "in the Software without restriction, including without limitation the rights\n" +
                                "to use, copy, modify, merge, publish, distribute, sublicense, and/or sell\n" +
                                "copies of the Software, and to permit persons to whom the Software is\n" +
                                "furnished to do so, subject to the following conditions:\n" +
                                "\n" +
                                "The above copyright notice and this permission notice shall be included in all\n" +
                                "copies or substantial portions of the Software.\n" +
                                "\n" +
                                "THE SOFTWARE IS PROVIDED \"AS IS\", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR\n" +
                                "IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,\n" +
                                "FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE\n" +
                                "AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER\n" +
                                "LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,\n" +
                                "OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE\n" +
                                "SOFTWARE.\n")
                        .build());
    }
}
