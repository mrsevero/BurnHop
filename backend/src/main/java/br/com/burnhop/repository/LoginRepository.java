package br.com.burnhop.repository;

import br.com.burnhop.model.Login;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "login", path = "login")
public interface LoginRepository extends CrudRepository<Login, Long> {

}