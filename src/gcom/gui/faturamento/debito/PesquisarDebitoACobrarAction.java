package gcom.gui.faturamento.debito;

import gcom.cadastro.cliente.ClienteImovel;
import gcom.cadastro.cliente.ClienteRelacaoTipo;
import gcom.cadastro.cliente.FiltroClienteImovel;
import gcom.cadastro.imovel.FiltroImovel;
import gcom.cadastro.imovel.Imovel;
import gcom.fachada.Fachada;
import gcom.faturamento.debito.DebitoACobrar;
import gcom.faturamento.debito.FiltroDebitoACobrar;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.util.ConstantesSistema;
import gcom.util.Util;
import gcom.util.filtro.Intervalo;
import gcom.util.filtro.MaiorQue;
import gcom.util.filtro.MenorQue;
import gcom.util.filtro.ParametroNulo;
import gcom.util.filtro.ParametroSimples;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * Realiza a pesquisa de d�bitos a cobrar de im�vel de acordo com os par�metros informados na p�gina 
 *
 * @author Pedro Alexandre
 * @date 13/03/2006
 */
public class PesquisarDebitoACobrarAction extends GcomAction {
	
	/**
	 * Pesquisa os d�bitos a cobrar existentes para o im�vel
	 *
	 * [UC0271] Pesquisar D�bito a Cobrar
	 *
	 * <Breve descri��o sobre o subfluxo>
	 *
	 * <Identificador e nome do subfluxo>	
	 *
	 * <Breve descri��o sobre o fluxo secund�rio>
	 *
	 * <Identificador e nome do fluxo secund�rio> 
	 *
	 * @author Pedro Alexandre
	 * @date 13/03/2006
	 *
	 * @param actionMapping
	 * @param actionForm
	 * @param httpServletRequest
	 * @param httpServletResponse
	 * @return
	 */
	public ActionForward execute(ActionMapping actionMapping,
								ActionForm actionForm, 
								HttpServletRequest httpServletRequest,
								HttpServletResponse httpServletResponse) {

		//Seta o mapeamento de retorno para a tela de resultado da pesquisa de d�bitos a cobrar do im�vel
		ActionForward retorno = actionMapping.findForward("listaDebitoACobrar");

		//Cria uma inst�ncia da fachada
		Fachada fachada = Fachada.getInstancia();
		
		//Cria uma inst�ncia da sess�o
		HttpSession sessao = httpServletRequest.getSession(false);

		//Recupera o form de pesquisa de d�bitos a cobrar do im�vel
		PesquisarDebitoACobrarActionForm pesquisarDebitoACobrarActionForm = (PesquisarDebitoACobrarActionForm) actionForm;
		
		// Recupera os par�metros do form
		String idImovel =  pesquisarDebitoACobrarActionForm.getIdImovel();
		String referenciaDebitoInicial =  pesquisarDebitoACobrarActionForm.getReferenciaDebitoInicial();
		String referenciaDebitoFinal =  pesquisarDebitoACobrarActionForm.getReferenciaDebitoFinal();
		String dataGeracaoDebitoInicialEmString =  pesquisarDebitoACobrarActionForm.getDataGeracaoDebitoInicial();
		String dataGeracaoDebitoFinalEmString =  pesquisarDebitoACobrarActionForm.getDataGeracaoDebitoFinal();
		String[] idSituacaoDebitoACobrar =  pesquisarDebitoACobrarActionForm.getIdSituacaoDebitoACobrar();
		String[] idTipoDebito =  pesquisarDebitoACobrarActionForm.getIdTipoDebitoSelecionados();
		
		//Cria o filtro de d�bito a cobrar e seta a ordena��o de resultado da pesquisa
		//pelo tipo de d�bito do d�bito a cobrar
		FiltroDebitoACobrar filtroDebitoACobrar = new FiltroDebitoACobrar();
		filtroDebitoACobrar.setCampoOrderBy(FiltroDebitoACobrar.DEBITO_TIPO);

		//Cria flag que vai indicar se o usu�rio informou ao menos um par�metro para pesquisar
		boolean peloMenosUmParametroInformado = false;

		//Caso o usu�rio informou o c�digo do im�vel, pesquisa todos os d�bitos a cobrar relacionados com o im�vel
		//caso contr�rio indica que o usu�rio n�o informou o im�vel
		if(idImovel != null && !idImovel.trim().equalsIgnoreCase("")){
			
			//Indica que o usu�rio informou um par�metro para pesquisar os d�bitos a cobrar do im�vel 
			//peloMenosUmParametroInformado = true;
			
			//Cria o fitro de im�vel, e seta no filtro quais objetos necess�rios para a pesquisa de im�vel
			FiltroImovel filtroImovel = new FiltroImovel();
        	filtroImovel.adicionarCaminhoParaCarregamentoEntidade("localidade");        	
        	filtroImovel.adicionarCaminhoParaCarregamentoEntidade("setorComercial");        	
        	filtroImovel.adicionarCaminhoParaCarregamentoEntidade("quadra");        	
        	filtroImovel.adicionarCaminhoParaCarregamentoEntidade("ligacaoAguaSituacao");        	
        	filtroImovel.adicionarCaminhoParaCarregamentoEntidade("ligacaoEsgotoSituacao");        	
        	filtroImovel.adicionarCaminhoParaCarregamentoEntidade("ligacaoEsgoto");
        	
        	//Seta o c�digo do im�vel no filtro 
        	filtroImovel.adicionarParametro(new ParametroSimples(FiltroImovel.ID, idImovel));
        	
        	//Pesquisa o im�vel informado, no sistema 
        	Collection colecaoImovel = fachada.pesquisar(filtroImovel, Imovel.class.getName());
        	
        	//Caso o im�vel informado pelo usu�rio n�o tenha sido encontrado no sistema
        	//caso contr�rio manda o c�digo do im�vel no request 
        	if (colecaoImovel == null || colecaoImovel.isEmpty()){
        		throw new ActionServletException("atencao.naocadastrado", null, "im�vel");
        	}else{
        		httpServletRequest.setAttribute("idImovel",idImovel);
        	}
        	
        	//Recupera o objeto im�vel da cole��o
        	Imovel objetoImovel = (Imovel) Util.retonarObjetoDeColecao(colecaoImovel);        	
        	
        	//Cria o filtro para recuperar o relacionamento entre cliente e im�vel,
        	//e seta os objetos necess�rios no retorno da pesquisa
        	FiltroClienteImovel filtroClienteImovel = new FiltroClienteImovel();
        	filtroClienteImovel.adicionarCaminhoParaCarregamentoEntidade("cliente");
        	filtroClienteImovel.adicionarParametro(new ParametroSimples(FiltroClienteImovel.IMOVEL_ID, idImovel));
        	filtroClienteImovel.adicionarParametro(new ParametroSimples(FiltroClienteImovel.CLIENTE_RELACAO_TIPO, ClienteRelacaoTipo.USUARIO));
        	filtroClienteImovel.adicionarParametro(new ParametroNulo(FiltroClienteImovel.FIM_RELACAO_MOTIVO));
        	
        	//Efetua a pesquisa do relacionamento entre cliente e im�vel
        	Collection colecaoClienteImovel = fachada.pesquisar(filtroClienteImovel, ClienteImovel.class.getName());
        	
        	//Caso n�o exista nenhum cliente relacionado com o im�vel
        	if (colecaoClienteImovel == null || colecaoClienteImovel.isEmpty()){
        		throw new ActionServletException(
                        "atencao.naocadastrado", null, "cliente do tipo usu�rio foi");
        	}
        	
        	//Recupera o relacionamento entre cliente e im�vel da cole��o pesquisada 
        	ClienteImovel objetoClienteImovel = (ClienteImovel) Util.retonarObjetoDeColecao(colecaoClienteImovel);
        	        	
        	//Seta no form de pesquisar d�bito a cobrar todos os dados de im�vel para exibi��o na p�gina de resultado da pesquisa
        	pesquisarDebitoACobrarActionForm.setInscricaoImovel(objetoImovel.getInscricaoFormatada());
        	pesquisarDebitoACobrarActionForm.setNomeClienteUsuario(objetoClienteImovel.getCliente().getNome());
        	pesquisarDebitoACobrarActionForm.setSituacaoAguaImovel(objetoImovel.getLigacaoAguaSituacao().getDescricao());        	
        	pesquisarDebitoACobrarActionForm.setSituacaoEsgotoImovel(objetoImovel.getLigacaoEsgotoSituacao().getDescricao());
        	
			//Seta no filtro de d�bito a cobrar o c�digo do im�vel informado pelo usu�rio
        	filtroDebitoACobrar.adicionarParametro(new ParametroSimples(FiltroDebitoACobrar.IMOVEL_ID, idImovel));
		}else{
			throw new ActionServletException("atencao.naoinformado",null, "Im�vel");
		}
		
		//Caso o usu�rio tenha informado a refer�ncia inicial do d�bito a cobrar, pesquisa os d�bito a cobrar entre a refer�ncia inicial e final, replicando 
		//a refer�ncia inicial na final caso essa n�o tenha sido informada
		//Caso contr�rio seta a refer�ncia final para vazio, e pesquisa os d�bito a cobrar para todas as refer�ncias existentes
		if (referenciaDebitoInicial != null && !referenciaDebitoInicial.trim().equalsIgnoreCase("")) {
			
			//Formata a refer�ncia inicial informada, para o formato de ano e depois m�s
			referenciaDebitoInicial = Util.formatarMesAnoParaAnoMesSemBarra((String) pesquisarDebitoACobrarActionForm.getReferenciaDebitoInicial());
			
			//Caso a refer�ncia final n�o tenha sido informada , replica a refer�ncia inicial na final
			//Caso contr�rio, formata a refer�ncia final para ano e m�s
			if(referenciaDebitoFinal == null || referenciaDebitoFinal.trim().equalsIgnoreCase("")){
				//Replica a refer�ncia inicial na refer�ncia final
				referenciaDebitoFinal = referenciaDebitoInicial;
			}else{
				//Formata a refer�ncia final informada, para o formato de ano e depois m�s
				referenciaDebitoFinal = Util.formatarMesAnoParaAnoMesSemBarra((String) pesquisarDebitoACobrarActionForm.getReferenciaDebitoFinal());
				
				//[FS0002]Caso a refer�ncia final do d�bito a cobrar seja anterior a inicial 
				if((new Integer(referenciaDebitoInicial)).intValue() > (new Integer (referenciaDebitoFinal)).intValue()){
					throw new ActionServletException("atencao.referenciafinal.menorque");
				}
			}
		} else{
			//Seta a refer�ncia final da conta para nula
			referenciaDebitoFinal = null; 
		}

		//Caso a refer�ncia final do d�bito a cobrar esteja diferente de nulo,
		//Pesquisa os d�bitos a cobrar entre a refer�ncia inicial e final
		if (referenciaDebitoFinal != null && !referenciaDebitoFinal.trim().equalsIgnoreCase("")) {
			//Indica que o usu�rio informou um par�metro para pesquisar os d�bitos a cobrar do im�vel
			peloMenosUmParametroInformado = true;
			
			//Indica no filtro para pesquisar os d�bitos a cobrar no intervalo entre a refer�ncia inicial e a final
			filtroDebitoACobrar.adicionarParametro(new MaiorQue(FiltroDebitoACobrar.REFERENCIA_DEBITO, referenciaDebitoInicial, ParametroSimples.CONECTOR_AND));
			filtroDebitoACobrar.adicionarParametro(new MenorQue(FiltroDebitoACobrar.REFERENCIA_DEBITO, referenciaDebitoFinal));
		}

		//Cria o formato da data
		SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");

		//Cria as vari�veis que vai armazenar as datas inicial e final, da gera��o do d�bito
		Date dataGeracaoDebitoInicial = null;
		Date dataGeracaoDebitoFinal = null;
		
		
		 
		//Caso o usu�rio informou a data de gera��o do d�bito a cobrar inicial, pesquisa os d�bitos a cobrar do im�vel referentes a data de 
		//gera��o do d�bito informada
		//Caso contr�rio, pesquisa os d�bitos a cobrar sem restri��o de data de gera��o
		if (dataGeracaoDebitoInicialEmString != null && !dataGeracaoDebitoInicialEmString.toString().trim().equalsIgnoreCase("")) {
			
			//[FS0003] Valida a data de gera��o do d�bito inicial
			try {
				dataGeracaoDebitoInicial = formato.parse(dataGeracaoDebitoInicialEmString);
				
				//dataGeracaoDebitoInicialFormatoTimeStamp = new Timestamp(dataGeracaoDebitoInicial.getTime());
			} catch (ParseException e) {
				throw new ActionServletException("atencao.datageracaoinicial.invalida");
			}

			//Caso a data final de gera��o do d�bito n�o foi informada, replica a data inicial na final
			//Caso contr�rio, formata a data final 
			if(dataGeracaoDebitoFinalEmString == null || dataGeracaoDebitoFinalEmString.trim().equalsIgnoreCase("")){
				dataGeracaoDebitoFinal = dataGeracaoDebitoInicial;
				//dataGeracaoDebitoFinalFormatoTimeStamp = dataGeracaoDebitoInicialFormatoTimeStamp;
			}else{
				
				//[FS0003] Valida a data de gera��o do d�bito final
				try {
					dataGeracaoDebitoFinal = formato.parse(dataGeracaoDebitoFinalEmString);
					
					//dataGeracaoDebitoFinalFormatoTimeStamp = new Timestamp(dataGeracaoDebitoFinal.getTime());
				} catch (ParseException e) {
					throw new ActionServletException("atencao.datageracaofinal.invalida");
				}
				
				//[FS0004]Caso a data de gera��o final do d�bito seja anterior a inicial 
				//levanta a exce��o para o usu�rio indicando que a data final � anterior a data inicial
				if(dataGeracaoDebitoFinal.before(dataGeracaoDebitoInicial)){
					throw new ActionServletException("atencao.datageracaofinal.menorque");
				}
			}
		
		} else{
			//Seta para nula a data final de gera��o do d�bito
			dataGeracaoDebitoFinalEmString = null;
		}

		//Cria uma vari�vel que vai auxiliar na data final de gera��o,
		//para setar a data para o �ltimo segundo do dia informado
		Calendar data = Calendar.getInstance();
		
		//Caso a data de gera��o final do d�bito esteja diferente de nulo,
		//Pesquisa os d�bitos entre a data de gera��o inicial e final
		if (dataGeracaoDebitoFinalEmString != null && !dataGeracaoDebitoFinalEmString.toString().trim().equalsIgnoreCase("")) {
			//Indica que o usu�rio informou um par�metro para pesquisar os d�bitos a cobrar do im�vel
			peloMenosUmParametroInformado = true;
		
			//Seta a data final de gera��o no calendar
			data.setTime(dataGeracaoDebitoFinal);
			
			//Seta a data final de gera��o do d�bito para o �ltimo segundo do dia informado 
			data.set(Calendar.HOUR, data.getMaximum(Calendar.HOUR_OF_DAY));
			data.set(Calendar.MINUTE, data.getMaximum(Calendar.MINUTE));
			data.set(Calendar.SECOND, data.getMaximum(Calendar.SECOND));
			data.set(Calendar.MILLISECOND, data.getMaximum(Calendar.MILLISECOND));
			
			//Recupera a data final 
			dataGeracaoDebitoFinal = data.getTime();
			
			//Indica no filtro para pesquisar os d�bitos a cobrar no intervalo entre a data de gera��o inicial e a final
			filtroDebitoACobrar.adicionarParametro(new Intervalo(FiltroDebitoACobrar.GERACAO_DEBITO, new Timestamp(dataGeracaoDebitoInicial.getTime()), new Timestamp(dataGeracaoDebitoFinal.getTime())));
		}
		
		//Caso o usu�rio informou algum tipo de d�bito do d�bito a cobrar 
		if(idTipoDebito != null && 
				!idTipoDebito[0].equals("" + ConstantesSistema.NUMERO_NAO_INFORMADO)&&
				idTipoDebito.length >0){
			  //Indica que o usu�rio informou um par�metro para pesquisar
			  peloMenosUmParametroInformado = true;
				
			  //La�o para setar no filtro de guia todos os tipos de d�bitos selecionados
			  for(int i=0; i< idTipoDebito.length; i++ ){
			    if(! (new Integer(idTipoDebito[i]).equals(new Integer(ConstantesSistema.NUMERO_NAO_INFORMADO)))){
					  
				  if(idTipoDebito.length == 1){
					  filtroDebitoACobrar.adicionarParametro(new ParametroSimples(FiltroDebitoACobrar.DEBITO_TIPO_ID,idTipoDebito[i]));
				  }else{
				    if( i == 0 ){
				    	filtroDebitoACobrar.adicionarParametro(new ParametroSimples(FiltroDebitoACobrar.DEBITO_TIPO_ID,idTipoDebito[i], ParametroSimples.CONECTOR_OR,idTipoDebito.length ));	
					}else{
					  if( i  == (idTipoDebito.length - 1) ){
						  filtroDebitoACobrar.adicionarParametro(new ParametroSimples(FiltroDebitoACobrar.DEBITO_TIPO_ID,idTipoDebito[i]));
					  }else{
						  filtroDebitoACobrar.adicionarParametro(new ParametroSimples(FiltroDebitoACobrar.DEBITO_TIPO_ID,idTipoDebito[i], ParametroSimples.CONECTOR_OR));
					  }
					}
				  }
				}
			  }
			}
		
		
		//Caso o usu�rio informou alguma situa��o de d�bito do d�bito a cobrar 
		if(idSituacaoDebitoACobrar != null && 
				!idSituacaoDebitoACobrar[0].equals("" + ConstantesSistema.NUMERO_NAO_INFORMADO)&&
				idSituacaoDebitoACobrar.length >0){
			//Indica que o usu�rio informou um par�metro para pesquisar os d�bitos a cobrar do im�vel
			peloMenosUmParametroInformado = true;
			
			//La�o para incluir no filtro todas as situa��es de d�bito a cobrar informadas pelo usu�rio para pesquisa 
			for(int i=0; i< idSituacaoDebitoACobrar.length; i++ ){
			  if(! (new Integer(idSituacaoDebitoACobrar[i]).equals(new Integer(ConstantesSistema.NUMERO_NAO_INFORMADO)))){
				  
			    if(idSituacaoDebitoACobrar.length==1){
				  filtroDebitoACobrar.adicionarParametro(new ParametroSimples(FiltroDebitoACobrar.DEBITO_CREDITO_SITUACAO_ATUAL_ID,idSituacaoDebitoACobrar[i]));	
				}else{  
				  
				  if( i == 0 ){
					filtroDebitoACobrar.adicionarParametro(new ParametroSimples(FiltroDebitoACobrar.DEBITO_CREDITO_SITUACAO_ATUAL_ID,idSituacaoDebitoACobrar[i], ParametroSimples.CONECTOR_OR,idSituacaoDebitoACobrar.length ));	
				  }else{
				    if( i  == (idSituacaoDebitoACobrar.length - 1) ){
				      filtroDebitoACobrar.adicionarParametro(new ParametroSimples(FiltroDebitoACobrar.DEBITO_CREDITO_SITUACAO_ATUAL_ID,idSituacaoDebitoACobrar[i]));
				    }else{
				      filtroDebitoACobrar.adicionarParametro(new ParametroSimples(FiltroDebitoACobrar.DEBITO_CREDITO_SITUACAO_ATUAL_ID,idSituacaoDebitoACobrar[i], ParametroSimples.CONECTOR_OR));
				    }
				  }
			    }  
			  }
			}
		}
		
		// [FS0006] Erro caso o usu�rio mandou pesquisar sem nenhum par�metro
		if (!peloMenosUmParametroInformado) {
			throw new ActionServletException("atencao.filtro.nenhum_parametro_informado");
		}

		//Carrega no filtro os objetos necess�rios para pesquisa
		filtroDebitoACobrar.adicionarCaminhoParaCarregamentoEntidade("debitoTipo");
		filtroDebitoACobrar.adicionarCaminhoParaCarregamentoEntidade("debitoCreditoSituacaoAtual");

		//Pesquisa os d�bitos a cobrar do im�vel, com os par�metros indicados no filtro
		Collection colecaoDebitosACobrar = null;//fachada.pesquisar(filtroDebitoACobrar,DebitoACobrar.class.getName());
		
		Map resultado = controlarPaginacao(httpServletRequest, retorno,
				filtroDebitoACobrar, DebitoACobrar.class.getName());
		colecaoDebitosACobrar = (Collection) resultado.get("colecaoRetorno");
		retorno = (ActionForward) resultado.get("destinoActionForward");

		//Caso nenhum d�bito a cobrar tenha sido encontrado com os par�metros indicados
		if (colecaoDebitosACobrar == null || colecaoDebitosACobrar.isEmpty()) {
			//[FS0008]Nenhum d�bito a cobrar cadastrado
			throw new ActionServletException("atencao.pesquisa.nenhumresultado", null, "D�bito a Cobrar");
			
			//Caso o n� de d�bitos a cobrar retornados pela pesquisa seja maior que o n� m�ximo de registros permitidos
		} else if (colecaoDebitosACobrar.size() > ConstantesSistema.NUMERO_MAXIMO_REGISTROS_PESQUISA) {
			//[FS0007]Muitos registros encontrados
			throw new ActionServletException("atencao.pesquisa.muitosregistros");
		} else {
			//Coloca a cole��o de d�bitos a cobrar do im�vel pesquisados na sess�o 
			sessao.setAttribute("colecaoDebitosACobrar", colecaoDebitosACobrar);
		}

		//Retorna o mapeamento contido na vari�vel retorno
		return retorno;
	}
}
