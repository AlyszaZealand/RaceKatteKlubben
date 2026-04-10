package feedback.racekatteklubben.Service;

import feedback.racekatteklubben.Model.Cat;
import feedback.racekatteklubben.Repository.CatRepository;
import feedback.racekatteklubben.Service.Validation.ValidateCat;
import feedback.racekatteklubben.Service.Validation.ValidationResult;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CatService {

    private final CatRepository catRepository;
    private final ValidateCat validateCat;

    public CatService(CatRepository catRepository, ValidateCat validateCat) {
        this.catRepository = catRepository;
        this.validateCat = validateCat;
    }

    public void deleteCat(int catID){
        catRepository.deleteCat(catID);
    }

    public ValidationResult updateCatInformation(Cat cat){
        ValidationResult result = validateCat.validateRegisterCat(cat);
        if(result.hasErrors()){
            return result;
        }
        catRepository.updateCatInformation(cat);
        return result;
    }

    public ValidationResult registerNewCat(Cat newCat){
        ValidationResult result = validateCat.validateRegisterCat(newCat);

        if(result.hasErrors()){
            return result;
        }

        catRepository.saveCat(newCat);
        return result;
    }

    public List<Cat> getCatsForMember(int memberID){
        return catRepository.findCatsByMemberID(memberID);
    }

    public Optional<Cat> getCatByID(int catID){
        return catRepository.findCatByID(catID);
    }

    public List<Cat> getAllCats(){
        return catRepository.findAllCats();
    }






}
