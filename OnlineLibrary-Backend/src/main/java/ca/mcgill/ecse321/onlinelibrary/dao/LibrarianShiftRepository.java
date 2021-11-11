package ca.mcgill.ecse321.onlinelibrary.dao;

import java.sql.Date;
import java.util.ArrayList;

import org.springframework.data.repository.CrudRepository;

import ca.mcgill.ecse321.onlinelibrary.model.LibrarianShift;

public interface LibrarianShiftRepository extends CrudRepository<LibrarianShift, Integer> {

	public LibrarianShift findLibrarianShiftById(int id);
	
	public ArrayList<LibrarianShift> findLibrarianShiftByDate(Date date);
	
	public ArrayList<LibrarianShift> findLibrarianShiftByLibrarianId(int librarianId);

	public ArrayList<LibrarianShift> findLibrarianShiftByDateAndLibrarianId(Date date, int librarianId);
	
	public void deleteById(int id);
}
