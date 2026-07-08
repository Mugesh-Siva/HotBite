package com.hexaware.hotbyte.config;

import com.hexaware.hotbyte.entity.Role;
import com.hexaware.hotbyte.entity.User;
import com.hexaware.hotbyte.repository.RoleRepository;
import com.hexaware.hotbyte.repository.UserRepository;
import com.hexaware.hotbyte.security.UserDetailsImpl;
import com.hexaware.hotbyte.util.JwtUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDateTime;

@Component
public class OAuth2LoginSuccessHandler implements AuthenticationSuccessHandler {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        
        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
        String email = oAuth2User.getAttribute("email");
        String name = oAuth2User.getAttribute("name");
        
        if (email == null) {
            email = oAuth2User.getAttribute("login") + "@github.com";
        }
        if (name == null) {
            name = oAuth2User.getAttribute("login");
        }

        User user = userRepository.findByEmail(email).orElse(null);
        if (user == null) {
            user = new User();
            user.setEmail(email);
            user.setFullName(name);
            user.setIsActive(true);
            user.setCreatedAt(LocalDateTime.now());
            user.setPasswordHash("OAUTH2_USER");
            user.setContactNumber("0000000000"); // Placeholder
            
            Role role = roleRepository.findById(2).orElse(null); // Assuming 2 is CUSTOMER
            user.setRole(role);
            
            user = userRepository.save(user);
        }

        UserDetailsImpl UserDetailsImpl = new UserDetailsImpl(user);
        String token = jwtUtil.generateToken(UserDetailsImpl);

        String frontendUrl = "http://localhost:3000/oauth2/callback?token=" + token + "&email=" + email;
        response.sendRedirect(frontendUrl);
    }
}
