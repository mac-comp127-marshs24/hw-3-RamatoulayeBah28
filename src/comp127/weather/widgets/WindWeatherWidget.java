package comp127.weather.widgets;

import comp127.weather.api.CurrentConditions;
import comp127.weather.api.WeatherData;
import edu.macalester.graphics.*;

public class WindWeatherWidget implements WeatherWidget {
    private double size;
    private GraphicsGroup group;

    private GraphicsText windLabel;
    private GraphicsText description;
    private GraphicsGroup icon;
    private GraphicsGroup windial;

     /**
     * Constructs a WindWeatherWidget with the given size.
     *
     * @param size The size of the widget.
     */
    public WindWeatherWidget(double size) {
        this.size = size;

        group = new GraphicsGroup();

        icon = new GraphicsGroup();
        group.add(icon);

        windial = new GraphicsGroup();
        windial.add(new Line(0,0,0,-size * 0.4));
        windial.add(new Ellipse(-size * 0.4, -size * 0.4, size * 0.8, size * 0.8));

        // Text showing the speed of the wind
        windLabel = new GraphicsText();
        windLabel.setFont(FontStyle.BOLD, size * 0.1);
        group.add(windLabel);

        description = new GraphicsText();
        description.setFont(FontStyle.BOLD, size * 0.05);
        group.add(description);

        updateLayout();
    }

    public void update(WeatherData data) {
        group.removeAll();

        CurrentConditions currentConditions = data.getCurrentConditions();

        icon = createIcon(currentConditions);
        icon.setCenter(size * 0.5, size * 0.5);
        windial.setPosition(size * 0.5, size * 0.5);
        windial.setRotation(currentConditions.getWindDirectionInDegrees());
        group.add(windial);
        group.add(icon);

        description.setText("Wind W");

        windLabel.setText(
            FormattingHelpers.windFormat(currentConditions.getWindSpeed()));
        windLabel.setFontSize(size * 0.07);

        windLabel.setCenter(size * 0.5, size * 0.7);
        icon.add(windLabel);

        group.add(description);

        updateLayout();
    }

    private GraphicsGroup createIcon(CurrentConditions currentConditions) {
        // windIcon stores the graphics for displaying the wind direction.
        GraphicsGroup windIcon = new GraphicsGroup();

        // the "N" label referencing to north is added to the icon to then represent the wind direction.
        GraphicsText label = new GraphicsText("N");
        label.setCenter(size * 0.5, size * 0.2);
        label.setFontSize(size * 0.05);

        windIcon.add(label);

        return windIcon;
    }

    private void updateLayout() {
        icon.setCenter(size * 0.5, size * 0.4);
        windLabel.setCenter(size * 0.5, size * 0.6); // Center the wind speed in the middle
    
        double windLabelnMaxY = windLabel.getBounds().getMaxY();
        double descriptionY = windLabelnMaxY + size * 0.95; // Position description "Wind W" below the icon
        description.setCenter(size * 0.5, descriptionY);

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
