package ch.kabashi.janie.MyToDo.task;

import lombok.Data;

import java.time.LocalDate;
import java.util.List;

import ch.kabashi.janie.MyToDo.employee.Employee;
import ch.kabashi.janie.MyToDo.folder.Folder;
import ch.kabashi.janie.MyToDo.tag.Tag;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;

@Data
@Entity
public class Task {

    @Id
    @GeneratedValue
    private Long id;

    @Column(length = 100, nullable = false)
    private String title;

    @Column(length = 255)
    private String description;

    @Column(nullable = false)
    private LocalDate dueDate;

    @Column(nullable = false)
    private int priority;

    public Task() {
    }

    @ManyToOne
    @JoinColumn(name = "folder_id")
    private Folder folder;

    @ManyToOne
    @JoinColumn(name = "employee_id")
    private Employee employee;

    @ManyToMany
    @JoinTable(name = "task_tags",
           joinColumns = @JoinColumn(name = "task_id"),
           inverseJoinColumns = @JoinColumn(name = "tag_id"))
    private List<Tag> tags;

}
