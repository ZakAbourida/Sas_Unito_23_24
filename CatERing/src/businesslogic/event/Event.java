package businesslogic.event;

import java.util.List;

public class Event {
    private Service currentService;
    private List<EventDay> days;

    public boolean isExpectedService(Service service) {
        // Implementazione della logica per verificare se il servizio è previsto
        return false;
    }

    public Service getCurrentService() {
        return currentService;
    }

    public void setCurrentService(Service currentService) {
        this.currentService = currentService;
    }

    public List<EventDay> getDays() {
        return days;
    }

    public void setDays(List<EventDay> days) {
        this.days = days;
    }
}
