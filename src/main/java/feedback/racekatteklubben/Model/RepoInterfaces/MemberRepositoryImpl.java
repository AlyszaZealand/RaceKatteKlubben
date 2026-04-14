package feedback.racekatteklubben.Model.RepoInterfaces;

import feedback.racekatteklubben.Model.Member;


import java.util.List;
import java.util.Optional;

public interface MemberRepositoryImpl {

    void saveProfile(Member member);
    void deleteProfile(int memberID);
    void updateMemberInformation(Member member);
    List<Member> findAllMembers();
    Optional<Member> findMemberByEmail(String email);
    Optional<Member> findMemberByID(int memberID);
    void updateMemberPassword(Member member);

}
