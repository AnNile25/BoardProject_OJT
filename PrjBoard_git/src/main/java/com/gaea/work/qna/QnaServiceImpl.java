package com.gaea.work.qna;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gaea.work.cmn.PagingVO;
import com.gaea.work.reply.ReplyService;

@Service
public class QnaServiceImpl implements QnaService {
	
    @Autowired
    QnaDao dao;
    @Autowired
    ReplyService replyService;
    
    public QnaServiceImpl() {}

    @Override
    public int updateQnaArticle(QnaVO inVO) throws SQLException {
        return dao.updateArticle(inVO);
    }

    @Override
    @Transactional
    public int deleteQnaArticle(QnaVO inVO) throws SQLException {    	
    	replyService.deleteReplyByBoardSeq(inVO.getBoardSeq()); // 댓글 삭제
        return dao.deleteArticle(inVO);
    }

    @Override
    public QnaVO viewQnaArticleDetail(QnaVO inVO) throws SQLException, EmptyResultDataAccessException {
    	dao.updateReadCnt(inVO);
        return dao.selectOneArticle(inVO);
    }

    @Override
    public int saveQnaArticle(QnaVO inVO) throws SQLException {
        return dao.saveArticle(inVO);
    }

    @Override
    public List<QnaVO> retrieveQnaArticle(PagingVO pagingVO) throws SQLException {
    	if (pagingVO.getNowPage() < 1) {
            pagingVO.setNowPage(1);
        }
        if (pagingVO.getCntPerPage() < 1 || pagingVO.getCntPerPage() > 100) {
            pagingVO.setCntPerPage(10);
        }
        // 검색 조건을 반영하여 전체 게시글 수를 계산
        int totalCnt = dao.qnaCount(pagingVO);
        pagingVO.setTotalCnt(totalCnt);
        pagingVO.calculatedLastPage(totalCnt, pagingVO.getCntPerPage());
        pagingVO.calculatedStartEndPage(pagingVO.getNowPage(), pagingVO.getCntPage());
        pagingVO.calculatedStartEnd(pagingVO.getNowPage(), pagingVO.getCntPerPage());
        return dao.retrieveArticle(pagingVO);
    }


    @Override
    public QnaVO viewQnaArticleMod(QnaVO inVO) throws SQLException, EmptyResultDataAccessException {
        return dao.selectOneArticle(inVO);
    }

    @Override
    @Transactional
    public void deleteOldQnaArticle() {
        LocalDate sevenDaysAgo = LocalDate.now().minusDays(7);
        Date thresholdDate = Date.from(sevenDaysAgo.atStartOfDay(ZoneId.systemDefault()).toInstant());
        dao.deleteByCreatedDateBefore(thresholdDate);
    }
    
	@Override
	public int deleteArticleByMemberId(String  memberId) throws SQLException {
		return dao.deleteArticleByMemberId(memberId);
	}

	@Override
	public List<QnaVO> getAllAtricleByMemberId(String memberId) throws SQLException {
		 return dao.getAllAtricleByMemberId(memberId);
	}

}
