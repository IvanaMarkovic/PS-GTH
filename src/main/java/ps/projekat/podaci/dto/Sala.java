package ps.projekat.podaci.dto;

import javax.persistence.*;

@Entity
@Table(name = "sala")
public class Sala {

    @Id
    @Column(name = "IdSale")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idSale;

    @Column(name = "NazivSale")
    private String nazivSale;

    @Column(name = "OznaciObrisan")
    private boolean oznaciObrisan;

    public Sala() {
        oznaciObrisan = false;
    }

    public Sala(String nazivSale) {
        this();
        this.nazivSale = nazivSale;
    }

    public long getIdSale() {
        return idSale;
    }

    public void setIdSale(long idSale) {
        this.idSale = idSale;
    }

    public String getNazivSale() {
        return nazivSale;
    }

    public void setNazivSale(String nazivSale) {
        this.nazivSale = nazivSale;
    }

    public boolean isOznaciObrisan() {
        return oznaciObrisan;
    }

    public void setOznaciObrisan(boolean oznaciObrisan) {
        this.oznaciObrisan = oznaciObrisan;
    }



    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + (int) (idSale ^ (idSale >>> 32));
        return result;
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
        Sala other = (Sala) o;
        if (idSale != other.idSale)
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "Sala{" +
                "id='" + idSale + '\'' +
                ", nazivSale='" + nazivSale + '\'' +
                ", oznaciObrisan=" + oznaciObrisan +
                '}';
    }
}
