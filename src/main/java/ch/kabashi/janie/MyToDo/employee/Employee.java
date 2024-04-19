package ch.kabashi.janie.MyToDo.employee;

import lombok.Data;

import java.util.List;

import ch.kabashi.janie.MyToDo.task.Task;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

@Data
@Entity
public class Employee {

    @Id
    @GeneratedValue
    private Long id;

    @Column(length = 100, nullable = false)
    @Size(max = 100)
    @NotEmpty
    private String name;

    @Column(length = 100, nullable = false)
    @Size(max = 100)
    @NotEmpty
    private String firstName;

    public Employee() {
    }

    @OneToMany(mappedBy = "assignee")
    private List<Task> assignedTasks;

}
