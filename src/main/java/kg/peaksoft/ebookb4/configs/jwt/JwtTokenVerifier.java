package kg.peaksoft.ebookb4.configs.jwt;

import kg.peaksoft.ebookb4.configs.JwtConfig;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
@AllArgsConstructor
public class JwtTokenVerifier extends OncePerRequestFilter {
    private JwtUtils jwtUtils;
    private JwtConfig jwtConfig;
    private UserDetailsService userDetailsService;
    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        String header = request.getHeader(jwtConfig.getAuthorizationHeader());
        String email = null;
        String token = null;

        if (header != null) {
            if (header.startsWith(jwtConfig.getTokenPrefix() + " ")) {
                header = header.substring(7);
            }
            email = jwtUtils.getEmailFromToken(header);
            token = header;
        }

             if (email !=null) {
            UserDetails userDetails = userDetailsService.loadUserByUsername(email);

            if (jwtUtils.validateJWTToken(token)) {
                UsernamePasswordAuthenticationToken upat = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities()
                );
                upat.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(upat);
            }
        }

        filterChain.doFilter(request, response);

    }
    }


