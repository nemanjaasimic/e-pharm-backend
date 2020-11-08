package com.isa.epharm.model;

import com.isa.epharm.model.enumeration.Role;
import lombok.*;
import org.hibernate.annotations.Where;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Collection;
import java.util.Collections;
import java.util.UUID;

import static com.isa.epharm.util.ValidationConstants.Common.NAME_MAX_SIZE;
import static com.isa.epharm.util.ValidationConstants.User.*;

@Getter
@Setter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "`user`")
@Where(clause = "deleted='false'")
public class User extends BaseEntity implements UserDetails {

    @NotBlank
    @Size(max = USERNAME_MAX_SIZE)
    @Column(name = "username", unique = true)
    private String username;

    @NotBlank
    @Size(max = EMAIL_MAX_SIZE)
    @Column(name = "email", unique = true)
    private String email;

    @NotBlank
    @Size(min = PASSWORD_HASH_SIZE, max = PASSWORD_HASH_SIZE)
    @Column(name = "password")
    private String password;

    @NotBlank
    @Size(max = NAME_MAX_SIZE)
    @Column(name = "first_name")
    private String firstName;

    @NotBlank
    @Size(max = NAME_MAX_SIZE)
    @Column(name = "last_name")
    private String lastName;

    @NotBlank
    @Size(max = PHONE_NUMBER_MAX_SIZE)
    @Column(name = "phone_number", unique = true)
    private String phoneNumber;

    @NotNull
    @Column(name = "accepted")
    private Boolean accepted;

    @NotNull
    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    private Role role;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(new SimpleGrantedAuthority(role.toString()));
    }

    @Override
    public UUID getId() {
        return super.getId();
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return accepted;
    }
}
