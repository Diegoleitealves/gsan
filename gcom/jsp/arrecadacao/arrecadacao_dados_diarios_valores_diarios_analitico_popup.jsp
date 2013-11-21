<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-template.tld" prefix="template"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.Collection"%>
<%@ page import="java.math.BigDecimal,gcom.arrecadacao.ArrecadacaoDadosDiarios,gcom.util.Util,java.util.Date,java.util.Calendar,java.util.GregorianCalendar" %>
<%@ page import="gcom.arrecadacao.DevolucaoDadosDiarios, gcom.arrecadacao.bean.FiltrarDadosDiariosArrecadacaoHelper, gcom.arrecadacao.bean.FormasArrecadacaoDadosDiariosHelper" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<html:html>

<head>
<script language="JavaScript"
	src="<bean:message key="caminho.js"/>util.js"></script>
<script language="JavaScript"
	src="<bean:message key="caminho.js"/>validacao/ManutencaoRegistro.js"></script>

<title></title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link rel="stylesheet"
	href="<bean:message key="caminho.css"/>EstilosCompesa.css"
	type="text/css">
<SCRIPT LANGUAGE="JavaScript">
<!--

function fechar(){
		window.close();
-->		
}
</SCRIPT>
</head>
<body leftmargin="5" topmargin="5">
<html:form action="/exibirConsultarDadosDiariosValoresDiariosAnaliticoAction.do"
	name="FiltrarDadosDiariosArrecadacaoActionForm"
	type="gcom.gui.arrecadacao.FiltrarDadosDiariosArrecadacaoActionForm"
	method="post">
	<table width="670" border="0" cellpadding="0" cellspacing="5">
		<tr>
			<td width="670" valign="top" class="centercoltext">
			<table height="100%">
				<tr>
					<td></td>
				</tr>
			</table>
			<table width="100%" border="0" align="center" cellpadding="0"
				cellspacing="0">
				<tr>
					<td width="11"><img border="0"
						src="<bean:message key="caminho.imagens"/>parahead_left.gif" /></td>
					<td class="parabg">Consultar Dados Di�rios da Arrecada��o - Valores
					Di�rios - Analitico</td>
					<td width="11"><img border="0"
						src="<bean:message key="caminho.imagens"/>parahead_right.gif" /></td>
				</tr>
				<tr>
					<td>&nbsp;</td>
				</tr>
			</table>
			<table width="100%" border="0">
				<tr>
					<td height="10">
					<table width="100%" border="0">
						<%
						BigDecimal valorTotal = (BigDecimal)session.getAttribute("valorTotal");
						String referencia = (String)session.getAttribute("referencia");
						String dadosMesInformado = (String)request.getAttribute("dadosMesInformado");
						String dadosAtual = (String)request.getAttribute("dadosAtual");
						String nomeGerencia = (String)request.getAttribute("nomeGerencia");
						String descricaoLocalidade = (String)request.getAttribute("descricaoLocalidade");
						String descricaoElo = (String)request.getAttribute("descricaoElo");
						String nomeAgente = (String)request.getAttribute("nomeAgente");
						String nomeCategoria = (String)request.getAttribute("nomeCategoria");
						String nomePerfil = (String)request.getAttribute("nomePerfil");
						String nomeDocumento = (String)request.getAttribute("nomeDocumento");
						String nomeArrecadacaoForma = (String)request.getAttribute("nomeArrecadacaoForma");
						
						String nomeUnidadeNegocio = (String)request.getAttribute("nomeUnidadeNegocio");
						String mostraUnidadeGerencia = (String)request.getAttribute("mostraUnidadeGerencia");
						
						%>
					<tr bgcolor="#90c7fc">
							<td colspan="4">
								<table width="100%">
									<tr>
										<td><strong>Processamento Definitivo: 
											<%= dadosMesInformado %>
											</strong>
									    </td>
										<td>
											<div align="right"><strong>M&ecirc;s/Ano:</strong><strong>
											<%= Util.formatarAnoMesParaMesAno(referencia) %></strong></div>
										</td>
									</tr>
								</table>
							</td>
						</tr>
						<tr bgcolor="#90c7fc">
							<td colspan="4">
								<table width="100%">
									<tr>
										<td><strong>�ltimo Processamento Atual: 
											<%= dadosAtual %>
											</strong>
									    </td>

									</tr>
								</table>
							</td>
						</tr>
						<% 
						if (nomeGerencia != null)
						{
						%>
							<tr>
				                <td width="15%"><strong>Ger&ecirc;ncia:</strong></td>
				                <td width="85%" align="left"><strong> <%= nomeGerencia %></strong></td>
			                </tr>
						<%
						}
						if (nomeUnidadeNegocio != null)
						{
						%>
							<tr>
					            <td width="25%"><strong>Unidade Neg�cio:</strong></td>
					            <td width="75%" align="left"><strong> <%= nomeUnidadeNegocio %></strong></td>
				            </tr>
						<%
						}
						
						
						if (descricaoElo != null)
						{
						%>
							<tr>
					            <td width="15%"><strong>Localidade P�lo</strong></td>
					            <td width="85%" align="left"><strong> <%= descricaoElo %></strong></td>
				            </tr>
						<%
						}
						if (descricaoLocalidade != null)
						{
						%>
						   <tr>
					          <td width="15%"><strong>Localidade:</strong></td>
					          <td width="85%" align="left"><strong> <%= descricaoLocalidade %></strong></td>
				           </tr>
						<%
						}
						if (nomeAgente != null)
						{
						%>
							<tr>
						        <td width="15%"><strong>Banco:</strong></td>
						    	<td width="85%" align="left"><strong> <%= nomeAgente %></strong></td>
					    	</tr>
						<%
						}
						if (nomeCategoria != null)
						{
						%>
							<tr>
						        <td width="15%"><strong>Categoria:</strong></td>
						    	<td width="85%" align="left"><strong> <%= nomeCategoria %></strong></td>
					    	</tr>
						<%
						}
						if (nomePerfil != null)
						{
						%>
							<tr>
						        <td width="15%"><strong>Perfil:</strong></td>
						    	<td width="85%" align="left"><strong> <%= nomePerfil %></strong></td>
					    	</tr>
						<%
						}
						if (nomeDocumento != null)
						{
						%>
							<tr>
						        <td width="25%"><strong>Tipo do Documento:</strong></td>
						    	<td width="75%" align="left"><strong> <%= nomeDocumento %></strong></td>
					    	</tr>
						<%
						}
						if (nomeArrecadacaoForma != null)
						{
						%>
							<tr>
						        <td width="25%"><strong>Forma de arrecada��o:</strong></td>
						    	<td width="75%" align="left"><strong> <%= nomeArrecadacaoForma %></strong></td>
					    	</tr>
						<%
						}
						
						%>
					</table>
					<table width="100%" border="0">
						<tr bgcolor="#cbe5fe">
							<td width="82%" align="right">
							<strong>Valor:</strong>
							</td>
							<td align="right" width="18%">
								<strong><%= Util.formatarMoedaReal(valorTotal) %></strong>
							</td>
						</tr>
					</table>
					<table width="100%" bgcolor="#99CCFF" dwcopytype="CopyTableRow">
						<!--header da tabela interna -->
						<tr bordercolor="#FFFFFF" bgcolor="#90c7fc">
							<td width="25%">
							<div align="center" class="style9">
							<font color="#000000" style="font-size:12px"
								face="Verdana, Arial, Helvetica, sans-serif">
								<strong>Data</strong></font></div>
							</td>
							<td width="25%">
							<div align="center" class="style9">
							<font color="#000000" style="font-size:12px"
								face="Verdana, Arial, Helvetica, sans-serif">
								<strong>Quant Doc</strong></font></div>
							</td>							
							<td width="25%">
							<div align="center" class="style9">
							<font color="#000000" style="font-size:12px"
								face="Verdana, Arial, Helvetica, sans-serif">
							<strong>Quant Pag</strong></font></div>
							</td>							
							<td width="25%">
							<div align="center" class="style9">
							<font color="#000000" style="font-size:12px"
								face="Verdana, Arial, Helvetica, sans-serif">
								<strong>D�bitos</strong></font></div>
							</td>							
							<td width="25%">
							<div align="center" class="style9">
							<font color="#000000" style="font-size:12px"
								face="Verdana, Arial, Helvetica, sans-serif">
								<strong>Descontos</strong></font></div>
							</td>							
							<td width="25%">
							<div align="center" class="style9">
							<font color="#000000" style="font-size:12px"
								face="Verdana, Arial, Helvetica, sans-serif">
								<strong>Valor Arrecadado</strong></font></div>
							</td>							
							<td width="25%">
							<div align="center" class="style9">
							<font color="#000000" style="font-size:12px"
								face="Verdana, Arial, Helvetica, sans-serif">
								<strong>Devolu��es</strong></font></div>
							</td>							
							<td width="25%">
							<div align="center" class="style9">
							<font color="#000000" style="font-size:12px"
								face="Verdana, Arial, Helvetica, sans-serif">
								<strong>Arrecada��o L�quida</strong></font></div>
							</td>							
							<td width="25%">
							<div align="center" class="style9">
							<font color="#000000" style="font-size:12px"
								face="Verdana, Arial, Helvetica, sans-serif">
								<strong>%</strong></font></div>
							</td>							
							<td width="25%">
							<div align="center" class="style9">
							<font color="#000000" style="font-size:12px"
								face="Verdana, Arial, Helvetica, sans-serif">
								<strong>Valor at� o Dia</strong></font></div>
							</td>							
							<td width="25%">
							<div align="center" class="style9">
							<font color="#000000" style="font-size:12px"
								face="Verdana, Arial, Helvetica, sans-serif"><strong>%</strong></font></div>
							</td>														
						</tr>
						<% 
						BigDecimal valorDia = new BigDecimal("0.00");
						BigDecimal valorDiaFA = new BigDecimal("0.00");
						%>
						<logic:present name="colecaoDadosDiarios">
						<logic:iterate name="colecaoDadosDiarios" id="itemHelper" type="FiltrarDadosDiariosArrecadacaoHelper">
							<tr bgcolor="#FFFFFF">
					
							<%

							Date data = (Date) itemHelper.getItemAgrupado();
							String quantidadeDocumentos = Util.agruparNumeroEmMilhares(itemHelper.getQuantidadeDocumentos());
							String quantidadePagamentos = Util.agruparNumeroEmMilhares(itemHelper.getQuantidadePagamentos());
							BigDecimal valorArrecadadoBruto = itemHelper.getValorDebitos();
							BigDecimal valorDescontos = itemHelper.getValorDescontos();
							BigDecimal valorDevolucoes = itemHelper.getValorDevolucoes();
							BigDecimal valorArrecadado = itemHelper.getValorArrecadacao();
							BigDecimal valorArrecadadoLiquido = itemHelper.getValorArrecadacaoLiquida();
							Collection<FormasArrecadacaoDadosDiariosHelper> colecaoFormasArrecadacao = itemHelper.getColecaoFormasArrecadacao();
							
							Calendar calendarioDataPagamento = new GregorianCalendar();
							calendarioDataPagamento.setTime(data);
							
							String anoMesDataPagamento = "";
							String corFont = "";
							if ((calendarioDataPagamento.get(Calendar.MONTH) + 1) < 10) {
								anoMesDataPagamento = calendarioDataPagamento.get(Calendar.YEAR)+ "0"+ (calendarioDataPagamento.get(Calendar.MONTH) + 1);
							} else {
								anoMesDataPagamento = calendarioDataPagamento.get(Calendar.YEAR)+ "" + (calendarioDataPagamento.get(Calendar.MONTH) + 1);
							}
							if(!anoMesDataPagamento.equals(referencia)){
								corFont = "#CC0000";
							} else {
								corFont = "#000000";
							}
							
							%>
							<td height="17" align="center">												 
								 <font color="<%=corFont%>" style="font-size:12px"> 
								 		<div align="right"><%= Util.formatarData(data) %></div>
								 </font>									
							</td>
							<td nowrap="nowrap">
								 <font color="<%=corFont%>" style="font-size:12px"> 
								 		<div align="right"><%= quantidadeDocumentos == null ? "" : quantidadeDocumentos + "" %></div>
								 </font>																
							</td>
							<td nowrap="nowrap">
								 <font color="<%=corFont%>" style="font-size:12px"> 
								 		<div align="right"><%= quantidadePagamentos == null ? "" : quantidadePagamentos + "" %></div>
								 </font>																
							</td>
							<td nowrap="nowrap">
								<font color="<%=corFont%>" style="font-size:12px"> 
									<div align="right"><%= Util.formatarMoedaReal(valorArrecadadoBruto) %></div>
								 </font>									
							</td>
							<td nowrap="nowrap">
								<font color="<%=corFont%>" style="font-size:12px"> 
									<div align="right"><%= Util.formatarMoedaReal(valorDescontos) %></div>
								 </font>									
							</td>
							<td nowrap="nowrap">
								<font color="<%=corFont%>" style="font-size:12px"> 
									<div align="right"><%= Util.formatarMoedaReal(valorArrecadado) %></div>
								 </font>									
							</td>							
							<td nowrap="nowrap">
								<font color="<%=corFont%>" style="font-size:12px"> 
									<div align="right"><%= Util.formatarMoedaReal(valorDevolucoes) %></div>
								 </font>									
							</td>
							<td nowrap="nowrap">
								<font color="<%=corFont%>" style="font-size:12px"> 
									<div align="right"><%= Util.formatarMoedaReal(valorArrecadadoLiquido) %></div>
								 </font>									
							</td>							
							<% 
								BigDecimal percentualMultiplicacao = itemHelper.getValorDebitos().multiply(new BigDecimal("100.00"));

								BigDecimal percentual = percentualMultiplicacao.divide(
										valorTotal,2,BigDecimal.ROUND_HALF_UP);

								valorDia = itemHelper.getValorArrecadacaoLiquida().add(valorDia);
								
								BigDecimal percentualMultiplicacaoDoDia = valorDia.multiply(new BigDecimal("100.00"));

								BigDecimal percentualDoDia = percentualMultiplicacaoDoDia.divide(
										valorTotal,2,BigDecimal.ROUND_HALF_UP);
							%>								
							<td nowrap="nowrap">
								<font color="<%=corFont%>" style="font-size:12px"> 
									<div align="right"><%= Util.formatarMoedaReal(percentual) %> %</div>
								 </font>									
							</td>
							<td nowrap="nowrap">
								<font color="<%=corFont%>" style="font-size:12px"> 
									<div align="right"><%= Util.formatarMoedaReal(valorDia) %></div>
								 </font>									
							</td>
							<td nowrap="nowrap">
								<font color="<%=corFont%>" style="font-size:12px"> 
									<div align="right"><%= Util.formatarMoedaReal(percentualDoDia) %> %</div>
								 </font>									
							</td>
						</tr>
									<% 
										if(colecaoFormasArrecadacao != null) {%>
											<%Iterator colecaoIterator = colecaoFormasArrecadacao.iterator();
											while(colecaoIterator.hasNext()) { %>
												<% FormasArrecadacaoDadosDiariosHelper helper = (FormasArrecadacaoDadosDiariosHelper) colecaoIterator.next(); %>
										<tr bgcolor="#80bffd">
											<td height="17" align="left">
												<font color="<%=corFont%>" style="font-size:11px"> 
													<div align="right"><%= helper.getDescricaoArrecadador() %> </div>
								 				</font>
											</td>
											<td nowrap="nowrap">
												<font color="<%=corFont%>" style="font-size:11px"> 
													<div align="right"><%= helper.getQtdeDocumentos() %></div>
								 				</font>
											</td>
											<td nowrap="nowrap">
												<font color="<%=corFont%>" style="font-size:11px"> 
													<div align="right"><%= helper.getQtdePagamentos() %></div>
								 				</font>
											</td>
											<td nowrap="nowrap">
												<font color="<%=corFont%>" style="font-size:11px"> 
													<div align="right"><%= Util.formatarMoedaReal(helper.getDebitos()) %></div>
								 				</font>
											</td>
											<td nowrap="nowrap">
												<font color="<%=corFont%>" style="font-size:11px"> 
													<div align="right"><%= Util.formatarMoedaReal(helper.getDescontos()) %></div>
								 				</font>
											</td>
											<td nowrap="nowrap">
												<font color="<%=corFont%>" style="font-size:11px"> 
													<div align="right"><%= Util.formatarMoedaReal(helper.getValorArrecadado()) %></div>
								 				</font>
											</td>
											<td nowrap="nowrap">
												<font color="<%=corFont%>" style="font-size:11px"> 
													<div align="right"><%= Util.formatarMoedaReal(helper.getDevolucoes()) %></div>
								 				</font>
											</td>
											<td nowrap="nowrap">
												<font color="<%=corFont%>" style="font-size:11px"> 
													<div align="right"><%= Util.formatarMoedaReal(helper.getArrecadacaoLiquida()) %></div>
								 				</font>
											</td>
											<% 
												BigDecimal percentualMultiplicacaoFA = helper.getDebitos().multiply(new BigDecimal("100.00"));

												BigDecimal percentualFA = percentualMultiplicacaoFA.divide(
														valorTotal,2,BigDecimal.ROUND_HALF_UP);
												
												valorDiaFA = helper.getArrecadacaoLiquida().add(valorDiaFA);

												BigDecimal percentualMultiplicacaoDoDiaFA = valorDiaFA.multiply(new BigDecimal("100.00"));

												BigDecimal percentualDoDiaFA = percentualMultiplicacaoDoDiaFA.divide(
														valorTotal,2,BigDecimal.ROUND_HALF_UP);
											%>	
											<td nowrap="nowrap">
												<font color="<%=corFont%>" style="font-size:11px"> 
													<div align="right"><%= Util.formatarMoedaReal(percentualFA) %> %</div>
								 				</font>
											</td>
											<td nowrap="nowrap">
												<font color="<%=corFont%>" style="font-size:11px"> 
													<div align="right"><%= Util.formatarMoedaReal(valorDiaFA) %></div>
								 				</font>
											</td>
										
											<td nowrap="nowrap">
												<font color="<%=corFont%>" style="font-size:12px"> 
													<div align="right"><%= Util.formatarMoedaReal(percentualDoDiaFA) %> %</div>
								 				</font>
											</td>
										</tr>
										<%
											}%>
										<%} %>
						
					</logic:iterate>
					</logic:present>
					</table>
					<p></p>

					<p>&nbsp;</p>
					</td>
				</tr>
			</table>
			<table border="0" width="100%">
				<tr>
					<td colspan="3">&nbsp;</td>
				</tr>
				<tr>
					<td colspan="2" align="left">
					<input name="ButtonCancelar" type="button"
						class="bottonRightCol" value="Voltar"
						onClick="javascript:window.history.go(-1);">
						</td>
					<td align="right">
					<input name="Button" type="button"
						class="bottonRightCol" value="Fechar"
						onClick="javascript:fechar();"></td>
				</tr>
				<p>&nbsp;</p>
			</table>
			</td>
		</tr>
	</table>
</html:form>
<body>
</html:html>
