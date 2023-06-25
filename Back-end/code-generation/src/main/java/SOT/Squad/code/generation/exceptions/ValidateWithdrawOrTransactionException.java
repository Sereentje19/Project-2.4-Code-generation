package SOT.Squad.code.generation.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "something went wrong validating the request, please try again later.")
public class ValidateWithdrawOrTransactionException extends RuntimeException implements ExceptionBase{

    public ValidateWithdrawOrTransactionException(String message) {
        super(message);
    }

    @Override
    public HttpStatus getStatusCode() {
        return HttpStatus.BAD_REQUEST;
    }
}
