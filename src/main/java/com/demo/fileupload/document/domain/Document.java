package com.demo.fileupload.document.domain;


import com.demo.fileupload.user.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Entity
@Table(name = "documents")
public class Document {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", length = 25)
    private Long id;

    @Column(name = "file_type", length = 100, nullable = false)
    @Enumerated(EnumType.STRING)
    private FileType fileType;

    @Column(name = "title", length = 500, nullable = false)
    private String title;

    @Column(name = "description", length = 1000)
    @Lob
    private String description;

    @Column(name = "status", length = 100, nullable = false)
    @Enumerated(EnumType.STRING)
    private DocumentStatus status = DocumentStatus.PENDING;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false, referencedColumnName = "id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private User user;

    @Transient
    private String storageUrl;

    public String getStorageUrl() {
        return storageUrl;
    }

    public void setStorageUrl(String storageUrl) {
        this.storageUrl = storageUrl;
    }

    public Long getId() {
        return id;
    }

    public FileType getFileType() {
        return fileType;
    }

    public void setFileType(FileType fileType) {
        this.fileType = fileType;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public DocumentStatus getStatus() {
        return status;
    }

    public void setStatus(DocumentStatus status) {
        this.status = status;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Document document = (Document) o;

        if (!id.equals(document.id)) return false;
        if (fileType != document.fileType) return false;
        if (!title.equals(document.title)) return false;
        if (description != null ? !description.equals(document.description) : document.description != null)
            return false;
        return status == document.status;
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + fileType.hashCode();
        result = 31 * result + title.hashCode();
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + status.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Document{" +
                "id=" + id +
                ", fileType=" + fileType +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", status=" + status +
                '}';
    }
}

enum FileType {
    PNG, JPG, GIF, TIFF, PDF, DOC, TXT, DOCX, HTML;
}

