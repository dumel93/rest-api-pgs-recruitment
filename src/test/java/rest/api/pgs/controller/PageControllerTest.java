package rest.api.pgs.controller;

import com.github.javafaker.Internet;
import com.github.javafaker.Stock;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import rest.api.pgs.entity.User;
import rest.api.pgs.rest.UserRestController;
import rest.api.pgs.service.UserService;

import static java.util.Collections.singletonList;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.hamcrest.core.Is.is;
import static org.mockito.BDDMockito.given;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.mockito.BDDMockito.given;

@RunWith(SpringRunner.class)
@WebMvcTest
public class PageControllerTest {
    @Autowired
    private MockMvc mockMvc;



    @MockBean
    private UserRestController userRestController;

    @Test
    public void findAll() throws Exception {
        // given
        User user = new User();
        user.setId(Long.valueOf(1));
        user.setFirstName("Damian");
        user.setLastName("Krawczyk");
        user.setEmail("irekfotel.g11@gmail.com");

        List<User> allArrivals = Collections.singletonList(user);

        given(userRestController.getAllEmployees(0,1,"id","asc")).willReturn((ResponseEntity<List<User>>) allArrivals);

        // when + then
        this.mockMvc.perform(get("http://localhost:8080/users/")
                .with(user("admin@wp.pl").password("admin123"))
                .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)));

    }

    @Test
    public void getUserById() throws Exception {
        User user = new User();
        user.setId(Long.valueOf(26));
        user.setFirstName("Damian");
        user.setLastName("Krawczyk");
        user.setEmail("irekfotel.g11@gmail.com");

        given(userRestController.findOne(user.getId())).willReturn(user);

        mockMvc.perform(get("http://localhost:8080/users/"+  user.getId())
                .with(user("admin@wp.pl").password("admin123"))
                .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("firstName", is(user.getFirstName())));
    }


}
