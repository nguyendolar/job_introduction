package com.cv.spring_workcv.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "applypost")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApplyPost {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name_cv")
    private String nameCv;

    @Column(name = "text")
    private String text;

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

    @Column(name = "createdAt")
    private String createdAt;

    @Column(name = "status")
    private int status;
}
