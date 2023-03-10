package com.bookinghotel.mapper;

import com.bookinghotel.dto.ProductCreateDTO;
import com.bookinghotel.dto.ProductDTO;
import com.bookinghotel.dto.ProductUpdateDTO;
import com.bookinghotel.entity.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProductMapper {

  @Mapping(target = "serviceId", source = "product.service.id")
  ProductDTO toProductDTO(Product product);

  List<ProductDTO> toProductDTOs(List<Product> products);

  Product createDtoToProduct(ProductCreateDTO createDTO);

  void updateProductFromDTO(ProductUpdateDTO updateDTO, @MappingTarget Product product);

}
