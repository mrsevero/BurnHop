package br.com.burnhop.repository;

import br.com.burnhop.model.Posts;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PostsRepository extends CrudRepository<Posts, Integer> {
    /*Realizando consulta pelo id do usuário*/
    @Query(value = "SELECT p.* from posts p join users u on p.user_id = u.id WHERE p.user_id = :id", nativeQuery = true)
    Posts findById(@Param("id") String userId);
}