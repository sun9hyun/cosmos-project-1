package com.cosmos.app.board.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import com.cosmos.app.board.vo.BoardDTO;
import com.cosmos.app.board.vo.BoardSkillVO;
import com.cosmos.app.board.vo.BoardVO;
import com.cosmos.app.board.vo.SupporterVO;
import com.cosmos.mybatis.config.MyBatisConfig;

public class BoardDAO {
	SqlSessionFactory sqlSessionFactory = MyBatisConfig.getSqlsessionFactory();
    SqlSession sqlSession;
    
    public BoardDAO() {
    	sqlSession = sqlSessionFactory.openSession(true);
    }
    
    public void insert(BoardVO boardVO) {
    	sqlSession.insert("Board.insertBoard", boardVO);
    }
    
    public List<BoardDTO> selectUserCompany(int userNumber){
    	return sqlSession.selectList("Board.selectUserCompany", userNumber);
    }
    
    public BoardDTO selectBoardDetail(int boardNumber){
    	return sqlSession.selectOne("Board.selectBoardDetail", boardNumber);
    }
    
    public BoardDTO selectCompany(int boardNumber){
    	return sqlSession.selectOne("Board.selectCompany", boardNumber);
    }
    
    public void updateReadCount(int boardNumber) {
		sqlSession.update("Board.updateReadCount", boardNumber);
	}
    
    public void updateClose(int boardNumber) {
		sqlSession.update("Board.updateClose", boardNumber);
	}
    
    public void updateBoard(BoardVO boardVO) {
    	sqlSession.update("Board.updateBoard", boardVO);
    }
    
    public void deleteBoard(int boardNumber) {
		sqlSession.delete("Board.deleteBoard", boardNumber);
	}
    
    public List<BoardVO> selectTitle() {
    	return sqlSession.selectList("Board.selectTitle");
    }
    
    public int selectSupport(SupporterVO supporterVO) {
    	return sqlSession.selectOne("Board.selectSupport", supporterVO);
    }
    
    public void insertSupport(SupporterVO supporterVO) {
    	sqlSession.insert("Board.insertSupport", supporterVO);
    }
    
    public void updateSupporter(BoardVO boardVO) {
    	sqlSession.insert("Board.updateSupporter", boardVO);
    }
	
    public int getLastBoardNumber() {
    	return sqlSession.selectOne("Board.getLastBoardNumber");
    }
    
    public void insertBoardSkill(BoardSkillVO boardSkillVO) {
    	sqlSession.insert("Board.insertBoardSkill",boardSkillVO);
    }
}
