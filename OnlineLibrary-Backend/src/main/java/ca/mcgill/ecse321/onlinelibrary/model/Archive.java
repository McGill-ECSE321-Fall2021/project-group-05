package ca.mcgill.ecse321.onlinelibrary.model;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
public class Archive extends LibraryItem{
    @ManyToOne
    private ArchiveInfo archiveInfo;

    public Archive(ArchiveInfo archiveInfo) {
        this.archiveInfo = archiveInfo;
    }

    protected Archive() {

    }

    public ArchiveInfo getArchiveInfo() {
        return archiveInfo;
    }

}
