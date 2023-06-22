package SOT.Squad.code.generation.exceptions;

import org.springframework.http.HttpStatus;

public interface ExceptionBase {
    public HttpStatus getStatusCode();
}
