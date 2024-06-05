package com.gaea.work.board;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.sql.SQLException;
import java.util.List;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.gaea.work.board.dao.BoardDao;
import com.gaea.work.board.domain.BoardVO;
import com.gaea.work.cmn.GLog;

@RunWith(SpringJUnit4ClassRunner.class) //스프링 테스트 컨텍스트 프레임웤그의 JUnit의 확장기능 지정
@ContextConfiguration(locations = {"file:src/main/webapp/WEB-INF/spring/root-context.xml"
		,"file:src/main/webapp/WEB-INF/spring/appServlet/servlet-context.xml"})
public class BoardDaoJUnitTest implements GLog {
	
	@Autowired
	BoardDao dao;
	
	BoardVO board01;
	BoardVO board02;
	BoardVO board03;
	
	BoardVO searchVO;
	
	@Autowired
	ApplicationContext context;
	
	@Before
	public void setUp() throws Exception {
		LOG.debug("┌───────────────────────────────────┐");
		LOG.debug("│ setUp                             │");
		LOG.debug("└───────────────────────────────────┘");		
		
		String memberId = "admin";
		String title = "title";
		String content = "content";
		int likeCnt = 0;
		int readCnt = 0;
		String regDt = "사용 하지 않음";
		String modDt = "";
		
		String searchKeyword = "";
		
		board01 = new BoardVO(dao.getSeq(), 1, memberId, title+"-01", content+"-01", likeCnt, readCnt, regDt, modDt, searchKeyword);
		board02 = new BoardVO(dao.getSeq(), 1, memberId, title+"-02", content+"-02", likeCnt, readCnt, regDt, modDt, searchKeyword);
		board03 = new BoardVO(dao.getSeq(), 1, memberId, title+"-03", content+"-03", likeCnt, readCnt, regDt, modDt, searchKeyword);
		
		searchVO = new BoardVO();
		searchVO.setTitle(title);
	}
	
	@Ignore
	@Test
	public void updateLikeCnt() throws SQLException {
		//1.
		dao.doDeleteAll(searchVO);
		
		//2.
		int flag = dao.doSave(board01);
		assertEquals(1, flag);
		
		flag = dao.doSave(board02);
		assertEquals(1, flag);	
		
		flag = dao.doSave(board03);
		assertEquals(1, flag);	
		
		//3.단건조회
		//최초 등록자와 id가 동일하면 update 안 됨
		board01.setMemberId(board01.getMemberId()+"U");
		flag = dao.doUpdateLikeCnt(board01);
		assertEquals(1, flag);
		BoardVO vs01 = dao.doSelectOne(board01);
		
		assertEquals(vs01.getBoardSeq(), board01.getBoardSeq());
		assertEquals(vs01.getLikeCnt(), board01.getLikeCnt()+1);
	}
	
	@Ignore
	@Test
	public void updateReadCnt() throws SQLException {
		//1.
		dao.doDeleteAll(searchVO);
		
		//2.
		int flag = dao.doSave(board01);
		assertEquals(1, flag);
		
		flag = dao.doSave(board02);
		assertEquals(1, flag);	
		
		flag = dao.doSave(board03);
		assertEquals(1, flag);	
		
		//3.단건조회
		//최초 등록자와 id가 동일하면 update 안 됨
		board01.setMemberId(board01.getMemberId()+"U");
		flag = dao.doUpdateReadCnt(board01);
		assertEquals(1, flag);
		BoardVO vs01 = dao.doSelectOne(board01);
		
		assertEquals(vs01.getBoardSeq(), board01.getBoardSeq());
		assertEquals(vs01.getReadCnt(), board01.getReadCnt()+1);
	}
	
	@Ignore
	@Test
	public void doRetrieve() throws SQLException {				
		//1.
		dao.doDeleteAll(searchVO);
		
		//2.
		int flag = dao.doSave(board01);
		assertEquals(1, flag);
		
		flag = dao.doSave(board02);
		assertEquals(1, flag);	
		
		flag = dao.doSave(board03);
		assertEquals(1, flag);	
		
		List<BoardVO> list=dao.doRetrieve(searchVO);
		
		for(BoardVO vo :list) {
			LOG.debug(vo);
		}
	}
	
	@Ignore
	@Test
	public void update() throws SQLException {
		LOG.debug("┌───────────────────────────────────┐");
		LOG.debug("│ update                            │");
		LOG.debug("└───────────────────────────────────┘");
		
		//1. 데이터 삭제
		dao.doDeleteAll(searchVO);
		
		//2. 데이터 등록
		int flag = dao.doSave(board01);
		assertEquals(1, flag);
		
		flag = dao.doSave(board02);
		assertEquals(1, flag);	
		
		flag = dao.doSave(board03);
		assertEquals(1, flag);		
		
		//3. 등록데이터 조회
		BoardVO vo01 =dao.doSelectOne(board01);
		
		String title = vo01.getTitle()+"_U";
		String content = vo01.getContent()+"_U";
		int likeCnt = vo01.getLikeCnt() + 1;
		int readCnt = vo01.getReadCnt() + 1;
		
		//4. 3번 조회된 데이터를 수정
		vo01.setTitle(title);
		vo01.setContent(content);
		vo01.setLikeCnt(likeCnt);
		vo01.setReadCnt(readCnt);
		
		//5. update
		flag = dao.doUpdate(vo01);
		assertEquals(1, flag);
		
		//6. 수정데이터 조회
		BoardVO vs01=dao.doSelectOne(vo01);
		
		//7. 비교
		isSameBoard(vs01, vo01);
	}
	
	@Ignore
	@Test
	public void addAndGet()throws SQLException{
		//1. 삭제
		dao.doDelete(board01);
		dao.doDelete(board02);
		dao.doDelete(board03);
		
		LOG.debug("board01.getBoardSeq():"+board01.getBoardSeq());
		LOG.debug("board02.getBoardSeq():"+board02.getBoardSeq());
		LOG.debug("board03.getBoardSeq():"+board03.getBoardSeq());
		
		//2. 등록
		int flag = dao.doSave(board01);
		assertEquals(1, flag);
		
		flag = dao.doSave(board02);
		assertEquals(1, flag);
		
		flag = dao.doSave(board03);
		assertEquals(1, flag);		
		
		//3. 단건조회
		BoardVO vo01 = dao.doSelectOne(board01);
		BoardVO vo02 = dao.doSelectOne(board02);
		BoardVO vo03 = dao.doSelectOne(board03);
		
		isSameBoard(vo01, board01);
		isSameBoard(vo02, board02);
		isSameBoard(vo03, board03);
	}
	
	private void isSameBoard(BoardVO vo, BoardVO board) {
		assertEquals(vo.getBoardSeq(), board.getBoardSeq());
		assertEquals(vo.getBoardCtgrySeq(), board.getBoardCtgrySeq());
		assertEquals(vo.getMemberId(), board.getMemberId());
		assertEquals(vo.getTitle(), board.getTitle());
		assertEquals(vo.getContent(), board.getContent());
		assertEquals(vo.getLikeCnt(), board.getLikeCnt());
		assertEquals(vo.getReadCnt(), board.getReadCnt());
	}

	@Test
	public void beans() {
		LOG.debug("┌───────────────────────────────────┐");
		LOG.debug("│ beans                             │");
		LOG.debug("│ dao                               │"+dao);
		LOG.debug("│ context                           │"+context);
		LOG.debug("└───────────────────────────────────┘");
		
		assertNotNull(dao);
		assertNotNull(context);
	}

}
