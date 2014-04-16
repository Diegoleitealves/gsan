package gcom.arrecadacao.bean;

import java.io.Serializable;

/**
 * Objeto que pode ser um dos 4 SF desses dependendo do tipo de pagamento
 * 
 * @author S�vio Luiz
 * @created 30 de Janeiro de 2006 
 * [UC0264] - Distribuir Dados do C�digo de Barras. 
 * [SF0001] - Distribuir Pagamento de Conta 
 * [SF0002] - Distribuir Pagamento de Guia de Pagamento 
 * [SF0003] - Distribuir Pagamento de Documento de Cobram�a 
 * [SF0004] - Distribuir Pagamento de Fatura do Cliente Respons�vel
 */
public class RegistroHelperCodigoBarrasTipoPagamento implements Serializable {
	
	private static final long serialVersionUID = 1L;

	public RegistroHelperCodigoBarrasTipoPagamento() {
	}

	// campos do idPagamento que pode ser de um dos 4 SF,como podem ser campos
	// diferentes
	// dependendo do SF ent�o tem esses n�meros
	private String idPagamento1;

	private String idPagamento2;

	private String idPagamento3;

	private String idPagamento4;

	private String idPagamento5;

	private String idPagamento6;
	
	/*---------------------------------------------------------------------------------------------------------------
	 * [UC0264] - Distribuir Dados do C�digo de Barras
	 * - Caso o tipo de pagamento(campo G05.8) corresponda a Conta(valor = 3).
	 *   .idPagamento1 = C�digo da Localidade.
	 *   .idPagamento2 = M�tricula do Im�vel.
	 *   .idPagamento3 = N�o utilizado.
	 *   .idPagamento4 = M�s e Ano de Refer�ncia da Conta (MMAAAA).
	 *   .idPagamento5 = Digito Verificador da Conta no modulo 10.
	 *   .idPagamento6 = N�o Utilizado.
	 *   
	 * - Caso o tipo de pagamento(campo G05.8) corresponda a Guia Pagamento(valor = 4)
	 *   .idPagamento1 = C�digo da Localidade.
	 *   .idPagamento2 = M�tricula do Im�vel.
	 *   .idPagamento3 = N�o utilizado.
	 *   .idPagamento4 = C�digo do tipo do d�bito(DBTP_ID).
	 *   .idPagamento5 = Ano da Emiss�o da Guia de Pagamento(AAAA).
	 *   .idPagamento6 = N�o Utilizado.
	 * 
	 * - Caso o tipo de pagamento(campo G05.8) corresponda a Documento de Cobran�a(valor = 5)
	 *   .idPagamento1 = C�digo da Localidade.
	 *   .idPagamento2 = M�tricula do Im�vel.
	 *   .idPagamento3 = Sequencia do Documento de Cobran�a.
	 *   .idPagamento4 = C�digo do tipo de Documento(DOTP_ID).
	 *   .idPagamento5 = N�o Utilizado.
	 * 
	 * - Caso o tipo de pagamento(campo G05.8) corresponda a Fatura do Cliente Respons�vel (valor = 7)
	 *   .idPagamento1 = N�o Utilizado.
	 *   .idPagamento2 = C�digo do Cliente Respons�vel(CLIE_ID).
	 *   .idPagamento3 = N�o Utilizado.
	 *   .idPagamento4 = M�s e Ano de Refer�ncia da Conta (MMAAAA).
	 *   .idPagamento5 = Digito Verificador da Conta no modulo 10.
	 *   .idPagamento6 = Sequencial da Fatura do Cliente Respons�vel.
	 *
	 * - Identifica��o da Empresa for CAER (campo G05.6 = 0004)
	 * 
	 * 		.idPagamento1 = Tipo do Documento (G05.7.1).
	 * 		.idPagamento2 = Identifica��o.
	 *
	 * 		- Caso o tipo de pagamento(campo G05.7.1) corresponda a Conta e Segunda Via (valor = 1)
	 *   		.idPagamento3 = Matricula do Imovel (IMOV_ID).
	 *   		.idPagamento4 = Ano e M�s de Refer�ncia da Conta (AAAAMM).
	 *   		.idPagamento5 = Codigo Origem da Conta.
	 *   		.idPagamento6 = Numero do Documento + campo G05.8.  
	 * 		
	 * 		- Caso o tipo de pagamento(campo G05.7.1) corresponda a Fatura (valor = 2)
	 *   		.idPagamento3 = Qualifica��o.
	 *   		.idPagamento4 = Ano e M�s de Refer�ncia da Conta (AAAAMM).
	 *   		.idPagamento5 = Numero do Documento + campo G05.8.   
	 * 		
	 * 		- Caso o tipo de pagamento(campo G05.7.1) corresponda a Reaviso de Debito (valor = 3)
	 *   		.idPagamento3 = Matricula do Imovel (IMOV_ID).
	 *   		.idPagamento4 = Numero do Documento + campo G05.8. 
	 *   		    
	 *   
	 -----------------------------------------------------------------------------------------------------------------*/

	public String getIdPagamento1() {
		return idPagamento1;
	}

	public void setIdPagamento1(String idPagamento1) {
		this.idPagamento1 = idPagamento1;
	}

	public String getIdPagamento2() {
		return idPagamento2;
	}

	public void setIdPagamento2(String idPagamento2) {
		this.idPagamento2 = idPagamento2;
	}

	public String getIdPagamento3() {
		return idPagamento3;
	}

	public void setIdPagamento3(String idPagamento3) {
		this.idPagamento3 = idPagamento3;
	}

	public String getIdPagamento4() {
		return idPagamento4;
	}

	public void setIdPagamento4(String idPagamento4) {
		this.idPagamento4 = idPagamento4;
	}

	public String getIdPagamento5() {
		return idPagamento5;
	}

	public void setIdPagamento5(String idPagamento5) {
		this.idPagamento5 = idPagamento5;
	}

	public String getIdPagamento6() {
		return idPagamento6;
	}

	public void setIdPagamento6(String idPagamento6) {
		this.idPagamento6 = idPagamento6;
	}

}
