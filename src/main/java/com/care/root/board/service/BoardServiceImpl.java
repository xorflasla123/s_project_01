package com.care.root.board.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.care.root.board.dto.BoardDTO;
import com.care.root.board.dto.BoardRepDTO;
import com.care.root.member.session_name.MemberSessionName;
import com.care.root.message.MessageDTO;
import com.care.root.mybatis.board.BoardMapper;

@Service
public abstract class BoardServiceImpl implements BoardService{
	@Autowired BoardMapper mapper;
	public void selectAllBoardList(Model model, int num) { //num=pageNo
		int allCount = mapper.selectBoardCount(); //count of writes
		int pageLetter = 3; //3 writes in 1 page
		int repeat = allCount/pageLetter;
		if(allCount%pageLetter != 0) {
			repeat++;
		}
		int end = num * pageLetter;
		int start = end + 1 -pageLetter;
		model.addAttribute("repeat", repeat);
		model.addAttribute("boardList",mapper.selectAllBoardList(start, end));
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
	@Override
	public void contentView(int writeNo, Model model) {
		model.addAttribute("personalData", mapper.contentView(writeNo));
		upHit(writeNo);
	}
	private void upHit(int writeNo) {
		mapper.upHit(writeNo);
	}
	@Override
	public String boardDelete(int writeNo, String imageFileName, HttpServletRequest request) {
		BoardFileService bfs = new BoardFileServiceImpl();
		int result = mapper.delete(writeNo);
		MessageDTO dto = new MessageDTO();
		
		if(result == 1) { //DB?????? ??????
			bfs.deleteImage(imageFileName);
		}
		dto.setRequest(request);
		dto.setResult(result);
		dto.setSuccessMessage("??????????????? ?????????????????????");
		dto.setSuccessURL("/board/boardAllList");
		dto.setFailMessage("?????? ??? ????????? ?????????????????????");
		dto.setFailURL("/board/contentView");
		
		return bfs.getMessage(dto);
	}
	@Override
	public String modify(MultipartHttpServletRequest mul, HttpServletRequest request) {
		BoardDTO dto = new BoardDTO();
		dto.setWriteNo(Integer.parseInt(mul.getParameter("writeNo")));
		dto.setTitle(mul.getParameter("title"));
		dto.setContent(mul.getParameter("content"));
		
		MultipartFile file = mul.getFile("imageFileName");
		BoardFileService bfs = new BoardFileServiceImpl();
		
		if(file.isEmpty()) { //???????????? ????????? ???????????? ???????????? ??????(????????????)
			dto.setImageFileName(mul.getParameter("originFileName"));
		}else { //????????? ?????????
			dto.setImageFileName(bfs.saveFile(file));
			bfs.deleteImage(mul.getParameter("originFileName"));
		}
		int result = mapper.modify(dto);
		MessageDTO mDto = new MessageDTO();
		mDto.setResult(result);
		mDto.setRequest(request);
		mDto.setSuccessMessage("??????????????? ?????????????????????.");
		mDto.setSuccessURL("/board/boardAllList");
		mDto.setFailMessage("?????? ??? ?????? ??????!");
		mDto.setFailURL("/board/modify_form");
		
		return bfs.getMessage(mDto);
	}
	@Override
	public void addReply(BoardRepDTO dto) {
		mapper.addReply(dto);
	}
	@Override
	public List<BoardRepDTO> getRepList(int write_group) {
		return mapper.getRepList(write_group);
	}
}









