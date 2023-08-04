package baykov.daniel.security.app.exception;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
public class SecurityAPIException extends RuntimeException {

    private HttpStatus status;
    private String message;

    public SecurityAPIException(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }
}
