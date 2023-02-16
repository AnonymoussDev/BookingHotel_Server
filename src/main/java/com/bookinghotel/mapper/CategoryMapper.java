package com.bookinghotel.mapper;

import com.bookinghotel.dto.CategoryDTO;
import com.bookinghotel.entity.Category;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CategoryMapper {

  CategoryDTO toCategoryDTO(Category category);

  List<CategoryDTO> toCategoryDTOs(List<Category> categories);

}
