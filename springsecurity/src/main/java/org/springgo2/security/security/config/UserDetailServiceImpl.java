package org.springgo2.security.security.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springgo2.security.security.entity.ClientUser;
import org.springgo2.security.security.entity.ClientUserDetails;
import org.springgo2.security.security.repository.UserRepository;

import java.util.Optional;

@Component
public class UserDetailServiceImpl implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    /**
     * 定制登录校验
     * @param s
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        Optional<ClientUser> optional = userRepository.findByUsername(s);
        if(!optional.isPresent()) throw new RuntimeException("用户名错误");
        System.out.println(optional.get());
        return new ClientUserDetails(optional.get());
    }
}
