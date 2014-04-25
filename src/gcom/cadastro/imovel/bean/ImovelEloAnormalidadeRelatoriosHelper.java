package gcom.cadastro.imovel.bean;

import gcom.cadastro.imovel.ImovelEloAnormalidade;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Date;

/**
 * [CRC:1710] - Bot�es de imprimir nas abas de Consultar Imovel.<br/><br/>
 * 
 * Classe que servir� para exibir os dados dos Cadastros Ocorr�ncias
 * no RelatorioDadosComplementaresImovel.<br/><br/>
 *
 *Pode ser usada por qualquer outro relatorio desde
 *que n�o altere o que j� existe.
 *
 * @author Marlon Patrick
 * @since 23/09/2009
 */
public class ImovelEloAnormalidadeRelatoriosHelper {
	
	private ImovelEloAnormalidade imovelEloAnormalidade;

	public ImovelEloAnormalidade getImovelEloAnormalidade() {
		return imovelEloAnormalidade;
	}

	public void setImovelEloAnormalidade(ImovelEloAnormalidade cadastroOcorrencia) {
		this.imovelEloAnormalidade = cadastroOcorrencia;
	}
	
	public String getDescricaoEloAnormalidade() {		
		if(imovelEloAnormalidade!=null && imovelEloAnormalidade.getEloAnormalidade()!=null){
			return imovelEloAnormalidade.getEloAnormalidade().getDescricao();
		}
		return "";
	}

	public Date getDataOcorrencia() {
		if(imovelEloAnormalidade!=null){
			return imovelEloAnormalidade.getDataAnormalidade();
		}
		return null;
	}

	public InputStream getFotoOcorrencia() {
		if(imovelEloAnormalidade!=null && imovelEloAnormalidade.getFotoAnormalidade()!=null ){
			return new ByteArrayInputStream(imovelEloAnormalidade.getFotoAnormalidade());
		}
		return null;
	}	
}
