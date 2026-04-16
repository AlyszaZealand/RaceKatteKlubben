package feedback.racekatteklubben.Service.Validation;

import feedback.racekatteklubben.Model.Cat;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Base64;

@Component
public class ValidateCat {

    public ValidationResult validateRegisterCat(Cat cat) {
        ValidationResult result = new ValidationResult();
        if(cat.getCatName() == null || cat.getCatName().trim().isEmpty()){
            result.addError("Du skal indtaste et navn til katten");
        }

        if (cat.getCatBirthday() == null || cat.getCatBirthday().isAfter(LocalDate.now())) {
            result.addError("Du skal indtaste en gyldig fødselsdato for katten (må ikke være i fremtiden).");
        }

        return result;
    }

}
