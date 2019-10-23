package errorhandler.rest;

import java.util.Locale;

import errorhandler.factory.ErrorFactory;
import errorhandler.api.ApiErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Handle some exceptions in a standard way and try to handle Localisation
 * by using any incoming Accept-Language header to translate the error message into
 * the appropriate language
 */
@ControllerAdvice
public class RestErrorHandler {

	private final ErrorFactory errorFactory;

	public RestErrorHandler(ErrorFactory errorFactory){
		this.errorFactory = errorFactory;
	}

	// VALIDATION ERRORS

	@ExceptionHandler(MethodArgumentNotValidException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ResponseBody
	public ApiErrorResponse handleValidationError(MethodArgumentNotValidException exception, Locale locale) {
		BindingResult bindingResult = exception.getBindingResult();
		return ApiErrorResponse.of(errorFactory.convertErrors(bindingResult, locale));
	}
}
