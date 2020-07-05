package com.demo.fileupload.storage;

import com.demo.fileupload.document.domain.Document;

public interface StorageProvider<T> {

    String getUploadFileUrl(T instance);

    String getFileUrl(T instance);

    void remove(T instance);
}
