package dev.jamius.weaver.controller;

import dev.jamius.weaver.dto.ApiResponse;
import dev.jamius.weaver.dto.team.CreateTeamRequest;
import dev.jamius.weaver.dto.team.EditTeamRequest;
import dev.jamius.weaver.dto.team.TeamResponse;
import dev.jamius.weaver.service.TeamService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/teams")
@RequiredArgsConstructor
public class TeamController {

    private final TeamService teamService;

    @PostMapping
    public ResponseEntity<ApiResponse<?>> createTeam(
            Authentication authentication,
            @Valid @RequestBody CreateTeamRequest request) {

        String username = authentication.getName();
        TeamResponse team = teamService.createTeam(username, request);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponse<>(true, "Team created successfully", Map.of("team", team)));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<?>> getTeams(Authentication authentication) {
        String username = authentication.getName();
        List<TeamResponse> teams = teamService.getTeams(username);

        return ResponseEntity.ok(new ApiResponse<>(true, "Teams retrieved successfully", Map.of("teams", teams)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<?>> editTeam(
            Authentication authentication,
            @PathVariable Long id,
            @Valid @RequestBody EditTeamRequest request) {

        String username = authentication.getName();
        TeamResponse team = teamService.editTeam(username, id, request);

        return ResponseEntity.ok(new ApiResponse<>(true, "Team updated successfully", Map.of("team", team)));
    }

    @PostMapping("/{id}/leave")
    public ResponseEntity<ApiResponse<?>> leaveTeam(
            Authentication authentication,
            @PathVariable Long id) {

        String requesterUsername = authentication.getName();
        teamService.leaveTeam(requesterUsername, id);

        return ResponseEntity.ok(new ApiResponse<>(true, "Left team successfully", null));
    }

    @DeleteMapping("/{id}/members/{usernameToRemove}")
    public ResponseEntity<ApiResponse<?>> removeAccountFromTeam(
            Authentication authentication,
            @PathVariable Long id,
            @PathVariable String usernameToRemove) {

        String requesterUsername = authentication.getName();
        teamService.removeAccountFromTeam(requesterUsername, id, usernameToRemove);

        return ResponseEntity.ok(new ApiResponse<>(true, "Member removed successfully", null));
    }
}
