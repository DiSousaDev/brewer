package br.com.diego.brewer.storage;

import org.springframework.web.multipart.MultipartFile;

public interface FotoStorage {

	void salvar(String foto);
	
	String salvarTemporariamente(MultipartFile[] files);

	byte[] recuperarFotoTemporaria(String nome);

	byte[] recuperarFoto(String nome);
	
}
