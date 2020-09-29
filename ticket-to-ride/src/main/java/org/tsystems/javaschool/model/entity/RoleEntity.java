package org.tsystems.javaschool.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

/**
 * @author Trofim Kremen
 */
@Entity
@Table(name = "roles")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class RoleEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id")
    private int id;

    @Column(name = "role")
    private String role;

    @OneToMany(mappedBy = "roleEntity", fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    List<UserEntity> userEntityList;
}
