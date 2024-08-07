package com.gaea.work.qna;

import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gaea.work.cmn.PagingVO;

@Service
public class QnaServiceImpl implements QnaService {
	
    @Autowired
    QnaDao dao;
    
    public QnaServiceImpl() {}

    @Override
    public int updateQnaArticle(QnaVO inVO) throws SQLException {
        return dao.updateArticle(inVO);
    }

    @Override
    @Transactional
    public int deleteQnaArticle(QnaVO inVO) throws SQLException {    	
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
        if (isNotEmpty(pagingVO.getStartDate()) && isNotEmpty(pagingVO.getEndDate())) {
        	SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        	try {
        		Date startDate = dateFormat.parse(pagingVO.getStartDate());
        		Date endDate = dateFormat.parse(pagingVO.getEndDate());
        		if (endDate.before(startDate)) {
                    throw new IllegalArgumentException("종료 날짜는 시작 날짜보다 이전일 수 없습니다.");
                }
        	} catch (ParseException e) {
        		e.printStackTrace();
			}
        }
        // 검색 조건을 반영하여 전체 게시글 수를 계산
        int totalCnt = dao.qnaCount(pagingVO);
        pagingVO.setTotalCnt(totalCnt);
        pagingVO.calculatedLastPage(totalCnt, pagingVO.getCntPerPage());
        pagingVO.calculatedStartEndPage(pagingVO.getNowPage(), pagingVO.getCntPage());
        pagingVO.calculatedOffsetLimit(pagingVO.getNowPage(), pagingVO.getCntPerPage());
        pagingVO.setStartDate(pagingVO.getStartDate());
        pagingVO.setEndDate(pagingVO.getEndDate());
        return dao.retrieveArticle(pagingVO);
    }

    private boolean isNotEmpty(String str) {
        return str != null && !str.trim().isEmpty();
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

}
