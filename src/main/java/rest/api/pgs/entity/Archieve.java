package rest.api.pgs.entity;

import lombok.Data;

@Data
public class Archieve {

    private String firstName;
    private String lastName;
    private String taskDone;
    private String description;

    public Archieve(String firstName, String lastName, String taskDone, String description) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.taskDone = taskDone;
        this.description = description;
    }
}
