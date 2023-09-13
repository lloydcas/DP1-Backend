package pucp.edu.pe.sap_backend.Ruta;

import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.ArrayList;
@ToString
@EqualsAndHashCode
public class Blocknode {
    //@Id
    //@GeneratedValue(strategy = GenerationType.IDENTITY)
    //@Column(name = "id", nullable = false)
    //private Long id;

    private ArrayList<LocalDateTime> startTime;
    private ArrayList<LocalDateTime> endTime;
    private Boolean bloqueado;
    public Blocknode() {
        this.startTime = new ArrayList<>();
        this.endTime = new ArrayList<>();
        this.bloqueado = true;
    }


    public ArrayList<LocalDateTime> getStartTime() {
        return startTime;
    }

    public void setStartTime(ArrayList<LocalDateTime> startTime) {
        this.startTime = startTime;
    }

    public ArrayList<LocalDateTime> getEndTime() {
        return endTime;
    }

    public void setEndTime(ArrayList<LocalDateTime> endTime) {
        this.endTime = endTime;
    }

    public Boolean getBloqueado() {
        return bloqueado;
    }
}

