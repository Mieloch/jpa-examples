package com.example.jparelations;

import java.time.Instant;
import java.util.Collections;
import java.util.Date;
import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class PersonRepositoryTest {

  @Autowired
  PersonRepository repository;

  @BeforeEach
  void clean(){
    repository.deleteAll();
  }

  @Test
  void persist() {
    //given
    Person person = new Person();
    person.setName("Pawel");
    //when
    Person saved = repository.save(person);
    //then
    Assertions.assertNotNull(saved.getId());
  }

  @Test
  void findOne() {
    //given
    Person person = new Person();
    person.setName("Pawel");
    Person saved = repository.save(person);
    //when
    Iterable<Person> all = repository.findAll();
    //then
    Assertions.assertEquals(Collections.singletonList(saved), all);
  }

	@Test
	void updateTimestamp() {
		//given
		Person person = new Person();
		person.setName("Pawel");
		Person savedFirstTime = repository.save(person);
		Instant firstTimestamp = savedFirstTime.getUpdateTimestamp();
		//when
		savedFirstTime.setName("Not-Pawel");
		Person savedSecondTime = repository.save(person);
    Instant secondTimestamp = savedSecondTime.getUpdateTimestamp();
		//then
		org.assertj.core.api.Assertions.assertThat(firstTimestamp).isBefore(secondTimestamp);
	}

  @Test
  void findByName() {
    //given
    Person person = new Person();
    person.setName("Pawel");
    Person saved = repository.save(person);
    //when
    Optional<Person> found = repository.findByName("Pawel");
    //then
    org.assertj.core.api.Assertions.assertThat(found).contains(saved);
  }

  @Test
  void optimisticLocking() {
    //given
    Person person = new Person();
    person.setName("Pawel");
    Person savedFirstTime = repository.save(person);
    Integer firstVersion = savedFirstTime.getVersion();
    //when
    savedFirstTime.setName("Not-Pawel");
    Person savedSecondTime = repository.save(person);
    Integer secondVersion = savedSecondTime.getVersion();
    //then
    org.assertj.core.api.Assertions.assertThat(firstVersion).isNotEqualTo(secondVersion);
  }
}
