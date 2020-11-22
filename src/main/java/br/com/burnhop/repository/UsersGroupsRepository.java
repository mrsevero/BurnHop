package br.com.burnhop.repository;

import br.com.burnhop.model.UsersGroups;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsersGroupsRepository extends CrudRepository<UsersGroups, Integer> {

    @Query(value = "SELECT u.* FROM usersgroups u WHERE (u.group_id = :group_id AND u.user_id = :user_id);",
            nativeQuery = true)
    Optional<UsersGroups> findByGroupAndUserId(@Param("group_id") int group_id,
                                        @Param("user_id") int user_id);
}