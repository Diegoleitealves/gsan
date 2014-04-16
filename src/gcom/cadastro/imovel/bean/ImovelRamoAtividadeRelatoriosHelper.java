package gcom.cadastro.imovel.bean;

import gcom.cadastro.imovel.ImovelRamoAtividade;

/**
 * [CRC:1710] - Bot�es de imprimir nas abas de Consultar Imovel.<br/><br/>
 * 
 * Classe que servir� para exibir os dados dos Cadastros Ocorr�ncias
 * no RelatorioDadosComplementaresImovel.<br/><br/>
 * 
 * OBS: Pode ser usada em qualquer relatorio desde que ele n�o altere o que j� existe.
 *
 * @author Marlon Patrick
 * @since 23/09/2009
 */
public class ImovelRamoAtividadeRelatoriosHelper {
	
	private ImovelRamoAtividade imovelRamoAtividade;

	public ImovelRamoAtividade getImovelRamoAtividade() {
		return imovelRamoAtividade;
	}

	public void setImovelRamoAtividade(ImovelRamoAtividade cadastroOcorrencia) {
		this.imovelRamoAtividade = cadastroOcorrencia;
	}
	
	public String getIdRamoAtividade() {		
		if(imovelRamoAtividade!=null && imovelRamoAtividade.getComp_id()!=null
				&& imovelRamoAtividade.getComp_id().getRamo_atividade()!=null){
			
			return String.valueOf(imovelRamoAtividade.getComp_id().getRamo_atividade().getId());
		}

		return "";
	}

	public String getDescricaoRamoAtividade() {		
		if(imovelRamoAtividade!=null && imovelRamoAtividade.getComp_id()!=null
				&& imovelRamoAtividade.getComp_id().getRamo_atividade()!=null){
			
			return imovelRamoAtividade.getComp_id().getRamo_atividade().getDescricao();
		}

		return "";
	}

}
