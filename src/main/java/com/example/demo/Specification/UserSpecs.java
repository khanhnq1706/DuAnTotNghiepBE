package com.example.demo.Specification;

import org.springframework.data.jpa.domain.Specification;
import com.example.demo.entity.UserEnitty;

public class UserSpecs {


	public static Specification<UserEnitty> hasUserName(String keyword) {
		return (root, query, criteriaBuilder) -> {
			if (keyword != null) {
				return criteriaBuilder.like(root.get("username"), "%" + keyword + "%");
			}
			System.out.println("username null");
			return null;
		};
	}
	public static Specification<UserEnitty> hasFullName(String keyword) {
		return (root, query, criteriaBuilder) -> {
			if (keyword != null) {
				return criteriaBuilder.like(root.get("fullname"), "%" + keyword + "%");
			}
			System.out.println("fullname null");
			return null;
		};
	}

    public static Specification<UserEnitty> isAdmin(String isAdmin) {
        return (root, query, criteriaBuilder) ->{
         if(isAdmin!=null){

             return criteriaBuilder.equal(root.get("isAdmin"), Boolean.valueOf(isAdmin));
         }
            System.out.println("Admin null");
            return null;
        };

    }

}
