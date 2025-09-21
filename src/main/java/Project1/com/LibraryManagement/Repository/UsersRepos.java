package Project1.com.LibraryManagement.Repository;

import Project1.com.LibraryManagement.Entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsersRepos extends JpaRepository<Users,Long> {
    Boolean existsByEmail(String email);
}
