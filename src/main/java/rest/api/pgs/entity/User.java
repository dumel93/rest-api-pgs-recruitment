package rest.api.pgs.entity;


import com.fasterxml.jackson.annotation.*;
import com.sun.istack.internal.NotNull;
import com.sun.istack.internal.Nullable;
import lombok.Data;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;



import javax.persistence.*;
import javax.sound.sampled.Clip;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Data
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy =
            GenerationType.IDENTITY)
    private Long id;


    @Column(
            length = 100,
            nullable = false)
    @NotEmpty(message = "Please provide a firstName")
    private String firstName;


    @Column(
            length = 100,
            nullable = true)
    @NotEmpty(message = "Please provide a lastName")
    private String lastName;

//    @JsonIgnoreProperties("user")
    @OneToMany(fetch = FetchType.EAGER, cascade = { CascadeType.ALL }, orphanRemoval=true,
            mappedBy = "activeUser")
    private List<Task> tasks= new ArrayList<>();



    @Email
    @Column(nullable = false, unique = true)
    private String email;


    @Column(name = "password")
    @Length(min = 5, message = "*Your password must have at least 5 characters")
    private String password;

    @JsonIgnore
    @Column(name = "active")
    private boolean active;


    @Nullable
    @ManyToMany( fetch = FetchType.EAGER)
    @JoinTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles;


}
