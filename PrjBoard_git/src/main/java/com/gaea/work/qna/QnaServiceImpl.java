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
	public int countQnaArticle() throws SQLException {
		return dao.qnaCount();
	}

}
