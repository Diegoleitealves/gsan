package gcom.gui.relatorio.micromedicao;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

/**
 * < <Descri��o da Classe>>
 * 
 * @author Administrador
 */
public class GerarRelatorioFaixasFalsasLeituraActionForm extends ActionForm {
	private static final long serialVersionUID = 1L;
	private String mesAnoReferencia;

	/**
	 * @return Retorna o campo mesAnoReferencia.
	 */
	public String getMesAnoReferencia() {
		return mesAnoReferencia;
	}

	/**
	 * @param mesAnoReferencia O mesAnoReferencia a ser setado.
	 */
	public void setMesAnoReferencia(String mesAnoReferencia) {
		this.mesAnoReferencia = mesAnoReferencia;
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
