package feedback.racekatteklubben.Service;

import feedback.racekatteklubben.Model.Cat;
import feedback.racekatteklubben.Model.RepoInterfaces.CatRepositoryImpl;
import feedback.racekatteklubben.Repository.CatRepository;
import feedback.racekatteklubben.Service.Validation.ValidateCat;
import feedback.racekatteklubben.Service.Validation.ValidationResult;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

@Service
public class CatService {

    private final CatRepositoryImpl catRepository;
    private final ValidateCat validateCat;

    public CatService(CatRepository catRepository, ValidateCat validateCat) {
        this.catRepository = catRepository;
        this.validateCat = validateCat;
    }

    public void deleteCat(int catID){
        catRepository.deleteCat(catID);
    }


    public ValidationResult updateCatInformation(Cat cat, MultipartFile image) throws IOException {
        ValidationResult result = validateCat.validateRegisterCat(cat);

        validateImageForCat(cat, image);
        if(result.hasErrors()){
            return result;
        }
        catRepository.updateCatInformation(cat);
        return result;
    }

    public ValidationResult registerNewCat(Cat newCat, MultipartFile image) throws IOException{
        ValidationResult result = validateCat.validateRegisterCat(newCat);

        validateImageForCat(newCat, image);

        if(result.hasErrors()){
            return result;
        }

        catRepository.saveCat(newCat);
        return result;
    }

    public List<Cat> getCatsForEvent(int eventID) {
        return catRepository.findCatsByEventID(eventID);
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


    public void validateImageForCat(Cat newCat,MultipartFile image) throws IOException{
        if (image != null && !image.isEmpty()) {
            // Convert to Base64 and store directly in DB
            String base64 = null;

            base64 = Base64.getEncoder().encodeToString(image.getBytes());

            String mimeType = image.getContentType();
            newCat.setImageName("data:" + mimeType + ";base64," + base64);
        } else {
            // Keep existing image if no new one uploaded
            Cat existing = getCatByID(newCat.getCatID()).orElse(null);
            if (existing != null) {
                newCat.setImageName(existing.getImageName());
            }
        }
    }




}
