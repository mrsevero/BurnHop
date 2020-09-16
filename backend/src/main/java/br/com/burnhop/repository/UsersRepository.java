package br.com.burnhop.repository;

import br.com.burnhop.model.Users;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

public interface UsersRepository extends CrudRepository<Users, Integer> {

}