package dev.jamius.weaver.repository;

import dev.jamius.weaver.entity.AccountTeam;
import dev.jamius.weaver.entity.TeamRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AccountTeamRepository extends JpaRepository<AccountTeam, Long> {

    List<AccountTeam> findByAccountUsername(String username);

    Optional<AccountTeam> findByAccountUsernameAndTeamId(String username, Long teamId);

    long countByTeamIdAndRole(Long teamId, TeamRole role);
}
