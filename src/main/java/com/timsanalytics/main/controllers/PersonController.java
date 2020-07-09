package com.timsanalytics.main.controllers;

import com.timsanalytics.main.beans.Person;
import com.timsanalytics.main.beans.ServerSidePaginationRequest;
import com.timsanalytics.main.beans.ServerSidePaginationResponse;
import com.timsanalytics.main.services.PersonService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/v1/person")
@Tag(name = "Person", description = "This is used to query Person data.")
public class PersonController {
    private final Logger logger = LoggerFactory.getLogger(getClass().getName());
    private final PersonService personService;

    @Autowired
    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    @ResponseBody
    @RequestMapping(value = "/all", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "getPersonList_ALl", tags = {"Person"}, description = "Get all Person records.", security = @SecurityRequirement(name = "bearerAuth"))
    public ResponseEntity<List<Person>> getPersonList_All() {
        this.logger.debug("PersonController -> getPersonList_All");
        try {
            return ResponseEntity.ok()
                    .body(this.personService.getPersonList_All());
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @ResponseBody
    @RequestMapping(value = "/infinite-scroll", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "getPersonList_InfiniteScroll", tags = {"Person"}, description = "Get Person records using infinite scroll.", security = @SecurityRequirement(name = "bearerAuth"))
    public ResponseEntity<ServerSidePaginationResponse> getPersonList_InfiniteScroll(@RequestBody ServerSidePaginationRequest serverSidePaginationRequest) {
        this.logger.debug("PersonController -> getPersonList_InfiniteScroll");

        long startTime = new Date().getTime();
        this.logger.debug("Page Index=" + serverSidePaginationRequest.getPageIndex());
        this.logger.debug("Page Size =" + serverSidePaginationRequest.getPageSize());
        try {
            ServerSidePaginationResponse container = new ServerSidePaginationResponse();
            List<Person> personList = this.personService.getPersonList_InfiniteScroll(serverSidePaginationRequest);
            container.setLength(personList.size());
            container.setData(personList);
            container.setServerSidePaginationRequest(serverSidePaginationRequest);
            container.setRequestTime(new Date().getTime() - startTime);
            return ResponseEntity.ok()
                    .body(container);
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @RequestMapping(value = "/{personGuid}", method = RequestMethod.GET)
    @Operation(summary = "getPersonDetail", tags = {"Person"}, security = @SecurityRequirement(name = "bearerAuth"))
    public ResponseEntity<Person> getPersonDetail(@Parameter(description = "Person GUID", required = true) @PathVariable String personGuid) {
        try {
            return ResponseEntity.ok()
                    .body(personService.getPersonDetail(personGuid));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @ResponseBody
    @RequestMapping(value = "/", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "updatePerson", tags = {"Person"}, description = "Update Person record.", security = @SecurityRequirement(name = "bearerAuth"))
    public ResponseEntity<Person> updatePerson(@RequestBody Person person) {
        try {
            return ResponseEntity.ok()
                    .body(personService.updatePerson(person));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


}
