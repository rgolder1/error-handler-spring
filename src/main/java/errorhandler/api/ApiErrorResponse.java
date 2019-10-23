package errorhandler.api;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApiErrorResponse {
	private List<ApiError> errors;

	public static ApiErrorResponse of(List<ApiError> errors){
		return new ApiErrorResponse(errors);
	}
}
