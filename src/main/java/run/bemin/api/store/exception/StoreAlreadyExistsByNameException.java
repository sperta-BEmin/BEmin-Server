package run.bemin.api.store.exception;

public class StoreAlreadyExistsByNameException extends RuntimeException {

    public StoreAlreadyExistsByNameException(String message) {
        super(message);
    }
}
