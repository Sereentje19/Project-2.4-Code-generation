package SOT.Squad.code.generation.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Account is not active, please contact your administrator.")
public class InActiveAccountException extends RuntimeException implements ExceptionBase{
    public InActiveAccountException(String message) {
        super(message);
    }

    @Override
    public HttpStatus getStatusCode() {
        return HttpStatus.BAD_REQUEST;
    }
}
