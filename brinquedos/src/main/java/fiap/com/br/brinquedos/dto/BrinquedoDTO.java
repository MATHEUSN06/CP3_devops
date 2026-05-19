package fiap.com.br.brinquedos.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BrinquedoDTO {

    @NotBlank(message = "O nome do brinquedo é obrigatório.")
    @Size(max = 100, message = "O nome deve ter no máximo 100 caracteres.")
    private String nome;

    @NotBlank(message = "O tipo do brinquedo é obrigatório.")
    @Size(max = 50, message = "O tipo deve ter no máximo 50 caracteres.")
    private String tipo;

    @NotBlank(message = "A classificação etária é obrigatória.")
    @Size(max = 50, message = "A classificação deve ter no máximo 50 caracteres.")
    private String classificacao;

    @NotBlank(message = "O tamanho do brinquedo é obrigatório.")
    @Size(max = 20, message = "O tamanho deve ter no máximo 20 caracteres.")
    private String tamanho;

    @NotNull(message = "O preço é obrigatório.")
    @Positive(message = "O preço deve ser um valor positivo.")
    private Double preco;
}
