package com.example.library.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.hibernate.envers.Audited;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@EntityListeners(AuditingEntityListener.class)
@Audited
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "Authors")
public class AuthorEntity extends AuditableEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false,length = 100)
    private String name;

    @Column(unique = true,length = 150)
    private String email;

    @Column(length = 500)
    private String bio;

    @OneToMany(mappedBy = "author")
    private List<BookEntity> books=new ArrayList<>();
}
