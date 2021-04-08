package ps.projekat.podaci.model;

import ps.projekat.podaci.dto.NalogAdministratora;

import java.util.List;

public class NalogFabrika extends AbstraktnaFabrika<NalogAdministratora>{

    @Override
    public NalogAdministratora action(TipAkcije tipAkcije, List<String> data) {
        return null;
    }
    public void kreirajNalog(List<String> data){

    }
    public void izmjeniNalog(List<String> data){

    }
    public void izbrisiNalog(){

    }
}
