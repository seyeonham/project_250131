package com.project_250131.continent.entity;

import jakarta.persistence.*;
import lombok.*;

@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Table(name = "continent")
@Entity
public class ContinentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String continent;

    @Column(name = "imagePath")
    private String imagePath;
}
