package br.edu.ufcg.projetomelevamavem.testes;

import br.edu.ufcg.projetomelevamavem.controller.MeLevaController;
import easyaccept.EasyAcceptFacade;
import java.util.ArrayList;
import java.util.List;

/**
 * UFCG - CEEI - DSC Disciplina: Sistema de Informacao I. Professor: Nazareno.
 * 
 * Projeto SI1 2012.1.
 * 
 * Pacote testesProject Classe Main
 * 
 * Classe para execucao dos testes do EasyAcceppt.
 * 
 * @author Grupo do Projeto MeLeva
 * @version 1.0
 * 
 */

public class TesteEasyAccept {

	public static void main(String[] args) throws Exception {

		List<String> files = new ArrayList<String>();

		files.add("scripts/US01.txt");
		files.add("scripts/US02.txt");
		files.add("scripts/US03.txt");
		files.add("scripts/US04.txt");
		files.add("scripts/US05.txt");
		files.add("scripts/US06.txt");
		files.add("scripts/US07.txt");
		files.add("scripts/US08.txt");
		files.add("scripts/US09.txt");
		files.add("scripts/US10.txt");
		files.add("scripts/US11.txt");
		files.add("scripts/US12.txt");
		
		MeLevaController projeto = MeLevaController.getInstance();

		// Instantiate EasyAccept façade

		EasyAcceptFacade eaFacade = new EasyAcceptFacade(projeto, files);

		// Execute the tests

		eaFacade.executeTests();

		// Print the tests execution results

		System.out.println(eaFacade.getCompleteResults());

	}

}
