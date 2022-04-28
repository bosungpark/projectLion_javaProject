package mission_2.crud.jpa.entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "board")
public class BoardEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @OneToMany(
            targetEntity =PostEntity.class,
            fetch = FetchType.LAZY,
            mappedBy = "board"
    )
    private List<PostEntity> postEntities=new ArrayList<>();

    public BoardEntity() {
    }

    public BoardEntity(Long id, String name, List<PostEntity> postEntities) {
        this.id = id;
        this.name = name;
        this.postEntities = postEntities;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<PostEntity> getPostEntities() {
        return postEntities;
    }

    public void setPostEntities(List<PostEntity> postEntities) {
        this.postEntities = postEntities;
    }
}
