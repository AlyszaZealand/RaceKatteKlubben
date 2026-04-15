package feedback.racekatteklubben.Model;

import java.util.ArrayList;
import java.util.List;

public class Member{

    private int memberID;
    private String username;
    private String password;
    private String email;
    private String memberDescription;
    private List<Cat> catList;

    public Member(){}

    public Member(int memberID, String username, String password, String email,  String memberDescription) {
        this.memberID = memberID;
        this.username = username;
        validatePassword(password);
        this.password = password;
        validateEmail(email);
        this.email = email;
        this.memberDescription = memberDescription;
        this.catList = new ArrayList<>();
    }

    public void setUsername(String username){
        this.username = username;
    }

    public void setPassword(String password){
        validatePassword(password);
        this.password = password;
    }

    public void setEmail(String email){
        validateEmail(email);
        this.email = email;
    }
    public void setCatList(List<Cat> catList){
        this.catList = catList;
    }
    public int getMemberID(){
        return this.memberID;
    }
    public String getUsername(){
        return this.username;
    }
    public String getPassword(){
        return this.password;
    }
    public String getEmail(){
        return this.email;
    }
    public List<Cat> getCatList(){
        return this.catList;
    }
    public String getMemberDescription(){
        return this.memberDescription;
    }
    public void setMemberDescription(String memberDescription){
        this.memberDescription = memberDescription;
    }
    public void setMemberID(int memberID){
        this.memberID = memberID;
    }
    public void addCat(Cat cat){
        catList.add(cat);
    }
    public void removeCat(Cat cat){
        catList.remove(cat);
    }

    private void validatePassword(String newPassword){
        if(newPassword == null || newPassword.trim().isEmpty()){
            throw new IllegalArgumentException("Du skal indtaste et password");
        }
        else if(newPassword.length() < 6){
            throw new IllegalArgumentException("Dit password skal være mindst 6 tegn langt");
        }
    }

    private void validateEmail(String newEmail){
        if (newEmail == null || !newEmail.contains("@")) {
            throw new IllegalArgumentException("E-mailen skal indeholde et '@' og være gyldig.");
        }
    }
}
