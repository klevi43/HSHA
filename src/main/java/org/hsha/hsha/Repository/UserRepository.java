package org.hsha.hsha.Repository;

import org.hsha.hsha.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer>{
//

    void deleteUserById(Integer id);
}
