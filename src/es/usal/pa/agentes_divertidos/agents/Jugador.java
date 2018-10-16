package es.usal.pa.agentes_divertidos.agents;

import es.usal.pa.agentes_divertidos.behaviours.OneShotJugadorComienzo;
import es.usal.pa.agentes_divertidos.behaviours.OneShotJugadorPartida;
import jade.content.lang.sl.SLCodec;
import jade.core.behaviours.SequentialBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;

public class Jugador extends AgenteComunicador {
	private static final long serialVersionUID = 1L;
	
	public void setup() {
		DFAgentDescription dfd = new DFAgentDescription();
		dfd.setName(getAID());

		ServiceDescription sd = new ServiceDescription();
		sd.setName("Jugador");
		sd.setType("jugador");
		sd.addOntologies("ontología");
		sd.addLanguages(new SLCodec().getName());

		dfd.addServices(sd);

		try {
			DFService.register(this, dfd);
		} catch (FIPAException e) {
			System.err.println("Error : Jugador.setup : Agent " + getLocalName() + ": " + e.getMessage());
		}
		
		SequentialBehaviour seq = new SequentialBehaviour();
		seq.addSubBehaviour(new OneShotJugadorComienzo());
		seq.addSubBehaviour(new OneShotJugadorPartida());
		addBehaviour(seq);
	}
}
