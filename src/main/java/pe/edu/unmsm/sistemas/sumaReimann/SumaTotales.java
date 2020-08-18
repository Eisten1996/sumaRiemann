package pe.edu.unmsm.sistemas.sumaReimann;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedAbstractActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;

public class SumaTotales extends UntypedAbstractActor {

	private ActorRef[] sumaParcial = new ActorRef[10];

	public enum mensajes {
		YA_ME_CREE, CREATE
	}

	LoggingAdapter log = Logging.getLogger(this.getContext().getSystem(), this);

	@Override
	public void preStart() throws Exception {

		log.info("[Suma] iniciando {} ", this.getSelf().path());
		super.preStart();
		for (int i = 0; i < 10; i++) {

			sumaParcial[i] = getContext().actorOf(Props.create(SumaParcial.class), "SumaParcial" + i);
			sumaParcial[i].tell(i + 1, this.getSelf());
		}

	}

	@Override
	public void onReceive(Object message) throws Throwable {

		if (message == mensajes.YA_ME_CREE) {
			log.info("[Soldado] Arma Lista . . . ");
			this.getContext().stop(getSelf());
		}

	}
}