package SOT.Squad.code.generation.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Wrong pincode, please try again later.")
public class WrongPincodeException extends RuntimeException implements ExceptionBase{

    public WrongPincodeException(String message) {
        super(message);
    }

    @Override
    public HttpStatus getStatusCode() {
        return HttpStatus.BAD_REQUEST;
    }
}
