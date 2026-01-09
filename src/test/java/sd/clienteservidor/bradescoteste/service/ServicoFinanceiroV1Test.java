package sd.clienteservidor.bradescoteste.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import sd.clienteservidor.bradescoteste.DTO.ClienteV1DTO;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ServicoFinanceiroV1Test {

    private ServicoFinanceiroV1 service;

    @BeforeEach
    void setup() {
        service = new ServicoFinanceiroV1();
    }

    @Test
    void devePagarComSucessoEFormatarCnpj() {
        ClienteV1DTO dto = new ClienteV1DTO();
        dto.setNome("  Albino  "); // com espaços pra validar trim()
        dto.setValorPago(BigDecimal.valueOf(100.00));
        dto.setCnpj(123L); // deve virar 00000000000123
        dto.setTelefone(1199999999);

        var resultado = service.pagar(dto);

        assertNotNull(resultado);
        assertEquals("Pagamento V1 recebido com sucesso", resultado.getMensagem());
        assertEquals("00000000000123", resultado.getCnpjFormatado());
        assertEquals(BigDecimal.valueOf(100.00), resultado.getValorPago());

        // garante que entrou na lista
        List<ServicoFinanceiroV1.PagamentoV1> lista = service.listar();
        assertEquals(1, lista.size());

        var pagamento = lista.get(0);
        assertEquals("Albino", pagamento.getNome()); // trim aplicado
        assertEquals("00000000000123", pagamento.getCnpjFormatado());
        assertEquals(1199999999, pagamento.getTelefone());
        assertEquals(BigDecimal.valueOf(100.00), pagamento.getValorPago());
        assertNotNull(pagamento.getDataHora());
    }

    @Test
    void deveLancarExcecaoQuandoDtoForNulo() {
        var ex = assertThrows(IllegalArgumentException.class, () -> service.pagar(null));
        assertEquals("Body não pode ser nulo.", ex.getMessage());
    }

    @Test
    void deveLancarExcecaoQuandoNomeForNulo() {
        ClienteV1DTO dto = new ClienteV1DTO();
        dto.setNome(null);
        dto.setValorPago(BigDecimal.ONE);
        dto.setCnpj(1L);

        var ex = assertThrows(IllegalArgumentException.class, () -> service.pagar(dto));
        assertEquals("Nome é obrigatório.", ex.getMessage());
    }

    @Test
    void deveLancarExcecaoQuandoNomeForVazioOuEspacos() {
        ClienteV1DTO dto = new ClienteV1DTO();
        dto.setNome("   ");
        dto.setValorPago(BigDecimal.ONE);
        dto.setCnpj(1L);

        var ex = assertThrows(IllegalArgumentException.class, () -> service.pagar(dto));
        assertEquals("Nome é obrigatório.", ex.getMessage());
    }

    @Test
    void deveLancarExcecaoQuandoValorPagoForNulo() {
        ClienteV1DTO dto = new ClienteV1DTO();
        dto.setNome("Albino");
        dto.setValorPago(null);
        dto.setCnpj(1L);

        var ex = assertThrows(IllegalArgumentException.class, () -> service.pagar(dto));
        assertEquals("valorPago deve ser maior que zero.", ex.getMessage());
    }

    @Test
    void deveLancarExcecaoQuandoValorPagoForZero() {
        ClienteV1DTO dto = new ClienteV1DTO();
        dto.setNome("Albino");
        dto.setValorPago(BigDecimal.ZERO);
        dto.setCnpj(1L);

        var ex = assertThrows(IllegalArgumentException.class, () -> service.pagar(dto));
        assertEquals("valorPago deve ser maior que zero.", ex.getMessage());
    }

    @Test
    void deveLancarExcecaoQuandoValorPagoForNegativo() {
        ClienteV1DTO dto = new ClienteV1DTO();
        dto.setNome("Albino");
        dto.setValorPago(BigDecimal.valueOf(-1));
        dto.setCnpj(1L);

        var ex = assertThrows(IllegalArgumentException.class, () -> service.pagar(dto));
        assertEquals("valorPago deve ser maior que zero.", ex.getMessage());
    }

    @Test
    void deveLancarExcecaoQuandoCnpjForNegativo() {
        ClienteV1DTO dto = new ClienteV1DTO();
        dto.setNome("Albino");
        dto.setValorPago(BigDecimal.ONE);
        dto.setCnpj(-1L);

        var ex = assertThrows(IllegalArgumentException.class, () -> service.pagar(dto));
        assertEquals("CNPJ deve ter no máximo 14 dígitos numéricos.", ex.getMessage());
    }

    @Test
    void deveLancarExcecaoQuandoCnpjForMaiorQue14Digitos() {
        ClienteV1DTO dto = new ClienteV1DTO();
        dto.setNome("Albino");
        dto.setValorPago(BigDecimal.ONE);
        dto.setCnpj(100_000_000_000_000L); // 15 dígitos

        var ex = assertThrows(IllegalArgumentException.class, () -> service.pagar(dto));
        assertEquals("CNPJ deve ter no máximo 14 dígitos numéricos.", ex.getMessage());
    }
    @Test
    void listarDeveRetornarListaImutavel() {
        // lista começa vazia
        var lista = service.listar();
        assertNotNull(lista);
        assertTrue(lista.isEmpty());


        assertThrows(UnsupportedOperationException.class,
                () -> lista.add(null));
    }

    @Test
    void listarDeveRefletirPagamentosRealizados() {
        ClienteV1DTO dto1 = new ClienteV1DTO();
        dto1.setNome("A");
        dto1.setValorPago(BigDecimal.valueOf(10));
        dto1.setCnpj(1L);
        dto1.setTelefone(111);

        ClienteV1DTO dto2 = new ClienteV1DTO();
        dto2.setNome("B");
        dto2.setValorPago(BigDecimal.valueOf(20));
        dto2.setCnpj(2L);
        dto2.setTelefone(222);

        service.pagar(dto1);
        service.pagar(dto2);

        var lista = service.listar();
        assertEquals(2, lista.size());
        assertEquals("A", lista.get(0).getNome());
        assertEquals("B", lista.get(1).getNome());
    }
}
