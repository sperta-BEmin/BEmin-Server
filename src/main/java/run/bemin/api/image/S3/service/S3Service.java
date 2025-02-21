package run.bemin.api.image.S3.service;

import io.awspring.cloud.s3.ObjectMetadata;
import io.awspring.cloud.s3.S3Operations;
import io.awspring.cloud.s3.S3Resource;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.web.servlet.MultipartProperties;
import org.springframework.stereotype.Service;
import run.bemin.api.image.exception.S3UploadFailException;

@Slf4j
@Service
@RequiredArgsConstructor
public class S3Service {

  private final S3Operations s3Operations;
  private final MultipartProperties multipartProperties;

  @Value("${spring.cloud.aws.s3.bucket}")
  private String bucket;

  @Value("${file.food-path}")
  private String path;

  private S3Resource uploadFileToS3(File file) {
    try (FileInputStream fileInputStream = new FileInputStream(file)) {
      return s3Operations.upload(
          bucket,
          "food" + "/" + file.getName(),
          fileInputStream,
          ObjectMetadata.builder()
              .contentType(Files.probeContentType(file.toPath()))
              .build()
      );
    } catch (IOException e) {
      throw new S3UploadFailException();
    }
  }


  public String uploadImg(File img) {
    S3Resource upload = uploadFileToS3(img);
    return upload.getFilename();
  }

}
