package ca.mcgill.ecse321.onlinelibrary.model;

import ca.mcgill.ecse321.onlinelibrary.dto.ArchiveDto;
import ca.mcgill.ecse321.onlinelibrary.dto.LibraryItemDto;
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

    @Override
    public LibraryItemDto convertToDto() {
        return ArchiveDto.fromArchive(this);
    }
    @Override
    public LibraryItemInfo getItemInfo() {
        return this.getArchiveInfo();
    }
}
