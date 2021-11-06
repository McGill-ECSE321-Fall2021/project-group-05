package ca.mcgill.ecse321.onlinelibrary.dto;

import ca.mcgill.ecse321.onlinelibrary.model.Archive;

public class ArchiveDto extends LibraryItemDto{
	private ArchiveInfoDto archiveInfo;
	
	public ArchiveDto (int id, ArchiveInfoDto archiveInfo) {
		super(id);
		this.archiveInfo = archiveInfo;
	}
	
	public static ArchiveDto fromArchive(Archive archive) {
		if (archive == null) {
			throw new IllegalArgumentException("There is no such archive.");
		}
		return new ArchiveDto(archive.getId(), ArchiveInfoDto.fromArchiveInfo(archive.getArchiveInfo()));
	}
	
	public ArchiveInfoDto getArchiveInfo() {
		return this.archiveInfo;
	}
}
