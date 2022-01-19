package com.cv.spring_workcv.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "recruitment")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Recruitment {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "title")
    private String title;

    @Column(name = "salary")
    private String salary;

    @Column(name = "quantity")
    private int quantity;

    @Column(name = "type")
    private String type;

    @Column(name = "experience")
    private String experience;

    @Column(name = "address")
    private String address;

    @Column(name = "rank")
    private String rank;

    @Column(name = "deadline")
    private String deadline;

    @Column(name = "description")
    private String description;

    @Column(name = "status")
    private int status;

    @Column(name = "view",columnDefinition = "int default 0")
    private int view;

    @ManyToOne
    @JoinColumn(name = "company_id",referencedColumnName = "id")
    private Company company;

    @ManyToOne
    @JoinColumn(name = "category_id",referencedColumnName = "id")
    private Category category;

    @Column(name = "createdAt")
    private String createdAt;
}
