package feedback.racekatteklubben.Model.RepoInterfaces;

import feedback.racekatteklubben.Model.Cat;
import java.util.Optional;
import java.util.List;

public interface CatRepositoryImpl {
    void saveCat(Cat cat);
    void updateCatInformation(Cat cat);
    void deleteCat(int catID);
    Optional<Cat> findCatByID(int catID);
    List<Cat> findCatsByMemberID(int memberId);
    List<Cat> findAllCats();

}
