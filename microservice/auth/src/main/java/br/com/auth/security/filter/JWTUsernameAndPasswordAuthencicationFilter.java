package br.com.auth.security.filter;

import br.com.core.model.UserEntity;
import br.com.core.property.JWTConfiguration;
import br.com.token.creator.TokenCreator;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nimbusds.jose.*;
import com.nimbusds.jwt.SignedJWT;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;

public class JWTUsernameAndPasswordAuthencicationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;
    private final JWTConfiguration jwtConfiguration;
    private final TokenCreator tokenCreator;

    public JWTUsernameAndPasswordAuthencicationFilter(AuthenticationManager authenticationManager, JWTConfiguration jwtConfiguration, TokenCreator tokenCreator) {
        this.authenticationManager = authenticationManager;
        this.jwtConfiguration = jwtConfiguration;
        this.tokenCreator = tokenCreator;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        UserEntity user = null;

        try {
            user = new ObjectMapper().readValue(request.getInputStream(), UserEntity.class);
        } catch (JsonParseException e) {
            e.printStackTrace();
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (user == null)
            throw new UsernameNotFoundException("Unable to retrieve the username or password");

        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword(), Collections.emptyList());
        usernamePasswordAuthenticationToken.setDetails(user);
        return authenticationManager.authenticate(usernamePasswordAuthenticationToken);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        SignedJWT signedJWT = tokenCreator.creSignedJWT(authResult);
        String encrypetedToken = null;
        try {
            encrypetedToken = tokenCreator.encryptToken(signedJWT);
        } catch (JOSEException e) {
            e.printStackTrace();
        }
        response.addHeader("Access-Control-Expose-Headers", "XSRF-TOKEN" + jwtConfiguration.getHeader().getName());
        response.addHeader(jwtConfiguration.getHeader().getName(), jwtConfiguration.getHeader().getPrefix() + encrypetedToken);
    }

}
