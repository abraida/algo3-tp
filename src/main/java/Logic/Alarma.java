package Logic;

import Controller.EfectoVisitor;

import java.time.LocalDateTime;


public abstract class Alarma implements Identificable {
	protected Efecto efecto;
	protected long id;
	private boolean fueRealizada = false;

	public Alarma(Efecto efecto){
		this.efecto = efecto;
	}
	public abstract LocalDateTime getTiempo(LocalDateTime tiempo);

	public EfectoEnum disparar() {
		return this.efecto.realizar();
	}

	public void recibir(EfectoVisitor v){
		if (!fueRealizada){
			this.efecto.recibir(v);
			fueRealizada = true;
		}
	}
	@Override
	public long getID() {
		return this.id;
	}

	@Override
	public void setID(long id) {
		this.id = id;
	}

}