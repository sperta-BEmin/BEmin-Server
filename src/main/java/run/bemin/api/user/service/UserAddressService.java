package run.bemin.api.user.service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import run.bemin.api.general.exception.ErrorCode;
import run.bemin.api.user.dto.UserAddressRequestDto;
import run.bemin.api.user.dto.UserAddressResponseDto;
import run.bemin.api.user.entity.User;
import run.bemin.api.user.entity.UserAddress;
import run.bemin.api.user.exception.UserAddressNotFoundException;
import run.bemin.api.user.exception.UserNotFoundException;
import run.bemin.api.user.exception.UserUnauthorizedException;
import run.bemin.api.user.repository.UserAddressRepository;
import run.bemin.api.user.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class UserAddressService {

  private final UserRepository userRepository;
  private final UserAddressRepository userAddressRepository;

  /**
   * 특정 회원의 배달 주소 추가 - 무조건 대표 주소로 등록
   */
  @Transactional
  public UserAddressResponseDto addAddress(String userEmail, UserAddressRequestDto addressRequestDto) {
    User user = userRepository.findByUserEmail(userEmail)
        .orElseThrow(() -> new UserNotFoundException(ErrorCode.USER_NOT_FOUND.getMessage()));

    // 기존 대표 주소 조회 후 대표 주소 해제
    List<UserAddress> existingReps = userAddressRepository.findByUserAndIsRepresentativeTrue(user);
    for (UserAddress addr : existingReps) {
      addr.setRepresentative(false);
      userAddressRepository.save(addr);
    }

    // 추가 된 주소-> 대표 주소 변경
    UserAddress userAddress = UserAddress.builder()
        .bcode(addressRequestDto.getBcode())
        .jibunAddress(addressRequestDto.getJibunAddress())
        .roadAddress(addressRequestDto.getRoadAddress())
        .detail(addressRequestDto.getDetail())
        .isRepresentative(true)
        .user(user)
        .build();

    UserAddress savedAddress = userAddressRepository.save(userAddress);

    user.setRepresentativeAddress(savedAddress);
    userRepository.save(user);

    return new UserAddressResponseDto(savedAddress);
  }

  /**
   * 특정 회원의 주소 목록 조회
   */
  @Transactional(readOnly = true)
  public List<UserAddressResponseDto> getAddresses(String userEmail) {
    User user = userRepository.findByUserEmail(userEmail)
        .orElseThrow(() -> new UserNotFoundException(ErrorCode.USER_NOT_FOUND.getMessage()));

    return userAddressRepository.findByUser(user)
        .stream()
        .map(UserAddressResponseDto::new)
        .collect(Collectors.toList());
  }

  @Transactional
  public UserAddressResponseDto setRepresentativeAddress(String userEmail, UUID addressId) {

    User user = userRepository.findByUserEmail(userEmail)
        .orElseThrow(() -> new UserNotFoundException(ErrorCode.USER_NOT_FOUND.getMessage()));

    // 기존 대표 주소 해지
    List<UserAddress> existingReps = userAddressRepository.findByUserAndIsRepresentativeTrue(user);
    for (UserAddress addr : existingReps) {
      addr.setRepresentative(false);
      userAddressRepository.save(addr);
    }

    // 새로 지정할 대표 주소 조회 & 소유 여부 확인
    UserAddress newRep = userAddressRepository.findById(addressId)
        .orElseThrow(() -> new UserAddressNotFoundException(ErrorCode.USER_ADDRESS_NOT_FOUND.getMessage()));
    if (!newRep.getUser().getUserEmail().equals(userEmail)) {
      throw new UserUnauthorizedException(ErrorCode.USER_UNAUTHORIZED.getMessage());
    }

    // 새 주소를 대표 주소로 업데이트
    newRep.setRepresentative(true);
    userAddressRepository.save(newRep);

    // 회원의 대표 주소 필드 업데이트
    user.setRepresentativeAddress(newRep);
    userRepository.save(user);

    return new UserAddressResponseDto(newRep);
  }


}
