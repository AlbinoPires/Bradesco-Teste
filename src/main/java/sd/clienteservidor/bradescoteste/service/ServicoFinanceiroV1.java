package sd.clienteservidor.bradescoteste.service;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import sd.clienteservidor.bradescoteste.DTO.ClienteV1DTO;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Component("servicoV1")
public class ServicoFinanceiroV1 implements IService<ClienteV1DTO, ServicoFinanceiroV1.ResultadoPagamentoV1, ServicoFinanceiroV1.PagamentoV1> {

    private final List<PagamentoV1> pagamentos = new ArrayList<>();

    @Override
    public ResultadoPagamentoV1 pagar(ClienteV1DTO dto) {

        if (dto == null) throw new IllegalArgumentException("Body não pode ser nulo.");
        if (dto.getNome() == null || dto.getNome().trim().isEmpty())
            throw new IllegalArgumentException("Nome é obrigatório.");

        if (dto.getValorPago() == null || dto.getValorPago().compareTo(BigDecimal.ZERO) <= 0)
            throw new IllegalArgumentException("valorPago deve ser maior que zero.");

        long cnpj = dto.getCnpj();
        if (cnpj < 0 || cnpj > 99_999_999_999_999L)
            throw new IllegalArgumentException("CNPJ deve ter no máximo 14 dígitos numéricos.");

        String cnpjFormatado = String.format("%014d", cnpj);

        PagamentoV1 pagamento = new PagamentoV1(
                dto.getNome().trim(),
                cnpjFormatado,
                dto.getTelefone(),
                dto.getValorPago(),
                LocalDateTime.now()
        );

        pagamentos.add(pagamento);

        return new ResultadoPagamentoV1(
                "Pagamento V1 recebido com sucesso",
                cnpjFormatado,
                dto.getValorPago()
        );
    }

    @Override
    public List<PagamentoV1> listar() {
        return Collections.unmodifiableList(pagamentos);
    }

    public static class PagamentoV1 {
        private final String nome;
        private final String cnpjFormatado;
        private final int telefone;
        private final BigDecimal valorPago;
        private final LocalDateTime dataHora;

        public PagamentoV1(String nome, String cnpjFormatado, int telefone, BigDecimal valorPago, LocalDateTime dataHora) {
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

    public static class ResultadoPagamentoV1 {
        private final String mensagem;
        private final String cnpjFormatado;
        private final BigDecimal valorPago;

        public ResultadoPagamentoV1(String mensagem, String cnpjFormatado, BigDecimal valorPago) {
            this.mensagem = mensagem;
            this.cnpjFormatado = cnpjFormatado;
            this.valorPago = valorPago;
        }

        public String getMensagem() { return mensagem; }
        public String getCnpjFormatado() { return cnpjFormatado; }
        public BigDecimal getValorPago() { return valorPago; }
    }
}
