package mission_2.crud.repository;

import mission_2.crud.jpa.entity.BoardEntity;
import mission_2.crud.jpa.entity.PostEntity;
import mission_2.crud.jpa.entity.UserEntity;
import mission_2.crud.model.PostDto;
import mission_2.crud.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
public class JpaPostService implements PostService{
    private final PostRepository postRepository;
    private final BoardRepository boardRepository;
    private final UserRepository userRepository;

    public JpaPostService(
            @Autowired PostRepository postRepository,
            @Autowired BoardRepository boardRepository,
            @Autowired UserRepository userRepository
    ){
        this.postRepository=postRepository;
        this.boardRepository=boardRepository;
        this.userRepository=userRepository;
    }

    @Override
    public PostDto create(Long boardId, PostDto dto) {
        if (!this.boardRepository.existsById(boardId)) throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        if (!this.userRepository.existsById(dto.getUserId())) throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        BoardEntity boardEntity=this.boardRepository.findById(boardId).get();
        UserEntity userEntity= this.userRepository.findById(dto.getUserId()).get();
        PostEntity postEntity=new PostEntity();

        postEntity.setTitle(dto.getTitle());
        postEntity.setContent(dto.getContent());
        postEntity.setWriter(userEntity);
        postEntity.setBoard(boardEntity);
        postEntity=this.postRepository.save(postEntity);

        return new PostDto(
                postEntity.getId(),
                postEntity.getTitle(),
                postEntity.getContent(),
                postEntity.getWriter().getId()
        );
    }

    @Override
    public PostDto read(Long boardId, Long postId) {
        if (!this.postRepository.existsById(postId)) throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        PostEntity postEntity=this.postRepository.findById(postId).get();
        if (!postEntity.getBoard().getId().equals(boardId))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        return new PostDto(
                postEntity.getId(),
                postEntity.getTitle(),
                postEntity.getContent(),
                postEntity.getWriter().getId()
        );
    }

    @Override
    public Collection<PostDto> readAll(Long boardId) {
        Optional<BoardEntity> boardEntityOptional=this.boardRepository.findById(boardId);
        if (boardEntityOptional.isEmpty()) throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        BoardEntity boardEntity=boardEntityOptional.get();
        List<PostDto> postDtoList= new ArrayList<>();
        boardEntity.getPostEntities().forEach(postEntity -> postDtoList.add(new PostDto(
                postEntity.getId(),
                postEntity.getTitle(),
                postEntity.getContent(),
                postEntity.getWriter().getId()
        )));
        return postDtoList;
    }

    @Override
    public boolean update(Long boardId, Long postId, PostDto dto) {
        Optional<BoardEntity> boardEntityOptional= this.boardRepository.findById(postId);
        if(boardEntityOptional.isEmpty()) throw  new ResponseStatusException(HttpStatus.NOT_FOUND);
        BoardEntity boardEntity= boardEntityOptional.get();
        boardEntity.setName(dto.getTitle());

        this.boardRepository.save(boardEntity);
        return true;
    }

    @Override
    public boolean delete(Long boardId, Long postId, String password) {
        Optional<BoardEntity> boardEntityOptional= this.boardRepository.findById(boardId);
        if(boardEntityOptional.isEmpty()) throw  new ResponseStatusException(HttpStatus.NOT_FOUND);
        BoardEntity boardEntity= boardEntityOptional.get();
        this.boardRepository.delete(boardEntity);
        return true;
    }
}
