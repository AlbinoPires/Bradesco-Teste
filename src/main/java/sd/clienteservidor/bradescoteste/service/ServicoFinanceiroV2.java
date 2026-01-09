package sd.clienteservidor.bradescoteste.service;

import org.springframework.stereotype.Component;
import sd.clienteservidor.bradescoteste.DTO.ClienteV2DTO;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Component("servicoV2")
public class ServicoFinanceiroV2
        implements IService<ClienteV2DTO, ServicoFinanceiroV2.ResultadoPagamentoV2, ServicoFinanceiroV2.PagamentoV2> {

    private final List<PagamentoV2> pagamentos = new ArrayList<>();

    @Override
    public ResultadoPagamentoV2 pagar(ClienteV2DTO dto) {

        if (dto == null) {
            throw new IllegalArgumentException("Body não pode ser nulo.");
        }

        if (dto.getNome() == null || dto.getNome().trim().isEmpty()) {
            throw new IllegalArgumentException("Nome é obrigatório.");
        }

        if (dto.getValorPago() == null || dto.getValorPago().compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("valorPago deve ser maior que zero.");
        }


        String cnpj = (dto.getCnpj() == null) ? null : dto.getCnpj().trim();

        if (cnpj == null || cnpj.isEmpty()) {
            throw new IllegalArgumentException("CNPJ é obrigatório.");
        }


        if (!cnpj.matches("^[A-Za-z0-9]{14}$")) {
            throw new IllegalArgumentException(
                    "CNPJ deve conter exatamente 14 caracteres alfanuméricos."
            );
        }



        String cnpjFormatado = cnpj;

        PagamentoV2 pagamento = new PagamentoV2(
                dto.getNome().trim(),
                cnpjFormatado,
                dto.getTelefone(),
                dto.getValorPago(),
                LocalDateTime.now()
        );

        pagamentos.add(pagamento);

        return new ResultadoPagamentoV2(
                "Pagamento V2 recebido com sucesso",
                cnpjFormatado,
                dto.getValorPago()
        );
    }

    @Override
    public List<PagamentoV2> listar() {
        return Collections.unmodifiableList(pagamentos);
    }

    public static class PagamentoV2 {
        private final String nome;
        private final String cnpjFormatado;
        private final int telefone;
        private final BigDecimal valorPago;
        private final LocalDateTime dataHora;

        public PagamentoV2(String nome, String cnpjFormatado, int telefone, BigDecimal valorPago, LocalDateTime dataHora) {
            this.nome = nome;
            this.cnpjFormatado = cnpjFormatado;
            this.telefone = telefone;
            this.valorPago = valorPago;
            this.dataHora = dataHora;
        }

        public String getNome() { return nome; }
        public String getCnpjFormatado() { return cnpjFormatado; }
        public int getTelefone() { return telefone; }
        public BigDecimal getValorPago() { return valorPago; }
        public LocalDateTime getDataHora() { return dataHora; }
    }

    public static class ResultadoPagamentoV2 {
        private final String mensagem;
        private final String cnpjFormatado;
        private final BigDecimal valorPago;

        public ResultadoPagamentoV2(String mensagem, String cnpjFormatado, BigDecimal valorPago) {
            this.mensagem = mensagem;
            this.cnpjFormatado = cnpjFormatado;
            this.valorPago = valorPago;
        }

        public String getMensagem() { return mensagem; }
        public String getCnpjFormatado() { return cnpjFormatado; }
        public BigDecimal getValorPago() { return valorPago; }
    }
}
