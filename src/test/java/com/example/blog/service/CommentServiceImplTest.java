//package com.example.blog.service;
//
//import com.example.blog.model.Article;
//import com.example.blog.model.Comment;
//import com.example.blog.model.User;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.fasterxml.jackson.databind.SerializationFeature;
//import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.json.JacksonTester;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.PageImpl;
//import org.springframework.data.domain.Pageable;
//import org.springframework.http.MediaType;
//import org.springframework.test.web.servlet.MockMvc;
//
//import java.io.IOException;
//import java.time.LocalDateTime;
//import java.util.Collections;
//import java.util.List;
//import java.util.Optional;
//
//import static org.hamcrest.Matchers.containsString;
//import static org.mockito.Mockito.*;
//import static org.mockito.Mockito.verifyNoMoreInteractions;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
//import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
//
//@SpringBootTest
//@AutoConfigureMockMvc
//class CommentServiceImplTest {
//    private final String apiPath = "/api/comments";
//    @MockBean
//    private CommentService mockCommentService;
//    @Autowired
//    private MockMvc mockMvc;
//    private JacksonTester<Comment> jacksonTester;
//    private User author;
//    private Article article;
//    private Comment comment;
//    private Page<Comment> page;
//    @BeforeEach
//    void setUp() {
//        author = new User(1, "Jacek","Placek", "jacek.placek@pl", "dupa1234", LocalDateTime.now(), true);
//        article = new Article(1, "Test", "Lorem ipsum", LocalDateTime.now(), author);
//        author.setArticles(List.of(article));
//        comment = new Comment(1, "Komentarz", LocalDateTime.now(), author, article);
//        page = new PageImpl<>(Collections.singletonList(comment));
//        ObjectMapper mapper = new ObjectMapper();
//        mapper.registerModule(new JavaTimeModule());
//        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
//        JacksonTester.initFields(this, mapper);
//    }
//
//    @Test
//    void getComment() throws Exception {
//        when(mockCommentService.getComment(1)).thenReturn(Optional.of(comment));
//        mockMvc.perform(get(apiPath + "/{idComment}", comment.getIdComment()).accept(MediaType.APPLICATION_JSON))
//                .andDo(print())
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.idComment").value(comment.getIdComment()));
//        verify(mockCommentService, times(1)).getComment(comment.getIdComment());
//        verifyNoMoreInteractions(mockCommentService);
//    }
//
//    @Test
//    void getArticleComments() throws Exception {
//        when(mockCommentService.getArticleComments(eq(1), any(Pageable.class))).thenReturn(page);
//        mockMvc.perform(get(apiPath + "/article/{idArticle}", article.getIdArticle()).accept(MediaType.APPLICATION_JSON))
//                .andDo(print())
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.content[0].idComment").value(1));
//        verify(mockCommentService, times(1)).getArticleComments(eq(article.getIdArticle()), any(Pageable.class));
//        verifyNoMoreInteractions(mockCommentService);
//    }
//
//    @Test
//    void getUserComments() throws Exception {
//        when(mockCommentService.getUserComments(eq(1), any(Pageable.class))).thenReturn(page);
//        mockMvc.perform(get(apiPath + "/user/{idUser}", author.getIdUser()).accept(MediaType.APPLICATION_JSON))
//                .andDo(print())
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.content[0].idComment").value(1));
//        verify(mockCommentService, times(1)).getUserComments(eq(author.getIdUser()), any(Pageable.class));
//        verifyNoMoreInteractions(mockCommentService);
//    }
//
//    @Test
//    void setComment() throws Exception {
//        Comment comment1 = new Comment(null, "Created", LocalDateTime.now(), author, article);
//        String jsonComment = jacksonTester.write(comment1).getJson();
//        comment1.setIdComment(2);
//        when(mockCommentService.setComment(any(Comment.class))).thenReturn(comment1);
//        mockMvc.perform(post(apiPath).content(jsonComment).contentType(MediaType.APPLICATION_JSON))
//                .andDo(print())
//                .andExpect(status().isCreated())
//                .andExpect(header().string("location", containsString(apiPath + "/" + comment1.getIdComment())));
//    }
//}