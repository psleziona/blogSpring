//package com.example.blog.service;
//
//import com.example.blog.model.Article;
//import com.example.blog.model.Comment;
//import com.example.blog.model.Role;
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
//import org.springframework.data.domain.PageRequest;
//import org.springframework.data.domain.Pageable;
//import org.springframework.http.MediaType;
//import org.springframework.test.web.servlet.MockMvc;
//
//import java.time.LocalDateTime;
//import java.util.Collection;
//import java.util.Collections;
//import java.util.List;
//import java.util.Optional;
//
//import static org.hamcrest.Matchers.containsString;
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.Mockito.*;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
//import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
//
//@SpringBootTest
//@AutoConfigureMockMvc
//class UserServiceImplTest {
//    private final String apiPath = "/api/users";
//    @MockBean
//    private UserService mockUserService;
//    @Autowired
//    private MockMvc mockMvc;
//    private JacksonTester<User> jacksonTester;
//    private User user1;
//    private User user2;
//
//    @BeforeEach
//    void setUp() {
//        user1 = new User(1, "Adam","Nowak", "am@mail.pl", "pass", LocalDateTime.now(), true);
//        user2 = new User(2, "Krystyna","Gazowa", "kryska@gm.pl","qwer", LocalDateTime.now(), true);
//        ObjectMapper mapper = new ObjectMapper();
//        mapper.registerModule(new JavaTimeModule());
//        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
//        JacksonTester.initFields(this, mapper);
//    }
//
//    @Test
//    void getUser() throws Exception{
//        when(mockUserService.getUser(1)).thenReturn(Optional.of(user1));
//        mockMvc.perform(get(apiPath + "/{idUser}", user1.getIdUser()).accept(MediaType.APPLICATION_JSON))
//                .andDo(print())
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.idUser").value(user1.getIdUser()));
//        verify(mockUserService, times(1)).getUser(user1.getIdUser());
//        verifyNoMoreInteractions(mockUserService);
//    }
//
//    @Test
//    void getUserByEmail() throws Exception {
//        when(mockUserService.getUserByEmail("am@mail.pl")).thenReturn(Optional.of(user1));
//        mockMvc.perform(get(apiPath + "/email/{email}", user1.getEmail()).accept(MediaType.APPLICATION_JSON))
//                .andDo(print())
//                .andExpect(jsonPath("$.idUser").value(user1.getIdUser()));
//        verify(mockUserService, times(1)).getUserByEmail(user1.getEmail());
//        verifyNoMoreInteractions(mockUserService);
//    }
//
//    @Test
//    void getUsers() throws Exception{
//        Page<User> page = new PageImpl<>(List.of(user1, user2));
//        when(mockUserService.getUsers(any(Pageable.class))).thenReturn(page);
//        mockMvc.perform(get(apiPath).accept(MediaType.APPLICATION_JSON))
//                .andDo(print())
//                .andExpect(jsonPath("$.content.length()").value(2))
//                .andExpect(jsonPath("$.content[0].idUser").value(1));
//        verify(mockUserService, times(1)).getUsers(any(Pageable.class));
//        verifyNoMoreInteractions(mockUserService);
//    }
//
//    @Test
//    void searchByName() throws Exception {
//        Page<User> page = new PageImpl<>(Collections.singletonList(user2));
//        when(mockUserService.searchByName(contains("azow"), any(Pageable.class))).thenReturn(page);
//        mockMvc.perform(get(apiPath + "/name/{name}", "azow").accept(MediaType.APPLICATION_JSON))
//                .andDo(print())
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.content[0].idUser").value(user2.getIdUser()));
//        verify(mockUserService, times(1)).searchByName(contains("azow"), any(Pageable.class));
//        verifyNoMoreInteractions(mockUserService);
//    }
//
//    @Test
//    void setUser() throws Exception {
//        User user = new User(null, "Tomek", "Ato", "atto@o2.pl", "pass", LocalDateTime.now(), Role.Admin, true);
//        String jsonUser = jacksonTester.write(user).getJson();
//        user.setIdUser(3);
//        when(mockUserService.setUser(any(User.class))).thenReturn(user);
//        mockMvc.perform(post(apiPath).content(jsonUser).contentType(MediaType.APPLICATION_JSON))
//                .andDo(print())
//                .andExpect(status().isCreated())
//                .andExpect(header().string("location", containsString(apiPath + "/" + user.getIdUser())));
//    }
//}