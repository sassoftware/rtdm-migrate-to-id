package com.sas.rtdm2id.config;

import com.sas.rtdm2id.controller.ApplicationController;
import com.sas.rtdm2id.service.ApplicationService;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.HttpHeaders;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.options;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class WebCorsConfigurationTest {

    @Nested
    @WebMvcTest(ApplicationController.class)
    class Default {
        @Autowired
        private MockMvc mockMvc;

        @MockitoBean
        private ApplicationService service;

        @Test
        void rejectsCrossOriginPreflightWhenNoOriginsConfigured() throws Exception {
            mockMvc.perform(preflight("https://ui.example"))
                    .andExpect(header().doesNotExist(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN));
        }
    }

    @Nested
    @WebMvcTest(value = ApplicationController.class,
            properties = "rtdm2id.cors.allowed-origins=https://ui.example, https://other.example")
    class AllowList {
        @Autowired
        private MockMvc mockMvc;

        @MockitoBean
        private ApplicationService service;

        @Test
        void allowsConfiguredOrigin() throws Exception {
            mockMvc.perform(preflight("https://other.example"))
                    .andExpect(status().isOk())
                    .andExpect(header().string(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN, "https://other.example"));
        }

        @Test
        void rejectsOriginNotInAllowList() throws Exception {
            mockMvc.perform(preflight("https://evil.example"))
                    .andExpect(status().isForbidden())
                    .andExpect(header().doesNotExist(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN));
        }
    }

    private static org.springframework.test.web.servlet.RequestBuilder preflight(String origin) {
        return options("/api/rtdm2id/create-diagram")
                .header(HttpHeaders.ORIGIN, origin)
                .header(HttpHeaders.ACCESS_CONTROL_REQUEST_METHOD, "POST");
    }
}
