package feedback.racekatteklubben.Service;



import feedback.racekatteklubben.Model.Member;
import feedback.racekatteklubben.Model.RepoInterfaces.CatRepositoryImpl;
import feedback.racekatteklubben.Model.RepoInterfaces.MemberRepositoryImpl;
import feedback.racekatteklubben.Service.Validation.ValidateMember;
import feedback.racekatteklubben.Service.Validation.ValidationResult;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MemberService {

    private MemberRepositoryImpl memberRepository;
    private ValidateMember validateMember;
    private CatRepositoryImpl catRepository;


    public MemberService(MemberRepositoryImpl memberRepository, ValidateMember validateMember, CatRepositoryImpl catRepository) {
        this.memberRepository = memberRepository;
        this.validateMember = validateMember;
        this.catRepository = catRepository;
    }


    public List<Member> getAllMembers(){

        List<Member> members = memberRepository.findAllMembers();

        for(Member member : members){
            member.setCatList(catRepository.findCatsByMemberID(member.getMemberID()));
        }

        return members;
    }



    public Optional<Member> getMemberByEmail(String email){
        return memberRepository.findMemberByEmail(email);
    }

    public Optional<Member> getMemberByID(int memberID){
        return memberRepository.findMemberByID(memberID);
    }

    public void saveProfile(Member member){
        memberRepository.saveProfile(member);
    }

    public void deleteProfile(Member member){
        memberRepository.deleteProfile(member.getMemberID());
    }

    public ValidationResult updateMemberInformation(Member member){
        ValidationResult result = validateMember.validateUpdateMember(member);

        if (result.hasErrors()){
            return result;
        }
        memberRepository.updateMemberInformation(member);
        return result;
    }

    public ValidationResult updateMemberPassword(Member member, String currentPassword, String newPassword, String confirmPassword) {
        ValidationResult result = new ValidationResult();

        //Ryke til validateMember som sin egen metode
        // 1. Check current password is correct
        if (!BCrypt.checkpw(currentPassword, member.getPassword())) {
            result.addError("Det nuværende password er forkert");
            return result;
        }

        // 2. Check new passwords match
        if (!newPassword.equals(confirmPassword)) {
            result.addError("De nye passwords matcher ikke");
            return result;
        }

        // 3. Check new password length
        if (newPassword == null || newPassword.length() < 6) {
            result.addError("Det nye password skal være mindst 6 tegn langt");
            return result;
        }

        // 4. Hash and save
        String hashedPassword = BCrypt.hashpw(newPassword, BCrypt.gensalt());
        member.setPassword(hashedPassword);
        memberRepository.updateMemberPassword(member);

        return result;
    }

    public Optional<Member> loginValidation(String email, String rawPassword){
        Optional<Member> memberOptional = memberRepository.findMemberByEmail(email);

        if(memberOptional.isPresent()){
            Member loggedMember = memberOptional.get();

            try{
                if(BCrypt.checkpw(rawPassword, loggedMember.getPassword())){
                    return Optional.of(loggedMember);
                } else {
                    return Optional.empty();
                }
            } catch (IllegalArgumentException e) {
                return Optional.empty();
            }
        }
        return Optional.empty();
    }


    public ValidationResult registerNewMember(Member newMember) {

        ValidationResult result = validateMember.validateRegisterMember(newMember);

        // 1. Return early if basic validation fails
        if (result.hasErrors()) {
            return result;
        }

        // 2. Check if email is already taken
        Optional<Member> existingMember = memberRepository.findMemberByEmail(newMember.getEmail());
        if (existingMember.isPresent()) {
            result.addError("Denne e-mail er allerede i brug");
            return result;
        }

        // 3. Hash password and save
        String hashedPassword = BCrypt.hashpw(newMember.getPassword(), BCrypt.gensalt());
        newMember.setPassword(hashedPassword);
        memberRepository.saveProfile(newMember);

        return result; // no errors = success
    }
    //---------------------------------------------------------------------------------------------------




}
