package br.com.filter;

import br.com.core.property.JWTConfiguration;
import br.com.token.convert.TokenConverter;
import br.com.util.SecurityContextUtil;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jwt.SignedJWT;
import lombok.SneakyThrows;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.file.AccessDeniedException;
import java.text.ParseException;

import static org.apache.commons.lang3.StringUtils.equalsIgnoreCase;

public class JWTTokenAuthorizationFilter extends OncePerRequestFilter {

    protected final JWTConfiguration jwtConfiguration;
    protected final TokenConverter tokenConverter;

    public JWTTokenAuthorizationFilter(JWTConfiguration jwtConfiguration, TokenConverter tokenConverter) {
        this.jwtConfiguration = jwtConfiguration;
        this.tokenConverter = tokenConverter;
    }

    @SneakyThrows
    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain chain) throws ServletException, IOException {
        String header = httpServletRequest.getHeader(jwtConfiguration.getHeader().getName());

        if (header == null || !header.startsWith(jwtConfiguration.getHeader().getPrefix())) {
            chain.doFilter(httpServletRequest, httpServletResponse);
            return;
        }

        String token = header.replace(jwtConfiguration.getHeader().getPrefix(), "").trim();

        SecurityContextUtil.setSecurityContext(equalsIgnoreCase("signed", jwtConfiguration.getType()) ? validate(token) : decrypetValidating(token));
        chain.doFilter(httpServletRequest, httpServletResponse);
    }

    private SignedJWT decrypetValidating(String encrypetToken) throws ParseException, JOSEException, AccessDeniedException {
        String signedToken = tokenConverter.decryptTOken(encrypetToken);
        tokenConverter.validateTokenSignature(signedToken);
        return SignedJWT.parse(signedToken);
    }

    private SignedJWT validate(String signedToken) throws ParseException, JOSEException, AccessDeniedException {
        tokenConverter.validateTokenSignature(signedToken);
        return SignedJWT.parse(signedToken);
    }

}
