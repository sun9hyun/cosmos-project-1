package com.cosmos.app.user;

import java.io.IOException;
import java.util.Base64;
import java.util.Enumeration;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.cosmos.app.Execute;
import com.cosmos.app.Result;
import com.cosmos.app.user.dao.UserDAO;
import com.cosmos.app.user.vo.UserCanSkillVO;
import com.cosmos.app.user.vo.UserCompanyVO;
import com.cosmos.app.user.vo.UserInterestSkillVO;
import com.cosmos.app.user.vo.UserVO;
import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

public class JoinOkController implements Execute{
	@Override
	public Result execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
	    resp.setCharacterEncoding("UTF-8");
	    resp.setContentType("text/html; charset=utf-8");
		HttpSession session = req.getSession();
		Result result = new Result();
		String uploadPath = req.getSession().getServletContext().getRealPath("/") + "upload/";
		int fileSize = 1024 * 1024 * 5; //5M 
		MultipartRequest multipartRequest = new MultipartRequest(req, uploadPath, fileSize, "UTF-8", new DefaultFileRenamePolicy()); 
		UserCanSkillVO userCanSkillVO;
		UserInterestSkillVO userInterestSkillVO;
		UserCompanyVO userCompanyVO;
		
		System.out.println("---joinok 들어옴---");
		
		UserDAO userDAO = new UserDAO();
		UserVO userVO = new UserVO();
		
		System.out.println(multipartRequest.getParameter("userNickname"));
		System.out.println(multipartRequest.getParameter("userIntroduce"));
		System.out.println(multipartRequest.getParameter("userCareer"));
		System.out.println(multipartRequest.getParameter("userCareerYear"));
//		System.out.println(Integer.parseInt(req.getParameter("userCareerYear")));
//		System.out.println(multipartRequest.getParameter("userFile"));
		System.out.println(multipartRequest.getParameter("userId"));
		
		String userNickname = multipartRequest.getParameter("userNickname");
		String userIntroduce = multipartRequest.getParameter("userIntroduce");
		String userCareer = multipartRequest.getParameter("userCareer");
		int userCareerYear = Integer.parseInt(multipartRequest.getParameter("userCareerYear"));
//		String userFile = multipartRequest.getParameter("userFile");
		String userId = multipartRequest.getParameter("userId");
		
		String [] companyNames = multipartRequest.getParameterValues("userConpany");
		String [] interestSkills = multipartRequest.getParameterValues("interestSkill");
		String [] CanSkills = multipartRequest.getParameterValues("CanSkill");

		Enumeration files = multipartRequest.getFileNames();

	    String name = (String) files.nextElement();	    
	    String userFile = multipartRequest.getFilesystemName(name); //서버에 실제로 업로드된 파일명

	    System.out.println("---변수 저장---");
		System.out.println(companyNames);
		System.out.println(interestSkills);
		System.out.println(CanSkills);
		
			
		userVO.setUserNickname(userNickname);
		userVO.setUserIntroduce(userIntroduce);
		userVO.setUserCareer(userCareer);
		userVO.setUserCareerYear(userCareerYear);
		userVO.setUserFile(userFile);
		userVO.setUserId(userId);
		
		
		System.out.println("userVO 저장");

		userDAO.join(userVO);
		
		session.setAttribute("userId", userId);	
		
		
		
		userVO = userDAO.userSession(userId);		
		int loginNumber = userVO.getUserNumber();
		
		System.out.println("---조인ok 회원번호 조회----");
		System.out.println(loginNumber);
		
		
		  //유저 회사 추가
		  for (String company : companyNames) { 
		  userCompanyVO=new UserCompanyVO();
		  userCompanyVO.setUserNumber(loginNumber);
		  userCompanyVO.setCompanyNumber(userDAO.getCompanyNumber(company.trim()));
		  userDAO.insertUserCompany(userCompanyVO); 
		  
		  }
		  
		  for (String canSkill : CanSkills) { userCanSkillVO= new UserCanSkillVO();
		  userCanSkillVO.setUserNumber(loginNumber);
		  userCanSkillVO.setSkillNumber(userDAO.getSkillNumber(canSkill.trim()));
		  userDAO.insertUserCanSkill(userCanSkillVO); }
		  
		  for (String interestSkill : interestSkills) { 
				 userInterestSkillVO=new UserInterestSkillVO();
				 userInterestSkillVO.setUserNumber(loginNumber);
				 userInterestSkillVO.setSkillNumber(userDAO.getSkillNumber(interestSkill.trim()));
				 userDAO.insertUserInterestSkill(userInterestSkillVO);
			  }
		
		System.out.println("메소드");
		
//		result.setRedirect(true);
//		result.setPath(req.getContextPath() + "/user/login.us");
		return result;
	
	}
}

