package com.timsanalytics.testing;

import com.timsanalytics.main.beans.Person;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class JavaStreamTest {

    public static void main(String[] args) {
        JavaStreamTest javaStreamTest = new JavaStreamTest();
        List<Person> existingRecords = javaStreamTest.createExistingRecords();
//        javaStreamTest.PrintObject("existingRecords", existingRecords);


    }

    private List<Person> createExistingRecords() {
        List<Person> personList = new ArrayList<>();
        Person person;

        person = new Person();
        person.setGuid("1");
        personList.add(person);

        person = new Person();
        person.setGuid("2");
        personList.add(person);

        person = new Person();
        person.setGuid("3");
        personList.add(person);

        person = new Person();
        person.setGuid("4");
        personList.add(person);

        return personList;
    }

    public void PrintObject(String title, Object object) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            System.out.println(title + ":\n" + mapper.writerWithDefaultPrettyPrinter().writeValueAsString(object));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
