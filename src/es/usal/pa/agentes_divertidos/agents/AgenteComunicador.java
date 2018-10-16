package es.usal.pa.agentes_divertidos.agents;

import java.io.IOException;
import java.io.Serializable;

import jade.content.lang.sl.SLCodec;
import jade.core.AID;
import jade.core.Agent;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.Envelope;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;


/**
 * AgenteComunicador
 * @author AlejandroRodríguezDí
 * Class for modeling a JADE Agent with basic search and communication functionality.
 */
public class AgenteComunicador extends Agent {
	private static final long serialVersionUID = 1L;

	public AgenteComunicador() {
		// TODO Auto-generated constructor stub
	}

	
	/**
	 * sendMsg
	 * Sends an ACLMessage which's content is the given String.
	 * Sends a message from the current instance using the default ontology
	 *  
	 * @param content String with the content wished to be sent.
	 * @param dest DFAgentDescription of the agent whom to send the message.
	 * @return Returns a 0 if the message was corretly sent.
	 */
	public int sendMsg(String content, DFAgentDescription dest) {
		int sent = -1;
		
		if(!content.isEmpty() && dest != null) {
			ACLMessage msg = new ACLMessage(ACLMessage.INFORM);
			
				try {
					msg.addReceiver(dest.getName());
					msg.setLanguage(new SLCodec().getName());
					msg.setEnvelope(new Envelope());
					msg.getEnvelope().setPayloadEncoding("ISO8859_1");
					msg.setOntology("ontology");
					msg.setContentObject((Serializable) content);

					this.send(msg);
				} catch (IOException e) {
					System.err.println("Error : AgenteComunicador.sendMsg : IOException");
				}
			
			sent = 0;
		}
		
		return(sent);		
	}
	
	/**
	 * sendMsg
	 * Sends an ACLMessage which's content is the given String.
	 * Sends a message from the current instance using the default ontology
	 *  
	 * @param content String with the content wished to be sent.
	 * @param dest AID of the agent whom to send the message.
	 * @return Returns a 0 if the message was corretly sent.
	 */
	public int sendMsg(String content, AID dest) {
		int sent = -1;
		
		if(!content.isEmpty() && dest != null) {
			ACLMessage msg = new ACLMessage(ACLMessage.INFORM);
			
				try {
					msg.addReceiver(dest);
					msg.setLanguage(new SLCodec().getName());
					msg.setEnvelope(new Envelope());
					msg.getEnvelope().setPayloadEncoding("ISO8859_1");
					msg.setOntology("ontology");
					msg.setContentObject((Serializable) content);

					this.send(msg);
				} catch (IOException e) {
					System.err.println("Error : AgenteComunicador.sendMsg : IOException");
				}
			
			sent = 0;
		}
		
		return(sent);		
	}
	
	/**
	 * receiveMsg
	 * Makes a blocking reception of an ACLMessage and returns the String content.
	 * @return ACLMessage content of the received message.
	 */
	public ACLMessage recieveMsg(){
		ACLMessage aclmsg;
		
		aclmsg = this
				.blockingReceive(MessageTemplate.or(MessageTemplate.MatchPerformative(ACLMessage.INFORM),
						MessageTemplate.MatchPerformative(ACLMessage.FAILURE)));

		if (aclmsg.getPerformative() == ACLMessage.FAILURE)
			System.out.println("Error : AgenteComunicador.receiveMsg : FAILURE");
		
		return(aclmsg);
	}
	
	/**
	 * findAgents
	 * Makes a search for all agents giving the given service.
	 * @param serviceName String with the name of the service desired.
	 * @param serviceType String with the type of the service desired.
	 * @return DFAgentDescription of each of the matching agents.
	 */
	public DFAgentDescription[] findAgents(String serviceName, String serviceType){
		DFAgentDescription template = new DFAgentDescription();
		ServiceDescription sd = new ServiceDescription();
		sd.setName(serviceName);
		sd.setType(serviceType);
		template.addServices(sd);
		DFAgentDescription[] agents = null;
		
		try {
			agents = DFService.search(this, template);
		} catch (FIPAException fe) {
			fe.printStackTrace();
		}
		return(agents);
	}
}
