package gcom.gui.cadastro.atualizacaocadastral;

import java.util.HashMap;

public class ExibirAnaliseSituacaoArquivoAtualizacaoCadastralActionForm {

	public static String TOTAL_IMOVEIS = "Total de im�veis";
	public static String TRANSMITIDOS = "Im�veis transmitidos";
	public static String APROVADOS = "Im�veis aprovados";
	public static String ANORMALIDADE = "Im�veis com anormalidade";
	public static String ALTERACAO_HIDROMETRO = "Im�veis com altera��o de hidr�metro";
	public static String ALTERACAO_LIGACAO_AGUA = "Im�veis com altera��o de liga��o de �gua";
	public static String ALTERACAO_LIGACAO_ESGOTO = "Im�veis com altera��o de liga��o de esgoto";
	public static String ALTERACAO_CATEGORIA_SUB_ECONOMIAS = "Im�veis com altera��o de categoria/subcategoria/qtd de economias";

	public HashMap<String, Integer> mapDadosAnalise;
	
	public HashMap<String, Integer> getMapDadosAnalise() {
		return mapDadosAnalise;
	}

	public void setMapDadosAnalise(HashMap<String, Integer> mapAlteracaoCategoriaSubcategoriaEconomias) {
		this.mapDadosAnalise = mapAlteracaoCategoriaSubcategoriaEconomias;
	}
	
}
