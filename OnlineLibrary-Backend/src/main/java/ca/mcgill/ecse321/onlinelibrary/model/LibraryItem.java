package ca.mcgill.ecse321.onlinelibrary.model;

import ca.mcgill.ecse321.onlinelibrary.dto.LibraryItemDto;

import javax.persistence.*;

// WARNING: Changing the following `InheritanceType` to anything other than `JOINED` or `TABLE_PER_CLASS` will bring
// down the wrath of all the dysfunctional Hibernate pantheon. All your tests will fail. Luckily for you, if you ever
// want to try your luck, the solution is simple. Just put that line back to its original state. You have been warned.
//
// More details are available in the commit message for a07b3989.
@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
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
    
    public void setId(int id) {
    	this.id = id;
    }

    public abstract LibraryItemDto convertToDto();

    public abstract LibraryItemInfo getItemInfo();
}
