package ps.projekat.prikaziKontroleri;

import com.calendarfx.model.Calendar;
import com.calendarfx.model.Entry;
import com.calendarfx.view.CalendarSelector;
import com.calendarfx.view.CalendarView;
import com.calendarfx.view.Messages;
import javafx.beans.binding.Bindings;
import javafx.geometry.VPos;
import javafx.scene.control.TextField;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;

import java.util.List;
import static java.util.Objects.requireNonNull;


public class EntryZaglavlje extends GridPane {

    private final CalendarSelector sale;
    private Entry<?> entry;

    public EntryZaglavlje(Entry<?> entry, List<Calendar> calendars) {
        this.entry = requireNonNull(entry);
        requireNonNull(calendars);
        getStylesheets().add(CalendarView.class.getResource("calendar.css").toExternalForm());

        TextField klijent = new TextField(entry.getTitle());
        Bindings.bindBidirectional(klijent.textProperty(), entry.titleProperty());
        klijent.disableProperty().bind(entry.getCalendar().readOnlyProperty());
        klijent.getStyleClass().add("title");
        klijent.setPromptText("Klijent"); //$NON-NLS-1$
        klijent.setMaxWidth(500);

        TextField rezervacija = new TextField(entry.getLocation());
        Bindings.bindBidirectional(rezervacija.textProperty(), entry.locationProperty());
        rezervacija.getStyleClass().add("location");
        rezervacija.setEditable(true);
        rezervacija.setPromptText("Rezervacija");
        rezervacija.setMaxWidth(500);
        rezervacija.disableProperty().bind(entry.getCalendar().readOnlyProperty());

        sale = new CalendarSelector();
        sale.disableProperty().bind(entry.getCalendar().readOnlyProperty());
        sale.getCalendars().setAll(calendars);
        sale.setCalendar(entry.getCalendar());

        Bindings.bindBidirectional(sale.calendarProperty(), entry.calendarProperty());
        klijent.getStyleClass().add("default-style-entry-popover-title");
        add(klijent, 0, 0);
        add(sale, 1, 0, 1, 2);
        add(rezervacija, 0, 1);

        RowConstraints row1 = new RowConstraints();
        row1.setValignment(VPos.TOP);
        row1.setFillHeight(true);

        RowConstraints row2 = new RowConstraints();
        row2.setValignment(VPos.TOP);
        row2.setFillHeight(true);

        getRowConstraints().addAll(row1, row2);

        ColumnConstraints col1 = new ColumnConstraints();
        col1.setFillWidth(true);
        col1.setHgrow(Priority.ALWAYS);

        ColumnConstraints col2 = new ColumnConstraints();
        col2.setFillWidth(true);
        col2.setHgrow(Priority.NEVER);

        getColumnConstraints().addAll(col1, col2);
        getStyleClass().add("popover-header");

        Calendar calendar = entry.getCalendar();
        klijent.getStyleClass().add(calendar.getStyle() + "-entry-popover-title"); //$NON-NLS-1$

        entry.calendarProperty()
                .addListener((observable, oldCalendar, newCalendar) -> {
                    if (oldCalendar != null) {
                        klijent.getStyleClass().remove(oldCalendar.getStyle() + "-entry-popover-title"); //$NON-NLS-1$
                    }
                    if (newCalendar != null) {
                        klijent.getStyleClass().add(newCalendar.getStyle() + "-entry-popover-title"); //$NON-NLS-1$
                    }
                });
    }

    public final Calendar getCalendar() {

        Calendar calendar = sale.getCalendar();
        if (calendar == null) {
            calendar = entry.getCalendar();
        }
        return calendar;
    }

}