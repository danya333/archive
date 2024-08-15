package com.mrv.archive.security;


import com.mrv.archive.model.User;
import com.mrv.archive.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JwtUserDetailsService implements UserDetailsService {

    private final UserService userService;

    @Override
    public UserDetails loadUserByUsername(
            final String username
    ) {
        User user = userService.getByUsername(username);
        return new JwtEntity(user);
    }

}