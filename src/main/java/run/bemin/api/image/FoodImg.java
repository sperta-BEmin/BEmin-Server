package run.bemin.api.image;

import com.sksamuel.scrimage.ImmutableImage;
import java.awt.image.BufferedImage;
import run.bemin.api.image.util.PixelUtil;

public class FoodImg {
  private final BufferedImage image;

  public FoodImg(BufferedImage image) {
    this.image = image;
  }

  public boolean hasMinimumImageSize(int size) {
    return image.getWidth() >= size && image.getHeight() >= size;
  }

  public boolean hasAspectRatioRange(double min, double max) {
    double ratio = (image.getHeight() / (double) image.getWidth()) * 100;
    return ratio >= min && ratio <= max;
  }

  public boolean isImageSizeSmallerWidth(int size) {
    return image.getWidth() < size;
  }

  public ImmutableImage getImmutableImage() {
    return ImmutableImage.create(
        image.getWidth(),
        image.getHeight(),
        PixelUtil.getPixelArrayFromImage(image),
        BufferedImage.TYPE_3BYTE_BGR
    );
  }

  public ImmutableImage getImmutableImageWithScaleToWidth(int width) {
    return getImmutableImage().scaleToWidth(width);
  }
}
