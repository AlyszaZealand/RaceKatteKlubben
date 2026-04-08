package feedback.racekatteklubben.Model.RepoInterfaces;

import feedback.racekatteklubben.Model.Cat;
import java.util.Optional;
import java.util.List;

public interface CatRepositoryImpl {
    void saveCat(Cat cat);
    void updateCatInformation(Cat cat);
    void deleteCat(Cat cat);
    Optional<Cat> findById(int catID);
    Optional<Cat> findByMemberId(int memberId);
    List<Cat> findAllCats();

}
