package es.usal.pa.agentes_divertidos.agents;

import es.usal.pa.agentes_divertidos.behaviours.OneShotArbitro;
import jade.content.lang.sl.SLCodec;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;

public class Arbitro extends AgenteComunicador {
	private static final long serialVersionUID = 1L;
	
	public void setup() {
		DFAgentDescription dfd = new DFAgentDescription();
		dfd.setName(getAID());

		ServiceDescription sd = new ServiceDescription();
		sd.setName("Arbitro");
		sd.setType("jugadoresdisponibles");
		sd.addOntologies("ontología");
		sd.addLanguages(new SLCodec().getName());

		dfd.addServices(sd);

		try {
			DFService.register(this, dfd);
		} catch (FIPAException e) {
			System.err.println("Error : Arbitro.setup : Agent " + getLocalName() + ": " + e.getMessage());
		}
		
		addBehaviour(new OneShotArbitro());
	}
}