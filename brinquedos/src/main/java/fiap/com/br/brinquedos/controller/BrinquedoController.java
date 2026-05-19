package fiap.com.br.brinquedos.controller;

import fiap.com.br.brinquedos.dto.BrinquedoDTO;
import fiap.com.br.brinquedos.exception.BrinquedoNotFoundException;
import fiap.com.br.brinquedos.model.Brinquedo;
import fiap.com.br.brinquedos.repository.BrinquedoRepository;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/brinquedos")
public class BrinquedoController {

    private final BrinquedoRepository repository;

    public BrinquedoController(BrinquedoRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public ResponseEntity<List<Brinquedo>> listarTodos() {
        List<Brinquedo> brinquedos = repository.findAll();
        return ResponseEntity.ok(brinquedos);
    }


    @GetMapping("/{id}")
    public ResponseEntity<Brinquedo> buscarPorId(@PathVariable Long id) {
        Brinquedo brinquedo = repository.findById(id)
                .orElseThrow(() -> new BrinquedoNotFoundException(id));
        return ResponseEntity.ok(brinquedo);
    }

    @PostMapping
    public ResponseEntity<Brinquedo> criar(@RequestBody @Valid BrinquedoDTO dto) {
        Brinquedo brinquedo = converterDtoParaEntidade(dto);
        Brinquedo salvo = repository.save(brinquedo);
        return ResponseEntity.status(HttpStatus.CREATED).body(salvo);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Brinquedo> atualizar(
            @PathVariable Long id,
            @RequestBody @Valid BrinquedoDTO dto) {

        Brinquedo existente = repository.findById(id)
                .orElseThrow(() -> new BrinquedoNotFoundException(id));

        existente.setNome(dto.getNome());
        existente.setTipo(dto.getTipo());
        existente.setClassificacao(dto.getClassificacao());
        existente.setTamanho(dto.getTamanho());
        existente.setPreco(dto.getPreco());

        Brinquedo atualizado = repository.save(existente);
        return ResponseEntity.ok(atualizado);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        if (!repository.existsById(id)) {
            throw new BrinquedoNotFoundException(id);
        }
        repository.deleteById(id);
        return ResponseEntity.noContent().build(); // HTTP 204
    }

    private Brinquedo converterDtoParaEntidade(BrinquedoDTO dto) {
        Brinquedo brinquedo = new Brinquedo();
        brinquedo.setNome(dto.getNome());
        brinquedo.setTipo(dto.getTipo());
        brinquedo.setClassificacao(dto.getClassificacao());
        brinquedo.setTamanho(dto.getTamanho());
        brinquedo.setPreco(dto.getPreco());
        return brinquedo;
    }
}
