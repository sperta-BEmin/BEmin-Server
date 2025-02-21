package run.bemin.api.image.util;

import com.sksamuel.scrimage.webp.WebpWriter;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.util.UUID;
import javax.imageio.ImageIO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import run.bemin.api.image.FoodImg;
import run.bemin.api.image.S3.service.S3Service;

@Slf4j
@Component
public class UrlUtil {
  @Value("${file.food-path}")
  private String path;

  private final S3Service s3Service;

  private static final int TARGET_WIDTH = 360;
  private static final String WEBP_SUFFIX = ".webp";

  public UrlUtil(S3Service s3Service) {
    this.s3Service = s3Service;
  }

  public String getFoodImgAndUploadImg(String url) {
    initFilePath();
    FoodImg img = getBufferImages(url);
    return ResizeAndConvertWebp(img);
  }

  private void initFilePath() {
    File file = new File(path);
    if (!file.exists()) {
      file.mkdir();
    }
  }

  private FoodImg getBufferImages(String url) {
    try {
      return new FoodImg(ImageIO.read(new URI(url).toURL()));
    } catch (Exception e) {
      log.error("Failed to load image from URL: {}", url, e);
      return null;
    }
  }

  private String ResizeAndConvertWebp(FoodImg foodImg) {
    File convertImage = null;
    try {
      convertImage = (
          foodImg.isImageSizeSmallerWidth(TARGET_WIDTH) ?
          foodImg.getImmutableImage() :
          foodImg.getImmutableImageWithScaleToWidth(TARGET_WIDTH)
      ).output(WebpWriter.DEFAULT, new File(path + "/" + UUID.randomUUID() + WEBP_SUFFIX));
    } catch (IOException e) {
      log.error("Failed to convert image", e);
      return null;
    }

    s3Service.uploadImg(convertImage);
    convertImage.delete();
    return convertImage.getName();
  }
}
