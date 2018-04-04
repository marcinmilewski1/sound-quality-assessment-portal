package com.sqap.auth.config;

import com.sqap.auth.domain.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@Slf4j
public class CustomUserDetailsService implements UserDetailsService {

    private UserRepository userRepository;

//    @Autowired
    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try {
            return this.userRepository.loadUserByUsername(username);
        } catch (EmptyResultDataAccessException e) {
            log.debug("No user found with username: " + username);
            throw new UsernameNotFoundException("No user found with username: " + username);
        } catch (IncorrectResultSizeDataAccessException e) {
            log.error("Database inconsistency, more than one record with username: " + username);
            throw new UsernameNotFoundException("Database inconsistency, more than one record with username: " + username);
        } catch (DataAccessException e) {
            log.error("Database error");
            throw new UsernameNotFoundException("Database error");
        }
    }
}
