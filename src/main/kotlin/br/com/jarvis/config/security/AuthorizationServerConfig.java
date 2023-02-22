//package br.com.jarvis.config.security;
//
//import br.com.jarvis.service.auth.CustomUserDetailsService;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
//import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
//import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
//import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
//import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
//import org.springframework.security.oauth2.provider.token.DefaultAccessTokenConverter;
//import org.springframework.security.oauth2.provider.token.TokenEnhancer;
//import org.springframework.security.oauth2.provider.token.TokenStore;
//
//@Configuration
//@EnableAuthorizationServer
//public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {
//
//	private final String clientId;
//
//	private final String clientSecret;
//
//	private static final String GRANT_TYPE_PASS = "password";
//	private static final String AUTHORIZATION_CODE = "authorization_code";
//	private static final String REFRESH_TOKEN = "refresh_token";
//	private static final String IMPLICIT = "implicit";
//	private static final String ADMIN = "admin";
//	private static final String APP = "app";
//	private static final String SITE = "site";
//	private static final int ACCESS_TOKEN_VALIDITY_SECONDS = 8 * 60 * 60;
//
//	private final TokenStore tokenStore;
//
//	private final AuthenticationManager authenticationManager;
//
//	private final BCryptPasswordEncoder bCryptPasswordEncoder;
//
//	private final CustomUserDetailsService customUserDetailsService;
//
//
//	public AuthorizationServerConfig(
//			@Value("${spring.security.oauth2.client.clientId}") String clientId,
//			@Value("${spring.security.oauth2.client.clientSecret}") String clientSecret,
//			TokenStore tokenStore,
//			AuthenticationManager authenticationManager,
//			BCryptPasswordEncoder bCryptPasswordEncoder,
//			CustomUserDetailsService customUserDetailsService) {
//		this.clientId = clientId;
//		this.clientSecret = clientSecret;
//		this.tokenStore = tokenStore;
//		this.authenticationManager = authenticationManager;
//		this.bCryptPasswordEncoder = bCryptPasswordEncoder;
//		this.customUserDetailsService = customUserDetailsService;
//	}
//
//	@Override
//	public void configure(ClientDetailsServiceConfigurer configurer) throws Exception {
//		configurer
//				.inMemory()
//				.withClient(clientId)
//				.secret(bCryptPasswordEncoder.encode(clientSecret))
//				.authorizedGrantTypes(AuthorizationServerConfig.GRANT_TYPE_PASS, AuthorizationServerConfig.AUTHORIZATION_CODE, AuthorizationServerConfig.REFRESH_TOKEN, AuthorizationServerConfig.IMPLICIT)
//				.scopes(AuthorizationServerConfig.ADMIN, AuthorizationServerConfig.APP, AuthorizationServerConfig.SITE)
//				.accessTokenValiditySeconds(AuthorizationServerConfig.ACCESS_TOKEN_VALIDITY_SECONDS)
//				.refreshTokenValiditySeconds(0);
//	}
//
//	@Bean
//	public DefaultAccessTokenConverter accessTokenConverter() {
//		return new DefaultAccessTokenConverter();
//	}
//
//	@Bean
//	public TokenEnhancer tokenEnhancer() {
//		return new CustomTokenEnhancer();
//	}
//
//
//	@Override
//	public void configure(AuthorizationServerEndpointsConfigurer endpoints) {
//		endpoints.tokenStore(tokenStore)
//				.tokenEnhancer(tokenEnhancer())
//				.accessTokenConverter(accessTokenConverter())
//				.authenticationManager(authenticationManager)
//				.userDetailsService(customUserDetailsService);
//	}
//
//	@Override
//	public void configure(AuthorizationServerSecurityConfigurer oauthServer) {
//		oauthServer.checkTokenAccess("permitAll()");
//	}
//
//}
