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
        validateCatBirthday(catBirthday);
        validateCatGender(catGender);
        validateCatName(catName);
        this.catID = catID;
        this.catRace = "sibirisk-kat";
        this.catName = catName;
        this.catBirthday = catBirthday;
        this.catGender = catGender;
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
        validateCatName(catName);
        this.catName = catName;
    }

    public LocalDate getCatBirthday() {
        return catBirthday;
    }

    public void setCatBirthday(LocalDate catBirthday) {
        validateCatBirthday(catBirthday);
        this.catBirthday = catBirthday;
    }


    public String getCatGender() {
        return catGender;
    }

    public void setCatGender(String catGender) {
        validateCatGender(catGender);
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


    private void validateCatGender(String newCatGender){
        if (newCatGender == null || (!newCatGender.trim().equals("Han") && !newCatGender.trim().equals("Hun"))) {
            throw new IllegalArgumentException("Kattens køn skal være enten 'Han' eller 'Hun'.");
        }
    }

    private void validateCatName(String newCatName){
        if (newCatName == null || newCatName.trim().isEmpty()) {
            throw new IllegalArgumentException("Kattens navn må ikke være tomt.");
        }
    }
    private void validateCatBirthday(LocalDate newCatBirthday){
        if (newCatBirthday != null && newCatBirthday.isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("En kat kan ikke være født i fremtiden.");
        }
    }
}
