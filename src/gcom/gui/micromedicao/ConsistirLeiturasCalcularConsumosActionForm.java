package gcom.gui.micromedicao;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

/**
 * < <Descri��o da Classe>>
 * 
 * @author Administrador
 */
public class ConsistirLeiturasCalcularConsumosActionForm extends ActionForm {
	private static final long serialVersionUID = 1L;
	private String idFaturamentoGrupo;

    /**
     * Seta o valor de idFaturamentoGrupo
     * 
     * @param idFaturamentoGrupo
     *            O novo valor de idFaturamentoGrupo
     */
    public void setIdFaturamentoGrupo(String idFaturamentoGrupo) {
        this.idFaturamentoGrupo = idFaturamentoGrupo;
    }

    /**
     * Retorna o valor de idFaturamentoGrupo
     * 
     * @return O valor de idFaturamentoGrupo
     */
    public String getIdFaturamentoGrupo() {
        return idFaturamentoGrupo;
    }

    /**
     * < <Descri��o do m�todo>>
     * 
     * @param actionMapping
     *            Descri��o do par�metro
     * @param httpServletRequest
     *            Descri��o do par�metro
     * @return Descri��o do retorno
     */
    public ActionErrors validate(ActionMapping actionMapping,
            HttpServletRequest httpServletRequest) {

        return null;
    }

    /**
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
