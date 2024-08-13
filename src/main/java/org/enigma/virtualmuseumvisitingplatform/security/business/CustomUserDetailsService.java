package org.enigma.virtualmuseumvisitingplatform.security.business;

import lombok.AllArgsConstructor;
import org.enigma.virtualmuseumvisitingplatform.entity.User;
import org.enigma.virtualmuseumvisitingplatform.repository.UserRepository;
import org.enigma.virtualmuseumvisitingplatform.security.entities.UserDetailsImpl;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private UserRepository userRepository;



    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + username));

        return UserDetailsImpl.build(user);
    }
}
