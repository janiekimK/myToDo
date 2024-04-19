package ch.kabashi.janie.MyToDo;

import com.fasterxml.jackson.databind.ObjectMapper;

import ch.kabashi.janie.MyToDo.employee.Employee;
import ch.kabashi.janie.MyToDo.employee.EmployeeRepository;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.json.JacksonJsonParser;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureDataJpa;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.client.RestTemplate;
import static org.hamcrest.Matchers.containsString;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@AutoConfigureMockMvc
@AutoConfigureDataJpa
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(false)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)

class RestControllerTests {
    @Autowired
    private MockMvc api;

    @Autowired
    private EmployeeRepository employeeRepository;

    @BeforeAll
    void setUp() {
        Employee objTest1 = new Employee();
        objTest1.setName("Mustermann");
        objTest1.setFirstName("Max");
        objTest1 = this.employeeRepository.save(objTest1);
        Assertions.assertNotNull(objTest1.getId());

        Employee objTest2 = new Employee();
        objTest2.setName("Musterfrau");
        objTest2.setFirstName("Maxine");
        objTest2 = this.employeeRepository.save(objTest2);
        Assertions.assertNotNull(objTest2.getId());
    }

    @Test
    @Order(1)
    void testGetEmployee() throws Exception {

        String accessToken = obtainAccessToken();

        api.perform(get("/api/employee").header("Authorization", "Bearer " + accessToken)
                        .with(csrf()))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(containsString("Max")));
    }

    @Test
    @Order(1)
    void testSaveEmployee() throws Exception {

        Employee employee = new Employee();

        employee.setName("Müller");
        employee.setFirstName("Stefan");

        String accessToken = obtainAccessToken();
        String body = new ObjectMapper().writeValueAsString(employee);

        api.perform(post("/api/employees")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body)
                        .header("Authorization", "Bearer " + accessToken)
                        .with(csrf()))
                .andDo(print()).andExpect(status().isOk())
                 .andExpect(content().string(containsString("Maxine")));
    }

    private String obtainAccessToken() {

        RestTemplate rest = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        String body = "client_id=mytodo&" +
                "grant_type=password&" +
                "scope=openid profile roles offline_access&" +
                "username=projektleiter&" +
                "password=projektleiter";

        HttpEntity<String> entity = new HttpEntity<>(body, headers);

        ResponseEntity<String> resp = rest.postForEntity("http://localhost:8080/realms/ILV/protocol/openid-connect/token", entity, String.class);

        JacksonJsonParser jsonParser = new JacksonJsonParser();
        return jsonParser.parseMap(resp.getBody()).get("access_token").toString();
    }
}
