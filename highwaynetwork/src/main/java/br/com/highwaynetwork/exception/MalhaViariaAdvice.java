package br.com.highwaynetwork.exception;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author luis
 *
 */
@ControllerAdvice
public class MalhaViariaAdvice {
    
    @Autowired
    private MessageSource msgSource;

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ExceptionMessageDTO processValidationError(MethodArgumentNotValidException ex) {
      BindingResult result = ex.getBindingResult();
      FieldError error = result.getFieldError();

      return processFieldError(error);
    }

    /**
     * @param error 
     * @return ExceptionMessageDTO usando resources bundle
     */
    private ExceptionMessageDTO processFieldError(FieldError error) {
      ExceptionMessageDTO msgException = null;
      if (error != null) {
        Locale currentLocale = LocaleContextHolder.getLocale();
        String msg = msgSource.getMessage(error.getDefaultMessage(), null, currentLocale);
        msgException = new ExceptionMessageDTO(msg, TipoMensagemEnum.ERROR);
      }
      return msgException;
    }
}
