package org.zerock.guestbook.service;

import org.zerock.guestbook.dto.GuestbookDTO;
import org.zerock.guestbook.dto.PageRequestDTO;
import org.zerock.guestbook.dto.PageResultDTO;
import org.zerock.guestbook.entity.Guestbook;

public interface GuestbookService {

    // 고객의 요구사항 반영
    // register 기능, getList 기능
    Long register(GuestbookDTO dto);
    PageResultDTO<GuestbookDTO, Guestbook> getList(PageRequestDTO requestDTO);

    // default 키워드를 이용하여, 별도의 추상 method 없이 직접 method 구현해서
    // DTO로 들어오면 Entity로 변환해주는
    default Guestbook dtoToEntity(GuestbookDTO dto) {

        Guestbook entity = Guestbook.builder()
                .gno(dto.getGno())
                .title(dto.getTitle())
                .content(dto.getContent())
                .writer(dto.getWriter())
                .build();
        return entity;
    }

    // Entity로 들어오면 ETO로 변환해주는
    default GuestbookDTO entityToDto(Guestbook entity){

        GuestbookDTO dto  = GuestbookDTO.builder()
                .gno(entity.getGno())
                .title(entity.getTitle())
                .content(entity.getContent())
                .writer(entity.getWriter())
                .regDate(entity.getRegDate())
                .modDate(entity.getModDate())
                .build();

        return dto;
    }


}
