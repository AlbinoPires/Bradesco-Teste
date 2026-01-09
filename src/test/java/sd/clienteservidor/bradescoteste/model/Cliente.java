package sd.clienteservidor.bradescoteste.model;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class ClienteDTOTest {

    @Test
    void deveSetarEObterCamposClienteV1() {
        ClienteV1 dto = new ClienteV1();

        dto.setNome("Albino");
        dto.setCnpj(12345678910110L);
        dto.setTelefone(1199999999);
        dto.setValorPago(BigDecimal.valueOf(100));

        assertEquals("Albino", dto.getNome());
        assertEquals(12345678910110L, dto.getCnpj());
        assertEquals(1199999999, dto.getTelefone());
        assertEquals(BigDecimal.valueOf(100), dto.getValorPago());
    }

    @Test
    void devePermitirCamposNulosClienteV1() {
        ClienteV1 dto = new ClienteV1();

        dto.setNome(null);
        dto.setValorPago(null);

        assertNull(dto.getNome());
        assertNull(dto.getValorPago());
        assertEquals(0L, dto.getCnpj());
        assertEquals(0, dto.getTelefone());
    }

    @Test
    void deveSetarEObterCamposClienteV2() {
        ClienteV2 dto = new ClienteV2();

        dto.setNome("Albino");
        dto.setCnpj("12AEDW78911111");
        dto.setTelefone(1199999999);
        dto.setValorPago(BigDecimal.valueOf(200));

        assertEquals("Albino", dto.getNome());
        assertEquals("12AEDW78911111", dto.getCnpj());
        assertEquals(1199999999, dto.getTelefone());
        assertEquals(BigDecimal.valueOf(200), dto.getValorPago());
    }

    @Test
    void devePermitirCamposNulosClienteV2() {
        ClienteV2 dto = new ClienteV2();

        dto.setNome(null);
        dto.setCnpj(null);
        dto.setValorPago(null);

        assertNull(dto.getNome());
        assertNull(dto.getCnpj());
        assertNull(dto.getValorPago());
        assertEquals(0, dto.getTelefone());
    }
}
