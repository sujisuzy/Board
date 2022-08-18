package com.pgm.board.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.pgm.board.model.Board;

public interface BoardRepository extends JpaRepository<Board, Long> {
	 Page<Board> findByTitleContaining( String word, Pageable pageable);
	 Page<Board> findByWriterContaining( String word, Pageable pageable);
	 Page<Board> findByContentContaining( String word, Pageable pageable);
	 Page<Board> findByTitleContainingOrWriterContainingOrContentContaining( String title,String writer,String content, Pageable pageable);

}
