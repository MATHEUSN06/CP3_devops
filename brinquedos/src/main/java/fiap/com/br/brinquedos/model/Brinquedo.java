package fiap.com.br.brinquedos.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "TDS_TB_Brinquedos")
public class Brinquedo {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "brinquedo_seq")
    @SequenceGenerator(
            name = "brinquedo_seq",
            sequenceName = "SEQ_TDS_BRINQUEDO",
            allocationSize = 1
    )
    @Column(name = "id")
    private Long id;

    @Column(name = "nome", nullable = false, length = 100)
    private String nome;

    @Column(name = "tipo", nullable = false, length = 50)
    private String tipo;

    @Column(name = "classificacao", nullable = false, length = 50)
    private String classificacao;

    @Column(name = "tamanho", nullable = false, length = 20)
    private String tamanho;

    @Column(name = "preco", nullable = false)
    private Double preco;
}
