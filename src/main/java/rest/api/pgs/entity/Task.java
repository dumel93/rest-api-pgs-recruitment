package rest.api.pgs.entity;


import com.fasterxml.jackson.annotation.*;
import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "tasks")
public class Task {

    @Id
    @GeneratedValue(strategy =
            GenerationType.IDENTITY)
    private Long id;



//    @Column(updatable=false)
//    private LocalDateTime created;

    @Column
    @NotEmpty(message = "can not be empty")
    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column
    private boolean isDone=false;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="user_id")
    private User activeUser;

}
