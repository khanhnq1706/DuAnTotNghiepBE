package com.example.demo.request;

import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CategoryRequest {
	int idCategory;
	@NotNull(message = "Is_Deleted_not_null")
    String isDeleted;
	@NotNull(message = "Name_Category_not_null")
    String NameCategory;
}
