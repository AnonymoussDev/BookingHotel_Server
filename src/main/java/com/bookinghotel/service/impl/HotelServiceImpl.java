package com.bookinghotel.service.impl;

import com.bookinghotel.constant.CommonConstant;
import com.bookinghotel.constant.CommonMessage;
import com.bookinghotel.constant.ErrorMessage;
import com.bookinghotel.constant.SortByDataConstant;
import com.bookinghotel.dto.ServiceCreateDTO;
import com.bookinghotel.dto.ServiceDTO;
import com.bookinghotel.dto.ServiceUpdateDTO;
import com.bookinghotel.dto.common.CommonResponseDTO;
import com.bookinghotel.dto.pagination.PaginationResponseDTO;
import com.bookinghotel.dto.pagination.PaginationSearchSortRequestDTO;
import com.bookinghotel.dto.pagination.PagingMeta;
import com.bookinghotel.entity.Service;
import com.bookinghotel.exception.InternalServerException;
import com.bookinghotel.exception.NotFoundException;
import com.bookinghotel.mapper.ServiceMapper;
import com.bookinghotel.repository.ServiceRepository;
import com.bookinghotel.service.HotelService;
import com.bookinghotel.util.PaginationUtil;
import com.bookinghotel.util.UploadFileUtil;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@org.springframework.stereotype.Service
public class HotelServiceImpl implements HotelService {

  private final ServiceRepository serviceRepository;

  private final ServiceMapper serviceMapper;

  private final UploadFileUtil uploadFile;

  @Override
  public ServiceDTO getServiceById(Long serviceId) {
    Optional<Service> service = serviceRepository.findById(serviceId);
    checkNotFoundServiceById(service, serviceId);
    return serviceMapper.toServiceDTO(service.get());
  }

  @Override
  public PaginationResponseDTO<ServiceDTO> getServices(PaginationSearchSortRequestDTO requestDTO) {
    //Pagination
    Pageable pageable = PaginationUtil.buildPageable(requestDTO, SortByDataConstant.SERVICE);
    Page<Service> services = serviceRepository.findAllByKey(pageable, requestDTO.getKeyword());

    //Create Output
    PagingMeta meta = PaginationUtil.buildPagingMeta(requestDTO, SortByDataConstant.SERVICE, services);
    List<ServiceDTO> serviceDTOs = serviceMapper.toServiceDTOs(services.getContent());
    return new PaginationResponseDTO<ServiceDTO>(meta, serviceDTOs);
  }

  @Override
  public ServiceDTO createService(ServiceCreateDTO serviceCreateDTO) {
    Service service = serviceMapper.createDtoToProduct(serviceCreateDTO);
    service.setThumbnail(uploadFile.getUrlFromFile(serviceCreateDTO.getThumbnailFile()));
    return serviceMapper.toServiceDTO(serviceRepository.save(service));
  }

  @Override
  public ServiceDTO updateService(Long serviceId, ServiceUpdateDTO serviceUpdateDTO) {
    Optional<Service> currentService = serviceRepository.findById(serviceId);
    checkNotFoundServiceById(currentService, serviceId);
    //update thumbnail
    if(StringUtils.isEmpty(serviceUpdateDTO.getThumbnail()) && serviceUpdateDTO.getThumbnailFile() != null) {
      currentService.get().setThumbnail(uploadFile.getUrlFromFile(serviceUpdateDTO.getThumbnailFile()));
    }
    serviceMapper.updateProductFromDTO(serviceUpdateDTO, currentService.get());
    return serviceMapper.toServiceDTO(serviceRepository.save(currentService.get()));
  }

  @Override
  public CommonResponseDTO deleteService(Long serviceId) {
    Optional<Service> service = serviceRepository.findById(serviceId);
    checkNotFoundServiceById(service, serviceId);

    try {
      service.get().setDeleteFlag(CommonConstant.TRUE);
      serviceRepository.save(service.get());
      return new CommonResponseDTO(CommonConstant.TRUE, CommonMessage.DELETE_SUCCESS);
    } catch (Exception e) {
      throw new InternalServerException(ErrorMessage.ERR_EXCEPTION_GENERAL);
    }
  }

  private void checkNotFoundServiceById(Optional<Service> service, Long serviceId) {
    if (service.isEmpty()) {
      throw new NotFoundException(String.format(ErrorMessage.Product.ERR_NOT_FOUND_ID, serviceId));
    }
  }

}
