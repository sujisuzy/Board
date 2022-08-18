package com.pgm.board.controller;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.pgm.board.model.Board;
import com.pgm.board.model.Reply;
import com.pgm.board.model.User;
import com.pgm.board.service.BoardService;
import com.pgm.board.service.UserService;

import lombok.extern.java.Log;

@Log
@Controller
@RequestMapping("/files/*")
public class FileUploadController {
	
	@GetMapping("uploadForm")
		public void fileUploadForm() {
			
		}
	
	
	@PostMapping("uploadFormAction")
	public String uploadFormAction(MultipartFile[] uploads, HttpSession session, Model model) {
		String uploadFolder = session.getServletContext().getRealPath("/resources/upload");
		String names ="";
		String today=new SimpleDateFormat("yyMMdd").format(new Date());
		//파일명 : /resource/upload + / + 오늘날짜
		String saveFolder = uploadFolder + File.separator + today;
		List<String> mfile = new ArrayList<String>();
		log.info(saveFolder);
		
		for(MultipartFile mutipartFile:uploads ) {
			String orifile = mutipartFile.getOriginalFilename();
			//파일이름 중복 피하기. : 랜덤 16진수 숫자 사용하기. 
			UUID uuid = UUID.randomUUID();
			String uploadfileName = uuid.toString() + "_" + orifile;
			log.info("================================");
			log.info("origin name : " + mutipartFile.getOriginalFilename());
			log.info("uploadfile name : " +uploadfileName );
			
			//파일 객체 생성 (경로, 파일)
			File saveFile = new File(saveFolder,uploadfileName);
			
			try {
				//업로드한 파일을 saveFolder 경로에 uploadfileName 이름으로 전송.
				mutipartFile.transferTo(saveFile);
				mfile.add(orifile);
			} catch(IllegalStateException | IOException e) {
				e.printStackTrace();
				
			}
			
		}
		model.addAttribute("uploadFiles",mfile);
		return "files/fileResult";
		
	}

}













