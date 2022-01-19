package com.cv.spring_workcv.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "save_job")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SaveJob {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @OneToOne(
            cascade = CascadeType.MERGE,
            fetch = FetchType.EAGER
    )
    @JoinColumn(name = "recruitment_id",referencedColumnName = "id")
    private Recruitment recruitment;

    @OneToOne(
            cascade = CascadeType.MERGE,
            fetch = FetchType.EAGER
    )
    @JoinColumn(name = "user_id",referencedColumnName = "id")
    private User user;
}
