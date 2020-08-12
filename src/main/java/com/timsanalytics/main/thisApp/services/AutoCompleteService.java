package com.timsanalytics.main.thisApp.services;

import com.timsanalytics.main.thisApp.beans.Person;
import com.timsanalytics.main.thisApp.dao.AutoCompleteDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AutoCompleteService {
    private Logger logger = LoggerFactory.getLogger(getClass().getName());
    private AutoCompleteDao autoCompleteDao;

    @Autowired
    public AutoCompleteService(AutoCompleteDao autoCompleteDao) {
        this.autoCompleteDao = autoCompleteDao;
    }

    public List<Person> getAutoCompleteLastName(String filter) {
        this.logger.debug("SampleDataService -> getAutoCompleteLastName");
        return this.autoCompleteDao.getAutoCompleteLastName(filter);
    }

    public List<Person> getAutoCompleteAddress(String filter) {
        this.logger.debug("SampleDataService -> getAutoCompleteAddress");
        return this.autoCompleteDao.getAutoCompleteAddress(filter);
    }
}
