package pl.bk.contract.domain.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static pl.bk.contract.domain.exception.ErrorCode.INTERNAL_ERROR;

@ControllerAdvice
public class ExceptionHandler extends ResponseEntityExceptionHandler
{
    @org.springframework.web.bind.annotation.ExceptionHandler(value = AppException.class)
    public ResponseEntity<ErrorMessage> handleRequest(AppException exception)
    {
        return ResponseEntity
            .status(exception.getStatus())
            .body(new ErrorMessage(exception.getErrorCode()));
    }
    
    @org.springframework.web.bind.annotation.ExceptionHandler(value = Exception.class)
    public ResponseEntity<ErrorMessage> handleRequest()
    {
        return ResponseEntity
            .status(INTERNAL_SERVER_ERROR)
            .body(new ErrorMessage(INTERNAL_ERROR));
    }
}
