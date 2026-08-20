package askme.web;

import askme.User;
import askme.data.PostRepository;
import askme.data.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserController.class) // Тестируем только этот контроллер
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc; // Инструмент для имитации HTTP-запросов

    @MockitoBean
    private UserRepository userRepository; // "Поддельный" репозиторий

    @MockitoBean
    private PostRepository postRepository;

    @Test
    void testShowProfileSuccess() throws Exception {
        // 1. Готовим данные
        User mockUser = new User();
        mockUser.setId(1L);
        mockUser.setUsername("testuser");
        mockUser.setPosts(Collections.emptyList());

        // Обучаем Mock: когда кто-то ищет ID 1, верни нашего mockUser
        when(userRepository.findById(1L)).thenReturn(Optional.of(mockUser));

        // 2. Выполняем запрос и проверяем результат
        mockMvc.perform(get("/profile/1"))
                .andExpect(status().isOk()) // Проверяем статус 200 OK
                .andExpect(view().name("profile")) // Проверяем, что вернули шаблон profile.html
                .andExpect(model().attributeExists("user")) // Проверяем наличие юзера в модели
                .andExpect(model().attribute("user", mockUser)); // Проверяем, что это тот самый юзер
    }

    @Test
    void testShowProfileNotFound() {
        // Имитируем ситуацию, когда пользователя нет в базе
        when(userRepository.findById(99L)).thenReturn(Optional.empty());

        // Говорим JUnit: "Мы ожидаем, что этот кусок кода выбросит Exception"
        Exception exception = org.junit.jupiter.api.Assertions.assertThrows(Exception.class, () -> {
            mockMvc.perform(get("/profile/99"));
        });

        // Проверяем, что внутри лежит именно наш RuntimeException с нужным текстом
        org.junit.jupiter.api.Assertions.assertTrue(exception.getCause() instanceof RuntimeException);
        org.junit.jupiter.api.Assertions.assertEquals("User not found", exception.getCause().getMessage());
    }
}