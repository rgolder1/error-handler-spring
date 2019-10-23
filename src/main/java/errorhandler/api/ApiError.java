package errorhandler.api;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.ToString;

import javax.validation.constraints.NotNull;

@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiError {

	@NotNull
	private final String errorMessage;

	private final String location;

	@JsonCreator
	public ApiError(@JsonProperty("errorMessage") String errorMessage,
					@JsonProperty("location") String location) {
		this.errorMessage = errorMessage;
		this.location = location;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public String getLocation() {
		return location;
	}

	public static ApiError of (String message, String location) {
		if ( message == null ){
			throw new NullPointerException("ErrorMessage is a required field!");
		}
		return new ApiError(message,location);
	}
}
