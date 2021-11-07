package ca.mcgill.ecse321.onlinelibrary.service;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;

import ca.mcgill.ecse321.onlinelibrary.dao.LibraryItemInfoRespository;
import ca.mcgill.ecse321.onlinelibrary.model.LibraryItemInfo;

public class BrowseService {
    @Autowired
    LibraryItemInfoRespository libraryItemInfoRespository;

    @Transactional
    public List<LibraryItemInfo> browse(){

        List<LibraryItemInfo> lii = new ArrayList<LibraryItemInfo>();
        Iterable<LibraryItemInfo> iterativeItems = libraryItemInfoRespository.findAll();
        for (LibraryItemInfo item : iterativeItems){
            lii.add(item);
        }
        
        return lii;
    }
}
