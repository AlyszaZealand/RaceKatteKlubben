package feedback.racekatteklubben.Service.Validation;


import feedback.racekatteklubben.Model.Member;
import org.springframework.stereotype.Component;

@Component
public class ValidateMember {

    public ValidationResult validateRegisterMember(Member member){

        ValidationResult result = new ValidationResult();

        if(member.getUsername() == null || member.getUsername().trim().isEmpty()){
            result.addError("Du skal indtaste et navn");
        }

        if(member.getEmail() == null || !member.getEmail().contains("@")){
            result.addError("Du skal indtaste en  gyldig e-mailadresse (skal indholde @).");
        }

        if(member.getPassword() == null || member.getPassword().trim().isEmpty()){
            result.addError("Du skal indtaste et password");
        }

        else if(member.getPassword().length() < 6){
            result.addError("Dit password skal være mindst 6 tegn langt");
        }
        return result;
    }

    public ValidationResult validateUpdateMember(Member member){
        ValidationResult result = new ValidationResult();

        if(member.getUsername() == null || member.getUsername().trim().isEmpty()){
            result.addError("Du skal indtaste et navn");
        }

        if(member.getEmail() == null || !member.getEmail().contains("@")){
            result.addError("Du skal indtaste en  gyldig e-mailadresse (skal indholde @).");
        }
        return result;
    }
}
