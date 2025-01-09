package com.egroc.repository;
import com.egroc.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);

    User findByUsername(String username);

    static User findByUsernameAndPassword(String username, String password) {

        return null;
    }

}
