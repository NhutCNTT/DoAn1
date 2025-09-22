package Project1.com.LibraryManagement.Service;

import Project1.com.LibraryManagement.Entity.Users;
import org.springframework.security.core.userdetails.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    public boolean existsUser(String email);
    public Users saveUser(Users users);
    public Users findById(Long Id);
    public Users findByEmai(String email);
    public Optional<Users> deleteById(Long Id);
    public List<Users> getAllUsers();
}
