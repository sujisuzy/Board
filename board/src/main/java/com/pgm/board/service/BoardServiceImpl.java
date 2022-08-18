package com.pgm.board.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.pgm.board.model.Board;
import com.pgm.board.model.Reply;
import com.pgm.board.model.User;
import com.pgm.board.repository.BoardRepository;
import com.pgm.board.repository.ReplyRepository;

@Service
public class BoardServiceImpl implements BoardService{
	@Autowired
	private BoardRepository boardRepository;
	
	@Autowired
	private ReplyRepository replyRepository;
	
	@PersistenceContext
	EntityManager em;

	
	//작업중 검색 조건을 제목
	@Transactional
	@Override
	public Page<Board> search(String word , Pageable pageable) {
		Page<Board> boardEntities = boardRepository.findByTitleContaining(word,pageable);
		return boardEntities;
	}
	
	//작업중 검색 조건을 작성자.
	@Transactional
	@Override
	public Page<Board> searchWriter(String word, Pageable pageable) {
		Page<Board> boardEntities = boardRepository.findByWriterContaining(word,pageable);
		return boardEntities;
	}
	
	//작업중 검색 조건을 내용.
	@Transactional
	@Override
	public Page<Board> searchContent(String word, Pageable pageable) {
		Page<Board> boardEntities = boardRepository.findByContentContaining(word,pageable);
		return boardEntities;
	}
	
	//작업중 검색 조건을 제목(title)작성자(writer) 내용(content)으로 ,
	@Transactional
	@Override
	public Page<Board> searchCWT(String word, Pageable pageable) {
		String title = word;
		String writer = word;
		String content = word;
		
		Page<Board> boardEntities = boardRepository.findByTitleContainingOrWriterContainingOrContentContaining(title, writer,content,pageable);
		return boardEntities;
	}

	
	@Transactional
	@Override
	public void insert(Board board, User user) {
		// TODO Auto-generated method stub
		board.setUser(user);
		boardRepository.save(board);
	}

	@Override
	public List<Board> boardList() {
		// TODO Auto-generated method stub
		return boardRepository.findAll();
	}
	
	@Override
	public Page<Board> findAll(Pageable pageable) {
		// TODO Auto-generated method stub
		return boardRepository.findAll(pageable);
	}

	@Transactional
	@Override
	public Board findById(Long id) {
		// TODO Auto-generated method stub
		Board board=boardRepository.findById(id).get();
		board.setHitcount(board.getHitcount()+1);
		return board;
	}

	@Transactional
	@Override
	public void update(Board board) {
		// TODO Auto-generated method stub
		//boardRepository.save(board);
		// 더티 체킹
		Board b=boardRepository.findById(board.getId()).get();
		b.setTitle(board.getTitle());
		b.setContent(board.getContent());
	}

	@Override
	public void delete(Long id) {
		// TODO Auto-generated method stub
		boardRepository.deleteById(id);
	}

	@Override
	public Long count() {
		// TODO Auto-generated method stub
		return boardRepository.count();
	}

	@Override
	public void insetReply(Reply reply) {
		replyRepository.save(reply);
		// TODO Auto-generated method stub
		//replyRepository.replyInsert(
		//		reply.getWriter(), 
		//		reply.getContent(), 
		//		reply.getBoard().getId(), 
		//		reply.getUser().getId());
	}

	@Override
	public List<Reply> replyList(Long bno) {
		// TODO Auto-generated method stub
		
		return replyRepository.replyList(bno);
	}

	@Override
	public void replyDelete(Long id) {
		// TODO Auto-generated method stub
		replyRepository.deleteById(id);
	}


	
}
