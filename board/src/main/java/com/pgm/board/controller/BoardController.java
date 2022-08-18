package com.pgm.board.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.domain.Pageable;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.pgm.board.model.Board;
import com.pgm.board.model.User;
import com.pgm.board.service.BoardService;
import com.pgm.board.service.UserService;

import lombok.extern.java.Log;

@Log
@Controller
@RequestMapping("/board/*")
public class BoardController {
	
	@Autowired
	private BoardService boardService;
	@Autowired
	private UserService userService;
	
	@GetMapping("register")
	public void insert() {
		
	}
	
	@PostMapping("insert")
	public String insert(Board board) {
		log.info("board.............."+board.getWriter());
		
		User user=userService.findByUsername(board.getWriter());
		log.info("User.............."+user);
		
		boardService.insert(board,user);
		return "redirect:/board/list";
	}
	
	//@GetMapping("list")
	public String list(Model model) {
		model.addAttribute("list", boardService.boardList());
		model.addAttribute("count", boardService.count());
		return "/board/list2";
	}
	
	//전체보기(페이징)
	@GetMapping("list")
	public String listPage(Model model, 
			@PageableDefault(size=5,sort="id", 
			direction=Sort.Direction.DESC) Pageable pageable) {
		
		Page<Board> lists=boardService.findAll(pageable);
		
		long pageSize=pageable.getPageSize();
		long rowNm=boardService.count();
		long totPage=(long)Math.ceil((double)rowNm/pageSize);
		long currPage=pageable.getPageNumber();
		System.out.println("CurrPag=============="+currPage);
		
		long startPage=((currPage)/pageSize)*pageSize;
		long endPage=startPage+pageSize;
		if(endPage>totPage) 
			endPage=totPage;
			
		boolean prev=startPage>0?true:false;
		boolean next=endPage<totPage?true:false;
		
		model.addAttribute("pageSize", pageSize);
		model.addAttribute("startPage", startPage);
		model.addAttribute("endPage", endPage-1);
		model.addAttribute("prev", prev);
		model.addAttribute("next", next);
		model.addAttribute("count", rowNm);
		model.addAttribute("lists", lists);
		model.addAttribute("totPage", totPage);
		model.addAttribute("cp", currPage);
		
		return "board/list";
	}
	
