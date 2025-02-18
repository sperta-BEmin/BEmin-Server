package run.bemin.api.comment.repository;

import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import run.bemin.api.comment.entity.Comment;

public interface CommentRepository extends JpaRepository<Comment, UUID>{
}
