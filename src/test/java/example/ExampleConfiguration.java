package example;

import errorhandler.ErrorHandlerConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Import({
		ErrorHandlerConfiguration.class
		})
@Configuration
public class ExampleConfiguration {
}
