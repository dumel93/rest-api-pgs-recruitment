package rest.api.pgs.service;

import rest.api.pgs.entity.User;

import java.util.List;




public interface UserService {

    public User saveUser(User user);

    public void updateUser(User user);

    public void saveAdmin(User user);

    public List<User> getAllUsers(Integer pageNo, Integer pageSize, String sortBy, String orderBy);

    public List<User> findAllUsers();

}
