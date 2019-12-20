package rest.api.pgs.service;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import rest.api.pgs.entity.Role;
import rest.api.pgs.entity.User;
import rest.api.pgs.repositories.RoleRepository;
import rest.api.pgs.repositories.UserRepository;

import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

@Service
@Transactional
public class UserServiceImpl implements  UserService {




    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    public UserServiceImpl(UserRepository repository) {
        this.userRepository=repository;
    }


    @Override
    public User saveUser(User user)  {

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setActive(true);
        Role userRole = roleRepository.findByName("ROLE_USER");
        user.setRoles(new HashSet<Role>(Arrays.asList(userRole)));
        userRepository.save(user);

        return user;

    }


    @Override
    public void saveAdmin(User user) {

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setActive(true);
        Role userRole = roleRepository.findByName("ROLE_ADMIN");
        user.setRoles(new HashSet<Role>(Arrays.asList(userRole)));
        userRepository.save(user);

    }

    @Override
    public List<User> getAllUsers(Integer pageNo, Integer pageSize, String sortBy, String orderBy) {


            if(orderBy.toUpperCase().equals("DESC")){
                Sort sortdesc = new Sort(new Sort.Order(Sort.Direction.DESC, sortBy));
                Pageable pageable = new PageRequest(pageNo, pageSize, sortdesc);
                return (List<User>) userRepository.findAll(pageable).getContent();
            }
            Sort sort = new Sort(new Sort.Order(Sort.Direction.ASC, sortBy));
            Pageable pageable = new PageRequest(pageNo, pageSize, sort);
            return (List<User>) userRepository.findAll(pageable).getContent();


    }

    @Override
    public List<User> findAllUsers() {
        return userRepository.findAll();
    }


    @Override
    public void updateUser(User user) {

        user.setId(user.getId());
        user.setFirstName(user.getFirstName());
        user.setLastName(user.getLastName());
        user.setEmail(user.getEmail());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setActive(true);
        Role userRole = roleRepository.findByName("ROLE_USER");
        user.setRoles(new HashSet<Role>(Arrays.asList(userRole)));
        userRepository.save(user);

    }
}
