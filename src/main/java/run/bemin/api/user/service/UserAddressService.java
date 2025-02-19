package run.bemin.api.user.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import run.bemin.api.general.exception.ErrorCode;
import run.bemin.api.user.dto.UserAddressRequestDto;
import run.bemin.api.user.dto.UserAddressResponseDto;
import run.bemin.api.user.entity.User;
import run.bemin.api.user.entity.UserAddress;
import run.bemin.api.user.exception.UserNotFoundException;
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
        .zoneCode(addressRequestDto.getZoneCode())
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
}
