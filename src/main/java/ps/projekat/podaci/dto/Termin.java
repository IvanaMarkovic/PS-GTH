package ps.projekat.podaci.dto;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Time;

@Entity
@Table(name = "termin")
public class Termin {

    @Id
    @Column(name = "IdTermina")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idTermina;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "IdRezervacije")
    private Rezervacija rezervacija;

    @Column(name = "IdSale")
    private long IdSale;

    @Column(name = "DatumIznajmljivanjaPocetak")
    private Date datumIznajmljivanjaPocetak;

    @Column(name = "VrijemeIznajmljivanjaPocetak")
    private Time vrijemeIznajmljivanjaPocetak;

    @Column(name = "VrijemeIznajmljivanjaKraj")
    private Time vrijemeIznajmljivanjaKraj;

    @Column(name = "OznaciObrisan")
    private boolean oznaciObrisan;

    public Termin() {
    }

    public Termin(long IdSale, Date datumIznajmljivanjaPocetak, Time vrijemeIznajmljivanjaPocetak, Time vrijemeIznajmljivanjaKraj) {
        this.IdSale =  IdSale;
        this.datumIznajmljivanjaPocetak = datumIznajmljivanjaPocetak;
        this.vrijemeIznajmljivanjaPocetak = vrijemeIznajmljivanjaPocetak;
        this.vrijemeIznajmljivanjaKraj = vrijemeIznajmljivanjaKraj;
        oznaciObrisan = false;
    }

    public long getIdTermina() {
        return idTermina;
    }

    public void setIdTermina(long idTermina) {
        this.idTermina = idTermina;
    }

    public Rezervacija getRezervacija() {
        return rezervacija;
    }

    public void setRezervacija(Rezervacija rezervacija) {
        this.rezervacija = rezervacija;
    }

    public Date getDatumIznajmljivanjaPocetak() {
        return datumIznajmljivanjaPocetak;
    }

    public void setDatumIznajmljivanjaPocetak(Date datumIznajmljivanjaPocetak) {
        this.datumIznajmljivanjaPocetak = datumIznajmljivanjaPocetak;
    }

    public Time getVrijemeIznajmljivanjaPocetak() {
        return vrijemeIznajmljivanjaPocetak;
    }

    public void setVrijemeIznajmljivanjaPocetak(Time vrijemeIznajmljivanjaPocetak) {
        this.vrijemeIznajmljivanjaPocetak = vrijemeIznajmljivanjaPocetak;
    }

    public Time getVrijemeIznajmljivanjaKraj() {
        return vrijemeIznajmljivanjaKraj;
    }

    public void setVrijemeIznajmljivanjaKraj(Time vrijemeIznajmljivanjaKraj) {
        this.vrijemeIznajmljivanjaKraj = vrijemeIznajmljivanjaKraj;
    }

    public long getIdSale() {
        return IdSale;
    }

    public void setIdSale(long IdSale) {
        this.IdSale = IdSale;
    }

    public boolean isOznaciObrisan() {
        return oznaciObrisan;
    }

    public void setOznaciObrisan(boolean oznaciObrisan) {
        this.oznaciObrisan = oznaciObrisan;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null) {
            return false;
        }
        if (getClass() != o.getClass()) {
            return false;
        }
        Termin termin = (Termin) o;
        if (getIdSale() != termin.getIdSale() || !datumIznajmljivanjaPocetak.equals(termin.datumIznajmljivanjaPocetak) || !vrijemeIznajmljivanjaPocetak.equals(vrijemeIznajmljivanjaPocetak)
            || !vrijemeIznajmljivanjaKraj.equals(vrijemeIznajmljivanjaKraj))
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        long objekat = (datumIznajmljivanjaPocetak.getTime() ^ (vrijemeIznajmljivanjaPocetak.getTime() & vrijemeIznajmljivanjaKraj.getTime()));
        result = prime * result + (int) (objekat ^ (objekat >>> 32));
        return result;
    }

    @Override
    public String toString() {
        return "Termin{" +
                ", idSale=" + IdSale +
                ", datumIznajmljivanjaPocetak=" + datumIznajmljivanjaPocetak +
                ", vrijemeIznajmljivanjaPocetak=" + vrijemeIznajmljivanjaPocetak +
                ", vrijemeIznajmljivanjaKraj=" + vrijemeIznajmljivanjaKraj +
                ", oznaciObrisan=" + oznaciObrisan +
                '}';
    }
}
