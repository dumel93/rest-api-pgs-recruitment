package rest.api.pgs.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Pageable;
import rest.api.pgs.entity.Archieve;
import rest.api.pgs.entity.User;


import java.util.List;

@Repository
public interface UserRepository extends PagingAndSortingRepository<User, Long> ,
                                        JpaRepository<User, Long>,
                                        JpaSpecificationExecutor<User> {

    User findByEmail(String email);

    @Query("SELECT new rest.api.pgs.entity.Archieve(u.firstName,u.lastName,t.name,t.description) "
            + "FROM User u  JOIN u.tasks t where t.isDone=true ")
    List<Archieve> findTasksDone();



}
