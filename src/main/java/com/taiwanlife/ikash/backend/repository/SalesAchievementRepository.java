package com.taiwanlife.ikash.backend.repository;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.taiwanlife.ikash.backend.entity.acs.ACT_FYC_ACCEPTFEATS;
import com.taiwanlife.ikash.backend.entity.acs.ACT_FYC_ACCEPTFEATSKEY;

public interface SalesAchievementRepository extends JpaRepository<ACT_FYC_ACCEPTFEATS, ACT_FYC_ACCEPTFEATSKEY>{
	@Query(value = "SELECT * FROM ACT_FYC_ACCEPTFEATS", nativeQuery = true)
	List<ACT_FYC_ACCEPTFEATS> findAll();

	@Query(value = "SELECT * FROM ACT_FYC_ACCEPTFEATS t WHERE t.USER_CODE=?1", nativeQuery = true)
	List<ACT_FYC_ACCEPTFEATS> findByUSER_CODE(String USER_CODE);

	@Query(value = "SELECT * FROM ACT_FYC_ACCEPTFEATS t WHERE t.AGENT_NAME LIKE %?1%", nativeQuery = true)
	List<ACT_FYC_ACCEPTFEATS> findByAGENT_NAMELike(String AGENT_NAME);
	
	@Query(value= "SELECT  AFA.APPL_RECE_DATE, AFA.DIRECTOR_ID,   AFA.DIRECTOR_CODE\r\n"
			+ "         ,AFA.USER_CODE, AFA.FYC, AFA.AVG  , AFA.RANK , AFA.LEVEL_COUNT \r\n"
			+ "         ,AFA.DIRECTOR_YN   ,AFA.AGENT_NAME ,AFA.CREATE_DT ,AFA.CREATE_BY\r\n"
			+ "         ,AFA.MODIFY_DT ,AFA.MODIFY_BY ,AFA.COMM_LINE_CODE\r\n"
			+ "         ,AFA.COMMLINE_LEVEL_COUNT,ATFA.TEAM_FYC\r\n"
			+ "FROM ACT_FYC_ACCEPTFEATS AFA  LEFT JOIN ACT_TEAM_FYC_ACCEPTFEATS ATFA ON AFA.DIRECTOR_CODE=ATFA.DIRECTOR_CODE AND ATFA.APPL_RECE_DATE = AFA.APPL_RECE_DATE AND ATFA.APPL_RECE_DATE <= ?2\r\n"
			+ "WHERE AFA.USER_CODE = ?1 AND AFA.APPL_RECE_DATE <= ?2 AND AFA.DIRECTOR_YN='Y' ORDER BY ATFA.APPL_RECE_DATE ", nativeQuery = true)
	List<ACT_FYC_ACCEPTFEATS> findMonthlyKYC(String USER_CODE,  Date RECE_DATE );
	
	
	@Query(value= "SELECT  AFA.APPL_RECE_DATE, AFA.DIRECTOR_ID,   AFA.DIRECTOR_CODE\r\n"
			+ "         ,AFA.USER_CODE, AFA.FYC, AFA.AVG  , AFA.RANK , AFA.LEVEL_COUNT \r\n"
			+ "         ,AFA.DIRECTOR_YN   ,AFA.AGENT_NAME ,AFA.CREATE_DT ,AFA.CREATE_BY\r\n"
			+ "         ,AFA.MODIFY_DT ,AFA.MODIFY_BY ,AFA.COMM_LINE_CODE\r\n"
			+ "         ,AFA.COMMLINE_LEVEL_COUNT,ATFA.TEAM_FYC\r\n"
			+ "			FROM ACT_FYC_ACCEPTFEATS AFA  LEFT JOIN ACT_TEAM_FYC_ACCEPTFEATS ATFA ON AFA.DIRECTOR_CODE=ATFA.DIRECTOR_CODE AND ATFA.APPL_RECE_DATE = AFA.APPL_RECE_DATE ", nativeQuery = true)
	List<Map<String, Object>>  findJoinKYC();

}
