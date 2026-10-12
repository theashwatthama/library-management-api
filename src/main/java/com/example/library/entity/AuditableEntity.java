package com.example.library.entity;

import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.envers.Audited;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDate;
import java.time.LocalDateTime;

@MappedSuperclass
@Audited
@Getter
@Setter
public class AuditableEntity {

    @CreatedDate
    private LocalDate createdDate;

    @LastModifiedDate
    private LocalDateTime updatedTime;

    @CreatedBy
    private String createdBy;

    @LastModifiedBy
    private String updatedBy;
}
