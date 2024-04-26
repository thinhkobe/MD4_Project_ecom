package ra.md4_project.security.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import ra.md4_project.security.principle.UserdetailServiceCustom;

import java.io.IOException;
@Component
public class JWTAuthTokenFilter extends OncePerRequestFilter {
    @Autowired
   private UserdetailServiceCustom userdetailServiceCustom;

    @Autowired
    private JWTProvider jwtProvider;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token=getTokenFormRequest(request);
        if (token!=null && jwtProvider.validateToken(token)){
            String username= jwtProvider.getUserBytoken(token);
            UserDetails userDetails = userdetailServiceCustom.loadUserByUsername(username);
            Authentication auth=new UsernamePasswordAuthenticationToken(
                    userDetails,null,userDetails.getAuthorities()
            );
            SecurityContextHolder.getContext().setAuthentication(auth);
        }
        filterChain.doFilter(request, response);
    }

    private String getTokenFormRequest(HttpServletRequest request) {
        String bearer = request.getHeader("Authorization");
        if (bearer!=null && bearer.startsWith("Bearer ")){
            return bearer.substring("Bearer ".length());
        }
        return null;
    }
}
