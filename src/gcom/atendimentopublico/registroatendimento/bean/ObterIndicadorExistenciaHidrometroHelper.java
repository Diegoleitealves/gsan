package gcom.atendimentopublico.registroatendimento.bean;


/**
 * Esta classe tem por finalidade facilitar a forma como ser� retornado o resultado do 
 * [UC0409] Obter Indicador de Exist�ncia de Hidr�metro na Liga�a� de �gua e no Po�o
 * 
 * @author Ana Maria
 * @date 14/08/2006
 */
public class ObterIndicadorExistenciaHidrometroHelper {
	
	private Short indicadorLigacaoAgua;
	
	private Short indicadorPoco;
	
	public ObterIndicadorExistenciaHidrometroHelper(){}

	public Short getIndicadorLigacaoAgua() {
		return indicadorLigacaoAgua;
	}

	public void setIndicadorLigacaoAgua(Short indicadorLigacaoAgua) {
		this.indicadorLigacaoAgua = indicadorLigacaoAgua;
	}

	public Short getIndicadorPoco() {
		return indicadorPoco;
	}

	public void setIndicadorPoco(Short indicadorPoco) {
		this.indicadorPoco = indicadorPoco;
	}


}
