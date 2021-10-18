package ca.mcgill.ecse321.onlinelibrary.model;

import javax.persistence.*;

@Entity
public abstract class LibraryItem {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    /**
     * Returns the unique ID number of the library item.
     *
     * This id is unique for all items available in the library.
     * @return the id of the LibraryItem
     */
    public Integer getId() {
        return this.id;
    }

}
