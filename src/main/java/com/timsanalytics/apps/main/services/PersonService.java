package com.timsanalytics.apps.main.services;

import com.timsanalytics.apps.main.beans.Person;
import com.timsanalytics.apps.main.beans.PersonFriend;
import com.timsanalytics.apps.main.dao.PersonDao;
import com.timsanalytics.apps.main.dao.PersonFriendDao;
import com.timsanalytics.auth.authCommon.beans.KeyValue;
import com.timsanalytics.common.beans.ServerSidePaginationRequest;
import com.timsanalytics.common.beans.ServerSidePaginationResponse;
import com.timsanalytics.utils.GenerateUuidService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PersonService {
    private final PersonDao personDao;
    private final PersonFriendDao personFriendDao;
    private final GenerateUuidService generateUuidService;

    @Autowired
    public PersonService(PersonDao personDao,
                         PersonFriendDao personFriendDao,
                         GenerateUuidService generateUuidService) {
        this.personDao = personDao;
        this.personFriendDao = personFriendDao;
        this.generateUuidService = generateUuidService;
    }

    public Person createPerson(Person person) {
        return this.personDao.createPerson(person);
    }

    public List<Person> getPersonList() {
        return this.personDao.getPersonList();
    }

    public ServerSidePaginationResponse<Person> getPersonList_SSP(ServerSidePaginationRequest serverSidePaginationRequest) {
        ServerSidePaginationResponse<Person> serverSidePaginationResponse = new ServerSidePaginationResponse<>();
        serverSidePaginationResponse.setServerSidePaginationRequest(serverSidePaginationRequest);
        List<Person> personList = this.personDao.getPersonList_SSP(serverSidePaginationRequest);
        serverSidePaginationResponse.setData(personList);
        serverSidePaginationResponse.setLoadedRecords(personList.size());
        serverSidePaginationResponse.setTotalRecords(this.personDao.getPersonList_SSP_TotalRecords(serverSidePaginationRequest));
        return serverSidePaginationResponse;
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

    // PERSON-FRIEND LIST

    public List<Person> getPersonFriendList_SSP(ServerSidePaginationRequest serverSidePaginationRequest) {
        return this.personFriendDao.getPersonFriendList_SSP(serverSidePaginationRequest);
    }

    // FRIENDS ADD/REMOVE

    public List<Person> getCurrentFriends(String personGuid) {
        return this.personFriendDao.getCurrentFriends(personGuid);
    }

    public List<Person> getAvailableFriends(String personGuid) {
        return this.personFriendDao.getAvailableFriends(personGuid);
    }

    public List<Person> addFriends(String personGuid, List<Person> friendList) {
        // Because MySQL doesn't have a MERGE query like Oracle, we are forced to use MySQL's
        // ON DUPLICATE KEY UPDATE query. To make this work for us, we first have to get the key from the
        // PERSON_FRIEND table, if one exists. If the key already exists in the table, the query will update
        // it with a status of 'Active'. If it doesn't exist, the query will create it.
        List<PersonFriend> personFriendList = this.createPersonFriendListToAdd(personGuid, friendList);
        int[] recordsUpdated = this.personFriendDao.addFriends(personFriendList);
        return friendList;
    }

    private List<PersonFriend> createPersonFriendListToAdd(String personGuid, List<Person> friendList) {
        List<PersonFriend> temp = friendList.stream()
                .map(friend -> {
                    PersonFriend existingPersonFriend = this.personFriendDao.getPersonFriendByPersonAndFriendGuid(personGuid, friend.getGuid());
                    PersonFriend personFriend = new PersonFriend();
                    personFriend.setPersonFriendGuid(existingPersonFriend != null ? existingPersonFriend.getPersonFriendGuid() : this.generateUuidService.GenerateUuid());
                    personFriend.setPersonGuid(personGuid);
                    personFriend.setFriendGuid(friend.getGuid());
                    return personFriend;
                })
                .collect(Collectors.toList());
        System.out.println("FriendList: " + temp);
        return temp;
    }

    public List<Person> removeFriends(String personGuid, List<Person> friendList) {
        int[] recordsUpdated = this.personFriendDao.removeFriends(personGuid, friendList);
        return friendList;
    }

    public List<String> getStateDropDownOptions() {
        return this.personDao.getStateDropDownOptions();
    }
}
