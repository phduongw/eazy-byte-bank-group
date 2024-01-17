package com.dcorp.hightech.acounts.entities;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.FieldDefaults;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

/**
 * @MappedSuperClass: To indicate to spring data JPA that this class is going act as a superclass for
 * all entities
 **/
@Getter
@Setter
@ToString
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class) // To listen when validation is boarded cast
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BaseEntity {

    @CreatedDate
    @Column(updatable = false)
    LocalDateTime createdAt;

    @CreatedBy
    @Column(updatable = false)
    String createdBy;

    //    insert table = false: To say to spring don't populate or update columns whenever it is trying
//    to insert a new record
    @LastModifiedDate
    @Column(insertable = false)
    LocalDateTime updatedAt;

    @LastModifiedBy
    @Column(insertable = false)
    String updatedBy;
}
