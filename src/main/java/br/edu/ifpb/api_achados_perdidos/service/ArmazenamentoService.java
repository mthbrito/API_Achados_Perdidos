package br.edu.ifpb.api_achados_perdidos.service;

import br.edu.ifpb.api_achados_perdidos.dto.UploadResponse;
import br.edu.ifpb.api_achados_perdidos.exception.ArquivoInvalidoException;
import jakarta.annotation.PostConstruct;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;
import java.util.UUID;

@Service
public class ArmazenamentoService {

    private static final Map<String, String> TIPOS_PERMITIDOS = Map.of(
            "image/jpeg", ".jpg",
            "image/png", ".png",
            "image/webp", ".webp"
    );

    @Value("${app.upload-dir:uploads}")
    private String diretorio;

    @Getter
    private Path pasta;

    @PostConstruct
    void iniciar() throws IOException {
        pasta = Path.of(diretorio).toAbsolutePath().normalize();
        Files.createDirectories(pasta);
    }

    public UploadResponse salvar(MultipartFile arquivo) {
        String extensao = extensaoValida(arquivo);
        String nome = UUID.randomUUID() + extensao;
        gravar(arquivo, nome);
        return new UploadResponse(montarUrl(nome));
    }

    private String extensaoValida(MultipartFile arquivo) {
        if (arquivo == null || arquivo.isEmpty()) {
            throw new ArquivoInvalidoException("Arquivo vazio");
        }
        String extensao = TIPOS_PERMITIDOS.get(arquivo.getContentType());
        if (extensao == null) {
            throw new ArquivoInvalidoException("Formato não suportado. Use JPG, PNG ou WEBP");
        }
        return extensao;
    }

    private void gravar(MultipartFile arquivo, String nome) {
        try {
            Files.copy(arquivo.getInputStream(), pasta.resolve(nome));
        } catch (IOException e) {
            throw new UncheckedIOException("Falha ao salvar a imagem", e);
        }
    }

    private String montarUrl(String nome) {
        return ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/imagens/")
                .path(nome)
                .toUriString();
    }
}