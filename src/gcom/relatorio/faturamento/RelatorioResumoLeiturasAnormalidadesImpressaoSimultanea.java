package gcom.relatorio.faturamento;

import gcom.relatorio.ConstantesRelatorios;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.tarefa.TarefaException;
import gcom.tarefa.TarefaRelatorio;
import gcom.util.agendadortarefas.AgendadorTarefas;


/**
 * 
 * Este caso de uso permite a inser��o de dados na tabela movimento 
 * conta pr�-faturada.
 *
 * [UC0923] Incluir Movimento Conta Pr�-Faturada
 *
 *
 *  Caso seja chamado por uma tela, o sistema gera uma tela de acordo com o 
 *  movimento, Caso contr�rio, o sistema gera um relat�rio e envia, por 
 *  e-mail para o operador, registrado com os seguintes campos:
 *  
 *  No cabe�alho imprimir o grupo de faturamento informado (FTGR_ID), o 
 *  c�digo e descri��o da empresa (EMPR_ID e EMPR_NMEMPRESA da tabela 
 *  EMPRESA com EMPR_ID da tabela ROTA com ROTA_ID da tabela QUADRA com 
 *  QDRA_ID da tabela IMOVEL com IMOV_ID=matr�cula do im�vel do primeiro 
 *  registro do arquivo que exista na tabela IMOVEL), o c�digo da localidade 
 *  e o t�tulo fixo �MOVIMENTO CELULAR - IMPRESS�O SIMULT�NEA� quando 
 *  processado o arquivo de movimento;
 *  
 *  Imprimir o erro correspondente encontrado no processamento do im�vel;
 *  
 *  Caso seja chamado por uma tela, imprimir um texto �Arquivo processado 
 *  com problema e enviado para opera��o para processar o movimento. 
 *  Localidade <<C�digo da Localidade>>�;    
 *
 * [SB0001] - Gera Tela Resumo das leituras e anormalidades da impress�o 
 * simult�nea com Problemas
 *
 * @author bruno
 * @date 30/06/2009
 *
 * @param colErrors 
 */ 
public class RelatorioResumoLeiturasAnormalidadesImpressaoSimultanea extends TarefaRelatorio {
    
	private static final long serialVersionUID = 1L;
	
	private byte[] relatorio = null; 
    
	public RelatorioResumoLeiturasAnormalidadesImpressaoSimultanea(Usuario usuario) {
		super(usuario, ConstantesRelatorios.RELATORIO_RESUMO_LEITURAS_ANORMALIDADE_IMPRESSAO_SIMULTANEA );
	}
	
	@Deprecated
	public RelatorioResumoLeiturasAnormalidadesImpressaoSimultanea() {
		super(null, "");
	}
	public Object executar() throws TarefaException {
        return relatorio;
	}

	@Override
	public int calcularTotalRegistrosRelatorio() {
		int retorno = 0;

        return retorno;
	}

	public void agendarTarefaBatch() {
		AgendadorTarefas.agendarTarefa("relatorioResumoLeiturasAnormalidadesRegistradas", this);
	}

	public byte[] getRelatorio() {
		return relatorio;
	}

	public void setRelatorio(byte[] relatorio) {
		this.relatorio = relatorio;
	}

}
