package run.bemin.api.image.exception;

public class S3UploadFailException extends RuntimeException {
  public S3UploadFailException() {
  }

  public S3UploadFailException(String message) {
    super(message);
  }
}
