package com.gpms.petcare.client;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gpms.petcare.dto.localidadesAPIClient.EstadoDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;


@Component
public class LocalidadesAPIClient {

    @Autowired
    private RestTemplate restTemplate;

    public EstadoDTO[] getTodosEstados() {
        ResponseEntity<EstadoDTO[]> response =
                restTemplate.getForEntity(
                        "https://servicodados.ibge.gov.br/api/v1/localidades/estados",
                        EstadoDTO[].class);
        return response.getBody();

    }

    public List<Object> getCidadesPorEstado(Long ufId) throws JsonProcessingException {

        ObjectMapper mapper = new ObjectMapper();

        ResponseEntity<String> response =
                restTemplate.getForEntity(
                        "https://servicodados.ibge.gov.br/api/v1/localidades/estados/" + ufId + "/distritos",
                        String.class);

        String json = response.getBody();

        return Arrays.asList(mapper.readValue(json, Object[].class));

    }
}
