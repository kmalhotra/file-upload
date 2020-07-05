package com.demo.fileupload.document.service;

import com.demo.fileupload.common.exception.ResourceNotFoundException;
import com.demo.fileupload.document.domain.Document;
import com.demo.fileupload.document.domain.DocumentStatus;
import com.demo.fileupload.document.repository.DocumentRepository;
import com.demo.fileupload.document.validattor.DocumentValidator;
import com.demo.fileupload.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
public class DocumentService {

    @Autowired
    DocumentRepository documentRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    List<DocumentValidator> validators;

    @PreAuthorize("principal.userId == #userId")
    public Document save(Document document, Long userId) {
        return userRepository.findById(userId).map( user -> {
            document.setUser(user);
            return documentRepository.save(document);
        }).orElseThrow(() -> new ResourceNotFoundException("User Id " + userId + " not found"));
    }

    @PreAuthorize("hasRole('ROLE_ADMIN') or principal.userId == #userId")
    public Document get(Long documentId, Long userId) {
        return userRepository.findById(userId).map( user ->
           documentRepository.findByUserAndId(user, documentId)
                   .orElseThrow(() -> new ResourceNotFoundException("Document Id " + documentId + " not found"))
        ).orElseThrow(() -> new ResourceNotFoundException("User Id " + userId + " not found"));
    }

    @PreAuthorize("hasRole('ROLE_ADMIN') or principal.userId == #userId")
    public Page<Document> list(Long userId, Pageable pageable) {
        return userRepository.findById(userId).map( user ->
            documentRepository.findAllByUser(user, pageable)
        ).orElseThrow(() -> new ResourceNotFoundException("User Id " + userId + " not found"));
    }

    @PreAuthorize("hasRole('ROLE_ADMIN') or principal.userId == #userId")
    public void delete(Long documentId, Long userId) {
        documentRepository.findByIdAndUserId(documentId, userId).map(document -> {
            documentRepository.delete(document);
            return true;
        }).orElseThrow(() -> new ResourceNotFoundException("Document Id " + documentId + " not found"));
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_WORKER') or principal.userId == #userId")
    public Document updateStatus(Long documentId, Long userId, DocumentStatus status) {
        return documentRepository.findByIdAndUserId(documentId, userId).map(document -> {
            DocumentStatus persistentStatus = document.getStatus();
            document.setStatus(status);

            validators.forEach( validator -> validator.validate(document, new HashMap<String, Object>() {{
                put("persistentStatus", persistentStatus);
            }}).throwIfInvalid("status"));

            return documentRepository.save(document);
        }).orElseThrow(() -> new ResourceNotFoundException("Document Id " + documentId + " not found"));
    }
}
