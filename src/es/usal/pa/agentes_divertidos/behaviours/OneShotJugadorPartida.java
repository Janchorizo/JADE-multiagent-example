package es.usal.pa.agentes_divertidos.behaviours;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import es.usal.pa.agentes_divertidos.agents.AgenteComunicador;
import jade.core.behaviours.OneShotBehaviour;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.UnreadableException;

public class OneShotJugadorPartida extends OneShotBehaviour {
	private static final long serialVersionUID = 1L;
	private boolean finished = false;
	
	public void action() {
		int partida = 0, puntos = 0, tirada = 0, i = 0;
		Random rand = new Random();
		ArrayList<Integer> tiradas;
		
		for(partida=0; partida<3; partida+=1) {
			tiradas = new ArrayList<Integer>();
			tirada = rand.nextInt(10);
			
			DFAgentDescription[] jugadores = ((AgenteComunicador)this.myAgent).findAgents("Jugador", "jugador");
			for(i=0; i<jugadores.length; i+=1) {
				((AgenteComunicador)this.myAgent).sendMsg(String.valueOf(tirada), jugadores[i]);
			}
			
			
			for(i=0; i<jugadores.length; i+=1) {
				ACLMessage msg = ((AgenteComunicador)this.myAgent).recieveMsg();
				String content = "";
			
				try {
					content = (String) (msg.getContentObject());
				} catch (UnreadableException e) {
					System.err.println("Error : AgenteComunicador.receiveMsg : UnreadableException");
				}
				
				tiradas.add(Integer.valueOf(content).intValue());
			}
			
			
			System.out.println("-----------( Tirada "+partida+" )-----------");
			System.out.println("Mi tirada salió "+tirada+" y las del resto :");
			for(i=0; i<jugadores.length; i+=1)
				System.out.println(" > "+tiradas.get(i));
			System.out.println("La máxima ha sido "+Collections.max(tiradas));
			if(tirada == Collections.max(tiradas))
				puntos += 1;
		}
		
		System.out.println("He conseguido ganar "+puntos+" partidas!");
		
		finished = true;
	}

	public boolean Done() {
		return finished;
	}
}
