package com.duong.springdemoresful.service.specification;

import com.duong.springdemoresful.dto.request.CommentFilterRequest;
import com.duong.springdemoresful.model.Comment;
import org.springframework.data.jpa.domain.Specification;

public class CommentSpecification {

    public static Specification<Comment> hasComment(CommentFilterRequest filterRequest){
        return (root,query,criteriaBuilder)->{
            if(filterRequest.getComment()== null){
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.like(root.get("content"),"%" + filterRequest.getComment() + "%");
        };
    }
    public static Specification<Comment> hasUser(CommentFilterRequest filterRequest){
        return (root,query,criteriaBuilder)->{
            if(filterRequest.getUserId()== null){
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.equal(root.get("user").get("id"),filterRequest.getUserId());
        };
    }
    public static Specification<Comment> hasPost(CommentFilterRequest filterRequest){
        return (root,query,criteriaBuilder)->{
            if(filterRequest.getPostId()== null){
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.equal(root.get("post").get("id"),filterRequest.getPostId());
        };
    }
}
