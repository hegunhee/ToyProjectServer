package gunhee.simplememo.controller.advice;

import gunhee.simplememo.controller.advice.response.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.NoSuchElementException;

@Slf4j
@RestControllerAdvice(basePackages = "gunhee.simplememo.controller")
public class ExceptionControllerAdvice {

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NoSuchElementException.class)
    public ErrorResponse handleNoSuchElementException(NoSuchElementException e) {
        log.error(e.getMessage());
        return new ErrorResponse(HttpStatus.NOT_FOUND.value(),HttpStatus.NOT_FOUND.toString(),e.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ErrorResponse handleArgumentValidException(MethodArgumentNotValidException e) {
        log.error(e.getMessage());
        String message = "";
        if(e.getDetailMessageArguments() != null) {
            message = getValidParameterMessage(e.getDetailMessageArguments());
        }
        return new ErrorResponse(HttpStatus.BAD_REQUEST.value(),HttpStatus.BAD_REQUEST.toString(),message);
    }

    private String getValidParameterMessage(Object[] messageArguments) {
        StringBuilder builder = new StringBuilder();
        for (Object messageArgument : messageArguments) {
            String message = messageArgument.toString();
            if(!message.isEmpty()) {
                builder.append(message);
            }
        }
        return builder.toString();
    }
}
