package com.gaea.work.board.controller;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.gaea.work.board.domain.BoardVO;
import com.gaea.work.board.service.BoardService;
import com.gaea.work.cmn.GLog;
import com.gaea.work.cmn.MessageVO;
import com.gaea.work.cmn.StringUtil;

@Controller
@RequestMapping("board")
public class BoardController implements GLog {
	
	@Autowired
	BoardService service;
	
	public BoardController() {}
		
	/**
	 * 상세 페이지에서 수정 페이지로 이동
	 * @param inVO
	 * @param model
	 * @return board/board_mod
	 * @throws SQLException
	 */
	@GetMapping(value="/moveToMod.do")
	public String moveToMod(BoardVO inVO, Model model) throws SQLException {
		LOG.debug("┌───────────────────────────────────┐");
		LOG.debug("│ moveToMod                         │");
		LOG.debug("│ BoardVO                           │"+inVO);
		LOG.debug("└───────────────────────────────────┘");
		
		if(0 == inVO.getBoardSeq() ) {
			LOG.debug("============================");
			LOG.debug("==nullPointerException===");
			LOG.debug("============================");
			
			throw new NullPointerException("순번을 입력 하세요");
		}
		
		if( null==inVO.getMemberId()) {
			inVO.setMemberId(StringUtil.nvl(inVO.getMemberId(),"Guest"));
		}
		
		BoardVO  outVO = service.moveToMod(inVO);
		model.addAttribute("vo", outVO);
		
		return "board/board_mod";
	}
	
	@GetMapping(value = "/moveToReg.do")
	public String moveToReg() {
		LOG.debug("┌───────────────────────────────────┐");
		LOG.debug("│ moveToReg                         │");
		LOG.debug("└───────────────────────────────────┘");
		return "board/board_reg";
	}
	
	@PostMapping(value = "/doUpdateLikeCnt.do", produces = "application/json;charset=UTF-8")
	@ResponseBody
	public MessageVO doUpdateLikeCnt(BoardVO inVO) throws SQLException{
		LOG.debug("┌───────────────────────────────────┐");
		LOG.debug("│ doUpdateLikeCnt                   │");
		LOG.debug("│ BoardVO                           │"+inVO);
		LOG.debug("└───────────────────────────────────┘");
		
		int flag = service.doUpdateLikeCnt(inVO);
		
		String message = "";
		if(1==flag) {
			message = "좋아요 성공.";
		}else {
			message = "좋아요 실패.";
		}
		
		MessageVO messageVO=new MessageVO(flag+"",message);
		LOG.debug("│ messageVO                           │"+messageVO);
		return messageVO;
	}
	
	@GetMapping(value = "/doRetrieve.do")
	public ModelAndView doRetrieve(BoardVO inVO, ModelAndView modelAndView) throws SQLException {
	    LOG.debug("┌───────────────────────────────────┐");
	    LOG.debug("│ doRetrieve                        │");
	    LOG.debug("│ BoardVO                           │" + inVO);
	    LOG.debug("└───────────────────────────────────┘");

	    // 검색구분:""
	    if (inVO.getSearchDiv() == null) {
	        inVO.setSearchDiv(StringUtil.nvl(inVO.getSearchDiv()));
	    }
	    // 검색어:""
	    if (inVO.getSearchWord() == null) {
	        inVO.setSearchDiv(StringUtil.nvl(inVO.getSearchWord()));
	    }
	    LOG.debug("│ BoardVO Default 처리                       │" + inVO);

	    // 목록 조회
	    List<BoardVO> list = service.doRetrieve(inVO);

	    long totalCnt = 0;
	    
	    // 총 글 수
	    if (!list.isEmpty()) {
	        totalCnt = list.get(0).getTotalCnt();
	    }
	    
	    modelAndView.setViewName("board/board_list");
	    modelAndView.addObject("totalCnt", totalCnt);
	    modelAndView.addObject("list", list);
	    modelAndView.addObject("paramVO", inVO); // 검색 데이터

	    return modelAndView;
	}
	
	@PostMapping(value = "/doUpdate.do", produces = "application/json;charset=UTF-8")
	@ResponseBody
	public MessageVO doUpdate(BoardVO inVO) throws SQLException{
		LOG.debug("┌───────────────────────────────────┐");
		LOG.debug("│ doUpdate                          │");
		LOG.debug("│ BoardVO                           │"+inVO);
		LOG.debug("└───────────────────────────────────┘");
		
		int flag = service.doUpdate(inVO);
		
		String message = "";
		if(1==flag) {
			message = "수정 되었습니다.";
		}else {
			message = "수정 실패.";
		}
		
		MessageVO messageVO=new MessageVO(flag+"",message);
		LOG.debug("│ messageVO                           │"+messageVO);
		
		return messageVO;		
	}
	
	@GetMapping(value = "/doSelectOne.do")
	public String doSelectOne(BoardVO inVO, Model model) throws SQLException {
		String view = "board/board_mng";
		LOG.debug("┌───────────────────────────────────┐");
		LOG.debug("│ doSelectOne                       │");
		LOG.debug("│ BoardVO                           │"+inVO);
		LOG.debug("└───────────────────────────────────┘");			
		if(0 == inVO.getBoardSeq() ) {
			LOG.debug("============================");
			LOG.debug("==nullPointerException===");
			LOG.debug("============================");
			
			throw new NullPointerException("순번을 입력 하세요");
		}
		
		if( null==inVO.getMemberId()) {
			inVO.setMemberId(StringUtil.nvl(inVO.getMemberId(),"Guest"));
		}
		
		BoardVO  outVO = service.doSelectOne(inVO);
		model.addAttribute("vo", outVO);
				
		return view;
	}
	
	@PostMapping(value = "/doSave.do", produces = "application/json;charset=UTF-8")
	@ResponseBody
	public MessageVO doSave(BoardVO inVO) throws SQLException{
		LOG.debug("┌───────────────────────────────────┐");
		LOG.debug("│ doSave                            │");
		LOG.debug("│ BoardVO                           │"+inVO);
		LOG.debug("└───────────────────────────────────┘");				
		//seq조회
		int seq = service.getSeq();
		inVO.setBoardSeq(seq);
		LOG.debug("│ BoardVO boardSeq                  │"+inVO);
		int flag = service.doSave(inVO);
		
		String message = "";
		if(1 == flag) {
			message = "등록 되었습니다.";
		}else {
			message = "등록 실패.";
		}
		
		MessageVO  messageVO=new MessageVO(String.valueOf(flag), message);
		LOG.debug("│ messageVO                           │"+messageVO);
		return messageVO;
	}
	
	@GetMapping(value ="/doDelete.do",produces = "application/json;charset=UTF-8")
	@ResponseBody // HTTP 요청 부분의 body부분이 그대로 브라우저 전달
	public MessageVO doDelete(BoardVO inVO) throws SQLException {
		LOG.debug("┌───────────────────────────────────┐");
		LOG.debug("│ doDelete                          │");
		LOG.debug("│ BoardVO                           │"+inVO);
		LOG.debug("└───────────────────────────────────┘");		
		int flag = service.doDelete(inVO);
		
		String message = "";
		if(1 == flag) {//삭제 성공
			message = inVO.getBoardSeq()+"삭제 되었습니다.";	
		}else {
			message = inVO.getBoardSeq()+"삭제 실패.";
		}
		
		MessageVO messageVO = new MessageVO(String.valueOf(flag), message);
		
		LOG.debug("│ messageVO                           │"+messageVO);
		
		return messageVO;
	}

}
