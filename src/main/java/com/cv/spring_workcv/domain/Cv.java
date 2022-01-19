package com.cv.spring_workcv.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "cv")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Cv {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "file_name")
    private String fileName;

//    @Column(name = "file_type")
//    private String fileType;
//
//    @Lob
//    private byte[] data;
//
//    public Cv(String fileName, String fileType, byte[] data) {
//        this.fileName = fileName;
//        this.fileType = fileType;
//        this.data = data;
//    }
//
//    public Cv(String fileName, String fileType, byte[] data, User user) {
//        this.fileName = fileName;
//        this.fileType = fileType;
//        this.data = data;
//        this.user = user;
//    }

    @OneToOne(
            cascade = CascadeType.MERGE,
            fetch = FetchType.EAGER
    )
    @JoinColumn(name = "user_id",referencedColumnName = "id")
    private User user;
}
