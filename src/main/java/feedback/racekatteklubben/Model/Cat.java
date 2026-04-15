package feedback.racekatteklubben.Model;

import java.time.LocalDate;

import java.time.Period;

public class Cat {
    private int catID;
    private String catRace = "Sibirisk-Kat";
    private String catName;
    private LocalDate catBirthday;
    private String catGender;
    private String catDescription;
    private int memberID;
    private String imageName;


    public Cat(){}


    public Cat(int catID, String catRace, String catName, LocalDate catBirthday, String catGender, String catDescription,  int memberID, String imageName) {
        this.catID = catID;
        this.catRace = "sibirisk-kat";
        setCatName(catName);
        setCatBirthday(catBirthday);
        setCatGender(catGender);
        this.catDescription = catDescription;
        this.memberID = memberID;
        this.imageName = imageName;
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
        if (catName == null || catName.trim().isEmpty()) {
            throw new IllegalArgumentException("Kattens navn må ikke være tomt.");
        }
        this.catName = catName;
    }

    public LocalDate getCatBirthday() {
        return catBirthday;
    }

    public void setCatBirthday(LocalDate catBirthday) {
        if (catBirthday != null && catBirthday.isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("En kat kan ikke være født i fremtiden.");
        }
        this.catBirthday = catBirthday;
    }

    public String getCatGender() {
        return catGender;
    }

    public void setCatGender(String catGender) {
        if (catGender == null || (!catGender.trim().equals("Han") && !catGender.trim().equals("Hun"))) {
            throw new IllegalArgumentException("Kattens køn skal være enten 'Han' eller 'Hun'.");
        }
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

    public String getImageName() {
        return this.imageName;
    }
    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    // calculate age, sent from domain to html
    public int calculateCatAge(){
       return Period.between(getCatBirthday(),LocalDate.now()).getYears();
    }



}
