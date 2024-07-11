package dio.spring_security_jwt.security;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;

import static dio.spring_security_jwt.security.SecurityConfig.KEY;
import static dio.spring_security_jwt.security.SecurityConfig.PREFIX;

public class JWTFilter extends OncePerRequestFilter {

    private static final Logger logger = LoggerFactory.getLogger(JWTFilter.class);

    @Override
    protected void doFilterInternal(HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain)
            throws ServletException, IOException {
        //obtem o token da request com AUTHORIZATION
        String token =  request.getHeader(JWTCreator.HEADER_AUTHORIZATION);

        // Adiciona logs para depuração
        System.out.println("Received token: " + token);
        System.out.println("Prefix: " + SecurityConfig.PREFIX);
        System.out.println("Key: " + SecurityConfig.KEY);


        //esta implementação só esta validando a integridade do token
        try {
            if(token!=null && !token.isEmpty()) {
                JWTObject tokenObject = JWTCreator.create(token, SecurityConfig.PREFIX, SecurityConfig.KEY);

                System.out.println(tokenObject);

                List<SimpleGrantedAuthority> authorities = authorities(tokenObject.getRoles());

                UsernamePasswordAuthenticationToken userToken =
                        new UsernamePasswordAuthenticationToken(
                                tokenObject.getSubject(),
                                null,
                                authorities);

                SecurityContextHolder.getContext().setAuthentication(userToken);

                response.addHeader(JWTCreator.HEADER_AUTHORIZATION, PREFIX + " " + token);

            }else {
                SecurityContextHolder.clearContext();
            }
            filterChain.doFilter(request, response);
        }catch (ExpiredJwtException | UnsupportedJwtException | MalformedJwtException | SignatureException e) {
            logger.error("Unable to create JWT token", e);
            response.setStatus(HttpStatus.FORBIDDEN.value());
        }
    }
    private List<SimpleGrantedAuthority> authorities(List<String> roles){
        return roles.stream().map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }
}
