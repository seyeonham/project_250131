package com.project_250131.store.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Table(name = "store")
@Entity
public class StoreEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "storeName")
    private String storeName;

    private String continent;

    @Column(name = "roadAddress")
    private String roadAddress;

    @Column(name = "streetAddress")
    private String streetAddress;

    private String latitude;

    private String longitude;

    @Column(name = "phoneNumber")
    private String phoneNumber;

    @Column(name = "openHourWeekdays")
    private String openHourWeekdays;

    @Column(name = "openHourWeekends")
    private String openHourWeekends;

    @CreationTimestamp
    @Column(name = "createdAt")
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updatedAt")
    private LocalDateTime updatedAt;
}
