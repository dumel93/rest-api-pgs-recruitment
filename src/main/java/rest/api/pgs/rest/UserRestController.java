package rest.api.pgs.rest;


import io.swagger.annotations.ApiOperation;
import net.kaczmarzyk.spring.data.jpa.domain.Like;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Spec;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import rest.api.pgs.entity.Archieve;
import rest.api.pgs.entity.Task;
import rest.api.pgs.entity.User;
import rest.api.pgs.repositories.TaskRepository;
import rest.api.pgs.repositories.UserRepository;
import rest.api.pgs.service.TaskService;
import rest.api.pgs.service.UserService;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.ArrayList;
import java.util.List;



@Validated
@RestController
public class UserRestController {


    @Autowired
    private UserService userService;

    @Autowired
    private TaskService taskService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TaskRepository taskRepository;

    @ApiOperation(value = "Return list of all users, pagination and sorting includes (?offset,limit,sortBy,orderBy) ")
    @Secured("ROLE_ADMIN")
    @GetMapping("/users")
    public ResponseEntity<List<User>> getAllEmployees(@RequestParam(defaultValue = "0") Integer offset,
                                                      @RequestParam(defaultValue = "10") Integer limit,
                                                      @RequestParam(defaultValue = "id") String sortBy,
                                                      @RequestParam(defaultValue = "asc") String orderBy
                                                          ){


        List<User> list = userService.getAllUsers(offset, limit, sortBy, orderBy);

        return new ResponseEntity<List<User>>(list, new HttpHeaders(), HttpStatus.OK);
    }

    @ApiOperation(value = "Return list of all done tasks and name of user who made it ")
    @Secured("ROLE_ADMIN")
    @GetMapping("/users/archieve")
    List<Archieve> arch() {

        return userRepository.findTasksDone();

    }

    @ApiOperation(value = "search users by filter : firstName")
    @Secured("ROLE_ADMIN")
    @GetMapping(value = "/users/search")
    public Iterable<User> filter1(
            @Spec(path = "firstName", spec = Like.class) Specification<User> spec) {

        return userRepository.findAll(spec);
    }

    @ApiOperation(value = "search users by filter : lastName")
    @Secured("ROLE_ADMIN")
    @GetMapping(value = "/users/search2")
    public Iterable<User> filter2(
            @Spec(path = "lastName", spec = Like.class) Specification<User> spec2) {

        return userRepository.findAll(spec2);
    }

    @ApiOperation(value = "search users by filter : email")
    @Secured("ROLE_ADMIN")
    @GetMapping(value = "/users/search3")
    public Iterable<User> filter3(
            @Spec(path = "email", spec = Like.class) Specification<User> spec3) {

        return userRepository.findAll(spec3);
    }




    /*

 	USER – może operować tylko na swoich danych


     */


    @ApiOperation(value = " all info of authenticated user - your details")
    @Secured("ROLE_USER")
    @GetMapping("/users/me")
    User auth(@AuthenticationPrincipal UserDetails auth) {
        String emaill= auth.getUsername();
        User user= userRepository.findByEmail(emaill);
        return user;
    }


    @ApiOperation(value = " all tasks of authenticated user ")
    @Secured("ROLE_USER")
    @GetMapping("/users/me/tasks")
    List<Task> gettasks(@AuthenticationPrincipal UserDetails auth) {
        String emaill= auth.getUsername();
        User user= userRepository.findByEmail(emaill);
        return user.getTasks();
    }

    @ApiOperation(value = " all done tasks of authenticated user ")
    @Secured("ROLE_USER")
    @GetMapping("/users/me/archieve")
    List<Task> archieve(@AuthenticationPrincipal UserDetails auth) {
        String emaill= auth.getUsername();
        User user= userRepository.findByEmail(emaill);
        List<Task> tasks= new ArrayList<>();
        for(Task task: user.getTasks()){
            if(task.isDone()){
                tasks.add(task);
            }
        }

        return tasks;

    }


    @ApiOperation(value = " create your task")
    @Secured("ROLE_USER")
    @PostMapping("/users/me/task")
    User createTask(@AuthenticationPrincipal UserDetails auth, @Valid @RequestBody Task task) {
        String emaill= auth.getUsername();
        User user= userRepository.findByEmail(emaill);
        user.getTasks().add(task);
        userService.saveUser(user);
        task.setActiveUser(user);
        taskRepository.save(task);
        return user;
    }

    // made done task
    @ApiOperation(value = " select your task by id and make it done ")
    @Secured("ROLE_USER")
    @PostMapping("/users/me/task/{id}/done")
    User doneTask(@AuthenticationPrincipal UserDetails auth, @PathVariable long id) {
        String emaill= auth.getUsername();
        User user= userRepository.findByEmail(emaill);
        Task task=taskRepository.findOne(id);
        user.getTasks().remove(task);
        task.setDone(true);
        user.getTasks().add(task);
        userService.saveUser(user);
        task.setActiveUser(user);
        taskRepository.save(task);
        return user;
    }




//----------------------------------------------------------------

    @ApiOperation(value = "find user by id ")
    @Secured("ROLE_ADMIN")
    @GetMapping("/users/{id}")
    public User findOne(@PathVariable @Min(value = 1, message = "must be greater than or equal to 1") Long id) {
        return userRepository.findOne(id);
    }


    @ApiOperation(value = "list of tasks of user by user_id  ")
    @GetMapping("/users/{id}/tasks")
    List<Task> findTasks(@PathVariable @Min(value = 1,message = "must be greater than or equal to 1") Long id) {
        User user= userRepository.findOne(id);
        List<Task>  tasks= user.getTasks();
        return tasks;
    }

    @ApiOperation(value = "create user   ")
    @Secured("ROLE_ADMIN")
    @PostMapping("/users")
    @ResponseStatus(HttpStatus.CREATED)
    User newUser(   @RequestBody User user) {
        return userService.saveUser(user);
    }


    @ApiOperation(value = "update user")
    @Secured("ROLE_ADMIN")
    @PutMapping("/users/{id}")
    User saveOrUpdate(@Valid  @RequestBody User user, @PathVariable Long id) {
         user.setId(id);
         userService.updateUser(user);
         return user;
    }

    @ApiOperation(value = "delete user")
    @Secured("ROLE_ADMIN")
    @DeleteMapping("/users/{id}")
    void delOne(@PathVariable @Min(value = 1,message = "must be greater than or equal to 1") Long id) {
        User user=userRepository.findOne(id);
        userRepository.delete(user);

    }
    
    


    @EventListener(ApplicationReadyEvent.class)
    public void setUpPassForAllusers() {


        // admin has default pass:  'admin123' , all users have  default pass :'user123' all is bcrypted in db
        List<User> users= userService.findAllUsers();

        for(User user:users){
            user.setPassword("user123");
            userService.saveUser(user);
        }

        User admin = users.get(0);
        admin.setPassword("admin123");
        userService.saveAdmin(admin);



    }
}
