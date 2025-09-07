package hellojpa;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;

public class JpaMain {

    public static void main(String[] args) {

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");

        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {

            Member member = new Member();
            member.setUsername("user1");
            member.setCreatedBy("kim");
            member.setCreatedDate(LocalDateTime.now());

            em.persist(member);

/*            //팀 저장
            Team team = new Team();
            team.setName("TeamA");
            team.getMembers().add(member); //team 값을 변경하기 위해 member 에서 update를 해야한다. (1:N) [N:1]과 다름
            em.persist(team);
*/


            //team.addMember(member); //값 세팅

/*
            역방향(주인이 아닌 방향)만 연관관계 설정
            양방향 연관관계 세팅 시
            Team과 Member 둘다 값을 세팅해 줘야 한다.
            메모리에 값이 없으면(1차 캐시에만 값이 있을경우) 값을 읽을 수 X

            *양방향 연관관계 세팅시 양쪽에 다 값을 세팅한다.*
 */
            //em.flush();
            //em.clear();

//            Team findTeam = em.find(Team.class, team.getId()); //1차 캐시
//            List<Member> members = findTeam.getMembers();
//
//            System.out.println("===========================");
//            for (Member m : members) {
//                System.out.println("m.getUsername() = " + m.getUsername());
//            }
//            System.out.println("===========================");

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }

        emf.close();
    }
}
