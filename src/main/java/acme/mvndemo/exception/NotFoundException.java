package acme.mvndemo.exception;

public class NotFoundException extends Exception{
    public NotFoundException(Class<?> clazz, Long id) {
        super(String.format("Entity %s with id %d not found", clazz.getSimpleName(), id));
    }
}
