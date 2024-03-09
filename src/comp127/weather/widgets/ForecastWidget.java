package comp127.weather.widgets;

import comp127.weather.api.ForecastConditions;
import comp127.weather.api.WeatherData;
import edu.macalester.graphics.*;

import java.util.ArrayList;
import java.util.List;
import java.awt.Color;

public class ForecastWidget implements WeatherWidget {

    private final double size;
    private GraphicsGroup group;
    private GraphicsText temperature;
    private GraphicsText tempLow_tempHigh;
    private GraphicsText description;
    private GraphicsText date;
    private GraphicsText time;
    private Image icon;

    private final double BoxHeight;
    private final double BoxWidth;
    private final double BoxSpacing;

    private GraphicsGroup boxGroup;  // Holds all the ForecastBox objects

    private List<ForecastBox> boxes = new ArrayList<>();

    public ForecastWidget(double size) {
        this.size = size;
        


        group = new GraphicsGroup();

        temperature = new GraphicsText();
        temperature.setFont(FontStyle.BOLD, size * 0.08);
        group.add(temperature);

        tempLow_tempHigh = new GraphicsText();
        tempLow_tempHigh.setFont(FontStyle.PLAIN, size * 0.05);
        tempLow_tempHigh.setFillColor(Color.GRAY);
        group.add(tempLow_tempHigh);
        
        date = new GraphicsText();
        date.setFont(FontStyle.BOLD, size * 0.06);
        group.add(date);

        time = new GraphicsText();
        time.setFont(FontStyle.BOLD, size * 0.06);
        time.setAlignment(TextAlignment.RIGHT);
        group.add(time);

        description = new GraphicsText();
        description.setFont(FontStyle.PLAIN, size * 0.05);
        group.add(description);

        icon = new Image(0, 0);
        icon.setMaxWidth(size * 0.3);
        icon.setMaxHeight(size * 0.3);
        group.add(icon);

        boxGroup = new GraphicsGroup();
        group.add(boxGroup);

        BoxHeight = size * 0.05;
        BoxWidth = size * 0.04;
        BoxSpacing = size * 0.01;

        updateLayout();
    }

    @Override
    public GraphicsObject getGraphics() {
        return group;
    }

    public void update(WeatherData data) {
       
        boxGroup.removeAll();
        boxes.clear();  // Remove all the old ForecastBoxes from our list

        double spacingX = 0;
        double spacingY =0;

        for(ForecastConditions condition : data.getForecasts()){
            ForecastBox box = new ForecastBox(condition, 0, 0, BoxWidth, BoxHeight);
            boxGroup.add(box);
            box.setPosition(spacingX, spacingY);
            boxes.add(box);
            spacingX = spacingX + (BoxWidth + BoxSpacing);

            if (spacingX + BoxWidth > size){
                spacingX = 0;
                spacingY = BoxHeight + BoxSpacing;
            }
            
        }

        selectForecast(boxes.get(0));
    }

    private void selectForecast(ForecastBox box) {
        for(ForecastBox currentBox : boxes){
            if (currentBox.equals(box)) {
                currentBox.setActive(true);
            }
            else {
                currentBox.setActive(false);
            }
        }

        ForecastConditions forecast = box.getForecast();

        temperature.setText(FormattingHelpers.formatTemperature(forecast.getTemperature()) + "\u2109");   
        
        String minTemp = FormattingHelpers.formatTemperature(forecast.getMinTemperature());
        String maxTemp = FormattingHelpers.formatTemperature(forecast.getMaxTemperature());

        tempLow_tempHigh.setText(minTemp + "\u2109 | " + maxTemp+ "\u2109");

        icon.setImagePath(forecast.getWeatherIcon());
        description.setText(forecast.getWeatherDescription());

        if(forecast.getPredictionTime() != null) {
            time.setText(FormattingHelpers.TIME_OF_DAY.format(forecast.getPredictionTime()));
            date.setText(FormattingHelpers.WEEKDAY_AND_NAME.format(forecast.getPredictionTime()));
        }
        else {
            time.setText("-");
            date.setText("-");
        }

        updateLayout();
    }

    private void updateLayout() {
        icon.setCenter(size * 0.5, size * 0.3);

        time.setPosition(size, size * 0.05);
        time.setAlignment(TextAlignment.RIGHT);

        date.setPosition(0, size * 0.05);
        date.setAlignment(TextAlignment.LEFT);

        temperature.setCenter(size * 0.5, size * 0.5);

        tempLow_tempHigh.setCenter(size * 0.5,size * 0.6);

        description.setCenter(size * 0.5, size * 0.7);

        boxGroup.setPosition(0 + BoxSpacing, size - boxGroup.getHeight());
    }

    /**
     * Given a position in the widget, this returns the ForecastBox at that position if one exists
     *
     * @param location pos to check
     * @return null if not over a forecast box
     */
    private ForecastBox getBoxAt(Point location) {
        GraphicsObject obj = group.getElementAt(location);
        if (obj instanceof ForecastBox) {
            return (ForecastBox) obj;
        }
        return null;
    }

    /**
     * Updates the currently displayed forecast information as the mouse moves over the widget.
     * If there is not a ForecastBox at that position, the display does not change.
     */
    @Override
    public void onHover(Point position) {
        ForecastBox newBox = getBoxAt(position);
        if(newBox != null){
            selectForecast(newBox);
        }
    }
}
