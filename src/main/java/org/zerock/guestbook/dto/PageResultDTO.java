package org.zerock.guestbook.dto;

import lombok.Data;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

// 뒤에서 앞으로 갈 때
@Data
public class PageResultDTO<DTO, EN> {
    //DTO리스트
    private List<DTO> dtoList;

    //총 페이지 번호
    private int totalPage;

    //현재 페이지 번호
    private int page;
    //목록 사이즈
    private int size;

    //시작 페이지 번호, 끝 페이지 번호
    private int start, end;

    //이전, 다음
    private boolean prev, next;

    //페이지 번호  목록
    private List<Integer> pageList;

    public PageResultDTO(Page<EN> result , Function<EN, DTO> fn){

        dtoList = result.stream().map(fn).collect(Collectors.toList());
        totalPage = result.getTotalPages();
        makePageList(result.getPageable());
    }

    private void makePageList(Pageable pageable){

        this.page = pageable.getPageNumber() + 1; // 0부터 시작하므로 1을 추가
        this.size = pageable.getPageSize();

        //temp end page
        int tempEnd = (int)(Math.ceil(page/10.0)) * 10;
        start = tempEnd - 9;
        prev = start > 1;
        end = totalPage > tempEnd ? tempEnd: totalPage;
        next = totalPage > tempEnd;

        // 페이지 목록 만들어주기기
        // 숫자 처리 안되니까 boxed()를 이용해서 refer형..?으로 처리
       pageList = IntStream.rangeClosed(start, end).boxed().collect(Collectors.toList());

    }

}




