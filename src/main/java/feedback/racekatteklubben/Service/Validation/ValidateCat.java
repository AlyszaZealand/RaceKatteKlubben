package feedback.racekatteklubben.Service.Validation;

import feedback.racekatteklubben.Model.Cat;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class ValidateCat {

    public ValidationResult validateRegisterCat(Cat cat){
        ValidationResult result = new ValidationResult();
        if(cat.getCatName() == null || cat.getCatName().trim().isEmpty()){
            result.addError("Du skal indtaste et navn til katten");
        }

        if (cat.getCatBirthday() == null || cat.getCatBirthday().isAfter(LocalDate.now())) {
            result.addError("Du skal indtaste en gyldig fødselsdato for katten (må ikke være i fremtiden).");
        }

        if (cat.getCatGender() == null || cat.getCatGender().trim().isEmpty()
                || cat.getCatGender().equals("Han") || cat.getCatGender().equals("Hun")) {
            result.addError("Du skal indtaste enten 'Han eller Hun' køn til din kat.");
        }

        return result;
    }

}
