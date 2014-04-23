package gcom.arrecadacao.bean;

import gcom.arrecadacao.ArrecadadorMovimento;
import gcom.arrecadacao.banco.Banco;


/**
 * Classe que ir� auxiliar no formato do retorno da pesquisa da gera��o
 * de Movimento de D�bito Autom�tico para o Banco
 * 
 * [UC0319] - Gerar Movimento de D�bito Autom�tico 
 *            para o Banco 
 *
 * @author S�vio Luiz
 * @date 25/04/2006
 */
public class GerarMovimentoDebitoAutomaticoBancoHelper {
	
	private Banco banco;
	private ArrecadadorMovimento arrecadadorMovimento;
	private String descricaoEmail;
	private String situacaoEnvioEmail;
	private String ocorrencia;
	public ArrecadadorMovimento getArrecadadorMovimento() {
		return arrecadadorMovimento;
	}
	public void setArrecadadorMovimento(ArrecadadorMovimento arrecadadorMovimento) {
		this.arrecadadorMovimento = arrecadadorMovimento;
	}
	public Banco getBanco() {
		return banco;
	}
	public void setBanco(Banco banco) {
		this.banco = banco;
	}
	public String getDescricaoEmail() {
		return descricaoEmail;
	}
	public void setDescricaoEmail(String descricaoEmail) {
		this.descricaoEmail = descricaoEmail;
	}
	public String getOcorrencia() {
		return ocorrencia;
	}
	public void setOcorrencia(String ocorrencia) {
		this.ocorrencia = ocorrencia;
	}
	public String getSituacaoEnvioEmail() {
		return situacaoEnvioEmail;
	}
	public void setSituacaoEnvioEmail(String situacaoEnvioEmail) {
		this.situacaoEnvioEmail = situacaoEnvioEmail;
	}

	

}
