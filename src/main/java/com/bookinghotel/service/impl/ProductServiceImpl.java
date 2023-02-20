package com.bookinghotel.service.impl;

import com.bookinghotel.constant.CommonConstant;
import com.bookinghotel.constant.CommonMessage;
import com.bookinghotel.constant.ErrorMessage;
import com.bookinghotel.constant.SortByDataConstant;
import com.bookinghotel.dto.ProductCreateDTO;
import com.bookinghotel.dto.ProductDTO;
import com.bookinghotel.dto.ProductUpdateDTO;
import com.bookinghotel.dto.common.CommonResponseDTO;
import com.bookinghotel.dto.pagination.PaginationResponseDTO;
import com.bookinghotel.dto.pagination.PaginationSearchSortRequestDTO;
import com.bookinghotel.dto.pagination.PagingMeta;
import com.bookinghotel.entity.Media;
import com.bookinghotel.entity.Product;
import com.bookinghotel.exception.InternalServerException;
import com.bookinghotel.exception.NotFoundException;
import com.bookinghotel.mapper.MediaMapper;
import com.bookinghotel.mapper.ProductMapper;
import com.bookinghotel.repository.MediaRepository;
import com.bookinghotel.repository.ProductRepository;
import com.bookinghotel.service.ProductService;
import com.bookinghotel.util.PaginationUtil;
import com.bookinghotel.util.UploadFileUtil;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class ProductServiceImpl implements ProductService {

  private final ProductRepository productRepository;

  private final MediaRepository mediaRepository;

  private final ProductMapper productMapper;

  private final MediaMapper mediaMapper;

  private final UploadFileUtil uploadFile;

  @Override
  public ProductDTO getProduct(Long productId) {
    Optional<Product> product = productRepository.findById(productId);
    checkNotFoundProductById(product, productId);
    return productMapper.toProductDTO(product.get());
  }

  @Override
  public PaginationResponseDTO<ProductDTO> getProducts(PaginationSearchSortRequestDTO requestDTO) {
    //Pagination
    Pageable pageable = PaginationUtil.buildPageable(requestDTO, SortByDataConstant.PRODUCT);
    Page<Product> products = productRepository.findAll(pageable);

    //Create Output
    PagingMeta meta = PaginationUtil.buildPagingMeta(requestDTO, SortByDataConstant.PRODUCT, products);
    List<ProductDTO> productDTOs = productMapper.toProductDTOs(products.getContent());
    return new PaginationResponseDTO<ProductDTO>(meta, productDTOs);
  }

  @Override
  public ProductDTO createProduct(ProductCreateDTO productCreateDTO) {
    Product product = productMapper.createDtoToProduct(productCreateDTO);
    product.setThumbnail(uploadFile.getUrlFromFile(productCreateDTO.getThumbnailFile()));
    productRepository.save(product);
    return productMapper.toProductDTO(productRepository.save(product));
  }

  @Override
  public ProductDTO updateProduct(Long productId, ProductUpdateDTO productUpdateDTO) {
    Optional<Product> currentProduct = productRepository.findById(productId);
    checkNotFoundProductById(currentProduct, productId);

    //Delete media if not found in mediaDTO
    if (CollectionUtils.isNotEmpty(productUpdateDTO.getMedias())) {
      List<Media> medias = mediaMapper.toMedias(productUpdateDTO.getMedias());
      List<Media> mediaDeleteFlag = mediaRepository.findByProductIdAndNotInMedia(productId, medias);
      if (CollectionUtils.isNotEmpty(mediaDeleteFlag)) {
        mediaDeleteFlag.forEach(item -> {
          item.setDeleteFlag(CommonConstant.TRUE);
          mediaRepository.save(item);
        });
      }
    } else {
      List<Media> mediaDeleteFlag = mediaRepository.findByProductId(productId);
      if (CollectionUtils.isNotEmpty(mediaDeleteFlag)) {
        mediaDeleteFlag.forEach(item -> {
          item.setDeleteFlag(CommonConstant.TRUE);
          mediaRepository.save(item);
        });
      }
    }
    //update thumbnail
    if(StringUtils.isEmpty(productUpdateDTO.getThumbnail()) && productUpdateDTO.getThumbnailFile() != null) {
      currentProduct.get().setThumbnail(uploadFile.getUrlFromFile(productUpdateDTO.getThumbnailFile()));
    }
    productMapper.updateProductFromDTO(productUpdateDTO, currentProduct.get());
    return productMapper.toProductDTO(productRepository.save(currentProduct.get()));
  }

  @Override
  public CommonResponseDTO deleteProduct(Long productId) {
    Optional<Product> product = productRepository.findById(productId);
    checkNotFoundProductById(product, productId);

    try {
      product.get().setDeleteFlag(CommonConstant.TRUE);
      //set deleteFlag Media
      List<Media> mediaDeleteFlag = mediaRepository.findByProductId(productId);
      if (CollectionUtils.isNotEmpty(mediaDeleteFlag)) {
        mediaDeleteFlag.forEach(item -> {
          item.setDeleteFlag(CommonConstant.TRUE);
          mediaRepository.save(item);
        });
      }
      productRepository.save(product.get());
      return new CommonResponseDTO(CommonConstant.TRUE, CommonMessage.DELETE_SUCCESS);
    } catch (Exception e) {
      throw new InternalServerException(ErrorMessage.ERR_EXCEPTION_GENERAL);
    }
  }

  private void checkNotFoundProductById(Optional<Product> product, Long productId) {
    if (product.isEmpty()) {
      throw new NotFoundException(String.format(ErrorMessage.Product.ERR_NOT_FOUND_ID, productId));
    }
  }

}
