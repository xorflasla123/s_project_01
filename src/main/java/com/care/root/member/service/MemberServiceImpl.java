package com.care.root.member.service;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.care.root.member.dto.MemberDTO;
import com.care.root.mybatis.member.MemberMapper;

@Service
public class MemberServiceImpl implements MemberService{
	@Autowired MemberMapper mapper;
	public int user_check(HttpServletRequest request) {
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		
		MemberDTO dto = mapper.user_check(request.getParameter("id"));
		if(dto != null) {
			//if(request.getParameter("pw").equals(dto.getPw())) {
			if(encoder.matches(request.getParameter("pw"), dto.getPw()) ||
								request.getParameter("pw").equals(dto.getPw())) {
				return 0;
			}
		}
		return 1;
		
	}
	public void memberInfo(Model model) {
		ArrayList<MemberDTO> list = mapper.memberInfo();
		model.addAttribute("memberList", list);
		//model.addAttribute("memberList", mapper.memberInfo());
	}
	public void info(String userId, Model model) {
		model.addAttribute("info",mapper.info(userId)) ;
	}
	public int register(MemberDTO dto) {
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		//dto.setPw(encoder.encode(dto.getPw()));
		System.out.println("비밀번호 변경 전 : "+dto.getPw());
		String pw = encoder.encode(dto.getPw());
		System.out.println("암호화 후 : "+pw);
	
		dto.setPw(pw);
		
		dto.setLimitTime(new Date(System.currentTimeMillis()));
		dto.setSessionId("nan");
		
		try {
			return mapper.register(dto);
		}catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}
	
	public void keepLogin(String sessionId, Date limitDate, String id) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("sessionId", sessionId);
		map.put("limitDate", limitDate);
		map.put("id", id);
		mapper.keepLogin(map);
	}
	public MemberDTO getUserSessionId(String sessionId) {
		return mapper.getUserSessionId(sessionId);
	}
}








