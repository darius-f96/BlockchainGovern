package floread.backendapi.error;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

abstract class ApiSubError {

}

@Data
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
class ApiValidationError extends ApiSubError {
    @Getter @Setter
   private String object;
   @Getter @Setter
   private String field;
   @Getter @Setter
   private Object rejectedValue;
   @Getter @Setter
   private String message;

   ApiValidationError(String object, String message) {
       this.object = object;
       this.message = message;
   }
}