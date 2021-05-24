package com.care.root.board.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.care.root.board.dto.BoardDTO;
import com.care.root.member.session_name.MemberSessionName;
import com.care.root.mybatis.board.BoardMapper;

@Service
public class BoardServiceImpl implements BoardService{
	@Autowired BoardMapper mapper;
	public void selectAllBoardList(Model model) {
		model.addAttribute("boardList",mapper.selectAllBoardList());
	}
	@Override
	public String writeSave(MultipartHttpServletRequest mul, HttpServletRequest request) {
		BoardDTO dto = new BoardDTO();
		dto.setTitle(mul.getParameter("title"));
		dto.setContent(mul.getParameter("content"));
		HttpSession session = request.getSession();
		dto.setId((String) session.getAttribute(MemberSessionName.LOGIN));
		
		BoardFileService bfs = new BoardFileServiceImpl();
		MultipartFile file = mul.getFile("image_file_name");
		if(file.isEmpty()) {
			dto.setImageFileName("none");
		}else {
			dto.setImageFileName(bfs.saveFile(file));
		}
//		int result = mapper.writeSave(dto);
//		String message = bfs.getMessage(result, request);
		return bfs.getMessage(mapper.writeSave(dto), request);
	}
}


