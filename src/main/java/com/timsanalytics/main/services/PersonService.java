package com.timsanalytics.main.services;

import com.timsanalytics.auth.authCommon.beans.KeyValue;
import com.timsanalytics.main.beans.Person;
import com.timsanalytics.main.beans.ServerSidePaginationRequest;
import com.timsanalytics.main.dao.PersonDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PersonService {
    private final Logger logger = LoggerFactory.getLogger(getClass().getName());
    private final PersonDao personDao;

    @Autowired
    public PersonService(PersonDao personDao) {
        this.personDao = personDao;
    }

    public Person createPerson(Person person) {
        return this.personDao.createPerson(person);
    }

    public List<Person> getPersonList_All() {
        return this.personDao.getPersonList_All();
    }

    public List<Person> getPersonList_InfiniteScroll(ServerSidePaginationRequest serverSidePaginationRequest) {
        return this.personDao.getPersonList_InfiniteScroll(serverSidePaginationRequest);
    }

    public Person getPersonDetail(String personGuid) {
        return this.personDao.getPersonDetail(personGuid);
    }

    public Person updatePerson(Person person) {
        return this.personDao.updatePerson(person);
    }

    public KeyValue deletePerson(String personGuid) {
        return this.personDao.deletePerson(personGuid);
    }

    // Sub-List

    public List<Person> getPersonSubList_InfiniteScroll(ServerSidePaginationRequest serverSidePaginationRequest) {
        return this.personDao.getPersonSubList_InfiniteScroll(serverSidePaginationRequest);
    }

    // FRIENDS ADD/REMOVE

    public List<Person> getCurrentFriends(String personGuid) {
        return this.personDao.getCurrentFriends(personGuid);
    }

    public List<Person> getAvailableFriends(String personGuid) {
        return this.personDao.getAvailableFriends(personGuid);
    }

    public List<Person> addFriends(String personGuid, List<Person> friendList) {
        int[] recordsUpdated = this.personDao.addFriends(personGuid, friendList);
        return friendList;
    }

    public List<Person> removeFriends(String personGuid, List<Person> friendList) {
        int[] recordsUpdated = this.personDao.removeFriends(personGuid, friendList);
        return friendList;
    }
}
