package com.duong.springdemoresful.service.specification;

import com.duong.springdemoresful.dto.request.UserFilterRequest;
import com.duong.springdemoresful.model.Role;
import com.duong.springdemoresful.model.User;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import org.springframework.data.jpa.domain.Specification;

public class UserSpecification {
    public static Specification<User>hasName(UserFilterRequest filterRequest){

        return(root,query,criteriaBuilder)->{
            if(filterRequest.getName() == null){
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.equal(root.get("name"),filterRequest.getName());
        };
    }
    public static Specification<User>hasRole(UserFilterRequest filterRequest){

        return(root,query,criteriaBuilder)->{
            if(filterRequest.getRoleName() == null){
                return criteriaBuilder.conjunction();
            }
            Join<User, Role> role = root.join("role", JoinType.INNER);
            return criteriaBuilder.equal(criteriaBuilder.lower(role.get("name")),filterRequest.getRoleName());
        };
    }
}
