package ps.projekat.prikaziKontroleri;

import com.calendarfx.view.Messages;
import com.calendarfx.view.MonthView;
import com.calendarfx.view.page.MonthPage;
import com.calendarfx.view.page.PageBase;
import com.calendarfx.view.print.ViewType;
import impl.com.calendarfx.view.page.MonthPageSkin;
import javafx.scene.control.Skin;

import java.time.format.DateTimeFormatter;

public class Mjesec extends PageBase {

    private MonthView pogled;

    public Mjesec() {
        super();
        getStyleClass().add("month-page"); //$NON-NLS-1$
        this.pogled = new MonthView();
        setDateTimeFormatter(DateTimeFormatter.ofPattern(Messages.getString("MonthPage.DATE_FORMAT")));
    }

    @Override
    protected Skin<?> createDefaultSkin() {
        MonthPage page = new MonthPage();
        return new MonthPageSkin(page);
    }

    public MonthView getPogled() {
        return pogled;
    }

    public void setPogled(MonthView pogled) {
        this.pogled = pogled;
    }

    @Override
    public final void goForward() {
        setDate(getDate().plusMonths(1).withDayOfMonth(1));
    }

    @Override
    public final void goBack() {
        setDate(getDate().minusMonths(1).withDayOfMonth(1));
    }

    @Override
    public ViewType getPrintViewType() {
        return ViewType.MONTH_VIEW;
    }
}
