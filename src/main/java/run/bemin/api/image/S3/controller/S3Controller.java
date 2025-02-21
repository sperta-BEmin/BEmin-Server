package run.bemin.api.image.S3.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import run.bemin.api.image.S3.service.S3Service;
import run.bemin.api.image.util.UrlUtil;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/image")
@Slf4j
public class S3Controller {

  private final S3Service s3Service;
  private final UrlUtil util;

  @PostMapping
  public ResponseEntity<String> uploadFile(@RequestParam("url") String url){
    String uploadImg = util.getFoodImgAndUploadImg(url);
    return ResponseEntity.ok(uploadImg);
  }
}
