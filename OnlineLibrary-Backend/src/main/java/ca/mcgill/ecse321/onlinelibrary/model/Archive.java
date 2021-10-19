package ca.mcgill.ecse321.onlinelibrary.model;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
public class Archive extends LibraryItem{
    @ManyToOne(optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
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
