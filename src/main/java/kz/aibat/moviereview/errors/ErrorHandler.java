package kz.aibat.moviereview.errors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

//Not used but it good example of custom error controller
//@RestController
public class ErrorHandler implements ErrorController {

    private final ErrorAttributes errorAttributes;

    //@Autowired
    public ErrorHandler(ErrorAttributes errorAttributes) {
        this.errorAttributes = errorAttributes;
    }

    @RequestMapping("/error")
    public ApiError handleError(WebRequest webRequest) {
        Map<String,Object> attributes =  this.errorAttributes.getErrorAttributes(webRequest, ErrorAttributeOptions.of(
                ErrorAttributeOptions.Include.MESSAGE,
                ErrorAttributeOptions.Include.BINDING_ERRORS)
        );

        String message = (String) attributes.get("message");
        String path = (String) attributes.get("path");
        int status = (Integer) attributes.get("status");

        ApiError error = new ApiError(status, message, path);

        if(attributes.containsKey("errors")){
            @SuppressWarnings("uncheked")
            List<FieldError> fieldErrorList = (List<FieldError>) attributes.get("errors");

            Map<String, String> argsErrorList = new HashMap<>();

            fieldErrorList.forEach((err) -> {
                String fieldName = ((FieldError) err).getField();
                String errorMessage = err.getDefaultMessage();

                argsErrorList.put(fieldName, errorMessage);
            });

            error.setValidationErrors(argsErrorList);
        }



        return error;
    }
}
