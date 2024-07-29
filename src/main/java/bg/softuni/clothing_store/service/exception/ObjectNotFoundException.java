package bg.softuni.clothing_store.service.exception;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Getter
@Setter
@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class ObjectNotFoundException extends RuntimeException {

    private final Object id;

    public ObjectNotFoundException(String message, Object id) {
        super(message);
        this.id = id;
    }




}
