package com.mohirdev.mohirdev.security;

import com.mohirdev.mohirdev.domain.Authority;
import com.mohirdev.mohirdev.domain.User;
import com.mohirdev.mohirdev.exceptions.UserNotActiveException;
import com.mohirdev.mohirdev.repository.WorkersRepository;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Component("userDetailService")

public class CustomUserDetails implements UserDetailsService {
private final WorkersRepository workersRepository;

    public CustomUserDetails(WorkersRepository workersRepository) {
        this.workersRepository = workersRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        String loweCaseUsername = username.toLowerCase();

        return (UserDetails) workersRepository
                .findByUserName(loweCaseUsername)
                .map( user -> createSpringSecurity(loweCaseUsername, user))
                .orElseThrow(() -> new UserNotActiveException("User "+username+"  was not found in the database"));

    }
    private User createSpringSecurity(String username, User user){
        if (!user.isActived()) {
            throw new UserNotActiveException("User "+ username +" was not actived ");
        }
        List < GrantedAuthority > grantedAuthorities = user
                .getAuthorities()
                .stream()
                .map(authority -> new SimpleGrantedAuthority(authority.getName()))
                .collect(Collectors.toList());
        return new User(username, user.getPassword(), grantedAuthorities);
    }
}
