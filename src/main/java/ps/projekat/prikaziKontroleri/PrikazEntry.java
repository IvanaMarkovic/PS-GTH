package ps.projekat.prikaziKontroleri;

import com.calendarfx.model.Entry;
import com.calendarfx.view.CalendarView;
import com.calendarfx.view.DateControl;
import com.calendarfx.view.popover.*;
import javafx.beans.InvalidationListener;
import javafx.util.Duration;
import org.controlsfx.control.PopOver;

import java.util.Objects;

public class PrikazEntry extends PopOverContentPane {

    private Entry<?> entry;
    private DateControl dateControl;
    private PopOver popOver;

    public PrikazEntry(PopOver popOver, DateControl dateControl, Entry<?> entry) {
        getStylesheets().add(CalendarView.class.getResource("calendar.css").toExternalForm());

        this.popOver = popOver;
        this.dateControl = dateControl;
        this.entry = Objects.requireNonNull(entry);
        entry.setTitle(entry.getTitle().startsWith("New") ? "" : entry.getTitle());
        EntryDetalji detalji = new EntryDetalji(entry);
        PopOverTitledPane detaljiPrikaz = new PopOverTitledPane("Detalji", detalji);
        EntryZaglavlje header = new EntryZaglavlje(entry, dateControl.getCalendars());
        setHeader(header);

        if (Boolean.getBoolean("calendarfx.developer")) {
            EntryPropertiesView properties = new EntryPropertiesView(entry);
            PopOverTitledPane propertiesPane = new PopOverTitledPane("Properties", properties);
            getPanes().addAll(detaljiPrikaz, propertiesPane);
        } else {
            getPanes().addAll(detaljiPrikaz);
        }

        setExpandedPane(detaljiPrikaz);

        InvalidationListener listener = obs -> {
            if (entry.isFullDay() && !popOver.isDetached()) {
                popOver.setDetached(true);
            }
        };

        entry.fullDayProperty().addListener(listener);
        popOver.setOnHidden(evt -> entry.fullDayProperty().removeListener(listener));

        entry.calendarProperty().addListener(it -> {
            if (entry.getCalendar() == null) {
                popOver.hide(Duration.ZERO);
            }
        });
    }

    public final PopOver getPopOver() {
        return popOver;
    }

    public final DateControl getDateControl() {
        return dateControl;
    }

    public final Entry<?> getEntry() {
        return entry;
    }
}
