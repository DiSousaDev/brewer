package br.com.diego.brewer.storage.local;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.UUID;

import org.slf4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import br.com.diego.brewer.storage.FotoStorage;

@Component
public class FotoStorageLocal implements FotoStorage {

	private static final Logger LOG = org.slf4j.LoggerFactory.getLogger(FotoStorageLocal.class);
	
	private Path local;
	private Path localTemporario;
	
	public FotoStorageLocal() {
		this.local = FileSystems.getDefault().getPath(System.getenv("USER"), ".brewerfotos");
		criarPastas();
	}

	@Override
	public String salvarTemporariamente(MultipartFile[] files) {
		String novoNome = null;
		if(files != null && files.length > 0) {
			MultipartFile arquivo = files[0];
			novoNome = renomearArquivo(arquivo.getOriginalFilename());
			try {
				arquivo.transferTo(new File(this.localTemporario.toAbsolutePath().toString() + FileSystems.getDefault().getSeparator() + novoNome));
			} catch (IOException e) {
				throw new RuntimeException("Erro ao salvar arquivo na pasta temporária." + e);
			} 
		}
		return novoNome;
	}
	
	@Override
	public byte[] recuperarFotoTemporaria(String nome) {
		try {
			return Files.readAllBytes(this.localTemporario.resolve(nome));
		} catch (IOException e) {
			throw new RuntimeException("Erro ao ler foto temporária.", e);
		}
	}
	

	private void criarPastas() {
		try {
			Files.createDirectories(this.local);
			this.localTemporario = FileSystems.getDefault().getPath(this.local.toString(), "temp");
			Files.createDirectories(localTemporario);
			
			if(LOG.isDebugEnabled()) {
				LOG.debug("Pastas criadas para salvar fotos");
				LOG.debug("Pasta default: " + this.local.toAbsolutePath());
				LOG.debug("Pasta temporária: " + this.localTemporario.toAbsolutePath());
			}
		} catch (Exception e) {
			throw new RuntimeException("Erro ao criar pasta para salvar fotos", e);
		}
	}
	
	private String renomearArquivo(String nomeOriginal) {
		return UUID.randomUUID().toString() + "_" + nomeOriginal;
	}

}
