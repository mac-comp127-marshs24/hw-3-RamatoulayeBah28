package comp127.weather.widgets;

import java.util.List;

import comp127.weather.api.CurrentConditions;
import comp127.weather.api.ForecastConditions;
import comp127.weather.api.WeatherData;
import edu.macalester.graphics.*;

public class RainbowWeatherWidget implements WeatherWidget {
    private double size;
    private GraphicsGroup group;
    private Image rainbowIcon;
    private GraphicsText label;
    // private boolean isVisible;
    private GraphicsText description;


    public RainbowWeatherWidget(double size) {
        this.size = size;
        group = new GraphicsGroup();
        rainbowIcon = new Image(0, 0);
        rainbowIcon.setImagePath("condition-icons/rainbow.png");
        rainbowIcon.setMaxWidth(size);
        rainbowIcon.setMaxHeight(size * 0.5);
        group.add(rainbowIcon);
        label = new GraphicsText();
        label.setFont(FontStyle.BOLD, size * 0.1);
        group.add(label);

        description = new GraphicsText();
        description.setFont(FontStyle.PLAIN, size * 0.05);
        group.add(description);

        updateLayout();

    }

    public void update(WeatherData data) {

        // Check if the weather comditions are suitable for a rainbow and also in future forecasts

        CurrentConditions currentConditions = data.getCurrentConditions();
        List<ForecastConditions> futureConditions = data.getForecasts();
        if (currentConditions.isRainbowWeather()) {
            label.setText("Rainbow visible");
        }
        else {
            label.setText("No rainbow");
        }
        
        boolean rainbowComing = false;
        for (ForecastConditions forecast : futureConditions) {
            if (forecast.isRainbowWeatherComing()) {
                rainbowComing = true;
                break; 
            }
        }
        if (rainbowComing) {
            label.setText("Rainbow visible in a few days");
        } else {
            label.setText("No rainbow");
        }
    
        updateLayout();

    }

    private void updateLayout() {
        
        double temperatureLabelY = rainbowIcon.getBounds().getMaxY() + size * 0.02;
        label.setCenter(size * 0.5, temperatureLabelY);
        

        rainbowIcon.setCenter(size * 0.5, size * 0.4);

        label.setCenter(size * 0.5, size * 0.9);

        rainbowIcon.setCenter(size * 0.5, size * 0.5);
    }

    @Override
    public void onHover(Point position) {
        // This widget is not interactive, so this method does nothing.
    }

    @Override
    public GraphicsObject getGraphics() {
        return group;
    }
}