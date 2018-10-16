package es.usal.pa.agentes_divertidos.behaviours;

import java.util.Scanner;

import es.usal.pa.agentes_divertidos.agents.AgenteComunicador;
import jade.core.AID;
import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.UnreadableException;

public class OneShotArbitro extends OneShotBehaviour {
	private static final long serialVersionUID = 1L;
	private boolean finished = false;
	private int jugadores = 0;
	
	public void action() {
		String str;
		Scanner sc = new Scanner(System.in);
		
		while(this.jugadores <= 0) {
			System.out.println("\nIntroduzca el número de jugadores : ");
			try{
				str = sc.nextLine();
				if(Integer.valueOf(str).intValue() >= 0)
					this.jugadores = Integer.valueOf(str).intValue();
			}catch (Exception err) {
				System.err.println("ERROR : Lectura");
			}	
		}
		sc.close();
		
		System.out.println("Esperando "+this.jugadores+" ...");

		int i;
		ACLMessage msg;
		String content;
		AID[] senders = new AID[this.jugadores];
		for(i=0; i<this.jugadores; i+=1) {
			msg = ((AgenteComunicador)this.myAgent).recieveMsg();
			content = "";
			
			try {
				content = (String) (msg.getContentObject());
			} catch (UnreadableException e) {
				System.err.println("Error : AgenteComunicador.receiveMsg : UnreadableException");
			}
			
			System.out.println("Respuesta ("+(i+1)+"/"+this.jugadores+") : "+content);
			senders[i] = msg.getSender();
		}
		
		for(i=0; i<this.jugadores; i+=1) {
			((AgenteComunicador)this.myAgent).sendMsg("Ya están los "+this.jugadores+" jugadores preparados.", senders[i]);
		}
				
		finished = true;
	}

	public boolean Done() {
		return finished;
	}
}
