package mission_2.crud.repository;

import mission_2.crud.jpa.entity.BoardEntity;
import mission_2.crud.jpa.entity.UserEntity;
import mission_2.crud.model.BoardDto;
import mission_2.crud.model.UserDto;
import mission_2.crud.service.BoardService;
import mission_2.crud.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;


public class JpaBoardService implements BoardService {
    private final BoardRepository boardRepository;

    public JpaBoardService(BoardRepository boardRepository) {
        this.boardRepository = boardRepository;
    }

    @Override
    public BoardDto create(BoardDto dto) {
        BoardEntity boardEntity=new BoardEntity();
        boardEntity.setName(dto.getName());
        boardEntity=this.boardRepository.save(boardEntity);
        return new BoardDto(
                boardEntity.getId(),
                boardEntity.getName()
        );
    }

    @Override
    public BoardDto read(Long id) {
        Optional<BoardEntity> boardEntityOptional= this.boardRepository.findById(id);
        if(boardEntityOptional.isEmpty()) throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        BoardEntity boardEntity= boardEntityOptional.get();
        return new BoardDto(
                boardEntity.getId(),
                boardEntity.getName()
        );
    }

    @Override
    public Collection<BoardDto> readAll() {
        List<BoardDto> boardDtoList= new ArrayList<>();
        this.boardRepository.findAll().forEach((boardEntity) ->
                boardDtoList.add(new BoardDto(boardEntity.getId(), boardEntity.getName()))
        );
        return boardDtoList;
    }

    @Override
    public boolean update(Long id, BoardDto dto) {
        Optional<BoardEntity> boardEntityOptional= this.boardRepository.findById(id);
        if(boardEntityOptional.isEmpty()) throw  new ResponseStatusException(HttpStatus.NOT_FOUND);
        BoardEntity boardEntity= boardEntityOptional.get();
        boardEntity.setName(dto.getName());

        this.boardRepository.save(boardEntity);
        return true;
    }

    @Override
    public boolean delete(Long id) {
        Optional<BoardEntity> boardEntityOptional= this.boardRepository.findById(id);
        if(boardEntityOptional.isEmpty()) throw  new ResponseStatusException(HttpStatus.NOT_FOUND);
        BoardEntity boardEntity= boardEntityOptional.get();
        this.boardRepository.delete(boardEntity);
        return true;
    }
}

