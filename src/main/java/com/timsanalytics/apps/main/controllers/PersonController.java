package com.timsanalytics.apps.main.controllers;

import com.timsanalytics.apps.main.beans.Person;
import com.timsanalytics.apps.main.services.PersonService;
import com.timsanalytics.auth.authCommon.beans.KeyValue;
import com.timsanalytics.common.beans.ServerSidePaginationRequest;
import com.timsanalytics.common.beans.ServerSidePaginationResponse;
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
    @RequestMapping(value = "/", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Create Person", tags = {"Person"}, description = "Create Person", security = @SecurityRequirement(name = "bearerAuth"))
    public ResponseEntity<Person> createPerson(@RequestBody Person person) {
        this.logger.debug("PersonController -> createPerson");
        try {
            return ResponseEntity.ok()
                    .body(personService.createPerson(person));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @ResponseBody
    @RequestMapping(value = "/all", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Get Person List (All)", tags = {"Person"}, description = "Get all Person records.", security = @SecurityRequirement(name = "bearerAuth"))
    public ResponseEntity<List<Person>> getPersonList() {
        this.logger.trace("PersonController -> getPersonList");
        try {
            return ResponseEntity.ok()
                    .body(this.personService.getPersonList());
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @ResponseBody
    @RequestMapping(value = "/ssp", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Get Person List (SSP)", tags = {"Person"}, description = "Get Person List (SSP)", security = @SecurityRequirement(name = "bearerAuth"))
    public ResponseEntity<ServerSidePaginationResponse<Person>> getPersonList_SSP(@RequestBody ServerSidePaginationRequest serverSidePaginationRequest) {
        long startTime = new Date().getTime();
        try {
            ServerSidePaginationResponse<Person> container = this.personService.getPersonList_SSP(serverSidePaginationRequest);
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
    @Operation(summary = "Get Person Detail", tags = {"Person"}, security = @SecurityRequirement(name = "bearerAuth"))
    public ResponseEntity<Person> getPersonDetail(@Parameter(description = "Person GUID", required = true) @PathVariable String personGuid) {
        try {
            Person person = personService.getPersonDetail(personGuid);
            return ResponseEntity.ok()
                    .body(person);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @ResponseBody
    @RequestMapping(value = "/", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Update Person", tags = {"Person"}, description = "Update Person", security = @SecurityRequirement(name = "bearerAuth"))
    public ResponseEntity<Person> updatePerson(@RequestBody Person person) {
        try {
            return ResponseEntity.ok()
                    .body(personService.updatePerson(person));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @ResponseBody
    @RequestMapping(value = "/{personGuid}", method = RequestMethod.DELETE, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Delete Person", tags = {"Person"}, description = "Delete Person", security = @SecurityRequirement(name = "bearerAuth"))
    public ResponseEntity<KeyValue> deletePerson(@Parameter(description = "Person GUID", required = true) @PathVariable String personGuid) {
        try {
            return ResponseEntity.ok()
                    .body(personService.deletePerson(personGuid));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // PERSON-FRIEND ASSOCIATIONS

    @ResponseBody
    @RequestMapping(value = "/person-friend-associations-ssp", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Get Friend List (SSP)", tags = {"Person"}, description = "Get Friend List (SSP)", security = @SecurityRequirement(name = "bearerAuth"))
    public ResponseEntity<ServerSidePaginationResponse<Person>> getPersonFriendList_SSP(@RequestBody ServerSidePaginationRequest serverSidePaginationRequest) {
        long startTime = new Date().getTime();
        try {
            ServerSidePaginationResponse<Person> container = new ServerSidePaginationResponse<>();
            List<Person> personList = this.personService.getPersonFriendList_SSP(serverSidePaginationRequest);
            container.setLoadedRecords(personList.size());
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

    // FRIENDS ADD/REMOVE

    @ResponseBody
    @RequestMapping(value = "/friends/current/{personGuid}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Person>> getCurrentFriends(@PathVariable String personGuid) {
        this.logger.trace("getCurrentFriends: personGuid=" + personGuid);
        try {
            List<Person> personList = this.personService.getCurrentFriends(personGuid);
            return ResponseEntity.ok()
                    .body(personList);
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @ResponseBody
    @RequestMapping(value = "/friends/available/{personGuid}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Person>> getAvailableFriends(@PathVariable String personGuid) {
        this.logger.trace("getAvailableFriends: personGuid=" + personGuid);
        try {
            List<Person> personList = this.personService.getAvailableFriends(personGuid);
            return ResponseEntity.ok()
                    .body(personList);
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @ResponseBody
    @RequestMapping(value = "/friends/add/{personGuid}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Person>> addFriends(@PathVariable String personGuid,
                                                   @RequestBody List<Person> friendList) {
        try {
            this.personService.addFriends(personGuid, friendList);
            return ResponseEntity.ok()
                    .body(friendList);
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @ResponseBody
    @RequestMapping(value = "/friends/remove/{personGuid}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Person>> removeFriends(@PathVariable String personGuid,
                                                      @RequestBody List<Person> friendList) {
        try {
            this.personService.removeFriends(personGuid, friendList);
            return ResponseEntity.ok()
                    .body(friendList);
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @ResponseBody
    @RequestMapping(value = "/state-list", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Get State List", tags = {"Person"}, description = "Get state list from Person records.", security = @SecurityRequirement(name = "bearerAuth"))
    public ResponseEntity<List<String>> getStateDropDownOptions() {
        this.logger.trace("PersonController -> getStateDropDownOptions");
        try {
            return ResponseEntity.ok()
                    .body(this.personService.getStateDropDownOptions());
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

}
