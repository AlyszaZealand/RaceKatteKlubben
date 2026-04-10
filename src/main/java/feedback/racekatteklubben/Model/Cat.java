package feedback.racekatteklubben.Model;

import java.time.LocalDate;

public class Cat {
    private int catID;
    private String catRace = "Sibirisk-Kat";
    private String catName;
    private LocalDate catBirthday;
    private String catGender;
    public String catDescription;
    private int memberID;

    public Cat(){}

    public Cat(int catID, String catRace, String catName, LocalDate catBirthday, String catGender, String catDescription,  int memberID){
        this.catID = catID;
        this.catRace = "sibirisk-kat";
        this.catName = catName;
        this.catBirthday = catBirthday;
        this.catGender = catGender;
        this.catDescription = catDescription;
        this.memberID = memberID;
    }


    public int getCatID() {
        return catID;
    }

    public void setCatID(int catID) {
        this.catID = catID;
    }

    public String getCatRace() {
        return catRace;
    }

    public void setCatRace(String catRace) {
        this.catRace = catRace;
    }

    public String getCatName() {
        return catName;
    }

    public void setCatName(String catName) {
        this.catName = catName;
    }

    public LocalDate getCatBirthday() {
        return catBirthday;
    }

    public void setCatBirthday(LocalDate catBirthday) {
        this.catBirthday = catBirthday;
    }

    public String getCatGender() {
        return catGender;
    }

    public void setCatGender(String catGender) {
        this.catGender = catGender;
    }

    public String getCatDescription() {
        return catDescription;
    }

    public void setCatDescription(String catDescription) {
        this.catDescription = catDescription;
    }

    public int getMemberID() {
        return memberID;
    }
    public void setMemberID(int memberID) {
        this.memberID = memberID;
    }

}
