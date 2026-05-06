package com.example.umcspringbootstudy.global.auth;

import com.example.umcspringbootstudy.user.entity.User;
import com.example.umcspringbootstudy.user.exception.UserException;
import com.example.umcspringbootstudy.user.exception.code.UserErrorCode;
import com.example.umcspringbootstudy.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(username)
                .orElseThrow(() -> new UserException(UserErrorCode.USER_NOT_FOUND));
        return new CustomUserDetails(user);
    }
}
