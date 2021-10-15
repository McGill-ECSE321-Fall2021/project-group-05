package ca.mcgill.ecse321.onlinelibrary.model;

import javax.persistence.*;

@Entity
public abstract class LibraryItem {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    public Integer getId() {
        return this.id;
    }

}
