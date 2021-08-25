package com.rename.me.interfaces;

import java.util.List;
import java.util.UUID;

import com.rename.me.model.Person;

public interface ServiceI {
	
	void save(Person person);
	void delete(Person person);
	List<Person> findAll();
	boolean existsByEmail(String email);
	Person getByEmail(String email);
	boolean existsById(UUID uuid);
	Person getById(UUID uuid);
}
