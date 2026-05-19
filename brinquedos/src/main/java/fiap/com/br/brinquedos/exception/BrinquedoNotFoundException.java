package fiap.com.br.brinquedos.exception;

public class BrinquedoNotFoundException extends RuntimeException {

    public BrinquedoNotFoundException(Long id) {
        super("Brinquedo com ID " + id + " não encontrado.");
    }
}
