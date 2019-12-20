package rest.api.pgs.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import rest.api.pgs.entity.Role;
import rest.api.pgs.entity.User;
import rest.api.pgs.repositories.UserRepository;


import java.util.*;

@Slf4j
public class SpringDataUserDetailsService implements UserDetailsService {

    private UserService userService;

    @Autowired
    private UserRepository userRepository;



    @Override
    public UserDetails loadUserByUsername(String email) {
        User user = userRepository.findByEmail(email);
        log.info(user.toString());

        if (user == null) {throw new UsernameNotFoundException(email); }
        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
        for (Role role : user.getRoles()) {
            grantedAuthorities.add(new SimpleGrantedAuthority(role.getName()));
        }

        User user1=new User();
        user1.setId(user.getId());
        user1.setFirstName(user.getFirstName());
        user1.setLastName(user.getLastName());
        user1.setActive(true);
        user1.setTasks(user.getTasks());

        return new CurrentUser(user.getEmail(),user.getPassword(),

                grantedAuthorities, user1);



    }

}
