package com.societe.service.service.impl;

import com.societe.service.dao.UserRepository;
import com.societe.service.entities.User;
import com.societe.service.service.UserService;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.Assert.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;


@RunWith(MockitoJUnitRunner.class)
public class UserServiceImplTest {

    private static final String USERNAME = "username1";

    @InjectMocks
    private UserService userService = new UserServiceImpl();

    @Mock
    private UserRepository userRepository;

    private User user;
    private List<User> users;

    @Before
    public void init() {
        users = new ArrayList<>();
        user = new User();
        user.setCreationDate(LocalDate.now());
        user.setVersion("version");
        user.setPassword("pwd");
        user.setUsername("username");
        users.add(user);
    }

    @Ignore
    @Test
    public void should_create_user_test() throws Exception {
        when(userRepository.save(user)).thenReturn(user);
        User insert = userService.create(user);
        assertNotNull(insert);

    }

    @Test
    public void should_update_an_existing_user_test() throws Exception {
        User userToBeUpdated = new User();
        userToBeUpdated.setVersion("new vesrion");

        when(userRepository.saveAndFlush(user)).thenReturn(userToBeUpdated);

        User updatedUser = userService.update(user);
        assertNotNull(updatedUser);

    }

    @Test
    public void should_delete_an_existing_user_test() throws Exception {
        User user = new User();
        user.setId(1L);

        doNothing().when(userRepository).delete(user);

        User deletedUser = userService.delete(user);
        assertNotNull(deletedUser);

    }

    @Test
    public void should_return_an_existing_user_test() throws Exception {
        User user = new User(1L, "version-tdd", LocalDate.now(), "", "", "", "", null, null);
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        User userToFind = userService.findById(1L);
        assertNotNull(userToFind);
        assertEquals("version-tdd", userToFind.getVersion());
        assertEquals(LocalDate.now().toString(), userToFind.getCreationDate().toString());
    }

    @Test
    public void should_return_user_by_username_test() {
        User user = new User();
        user.setUsername(USERNAME);
        user.setFirstname("Youssef");
        user.setLastname("SMIMAA");

        Optional<User> optionalUser = Optional.of(user);

        when(userRepository.findUserByUsername(USERNAME)).thenReturn(optionalUser);

        User userToFind = userService.findUserByUsername(USERNAME);

        assertNotNull(userToFind);
        assertEquals(userToFind.getUsername(), USERNAME);
        assertEquals(userToFind.getLastname(), "SMIMAA");
        assertEquals(userToFind.getFirstname(), "Youssef");

    }

    @Test
    public void should_fetch_users_list_test() {
        Stream<User> users = Stream.empty();
        when(userRepository.findAll()).thenReturn(users.collect(Collectors.toList()));

        List<User> userStream = userService.findUsers();
        assertNotNull(userStream);
        assertEquals(0, userStream.size());
    }


}