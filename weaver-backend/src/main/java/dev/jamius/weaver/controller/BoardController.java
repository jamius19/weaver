package dev.jamius.weaver.controller;

import dev.jamius.weaver.dto.ApiResponse;
import dev.jamius.weaver.dto.board.BoardResponse;
import dev.jamius.weaver.dto.board.CreateBoardRequest;
import dev.jamius.weaver.dto.board.EditBoardRequest;
import dev.jamius.weaver.service.BoardService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    @PostMapping("/teams/{teamId}/boards")
    public ResponseEntity<ApiResponse<?>> createBoard(
            Authentication authentication,
            @PathVariable Long teamId,
            @Valid @RequestBody CreateBoardRequest request) {

        String username = authentication.getName();
        BoardResponse board = boardService.createBoard(username, teamId, request);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponse<>(true, "Board created successfully", Map.of("board", board)));
    }

    @GetMapping("/teams/{teamId}/boards")
    public ResponseEntity<ApiResponse<?>> getBoards(
            Authentication authentication,
            @PathVariable Long teamId) {

        String username = authentication.getName();
        List<BoardResponse> boards = boardService.getBoards(username, teamId);

        return ResponseEntity.ok(new ApiResponse<>(true, "Boards retrieved successfully", Map.of("boards", boards)));
    }

    @PutMapping("/boards/{id}")
    public ResponseEntity<ApiResponse<?>> editBoard(
            Authentication authentication,
            @PathVariable Long id,
            @Valid @RequestBody EditBoardRequest request) {

        String username = authentication.getName();
        BoardResponse board = boardService.editBoard(username, id, request);

        return ResponseEntity.ok(new ApiResponse<>(true, "Board updated successfully", Map.of("board", board)));
    }

    @DeleteMapping("/boards/{id}")
    public ResponseEntity<ApiResponse<?>> deleteBoard(
            Authentication authentication,
            @PathVariable Long id) {

        String username = authentication.getName();
        boardService.deleteBoard(username, id);

        return ResponseEntity.ok(new ApiResponse<>(true, "Board deleted successfully", null));
    }
}
