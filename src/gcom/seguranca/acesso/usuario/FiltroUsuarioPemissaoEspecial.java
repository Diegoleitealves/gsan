package gcom.seguranca.acesso.usuario;

import gcom.util.filtro.Filtro;

import java.io.Serializable;

/**
 * 
 * @author S�vio Luiz
 */
public class FiltroUsuarioPemissaoEspecial extends Filtro implements
		Serializable {
	private static final long serialVersionUID = 1L;
	/**
	 * Constructor
	 */
	public FiltroUsuarioPemissaoEspecial() {
	}

	/**
	 * Constructor
	 * 
	 * @param campoOrderBy
	 *            Description of the Parameter
	 */
	public FiltroUsuarioPemissaoEspecial(String campoOrderBy) {
		this.campoOrderBy = campoOrderBy;
	}

	/**
	 * C�digo
	 */
	public final static String USUARIO_ID = "usuario.id";
	
	public final static String USUARIO = "usuario";
	
	/**
	 * C�digo
	 */
	public final static String USUARIO_COMP_ID = "comp_id.usuarioId";
	
	/**
	 * C�digo
	 */
	public final static String PERMISSAO_ESPECIAL_COMP_ID = "comp_id.permissaoEspecialId";
	
	public final static String PERMISSAO_ESPECIAL_ID = "permissaoEspecial.id";
	
	public final static String PERMISSAO_ESPECIAL_DESCRICAO = "permissaoEspecial.descricao";
	
	public final static String PERMISSAO_ESPECIAL = "permissaoEspecial";

	public final static String GRUPO_FUNCIONALIDADE_OPERACAO = "grupoFuncionalidadeOperacao";
	
}
