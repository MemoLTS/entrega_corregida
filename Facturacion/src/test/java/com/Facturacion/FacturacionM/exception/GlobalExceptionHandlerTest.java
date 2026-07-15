package com.Facturacion.FacturacionM.exception;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import com.facturacion.facturacionm.exception.BusinessException;
import com.facturacion.facturacionm.exception.GlobalExceptionHandler;
import com.facturacion.facturacionm.exception.ResourceNotFoundException;

class GlobalExceptionHandlerTest {

    private final GlobalExceptionHandler handler = new GlobalExceptionHandler();

    @Test
    void handleValidation_retorna400ConMensajesDeCampo() {
        FieldError fieldError = new FieldError("request", "descripcion", "La descripción es obligatoria");
        BindingResult bindingResult = mock(BindingResult.class);
        when(bindingResult.getFieldErrors()).thenReturn(List.of(fieldError));
        MethodArgumentNotValidException ex = mock(MethodArgumentNotValidException.class);
        when(ex.getBindingResult()).thenReturn(bindingResult);

        ResponseEntity<Map<String, String>> response = handler.handleValidation(ex);
        Map<String, String> body = response.getBody();

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNotNull(body);
        assertEquals("descripcion: La descripción es obligatoria", body.get("error"));
    }

    @Test
    void handleNotFound_retorna404ConMensaje() {
        ResourceNotFoundException ex = new ResourceNotFoundException("Factura no encontrada: 99");

        ResponseEntity<Map<String, String>> response = handler.handleNotFound(ex);
        Map<String, String> body = response.getBody();

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNotNull(body);
        assertEquals("Factura no encontrada: 99", body.get("error"));
    }

    @Test
    void handleBusiness_retorna400ConMensaje() {
        BusinessException ex = new BusinessException("El microservicio de usuarios no está disponible.");

        ResponseEntity<Map<String, String>> response = handler.handleBusiness(ex);
        Map<String, String> body = response.getBody();

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNotNull(body);
        assertEquals("El microservicio de usuarios no está disponible.", body.get("error"));
    }

    @Test
    void handleGeneral_retorna500ConMensajeGenerico() {
        Exception ex = new Exception("error inesperado");

        ResponseEntity<Map<String, String>> response = handler.handleGeneral(ex);
        Map<String, String> body = response.getBody();

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertNotNull(body);
        assertEquals("Error interno del servidor", body.get("error"));
    }
}
