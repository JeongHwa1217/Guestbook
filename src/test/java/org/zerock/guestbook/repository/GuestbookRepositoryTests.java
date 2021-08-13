package org.zerock.guestbook.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.zerock.guestbook.entity.Guestbook;

import java.util.Optional;
import java.util.stream.IntStream;

@SpringBootTest
public class GuestbookRepositoryTests {
    @Autowired
    private GuestbookRepository guestbookRepository;

    @Test
    public void insertDummies(){

        IntStream.rangeClosed(1,300).forEach(i -> {

            Guestbook guestbook = Guestbook.builder()
                    .title("Title...." + i)
                    .content("Content..." +i)
                    .writer("user" + (i % 10))
                    .build();
            System.out.println(guestbookRepository.save(guestbook));
        });
    }

    @Test
    public void updateTest() {

        Optional<Guestbook> result = guestbookRepository.findById(300L); //존재하는 번호로 테스트

        if(result.isPresent()){

            Guestbook guestbook = result.get();

            guestbook.changeTitle("Changed Title....");
            guestbook.changeContent("Changed Content...");

            guestbookRepository.save(guestbook);
        }
    }

    @Test
    public void testQuery1(){
        //목록 type을 조회할 것이기 때문에 Pageable type 사용
        Pageable pageable = PageRequest.of(0,10, Sort.by("gno").descending());

        // findAll -> JpaRepository  _. Querydsl -> QuerydslPredicateExecutor
        // Predicate java와 querydsl에 둘 다 존재해서 import할 때 조심
        QGuestbook qGuestbook = QGuestbook.guestbook;
        String keyword = "1";

        BooleanBuilder booleanBuilder = new BooleanBuilder();

        // title like %1%
        BooleanExpression expression = qGuestbook.title.contains(keyword);

        booleanBuilder.and(expression);

        //실행
        // predicate에 해당하는 부분이 boolenaBuilder
        Page<Guestbook> result = guestbookRepository.findAll(booleanBuilder, pageable);

        result.forEach(guestbook -> {
            System.out.println(guestbook);
        });
    }

    @Test
    public void testQuery2(){
        Pageable pageable = PageRequest.of(0,10, Sort.by("gno").descending());

        QGuestbook qGuestbook = QGuestbook.guestbook;
        String keyword = "1";

        BooleanBuilder booleanBuilder = new BooleanBuilder();

        // title like %1% or content like %1%
        BooleanExpression expression = qGuestbook.title.contains(keyword);
        BooleanExpression exAll = expression.or(qGuestbook.content.contains(keyword));

        booleanBuilder.and(exAll);
        booleanBuilder.and(qGuestbook.gno.gt(0L));

        Page<Guestbook> result = guestbookRepository.findAll(booleanBuilder, pageable);

        result.forEach(guestbook -> {
            System.out.println(guestbook);
        });
    }

}
