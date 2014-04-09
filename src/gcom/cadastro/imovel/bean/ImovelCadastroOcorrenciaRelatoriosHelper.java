package gcom.cadastro.imovel.bean;

import gcom.cadastro.imovel.ImovelCadastroOcorrencia;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Date;

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
public class ImovelCadastroOcorrenciaRelatoriosHelper {
	
	private ImovelCadastroOcorrencia imovelCadastroOcorrencia;

	public ImovelCadastroOcorrencia getImovelCadastroOcorrencia() {
		return imovelCadastroOcorrencia;
	}

	public void setImovelCadastroOcorrencia(ImovelCadastroOcorrencia cadastroOcorrencia) {
		this.imovelCadastroOcorrencia = cadastroOcorrencia;
	}
	
	public String getDescricaoCadastroOcorrencia() {		
		if(imovelCadastroOcorrencia!=null && imovelCadastroOcorrencia.getCadastroOcorrencia()!=null){
			return imovelCadastroOcorrencia.getCadastroOcorrencia().getDescricao();
		}
		return "";
	}

	public Date getDataOcorrencia() {
		if(imovelCadastroOcorrencia!=null){
			return imovelCadastroOcorrencia.getDataOcorrencia();
		}
		return null;
	}

	public InputStream getFotoOcorrencia() {
		if(imovelCadastroOcorrencia!=null && imovelCadastroOcorrencia.getFotoOcorrencia()!=null ){
			return new ByteArrayInputStream(imovelCadastroOcorrencia.getFotoOcorrencia());
		}
		return null;
	}	
}
