package gcom.gui.cadastro.atualizacaocadastral;

import java.util.HashMap;

import org.apache.struts.action.ActionForm;

public class ExibirAnaliseSituacaoArquivoAtualizacaoCadastralActionForm extends ActionForm{

	private static final long serialVersionUID = -5025032611669050238L;

	public static String TOTAL_IMOVEIS = "Total de im�veis";
	public static String TRANSMITIDOS = "Im�veis transmitidos";
	public static String APROVADOS = "Im�veis aprovados";
	public static String ANORMALIDADE = "Im�veis com anormalidade";
	public static String ALTERACAO_HIDROMETRO = "Im�veis com altera��o de hidr�metro";
	public static String ALTERACAO_LIGACAO_AGUA = "Im�veis com altera��o de liga��o de �gua";
	public static String ALTERACAO_LIGACAO_ESGOTO = "Im�veis com altera��o de liga��o de esgoto";
	public static String ALTERACAO_CATEGORIA_SUB_ECONOMIAS = "Im�veis com altera��o de categoria/subcategoria/qtd de economias";

	public HashMap<String, Integer> mapDadosAnalise;
	
	
	public ExibirAnaliseSituacaoArquivoAtualizacaoCadastralActionForm() {
		this.mapDadosAnalise = new HashMap<String, Integer>();
	}
	
	public HashMap<String, Integer> getMapDadosAnalise() {
		return mapDadosAnalise;
	}

	public void setMapDadosAnalise(HashMap<String, Integer> mapAlteracaoCategoriaSubcategoriaEconomias) {
		this.mapDadosAnalise = mapAlteracaoCategoriaSubcategoriaEconomias;
	}
	
	public Integer getTotalImoveis() {
		return mapDadosAnalise.get(TOTAL_IMOVEIS);
	}
	
	public Integer getImoveisTransmitidos() {
		return mapDadosAnalise.get(TRANSMITIDOS);
	}
	
	public Integer getImoveisAprovados() {
		return mapDadosAnalise.get(APROVADOS);
	}
	
	public Integer getImoveisComAnormalidade() {
		return mapDadosAnalise.get(ANORMALIDADE);
	}
	
	public Integer getImoveisComAlteracaoHidrometro() {
		return mapDadosAnalise.get(ALTERACAO_HIDROMETRO);
	}
	
	public Integer getImoveisComAlteracaoLigacaoAgua() {
		return mapDadosAnalise.get(ALTERACAO_LIGACAO_AGUA);
	}
	
	public Integer getImoveisComAlteracaoLigacaoEsgoto() {
		return mapDadosAnalise.get(ALTERACAO_LIGACAO_ESGOTO);
	}

	public Integer getImoveisComAlteracaoCategoriaSubEconomias() {
		return mapDadosAnalise.get(ALTERACAO_CATEGORIA_SUB_ECONOMIAS);
	}
	
	
}
