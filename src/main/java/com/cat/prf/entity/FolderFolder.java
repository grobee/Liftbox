package com.cat.prf.entity;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "folders_folders", schema = "liftbox")
public class FolderFolder implements Serializable {
    private long folderId;
    private long foldersId;

    @Id
    @Column(name = "Folder_id")
    public long getFolderId() {
        return folderId;
    }

    public void setFolderId(long folderId) {
        this.folderId = folderId;
    }

    @Id
    @Column(name = "folders_id")
    public long getFoldersId() {
        return foldersId;
    }

    public void setFoldersId(long foldersId) {
        this.foldersId = foldersId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FolderFolder that = (FolderFolder) o;

        if (folderId != that.folderId) return false;
        return foldersId == that.foldersId;

    }

    @Override
    public int hashCode() {
        int result = (int) (folderId ^ (folderId >>> 32));
        result = 31 * result + (int) (foldersId ^ (foldersId >>> 32));
        return result;
    }
}
