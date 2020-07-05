package com.demo.fileupload.document.controller;

import com.demo.fileupload.document.domain.Document;
import com.demo.fileupload.document.service.DocumentService;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.util.Collection;

@Slf4j
@RestController
public class DocumentController {

    private DocumentService documentService;

    @Autowired
    public DocumentController(final DocumentService documentService) {
        this.documentService = documentService;
    }

    @PreAuthorize("hasAuthority('ROLE_USER')")
    @PostMapping(value = "/users/{userId}/documents", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Document> saveDocument(@PathVariable (value = "userId") Long userId, @Valid @RequestBody Document document) {
        document = documentService.save(document, userId);
        return ResponseEntity.created(URI.create("/users/" + userId + "/document/" + document.getId())).build();
    }

    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN', 'ROLE_WORKER')")
    @GetMapping(value = "/users/{userId}/document/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Document> getDocument(@PathVariable(value = "userId") final Long userId, @PathVariable(value = "id") final Long id) {
        final Document document = documentService.get(id, userId);
        return ResponseEntity.ok().body(document);
    }

    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
    @GetMapping(value = "/users/{userId}/documents", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Page<Document>> listDocuments(@PathVariable(value = "userId") final Long userId, Pageable pageable) {
        final Page<Document> documents = documentService.list(userId, pageable);
        return ResponseEntity.ok().body(documents);
    }

    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
    @DeleteMapping(value = "/users/{userId}/document/{documentId}")
    public ResponseEntity<?> deleteDocument(@PathVariable (value = "userId") Long userId,
                                           @PathVariable (value = "documentId") Long documentId) {
        documentService.delete(documentId, userId);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN', 'ROLE_WORKER')")
    @PatchMapping(value = "/users/{userId}/document/{documentId}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> patchDocument(@PathVariable (value = "userId") Long userId,
                                          @PathVariable (value = "documentId") Long documentId,
                                          @RequestBody Document document) {
        Document doc = documentService.updateStatus(documentId, userId, document.getStatus());
        return ResponseEntity.ok().body(doc);
    }
}
