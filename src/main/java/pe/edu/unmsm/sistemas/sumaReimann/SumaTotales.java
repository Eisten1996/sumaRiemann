package pe.edu.unmsm.sistemas.sumaReimann;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedAbstractActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;

public class SumaTotales extends UntypedAbstractActor {

	private double sumaT = 0.0;
	private int nObreros = 0;
	private int iteraciones = 0;
	private int contador = 0;
	private double dx = 0.0;
	private int a = 0;
	private ActorRef[] actores;

	public SumaTotales(int A, int B, int N, int it) {
		// TODO Auto-generated constructor stub
		this.iteraciones = N;
		this.contador = it;
		this.nObreros = it;
		this.dx = (B - A) / (N * 1.0);
		this.a = A;
	}

	LoggingAdapter log = Logging.getLogger(this.getContext().getSystem(), this);

	@Override
	public void preStart() throws Exception {
		// TODO Auto-generated method stub
		log.info("Con {} iteraciones y {} actores", iteraciones, nObreros);
		super.preStart();
		actores = new ActorRef[nObreros];
		for (int i = 0; i < nObreros; i++) {
			actores[i] = this.getContext().actorOf(Props.create(SumaParcial.class), ("Obrero" + i));
		}
		int valor = iteraciones / nObreros;
		for (int j = 0; j < nObreros; j++) {
			actores[j].tell(new Tarea((valor * j), (valor * (j + 1)), dx, a), this.getSelf());
		}
	}

	@Override
	public void onReceive(Object message) throws Throwable {
		// TODO Auto-generated method stub
		if (message.getClass() == Double.class) {
			if (contador > 1) {
				sumaT += (Double) message;
				contador--;
			} else {
				sumaT += (Double) message;
				log.info("Resultado: " + sumaT);
				this.getContext().stop(getSelf());
			}
		}
	}
}