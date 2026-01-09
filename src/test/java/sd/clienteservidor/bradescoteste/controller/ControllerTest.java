package sd.clienteservidor.bradescoteste.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import sd.clienteservidor.bradescoteste.DTO.ClienteV1DTO;
import sd.clienteservidor.bradescoteste.DTO.ClienteV2DTO;
import sd.clienteservidor.bradescoteste.service.IService;
import sd.clienteservidor.bradescoteste.service.ServicoFinanceiroV1;
import sd.clienteservidor.bradescoteste.service.ServicoFinanceiroV2;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ControllerV1Test {

    @InjectMocks
    private Controller controller;
    @Mock
    private IService<ClienteV1DTO, ServicoFinanceiroV1.ResultadoPagamentoV1, ServicoFinanceiroV1.PagamentoV1> servicoV1;

    @Mock
    private IService<ClienteV2DTO, ServicoFinanceiroV2.ResultadoPagamentoV2, ServicoFinanceiroV2.PagamentoV2> servicoV2;

    @BeforeEach
    void setup() {
        controller = new Controller(servicoV1, servicoV2);
    }

    @Test
    void devePagarV1ComSucesso() {
        ClienteV1DTO dto = new ClienteV1DTO();
        dto.setNome("Albino");
        dto.setValorPago(BigDecimal.valueOf(100.0));

        var resultadoMock =
                new ServicoFinanceiroV1.ResultadoPagamentoV1(
                        "OK", "12345678910110", BigDecimal.valueOf(100.0)
                );

        when(servicoV1.pagar(Mockito.any(ClienteV1DTO.class)))
                .thenReturn(resultadoMock);


        var response = controller.pagarV1(dto);

        assertEquals(HttpStatus.CREATED.value(), response.getStatusCode().value());
        System.out.println("Retorno Status: " + response.getStatusCode().value() + " - OK");

        assertNotNull(response.getBody());

        var body = (ServicoFinanceiroV1.ResultadoPagamentoV1) response.getBody();
        assertEquals("OK", body.getMensagem());
        assertEquals(BigDecimal.valueOf(100.0), body.getValorPago());

        verify(servicoV1).pagar(Mockito.any(ClienteV1DTO.class));
        verifyNoInteractions(servicoV2);
    }

    @Test
    void devePropagarExcecaoNoPagarV1QuandoServiceFalhar() {
        ClienteV1DTO dto = new ClienteV1DTO();

        when(servicoV1.pagar(any(ClienteV1DTO.class)))
                .thenThrow(new IllegalArgumentException("CNPJ inválido"));

        assertThrows(IllegalArgumentException.class, () -> controller.pagarV1(new ClienteV1DTO()));

        verify(servicoV1).pagar(any(ClienteV1DTO.class));
        verifyNoInteractions(servicoV2);

    }


    @Test
    void devePropagarExcecaoNoPagarV2QuandoServiceFalhar() {
        ClienteV2DTO dto = new ClienteV2DTO();

        when(servicoV2.pagar(any(ClienteV2DTO.class)))
                .thenThrow(new IllegalArgumentException("CNPJ inválido"));

        var ex = assertThrows(IllegalArgumentException.class, () -> controller.pagarV2(dto));
        assertEquals("CNPJ inválido", ex.getMessage());

        verify(servicoV2, times(1)).pagar(dto);
        verifyNoInteractions(servicoV1);
    }


    @Test
    void devePagarV2ComSucesso() {
        ClienteV2DTO dto = new ClienteV2DTO();

        var resultadoMock = new ServicoFinanceiroV2.ResultadoPagamentoV2(
                "OK", "ABCD1234EFGH56", BigDecimal.valueOf(200.0)
        );

        when(servicoV2.pagar(any(ClienteV2DTO.class))).thenReturn(resultadoMock);

        var response = controller.pagarV2(dto);

        assertEquals(201, response.getStatusCodeValue());
        assertNotNull(response.getBody());

        var body = (ServicoFinanceiroV2.ResultadoPagamentoV2) response.getBody();
        assertEquals("OK", body.getMensagem());
        assertEquals(BigDecimal.valueOf(200.0), body.getValorPago());

        verify(servicoV2, times(1)).pagar(dto);
        verifyNoMoreInteractions(servicoV2);
        verifyNoInteractions(servicoV1);
    }


    @Test
    void deveListarV2ComSucesso() {
        var pagamentosMock = List.of(
                new ServicoFinanceiroV2.PagamentoV2 (
                        "Fulano",
                        "12345678912345",
                        1199999999,
                        BigDecimal.valueOf(200.0),
                        LocalDateTime.now()

                ),
                new ServicoFinanceiroV2.PagamentoV2 (
                        "Jones",
                        "WXYZ9876LMNO54",
                        1188888888,
                        BigDecimal.valueOf(150.0),
                        LocalDateTime.now()
                )
        );

        when(servicoV2.listar()).thenReturn(pagamentosMock);

        var response = controller.listarV2();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(2, response.getBody().size());

        verify(servicoV2, times(1)).listar();
        verifyNoMoreInteractions(servicoV2);
        verifyNoInteractions(servicoV1);
    }



    @Test
    void deveListarV1ComSucesso() {
        var pagamentosMock = List.of(
                new ServicoFinanceiroV1.PagamentoV1(
                        "Fulano", "12345678912345", 1199999999,
                        BigDecimal.valueOf(200.0), LocalDateTime.now()
                ),
                new ServicoFinanceiroV1.PagamentoV1(
                        "Jones", "WXYZ9876LMNO54", 1188888888,
                        BigDecimal.valueOf(150.0), LocalDateTime.now()
                )
        );

        when(servicoV1.listar()).thenReturn(pagamentosMock);

        var response = controller.listarV1(); // <- aqui!

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(2, response.getBody().size());

        verify(servicoV1).listar();
        verifyNoMoreInteractions(servicoV1);
        verifyNoInteractions(servicoV2);
    }






}