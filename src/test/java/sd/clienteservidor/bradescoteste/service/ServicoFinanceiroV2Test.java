package sd.clienteservidor.bradescoteste.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import sd.clienteservidor.bradescoteste.DTO.ClienteV2DTO;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class ServicoFinanceiroV2Test {

    private ServicoFinanceiroV2 service;

    @BeforeEach
    void setup() {
        service = new ServicoFinanceiroV2();
    }

    @Test
    void devePagarComSucesso() {
        ClienteV2DTO dto = new ClienteV2DTO();
        dto.setNome("  Albino  ");
        dto.setValorPago(BigDecimal.valueOf(100));
        dto.setCnpj("12AEDW78911111");
        dto.setTelefone(1199999999);

        var resultado = service.pagar(dto);

        assertNotNull(resultado);
        assertEquals("Pagamento V2 recebido com sucesso", resultado.getMensagem());
        assertEquals("12AEDW78911111", resultado.getCnpjFormatado());
        assertEquals(BigDecimal.valueOf(100), resultado.getValorPago());

        var lista = service.listar();
        assertEquals(1, lista.size());

        var pagamento = lista.get(0);
        assertEquals("Albino", pagamento.getNome());
        assertEquals("12AEDW78911111", pagamento.getCnpjFormatado());
        assertEquals(1199999999, pagamento.getTelefone());
        assertEquals(BigDecimal.valueOf(100), pagamento.getValorPago());
        assertNotNull(pagamento.getDataHora());
    }

    @Test
    void deveLancarExcecaoQuandoDtoForNulo() {
        var ex = assertThrows(IllegalArgumentException.class, () -> service.pagar(null));
        assertEquals("Body não pode ser nulo.", ex.getMessage());
    }

    @Test
    void deveLancarExcecaoQuandoNomeForNulo() {
        ClienteV2DTO dto = new ClienteV2DTO();
        dto.setNome(null);
        dto.setValorPago(BigDecimal.ONE);
        dto.setCnpj("12AEDW78911111");

        var ex = assertThrows(IllegalArgumentException.class, () -> service.pagar(dto));
        assertEquals("Nome é obrigatório.", ex.getMessage());
    }

    @Test
    void deveLancarExcecaoQuandoNomeForVazioOuEspacos() {
        ClienteV2DTO dto = new ClienteV2DTO();
        dto.setNome("   ");
        dto.setValorPago(BigDecimal.ONE);
        dto.setCnpj("12AEDW78911111");

        var ex = assertThrows(IllegalArgumentException.class, () -> service.pagar(dto));
        assertEquals("Nome é obrigatório.", ex.getMessage());
    }

    @Test
    void deveLancarExcecaoQuandoValorPagoForNulo() {
        ClienteV2DTO dto = new ClienteV2DTO();
        dto.setNome("Albino");
        dto.setValorPago(null);
        dto.setCnpj("12AEDW78911111");

        var ex = assertThrows(IllegalArgumentException.class, () -> service.pagar(dto));
        assertEquals("valorPago deve ser maior que zero.", ex.getMessage());
    }

    @Test
    void deveLancarExcecaoQuandoValorPagoForZero() {
        ClienteV2DTO dto = new ClienteV2DTO();
        dto.setNome("Albino");
        dto.setValorPago(BigDecimal.ZERO);
        dto.setCnpj("12AEDW78911111");

        var ex = assertThrows(IllegalArgumentException.class, () -> service.pagar(dto));
        assertEquals("valorPago deve ser maior que zero.", ex.getMessage());
    }

    @Test
    void deveLancarExcecaoQuandoValorPagoForNegativo() {
        ClienteV2DTO dto = new ClienteV2DTO();
        dto.setNome("Albino");
        dto.setValorPago(BigDecimal.valueOf(-1));
        dto.setCnpj("12AEDW78911111");

        var ex = assertThrows(IllegalArgumentException.class, () -> service.pagar(dto));
        assertEquals("valorPago deve ser maior que zero.", ex.getMessage());
    }

    @Test
    void deveLancarExcecaoQuandoCnpjForNulo() {
        ClienteV2DTO dto = new ClienteV2DTO();
        dto.setNome("Albino");
        dto.setValorPago(BigDecimal.ONE);
        dto.setCnpj(null);

        var ex = assertThrows(IllegalArgumentException.class, () -> service.pagar(dto));
        assertEquals("CNPJ é obrigatório.", ex.getMessage());
    }

    @Test
    void deveLancarExcecaoQuandoCnpjForNuloOuVazio() {
        ClienteV2DTO dto = new ClienteV2DTO();
        dto.setNome("Albino");
        dto.setValorPago(BigDecimal.ONE);
        dto.setCnpj("");

        var ex = assertThrows(IllegalArgumentException.class, () -> service.pagar(dto));
        assertEquals("CNPJ é obrigatório.", ex.getMessage());
    }



    @Test
    void deveLancarExcecaoQuandoCnpjNaoTiver14Caracteres() {
        ClienteV2DTO dto = new ClienteV2DTO();
        dto.setNome("Albino");
        dto.setValorPago(BigDecimal.ONE);
        dto.setCnpj("ABC123");

        var ex = assertThrows(IllegalArgumentException.class, () -> service.pagar(dto));
        assertEquals("CNPJ deve conter exatamente 14 caracteres alfanuméricos.", ex.getMessage());
    }

    @Test
    void listarDeveRetornarListaImutavel() {
        var lista = service.listar();
        assertNotNull(lista);
        assertTrue(lista.isEmpty());

        assertThrows(UnsupportedOperationException.class, () -> lista.add(null));
    }

    @Test
    void listarDeveRefletirPagamentosRealizados() {
        ClienteV2DTO dto1 = new ClienteV2DTO();
        dto1.setNome("A");
        dto1.setValorPago(BigDecimal.valueOf(10));
        dto1.setCnpj("AAAAAAAAAAAAAA");
        dto1.setTelefone(111);

        ClienteV2DTO dto2 = new ClienteV2DTO();
        dto2.setNome("B");
        dto2.setValorPago(BigDecimal.valueOf(20));
        dto2.setCnpj("BBBBBBBBBBBBBB");
        dto2.setTelefone(222);

        service.pagar(dto1);
        service.pagar(dto2);

        var lista = service.listar();
        assertEquals(2, lista.size());
        assertEquals("A", lista.get(0).getNome());
        assertEquals("B", lista.get(1).getNome());
    }
}