	//검색 작업 중 : 제목을 기반으로 검색.  
		@GetMapping("search")
		public String search(@RequestParam("field") String field,@RequestParam("word") String word,Model model, 
				@PageableDefault(size=5,sort="id", 
				direction=Sort.Direction.DESC) Pageable pageable) {
			
			
			Page<Board> searchListTitle;
			Page<Board> searchListWriter;
			Page<Board> searchListContent;
			Page<Board> searchListCWT; 
			
			if(field.equals("title")) {
				searchListTitle= boardService.search(word, pageable);
				System.out.println("제목(field=title) 조건으로  검색 작업중.");
				
//				Page<Board> lists=boardService.findAll(pageable);
				
				long pageSize=pageable.getPageSize();
				
				//작업중 검색 조건으로 제목 , 검색된 갯수를 확인하는 작업. 
				long rowNm=searchListTitle.getTotalElements();
				
				long totPage=(long)Math.ceil((double)rowNm/pageSize);
				long currPage=pageable.getPageNumber();
				System.out.println("CurrPag=============="+currPage);
				
				long startPage=((currPage)/pageSize)*pageSize;
				long endPage=startPage+pageSize;
				if(endPage>totPage) 
					endPage=totPage;
					
				boolean prev=startPage>0?true:false;
				boolean next=endPage<totPage?true:false;
				
				model.addAttribute("pageSize", pageSize);
				model.addAttribute("startPage", startPage);
				model.addAttribute("endPage", endPage-1);
				
				//작업중 검색 제목 조건으로 , 페이징에서 번호를 클릭하면, 해당 word, field 부분이 풀리는 문제 해결. 
				model.addAttribute("field", field);
				model.addAttribute("word", word);
				
				model.addAttribute("prev", prev);
				model.addAttribute("next", next);
				model.addAttribute("count", rowNm);
				model.addAttribute("lists", searchListTitle);
				model.addAttribute("totPage", totPage);
				model.addAttribute("cp", currPage);
				
				
			} else if(field.equals("writer")) {
				
				//작업중 검색 조건을 작성자로 , 
				searchListWriter= boardService.searchWriter(word, pageable);
				
				System.out.println("작성자(field=writer) 조건으로  검색 작업중.");
				
//				Page<Board> lists=boardService.findAll(pageable);
				
				long pageSize=pageable.getPageSize();
				
				//작업중 검색 조건으로 제목 , 검색된 갯수를 확인하는 작업. 
				long rowNm=searchListWriter.getTotalElements();
				
				long totPage=(long)Math.ceil((double)rowNm/pageSize);
				long currPage=pageable.getPageNumber();
				System.out.println("CurrPag=============="+currPage);
				
				long startPage=((currPage)/pageSize)*pageSize;
				long endPage=startPage+pageSize;
				if(endPage>totPage) 
					endPage=totPage;
					
				boolean prev=startPage>0?true:false;
				boolean next=endPage<totPage?true:false;
				
				model.addAttribute("pageSize", pageSize);
				model.addAttribute("startPage", startPage);
				model.addAttribute("endPage", endPage-1);
				
				//작업중 검색 제목 조건으로 , 페이징에서 번호를 클릭하면, 해당 word, field 부분이 풀리는 문제 해결. 
				model.addAttribute("field", field);
				model.addAttribute("word", word);
				
				model.addAttribute("prev", prev);
				model.addAttribute("next", next);
				model.addAttribute("count", rowNm);
				model.addAttribute("lists", searchListWriter);
				model.addAttribute("totPage", totPage);
				model.addAttribute("cp", currPage);
				
				
			} else if(field.equals("content")) {
				//작업중 검색 조건을 내용으로 , 
				
				searchListContent= boardService.searchContent(word, pageable);
				
				System.out.println("작성자(field=writer) 조건으로  검색 작업중.");
				
//				Page<Board> lists=boardService.findAll(pageable);
				
				long pageSize=pageable.getPageSize();
				
				//작업중 검색 조건으로 제목 , 검색된 갯수를 확인하는 작업. 
				long rowNm=searchListContent.getTotalElements();
				
				long totPage=(long)Math.ceil((double)rowNm/pageSize);
				long currPage=pageable.getPageNumber();
				System.out.println("CurrPag=============="+currPage);
				
				long startPage=((currPage)/pageSize)*pageSize;
				long endPage=startPage+pageSize;
				if(endPage>totPage) 
					endPage=totPage;
					
				boolean prev=startPage>0?true:false;
				boolean next=endPage<totPage?true:false;
				
				model.addAttribute("pageSize", pageSize);
				model.addAttribute("startPage", startPage);
				model.addAttribute("endPage", endPage-1);
				
				//작업중 검색 제목 조건으로 , 페이징에서 번호를 클릭하면, 해당 word, field 부분이 풀리는 문제 해결. 
				model.addAttribute("field", field);
				model.addAttribute("word", word);
				
				model.addAttribute("prev", prev);
				model.addAttribute("next", next);
				model.addAttribute("count", rowNm);
				model.addAttribute("lists", searchListContent);
				model.addAttribute("totPage", totPage);
				model.addAttribute("cp", currPage);
				
			} else if(field.equals("cwt")) {
				//작업중 검색 조건을 제목(title)작성자(writer) 내용(content)으로 , 
				
				searchListCWT= boardService.searchCWT(word, pageable);
				
				System.out.println("작성자(field=writer) 조건으로  검색 작업중.");
				
//				Page<Board> lists=boardService.findAll(pageable);
				
				long pageSize=pageable.getPageSize();
				
				//작업중 검색 조건으로 제목 , 검색된 갯수를 확인하는 작업. 
				long rowNm=searchListCWT.getTotalElements();
				
				long totPage=(long)Math.ceil((double)rowNm/pageSize);
				long currPage=pageable.getPageNumber();
				System.out.println("CurrPag=============="+currPage);
				
				long startPage=((currPage)/pageSize)*pageSize;
				long endPage=startPage+pageSize;
				if(endPage>totPage) 
					endPage=totPage;
					
				boolean prev=startPage>0?true:false;
				boolean next=endPage<totPage?true:false;
				
				model.addAttribute("pageSize", pageSize);
				model.addAttribute("startPage", startPage);
				model.addAttribute("endPage", endPage-1);
				
				//작업중 검색 제목 조건으로 , 페이징에서 번호를 클릭하면, 해당 word, field 부분이 풀리는 문제 해결. 
				model.addAttribute("field", field);
				model.addAttribute("word", word);
				
				model.addAttribute("prev", prev);
				model.addAttribute("next", next);
				model.addAttribute("count", rowNm);
				model.addAttribute("lists", searchListCWT);
				model.addAttribute("totPage", totPage);
				model.addAttribute("cp", currPage);
				
			}
			
			return "board/list";
		
		}
	

	@GetMapping("detail")
	public String detail(@RequestParam("bno") Long id, Model model) {
		model.addAttribute("board", boardService.findById(id));
		return "/board/detail";
	}
	
	/*
	@GetMapping({"detail/{id}","update/{id}"})
	public void view(@PathVariable("id") Long id, Model model) {
		model.addAttribute("board", boardService.findById(id));
	}*/
	
	@GetMapping("update/{id}")
	public String update(@PathVariable("id") Long id, Model model) {
		model.addAttribute("board", boardService.findById(id));
		return "/board/update";
	}
	
	@PostMapping("update")
	public String update(Board board) {
		boardService.update(board);
		return "redirect:/board/list";
	}
	
	@GetMapping("delete/{id}")
	public String delete(@PathVariable Long id) {
		boardService.delete(id);
		return "redirect:/board/list";
	}

}





