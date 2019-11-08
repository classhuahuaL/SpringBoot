package org.springgo2.security.security.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springgo2.security.security.entity.ClientUser;
import org.springgo2.security.security.entity.ClientUserDetails;
import org.springgo2.security.security.repository.UserRepository;

@RestController
public class UserController {

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    UserRepository userRepository;

    @GetMapping("/findByUserInfo")
    public ClientUser findByUserInfo(){
        // 获取当前登录用户信息 该类使用ThreadLocal
        ClientUserDetails clientUserDetails = (ClientUserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(clientUserDetails == null) throw new RuntimeException("当前用户未登录");
        ClientUser clientUser = clientUserDetails.getClientUser();
        return clientUser;
    }

    @GetMapping("/addUser")
    public String addUser(@RequestParam String userName,@RequestParam String password){
        ClientUser clientUser = new ClientUser();
        clientUser.setUsername(userName);
        clientUser.setPassword(passwordEncoder.encode(password));
        userRepository.save(clientUser);
        return "添加成功";
    }
}
