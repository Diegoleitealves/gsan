package gcom.gui.seguranca.acesso;

import org.apache.struts.action.*;
import javax.servlet.http.*;
import org.apache.struts.validator.ValidatorForm;


/**
 * Esse action form manipula os dados da p�gina de alterar a senha do usu�rio 
 *
 * @author Pedro Alexandre
 * @date 07/07/2006
 */
public class EfetuarAlteracaoSenhaSimplificadaActionForm extends ValidatorForm {
    private String senha;
    private String lembreteSenha;
    private String novaSenha;
    private String confirmacaoNovaSenha;
    private static final long serialVersionUID = 1L;
	/**
	 * @return Retorna o campo senha.
	 */
	public String getSenha() {
		return senha;
	}


	/**
	 * @param senha O senha a ser setado.
	 */
	public void setSenha(String senha) {
		this.senha = senha;
	}


	/**
     * <<Descri��o do m�todo>>
     *
     * @param actionMapping       Descri��o do par�metro
     * @param httpServletRequest  Descri��o do par�metro
     */
    public void reset(ActionMapping actionMapping, HttpServletRequest httpServletRequest) {
        senha = null;
    }

    /**
	 * @return Retorna o campo confirmacaoNovaSenha.
	 */
	public String getConfirmacaoNovaSenha() {
		return confirmacaoNovaSenha;
	}


	/**
	 * @param confirmacaoNovaSenha O confirmacaoNovaSenha a ser setado.
	 */
	public void setConfirmacaoNovaSenha(String confirmacaoNovaSenha) {
		this.confirmacaoNovaSenha = confirmacaoNovaSenha;
	}


	/**
	 * @return Retorna o campo lembreteSenha.
	 */
	public String getLembreteSenha() {
		return lembreteSenha;
	}


	/**
	 * @param lembreteSenha O lembreteSenha a ser setado.
	 */
	public void setLembreteSenha(String lembreteSenha) {
		this.lembreteSenha = lembreteSenha;
	}


	/**
	 * @return Retorna o campo novaSenha.
	 */
	public String getNovaSenha() {
		return novaSenha;
	}


	/**
	 * @param novaSenha O novaSenha a ser setado.
	 */
	public void setNovaSenha(String novaSenha) {
		this.novaSenha = novaSenha;
	}
}
