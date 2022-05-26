package com.billwen.learning.imooc.imoocsecurity.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.Collection;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@With
@Data
@Entity
@Table(name = "mooc_users")
public class User implements UserDetails, Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 50, unique = true, nullable = false)
    private String username;

    @JsonIgnore
    @Column(length = 160, name = "password_hash", nullable = false)
    private String password;

    @Column(length = 255)
    private String email;

    @Column(length = 50, nullable = false)
    private String name;

    @Column(length = 20, nullable = false)
    private String mobile;

    private boolean enabled;

    @Column(name = "credentials_non_expired", nullable = false, columnDefinition = "boolean default true")
    private boolean credentialsNonExpired;

    @Column(name = "account_non_locked", nullable = false, columnDefinition = "boolean default true")
    private boolean accountNonLocked;

    @Column(name = "account_non_expired", nullable = false, columnDefinition = "boolean default true")
    private boolean accountNonExpired;

    @ManyToMany
    @JoinTable(name = "mooc_users_roles",
            joinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "role_id", referencedColumnName = "id")})
    @Fetch(FetchMode.JOIN)
    private Set<Role> authorities;

}
