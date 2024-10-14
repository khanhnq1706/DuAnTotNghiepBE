package com.example.demo.Specification;

import com.example.demo.entity.FoodEntity;
import org.springframework.data.jpa.domain.Specification;

public class FoodSpecs {

    public static Specification<FoodEntity> hasIdCategory(String idCategory) {
        return (root, query, criteriaBuilder) -> {
            try {
                Integer categoryId = idCategory == null ? null : Integer.parseInt(idCategory);
                if (categoryId != null) {
                    return criteriaBuilder.equal(root.join("category").get("idCategory"), categoryId);
                }
                return null;
            } catch (Exception e) {
                throw new RuntimeException("Invalid_id_Category");
            }

        };

    }

    public static Specification<FoodEntity> hasNameFood(String keyword) {
        return (root, query, criteriaBuilder) -> {
            if (keyword != null) {
                return criteriaBuilder.like(root.get("nameFood"), "%" + keyword + "%");
            }
            return null;
        };
    }

    public static Specification<FoodEntity> isSelling(String isSelling) {
        return (root, query, criteriaBuilder) ->{
         if(isSelling!=null){

             return criteriaBuilder.equal(root.get("isSelling"), Boolean.valueOf(isSelling));
         }

            return null;
        };

    }

}
