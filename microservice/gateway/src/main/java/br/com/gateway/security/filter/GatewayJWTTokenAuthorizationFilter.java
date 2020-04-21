package br.com.gateway.security.filter;

import br.com.core.property.JWTConfiguration;
import br.com.filter.JWTTokenAuthorizationFilter;
import br.com.token.convert.TokenConverter;
import br.com.util.SecurityContextUtil;
import com.netflix.zuul.context.RequestContext;
import com.nimbusds.jwt.SignedJWT;
import lombok.SneakyThrows;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


public class GatewayJWTTokenAuthorizationFilter extends JWTTokenAuthorizationFilter {

    public GatewayJWTTokenAuthorizationFilter(JWTConfiguration jwtConfiguration, TokenConverter tokenConverter) {
        super(jwtConfiguration, tokenConverter);
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
        String signedToken = tokenConverter.decryptTOken(token);
        tokenConverter.validateTokenSignature(signedToken);

        if(jwtConfiguration.getType().equalsIgnoreCase("signed"))
            RequestContext.getCurrentContext().addZuulRequestHeader("Authorization", jwtConfiguration.getHeader().getPrefix() + signedToken);

        SecurityContextUtil.setSecurityContext(SignedJWT.parse(signedToken));

        chain.doFilter(httpServletRequest, httpServletResponse);
    }

}
