package com.isa.epharm.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.domain.Persistable;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.ZonedDateTime;
import java.util.UUID;

@Getter
@Setter
@MappedSuperclass
@EqualsAndHashCode(of = {"id"})
public abstract class BaseEntity implements Persistable<UUID> {

    @Id
    @Column(name = "id")
    @Type(type = "pg-uuid")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @NotNull
    @Column(name = "deleted")
    private Boolean deleted;

    @NotNull
    @CreationTimestamp
    @Column(name = "time_created")
    private ZonedDateTime timeCreated;

    @NotNull
    @UpdateTimestamp
    @Column(name = "time_updated")
    private ZonedDateTime timeUpdated;

    @PrePersist
    private void prePersist() {
        this.timeCreated = ZonedDateTime.now();
        this.deleted = false;
    }

    @PreUpdate
    private void preUpdate() {
        this.timeUpdated = ZonedDateTime.now();
    }

    @Override
    public boolean isNew() {
        return this.getId() == null;
    }
}