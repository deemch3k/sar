package dima.sarafan.service;

import dima.sarafan.domain.Comment;
import dima.sarafan.domain.User;
import dima.sarafan.repo.CommentRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommentService {

    private final CommentRepo commentRepo;

    @Autowired
    public CommentService(CommentRepo commentRepo) {
        this.commentRepo = commentRepo;
    }

    public Comment create(Comment comment, User user){
        comment.setAuthor(user);
        commentRepo.save(comment);

        return comment;
    }

}
