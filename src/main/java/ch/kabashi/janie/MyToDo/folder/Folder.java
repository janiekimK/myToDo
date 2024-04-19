package ch.kabashi.janie.MyToDo.folder;

import lombok.Data;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;


@Data
@Entity
public class Folder {

    @Id
    @GeneratedValue
    private Long id;

    @Column(length = 100, nullable = false)
    private String title;

    @Column(length = 255)
    private String description;

    public Folder() {
    }

}
