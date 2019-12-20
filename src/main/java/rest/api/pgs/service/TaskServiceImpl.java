package rest.api.pgs.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import rest.api.pgs.entity.Task;
import rest.api.pgs.entity.User;
import rest.api.pgs.repositories.TaskRepository;

import javax.transaction.Transactional;
import java.util.List;


@Service
@Transactional
public class TaskServiceImpl implements TaskService {

    @Autowired
    private TaskRepository taskRepository;
    @Override
    public Task saveTask(Task task) {
        taskRepository.save(task);
        return task;
    }

    @Override
    public void updateTask(Task task) {
        taskRepository.save(task);
    }

    @Override
    public List<Task> getAllTasks(Integer pageNo, Integer pageSize, String sortBy, String orderBy) {
        if(orderBy.toUpperCase().equals("DESC")){
            Sort sortdesc = new Sort(new Sort.Order(Sort.Direction.DESC, sortBy));
            Pageable pageable = new PageRequest(pageNo, pageSize, sortdesc);
            return  taskRepository.findAll(pageable).getContent();
        }
        Sort sort = new Sort(new Sort.Order(Sort.Direction.ASC, sortBy));
        Pageable pageable = new PageRequest(pageNo, pageSize, sort);
        return  taskRepository.findAll(pageable).getContent();
    }

    @Override
    public List<Task> findAllTasks() {
        return taskRepository.findAll();
    }
}
