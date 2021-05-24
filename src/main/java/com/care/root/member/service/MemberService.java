package com.care.root.member.service;

import java.sql.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.care.root.member.dto.MemberDTO;

public interface MemberService {
	public int user_check(HttpServletRequest request);
	public void memberInfo(Model model);
	public void info(String userId, Model model);
	public int register(MemberDTO dto);
	
	public void keepLogin(String sessionId, Date limitDate, String id);
	
	public MemberDTO getUserSessionId(String sessionId);
}
