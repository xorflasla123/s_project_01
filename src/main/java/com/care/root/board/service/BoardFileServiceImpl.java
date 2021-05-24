package com.care.root.board.service;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.multipart.MultipartFile;

public class BoardFileServiceImpl implements BoardFileService{

	@Override
	public String getMessage(int result, HttpServletRequest request) {
		String message = null;
		if(result==1) {
			message = "<script>alert('새 글이 추가 되었습니다.');";
			//message += "location.href='/root/board/boardAllList';</script>";
			message += "location.href='"+request.getContextPath()+"/board/boardAllList';</script>";
		}else {
			message = "<script>alert('문제가 발생했습니다.');";
			//message += "location.href='/root/board/writeForm';</script>";
			message += "location.href='"+request.getContextPath()+"/board/writeForm';</script>";
		}
		return message;
	}

	@Override
	public String saveFile(MultipartFile file) {
		SimpleDateFormat simpl = new SimpleDateFormat("yyyyMMddHHmmss-");
		Calendar calendar = Calendar.getInstance();
		String sysFileName = simpl.format(calendar.getTime())+file.getOriginalFilename();
		File saveFile = new File(IMAGE_REPO+"/"+sysFileName);
		try {
			file.transferTo(saveFile);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sysFileName;
	}

}
