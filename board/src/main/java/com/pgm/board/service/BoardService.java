package com.pgm.board.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.pgm.board.model.Board;
import com.pgm.board.model.Reply;
import com.pgm.board.model.User;

public interface BoardService {
	
	//검색 작업 중 : 제목을 기반으로 검색.  
	public Page<Board> search(String word,Pageable pageable);
	
	//검색 작업 중 : 작성자를 기반으로 검색.  
		public Page<Board> searchWriter(String word,Pageable pageable);
		
		//검색 작업 중 : content 내용으로 검색.  
		public Page<Board> searchContent(String word,Pageable pageable);
		
		//작업중 검색 조건을 제목(title)작성자(writer) 내용(content)으로 , 
				public Page<Board> searchCWT(String word,Pageable pageable);
		
		
	
	public void insert(Board board,User user);
	
	public List<Board> boardList();
	
	public Board findById(Long id);
	
	public void update(Board board);
	public void delete(Long id);

	public Long count();
	
	public void insetReply(Reply reply);
	
	public List<Reply> replyList(Long bno);
	
	public void replyDelete(Long id);

	public Page<Board> findAll(Pageable pageable);	

}
