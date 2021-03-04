package com.task.manager.model;

import com.task.manager.model.audit.Auditable;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "resetPasswordHistory")
public class ResetPasswordHistory extends Auditable<String> implements Serializable {
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    private Long id;
    private String remarks;
    private Long applicationUserId;
}
