package gcom.gui.faturamento.conta;

import gcom.fachada.Fachada;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.util.filtro.ParametroSimples;

import gcom.cadastro.imovel.FiltroImovel;
import gcom.cadastro.imovel.Imovel;
import gcom.faturamento.credito.CreditoOrigem;
import gcom.faturamento.credito.CreditoRealizado;
import gcom.faturamento.credito.CreditoTipo;
import gcom.faturamento.credito.FiltroCreditoOrigem;
import gcom.faturamento.credito.FiltroCreditoTipo;

import gcom.cadastro.sistemaparametro.SistemaParametro;

import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.Vector;

import gcom.util.ConstantesSistema;
import gcom.util.Util;
import java.math.BigDecimal;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;


public class AdicionarCreditoRealizadoContaAction extends GcomAction {

    
    public ActionForward execute(ActionMapping actionMapping,
            ActionForm actionForm, HttpServletRequest httpServletRequest,
            HttpServletResponse httpServletResponse) {

        //Seta o mapeamento de retorno
        ActionForward retorno = actionMapping
                .findForward("exibirAdicionarCreditoRealizadoConta");

        Fachada fachada = Fachada.getInstancia();
        
        //Mudar isso quando tiver esquema de seguran�a
        HttpSession sessao = httpServletRequest.getSession(false);

        //Inst�ncia do formul�rio que est� sendo utilizado
        AdicionarCreditoRealizadoContaActionForm adicionarCreditoRealizadoContaActionForm = 
        (AdicionarCreditoRealizadoContaActionForm) actionForm;
        
        //Par�metros recebidos para adicionar um d�bito cobrado
        String creditoTipoID = adicionarCreditoRealizadoContaActionForm.getCreditoTipoID();
        String mesAnoCredito = adicionarCreditoRealizadoContaActionForm.getMesAnoCredito();
        String mesAnoCobranca = adicionarCreditoRealizadoContaActionForm.getMesAnoCobranca();
        String valorCredito = adicionarCreditoRealizadoContaActionForm.getValorCredito();
        String idImovelSelected = adicionarCreditoRealizadoContaActionForm.getImovelID();
        String creditoOrigemID = adicionarCreditoRealizadoContaActionForm.getCreditoOrigemID();
        String confirmado = httpServletRequest.getParameter("confirmado");
        
        //Gerando o objeto CreditoRealizado que ser� inserido na cole��o
        CreditoRealizado  objCreditoRealizado = new CreditoRealizado();
        objCreditoRealizado.setUltimaAlteracao(new Date());
        
        //Verifica��o da matr�cula do im�vel selecionado
        if (idImovelSelected == null || idImovelSelected.equalsIgnoreCase("")){
        	throw new ActionServletException(
                    "atencao.adicionar_debito_imovel_obrigatorio");
        }
        
        
        //Verifica��o do m�s e ano de d�bito
        if (mesAnoCredito != null && !mesAnoCredito.equalsIgnoreCase("")){
        	
        	//[FS0006] - Validar ano e m�s de refer�ncia
        	if (Util.validarAnoMes(mesAnoCredito)){
        		throw new ActionServletException(
                "atencao.adicionar_debito_ano_mes_referencia_invalido");
        	}
        	//[FS0004] - Verifica ano e m�s do faturamento
        	
        	//Invertendo para AnoM�s e retirando a barra
        	mesAnoCredito = Util.formatarMesAnoParaAnoMesSemBarra(mesAnoCredito);
        	
    		FiltroImovel filtroImovel = new FiltroImovel();
        	
        	//Objetos que ser�o retornados pelo hibernate
        	//filtroImovel.adicionarCaminhoParaCarregamentoEntidade("quadra.rota.faturamentoGrupo.anoMesReferencia");
            filtroImovel.adicionarCaminhoParaCarregamentoEntidade("quadra.rota.faturamentoGrupo");
        	
        	filtroImovel.adicionarParametro(new ParametroSimples(FiltroImovel.ID, idImovelSelected));
        	
        	Collection colecaoImovel = fachada.pesquisar(filtroImovel, Imovel.class.getName());
        	
        	if (colecaoImovel == null || colecaoImovel.isEmpty()){
        		throw new ActionServletException(
                "atencao.adicionar_credito_ano_mes_debito_invalido");
        	}
        	
    		Imovel objImovel = (Imovel) Util.retonarObjetoDeColecao(colecaoImovel);
    		Integer mesAnoFaturamentoImovel = objImovel.getQuadra().getRota().getFaturamentoGrupo().getAnoMesReferencia();
    	
    		if (!compararAnoMesReferencia(mesAnoFaturamentoImovel, new Integer(mesAnoCredito), ">")){
    			throw new ActionServletException(
                "atencao.adicionar_credito_ano_mes_debito_invalido");
    		}
			
	        Integer qtdContas = fachada.pesquisarQuantidadeContasEContasHistorico(objImovel.getId(), new Integer(mesAnoCredito));
			
			// verifica se o valor da devolucao � maior que o valor da guia da devolucao
			if (qtdContas == null || qtdContas == 0) {
			
				if (confirmado == null || !confirmado.trim().equalsIgnoreCase("ok")){
					httpServletRequest.setAttribute("caminhoActionConclusao",
							"/gsan/adicionarCreditoRealizadoContaAction.do");
					// Monta a p�gina de confirma��o para perguntar se o usu�rio
					// quer inserir
					// a devolu��o mesmo com o valor da devolu��o sendo superior ao da guia da devolu��o
					return montarPaginaConfirmacao(
							"atencao.referencia.conta.inexistente",
							httpServletRequest, actionMapping);
				}
			}
			//Inserir o mesAnoReferencia do cr�dito no objeto final
			objCreditoRealizado.setAnoMesReferenciaCredito(new Integer(mesAnoCredito));        	
        }
        
        
        //Verifica��o do m�s e ano de cobran�a
        if (mesAnoCobranca != null && !mesAnoCobranca.equalsIgnoreCase("")){
        	
        	//[FS0002] - Validar ano e m�s de refer�ncia
        	if (Util.validarAnoMes(mesAnoCobranca)){
        		throw new ActionServletException(
                "atencao.adicionar_debito_ano_mes_referencia_invalido");
        	}
        	//[FS0011] - Verifica ano e m�s da cobran�a
        	
        	//Invertendo para AnoM�s e retirando a barra
        	mesAnoCobranca = Util.formatarMesAnoParaAnoMesSemBarra(mesAnoCobranca);
    		
        	SistemaParametro sistemaParametro = fachada.pesquisarParametrosDoSistema();
        	
        	if (sistemaParametro == null){
        		throw new ActionServletException(
                "atencao.adicionar_debito_ano_mes_cobranca_invalido");
        	}
        	else if (compararAnoMesReferencia(new Integer(mesAnoCobranca), new Integer(sistemaParametro.getAnoMesArrecadacao()), ">")){
        		throw new ActionServletException(
                "atencao.adicionar_debito_ano_mes_faturamento_invalido");
        	}
        	else{
    			//Inserir o mesAnoCobranca do d�bito no objeto final
        		objCreditoRealizado.setAnoMesCobrancaCredito(new Integer(mesAnoCobranca));
    		}
        	
        }
        
        
        //[FS0012] - Verificar valor do cr�dito realizado igual a zero
        BigDecimal objValorCredito = Util.formatarMoedaRealparaBigDecimal(valorCredito);
        if (objValorCredito.equals(new BigDecimal("0"))){
        	throw new ActionServletException(
            "atencao.adicionar_debito_valor_debito_igual_zero");
        }
        	
    	//Inserir o valor do cr�dito no objeto final
    	objCreditoRealizado.setValorCredito(objValorCredito);
        
        
        //Realizando consulta para obter os dados do tipo do cr�dito selecionado
        FiltroCreditoTipo filtroCreditoTipo = new FiltroCreditoTipo();
    	
        filtroCreditoTipo.adicionarParametro(new ParametroSimples(FiltroCreditoTipo.ID,
        		creditoTipoID));
        
        filtroCreditoTipo.adicionarParametro(new ParametroSimples(FiltroCreditoTipo.INDICADOR_USO,
    			ConstantesSistema.INDICADOR_USO_ATIVO));
    
    	Collection colecaoCreditoTipo = fachada.pesquisar(filtroCreditoTipo,
    		CreditoTipo.class.getName());
    
    	if (colecaoCreditoTipo == null || colecaoCreditoTipo.isEmpty()){
    	throw new ActionServletException(
                "atencao.pesquisa.nenhum_registro_tabela", null,
                "CREDITO_TIPO");
    	}
		CreditoTipo objCreditoTipo = (CreditoTipo) Util.retonarObjetoDeColecao(colecaoCreditoTipo);
		
		//Inserindo o tipo do cr�dito selecionado
		objCreditoRealizado.setCreditoTipo(objCreditoTipo);
    	
    	
    	//Realizando consulta para obter os dados do cr�dito origem selecionado
        FiltroCreditoOrigem filtroCreditoOrigem = new FiltroCreditoOrigem();
    	
        filtroCreditoOrigem.adicionarParametro(new ParametroSimples(FiltroCreditoOrigem.ID,
        		creditoOrigemID));
        
        filtroCreditoOrigem.adicionarParametro(new ParametroSimples(FiltroCreditoOrigem.INDICADOR_USO,
    			ConstantesSistema.INDICADOR_USO_ATIVO));
    
    	Collection colecaoCreditoOrigem = fachada.pesquisar(filtroCreditoOrigem,
    		CreditoOrigem.class.getName());
    
    	if (colecaoCreditoOrigem == null || colecaoCreditoOrigem.isEmpty()){
    	throw new ActionServletException(
                "atencao.pesquisa.nenhum_registro_tabela", null,
                "CREDITO_ORIGEM");
    	}
		CreditoOrigem objCreditoOrigem = (CreditoOrigem) Util.retonarObjetoDeColecao(colecaoCreditoOrigem);
		
		//Inserindo o tipo do cr�dito selecionado
		objCreditoRealizado.setCreditoOrigem(objCreditoOrigem);
    	
    	
    	//N�mero de presta��es
    	objCreditoRealizado.setNumeroPrestacao(new Short("1").shortValue());
    	
    	//N�mero da presta��o
    	objCreditoRealizado.setNumeroPrestacaoCredito(new Short("1").shortValue());
    	
    	
    	//Colocando o objeto gerado na cole��o que ficar� na sess�o
        if (sessao.getAttribute("colecaoCreditoRealizado") == null){
        	Collection colecaoCreditoRealizado = new Vector();
        	colecaoCreditoRealizado.add(objCreditoRealizado);
        	sessao.setAttribute("colecaoCreditoRealizado", colecaoCreditoRealizado);
        	
        	httpServletRequest.setAttribute("reloadPage", "OK");
        	
        }
        else{
        	Collection colecaoCreditoRealizado = (Collection) sessao.getAttribute("colecaoCreditoRealizado");
        	//[FS0014] - Verificar d�bito j� existente
        	if (!verificarCreditoJaExistente(colecaoCreditoRealizado, objCreditoRealizado)){
        		colecaoCreditoRealizado.add(objCreditoRealizado);
        		
        		httpServletRequest.setAttribute("reloadPage", "OK");
        	
        	}
        	else{
        		throw new ActionServletException(
                "atencao.adicionar_debito_ja_existente");
        	}
        }
        
        //Limpando o formul�rio para inserir um novo d�bito
        adicionarCreditoRealizadoContaActionForm.setCreditoTipoID("" + ConstantesSistema.NUMERO_NAO_INFORMADO);
        adicionarCreditoRealizadoContaActionForm.setMesAnoCobranca("");
        adicionarCreditoRealizadoContaActionForm.setMesAnoCredito("");
        adicionarCreditoRealizadoContaActionForm.setValorCredito("");
        adicionarCreditoRealizadoContaActionForm.setCreditoOrigemID("" + ConstantesSistema.NUMERO_NAO_INFORMADO);
        
        
        /*
		 * Colocado por Raphael Rossiter em 29/03/2007
		 * Objetivo: Manipula��o dos objetos que ser�o exibidos no formul�rio de acordo com a empresa
		 */
		SistemaParametro sistemaParametro = fachada.pesquisarParametrosDoSistema();
		httpServletRequest.setAttribute("empresaNome", sistemaParametro.getNomeAbreviadoEmpresa().trim());
        
      
        return retorno;
    }
    
    
    /**
     * Compara dois objetos no formato anoMesReferencia de acordo com o sinal logico passado.
     * @param anoMesReferencia1
     * @param anoMesReferencia2
     * @param sinal
     * @return um boleano
     */
    private boolean compararAnoMesReferencia(Integer anoMesReferencia1, Integer anoMesReferencia2, String sinal){
    	boolean retorno = true;
    	
    	//Separando os valores de m�s e ano para realizar a compara��o
    	String mesReferencia1 = String.valueOf(anoMesReferencia1.intValue()).substring(4, 6);
        String anoReferencia1 = String.valueOf(anoMesReferencia1.intValue()).substring(0, 4);
        
        String mesReferencia2 = String.valueOf(anoMesReferencia2.intValue()).substring(4, 6);
        String anoReferencia2 = String.valueOf(anoMesReferencia2.intValue()).substring(0, 4);
        
        if (sinal.equalsIgnoreCase("=")){
        	if (!Integer.valueOf(anoReferencia1).equals(Integer.valueOf(anoReferencia2))){
        		retorno = false;
        	}
        	else if (!Integer.valueOf(mesReferencia1).equals(Integer.valueOf(mesReferencia2))){
        		retorno = false;
        	}
        }
        else if (sinal.equalsIgnoreCase(">")){
        	if (Integer.valueOf(anoReferencia1).intValue() < Integer.valueOf(anoReferencia2).intValue()){
        		retorno = false;
        	}
        	else if (Integer.valueOf(anoReferencia1).equals(Integer.valueOf(anoReferencia2)) &&
        			 Integer.valueOf(mesReferencia1).intValue() <= Integer.valueOf(mesReferencia2).intValue()){
        		retorno = false;
        	}
        }
        else{
        	if (Integer.valueOf(anoReferencia2).intValue() < Integer.valueOf(anoReferencia1).intValue()){
        		retorno = false;
        	}
        	else if (Integer.valueOf(anoReferencia2).equals(Integer.valueOf(anoReferencia1)) &&
        			 Integer.valueOf(mesReferencia2).intValue() <= Integer.valueOf(mesReferencia1).intValue()){
        		retorno = false;
        	}
        }

    	
    	return retorno;
    }
    
    
    /**
     * [FS0014] - Caso o tipo do cr�dito selecionado j� esteja na lista
     * @param colecaoCreditoRealizado
     * @param creditoRealizadoInsert
     * @return um boleano
     */
    private boolean verificarCreditoJaExistente(Collection colecaoCreditoRealizado, CreditoRealizado creditoRealizadoInsert){
    	boolean retorno = false;
    	
    	Iterator colecaoCreditoRealizadoIt = colecaoCreditoRealizado.iterator();
    	CreditoRealizado creditoRealizadoColecao;
    	
    	while (colecaoCreditoRealizadoIt.hasNext()){
    		creditoRealizadoColecao = (CreditoRealizado) colecaoCreditoRealizadoIt.next();
    		if (creditoRealizadoColecao.getCreditoTipo().getId().equals(creditoRealizadoInsert.getCreditoTipo().getId())){
    			retorno = true;
    			break;
    		}
    	}
    	
    	return retorno;
    }

}
