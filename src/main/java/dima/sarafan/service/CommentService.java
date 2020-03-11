package dima.sarafan.service;

import dima.sarafan.domain.Comment;
import dima.sarafan.domain.User;
import dima.sarafan.domain.Views;
import dima.sarafan.dto.EventType;
import dima.sarafan.dto.ObjectType;
import dima.sarafan.repo.CommentRepo;
import dima.sarafan.util.WsSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.function.BiConsumer;

@Service
public class CommentService {

    private final CommentRepo commentRepo;
    private final BiConsumer<EventType, Comment> wsSender;

    @Autowired
    public CommentService(CommentRepo commentRepo, WsSender wsSender) {
        this.commentRepo = commentRepo;
        this.wsSender = wsSender.getSender(ObjectType.COMMENT, Views.FullComment.class);
    }

    public Comment create(Comment comment, User user){
        comment.setAuthor(user);
        Comment commentFromDB = commentRepo.save(comment);

        wsSender.accept(EventType.CREATE, commentFromDB);

        return commentFromDB;
    }

}
