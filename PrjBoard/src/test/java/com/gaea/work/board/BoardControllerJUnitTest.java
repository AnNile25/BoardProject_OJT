package com.gaea.work.board;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.ModelAndView;

import com.gaea.work.board.dao.BoardDao;
import com.gaea.work.board.domain.BoardVO;
import com.gaea.work.cmn.GLog;
import com.gaea.work.cmn.MessageVO;
import com.google.gson.Gson;

@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"file:src/main/webapp/WEB-INF/spring/root-context.xml"
		,"file:src/main/webapp/WEB-INF/spring/appServlet/servlet-context.xml"})
public class BoardControllerJUnitTest implements GLog {
	
	@Autowired
	BoardDao  dao;
	
	@Autowired
	WebApplicationContext webApplicationContext;
	
	//브라우저 대역
	MockMvc  	  mockMvc;
	List<BoardVO> boardList;
	BoardVO       searchVO;
	
	@Before
	public void setUp() throws Exception {
		LOG.debug("┌───────────────────────────────────────────┐");
		LOG.debug("│ setUp()                                   │");		
		LOG.debug("└───────────────────────────────────────────┘");
		
		String memberId = "admin";
		String title = "title";
		String content = "content";
		int likeCnt = 0;
		int readCnt = 0;
		String regDt = "사용 하지 않음";
		String modDt = "";
		
		String searchKeyword = "X";
		
		mockMvc  = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
		boardList = Arrays.asList(
				 new BoardVO(dao.getSeq(), 1, memberId, title+"-01", content+"-01", likeCnt,    readCnt  , regDt, modDt, searchKeyword),
				 new BoardVO(dao.getSeq(), 1, memberId, title+"-02", content+"-02", likeCnt+22, readCnt+2, regDt, modDt, searchKeyword),
				 new BoardVO(dao.getSeq(), 1, memberId, title+"-03", content+"-03", likeCnt+33, readCnt+3, regDt, modDt, searchKeyword)
		);
		
		searchVO = new BoardVO();
		searchVO.setTitle(title);
		
	}
	
	@Ignore
	@Test
	public void doUpdateLikeCnt() throws Exception {
		BoardVO board01 = boardList.get(0);
		
		//1. 데이터 입력
		int flag = dao.doSave(board01);
		assertEquals(1, flag);
		
		//2. 등록된 데이터 조회
		BoardVO vo = dao.doSelectOne(board01);
		
		//3. 조회된 데이터 수정
		vo.setLikeCnt(vo.getLikeCnt()+1);
		
		//4. url, param, method(get/post)
		MockHttpServletRequestBuilder requestBuilder =
				MockMvcRequestBuilders.post("/board/doUpdate.do")
				.param("boardSeq", vo.getBoardSeq()+"")
				.param("title",    vo.getTitle())
				.param("content",  vo.getContent())
				.param("likeCnt",  vo.getLikeCnt()+"")
				.param("readCnt",  vo.getReadCnt()+"")
				;
		
		// 호출
		ResultActions resultActions = mockMvc.perform(requestBuilder).andExpect(status().isOk());
		
		//호출 결과
		String result = resultActions.andDo(print()).andReturn().getResponse().getContentAsString();
		LOG.debug("│ result                                │"+result);		
		
		MessageVO messageVO = new Gson().fromJson(result, MessageVO.class);
		assertEquals("1", messageVO.getMsgId());
		
		//5. 수정 데이터 조회
		BoardVO updateResult = dao.doSelectOne(vo);
		
		//6. 데이터 비교
		isSameBoard(updateResult, vo);
	}
	
	@Ignore
	@Test
	public void doRetrieve() throws Exception{
		LOG.debug("┌───────────────────────────────────────────┐");
		LOG.debug("│ doRetrieve()                              │");		
		LOG.debug("└───────────────────────────────────────────┘");
		
		MockHttpServletRequestBuilder  requestBuilder  =
				MockMvcRequestBuilders.get("/board/doRetrieve.do")
				.param("pageSize",   "0")
				.param("pageNo",     "0")
				;		
		
		//호출 : ModelAndView      
		MvcResult mvcResult=  mockMvc.perform(requestBuilder).andExpect(status().isOk()).andReturn() ;
		
		//호출결과
		ModelAndView modelAndView = mvcResult.getModelAndView();
		List<BoardVO>  list  = (List<BoardVO>) modelAndView.getModel().get("list");
		BoardVO  paramVO  = (BoardVO) modelAndView.getModel().get("paramVO");
				
		for(BoardVO vo  :list) {
			LOG.debug(vo.toString());
		}
		
		assertNotNull(list);
		assertNotNull(paramVO);		
	}
	
