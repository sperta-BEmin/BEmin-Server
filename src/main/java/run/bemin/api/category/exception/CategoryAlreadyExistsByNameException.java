package run.bemin.api.category.exception;

public class CategoryAlreadyExistsByNameException extends RuntimeException {

    public CategoryAlreadyExistsByNameException(String message) {
        super(message);
    }
}
