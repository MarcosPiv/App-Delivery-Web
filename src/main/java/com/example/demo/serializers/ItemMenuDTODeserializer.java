package com.example.demo.serializers;

import com.example.demo.dto.BebidaDTO;
import com.example.demo.dto.ComidaDTO;
import com.example.demo.dto.ItemMenuDTO;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.JsonNode;

import java.io.IOException;

public class ItemMenuDTODeserializer extends JsonDeserializer<ItemMenuDTO> {
    @Override
    public ItemMenuDTO deserialize(JsonParser parser, DeserializationContext context) throws IOException {
        ObjectMapper mapper = (ObjectMapper) parser.getCodec();
        JsonNode node = parser.getCodec().readTree(parser);

        // Obtener el campo tipoItem desde categoria
        String tipoItem = node.get("categoria").get("tipoItem").asText();

        if ("Bebida".equalsIgnoreCase(tipoItem)) {
            return mapper.treeToValue(node, BebidaDTO.class);
        } else if ("Comida".equalsIgnoreCase(tipoItem)) {
            return mapper.treeToValue(node, ComidaDTO.class);
        }

        throw new IllegalArgumentException("Tipo desconocido: " + tipoItem);
    }
}
