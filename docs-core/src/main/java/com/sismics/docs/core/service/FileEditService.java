package com.sismics.docs.core.service;

import com.sismics.docs.core.dao.FileDao;
import com.sismics.docs.core.model.jpa.File;

/**
 * File manager: handles DB-level file operations.
 */
public class FileEditService {
    private final FileDao fileDao;

    public FileEditService(FileDao fileDao) {
        this.fileDao = fileDao;
    }

    public File findFileById(String fileId) {
        return fileDao.getActiveById(fileId);
    }

    public String saveFile(File file) {
        if (file.getId() == null || file.getId().isEmpty()) {
            return fileDao.create(file, file.getUserId());
        } else {
            fileDao.update(file);
            return file.getId();
        }
    }
}
