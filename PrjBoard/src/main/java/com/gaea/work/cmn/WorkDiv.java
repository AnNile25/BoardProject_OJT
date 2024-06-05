package com.gaea.work.cmn;

import java.sql.SQLException;
import java.util.List;

import org.springframework.dao.EmptyResultDataAccessException;

/**
 * 모든 DAO 인터페이스는 WorkDiv를 상속. 
 * @author user
 *
 * @param <T>
 */
public interface WorkDiv<T> {
	/**
	 * 수정
	 * @param inVO
	 * @return 1(성공)/0(실패)
	 * @throws SQLException
	 */
	int doUpdate(T inVO) throws SQLException;
	
	/**
	 * 삭제
	 * @param inVO
	 * @return 1(성공)/0(실패)
	 * @throws SQLException
	 */
	int doDelete(T inVO) throws SQLException;
	
	/**
	 * 단건 조회
	 * @param inVO
	 * @return T
	 * @throws SQLException
	 * @throws EmptyResultDataAccessException
	 */
	T doSelectOne(T inVO) throws SQLException, EmptyResultDataAccessException;
	
	/**
	 * 저장
	 * @param inVO
	 * @return 1(성공)/0(실패)
	 * @throws SQLException
	 */
	int doSave(T inVO) throws SQLException;
	
	/**
	 * 목록조회
	 * @param inVO
	 * @return List<T>
	 * @throws SQLException
	 */
	List<T> doRetrieve(T inVO) throws SQLException;
	
	
}
