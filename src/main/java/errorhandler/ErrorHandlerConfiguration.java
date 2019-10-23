package errorhandler;

import java.util.Locale;

import errorhandler.factory.DefaultErrorFactory;
import errorhandler.factory.ErrorFactory;
import errorhandler.messages.MessageSource;
import errorhandler.rest.RestErrorHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.Validator;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Spring Configuration for standardised error handling in API services
 */
@Configuration
public class ErrorHandlerConfiguration implements WebMvcConfigurer {

	/**
	 * Override the default message source with our own
	 * @return
	 */
	@Bean
	public MessageSource messageSource() {
		Locale.setDefault(Locale.ENGLISH);
		MessageSource messageSource = new MessageSource().loadMessages();
		messageSource.setDefaultEncoding("UTF-8");
		return messageSource;
	}

	@Override
	public Validator getValidator(){
		LocalValidatorFactoryBean validator = new LocalValidatorFactoryBean();
		validator.setValidationMessageSource(messageSource());
		return validator;
	}

	@Bean
	public ErrorFactory errorFactory(MessageSource messageSource){
		return new DefaultErrorFactory(messageSource);
	}

	@Bean
	public RestErrorHandler restErrorHandler(ErrorFactory errorFactory) {
		return new RestErrorHandler(errorFactory);
	}
}
