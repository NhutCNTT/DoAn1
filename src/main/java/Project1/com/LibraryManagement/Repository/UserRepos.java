package Project1.com.LibraryManagement.Repository;

import Project1.com.LibraryManagement.Entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepos extends JpaRepository<Users,Long> {
    Boolean existsByEmail(String email);
    Optional<Users> findByEmail(String email);
    Optional<Users> findById(Long Id);
    void deleteById(Long Id);

}
