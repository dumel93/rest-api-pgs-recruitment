package rest.api.pgs.rest;


import io.swagger.annotations.ApiOperation;
import net.kaczmarzyk.spring.data.jpa.domain.Like;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Spec;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import rest.api.pgs.entity.Task;
import rest.api.pgs.entity.User;
import rest.api.pgs.repositories.TaskRepository;
import rest.api.pgs.repositories.UserRepository;
import rest.api.pgs.service.TaskService;
import rest.api.pgs.service.UserService;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.List;


@RestController
public class TaskRestController {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private TaskService taskService;

    @ApiOperation(value = "return list of all tasks/ pagination and sorting includes (?offset,limit,sortBy,orderBy) ")
    @Secured("ROLE_ADMIN")
    @GetMapping("/tasks")
    public ResponseEntity<List<Task>> getAllEmployees(@RequestParam(defaultValue = "0") Integer offset,
                                                      @RequestParam(defaultValue = "10") Integer limit,
                                                      @RequestParam(defaultValue = "id") String sortBy,
                                                      @RequestParam(defaultValue = "asc") String orderBy
    ){


        List<Task> list = taskService.getAllTasks(offset, limit, sortBy, orderBy);

        return new ResponseEntity<List<Task>>(list, new HttpHeaders(), HttpStatus.OK);
    }

    @ApiOperation(value = "search task by subject")
    @Secured("ROLE_ADMIN")
    @GetMapping(value = "/tasks/search")
    public Iterable<Task> filter1(
            @Spec(path = "subject", spec = Like.class) Specification<Task> spec) {

        return taskRepository.findAll(spec);
    }

    @ApiOperation(value = "search task by description")
    @Secured("ROLE_ADMIN")
    @GetMapping(value = "/tasks/search2")
    public Iterable<Task> filter2(
            @Spec(path = "description", spec = Like.class) Specification<Task> spec2) {

        return taskRepository.findAll(spec2);
    }

    @ApiOperation(value = "find task by id ")
    @Secured("ROLE_ADMIN")
    @GetMapping("/tasks/{id}")
    Task findOne(@PathVariable @Min(value = 1,message = "must be greater than or equal to 1") Long id) {
        return taskRepository.findOne(id);
    }

    @ApiOperation(value = "create task")
    @Secured("ROLE_ADMIN")
    @PostMapping("/tasks")
    @ResponseStatus(HttpStatus.CREATED)
    Task newTask(@RequestBody Task task) {
        taskRepository.save(task);
        return task;

    }

    @ApiOperation(value = "update task")
    @Secured("ROLE_ADMIN")
    @PutMapping("/tasks/{id}")
    Task saveOrUpdate(@Valid  @RequestBody Task task, @PathVariable Long id) {
        task.setId(id);
        taskService.updateTask(task);
        return task;
    }

    @ApiOperation(value = "delete task")
    @Secured("ROLE_ADMIN")
    @DeleteMapping("/tasks/{id}")
    void delOne(@PathVariable @Min(value = 1,message = "must be greater than or equal to 1") Long id) {
        Task task=taskRepository.findOne(id);
        taskRepository.delete(task);

    }


}
