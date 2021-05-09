package com.timesheet.entities;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "T_ROLE", indexes = { @Index(name = "ROLENAME_INDEX", columnList = "ROL_NAME") })
public class Role implements Serializable {

    @Id
    @EqualsAndHashCode.Include
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ROL_ID")
    private Long id;

    @Column(name = "ROL_NAME", unique = true, nullable = false, length = 30)
    @Size(min = 6, max = 30)
    private String name;

    @ManyToMany(mappedBy = "roles")
    private List<User> users;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "JT_ROLE_AUTHORITY",
            joinColumns = @JoinColumn(
                    name = "ROL_ID", referencedColumnName = "ROL_ID"),
            inverseJoinColumns = @JoinColumn(
                    name = "AUT_ID", referencedColumnName = "AUT_ID"),
            uniqueConstraints = @UniqueConstraint(columnNames = {
                    "ROL_ID", "AUT_ID" }))
    private List<Authority> authorities;
}
