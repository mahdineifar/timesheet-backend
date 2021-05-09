package com.timesheet.entities;

import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Table(name = "T_USER", indexes = { @Index(name = "USERNAME_INDEX", columnList = "USR_USERNAME") })
@Entity
public class User implements UserDetails {

    @Id
    @EqualsAndHashCode.Include
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "USR_ID")
    private Long id;

    @Column(name = "USR_USERNAME", length = 30, unique = true, nullable = false)
    @Size(min = 5, max = 30)
    private String username;

    @Column(name = "USR_FIRST_NAME", length = 30)
    @Size(min = 3, max = 30)
    private String firstName;

    @Column(name = "USR_LAST_NAME", length = 30)
    @Size(min = 3, max = 30)
    private String lastName;

    @Column(name = "USR_PASSWORD", nullable = false)
    private String password;

    @Column(name = "USR_ENABLED", columnDefinition = "bit default 0")
    private boolean enabled;

    @Column(name = "USR_ACCOUNT_NOT_LOCKED", columnDefinition = "bit default 0")
    private boolean accountNonLocked;

    @Column(name = "USR_ACCOUNT_NON_EXPIRED", columnDefinition = "bit default 0")
    private boolean accountNonExpired;

    @Column(name = "USR_CREDENTIALS_NON_EXPIRED", columnDefinition = "bit default 0")
    private boolean credentialsNonExpired;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "JT_USER_ROLE",
            joinColumns = @JoinColumn(name = "USR_ID", referencedColumnName = "USR_ID"),
            inverseJoinColumns = @JoinColumn(name = "ROL_ID", referencedColumnName = "ROL_ID"),
            uniqueConstraints = @UniqueConstraint(columnNames = {
                    "USR_ID", "ROL_ID" }))
    private List<Role> roles;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        for (Role role : this.roles) {
            for(Authority authority: role.getAuthorities()){
                grantedAuthorities.add(new SimpleGrantedAuthority(authority.getName()));
            }
        }
        return grantedAuthorities;
    }

}
