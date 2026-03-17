package kr.ac.kopo.wodyd.bookmarket.repository;


import kr.ac.kopo.wodyd.bookmarket.domain.Book;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Repository
public class BookRepositoryImpl implements BookRepository {
    private List<Book> listofBooks = new ArrayList<Book>();
    
    public BookRepositoryImpl() {
        Book book1 = new Book();
        book1.setBookId("isbn1001");
        book1.setName("스프링 부트 완전정복");
        book1.setDescription("스프링 부트는 스프링을 기반으로 쉽고 빠르게 웹 애플리케이션을 개발할 수 있는 도구이다. 이 책에서는 스프링 부트의 기본 개념을 쉽게 이해하고 다양한 실습 예제로 빠르게 익힐 수 있다. " +
                "그리고 단계별 실습을 따라 하다 보면 도서 쇼핑몰 구축 프로젝트를 완성할 수 있다. 개념-실습-프로젝트의 3단계 학습으로 스프링 부트를 제대로 익힌다면 개발 시간을 단축하고 생산성을 높일 수 있는 개발자로 성장할 수 있다.");
        book1.setPublisher("길벗캠퍼스");
        book1.setCategory("IT전문서");
        book1.setAuthor("송미영");
        book1.setUnitPrice(new BigDecimal(35000));
        book1.setReleaseDate("2024/12/31");

        Book book2 = new Book();
        book2.setBookId("isbn1002");
        book2.setName("전문가를 위한 리액트");
        book2.setDescription("리액트는 웹 개발자에게 새로운 가능성을 열어 주는 강력한 도구이다. 이 책은 단순히 리액트 사용을 넘어, 내부 구조와 작동 방식을 완벽히 분석해 최적화된 코드를 작성하는 방법을 설명한다. JSX 문법, " +
                "가상 DOM, 재조정, 고급 최적화 기법 등 리액트 핵심 개념을 깊이 파헤친다. 리액트 애플리케이션을 대규모로 구축하고 유지하는 " +
                        "방법과 모바일이나 웹 이외의 다른 플랫폼에서도 활용할 수 있는 실용적인 조언도 소개한다. 리액트의 세부적인 작동 원리를 이해하고 개발 역량을 높여보자. 리액트 개발자로서 한 단계 더 도약할 차례이다.");
        book2.setPublisher("한빛미디어");
        book2.setCategory("IT교재");
        book2.setAuthor("테자스 쿠마르");
        book2.setUnitPrice(new BigDecimal(30600));
        book2.setReleaseDate("2024/12/23");

        Book book3 = new Book();
        book3.setBookId("isbn1003");
        book3.setName("요즘 우아한 AI 개발");
        book3.setDescription("우아한형제들의 생생한 개발 이야기를 엮은 『요즘 우아한 개발』이 AI 중심 개발 사례와 실무 노하우를 가득 담아 『요즘 우아한 AI 개발』로 돌아왔다! IT 서비스 개발에서 AI 활용의 중요성은 점점 커지고 있다. " +
                        "매일 새로워지는 AI 기술을 따라가는 것만큼 이를 실무에 효과적으로 적용하는 역량이 기업과 개인의 경쟁력을 좌우한다. 이 책은 우아한형제들이 실제 프로젝트에 적용한 AI 기술과 개발 경험을 담아냈다. AI 메뉴 추천 시스템부터 데이터 검색 및 분석 자동화, " +
                        "로봇 ML 모델 경량화와 MLOps 구축까지, 최신 AI 기술을 활용한 실무 사례를 생생하게 다룬다. AI를 도입하고자 하는 기획자와 개발자라면, 실제 서비스에 AI 기술을 어떻게 적용하는지 궁금하다면 우아한형제들이 걸어온 AI 개발의 여정을 통해 그 해답을 찾아보자."
                );
        book3.setPublisher("골든래빗");
        book3.setCategory("IT교재");
        book3.setAuthor("우아한형제들");
        book3.setUnitPrice(new BigDecimal(21600));
        book3.setReleaseDate("2025/04/01");
        
        listofBooks.add(book1);
        listofBooks.add(book2);
        listofBooks.add(book3);
    }


    @Override
    public List<Book> getAllBookList() {
        return listofBooks;
    }
}
