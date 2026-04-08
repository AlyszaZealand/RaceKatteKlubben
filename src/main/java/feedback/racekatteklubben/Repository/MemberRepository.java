package feedback.racekatteklubben.Repository;

import feedback.racekatteklubben.Model.Member;
import feedback.racekatteklubben.Model.RepoInterfaces.MemberRepositoryImpl;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Repository
public class MemberRepository implements MemberRepositoryImpl {

    private final JdbcTemplate jdbcTemplate;

    public MemberRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final RowMapper<Member> memberRowMapper = (rs, rowNum) -> {
        return new Member(
                rs.getInt("id"),
                rs.getString("username"),
                rs.getString("password"),
                rs.getString("email"),
                rs.getString("memberDescription")
        );
    };

    public List<Member> findAllMembers(){
        String sql = "Select * from Member";

        return jdbcTemplate.query(sql, memberRowMapper);
    }

    public Optional<Member> findMemberByEmail(String email) {
        String sql = "Select * from Member where email = ?";

        try {
            Member member = jdbcTemplate.queryForObject(sql, memberRowMapper, email);
            return Optional.of(member);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    public Optional<Member> findMemberByID(int memberId){

        String sql = "Select * from Member where id=?";

        try {
            Member member = jdbcTemplate.queryForObject(sql, memberRowMapper, memberId);
            return Optional.of(member);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    public void saveProfile(Member member){
        String sql = "Insert into Member (username,password,email,memberdescription) VALUES (?,?,?,?)";

        jdbcTemplate.update(sql,
                member.getUsername(),
                member.getPassword(),
                member.getEmail(),
                member.getMemberDescription()
        );
    }

    public void deleteProfile(int memberID){
        String sql = "DELETE from Member WHERE id =?";

        jdbcTemplate.update(sql, memberID);
    }

    public void updateMemberInformation(Member member){
        String sql = "UPDATE Member SET username = ?, email = ?,memberdescription=? WHERE id=?";
        jdbcTemplate.update(sql,
                member.getUsername(),
                member.getEmail(),
                member.getMemberDescription(),
                member.getMemberID()
        );
    }

}
