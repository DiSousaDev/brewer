package br.com.diego.brewer.storage;

import org.springframework.web.context.request.async.DeferredResult;
import org.springframework.web.multipart.MultipartFile;

import br.com.diego.brewer.dto.FotoDTO;

public class FotoStorageRunnable implements Runnable {

	private MultipartFile[] files;
	private DeferredResult<FotoDTO> resultado;
	
	public FotoStorageRunnable(MultipartFile[] files, DeferredResult<FotoDTO> resultado) {
		super();
		this.files = files;
		this.resultado = resultado;
	}

	@Override
	public void run() {
		System.out.println("Files: " + files[0].getSize());
		resultado.setResult(new FotoDTO(files[0].getOriginalFilename(), files[0].getContentType()));
	}

}
