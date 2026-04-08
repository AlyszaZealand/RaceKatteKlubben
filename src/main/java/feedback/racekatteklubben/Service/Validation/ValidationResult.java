package feedback.racekatteklubben.Service.Validation;

import org.springframework.stereotype.Component;

import java.util.List;
import java.util.ArrayList;

@Component
public class ValidationResult {

    private List<String> errors = new ArrayList<>();

    public void addError(String error){
        errors.add(error);
    }

    public boolean hasErrors(){
        return !errors.isEmpty();
    }

    public List<String> getErrors() {
        return errors;
    }
}
