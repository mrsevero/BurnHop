package br.com.burnhop.repository;

import br.com.burnhop.model.Login;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;


@RepositoryRestResource(collectionResourceRel = "login", path = "login")
public interface LoginRepository extends CrudRepository<Login, Long> {

    @Query(value = "SELECT * FROM Login where email = :email", nativeQuery = true)
    Login findByEmail(@Param("email") String email);
}