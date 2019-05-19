package ru.churakov.graduation.web;

import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import ru.churakov.graduation.model.Menu;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.churakov.graduation.TestData.*;
import static ru.churakov.graduation.TestUtil.readListFromJsonMvcResult;
import static ru.churakov.graduation.TestUtil.userHttpBasic;

class MenuControllerTest extends AbstractControllerTest {
    private static final String REST_URL = MenuController.REST_URL;

    @Test
    void getAll() throws Exception {
        mockMvc.perform(get(REST_URL))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(result -> assertThat(readListFromJsonMvcResult(result, Menu.class))
                        .isEqualTo(List.of(MENU_100008, MENU_100009)));
    }

    @Test
    void getAllForDate() throws Exception {
        mockMvc.perform(get(REST_URL)
                .param("date","2018-12-11"))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(result -> assertThat(readListFromJsonMvcResult(result, Menu.class))
                        .isEqualTo(List.of(MENU_100006, MENU_100007)));
    }

    @Test
    void getAllAuthenticated() throws Exception {
        mockMvc.perform(get(REST_URL)
                .with(userHttpBasic(USER)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(result -> assertThat(readListFromJsonMvcResult(result, Menu.class))
                        .isEqualTo(List.of(MENU_100008, MENU_100009)));
    }

}