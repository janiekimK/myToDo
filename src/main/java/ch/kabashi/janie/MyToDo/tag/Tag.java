package ch.kabashi.janie.MyToDo.tag;

import lombok.Data;

import java.util.List;

import ch.kabashi.janie.MyToDo.task.Task;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;

@Data
@Entity
public class Tag {

    @Id
    @GeneratedValue
    private Long id;

    @Column(length = 50, nullable = false)
    private String name;

    @Column(length = 255)
    private String description;

    public Tag() {
    }

    @ManyToMany(mappedBy = "tags")
    private List<Task> tasks;

}
