package mission_2.crud.repository;

import mission_2.crud.jpa.entity.PostEntity;
import org.springframework.data.repository.CrudRepository;

public interface PostRepository extends CrudRepository<PostEntity,Long> {
}
