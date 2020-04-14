package ktortest.example.entity;

import ktortest.entity.DiscoverableEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "user")
public class User extends DiscoverableEntity {

    @Column
    private String name;

    public User() {
        super();
    }

    public User(String name) {
        super();
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}