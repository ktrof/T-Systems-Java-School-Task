package org.tsystems.javaschool.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "train")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TrainEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id")
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "tonnage")
    private int tonnage;

    @Column(name = "technical_speed")
    private int technicalSpeed;

}
