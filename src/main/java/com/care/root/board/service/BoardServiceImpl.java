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
		
		if(result == 1) { //DB삭제 성공
			bfs.deleteImage(imageFileName);
		}
		dto.setRequest(request);
		dto.setResult(result);
		dto.setSuccessMessage("성공적으로 삭제되었습니다");
		dto.setSuccessURL("/board/boardAllList");
		dto.setFailMessage("삭제 중 문제가 발생하였습니다");
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
		
		if(file.isEmpty()) { //사용자가 새로운 이미지를 선택하지 않음(변경없음)
			dto.setImageFileName(mul.getParameter("originFileName"));
		}else { //이미지 수정됨
			dto.setImageFileName(bfs.saveFile(file));
			bfs.deleteImage(mul.getParameter("originFileName"));
		}
		int result = mapper.modify(dto);
		MessageDTO mDto = new MessageDTO();
		mDto.setResult(result);
		mDto.setRequest(request);
		mDto.setSuccessMessage("성공적으로 수정되었습니다.");
		mDto.setSuccessURL("/board/boardAllList");
		mDto.setFailMessage("수정 중 문제 발생!");
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









