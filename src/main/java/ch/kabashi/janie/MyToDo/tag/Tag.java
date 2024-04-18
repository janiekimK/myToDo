package ch.kabashi.janie.MyToDo.tag;

import lombok.Data;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

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
}
