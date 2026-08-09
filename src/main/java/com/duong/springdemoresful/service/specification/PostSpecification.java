package com.duong.springdemoresful.service.specification;

import com.duong.springdemoresful.dto.request.PostFilterRequest;
import com.duong.springdemoresful.model.Post;
import com.duong.springdemoresful.model.Tag;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import org.springframework.data.jpa.domain.Specification;

public class PostSpecification {
    public static Specification<Post> hasTitle(PostFilterRequest filterRequest){
        return (root,query,criteriaBuilder)->{
            if(filterRequest.getTitle()==null){
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.like(criteriaBuilder.lower(root.get("title")),"%" + filterRequest.getTitle().toLowerCase() + "%");
        };
    }

    public static Specification<Post> hasUser(PostFilterRequest filterRequest){
        return (root,query,criteriaBuilder)->{
            if(filterRequest.getUserId()==null){
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.equal(root.get("user").get("id"),filterRequest.getUserId());
        };
    }

    public static Specification<Post> hasTag(PostFilterRequest filterRequest){
        return (root,query,criteriaBuilder)->{
            if(filterRequest.getTags()==null || filterRequest.getTags().isEmpty()){
                return criteriaBuilder.conjunction();
            }
            Join<Post, Tag> post = root.join("tags", JoinType.INNER);
            return criteriaBuilder.in(post.get("name")).value(filterRequest.getTags());
        };
    }

    public static Specification<Post> createdAtBetween(PostFilterRequest filterRequest){
        return (root,query,criteriaBuilder)->{
            if(filterRequest.getToCreatedAt()== null || filterRequest.getFromCreatedAt()== null){
                return criteriaBuilder.conjunction();
            }

            return criteriaBuilder.between(root.get("createdAt"),filterRequest.getFromCreatedAt(),filterRequest.getToCreatedAt());
        };


    }
    public static Specification<Post> updatedAtBetween(PostFilterRequest filterRequest) {
        return (root, query, criteriaBuilder) -> {
            if (filterRequest.getFromUpdatedAt() == null || filterRequest.getToUpdatedAt() == null) {
                return criteriaBuilder.conjunction();
            }

            return criteriaBuilder.between(root.get("updatedAt"), filterRequest.getFromUpdatedAt(), filterRequest.getToUpdatedAt());
        };
    }
}
