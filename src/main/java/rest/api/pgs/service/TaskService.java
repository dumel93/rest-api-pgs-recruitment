package rest.api.pgs.service;

import rest.api.pgs.entity.Task;
import rest.api.pgs.entity.User;

import java.util.List;

public interface TaskService {

    public Task saveTask(Task task);

    public void updateTask(Task task);


    public List<Task> getAllTasks(Integer pageNo, Integer pageSize, String sortBy, String orderBy);

    public List<Task> findAllTasks();
}
