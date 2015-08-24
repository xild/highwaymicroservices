package br.com.highwaynetwork.exception;

/**
 * @author luis
 * 
 */
public class ExceptionMessageDTO {
    
    private String info;
    private TipoMensagemEnum tipo;
    
    public ExceptionMessageDTO(){
        
    }
    
    public ExceptionMessageDTO(String info, TipoMensagemEnum tipo) {
        this.info = info;
        this.tipo = tipo;
    }
    
    public String getInfo() {
        return info;
    }
    public void setInfo(String info) {
        this.info = info;
    }
    public TipoMensagemEnum getTipo() {
        return tipo;
    }
    public void setTipo(TipoMensagemEnum tipo) {
        this.tipo = tipo;
    }
    
}
