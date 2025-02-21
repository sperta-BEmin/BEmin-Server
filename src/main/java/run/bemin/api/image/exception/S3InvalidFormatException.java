package run.bemin.api.image.exception;

public class S3InvalidFormatException extends RuntimeException{
  public S3InvalidFormatException() {
  }

  public S3InvalidFormatException(String message) {
    super(message);
  }
}
