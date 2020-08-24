package pe.edu.unmsm.sistemas.sumaReimann;

import static java.lang.Math.pow;

import akka.actor.ActorRef;
import akka.actor.UntypedAbstractActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;

public class SumaParcial extends UntypedAbstractActor {

	ActorRef aPrincipal;
	LoggingAdapter log = Logging.getLogger(this.getContext().getSystem(), this);

	@Override
	public void onReceive(Object message) throws Throwable {
		// TODO Auto-generated method stub
		if (message.getClass() == Tarea.class) {
			double resultado = sumaReimann((Tarea) message);
			aPrincipal = this.getSender();
			aPrincipal.tell(resultado, this.getSelf());
		}
	}

	private double sumaReimann(Tarea t) {
		double sum = 0.0;
		double xi = 0.0;
		for (int k = t.ini; k < t.fin; k++) {
			// log.info("Valor de {} ", t.dx);
			xi = (t.dx) * (k + 1) + t.a;
			sum += funcion(xi);
		}
		log.info("Valor de {} a {} es {}", t.ini, t.fin, sum * t.dx);
		return sum * t.dx;
	}

	private double funcion(double x) {
		return 3 * pow(x, 3) + 2 * pow(x, 2) + 1;
	}

}