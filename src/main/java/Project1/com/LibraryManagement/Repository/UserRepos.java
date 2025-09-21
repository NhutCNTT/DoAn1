package Project1.com.LibraryManagement.Repository;

import Project1.com.LibraryManagement.Entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepos extends JpaRepository<Users,Long> {
}
