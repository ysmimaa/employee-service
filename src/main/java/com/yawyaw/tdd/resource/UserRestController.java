package com.yawyaw.tdd.resource;


import com.yawyaw.tdd.entities.User;
import com.yawyaw.tdd.exception.NotFoundException;
import com.yawyaw.tdd.exception.ValidationException;
import com.yawyaw.tdd.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.stream.Stream;

@RestController
@RequestMapping("/api/user")
public class UserRestController {

    @Autowired
    private UserService userService;

    @GetMapping
    public Stream<User> fetchUsers() {
        return userService.findUsers();
    }

    @PutMapping
    public User createUser(@Valid User user) {
        return userService.create(user).orElseThrow(() -> new ValidationException(""));
    }

    @GetMapping("/findById/{id}")
    public User getUserById(@PathVariable("id") Long id) {
        return userService.findById(id).orElseThrow(() -> new NotFoundException(""));
    }


}
