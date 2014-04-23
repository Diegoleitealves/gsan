package gcom.util.agendadortarefas;

import gcom.batch.VerificadorProcessosIniciados;
import gcom.fachada.Fachada;
import gcom.integracao.ControladorIntegracaoLocal;
import gcom.integracao.ControladorIntegracaoLocalHome;
import gcom.tarefa.Tarefa;
import gcom.util.ConstantesJNDI;
import gcom.util.ControladorException;
import gcom.util.ServiceLocator;
import gcom.util.ServiceLocatorException;
import gcom.util.SistemaException;
import gcom.util.TarefaIntegracaoUPA;
import gcom.util.Util;

import javax.ejb.CreateException;

import org.quartz.JobDetail;
import org.quartz.ObjectAlreadyExistsException;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.Trigger;
import org.quartz.TriggerUtils;
import org.quartz.impl.StdSchedulerFactory;

/**
 * Essa classe tem o papel de fornecer ao sistema servi�os de agendamento de
 * tarefas utilizando a biblioteca Quartz
 * 
 * @author Rodrigo Silveira
 * @date 03/08/2006
 */
public final class AgendadorTarefas {
	private static Scheduler instancia;

	private AgendadorTarefas() {
	}

	public static void initAgendador() {
		SchedulerFactory schedFact = new StdSchedulerFactory();

		try {
			instancia = schedFact.getScheduler();
			instancia.start();
			marcarProcessosInterrompidos();
			agendarTarefaVerificadoraProcessosIniciados();
			agendarTarefaIntegracaoUPA();

		} catch (SchedulerException e) {
			e.printStackTrace();
			throw new SistemaException(
					"Problema ao inicializar o Agendador de Tarefas");
		}

	}

	private static void marcarProcessosInterrompidos() {
		Fachada.getInstancia().marcarProcessosInterrompidos();
		
	}

	public static Scheduler getAgendador() {

		return instancia;

	}

	/**
	 * Inicializa a tarefa que busca no sistema processosIniciados para ativar a
	 * execu��o de minuto em minuto
	 * 
	 * @author Rodrigo Silveira
	 * @date 21/08/2006
	 * 
	 */
	private static void agendarTarefaVerificadoraProcessosIniciados() {
		JobDetail jobDetail = new JobDetail("verificador", null,
				VerificadorProcessosIniciados.class);

		Trigger trigger = TriggerUtils.makeMinutelyTrigger();
		trigger.setStartTime(TriggerUtils.getNextGivenSecondDate(null, 0));
		trigger.setName("verificador");

		try {
			getAgendador().scheduleJob(jobDetail, trigger);
		} catch (ObjectAlreadyExistsException e) {
			// Fazer nada pois o agendamento j� existe no sistema
			try {
				getAgendador().rescheduleJob("verificador", null, trigger);
			} catch (SchedulerException e1) {
				e1.printStackTrace();
				throw new SistemaException("Problema ao agendar uma tarefa");
			}

		} catch (SchedulerException e) {
			e.printStackTrace();
			throw new SistemaException("Problema ao agendar uma tarefa");

		}

	}

	public static void agendarTarefa(String nomeTarefa, Tarefa tarefa) {

		JobDetail jobDetail = new JobDetail(nomeTarefa + Util.geradorSenha(13),
				null, tarefa.getClass());

		jobDetail.getJobDataMap().put("usuario", tarefa.getUsuario());
		jobDetail.getJobDataMap().put("parametroTarefa",
				tarefa.getParametroTarefa());
		jobDetail.getJobDataMap().put("idFuncionalidadeIniciada",
				tarefa.getIdFuncionalidadeIniciada());

		// Trigger triggerTarefa = TriggerUtils.makeImmediateTrigger(0, 0);

		// triggerTarefa
		// .setStartTime(TriggerUtils.getNextGivenSecondDate(null, 0));
		// triggerTarefa.setName("trigger" + new Date());

		try {
			getAgendador().addJob(jobDetail, true);
			getAgendador().triggerJob(jobDetail.getName(), null);
			// getAgendador().scheduleJob(jobDetail, triggerTarefa);
		} catch (SchedulerException e) {
			e.printStackTrace();
			throw new SistemaException("Problema ao agendar uma tarefa");

		}

	}
	
	
	
	/**
	 * Inicializa a tarefa que busca no sistema processosIniciados para ativar a
	 * execu��o de minuto em minuto
	 * 
	 * @author Rodrigo Silveira
	 * @date 21/08/2006
	 * 
	 */
	private static void agendarTarefaIntegracaoUPA() {
		
		//Pesquisar o hor�rio inicial e o intervalo de horas para rodar o processo
		int horaInicial = 0;
		int intervalo = 0;
		try {
			
			Object[] retorno = getControladorIntegracao().pesquisarHorarioProcessoIntegracaoUPA();
			for (int i = 0; i < retorno.length; i++) {
				horaInicial = (Integer)retorno[0];
				intervalo = (Integer)retorno[1];
			}
			
		} catch (ControladorException e) {
			throw new SistemaException("Problema ao agendar uma tarefa");
		}
		
		JobDetail jobDetail = new JobDetail("TarefaIntegracaoUPADia", null,
				TarefaIntegracaoUPA.class);

    	Trigger trigger = TriggerUtils.makeHourlyTrigger(intervalo);
		trigger.setStartTime(TriggerUtils.getDateOf(0,0, horaInicial));
    	
		trigger.setName("TarefaIntegracaoUPA");
		try {
			getAgendador().scheduleJob(jobDetail, trigger);
		} catch (ObjectAlreadyExistsException e) {
			// Fazer nada pois o agendamento j� existe no sistema
			try {
				getAgendador().rescheduleJob("TarefaIntegracaoUPA", null, trigger);
			} catch (SchedulerException e1) {
				e1.printStackTrace();
				throw new SistemaException("Problema ao agendar uma tarefa");
			}

		} catch (SchedulerException e) {
			e.printStackTrace();
			throw new SistemaException("Problema ao agendar uma tarefa");

		}

	}

	public static void main(String[] args) {
		System.out.println(AgendadorTarefas.getAgendador());
	}
	
	private static ControladorIntegracaoLocal getControladorIntegracao() {
		ControladorIntegracaoLocalHome localHome = null;
		ControladorIntegracaoLocal local = null;

		// pega a inst�ncia do ServiceLocator.

		ServiceLocator locator = null;

		try {
			locator = ServiceLocator.getInstancia();

			localHome = (ControladorIntegracaoLocalHome) locator
					.getLocalHome(ConstantesJNDI.CONTROLADOR_INTEGRACAO_SEJB);
			// guarda a referencia de um objeto capaz de fazer chamadas
			// objetos remotamente
			local = localHome.create();

			return local;
		} catch (CreateException e) {
			throw new SistemaException(e);
		} catch (ServiceLocatorException e) {
			throw new SistemaException(e);
		}

	}

}
