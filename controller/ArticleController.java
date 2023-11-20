package test.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import test.controller.DTO.ArticleDTO;
import test.controller.DTO.ArticlesDTO;
import test.controller.DTO.CommentDTO;
import test.domain.Image;
import test.domain.embedded.UploadFile;
import test.image.FileStore;
import test.image.UploadImagesForm;
import test.service.ArticleService;
import test.service.CommentService;
import test.service.FileService;
import test.service.LikeArticleService;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class ArticleController {

    private final ArticleService articleService;
    private final LikeArticleService likeArticleService;
    private final CommentService commentService;
    private final FileService fileService;
    private final FileStore fileStore;
    //게시글 전체 출력 v1
    /*@GetMapping("/article")
    public String articles(@ModelAttribute(name = "article") ArticleDTO articleDTO,
                           @RequestParam(defaultValue = "") String redirect,
                           Model model) {

        if (redirect.equals("write")) {
            return "/article/writeArticle";
        }

        //이것은 이름을 어떻게 지어야할까?
        //articleDTOS?, articles?
        //리스트 전체 보이게 하기
        List<ArticleDTO> articles = articleService.printArticles();
        model.addAttribute("articles", articles);

        List<Long> countLikeArticles = likeArticleService.countLikeArticleAll(articles);
        model.addAttribute("countLikeArticles", countLikeArticles);

        return "/article/articles";
    }*/

    //게시글 전체 출력 v2
    @GetMapping("/article")
    public String articles(@ModelAttribute ArticlesDTO articlesDTO,
                           @ModelAttribute(name = "article") ArticleDTO articleDTO,
                           @RequestParam(defaultValue = "") String redirect,
                           Model model) {

        if (redirect.equals("write")) {
            return "/article/writeArticle";
        }

        //게시물id, 게시글 제목, 조회수, 좋아요 수 증가
        List<ArticlesDTO> articlesDTOS = articleService.view();
        model.addAttribute("articles", articlesDTOS);

        return "/article/articles";
    }


    //게시글 하나 출력
    @GetMapping("/article/{articleId}")
    public String article(@ModelAttribute(name = "article") ArticleDTO articleDTO,
                          @ModelAttribute(name = "comment") CommentDTO commentDTO,
                          @PathVariable Long articleId,
                          @RequestParam(defaultValue = "") String redirect,
                          Model model) {

        //게시글 내용에 관한 것
        ArticleDTO article = articleService.findArticleByArticleId(articleId);
        model.addAttribute("article", article);

        //좋아요 갯수
        Long countLikeArticle = likeArticleService.countLikeArticle(articleId);
        model.addAttribute("countLikeArticle", countLikeArticle);

        //댓글
        List<CommentDTO> commentDTOS = commentService.printCommentsAll(articleId);
        model.addAttribute("comments", commentDTOS);

        //조회 수 증가
        articleService.incrementArticleViewCount(articleId);

        List<Image> images = fileService.showImages(articleId);
        model.addAttribute("images", images);

        if (redirect.equals("modify")) {
            return "/article/modifyArticle";
        }

        return "/article/article";
    }

    @ResponseBody
    @GetMapping("/images/{filename}")
    public Resource imageView(@PathVariable String filename) throws MalformedURLException {
        return new UrlResource("file:" + fileStore.getFullPath(filename));
    }



    //게시글 쓰기
    @PostMapping("/article")
    public String writeArticle(@ModelAttribute(name = "article") ArticleDTO articleDTO,
                               @ModelAttribute UploadImagesForm uploadImagesForm) throws IOException {

        String articleTitle = articleDTO.getTitle();
        String articleContent = articleDTO.getContent();
        String articleCategory = articleDTO.getCategory();

        //게시글 저장
        Long articleId = articleService.saveArticle(articleTitle, articleContent, articleCategory);

        //이미지 저장
        fileService.saveImage(articleId, uploadImagesForm);

        return "redirect:/article";
    }

    //게시글 삭제
    //Delete에서 redirect 어떻게 하는지 모르겠음
    //그리고
    @DeleteMapping("/article/{articleId}")
    public String deleteArticle(@PathVariable Long articleId) {

        fileService.deleteImageByArticleId(articleId);
        commentService.deleteCommentByArticleId(articleId);
        likeArticleService.deleteLikeArticleByArticleId(articleId);
        articleService.deleteArticleByArticleId(articleId);

        return "redirect:/article";
    }

    @PostMapping("/article/{articleId}")
    public String modifyArticle(@ModelAttribute(name = "article") ArticleDTO articleDTO,
                                @PathVariable Long articleId) {

        articleService.updateArticle(articleId, articleDTO);

        return "redirect:/article/{articleId}";
    }

    //왜 21번 반복하지?
    //게시글 좋아요
    @PatchMapping("/article/{articleId}")
    public String clickLikeArticle(@PathVariable Long articleId) {
        likeArticleService.saveLikeArticleByArticleId(articleId);

        return "redirect:/article/{articleId}";
    }

    //댓글 추가
    @PostMapping("/article/{articleId}/comment")
    public String writeComment(@ModelAttribute(name = "comment") CommentDTO commentDTO,
            @PathVariable Long articleId) {
        
        commentService.saveComment(articleId, commentDTO);

        return "redirect:/article/{articleId}";
    }


    //댓글 삭제'
    //왜 널값이 들어가지?
    @DeleteMapping("/article/{articleId}/comment/{commentId}")
    public String deleteComment(@PathVariable Long articleId,
                                @PathVariable Long commentId) {

        commentService.deleteCommentByCommentId(commentId);

        return "redirect:/article/{articleId}";
    }

    //제목으로 게시글 검색
    @GetMapping("/article/search")
    public String articleByName(@ModelAttribute ArticlesDTO articlesDTO,
                                @ModelAttribute(name = "article") ArticleDTO articleDTO,
                                @RequestParam String q,
                                Model model) {


        List<ArticlesDTO> articlesDTOS = articleService.orSearchByArticleString(q);
        model.addAttribute("articles", articlesDTOS);

        return "/article/articles";
    }

}
