package com.bookinghotel.service.impl;

import com.bookinghotel.constant.CommonConstant;
import com.bookinghotel.constant.CommonMessage;
import com.bookinghotel.constant.ErrorMessage;
import com.bookinghotel.dto.CategoryDTO;
import com.bookinghotel.dto.common.CommonResponseDTO;
import com.bookinghotel.entity.Category;
import com.bookinghotel.exception.InternalServerException;
import com.bookinghotel.exception.NotFoundException;
import com.bookinghotel.mapper.CategoryMapper;
import com.bookinghotel.repository.CategoryRepository;
import com.bookinghotel.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

  private final CategoryRepository categoryRepository;

  private final CategoryMapper categoryMapper;

  @Override
  public List<CategoryDTO> getCategories() {
    return categoryMapper.toCategoryDTOs(categoryRepository.findAll());
  }

  @Override
  public CategoryDTO createCategory(String name) {
    Category category = new Category();
    category.setName(name);
    return categoryMapper.toCategoryDTO(categoryRepository.save(category));
  }

  @Override
  public CategoryDTO updateCategory(Long categoryId, String name) {
    Optional<Category> category = categoryRepository.findById(categoryId);
    checkNotFoundCategoryById(category, categoryId);
    category.get().setName(name);
    return categoryMapper.toCategoryDTO(categoryRepository.save(category.get()));
  }

  @Override
  public CommonResponseDTO deleteCategory(Long categoryId) {
    Optional<Category> category = categoryRepository.findById(categoryId);
    checkNotFoundCategoryById(category, categoryId);

    try {
      category.get().setDeleteFlag(CommonConstant.TRUE);
      categoryRepository.save(category.get());
      return new CommonResponseDTO(CommonConstant.TRUE, CommonMessage.DELETE_SUCCESS);
    } catch (Exception e) {
      throw new InternalServerException(ErrorMessage.ERR_EXCEPTION_GENERAL);
    }
  }

  private void checkNotFoundCategoryById(Optional<Category> category, Long categoryId) {
    if (category.isEmpty()) {
      throw new NotFoundException(String.format(ErrorMessage.Category.ERR_NOT_FOUND_ID, categoryId));
    }
  }

}
