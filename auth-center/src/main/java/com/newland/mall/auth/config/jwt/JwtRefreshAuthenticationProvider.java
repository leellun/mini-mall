package com.newland.mall.auth.config.jwt;

import com.newland.mall.auth.common.AuthConstant;
import com.newland.mall.auth.common.GrantType;
import com.newland.mall.auth.common.TokenType;
import com.newland.mall.auth.model.AuthUser;
import com.newland.mall.auth.utils.OAuth2AuthenticationProviderUtils;
import com.newland.mall.auth.utils.OAuth2EndpointUtils;
import com.newland.mall.model.LoginUser;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.core.*;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.server.authorization.OAuth2TokenType;
import org.springframework.security.oauth2.server.authorization.authentication.OAuth2AccessTokenAuthenticationToken;
import org.springframework.security.oauth2.server.authorization.authentication.OAuth2ClientAuthenticationToken;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.context.AuthorizationServerContextHolder;
import org.springframework.security.oauth2.server.authorization.token.DefaultOAuth2TokenContext;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenContext;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenGenerator;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 自定义jwt 认证提供，添加刷新token
 * Author: leell
 * Date: 2023/2/14 19:50:34
 */
public final class JwtRefreshAuthenticationProvider implements AuthenticationProvider {
    private static final String ERROR_URI = "https://datatracker.ietf.org/doc/html/rfc6749#section-5.2";
    private final Log logger = LogFactory.getLog(getClass());

    private JwtDecoder jwtDecoder;
    private final OAuth2TokenGenerator<? extends OAuth2Token> tokenGenerator;
    private final AuthenticationManager authenticationManager;

    public JwtRefreshAuthenticationProvider(AuthenticationManager authenticationManager, OAuth2TokenGenerator<? extends OAuth2Token> tokenGenerator, @NonNull JwtDecoder jwtDecoder) {
        this.authenticationManager = authenticationManager;
        this.tokenGenerator = tokenGenerator;
        this.jwtDecoder = jwtDecoder;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        JwtRefreshAuthenticationToken resourceOwnerPasswordAuthentication =
                (JwtRefreshAuthenticationToken) authentication;

        OAuth2ClientAuthenticationToken clientPrincipal =
                OAuth2AuthenticationProviderUtils.getAuthenticatedClientElseThrowInvalidClient(resourceOwnerPasswordAuthentication);
        RegisteredClient registeredClient = clientPrincipal.getRegisteredClient();

        if (!registeredClient.getAuthorizationGrantTypes().stream().map(AuthorizationGrantType::getValue).collect(Collectors.toList()).contains(GrantType.JWT.getValue())) {
            throw new OAuth2AuthenticationException(OAuth2ErrorCodes.UNAUTHORIZED_CLIENT);
        }

        UsernamePasswordAuthenticationToken usernamePasswordAuthentication = null;
        try {
            Jwt jwt = jwtDecoder.decode(resourceOwnerPasswordAuthentication.getRefreshToken());
            Map<String, Object> params = (Map<String, Object>) jwt.getClaims().get(AuthConstant.PRINCIPAL);
            LoginUser loginUser = new LoginUser();
            BeanUtils.copyProperties(params, loginUser);
            AuthUser authUser = new AuthUser(loginUser);
            UsernamePasswordAuthenticationToken result = UsernamePasswordAuthenticationToken.authenticated(authUser,
                    authentication.getCredentials(), new ArrayList<>());
            result.setDetails(authentication.getDetails());
            usernamePasswordAuthentication = result;
            logger.debug("[Herodotus] |- Resource Owner Password username and password authenticate success ");
        } catch (Exception ase) {
            ase.printStackTrace();
            OAuth2EndpointUtils.throwError(
                    OAuth2ErrorCodes.INVALID_GRANT,
                    ase.getMessage(),
                    OAuth2EndpointUtils.ACCESS_TOKEN_REQUEST_ERROR_URI);
        }
        Set<String> authorizedScopes = registeredClient.getScopes();
        if (!CollectionUtils.isEmpty(resourceOwnerPasswordAuthentication.getScopes())) {
            for (String requestedScope : resourceOwnerPasswordAuthentication.getScopes()) {
                if (!registeredClient.getScopes().contains(requestedScope)) {
                    throw new OAuth2AuthenticationException(OAuth2ErrorCodes.INVALID_SCOPE);
                }
            }
            authorizedScopes = new LinkedHashSet<>(resourceOwnerPasswordAuthentication.getScopes());
        }

        DefaultOAuth2TokenContext.Builder tokenContextBuilder = DefaultOAuth2TokenContext.builder()
                .registeredClient(registeredClient)
                .principal(usernamePasswordAuthentication)
                .authorizedScopes(authorizedScopes)
                .tokenType(OAuth2TokenType.ACCESS_TOKEN)
                .authorizationServerContext(AuthorizationServerContextHolder.getContext())
                .authorizationGrantType(AuthorizationGrantType.PASSWORD)
                .authorizationGrant(resourceOwnerPasswordAuthentication);

        OAuth2TokenContext tokenContext = tokenContextBuilder.tokenType(new OAuth2TokenType(TokenType.JWT.getValue())).build();
        OAuth2Token generatedAccessToken = this.tokenGenerator.generate(tokenContext);
        if (generatedAccessToken == null) {
            OAuth2Error error = new OAuth2Error(OAuth2ErrorCodes.SERVER_ERROR,
                    "The token generator failed to generate the access token.", ERROR_URI);
            throw new OAuth2AuthenticationException(error);
        }
        OAuth2AccessToken accessToken = new OAuth2AccessToken(OAuth2AccessToken.TokenType.BEARER,
                generatedAccessToken.getTokenValue(), generatedAccessToken.getIssuedAt(),
                generatedAccessToken.getExpiresAt(), tokenContext.getAuthorizedScopes());

        OAuth2RefreshToken refreshToken = null;
        if (registeredClient.getAuthorizationGrantTypes().stream().map(AuthorizationGrantType::getValue).collect(Collectors.toList()).contains(GrantType.JWT_REFRESH_TOKEN.getValue()) &&
                !clientPrincipal.getClientAuthenticationMethod().equals(ClientAuthenticationMethod.NONE)) {
            tokenContext = tokenContextBuilder.tokenType(new OAuth2TokenType(TokenType.JWT_REFRESH_TOKEN.getValue())).build();
            OAuth2Token generatedRefreshToken = this.tokenGenerator.generate(tokenContext);
            if (!(generatedRefreshToken instanceof OAuth2RefreshToken)) {
                OAuth2Error error = new OAuth2Error(OAuth2ErrorCodes.SERVER_ERROR,
                        "The token generator failed to generate the refresh token.", ERROR_URI);
                throw new OAuth2AuthenticationException(error);
            }
            refreshToken = (OAuth2RefreshToken) generatedRefreshToken;
        }

        Map<String, Object> additionalParameters = Collections.emptyMap();
        return new OAuth2AccessTokenAuthenticationToken(registeredClient, clientPrincipal, accessToken, refreshToken, additionalParameters);
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return JwtRefreshAuthenticationToken.class.isAssignableFrom(authentication);
    }

}
