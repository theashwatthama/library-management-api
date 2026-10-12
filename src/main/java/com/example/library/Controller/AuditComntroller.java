package com.example.library.Controller;

import com.example.library.entity.AuthorEntity;
import jakarta.persistence.EntityManagerFactory;
import org.hibernate.envers.AuditReader;
import org.hibernate.envers.AuditReaderFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/audit")
public class AuditComntroller {
    @Autowired
    private EntityManagerFactory entityManagerFactory;

    @GetMapping(path = "/author/{authorid}")
    List<AuthorEntity> getAuthorRevision(@PathVariable Long authorid){
        AuditReader reader= AuditReaderFactory.get(entityManagerFactory.createEntityManager());

        List<Number> revisions=reader.getRevisions(AuthorEntity.class,authorid);
        return revisions
                .stream()
                .map(revisionNumber->reader.find(AuthorEntity.class,authorid,revisionNumber))
                .collect(Collectors.toList());
    }
}
