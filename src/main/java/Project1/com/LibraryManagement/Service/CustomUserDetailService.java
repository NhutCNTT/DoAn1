package Project1.com.LibraryManagement.Service;

import Project1.com.LibraryManagement.Entity.Users;
import Project1.com.LibraryManagement.Repository.UsersRepos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class CustomUserDetailService implements UserDetailsService {
    @Autowired
    public UsersRepos usersRepos;
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException{
    Users users = usersRepos.findByEmail(email).orElseThrow(()-> new UsernameNotFoundException("User Email Is Not Found"));
        return new CustomUserDetail(users);
    }
}
