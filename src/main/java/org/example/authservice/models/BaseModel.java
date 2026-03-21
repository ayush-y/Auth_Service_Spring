package org.example.authservice.models;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
@EntityListeners(AuditingEntityListener.class)
@MappedSuperclass
@Getter
@Setter
public abstract class BaseModel {

    @Id //This annotation make the id property a primary key of our table
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long Id;

//    @Column(nullable = false)
//    @CreatedDate // this annotation tells spring that only handle it for object creation
//    private LocalDateTime createdAt;

    @Column(nullable = false)
    @LastModifiedDate // this annotation tells spring that only handle it for object creation
    private LocalDateTime updatedAt;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;
}
