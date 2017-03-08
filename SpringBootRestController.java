package com.ids.controller;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.ids.bean.User;
import com.ids.service.UserService;

@Component
@RestController
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Path("/users11")
public class SpringBootRestController {

	@Autowired
	// @Qualifier("userServiceImpl")
	private UserService userService;

	// -------------------Retrieve All Users--------------------------------------------------------

	// @RequestMapping(value = "/user/", method = RequestMethod.GET)
	@RequestMapping(method = RequestMethod.GET,produces = MediaType.APPLICATION_JSON)
	public ResponseEntity<List<User>> getUsers() {
		List<User> users = userService.getAllUsers();
		if (users.isEmpty()) {
			return new ResponseEntity<List<User>>(HttpStatus.NO_CONTENT);// You many decide to return HttpStatus.NOT_FOUND
		}
		return new ResponseEntity<List<User>>(users, HttpStatus.OK);
	}

	// -------------------Retrieve Single User--------------------------------------------------------

	// @RequestMapping(value = "/user/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON)
	public ResponseEntity<User> getUserById(@PathVariable("id") int id) {
		System.out.println("Fetching User with id " + id);
		User user = userService.getUserById(id);
		if (user == null) {
			System.out.println("User with id " + id + " not found");
			return new ResponseEntity<User>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<User>(user, HttpStatus.OK);
	}

	// -------------------Create a User--------------------------------------------------------

	// @RequestMapping(value = "/user/", method = RequestMethod.POST)
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Void> addUser(@RequestBody User user, UriComponentsBuilder ucBuilder) {
		System.out.println("Creating User " + user.getUserName());

		/*
		 * if (userService.isUserExist(user)) { System.out.println("A User with name " + user.getUserName() + " already exist"); return new ResponseEntity<Void>(HttpStatus.CONFLICT); }
		 */

		userService.addUser(user);

		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(ucBuilder.path("/user/{id}").buildAndExpand(user.getUserId()).toUri());
		return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
	}

	// ------------------- Update a User --------------------------------------------------------

	// @RequestMapping(value = "/user/{id}", method = RequestMethod.PUT)
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public ResponseEntity<User> updateUser(@PathVariable("id") int id, @RequestBody User user) {
		System.out.println("Updating User " + id);

		User currentUser = userService.getUserById(id);

		if (currentUser == null) {
			System.out.println("User with id " + id + " not found");
			return new ResponseEntity<User>(HttpStatus.NOT_FOUND);
		}

		currentUser.setUserName(user.getUserName());
		currentUser.setFatherName(user.getFatherName());

		userService.updateUser(currentUser);
		return new ResponseEntity<User>(currentUser, HttpStatus.OK);
	}

	// ------------------- Delete a User --------------------------------------------------------

	// @RequestMapping(value = "/user/{id}", method = RequestMethod.DELETE)
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<User> deleteUser(@PathVariable("id") int id) {
		System.out.println("Fetching & Deleting User with id " + id);

		User user = userService.getUserById(id);
		if (user == null) {
			System.out.println("Unable to delete. User with id " + id + " not found");
			return new ResponseEntity<User>(HttpStatus.NOT_FOUND);
		}

		userService.deleteUser(id);
		return new ResponseEntity<User>(HttpStatus.NO_CONTENT);
	}

	// ------------------- Delete All Users --------------------------------------------------------

	@RequestMapping(value = "/user/", method = RequestMethod.DELETE)
	public ResponseEntity<User> deleteAllUsers() {
		System.out.println("Deleting All Users");

		// userService.deleteAllUsers();
		return new ResponseEntity<User>(HttpStatus.NO_CONTENT);
	}

}
