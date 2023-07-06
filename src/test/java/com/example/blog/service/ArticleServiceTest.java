package com.example.blog.service;

import com.example.blog.model.Article;
import com.example.blog.model.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

@SpringBootTest
@AutoConfigureMockMvc
class ArticleServiceTest {
    private final String apiPath = "/api/articles";
    @MockBean
    private ArticleService mockArticleService;
    @Autowired
    private MockMvc mockMvc;
    private JacksonTester<Article> jacksonTester;
    private User author;

    @BeforeEach
    void setUp() {
        author = new User(1, "Jacek","Placek", "jacek.placek@pl", "dupa1234", LocalDateTime.now(), true);
        ObjectMapper mapper = new ObjectMapper(); // ustawienie formatu daty i czasu w komunikatach
        mapper.registerModule(new JavaTimeModule()); // JSON-a dla zmiennych typu LocalDate i LocalDateTime
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        JacksonTester.initFields(this, mapper);
    }


    @Test
    void getArticle() throws Exception {
        Article article = new Article(2, "Test2", "Lorem ipsum", LocalDateTime.now(), author);
        when(mockArticleService.getArticle(article.getIdArticle())).thenReturn(Optional.of(article));
        mockMvc.perform(get(apiPath + "/{idArticle}", article.getIdArticle()).accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idArticle").value(article.getIdArticle()))
                .andExpect(jsonPath("$.title").value(article.getTitle()));
        verify(mockArticleService, times(1)).getArticle(article.getIdArticle());
        verifyNoMoreInteractions(mockArticleService);
    }

    @Test
    void getArticles() throws Exception {
        Article article = new Article(1, "Test", "Lorem ipsum", LocalDateTime.now(), author);
        Page<Article> page = new PageImpl<>(Collections.singletonList(article));
        when(mockArticleService.getArticles(any(Pageable.class))).thenReturn(page);
        mockMvc.perform(get(apiPath).contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[*]").exists())
                .andExpect(jsonPath("$.content.length()").value(1))
                .andExpect(jsonPath("$.content[0].idArticle").value(article.getIdArticle()))
                .andExpect(jsonPath("$.content[0].title").value(article.getTitle()));
        verify(mockArticleService, times(1)).getArticles(any(Pageable.class));
        verifyNoMoreInteractions(mockArticleService);
    }

    @Test
    void getUserArticles() throws Exception {
        Article article = new Article(3, "Test3", "Lorem ipsum", LocalDateTime.now(), author);
        Article article2 = new Article(4, "Test4", "Lorem ipsum", LocalDateTime.now(), author);
        List<Article> articles = List.of(article, article2);
        Page<Article> page = new PageImpl<>(articles);
        when(mockArticleService.getUserArticles(eq(author.getIdUser()), any(Pageable.class))).thenReturn(page);
        mockMvc.perform(get(apiPath + "/user/{idUser}", author.getIdUser()).contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[*]").exists())
                .andExpect(jsonPath("$.content.length()").value(2))
                .andExpect(jsonPath("$.content[0].idArticle").value(article.getIdArticle()))
                .andExpect(jsonPath("$.content[1].idArticle").value(article2.getIdArticle()));
        verify(mockArticleService, times(1)).getUserArticles(eq(author.getIdUser()), any(Pageable.class));
        verifyNoMoreInteractions(mockArticleService);
    }

    @Test
    void searchByTitle() throws Exception {
        Article article = new Article(5, "Test5", "Lorem ipsum", LocalDateTime.now(), author);
        Page<Article> page = new PageImpl<>(Collections.singletonList(article));
        when(mockArticleService.searchByTitle(contains("Test"), any(Pageable.class))).thenReturn(page);
        mockMvc.perform(get(apiPath + "/title/{title}", "Test").contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content.length()").value(1))
                .andExpect(jsonPath("$.content[0].idArticle").value(article.getIdArticle()));
        verify(mockArticleService, times(1)).searchByTitle(contains("Test"), any(Pageable.class));
        verifyNoMoreInteractions(mockArticleService);
    }

    @Test
    void searchByAuthorName() throws Exception{
        Article article = new Article(6, "Test6", "Lorem ipsum", LocalDateTime.now(), author);
        Page<Article> page = new PageImpl<>(Collections.singletonList(article));
        when(mockArticleService.searchByAuthorName(contains(author.getLastname()), any(Pageable.class))).thenReturn(page);
        mockMvc.perform(get(apiPath + "/author/{author}", author.getLastname()).contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content.length()").value(1))
                .andExpect(jsonPath("$.content[0].idArticle").value(article.getIdArticle()));
        verify(mockArticleService, times(1)).searchByAuthorName(contains("Placek"), any(Pageable.class));
        verifyNoMoreInteractions(mockArticleService);
    }

//    @Test
//    void searchByRating() {
//    }
//
//    @Test
//    void searchByPopularity() {
//    }

    @Test
    void setArticle() throws Exception{
        Article article = new Article(null, "Created", "Lorem ipsum", LocalDateTime.now(), author);
        String jsonArticle = jacksonTester.write(article).getJson();
        article.setIdArticle(1);
        when(mockArticleService.setArticle(any(Article.class))).thenReturn(article);
        mockMvc.perform(post(apiPath).content(jsonArticle).contentType(MediaType.APPLICATION_JSON).accept(MediaType.ALL))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(header().string("location", containsString(apiPath + "/" + article.getIdArticle())));
    }

//    @Test
//    void deleteArticle() {
//    }
}