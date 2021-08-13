package org.zerock.guestbook.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;


@Builder
@AllArgsConstructor
@Data

//// 페이지 번호, 검색 조건 등의 처리
// 앞에서 뒤로 올 때
// Controller -> Service
public class PageRequestDTO {

    private int page;
    private int size;

    public PageRequestDTO(){
        this.page = 1;
        this.size = 10;
    }

    public Pageable getPageable(Sort sort){
        // FindAll 할 때 쓰기 위해 PageRequest로 변환해줌
        return PageRequest.of(page -1, size, sort);

    }
}

