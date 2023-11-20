package test.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import test.controller.DTO.CommentDTO;
import test.domain.Article;
import test.domain.Comment;
import test.repository.ArticleRepository;
import test.repository.CommentRepository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommentService {

    private final CommentRepository commentRepository;
    private final ArticleRepository articleRepository;

    //댓글 전체 출력
    public List<CommentDTO> printCommentsAll(Long articleId) {

        List<Comment> comments = commentRepository.findByArticleId(articleId);
        List<CommentDTO> articleDTOS = new ArrayList<>();

        for (Comment comment : comments) {
            CommentDTO commentDTO = CommentDTO.builder()
                    .article(comment.getArticle())
                    .content(comment.getContent())
                    .createDate(comment.getCreateDate())
                    .modifyDate(comment.getModifyDate())
                    .build();

            articleDTOS.add(commentDTO);
        }

        return articleDTOS;
    }


    //댓글 저장
    @Transactional(readOnly = false)
    public void saveComment(Long articleId, CommentDTO commentDTO) {

        Article article = articleRepository.findOne(articleId);

        Comment comment = Comment.builder()
                .article(article)
                .content(commentDTO.getContent())
                .createDate(new Date())
                .modifyDate(new Date())
                .build();

        commentRepository.save(comment);
    }

    //댓글 id로 댓글 삭제
    @Transactional(readOnly = false)
    public void deleteCommentByCommentId(Long commentId) {
        commentRepository.delete(commentId);
    }

    //게시글 id로 게시글 삭제 (게시글이 삭제될 때 관련된 댓글 일괄 삭제할 때 사용)
    @Transactional(readOnly = false)
    public void deleteCommentByArticleId(Long articleId) {
        commentRepository.deleteByArticleId(articleId);
    }
}
