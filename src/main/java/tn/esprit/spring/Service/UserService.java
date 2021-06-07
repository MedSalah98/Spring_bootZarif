package tn.esprit.spring.Service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tn.esprit.spring.Entity.User;
import tn.esprit.spring.Repository.UserRepository;

@Service
public  class UserService implements ImpUserService  {
	@Autowired 
    private UserRepository userRepository;   

    //Return All the users
	@Override
	public List<User> getAllTheUsers() {
		
		//List<User> userList = new ArrayList<>();
		List<User> userList = userRepository.findBySpecificRoles();
		return userList;
	}
 
	//Return Single User
	@Override
	public User getUser(int id) {
		User user = userRepository.findById(id).get();
			return user;
	}

	//Save the user
	@Override
	public void saveUser(User user){
				userRepository.save(user);
	}

	//update the user
	@Override
	public void updateUser(User user) {
		userRepository.save(user);
	}

	//Remove the User
	public void deleteUser(int id) {
		userRepository.deleteById(id);
	}
	//Remove all User

}
