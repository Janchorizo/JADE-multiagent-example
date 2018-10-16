package es.usal.pa.agentes_divertidos.behaviours;

import es.usal.pa.agentes_divertidos.agents.AgenteComunicador;
import jade.core.behaviours.OneShotBehaviour;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.UnreadableException;

public class OneShotJugadorComienzo extends OneShotBehaviour {
	private static final long serialVersionUID = 1L;
	private boolean finished = false;
	
	public void action() {
		try {
			DFAgentDescription arbitro = ((AgenteComunicador)this.myAgent).findAgents("Arbitro", "jugadoresdisponibles")[0];
			((AgenteComunicador)this.myAgent).sendMsg("Hola arbitro", arbitro);
		}catch(Exception e){
			System.err.println("Error : OneShotJugador.action : "+e.toString());
		}
		
		ACLMessage msg = ((AgenteComunicador)this.myAgent).recieveMsg();
		String content = "";
		
		try {
			content = (String) (msg.getContentObject());
		} catch (UnreadableException e) {
			System.err.println("Error : AgenteComunicador.receiveMsg : UnreadableException");
		}
		
		System.out.println("Me han contestado que ' "+content+" '");
		
		finished = true;
	}

	public boolean Done() {
		return finished;
	}
}
