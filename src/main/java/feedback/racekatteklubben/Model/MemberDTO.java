package feedback.racekatteklubben.Model;

public class MemberDTO {

    private int memberID;
    private String username;
    private String email;


    public MemberDTO(int memberID, String username, String email) {
        this.memberID = memberID;
        this.username = username;
        this.email = email;
    }

    public int getMemberID() {
        return memberID;
    }
    public void setMemberID(int memberID) {
        this.memberID = memberID;
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
}
