package br.com.burnhop.repository;

import br.com.burnhop.model.Groups;
import br.com.burnhop.model.Dto.GroupDto;
import br.com.burnhop.model.Posts;
import br.com.burnhop.model.Users;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface GroupsRepository extends CrudRepository<Groups, Integer> {
    @Query(value = "SELECT u.* FROM groups u where u.name = :name", nativeQuery = true)
    Optional<Groups> findByName(@Param("name") String name);
}
