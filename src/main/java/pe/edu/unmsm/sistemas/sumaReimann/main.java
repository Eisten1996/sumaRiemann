package pe.edu.unmsm.sistemas.sumaReimann;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;

public class main {

	public static void main(String[] args) {
		ActorSystem actores = ActorSystem.create("ValorReiman");
		ActorRef main = actores.actorOf(Props.create(SumaTotales.class, 1, 6, 100, 10));
	}

}
