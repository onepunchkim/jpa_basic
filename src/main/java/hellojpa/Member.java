package hellojpa;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
public class Member {

    @Id @GeneratedValue
    private Long id;

    @Column(name = "USERNAME")
    private String username;

    @ManyToOne //ManyToOne 은 mappedBy 제공x (주로 연관관계의 주인이기 때문)
    @JoinColumn(name = "TEAM_ID", insertable = false, updatable = false) //읽기전용으로 매핑됨
    private Team team;

    @OneToOne
    @JoinColumn(name = "LOCKER_ID")
    private Locker locker;

    @ManyToMany
    @JoinTable(name = "MEMBER_PRODUCT")
    private List<Product> products = new ArrayList<>();

//    @Column(name = "TEAM_ID")
//    private Long teamId;

    public void setTeam(Team team) {
        this.team = team;
    }

//    @ManyToOne
//    @JoinColumn(name = "TEAM_ID")
//    private Team team;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Team getTeam() {
        return team;
    }

}
