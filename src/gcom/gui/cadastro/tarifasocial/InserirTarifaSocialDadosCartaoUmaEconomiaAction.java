package gcom.gui.cadastro.tarifasocial;

import gcom.cadastro.cliente.ClienteImovel;
import gcom.cadastro.imovel.AreaConstruidaFaixa;
import gcom.cadastro.imovel.Imovel;
import gcom.cadastro.tarifasocial.FiltroRendaTipo;
import gcom.cadastro.tarifasocial.FiltroTarifaSocialCartaoTipo;
import gcom.cadastro.tarifasocial.RendaTipo;
import gcom.cadastro.tarifasocial.TarifaSocialCartaoTipo;
import gcom.cadastro.tarifasocial.TarifaSocialDadoEconomia;
import gcom.cadastro.tarifasocial.TarifaSocialExclusaoMotivo;
import gcom.fachada.Fachada;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.util.ConstantesSistema;
import gcom.util.Util;
import gcom.util.filtro.ParametroSimples;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * < <Descri��o da Classe>>
 * 
 * @author rodrigo
 */
public class InserirTarifaSocialDadosCartaoUmaEconomiaAction extends GcomAction {
    /**
     * < <Descri��o do m�todo>>
     * 
     * @param actionMapping
     *            Descri��o do par�metro
     * @param actionForm
     *            Descri��o do par�metro
     * @param httpServletRequest
     *            Descri��o do par�metro
     * @param httpServletResponse
     *            Descri��o do par�metro
     * @return Descri��o do retorno
     */
    public ActionForward execute(ActionMapping actionMapping,
            ActionForm actionForm, HttpServletRequest httpServletRequest,
            HttpServletResponse httpServletResponse) {

        ActionForward retorno = actionMapping
                .findForward("inserirTarifaSocialDadosCartaoUmaEconomia");

        //Instancia da Fachada
        Fachada fachada = Fachada.getInstancia();

        //Pega uma instancia da sessao
        HttpSession sessao = httpServletRequest.getSession(false);

        TarifaSocialCartaoActionForm form = (TarifaSocialCartaoActionForm) actionForm;

        //Descobrir se � um recadastramento
        ClienteImovel clienteImovel = (ClienteImovel) sessao
                .getAttribute("clienteImovel");
        
         
        //[FS0005] - Verificar preencimento dos campos
        
        String numeroIptu = form.getNumeroIPTU();
        String numeroContratoCompanhiaEletrica = form.getNumeroContratoCelpe();
        String areaConstruida = form.getAreaConstruida();
        String idAreaConstruidaFaixa = form.getAreaConstruidaFaixa();
        String numeroMoradores = form.getNumeroMoradores();
        
		Long numeroCelpeFormatado = null;
		
		if (numeroContratoCompanhiaEletrica != null && !numeroContratoCompanhiaEletrica.trim().equals("")) {
			numeroCelpeFormatado = new Long(numeroContratoCompanhiaEletrica);
		}
		
		BigDecimal areaConstruidaFormatada = null;
		
		if (areaConstruida != null && !areaConstruida.trim().equals("")) {
			areaConstruidaFormatada = Util.formatarMoedaRealparaBigDecimal(areaConstruida);
		}
		
		String numeroIptuFormatado = null;
		
		if (numeroIptu != null && !numeroIptu.trim().equals("")) {
			numeroIptuFormatado = Util.formatarIPTU(numeroIptu);
		}
			
		Integer idImovel = clienteImovel.getImovel().getId();
		

        String[] dado = fachada.verificarPreenchimentoInserirDadosTarifaSocial(numeroCelpeFormatado, areaConstruidaFormatada, numeroIptuFormatado, idImovel, 
                form.getNumero(), form.getDataValidade(), form
                        .getNumeroParcelas(), new Integer(form
                        .getNumeroCelpe().trim().equals("") ? "0" : form
                        .getNumeroCelpe()),
                        form.getValorRendaFamiliar().trim().equals("") ? Util.formatarMoedaRealparaBigDecimal("0") :  
                        Util.formatarMoedaRealparaBigDecimal(form.getValorRendaFamiliar()), form.getTipoCartao(), form
                        .getTipoRenda());
        
        if(dado != null){
        	if(dado[0].equals("9")){
        		httpServletRequest.setAttribute("codigo","9");
        		
        	}else if(dado[0].equals("10")){
        		
        		httpServletRequest.setAttribute("codigo","10");
        		httpServletRequest.setAttribute("valor",dado[1]);
        		
                httpServletRequest.setAttribute("operacaoConcluida", "true");

                return retorno;        		
        		
        		
        	}else if(dado[0].equals("11")){
        		
        		httpServletRequest.setAttribute("codigo","11");
        		httpServletRequest.setAttribute("valor",dado[1]);
        		
                httpServletRequest.setAttribute("operacaoConcluida", "true");

                return retorno;        		
        		
        	} else if (dado[0].equals("12")) {
        		
        		httpServletRequest.setAttribute("codigo","12");
        		httpServletRequest.setAttribute("valor",dado[1]);
        		
        		
                httpServletRequest.setAttribute("operacaoConcluida", "true");

                return retorno;   
        		
        	}
        	
        	
        }
        

        //Esse parametro indica se o registro que est� sendo avaliado j� est� na base
        
        //O c�digo � a chava prim�ria
        String chave = httpServletRequest.getParameter("codigo");

        Collection colecaoPesquisa;
        TarifaSocialDadoEconomia tarifaSocialDadoEconomia = new TarifaSocialDadoEconomia();

        tarifaSocialDadoEconomia.setUltimaAlteracao(new Date());
        tarifaSocialDadoEconomia.setImovel(clienteImovel.getImovel());

        if (form.getNumero() != null && !form.getNumero().equals("")) {
            tarifaSocialDadoEconomia.setNumeroCartaoProgramaSocial(new Long(
                    form.getNumero()));

            TarifaSocialCartaoTipo tarifaSocialCartaoTipo;
            FiltroTarifaSocialCartaoTipo filtroTarifaSocialCartaoTipo = new FiltroTarifaSocialCartaoTipo();

            filtroTarifaSocialCartaoTipo
                    .adicionarParametro(new ParametroSimples(
                            FiltroTarifaSocialCartaoTipo.ID, form
                                    .getTipoCartao()));

            colecaoPesquisa = fachada.pesquisar(filtroTarifaSocialCartaoTipo,
                    TarifaSocialCartaoTipo.class.getName());

            tarifaSocialCartaoTipo = (TarifaSocialCartaoTipo) Util
                    .retonarObjetoDeColecao(colecaoPesquisa);

            tarifaSocialDadoEconomia
                    .setTarifaSocialCartaoTipo(tarifaSocialCartaoTipo);
        }

        SimpleDateFormat formatoData = new SimpleDateFormat("dd/MM/yyyy");
        Date dataValidade = null;

        try {
            dataValidade = formatoData.parse(form.getDataValidade());
        } catch (ParseException ex) {
            dataValidade = null;
        }

        tarifaSocialDadoEconomia.setDataValidadeCartao(dataValidade);

        
        //Consumo Celpe
        if (form.getNumeroCelpe() != null
                && !form.getNumeroCelpe().equals("")) {
        	tarifaSocialDadoEconomia.setConsumoCelpe(new Integer(form.getNumeroCelpe()));
        }

        //N�mero de parcelas
        if (form.getNumeroParcelas() != null
                && !form.getNumeroParcelas().equals("")) {
        	tarifaSocialDadoEconomia.setNumeroMesesAdesao(new Short(form.getNumeroParcelas()));
        }
        
        if (form.getValorRendaFamiliar() != null
                && !form.getValorRendaFamiliar().equals("")) {
            tarifaSocialDadoEconomia.setValorRendaFamiliar(Util.formatarMoedaRealparaBigDecimal(form
                    .getValorRendaFamiliar()));

            RendaTipo rendaTipo;
            FiltroRendaTipo filtroRendaTipo = new FiltroRendaTipo();

            filtroRendaTipo.adicionarParametro(new ParametroSimples(
                    FiltroRendaTipo.ID, form.getTipoRenda()));

            colecaoPesquisa = fachada.pesquisar(filtroRendaTipo,
                    RendaTipo.class.getName());

            rendaTipo = (RendaTipo) Util
                    .retonarObjetoDeColecao(colecaoPesquisa);

            tarifaSocialDadoEconomia.setRendaTipo(rendaTipo);
        }
        
        if (form.getIdImovel() != null && !form.getIdImovel().equals("")) {
        	if (form.getMotivoExclusao() != null && !form.getMotivoExclusao().equals("" + ConstantesSistema.NUMERO_NAO_INFORMADO)) {
        		Integer idTarifaSocialExclusaoMotivo = new Integer(form.getMotivoExclusao());
        		
        		// Seta o motivo da exclus�o na tarifa social antiga para ser
				// recuperado posteriormente
        		TarifaSocialExclusaoMotivo tarifaSocialExclusaoMotivo = new TarifaSocialExclusaoMotivo();
        		tarifaSocialExclusaoMotivo.setId(idTarifaSocialExclusaoMotivo);
        		tarifaSocialDadoEconomia.setTarifaSocialExclusaoMotivo(tarifaSocialExclusaoMotivo);
        		
        		// Recupera o id da tarifa social que ser� exclu�da quando o
				// usu�rio concluir o inser��o e coloca-o no objeto a ser
				// inserido para ser recuperado posteriormente
        		String idTarifaSocialDadoEconomia = (String) sessao.getAttribute("idTarifaSocialDadoEconomia");
        		tarifaSocialDadoEconomia.setId(new Integer(idTarifaSocialDadoEconomia));
        		
        	} else {
        		throw new ActionServletException("atencao.tarifa_social_motivo_exclusao_nao_informado");
        	}
        }

        /*
         * Alterado para s� realizar a persist�ncia dos dados na conclus�o do
         * processo de inser��o da tarifa social - Raphael Rossiter 14/09/2005
         */
        
        // Seta novos campos no im�vel
        Imovel imovel = null;
        
        if (sessao.getAttribute("imovelAtualizado") != null) {
        	imovel = (Imovel) sessao.getAttribute("imovelAtualizado");
        } else {
        	imovel = clienteImovel.getImovel();
        }
        
		// N�mero do IPTU
		if (numeroIptu != null && !numeroIptu.trim().equals("")) {
			idImovel = fachada.verificarNumeroIptu(numeroIptuFormatado, imovel.getId(), null, imovel.getSetorComercial().getMunicipio().getId());
			
			if (idImovel != null) {
				throw new ActionServletException("atencao.imovel.iptu_jacadastrado", null, idImovel.toString());
			}
			
			imovel.setNumeroIptu(numeroIptuFormatado);
		} else {
			imovel.setNumeroIptu(null);
		}
		
		// N�mero do Contrato da Companhia El�trica
		if (numeroContratoCompanhiaEletrica != null && !numeroContratoCompanhiaEletrica.trim().equals("")) {
			
			Long numeroContratoCompanhiaEletricaFormatado = new Long(numeroContratoCompanhiaEletrica);
			
			idImovel = fachada.verificarNumeroCompanhiaEletrica(numeroContratoCompanhiaEletricaFormatado, imovel.getId(), null);
			
			if (idImovel != null) {
				throw new ActionServletException("atencao.imovel.numero_celpe_jacadastrado", null, idImovel.toString());
			}
			
			imovel.setNumeroCelpe(numeroContratoCompanhiaEletricaFormatado);
		} else {
			imovel.setNumeroCelpe(null);
		}
		
		// �rea Constru�da
		if (areaConstruida != null && !areaConstruida.trim().equals("")) {
			imovel.setAreaConstruida(areaConstruidaFormatada);
		} else {
			imovel.setAreaConstruida(null);
		}
		
		// �rea Constru�da Faixa
		if (idAreaConstruidaFaixa != null && !idAreaConstruidaFaixa.trim().equals("" + ConstantesSistema.NUMERO_NAO_INFORMADO)) {
			AreaConstruidaFaixa areaConstruidaFaixa = new AreaConstruidaFaixa();
			areaConstruidaFaixa.setId(new Integer(idAreaConstruidaFaixa));
			imovel.setAreaConstruidaFaixa(areaConstruidaFaixa);
		} else {
			imovel.setAreaConstruidaFaixa(null);
		}
		
		// N�mero de Moradores
		if (numeroMoradores != null && !numeroMoradores.trim().equals("")) {
			imovel.setNumeroMorador(new Short(numeroMoradores));
		} else {
			imovel.setNumeroMorador(null);
		}
		
		sessao.setAttribute("imovelAtualizado", imovel);

        Collection colecaoDadosTarifaSocial = new Vector();

        //Prepara os objetos que ficar�o na sess�o
        if (chave.trim().equals("")) {
            colecaoDadosTarifaSocial.add(tarifaSocialDadoEconomia);
        } else {
            //Atualiza o registro na base
            tarifaSocialDadoEconomia.setId(new Integer(chave));

            colecaoDadosTarifaSocial.add(tarifaSocialDadoEconomia);

        }
        // Coloca os dados da tarifa social na sess�o
        sessao.removeAttribute("clienteImovel");
        sessao.setAttribute("clienteImovel", clienteImovel);
        sessao.removeAttribute("colecaoDadosTarifaSocial");
        sessao.setAttribute("colecaoDadosTarifaSocial",
                colecaoDadosTarifaSocial);

        //Manda o parametro pelo request para que a p�gina de popup saiba a
        // hora de fechar
        httpServletRequest.setAttribute("operacaoConcluida", "true");

        return retorno;
    }
}