	@Ignore
	@Test
	public void doUpdate() throws Exception {
		BoardVO board01 = boardList.get(0);
		
		//1. 데이터 입력
		int flag = dao.doSave(board01);
		assertEquals(1, flag);
		
		//2. 등록된 데이터 조회
		BoardVO vo = dao.doSelectOne(board01);
		
		//3. 조회된 데이터 수정
		String upStr = "-U";
		vo.setTitle(vo.getTitle()+upStr);
		vo.setContent(vo.getContent()+upStr);
		
		//4. url, param, method(get/post)
		MockHttpServletRequestBuilder requestBuilder =
				MockMvcRequestBuilders.post("/board/doUpdate.do")
				.param("boardSeq", vo.getBoardSeq()+"")
				.param("title",    vo.getTitle())
				.param("content",  vo.getContent())
				.param("likeCnt",  vo.getLikeCnt()+"")
				.param("readCnt",  vo.getReadCnt()+"")
				.param("modDt",    vo.getModDt()+"")
				;
		
		// 호출
		ResultActions resultActions = mockMvc.perform(requestBuilder).andExpect(status().isOk());
		
		//호출 결과
		String result = resultActions.andDo(print()).andReturn().getResponse().getContentAsString();
		LOG.debug("│ result                                │"+result);		
		
		MessageVO messageVO = new Gson().fromJson(result, MessageVO.class);
		assertEquals("1", messageVO.getMsgId());
		
		//5. 수정 데이터 조회
		BoardVO updateResult = dao.doSelectOne(vo);
		
		//6. 데이터 비교
		isSameBoard(updateResult, vo);
	}
	
	private void isSameBoard(BoardVO vo, BoardVO board) {
		assertEquals(vo.getBoardSeq(), 	board.getBoardSeq());
		assertEquals(vo.getTitle(), 	board.getTitle());
		assertEquals(vo.getContent(), 	board.getContent());
		assertEquals(vo.getLikeCnt(), 	board.getLikeCnt());
		assertEquals(vo.getReadCnt(), 	board.getReadCnt());
		assertEquals(vo.getModDt(), 	board.getModDt());
	}
	
	@Ignore
	@Test
	public void doSelectOne()throws Exception{
		LOG.debug("┌───────────────────────────────────────────┐");
		LOG.debug("│ doSelectOne()                             │");		
		LOG.debug("└───────────────────────────────────────────┘");
		
		int flag = dao.doSave(boardList.get(0));
		assertEquals(1, flag);
		BoardVO vo = boardList.get(0);
		
		MockHttpServletRequestBuilder  requestBuilder  =
				MockMvcRequestBuilders.get("/board/doSelectOne.do")
				.param("boardSeq",  vo.getBoardSeq()+"")
				.param("memberId",  vo.getMemberId())
				;		
		
		//호출 : ModelAndView      
		MvcResult mvcResult=  mockMvc.perform(requestBuilder).andExpect(status().isOk()).andReturn() ;
		//호출결과
		ModelAndView modelAndView = mvcResult.getModelAndView();
		BoardVO outVO = (BoardVO) modelAndView.getModel().get("vo");
		LOG.debug("│ outVO                                │"+outVO);
		
		assertNotNull(outVO);
	}
	
	@Ignore
	@Test
	public void doSave()throws Exception{
		LOG.debug("┌───────────────────────────────────────────┐");
		LOG.debug("│ doSave  ()                                │");		
		LOG.debug("└───────────────────────────────────────────┘");		
		
		BoardVO vo = boardList.get(0);
		
		//url, 호출방식(get), seq
		MockHttpServletRequestBuilder  requestBuilder  =
				MockMvcRequestBuilders.post("/board/doSave.do")
				.param("boardseq",      vo.getBoardSeq()+"")
				.param("memberId",      vo.getMemberId())
				.param("boardCtgrySeq", vo.getBoardCtgrySeq()+"")
				.param("title",			vo.getTitle())
				.param("content", 		vo.getContent())
				.param("likeCnt",   	vo.getLikeCnt()+"")
				.param("readCnt",   	vo.getReadCnt()+"")
				;
		//호출        
		ResultActions resultActions=  mockMvc.perform(requestBuilder).andExpect(status().isOk());
		//호출결과
		String result = resultActions.andDo(print()).andReturn().getResponse().getContentAsString();
		LOG.debug("│ result                                │"+result);		
		MessageVO messageVO=new Gson().fromJson(result, MessageVO.class);
		LOG.debug("│ messageVO                                │"+messageVO);
		assertEquals("1", messageVO.getMsgId());
	}
	
	@Ignore
	@Test
	public void doDelete()throws Exception{
		LOG.debug("┌───────────────────────────────────────────┐");
		LOG.debug("│ doDelete()                                │");		
		LOG.debug("└───────────────────────────────────────────┘");
		
		int flag = dao.doSave(boardList.get(0));
		assertEquals(1, flag);
		
		BoardVO vo = boardList.get(0);
		
		//url, 호출방식(get), seq
		MockHttpServletRequestBuilder  requestBuilder  =
				MockMvcRequestBuilders.get("/board/doDelete.do")
				.param("boardSeq", vo.getBoardSeq() + "");
		
		ResultActions resultActions=  mockMvc.perform(requestBuilder).andExpect(status().isOk());
		String result = resultActions.andDo(print()).andReturn().getResponse().getContentAsString();
		LOG.debug("│ result                                │"+result);
		
		MessageVO messageVO=new Gson().fromJson(result, MessageVO.class);
		LOG.debug("│ messageVO                                │"+messageVO);
		
		assertEquals("1", messageVO.getMsgId());
		
	}

	@Test
	public void beans() {
		LOG.debug("┌───────────────────────────────────────────┐");
		LOG.debug("│ webApplicationContext                     │"+webApplicationContext);		
		LOG.debug("│ mockMvc                                   │"+mockMvc);
		LOG.debug("│ dao                                       │"+dao);
		LOG.debug("└───────────────────────────────────────────┘");			
		assertNotNull(webApplicationContext);
		assertNotNull(mockMvc);
		assertNotNull(dao);
	}

}
