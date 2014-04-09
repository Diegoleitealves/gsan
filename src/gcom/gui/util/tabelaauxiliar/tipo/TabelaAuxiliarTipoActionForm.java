package gcom.gui.util.tabelaauxiliar.tipo;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionMapping;
import org.apache.struts.validator.ValidatorActionForm;

/**
 * < <Descri��o da Classe>>
 * 
 * @author Administrador
 */
public class TabelaAuxiliarTipoActionForm extends ValidatorActionForm {
    private String descricao;
    private static final long serialVersionUID = 1L;
    private String tipo;

    /**
     * Retorna o valor de descricao
     * 
     * @return O valor de descricao
     */
    public String getDescricao() {
        return descricao;
    }

    /**
     * Seta o valor de descricao
     * 
     * @param descricao
     *            O novo valor de descricao
     */
    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    /**
     * Retorna o valor de tipo
     * 
     * @return O valor de tipo
     */
    public String getTipo() {
        return tipo;
    }

    /**
     * Seta o valor de tipo
     * 
     * @param tipo
     *            O novo valor de tipo
     */
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    /***************************************************************************
     * < <Descri��o do m�todo>>
     * 
     * @param actionMapping
     *            Descri��o do par�metro
     * @param httpServletRequest
     *            Descri��o do par�metro
     */
    public void reset(ActionMapping actionMapping,
            HttpServletRequest httpServletRequest) {
    }
}
