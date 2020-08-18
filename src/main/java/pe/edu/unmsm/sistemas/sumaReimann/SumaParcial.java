package pe.edu.unmsm.sistemas.sumaReimann;

import akka.actor.ActorRef;
import akka.actor.UntypedAbstractActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;

public class SumaParcial extends UntypedAbstractActor {

	private ActorRef sumaTotal;
	static final double A = 1.0;
	static final double B = 6.0;
	static final double N = 10.0;

	public enum mensajes {
		YA_ME_CREE, CREATE
	}

	LoggingAdapter log = Logging.getLogger(this.getContext().getSystem(), this);

	@Override
	public void onReceive(Object message) throws Throwable {
		if (message != null) {
			Integer n = Integer.parseInt(message.toString());
			log.info("[Armero] recibe orden para crearse . . .");
			sumaTotal = this.getSender();
			sumaTotal.tell(calculo(A + n * 0.5, A + (n + 1) * 0.5, N / 10), this.getSelf());
			log.info("Suma parcial es : " + calculo(A + n * 0.5, A + (n + 1) * 0.5, N / 10));
		}
	}

	public double calculo(double a, double b, double n) {
		double dx = ((b - a) / n) * 1.0;
		double suma = 0.0; // suma
		double xi = 0.0; // xi
		for (double i = a; i <= b - (dx / 2); i += dx) {
			xi = i + (dx / 2);
			suma += dx * funcion(xi);
		}
		return suma;
	}

	public double funcion(double x) {
		return 3 * Math.pow(x, 3) + 2 * Math.pow(x, 2) + 1;
	}

}