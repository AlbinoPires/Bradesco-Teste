package sd.clienteservidor.bradescoteste.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

import java.math.BigDecimal;

public class ClienteV2DTO {

    private String nome;

    @NotNull
    @NotBlank
    @Pattern(
            regexp = "^[A-Za-z0-9]{14}$",
            message = "CNPJ deve conter exatamente 14 caracteres alfanuméricos"
    )
    private String cnpj;


    private int telefone;

    private BigDecimal valorPago;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public int getTelefone() {
        return telefone;
    }

    public void setTelefone(int telefone) {
        this.telefone = telefone;
    }

    public BigDecimal getValorPago() {
        return valorPago;
    }

    public void setValorPago(BigDecimal valorPago) {
        this.valorPago = valorPago;
    }
}
