package example.api;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SimpleRequest {

	@Min(value = 5, message = "{test.api.simpleupdate.number.min}")
	@Max(value = 10, message = "{test.api.simpleupdate.number.max}")
	private int number;
}
