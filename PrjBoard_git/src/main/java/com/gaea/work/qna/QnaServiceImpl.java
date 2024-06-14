package com.gaea.work.qna;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

@Service
public class QnaServiceImpl implements QnaService {
    @Autowired
    QnaDao dao;
    
    @Autowired
    PageVO pageVO;
    
    final int PAGE_LIMIT = 10;
    final int BLOCK_LIMIT = 5;

    public QnaServiceImpl() {}

    @Override
    public int updateQnaArticle(QnaVO inVO) throws SQLException {
        return dao.updateArticle(inVO);
    }

    @Override
    public int deleteQnaArticle(QnaVO inVO) throws SQLException {
        return dao.deleteArticle(inVO);
    }

    @Override
    public QnaVO selectOneQnaArticle(QnaVO inVO) throws SQLException, EmptyResultDataAccessException {
    	dao.updateReadCnt(inVO);
        return dao.selectOneArticle(inVO);
    }

    @Override
    public int saveQnaArticle(QnaVO inVO) throws SQLException {
        return dao.saveArticle(inVO);
    }

    @Override
    public List<QnaVO> retrieveQnaArticle(QnaVO inVO) throws SQLException {
        return dao.retrieveArticle(inVO);
    }

    @Override
    public QnaVO moveToMod(QnaVO inVO) throws SQLException, EmptyResultDataAccessException {
        return dao.selectOneArticle(inVO);
    }

    @Override
    public List<QnaVO> pagingList(int page) throws SQLException {
        if (page <= 0) {
            page = 1;
        }
        int startRow = (page - 1) * PAGE_LIMIT + 1;
        int endRow = page * PAGE_LIMIT;

        Map<String, Integer> pagingParams = new HashMap<>();
        pagingParams.put("start", startRow);
        pagingParams.put("end", endRow);

        return dao.pagingList(pagingParams);
    }

    @Override
    public PageVO pagingParam(int page) throws SQLException {
        if (page <= 0) {
            page = 1;
        }
        int qnaCount = dao.qnaCount();
        int maxPage = (int) (Math.ceil((double) qnaCount / PAGE_LIMIT));
        int startPage = (((int)(Math.ceil((double) page / BLOCK_LIMIT))) - 1) * BLOCK_LIMIT + 1;
        int endPage = startPage + BLOCK_LIMIT - 1;
        if (endPage > maxPage) {
            endPage = maxPage;
        }
        int startRow = (page - 1) * PAGE_LIMIT + 1;
        PageVO pageVO = new PageVO();
        pageVO.setPage(page);
        pageVO.setMaxPage(maxPage);
        pageVO.setStartPage(startPage);
        pageVO.setEndPage(endPage);
        pageVO.setStartRow(startRow);
        return pageVO;
    }

}
