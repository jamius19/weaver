package dev.jamius.weaver.service;

import dev.jamius.weaver.dto.board.BoardResponse;
import dev.jamius.weaver.dto.board.CreateBoardRequest;
import dev.jamius.weaver.dto.board.EditBoardRequest;
import dev.jamius.weaver.entity.AccountTeam;
import dev.jamius.weaver.entity.Board;
import dev.jamius.weaver.entity.Team;
import dev.jamius.weaver.entity.TeamRole;
import dev.jamius.weaver.repository.AccountTeamRepository;
import dev.jamius.weaver.repository.BoardRepository;
import dev.jamius.weaver.repository.TeamRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;

    private final AccountTeamRepository accountTeamRepository;

    @Transactional
    public BoardResponse createBoard(String username, Long teamId, CreateBoardRequest request) {
        AccountTeam accountTeam = accountTeamRepository.findByAccountUsernameAndTeamId(username, teamId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Team not found or access denied"));

        if (accountTeam.getRole() != TeamRole.ADMIN) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Only admins can create boards");
        }

        Team team = accountTeam.getTeam();

        Board board = Board.builder()
                .title(request.title())
                .description(request.description())
                .team(team)
                .build();

        board = boardRepository.save(board);

        return BoardResponse.fromEntity(board);
    }

    @Transactional(readOnly = true)
    public List<BoardResponse> getBoards(String username, Long teamId) {
        // First verify the user is a member of the team
        AccountTeam accountTeam = accountTeamRepository.findByAccountUsernameAndTeamId(username, teamId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Team not found or access denied"));

        List<Board> boards = boardRepository.findByTeamId(teamId);
        return boards.stream()
                .map(BoardResponse::fromEntity)
                .collect(Collectors.toList());
    }

    @Transactional
    public BoardResponse editBoard(String username, Long boardId, EditBoardRequest request) {
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Board not found"));

        AccountTeam accountTeam = accountTeamRepository.findByAccountUsernameAndTeamId(username, board.getTeam().getId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.FORBIDDEN, "Access denied"));

        if (accountTeam.getRole() != TeamRole.ADMIN) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Only admins can edit boards");
        }

        board.setTitle(request.title());
        board.setDescription(request.description());

        board = boardRepository.save(board);

        return BoardResponse.fromEntity(board);
    }

    @Transactional
    public void deleteBoard(String username, Long boardId) {
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Board not found"));

        AccountTeam accountTeam = accountTeamRepository.findByAccountUsernameAndTeamId(username, board.getTeam().getId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.FORBIDDEN, "Access denied"));

        if (accountTeam.getRole() != TeamRole.ADMIN) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Only admins can delete boards");
        }

        boardRepository.delete(board);
    }
}
