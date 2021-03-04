package com.task.manager.model;

import com.task.manager.model.audit.Auditable;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Date;

@Setter
@Getter
@NoArgsConstructor
@Entity
@Table(name = "task")
public class Task extends Auditable<String> {

    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    private Long id;
    private String description;
    private Status status;
    @ManyToOne
    private Project project;
    private Date dueDate;
}
