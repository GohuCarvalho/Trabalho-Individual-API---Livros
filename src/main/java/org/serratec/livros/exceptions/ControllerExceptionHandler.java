package org.serratec.livros.exceptions;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ControllerExceptionHandler extends ResponseEntityExceptionHandler {

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatusCode status, WebRequest request) {
		
		List<String> erros = new ArrayList<>();
		for(FieldError er: ex.getBindingResult().getFieldErrors()) {
			String errorMessage = er.getField() + ": " + er.getDefaultMessage();
			erros.add(errorMessage);
		}
		
		ErroResposta erroResposta = new ErroResposta(status.value(), 
				"Existem Campos Inválidos. Confira o preenchimento", LocalDateTime.now(), erros);
		
		return super.handleExceptionInternal(ex, erroResposta, headers, status, request);
	}
	
	@Override
	protected ResponseEntity<Object> handleHttpMessageNotReadable(
	        HttpMessageNotReadableException ex,
	        HttpHeaders headers,
	        HttpStatusCode status,
	        WebRequest request) {

	    String mensagemAmigavel = "Erro no formato dos dados enviados. "
	            + "Verifique se os campos estão corretos e se o JSON está bem formatado.";

	    List<String> erros = new ArrayList<>();
	    erros.add(ex.getMostSpecificCause().getMessage());

	    ErroResposta erro = new ErroResposta(
	            HttpStatus.BAD_REQUEST.value(),
	            mensagemAmigavel,
	            LocalDateTime.now(),
	            erros
	    );

	    return new ResponseEntity<>(erro, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<Object> handleDataIntegrityViolation(DataIntegrityViolationException ex, WebRequest request) {
        

        String mensagemAmigavel = "Erro de violação de integridade de dados. "
                + "Um valor excede o limite do campo (Ex: campo 'autor' possui no máximo 25 caracteres) ou há conflito de chave única.";


        Throwable causaRaiz = ex.getRootCause();
        String detalheTecnico = (causaRaiz != null) ? causaRaiz.getMessage() : ex.getMessage();

        List<String> erros = new ArrayList<>();
        erros.add(detalheTecnico);

        HttpStatus status = HttpStatus.BAD_REQUEST; 

        ErroResposta erroResposta = new ErroResposta(
                status.value(),
                mensagemAmigavel,
                LocalDateTime.now(),
                erros
        );

        return new ResponseEntity<>(erroResposta, status);
    }
}
