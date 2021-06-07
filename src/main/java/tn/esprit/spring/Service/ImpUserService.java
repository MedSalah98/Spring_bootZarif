package tn.esprit.spring.Service;

import java.util.List;

import tn.esprit.spring.Entity.User;

public interface ImpUserService {
	public User getUser(int id);

	public List<User> getAllTheUsers();

	public void deleteUser(int id);

	public void updateUser(User user);

	public void saveUser(User user) ;

}
