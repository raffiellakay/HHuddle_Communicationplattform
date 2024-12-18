package com.knoettner.hhuddle.security.services;

import com.knoettner.hhuddle.models.MyUser;
import com.knoettner.hhuddle.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    UserRepository userRepository;

    @Override
    @Transactional
    //searchs for User in Repo
    public UserDetails loadUserByUsername(String mail) throws UsernameNotFoundException {
        MyUser user = userRepository.findByMail(mail)
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + mail));

        return UserDetailsImpl.build(user);
    }
}
