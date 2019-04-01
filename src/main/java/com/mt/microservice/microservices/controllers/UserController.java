package com.mt.microservice.microservices.controllers;

import com.mt.microservice.microservices.dao.UserService;
import com.mt.microservice.microservices.entities.User;
import com.mt.microservice.microservices.exceptions.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.Locale;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private MessageSource messageSource;

    @GetMapping("/all")
    public List<User> getUsers() {
        return userService.findAll();
    }

    @GetMapping("/{id}")
    public Resource<User> getUserById(@PathVariable Integer id) {
        User user = userService.findOneById(id);
        if (user == null) {
            throw new UserNotFoundException("id-" + id);
        }

        // HATEOAS : Here we add a link to all users for the user by {id}
        Resource<User> userResource = new Resource<>(user);
        ControllerLinkBuilder linkBuilder =
                ControllerLinkBuilder.linkTo(ControllerLinkBuilder.methodOn(this.getClass()).getUsers());
        userResource.add(linkBuilder.withRel("all-users"));

        return userResource;
    }

    @GetMapping("/user")
    public User getUser(User user) {
        return userService.findOneByUser(user);
    }

    @PostMapping("/add")
    public ResponseEntity<User> createUser(@Valid @RequestBody User user) {
        User userTemp = userService.saveUser(user);

        // This will add location of the created user into headers section of the response :
        // Location → http://localhost:8080/users/add/4
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(userTemp.getId())
                .toUri();

        return ResponseEntity.created(location).build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<User> deleteUser(@PathVariable Integer id) {
        User user = userService.delete(id);
        return new ResponseEntity<>(user, HttpStatus.ACCEPTED);
    }

    @GetMapping("/i18n")
    public String goodMorningInternationalization(@RequestHeader(name = "Accept-Language", required = false) Locale locale) {
        return messageSource.getMessage("good.morning.message", null, locale);
    }

    @GetMapping("/i18n/another-approach")
    public String goodMorningInternationalizationAnotherApproach() {
        return messageSource.getMessage("good.morning.message", null, LocaleContextHolder.getLocale());
    }
}