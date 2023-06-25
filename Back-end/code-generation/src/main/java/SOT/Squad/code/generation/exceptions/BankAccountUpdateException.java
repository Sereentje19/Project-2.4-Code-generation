package SOT.Squad.code.generation.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "BankAccount could not be Updated, please try again later.")
public class BankAccountUpdateException extends RuntimeException implements ExceptionBase{

    public BankAccountUpdateException(String message) {
        super(message);
    }

    @Override
    public HttpStatus getStatusCode() {
        return HttpStatus.BAD_REQUEST;
    }
}
