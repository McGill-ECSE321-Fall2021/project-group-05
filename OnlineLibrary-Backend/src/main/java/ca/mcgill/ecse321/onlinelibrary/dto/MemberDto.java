package ca.mcgill.ecse321.onlinelibrary.dto;

public class MemberDto {
    private Integer id;
    private String address;
    private String fullName;
    private MemberDtoStatus status;
    public enum MemberDtoStatus {
        GREEN, YELLOW, RED, BLACKLISTED
    }
    // Account fees (e.g. registration fee, late fees) in cents
    private int totalFee;
    private boolean isActive;

    public MemberDto(Integer id, String address, String fullName, MemberDtoStatus status, int totalFee, boolean isActive) {
        this.id = id;
        this.address = address;
        this.fullName = fullName;
        this.status = status;
        this.totalFee = totalFee;
        this.isActive = isActive;
    }

    public Integer getId() {
        return id;
    }

    public String getAddress() {
        return address;
    }

    public String getFullName() {
        return fullName;
    }

    public MemberDtoStatus getStatus() {
        return status;
    }

    public int getTotalFee() {
        return totalFee;
    }

    public boolean isActive() {
        return isActive;
    }
}
