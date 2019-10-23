package errorhandler.factory;

import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.validation.ConstraintViolation;

import errorhandler.messages.MessageSource;
import errorhandler.api.ApiError;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

public class DefaultErrorFactory implements ErrorFactory {

	private final MessageSource messageSource;

	public DefaultErrorFactory(MessageSource messageSource){
		this.messageSource = messageSource;
	}

	@Override
	public List<ApiError> convertErrors(BindingResult bindingResult, Locale locale){
		return Stream.concat(bindingResult.getFieldErrors().stream(), bindingResult.getGlobalErrors().stream())
				.map(e->convertError(e,locale))
				.sorted()
				.collect(Collectors.toList());
	}

	private ApiError convertError(ObjectError source, Locale locale){
		if ( source.contains(ConstraintViolation.class)){
			return convertConstraintViolation(source);
		} else{
			return ApiError.of(source.getDefaultMessage(), null);
		}
	}

	private ApiError convertConstraintViolation(ObjectError source) {
		ConstraintViolation violation = source.unwrap(ConstraintViolation.class);
		String location = violation.getPropertyPath()==null?null:violation.getPropertyPath().toString();
		// this message will already be localised by spring/validator
		return ApiError.of(violation.getMessage(), location);
	}
}
