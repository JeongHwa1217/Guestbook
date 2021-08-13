package org.zerock.guestbook.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.zerock.guestbook.dto.GuestbookDTO;
import org.zerock.guestbook.dto.PageRequestDTO;
import org.zerock.guestbook.entity.Guestbook;
import org.zerock.guestbook.service.GuestbookService;

@Controller
@RequestMapping("/guestbook")
@Log4j2
@RequiredArgsConstructor  // 서비스를 주입 받도록
public class GuestbookController {

    private final GuestbookService service;
//    @GetMapping({"/","/list"})
//    public String list(){
//        log.info("list.............");
//        return "/guestbook/list";
//    }

    @GetMapping("/list")
    public void list(@ModelAttribute("pageRequestDTO") PageRequestDTO pageRequestDTO,
                     Model model){
        log.info("list.........." + pageRequestDTO);
        //PageResultDTO - 목록data도 dto로 다 바뀌어 있고, 페이지 번호같은 것도..
        model.addAttribute("result", service.getList(pageRequestDTO));
    }

    @GetMapping("/register")
    public void register(){
        log.info("register get...");
    }
    @PostMapping("/register")
    public String registerPost(GuestbookDTO dto, RedirectAttributes redirectAttributes){
        log.info("dto..."+dto);
        // 새로 추가된 엔티티 번호
        Long gno = service.register(dto);
        redirectAttributes.addFlashAttribute("msg",gno);
        return "redirect:/guestbook/list";
    }
}
