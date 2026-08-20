package askme.web;

import askme.User;
import askme.data.PostRepository;
import askme.data.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(PostController.class)
class PostControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private UserRepository userRepository;

    @MockitoBean
    private PostRepository postRepository;

    @Test
    void testAddPost_Success() throws Exception {
        // Данные для теста
        Long userId = 1L;
        String postText = "Привет, это тестовый пост!";
        User mockUser = new User(); // Предположим, у User есть пустой конструктор

        // Настройка мока: когда ищем пользователя, возвращаем объект
        when(userRepository.findById(userId)).thenReturn(Optional.of(mockUser));

        // Выполнение POST запроса
        mockMvc.perform(post("/profile/{userId}", userId)
                        .param("text", postText))
                .andExpect(status().is3xxRedirection()) // Проверяем редирект
                .andExpect(redirectedUrl("/profile/" + userId));

        // Проверяем, что метод репозитория действительно вызывался
        verify(userRepository, times(1)).findById(userId);
    }

    @Test
    void testAddPost_UserNotFound() throws Exception {
        Long userId = 99L;

        // Настройка мока: пользователь не найден
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        // Проверяем, что выбрасывается исключение (Runtime)
        // В Spring MVC это обычно приводит к 500 ошибке, если нет HandlerExceptionResolver
        mockMvc.perform(post("/profile/{userId}", userId)
                        .param("text", "любой текст"))
                .andExpect(status().isInternalServerError());
    }
}