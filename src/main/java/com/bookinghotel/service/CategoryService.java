package com.bookinghotel.service;

import com.bookinghotel.dto.CategoryDTO;
import com.bookinghotel.dto.common.CommonResponseDTO;

import java.util.List;

public interface CategoryService {

  List<CategoryDTO> getCategories();

  CategoryDTO createCategory(String name);

  CategoryDTO updateCategory(Long categoryId, String name);

  CommonResponseDTO deleteCategory(Long categoryId);

}
