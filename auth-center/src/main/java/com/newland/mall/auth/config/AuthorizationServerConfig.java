/*
 * Copyright 2020-2022 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.newland.mall.auth.config;

import com.newland.mall.auth.config.core.FormIdentityLoginConfigurer;
import com.newland.mall.auth.config.filter.AuthExceptionTranslationFilter;
import com.newland.mall.auth.config.handler.AuthAuthenticationFailureHandler;
import com.newland.mall.auth.config.handler.AuthAuthenticationSuccessHandler;
import com.newland.mall.auth.config.jwt.JwtAuthenticationConverter;
import com.newland.mall.auth.config.jwt.JwtAuthenticationProvider;
import com.newland.mall.auth.config.jwt.JwtRefreshAuthenticationConverter;
import com.newland.mall.auth.config.jwt.JwtRefreshAuthenticationProvider;
import com.newland.mall.auth.config.password.OAuth2PasswordAuthenticationConverter;
import com.newland.mall.auth.config.password.OAuth2PasswordAuthenticationProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.server.authorization.JdbcOAuth2AuthorizationService;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationService;
import org.springframework.security.oauth2.server.authorization.client.JdbcRegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configurers.OAuth2AuthorizationServerConfigurer;
import org.springframework.security.oauth2.server.authorization.settings.AuthorizationServerSettings;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenGenerator;
import org.springframework.security.oauth2.server.authorization.web.authentication.*;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.ExceptionTranslationFilter;
import org.springframework.security.web.authentication.AuthenticationConverter;
import org.springframework.security.web.util.matcher.RequestMatcher;

import java.util.Arrays;

import static org.springframework.security.config.Customizer.withDefaults;

/**
 * @author leellun
 */
@Configuration(proxyBeanMethods = false)
public class AuthorizationServerConfig {
    @Autowired
    private AuthenticationProvider authDaoAuthenticationProvider;

    @Bean
    public OAuth2AuthorizationService authorizationService(JdbcTemplate jdbcTemplate, RegisteredClientRepository registeredClientRepository) {
        return new JdbcOAuth2AuthorizationService(jdbcTemplate, registeredClientRepository);
    }

    @Bean
    public RegisteredClientRepository registeredClientRepository(JdbcTemplate jdbcTemplate) {
        JdbcRegisteredClientRepository registeredClientRepository = new JdbcRegisteredClientRepository(jdbcTemplate);
        return registeredClientRepository;
    }

    @Bean
    public SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(authorize -> authorize.anyRequest().authenticated())
                .formLogin(withDefaults());
        return http.build();
    }

    @Bean
    @Order(Ordered.HIGHEST_PRECEDENCE)
    public SecurityFilterChain authorizationServerSecurityFilterChain(HttpSecurity http, OAuth2AuthorizationService authorizationService) throws Exception {
        OAuth2AuthorizationServerConfigurer authorizationServerConfigurer = new OAuth2AuthorizationServerConfigurer();

        http.apply(authorizationServerConfigurer.tokenEndpoint((tokenEndpoint) -> {
            tokenEndpoint.accessTokenRequestConverter(accessTokenRequestConverter())
                    .accessTokenResponseHandler(new AuthAuthenticationSuccessHandler())
                    .errorResponseHandler(new AuthAuthenticationFailureHandler());
        }).clientAuthentication(oAuth2ClientAuthenticationConfigurer ->
                oAuth2ClientAuthenticationConfigurer.errorResponseHandler(new AuthAuthenticationFailureHandler())));
        RequestMatcher endpointsMatcher = authorizationServerConfigurer.getEndpointsMatcher();

        DefaultSecurityFilterChain securityFilterChain = http.securityMatcher(endpointsMatcher)
                .addFilterAfter(new AuthExceptionTranslationFilter(), ExceptionTranslationFilter.class)
                .authorizeRequests(authorizeRequests -> authorizeRequests.anyRequest().authenticated())
                .apply(authorizationServerConfigurer.authorizationService(authorizationService)
                        .authorizationServerSettings(AuthorizationServerSettings.builder().build()))
                // ????????????????????????????????????
                .and().apply(new FormIdentityLoginConfigurer()).and().build();

        // ?????????????????????????????????
        addCustomOAuth2GrantAuthenticationProvider(http, authorizationService);
        return securityFilterChain;
    }

    /**
     * ???????????????????????? </br>
     * client:username:uuid
     *
     * @return OAuth2TokenGenerator
     */

    /**
     * request -> xToken ?????????????????????
     *
     * @return DelegatingAuthenticationConverter
     */
    private AuthenticationConverter accessTokenRequestConverter() {
        return new DelegatingAuthenticationConverter(Arrays.asList(
                new OAuth2PasswordAuthenticationConverter(),
                new JwtAuthenticationConverter(),
                new JwtRefreshAuthenticationConverter(),
                new OAuth2RefreshTokenAuthenticationConverter(),
                new OAuth2ClientCredentialsAuthenticationConverter(),
                new OAuth2AuthorizationCodeAuthenticationConverter(),
                new OAuth2AuthorizationCodeRequestAuthenticationConverter()));
    }

    @Autowired
    private OAuth2TokenGenerator oAuth2TokenGenerator;
    @Autowired
    private JwtDecoder jwtDecoder;

    /**
     * ?????????????????????????????????
     * <p>
     * 1. ???????????? </br>
     * 2. ???????????? </br>
     */
    @SuppressWarnings("unchecked")
    private void addCustomOAuth2GrantAuthenticationProvider(HttpSecurity http, OAuth2AuthorizationService authorizationService) {
        AuthenticationManager authenticationManager = http.getSharedObject(AuthenticationManager.class);
        OAuth2PasswordAuthenticationProvider oAuth2PasswordAuthenticationProvider = new OAuth2PasswordAuthenticationProvider(authenticationManager, authorizationService, oAuth2TokenGenerator);
        JwtAuthenticationProvider jwtAuthenticationProvider = new JwtAuthenticationProvider(
                authenticationManager, oAuth2TokenGenerator);
        JwtRefreshAuthenticationProvider jwtRefreshAuthenticationProvider = new JwtRefreshAuthenticationProvider(authenticationManager, oAuth2TokenGenerator, jwtDecoder);

        http.authenticationProvider(authDaoAuthenticationProvider);
        // ?????? ???????????? token????????????
        http.authenticationProvider(oAuth2PasswordAuthenticationProvider);
        // ?????? jwt
        http.authenticationProvider(jwtAuthenticationProvider);
        http.authenticationProvider(jwtRefreshAuthenticationProvider);
    }
}
