package br.com.burnhop.repository;

import br.com.burnhop.model.Login;
import br.com.burnhop.model.Users;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "users", path = "users")
public interface UsersRepository extends CrudRepository<Users, Integer> {

    @Query(value = "SELECT u.* FROM users u join login l on u.login_id = l.id where l.email = :email", nativeQuery = true)
    Users findByEmail(@Param("email") String email);
}