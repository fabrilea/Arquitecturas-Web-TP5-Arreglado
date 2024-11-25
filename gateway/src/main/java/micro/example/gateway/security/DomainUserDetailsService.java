package micro.example.gateway.security;

import micro.example.gateway.entity.Authority;
import micro.example.gateway.entity.User;
import micro.example.gateway.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;
@Component("userDetailsService")
public class DomainUserDetailsService implements UserDetailsService {

    private final Logger log = LoggerFactory.getLogger(DomainUserDetailsService.class);

    private final UserRepository userRepository;

    public DomainUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(final String username) {
        log.debug("Attempting to authenticate user: {}", username);

        return userRepository.findOneWithAuthoritiesByUsernameIgnoreCase(username.toLowerCase())
                .map(this::createSpringSecurityUser)
                .orElseThrow(() -> {
                    log.error("Authentication failed: User '{}' not found.", username);
                    return new UsernameNotFoundException(String.format("User '%s' not found", username));
                });
    }

    private UserDetails createSpringSecurityUser(User user) {
        List<GrantedAuthority> grantedAuthorities = user.getAuthorities().stream()
                .map(authority -> new SimpleGrantedAuthority(authority.getAuthority()))
                .collect(Collectors.toList());

        log.debug("User '{}' has authorities: {}", user.getUsername(), grantedAuthorities);

        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                grantedAuthorities
        );
    }
}

