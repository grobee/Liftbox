package com.cat.prf.entity;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "folders_files", schema = "liftbox")
public class FolderFile implements Serializable {
    private long folderId;
    private long filesId;

    public FolderFile() {
    }

    public FolderFile(long folderId, long filesId) {
        this.folderId = folderId;
        this.filesId = filesId;
    }

    @Id
    @Column(name = "Folder_id")
    public long getFolderId() {
        return folderId;
    }

    public void setFolderId(long folderId) {
        this.folderId = folderId;
    }

    @Id
    @Column(name = "files_id")
    public long getFilesId() {
        return filesId;
    }

    public void setFilesId(long filesId) {
        this.filesId = filesId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FolderFile that = (FolderFile) o;

        if (folderId != that.folderId) return false;
        if (filesId != that.filesId) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (folderId ^ (folderId >>> 32));
        result = 31 * result + (int) (filesId ^ (filesId >>> 32));
        return result;
    }
}
