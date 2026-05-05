package dev.jamius.weaver.dto.board;

import dev.jamius.weaver.entity.Board;

public record BoardResponse(
        Long id,
        String title,
        String description,
        Long teamId
) {
    public static BoardResponse fromEntity(Board board) {
        return new BoardResponse(
                board.getId(),
                board.getTitle(),
                board.getDescription(),
                board.getTeam().getId()
        );
    }
}
