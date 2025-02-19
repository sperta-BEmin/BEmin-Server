package run.bemin.api.comment.repository;

import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import run.bemin.api.comment.entity.Comment;

public interface CommentRepository extends JpaRepository<Comment, UUID>{
  @Query(value = """
    SELECT content
    FROM comment
    WHERE store_id = :storeId 
    ORDER BY created_at DESC LIMIT 5
    """, nativeQuery = true)
  List<String> getRecentComments(@Param("storeId") UUID storeId);


}
