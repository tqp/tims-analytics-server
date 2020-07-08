package com.timsanalytics.main.services;

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
    private Logger logger = LoggerFactory.getLogger(getClass().getName());
    private PersonDao personDao;

    @Autowired
    public PersonService(PersonDao personDao) {
        this.personDao = personDao;
    }

    public List<Person> getPersonList_All() {
        return this.personDao.getPersonList_All();
    }

    public List<Person> getPersonList_InfiniteScroll(ServerSidePaginationRequest serverSidePaginationRequest) {
        return this.personDao.getPersonList_InfiniteScroll(serverSidePaginationRequest);
    }

}
