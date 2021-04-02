package com.spring.mvc.springweb.board.controller;

import com.spring.mvc.springweb.board.domain.Board;
import com.spring.mvc.springweb.board.domain.ModifyBoard;
import com.spring.mvc.springweb.board.repository.BoardRepository;
import com.spring.mvc.springweb.board.service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class BoardController {

    private final BoardService boardService;
    private final BoardRepository boardRepository;

    @Autowired
    public BoardController(BoardService boardService, @Qualifier("TemplateBoardRepo") BoardRepository boardRepository) {
        this.boardService = boardService;
        this.boardRepository = boardRepository;
    }

    //글 작성 화면 요청
    @GetMapping("/board/write")
    public String write() {
        return "board/write";
    }
    //글 작성 처리 요청
    @PostMapping("/board/write")
    public String write(Board article) {
        boardRepository.insertArticle(article);
        return "redirect:/board/list";
    }
    //글 목록 요청
    @GetMapping("/board/list")
    public String list(Model model) {
        model.addAttribute("articles", boardRepository.getArticles());
        return "board/list";
    }

    //글 삭제 요청
    @GetMapping("/board/delete")
    public String delete(int boardNo) {
        boardRepository.deleteArticle(boardNo);
        return "redirect:/board/list";
    }

    //글 상세보기 요청
    @GetMapping("/board/content")
    public String content(int boardNo, Model model) {
        model.addAttribute("article", boardRepository.getContent(boardNo));
        return "board/content";
    }

    //글 수정하기 화면요청
    @GetMapping("/board/modify")
    public String modify(int boardNo, Model model) {
        model.addAttribute("article", boardRepository.getContent(boardNo));
        return "board/modify";
    }

    //글 수정 처리요청
    @PostMapping("/board/modify")
    public String modify(ModifyBoard modArticle) {
        Board board = boardRepository.getContent(modArticle.getBoardNo());
        board.setWriter(modArticle.getWriter());
        board.setTitle(modArticle.getTitle());
        board.setContent(modArticle.getContent());
        boardRepository.modifyArticle(board);
        return "redirect:/board/content?boardNo="+modArticle.getBoardNo();
    }
}