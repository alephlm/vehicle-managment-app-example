package com.VehicleManagement.VehicleManagement;

import com.VehicleManagement.VehicleManagement.entities.Vehicle;
import com.VehicleManagement.VehicleManagement.repositories.VehicleRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.mockito.internal.verification.VerificationModeFactory.times;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class VehicleControllerTest {

    private static final ObjectMapper om = new ObjectMapper();

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private VehicleRepository mockRepository;

    @Before
    public void init() {
        Vehicle vehicle = new Vehicle(1L,"WW", "Golf", "GL", "DE", "1", "076HFGEU676G", Date.from(LocalDate.of(2019,01,01).atStartOfDay().toInstant(ZoneOffset.UTC)));
        when(mockRepository.findById(1L)).thenReturn(Optional.of(vehicle));
    }

    @Test
    public void get_vehicle_OK() throws Exception {

        mockMvc.perform(get("/vehicle/get/1")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", Matchers.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.brand", Matchers.is("WW")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.vin", Matchers.is("076HFGEU676G")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.date", Matchers.is("2019-01-01T00:00:00.000+0000")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.country", Matchers.is("DE")));

        Mockito.verify(mockRepository, times(1)).findById(1L);

    }

    @Test
    public void save_vehicle_CREATED() throws Exception {
        String json = "{\n" +
                "  \"brand\": \"Ford\",\n" +
                "  \"model\": \"Fiesta\",\n" +
                "  \"type\": \"TT\",\n" +
                "  \"country\": \"Brazil\",\n" +
                "  \"number\": \"19438\",\n" +
                "  \"vin\": \"HXD-4487\",\n" +
                "  \"date\": \"2016-06-08\"\n" +
                "}";
        mockMvc.perform(post("/vehicle/save")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isCreated());
    }

    @Test
    public void save_vehicle_BAD_REQUEST() throws Exception {
        String json = "{";
        mockMvc.perform(post("/vehicle/save")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void update_vehicle_CREATED() throws Exception {
        String json = "{\n" +
                "  \"id\": \"1\",\n" +
                "  \"brand\": \"Ford\",\n" +
                "  \"model\": \"Fiesta\",\n" +
                "  \"type\": \"TT\",\n" +
                "  \"country\": \"Brazil\",\n" +
                "  \"number\": \"19438\",\n" +
                "  \"vin\": \"HXD-4487\",\n" +
                "  \"date\": \"2016-06-08\"\n" +
                "}";

        mockMvc.perform(put("/vehicle/update")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isCreated());
    }

    @Test
    public void update_vehicle_BAD_REQUEST() throws Exception {
        String json = "{";

        mockMvc.perform(put("/vehicle/update")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void delete_vehicle_OK() throws Exception {

        mockMvc.perform(delete("/vehicle/delete/1")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void delete_vehicle_BAD_REQUEST() throws Exception {

        mockMvc.perform(delete("/vehicle/delete/22")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }
}
