package com.example.jparelations;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class FamilyRepositoryTest {

  @Autowired
  FamilyRepository familyRepository;
  @Autowired
  PersonRepository personRepository;

  @BeforeEach
  void clean() {
    familyRepository.deleteAll();
    personRepository.deleteAll();
  }

  @Test
  void persist() {
    //given
    Person person = new Person();
    person.setName("Pawel");
    Family family = new Family();
    family.setName("Mieloch");
    family.setMembers(List.of(person));
    //when
    Family saved = familyRepository.save(family);
    //then
    Assertions.assertNotNull(saved.getId());
    Assertions.assertNotNull(saved.getMembers().get(0).getId());
  }

  @Test
  void find() {
    //given
    Person person = new Person();
    person.setName("Pawel");
    Family family = new Family();
    family.setName("Mieloch");
    family.setMembers(List.of(person));
    Family saved = familyRepository.save(family);
    //when
    Optional<Family> byId = familyRepository.findById(saved.getId());
    //then
    org.assertj.core.api.Assertions.assertThat(byId).isPresent();
    org.assertj.core.api.Assertions.assertThat(byId.get().getMembers()).contains(person);
  }

  @Test
  void deleteAll() {
    //given
    Person person = new Person();
    person.setName("Pawel");
    Family family = new Family();
    family.setName("Mieloch");
    family.setMembers(List.of(person));
    Family saved = familyRepository.save(family);
    //when
    saved.setMembers(Collections.emptyList());
    familyRepository.save(family);
    //then
    Assertions.assertNotNull(saved.getId());
    Iterable<Person> found = personRepository.findAll();
    org.assertj.core.api.Assertions.assertThat(found).isEmpty();
  }
}
