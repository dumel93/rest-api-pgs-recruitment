package rest.api.pgs.repositories;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import rest.api.pgs.entity.Task;

import java.util.List;

@Repository
public interface TaskRepository extends PagingAndSortingRepository<Task, Long>,
        JpaRepository<Task, Long>,
        JpaSpecificationExecutor<Task>,
        CrudRepository<Task,Long> {

    List<Task> findAll(Specification<Task> spec);


    Task save(Task entity);


}
