package com.demo.fileupload.document.repository;

import com.demo.fileupload.document.domain.Document;
import com.demo.fileupload.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.Collection;
import java.util.Optional;

public interface DocumentRepository extends JpaRepository<Document, Long> {

    public Optional<Document> findByUserAndId(User user, Long id);

    public Page<Document> findAllByUser(User user, Pageable pageable);

    public Optional<Document> findByIdAndUserId(Long id, Long userId);
}
