package com.care.root.board.service;

import javax.servlet.http.HttpServletRequest;

import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartHttpServletRequest;

public interface BoardService {
	public void selectAllBoardList(Model model);
	public String writeSave(MultipartHttpServletRequest mul, HttpServletRequest request);
}