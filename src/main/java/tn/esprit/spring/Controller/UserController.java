package tn.esprit.spring.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import tn.esprit.spring.Entity.Role;
import tn.esprit.spring.Entity.User;
import tn.esprit.spring.Service.UserService;
@CrossOrigin(origins = "http://localhost:4200")
@RestController
public class UserController {
	@Autowired
	private UserService userService;
	@Autowired

	//GET
	@GetMapping(value = "/Users") 
	public List<User> getUsers() {
		return userService.getAllTheUsers();
	}
	//GET by id
	@RequestMapping(value ="/Users/{id}", method = RequestMethod.GET)
	public User getUserById(@PathVariable int id) {
		return userService.getUser(id);
	}
	//POST -> Save the data
	@PostMapping(value="/Users/save")
	public void saveUser(@RequestBody User user) {
		user.setRole(Role.CUSTOMER);
		userService.saveUser(user);
	}
	//PUT
	@PutMapping(value="/Users/update")
	public void updateUser(@RequestBody User user) {
		userService.updateUser(user);
	}
	//DELETE
	@RequestMapping(value = "Users/delete/{id}", method = RequestMethod.DELETE)
	public void deleteUser(@PathVariable int id) {
		userService.deleteUser(id);
	}

}
