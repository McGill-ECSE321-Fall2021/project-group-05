package ca.mcgill.ecse321.onlinelibrary.model;

import javax.persistence.Entity;

@Entity
public abstract class ReservableItem extends LibraryItem {

    private ItemStatus status;

    public ItemStatus getStatus() {
        return this.status;
    }

    public void setStatus(ItemStatus status) {
        this.status = status;
    }

    public enum ItemStatus {Available, CheckedOut, Reserved}

}
