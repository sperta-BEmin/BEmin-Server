package run.bemin.api.image.util;

import com.sksamuel.scrimage.pixels.Pixel;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;

public class PixelUtil {
  public static Pixel[] getPixelArrayFromImage(BufferedImage image) {
    int width = image.getWidth();
    int height = image.getHeight();
    boolean hasAlphaChannel = image.getAlphaRaster() != null;
    byte[] data = ((DataBufferByte) image.getRaster().getDataBuffer()).getData();

    if (hasAlphaChannel) {
      return getPixelsWithAlphaChannel(data, width, height);
    }
    return getPixels(data, width, height);
  }

  private static Pixel[] getPixelsWithAlphaChannel(byte[] data, int width, int height) {
    Pixel[] result = new Pixel[height * width];
    int pixelLength = 4;
    int index = 0;

    for (int pixel = 0; pixel < data.length; pixel += pixelLength) {
      if (pixel + 3 >= data.length) break;

      int argb = 0;
      argb += ((data[pixel] & 0xff) << 24); // alpha
      argb += (data[pixel + 1] & 0xff); // blue
      argb += ((data[pixel + 2] & 0xff) << 8); // green
      argb += ((data[pixel + 3] & 0xff) << 16); // red

      int row = index / width;
      int col = index % width;
      result[index] = new Pixel(col, row, argb);
      index++;
    }
    return result;
  }

  private static Pixel[] getPixels(byte[] data, int width, int height) {
    Pixel[] result = new Pixel[height * width];
    int pixelLength = 3;
    int index = 0;

    for (int pixel = 0; pixel < data.length; pixel += pixelLength) {
      if (pixel + 2 >= data.length) break;

      int argb = 0;
      argb += 0xff000000; // 255 alpha
      argb += (data[pixel] & 0xff); // blue
      argb += ((data[pixel + 1] & 0xff) << 8); // green
      argb += ((data[pixel + 2] & 0xff) << 16); // red

      int row = index / width;
      int col = index % width;
      result[index] = new Pixel(col, row, argb);
      index++;
    }
    return result;
  }
}
