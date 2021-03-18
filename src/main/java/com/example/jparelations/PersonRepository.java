package com.example.jparelations;

import java.util.Optional;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface PersonRepository extends CrudRepository<Person, Long> {

  @Query(value = "select p from Person p where p.name = ?1")
  Optional<Person> findByName(String name);
}
