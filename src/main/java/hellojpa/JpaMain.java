package hellojpa;

import jakarta.persistence.*;

import java.util.List;

public class JpaMain {

    public static void main(String[] args) {

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");

        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {
            //팀 저장
            Team team = new Team();
            team.setName("TeamA");
            em.persist(team);

            //회원저장
            Member member = new Member();
            member.setUsername("member1");
            member.setTeam(team);
            em.persist(member);

/*
            역방향(주인이 아닌 방향)만 연관관계 설정
            양방향 연관관계 세팅 시
            Team과 Member 둘다 값을 세팅해 줘야 한다.
            메모리에 값이 없으면(1차 캐시에만 값이 있을경우) 값을 읽을 수 X

            *양방향 연관관계 세팅시 양쪽에 다 값을 세팅한다.*
 */
            team.getMembers().add(member);

            //em.flush();
            //em.clear();

            Team findTeam = em.find(Team.class, team.getId()); //1차 캐시
            List<Member> members = findTeam.getMembers();

            System.out.println("===========================");
            for (Member m : members) {
                System.out.println("m.getUsername() = " + m.getUsername());
            }
            System.out.println("===========================");

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }

        emf.close();
    }
}
