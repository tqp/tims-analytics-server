package com.timsanalytics.main.services;

import com.timsanalytics.auth.authCommon.beans.KeyValue;
import com.timsanalytics.main.beans.Person;
import com.timsanalytics.main.beans.PersonFriend;
import com.timsanalytics.main.beans.ServerSidePaginationRequest;
import com.timsanalytics.main.beans.ServerSidePaginationResponse;
import com.timsanalytics.main.dao.PersonDao;
import com.timsanalytics.main.dao.PersonFriendDao;
import com.timsanalytics.utils.GenerateUuidService;
import com.timsanalytics.utils.PrintObjectService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PersonService {
    private final Logger logger = LoggerFactory.getLogger(getClass().getName());
    private final PersonDao personDao;
    private final PersonFriendDao personFriendDao;
    private final PrintObjectService printObjectService;
    private final GenerateUuidService generateUuidService;

    @Autowired
    public PersonService(PersonDao personDao,
                         PersonFriendDao personFriendDao,
                         PrintObjectService printObjectService,
                         GenerateUuidService generateUuidService) {
        this.personDao = personDao;
        this.personFriendDao = personFriendDao;
        this.printObjectService = printObjectService;
        this.generateUuidService = generateUuidService;
    }

    public Person createPerson(Person person) {
        return this.personDao.createPerson(person);
    }

    public List<Person> getPersonList_All() {
        return this.personDao.getPersonList_All();
    }

    public ServerSidePaginationResponse getPersonList_InfiniteScroll(ServerSidePaginationRequest serverSidePaginationRequest) {
        if (serverSidePaginationRequest.getPageIndex() == 0) {
            serverSidePaginationRequest.setPageIndex(1);
        }
        if (serverSidePaginationRequest.getPageSize() == 0) {
            serverSidePaginationRequest.setPageSize(50);
        }
        this.logger.trace("Page Index=" + serverSidePaginationRequest.getPageIndex());
        this.logger.trace("Page Size =" + serverSidePaginationRequest.getPageSize());

        ServerSidePaginationResponse serverSidePaginationResponse = new ServerSidePaginationResponse();
        serverSidePaginationResponse.setServerSidePaginationRequest(serverSidePaginationRequest);
        List<Person> personList = this.personDao.getPersonList_InfiniteScroll(serverSidePaginationRequest);
        serverSidePaginationResponse.setData(personList);
        serverSidePaginationResponse.setLoadedRecords(personList.size());
        serverSidePaginationResponse.setTotalRecords(this.personDao.getPersonList_InfiniteScroll_TotalRecords(serverSidePaginationRequest));
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

    public List<Person> getPersonFriendList_InfiniteScroll(ServerSidePaginationRequest serverSidePaginationRequest) {
        return this.personFriendDao.getPersonFriendList_InfiniteScroll(serverSidePaginationRequest);
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
}
