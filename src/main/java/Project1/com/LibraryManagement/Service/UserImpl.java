package Project1.com.LibraryManagement.Service;

import Project1.com.LibraryManagement.Entity.Users;
import Project1.com.LibraryManagement.Repository.UsersRepos;
import org.springframework.beans.factory.annotation.Autowired;

public class UserImpl implements UserService{
    @Autowired
    public UsersRepos usersRepos;

    @Override
    public boolean existsUser(String email) {
        return usersRepos.existsByEmail(email) ;
    }

    @Override
    public Users saveUser(Users users) {
        return usersRepos.save(users);
    }
}
