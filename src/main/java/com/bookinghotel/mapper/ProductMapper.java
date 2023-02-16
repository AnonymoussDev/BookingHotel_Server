package com.bookinghotel.mapper;

import com.bookinghotel.dto.MediaDTO;
import com.bookinghotel.dto.ProductCreateDTO;
import com.bookinghotel.dto.ProductDTO;
import com.bookinghotel.dto.ProductUpdateDTO;
import com.bookinghotel.entity.Media;
import com.bookinghotel.entity.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProductMapper {

  @Mapping(target = "categoryId", source = "product.category.id")
  ProductDTO toProductDTO(Product product);

  List<ProductDTO> toProductDTOs(List<Product> products);

  MediaDTO toMediaDTO(Media media);

  Product createDtoToProduct(ProductCreateDTO createDTO);

  @Mapping(target = "medias", ignore = true)
  void updateProductFromDTO(ProductUpdateDTO updateDTO, @MappingTarget Product room);

}
