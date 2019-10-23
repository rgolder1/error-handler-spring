package errorhandler.factory;

import errorhandler.api.ApiError;

import java.util.List;
import java.util.Locale;

import org.springframework.validation.BindingResult;

public interface ErrorFactory {

	List<ApiError> convertErrors(BindingResult bindingResult, Locale locale);
}
