package com.demo.fileupload.storage;

import com.demo.fileupload.document.domain.Document;

public class AzureFileStorageProvider implements StorageProvider<Document> {

    @Override
    public String getUploadFileUrl(Document instance) {
        return null;
    }

    @Override
    public String getFileUrl(Document instance) {
        return null;
    }

    @Override
    public void remove(Document instance) {
        return;
    }
}
