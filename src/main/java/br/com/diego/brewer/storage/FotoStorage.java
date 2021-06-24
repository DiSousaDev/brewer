package br.com.diego.brewer.storage;

import org.springframework.web.multipart.MultipartFile;

public interface FotoStorage {

	String salvarTemporariamente(MultipartFile[] files);

	byte[] recuperarFotoTemporaria(String nome);
	
}
