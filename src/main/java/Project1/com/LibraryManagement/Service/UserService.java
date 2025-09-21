package Project1.com.LibraryManagement.Service;

import Project1.com.LibraryManagement.Entity.Users;

public interface UserService {
    public boolean existsUser(String email);
    public Users saveUser(Users users);
}
