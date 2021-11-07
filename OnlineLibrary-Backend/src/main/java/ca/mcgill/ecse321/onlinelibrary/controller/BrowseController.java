package ca.mcgill.ecse321.onlinelibrary.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import ca.mcgill.ecse321.onlinelibrary.dto.LibraryItemInfoDto;
import ca.mcgill.ecse321.onlinelibrary.model.LibraryItemInfo;
import ca.mcgill.ecse321.onlinelibrary.model.Movie;
import ca.mcgill.ecse321.onlinelibrary.service.BrowseService;

@CrossOrigin(origins = "*")
@RestController
public class BrowseController {
    @Autowired
    BrowseService browseService;

    @GetMapping(value = { "/browse", "/browse/" })
    public List<LibraryItemInfoDto> getAllLibraryItemInfos(){
        return browseService.browse().stream().map(p -> p.convertToDto()).collect(Collectors.toList());
    }

}
