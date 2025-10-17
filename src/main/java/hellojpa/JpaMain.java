package hellojpa;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

public class JpaMain {

    public static void main(String[] args) {

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");

        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {

            Member member = new Member();
            member.setUsername("member1");
            member.setHomeAddress(new Address("homeCity", "street", "1000"));

            member.getFavoriteFoods().add("치킨");
            member.getFavoriteFoods().add("피자");
            member.getFavoriteFoods().add("족발");

            member.getAddressHistory().add(new AddressEntity("old1", "street", "1000")); //많이 쓰는 방법 (Entity)
            member.getAddressHistory().add(new AddressEntity("old2", "street", "1000"));

            em.persist(member);

            em.flush();
            em.clear();

            System.out.println("=========== start =============");
            Member findMember = em.find(Member.class, member.getId());

            //homeCity -> newCity
            //findMember.getHomeAddress().setCity("newCity");

            Address a = findMember.getHomeAddress();
            findMember.setHomeAddress(new Address("newCity", a.getStreet(), a.getZipcode()));

            //치킨 -> 한식 // 값 타입 컬렉션은 값 수정 대신 값을 삭제하고 새로 만든다.
            findMember.getFavoriteFoods().remove("치킨");
            findMember.getFavoriteFoods().add("한식");

            //equnals()와 Hashcode가 작성되어 있지않으면 실행X
//            findMember.getAddressHistory().remove(new Address("old1", "street", "1000"));
//            findMember.getAddressHistory().add(new Address("newCity1", "street", "1000"));


            //SQL: select * from Member
            //SQL: select * from Team where TEAM_ID = xxx
            //LAZY로 설정한 후 fetch join을 사용한다.

/*
            역방향(주인이 아닌 방향)만 연관관계 설정
            양방향 연관관계 세팅 시
            Team과 Member 둘다 값을 세팅해 줘야 한다.
            메모리에 값이 없으면(1차 캐시에만 값이 있을경우) 값을 읽을 수 X

            *양방향 연관관계 세팅시 양쪽에 다 값을 세팅한다.*
 */

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            e.printStackTrace();
        } finally {
            em.close();
        }

        emf.close();
    }

}
