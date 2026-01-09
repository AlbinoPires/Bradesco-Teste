package sd.clienteservidor.bradescoteste.controller;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

import sd.clienteservidor.bradescoteste.DTO.ClienteV1DTO;
import sd.clienteservidor.bradescoteste.DTO.ClienteV2DTO;
import sd.clienteservidor.bradescoteste.service.IService;
import sd.clienteservidor.bradescoteste.service.ServicoFinanceiroV1;
import sd.clienteservidor.bradescoteste.service.ServicoFinanceiroV2;

import java.util.List;

@RestController
@RequestMapping("/api")
public class Controller {

    private final IService<ClienteV1DTO, ServicoFinanceiroV1.ResultadoPagamentoV1, ServicoFinanceiroV1.PagamentoV1> servicoV1;

    private final IService<ClienteV2DTO, ServicoFinanceiroV2.ResultadoPagamentoV2, ServicoFinanceiroV2.PagamentoV2> servicoV2;

    public Controller(
            @Qualifier("servicoV1")
            IService<ClienteV1DTO, ServicoFinanceiroV1.ResultadoPagamentoV1, ServicoFinanceiroV1.PagamentoV1> servicoV1,

            @Qualifier("servicoV2")
            IService<ClienteV2DTO, ServicoFinanceiroV2.ResultadoPagamentoV2, ServicoFinanceiroV2.PagamentoV2> servicoV2
    ) {
        this.servicoV1 = servicoV1;
        this.servicoV2 = servicoV2;
    }

    // ---------------- V1 ----------------

    @PostMapping("/v1/pagamentos")
    public ResponseEntity<?> pagarV1(@RequestBody ClienteV1DTO dto) {
        var resultado = servicoV1.pagar(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(resultado);
    }

    @GetMapping("/v1/pagamentos")
    public ResponseEntity<List<ServicoFinanceiroV1.PagamentoV1>> listarV1() {
        return ResponseEntity.ok(servicoV1.listar());
    }

    // ---------------- V2 ----------------

    @PostMapping("/v2/pagamentos")
    public ResponseEntity<?> pagarV2(@Valid @RequestBody ClienteV2DTO dto) {
        var resultado = servicoV2.pagar(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(resultado);
    }

    @GetMapping("/v2/pagamentos")
    public ResponseEntity<List<ServicoFinanceiroV2.PagamentoV2>> listarV2() {
        return ResponseEntity.ok(servicoV2.listar());
    }
}
